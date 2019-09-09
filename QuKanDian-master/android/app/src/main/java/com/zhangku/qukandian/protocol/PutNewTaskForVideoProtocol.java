package com.zhangku.qukandian.protocol;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.NewBaseProtocol;
import com.zhangku.qukandian.bean.SubmitTaskBean;
import com.zhangku.qukandian.config.Config;
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
 * 视频详情获取金币
 */
public class PutNewTaskForVideoProtocol extends NewBaseProtocol<SubmitTaskBean> {
    private ReadGoldView readGoldView;
    private int duration;
    private int postId;

    public PutNewTaskForVideoProtocol(Context context, int postId, ReadGoldView readGoldView, OnResultListener onResultListener) {
        super(context, onResultListener);
        this.readGoldView = readGoldView;
        this.duration = readGoldView.getReadTime();
        this.postId = postId;
    }

    @Override
    protected Call getMyCall() {
        call = getAPIService().putTaskForReadVideo(mAuthorization,
                mContentType,
                CommonHelper.MyCheck(rand, time, "duration" + duration, "postId" + postId),
                time, appid, rand,
                postId, duration);
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
                if ("1".equals(UserManager.getInst().getQukandianBean().getIsShowReadMaxTip())
                        && readGoldView != null) {
                    //今天第一次提示？
                    final String nowDate = TimeUtils.formatPhotoDate(System.currentTimeMillis());
                    final int cacheDateNum = UserSharedPreferences.getInstance().getInt(Constants.READ_DETAIL_FOR_GET_GOLD + nowDate, 0);
                    if (cacheDateNum == 0) {//今天无提示过
                        //展示提示
                        readGoldView.showRemind(UserManager.getInst().getReadTips(dou, mContext.getString(R.string.read_remindstr_get_tomorrow)), mContext.getString(R.string.know));
                        UserSharedPreferences.getInstance().putInt(Constants.READ_DETAIL_FOR_GET_GOLD + nowDate, cacheDateNum + 1);
                    } else if (cacheDateNum == 1) {//今天提示过一次
                        //展示提示
                        readGoldView.showRemind(UserManager.getInst().getReadTips(dou, mContext.getString(R.string.read_remindstr_get_tomorrow)), mContext.getString(R.string.read_remindbtn_no_show), new View.OnClickListener() {
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
                if (readGoldView != null) {
//                    readGoldView.showRemind("该视频今天已经领过奖励了，去观看其他视频吧", "知道了");
                    readGoldView.showRemind(UserManager.getInst().getReadTips(dou, "该视频今天已经领过奖励了，去观看其他视频吧"), "知道了");
                }
                break;
            case "0":
            case "3":
            case "4":
            case "6":
                if (readGoldView != null) {
//                    readGoldView.showRemind("诶呀，本视频的阅读奖励被趣友领完了，去继续观看其他视频赚金币吧", "继续观看");
                    readGoldView.showRemind(UserManager.getInst().getReadTips(dou, "诶呀，本视频的阅读奖励被趣友领完了，去继续观看其他视频赚金币吧"), "继续观看");
                }
                break;
            case "double":
                new DialogDoubleGold(getContext(), gold).show();
                break;
            case "有效阅读":
                CustomToast.showToast(mContext, "+" + gold, "阅读奖励");
                break;
            case "7":
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
        UserManager.getInst().goldChangeNofity(Integer.valueOf(temp));
        super.getResult(submitTaskBean);
    }
}
