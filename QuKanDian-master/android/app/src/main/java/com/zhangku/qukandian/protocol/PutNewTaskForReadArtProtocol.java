package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.SubmitTaskBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogDoubleGold;
import com.zhangku.qukandian.dialog.DialogNewPeopleDoneTask;
import com.zhangku.qukandian.dialog.DialogNewPeopleTask;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.TimeUtils;
import com.zhangku.qukandian.widght.ReadGoldView;

import retrofit2.Call;

/**
 * 阅读文章详情获取金币
 */

public class PutNewTaskForReadArtProtocol extends NewBaseProtocol<SubmitTaskBean> {
    private String zyId;
    private ReadGoldView readGoldView;
    private int duration;
    private int postId;
    private int from;//0 推送

    public PutNewTaskForReadArtProtocol(Context context, int postId, int from, String zyId, ReadGoldView readGoldView, OnResultListener onResultListener) {
        super(context, onResultListener);
        this.zyId = zyId;
        this.readGoldView = readGoldView;
        this.duration = readGoldView.getReadTime();
        this.postId = postId;
        this.from = from;
    }

    @Override
    protected Call getMyCall() {
        String signature;
        if (TextUtils.isEmpty(zyId)) {
            signature = CommonHelper.MyCheck(rand, time, "postId" + postId, "duration" + duration);
        } else {
            signature = CommonHelper.MyCheck(rand, time, "postId" + postId, "duration" + duration, "zyId" + zyId);
        }
        call = getAPIService().putTaskForReadArticle(mAuthorization,
                mContentType,
                signature,
                time, appid, rand,
                postId, duration, zyId);
        return call;
    }

    @Override
    protected void getResult(SubmitTaskBean submitTaskBean) {
        final String gold = submitTaskBean.getGoldAmount();
        /// description ::
        /// 验证结果:
        /// 0：表示无错误
        /// 1:当天阅读金币上限,不能再奖励了
        /// 2:当天阅读金币奖励册数上限
        /// 3:本次阅读无法获得奖励（概率决定）
        /// 4:接口调用次数过高，可能是刷量
        /// 5:一篇文章只能获得一次奖励
        /// double:双倍奖励
        /// 有效阅读:奖励（兼容旧版本和0是一样的）
        /// 6:本次阅读无法获得奖励（异常）
        /// 7:首次获取阅读奖励
        String dou = submitTaskBean.getDescription();
        switch (dou) {
            case "2":
            case "1"://今日奖励次数已用完
                if ("1".equals(UserManager.getInst().getQukandianBean().getIsShowReadMaxTip())//服务端控制显示数据
                        && readGoldView != null
                        && from != AnnoCon.ART_DETAIL_FROM_PUSH) {
                    //今天第一次提示？
                    final String nowDate = TimeUtils.formatPhotoDate(System.currentTimeMillis());
                    final int cacheDateNum = UserSharedPreferences.getInstance().getInt(Constants.READ_DETAIL_FOR_GET_GOLD + nowDate, 0);
                    if (cacheDateNum == 0) {//今天无提示过
                        //展示提示
                        //294 提示语后台控制
//                        readGoldView.showRemind(R.string.read_remindstr_get_tomorrow, R.string.know);
                        readGoldView.showRemind(UserManager.getInst().getReadTips(dou, "今日阅读奖励已领完，明天再来领吧"), "知道了");
                        UserSharedPreferences.getInstance().putInt(Constants.READ_DETAIL_FOR_GET_GOLD + nowDate, cacheDateNum + 1);
                    } else if (cacheDateNum == 1) {//今天提示过一次
                        //展示提示
                        readGoldView.showRemind(UserManager.getInst().getReadTips(dou, "今日阅读奖励已领完，明天再来领吧"), "不再提示", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UserSharedPreferences.getInstance().putInt(Constants.READ_DETAIL_FOR_GET_GOLD + nowDate, cacheDateNum + 1);
                                readGoldView.hideRemind();
                            }
                        });
                    } else {//今天提示过1次以上
                        //  无操作
                    }
                }
                break;
            case "5":
                if (readGoldView != null && from != AnnoCon.ART_DETAIL_FROM_PUSH) {
                    readGoldView.showRemind(UserManager.getInst().getReadTips(dou, "该文章的奖励今天已领取过了哦~"), "知道了");
                }
                break;
            case "0":
            case "3":
            case "4":
            case "6":
                if (readGoldView != null && from != AnnoCon.ART_DETAIL_FROM_PUSH) {
                    readGoldView.showRemind(UserManager.getInst().getReadTips(dou, "诶呀，本文的阅读奖励被趣友领完了，去继续阅读其他文章赚金币吧"), "继续阅读");
                }
                break;
            case "double":
                voice();
                new DialogDoubleGold(getContext(), gold).show();
                break;
            case "有效阅读":
                voice();
                CustomToast.showToast(mContext, "+" + gold, "阅读奖励");
                break;
            case "7":// 7:首次获取阅读奖励
//                new DialogNewPeopleTask(mContext,null,true).show();
                MobclickAgent.onEvent(mContext, "294-finishyouxiaoyuedu");
                new DialogNewPeopleDoneTask(mContext, gold).show();
                break;
        }
        String temp;
        if ("double".equals(dou)) {
            temp = TextUtils.isEmpty(gold) ? "0" : (Integer.valueOf(gold) * 2) + "";
        } else {
            temp = TextUtils.isEmpty(gold) ? "0" : gold;
        }
        if (UserManager.getInst().hadLogin()) {
            if (Integer.valueOf(temp) != 0) {
                UserManager.getInst().goldChangeNofity(Integer.valueOf(temp));
            }
        }
        if (submitTaskBean.getAdsCnt() == -1) {
            AdUtil.mAdsCnt--;
        }
        if (submitTaskBean.getAdsCnt() >= 0) {
            AdUtil.mAdsCnt = submitTaskBean.getAdsCnt();
        }
        super.getResult(submitTaskBean);
    }

    private void voice() {
        boolean isPlay = UserSharedPreferences.getInstance().getBoolean(Constants.ART_GOLD_SOUND, true);
        if (isPlay) {
            final SoundPool soundPool;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                soundPool = new SoundPool.Builder()
                        .setMaxStreams(1)
                        .build();
            } else {
                soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
            }
            soundPool.load(mContext, R.raw.gold, 1);

            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool sound, int sampleId, int status) {
                    soundPool.play(1, 1, 1, 0, 0, 1);
                }
            });
        }
    }
}
