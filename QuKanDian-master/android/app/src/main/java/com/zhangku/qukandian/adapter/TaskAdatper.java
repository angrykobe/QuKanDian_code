package com.zhangku.qukandian.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.PhoneUtils;
import com.duoyou.ad.openapi.DyAdApi;
import com.umeng.analytics.MobclickAgent;
import com.xianwan.sdklibrary.util.XWUtils;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.MainActivity;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.AdMissionBean;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.EventBusBean.ChaiBaoxiangEvent;
import com.zhangku.qukandian.bean.EventBusBean.TaskNewPeopleEvent;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.biz.adcore.yomob.YomobNativeAdLoader;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogNewPeopleTaskForRed;
import com.zhangku.qukandian.dialog.DialogPermissions;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutAdMissionProtocol;
import com.zhangku.qukandian.protocol.PutNewTaskProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.Draw274QR;
import com.zhangku.qukandian.utils.MachineInfoUtil;
import com.zhangku.qukandian.utils.OperateUtil;
import com.zhangku.qukandian.utils.QRShare;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.TaskItemView;
import org.greenrobot.eventbus.EventBus;
import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class TaskAdatper extends BaseRecyclerViewAdapter<TaskBean> {
    private OnClickItemListener mOnClickItemListener;
    private PutAdMissionProtocol mPutAdMissionProtocol;
    private PutNewTaskProtocol mSubmitTaskProtocol;

    public TaskAdatper(Context context, List<TaskBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ItemTaskHolder(new TaskItemView(parent.getContext()));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int viewPosition, final int dataIndex, final TaskBean bean) {
        TaskItemView mTaskItemView = (TaskItemView) holder.itemView;
        mTaskItemView.setData(bean);
        //头部显示
        if (dataIndex == 0) {
            mTaskItemView.getTitleView().setVisibility(View.VISIBLE);
        } else if (mBeans.get(dataIndex - 1).getClassifyId() == bean.getClassifyId()) {//当前item跟上一item分类一致，隐藏头部标签
            mTaskItemView.getTitleView().setVisibility(View.GONE);
        } else {
            mTaskItemView.getTitleView().setVisibility(View.VISIBLE);
        }

        mTaskItemView.getDoTaskTV().setSelected(false);
        if (bean.isButtonEnable()) {
            if (!"已领取".equals(bean.getBindingButton()) || bean.isRepeat()) {
                mTaskItemView.getDoTaskTV().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MobclickAgent.onEvent(mContext, "03_03_clicktofinshbtn");

                        if (Constants.LE_ZHEN_SHI_WAN.equals(bean.getName())) {//乐真试玩
//                            ActivityUtils.startToLeZhenShiWan(mContext,PhoneUtils.getDeviceId());
                            ActivityUtils.startToLeZhenShiWan(mContext, MachineInfoUtil.getInstance().getIMEI(mContext));
                            return;
                        }
                        if (Constants.BAOQU_GAME.equals(bean.getName())) {//豹趣游戏
                            baoQuGame();
                            return;
                        }


                        if (Constants.BINDING_WECHAT.equals(bean.getName())) {
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.WANG_MAI_VIDEO.equals(bean.getGotoLink())) {//旺脉激励视频
                            ActivityUtils.startToVideoAdActivity(mContext, bean);
//                            if (null != mOnClickItemListener) {
//                                mOnClickItemListener.onClickGetGoldTime(bean.getTypeId(), bean.getName(), dataIndex);
//                            }
                        } else if (Constants.OU_PENG_VIDEO.equals(bean.getName())) {//
                            ActivityUtils.startToVideoAdForOupengAct(mContext, bean);
//                            if (null != mOnClickItemListener) {
//                                mOnClickItemListener.onClickGetGoldTime(bean.getTypeId(), bean.getName(), dataIndex);
//                            }
                        } else if (Constants.YUEMENG_NOVEL.equals(bean.getGotoLink())) {//阅盟小说
                            ActivityUtils.startToWebviewForDiankaiSdkAct(v.getContext());
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickGetGoldTime(bean.getTypeId(), bean.getName(), dataIndex);
                            }
                        } else if (Constants.YOMOB_AD.equals(bean.getGotoLink())) {//yomob广告
                            YomobNativeAdLoader yomobNativeAdLoader = new YomobNativeAdLoader();
                            yomobNativeAdLoader.loadAds(mContext);
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickGetGoldTime(bean.getTypeId(), bean.getName(), dataIndex);
                            }
                        } else if (Constants.REGISTER_COIN.equals(bean.getName())) {//注册送红包
                            if (mContext instanceof MainActivity) {
                                MainActivity mainActivity = (MainActivity) TaskAdatper.this.mContext;
                                mainActivity.showDialog(true);
                            }
                        } else if (Constants.FIRST_READING.equals(bean.getName())) {//新手第一次阅读
                            MobclickAgent.onEvent(mContext, "293-xinshouyuedu");
                            DialogNewPeopleTaskForRed dialog = new DialogNewPeopleTaskForRed(mContext, "如何阅读咨讯", R.mipmap.new_people_task_guide_1);
                            dialog.setGotoLink(bean.getGotoLink());
                            dialog.show();
                        } else if (Constants.FIRST_ADS_GOLD.equals(bean.getName())) {//新手第一次广告红包获取
                            MobclickAgent.onEvent(mContext, "293-xinshougghb");
                            DialogNewPeopleTaskForRed dialog = new DialogNewPeopleTaskForRed(mContext, "如何领取阅读红包", R.mipmap.new_people_task_guide_2);
                            dialog.setGotoLink(bean.getGotoLink());
                            dialog.show();
                        } else if (Constants.READING_FIRST.equals(bean.getName())) {
                            ActivityUtils.startToNoviceReadTaskActivity(mContext);
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.FIRST_ON_MENTEE.equals(bean.getName())) {
                            ActivityUtils.startToWriteInviteCodeActivity(mContext);
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.MISSIONS_BAG_1.equals(bean.getName())) {
                            ActivityUtils.startToWithdrawalsActivity(mContext);
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.FIRST_ON_MENTOR.equals(bean.getName())
                                || Constants.INVITE_SUTDENT.equals(bean.getName())
                                || Constants.MENTEE_GOLD.equals(bean.getName())) {
                            ActivityUtils.startToShoutuActivity(mContext);
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.SHARED_INVITE_SUTDENT.equals(bean.getName())) {
                            if (Build.VERSION.SDK_INT >= 23) {
                                if (ContextCompat.checkSelfPermission(mContext,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    new DialogPermissions(mContext).show();
                                } else {
                                    File file = Draw274QR.drawQR(mContext, UserManager.mBitmaps.get(0), "QR1.jpg", "http://"
                                            + UserManager.getInst().getQukandianBean().getHost()
                                            + "/web/quyou/agent/" + UserManager.getInst().getUserBeam().getId()
                                            + "?source=h5_invitation_android_" + QuKanDianApplication.mUmen);
                                    new QRShare().shared(mContext, file);
                                }
                            } else {
                                File file = Draw274QR.drawQR(mContext, UserManager.mBitmaps.get(0), "QR1.jpg", "http://"
                                        + UserManager.getInst().getQukandianBean().getHost()
                                        + "/web/quyou/agent/" + UserManager.getInst().getUserBeam().getId()
                                        + "?source=h5_invitation_android_" + QuKanDianApplication.mUmen);
                                new QRShare().shared(mContext, file);
                            }
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.READING_ON.equals(bean.getName())) {
                            ActivityUtils.startToMainActivity(mContext, Constants.TAB_INFORMATION, 0);
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.BASK_MOMENT_COIN.equals(bean.getName())) {
                            ActivityUtils.startToShareIncomeActivity(mContext);
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.AWAKEN_MENTOR_GOLD.equals(bean.getName())) {
                            ActivityUtils.startToShoutuActivity(mContext);
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.SHARE_ARTICLE.equals(bean.getName())) {
                            ActivityUtils.startToMainActivity(mContext, 0, 0);
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.NOVICE_ANSWER.equals(bean.getName())) {
                            MobclickAgent.onEvent(mContext, "293-xinshoudati");
//                            ActivityUtils.startToNoviceAnswerActivity(mContext);
                            String url = UserManager.getInst().getQukandianBean().getPubwebUrl() + "/NoviceAnswer/";
                            ActivityUtils.startToWebviewAct(mContext,url,"新手答题");
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.ADS_CLICK_GIFT.equals(bean.getName())) {
                            ActivityUtils.startToMainActivity(mContext, 0, 0);
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.LAO_HUA_FEI.equals(bean.getName())) {
                            if ("领取奖励".equals(bean.getBindingButton())) {//动态任务
                                if (null == mPutAdMissionProtocol) {
                                    mPutAdMissionProtocol = new PutAdMissionProtocol(mContext,
                                            new AdMissionBean(Constants.MISSION_TYPE_HUITOUTIAO, mContext), new BaseModel.OnResultListener<Boolean>() {
                                        @Override
                                        public void onResultListener(Boolean response) {
                                            if (response) {
                                                AdsRecordUtils.getInstance().putString(bean.getDisplayName(), bean.getDisplayName());
                                                if (null != mOnClickItemListener) {
                                                    bean.setBindingButton("已领取");
                                                    notifyItemChanged(viewPosition);
                                                }
                                            }
                                            mPutAdMissionProtocol = null;
                                        }

                                        @Override
                                        public void onFailureListener(int code, String error) {
                                            mPutAdMissionProtocol = null;
                                        }
                                    });
                                    mPutAdMissionProtocol.postRequest();
                                }
                            } else {
                                Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(bean.getGotoLink()));
                                mContext.startActivity(viewIntent);
                                if (null != mOnClickItemListener) {
                                    mOnClickItemListener.onClickItemListener(Constants.LAO_HUA_FEI, dataIndex);
                                }
                            }
                        } else if (Constants.LUCK_BAG.equals(bean.getName())) {//动态任务
                            Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://kyzp.dsh178.com/act.php?bGlzdGlkPTEzOTkw"));
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener(Constants.LUCK_BAG, dataIndex);
                            }
                            mContext.startActivity(viewIntent);
                        } else if (Constants.SEARCH_SOUGOU.equals(bean.getName())) {
                            ActivityUtils.startToMainActivity(mContext, 0, 10000000);
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (bean.getTypeId() > 0 && bean.getTypeId() < 10000) {
                            String urls = bean.getGotoLink();
                            if (urls.startsWith("xcxα")) {// 分享小程序
                                OperateUtil.shareMiniAppMain(urls, (Activity) mContext, null);
                                if (null != mOnClickItemListener) {//分享小程序立马获得金币
                                    mOnClickItemListener.onClickGetGoldNow(bean.getTypeId(), bean.getName(), dataIndex);
                                }
                            } else {
                                Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls));
                                mContext.startActivity(viewIntent);

                                if (null != mOnClickItemListener) {
                                    mOnClickItemListener.onClickGetGoldTime(bean.getTypeId(), bean.getName(), dataIndex);
                                }
                            }
                        } else if (bean.getTypeId() >= 10000) {//是否动态任务 《10000  为动态任务 》 10000  运营活动，不用给金币
                            String urls = bean.getGotoLink();
                            if (urls.contains("http")) {
                                if (urls.contains("flag=chbyxj")) {
                                    ActivityUtils.startToChbyxjUrlActivity(mContext, urls);
                                } else {
                                    ActivityUtils.startToWebviewAct(mContext, urls);
                                }
                            } else if (urls.startsWith("xcxα")) {// 分享小程序
                                OperateUtil.shareMiniAppMain(urls, (Activity) mContext, null);
                            } else {
                                if (urls.contains("|")) {
                                    String[] url = urls.split("\\|");
                                    if (url.length > 1) {
                                        ActivityUtils.startToAssignActivity(mContext, url[0], Integer.valueOf(url[1]));
                                    } else {
                                        ActivityUtils.startToAssignActivity(mContext, urls, -1);
                                    }
                                } else {
                                    ActivityUtils.startToAssignActivity(mContext, urls, -1);
                                }
                            }
                            if (null != mOnClickItemListener) {
                                mOnClickItemListener.onClickItemListener("", dataIndex);
                            }
                        } else if (Constants.XIAN_WAN.equals(bean.getName())) {
                            XWUtils.getInstance(mContext).init("1290", "osy6lxq9ugw2fmcx", UserManager.getInst().getUserBeam().getId() + "");
                            XWUtils.getInstance(mContext).setMode(0);
                            XWUtils.getInstance(mContext).jumpToAd();
                        }else if (Constants.DUO_YOU.equals(bean.getName())){
                            DyAdApi.getDyAdApi().jumpAdList(mContext, UserManager.getInst().getUserBeam().getId() + "", 0);
                        }
                    }
                });
                mTaskItemView.getProcessView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Constants.FIRST_RESOU.equals(bean.getName())) {//新手热搜多任务
                            if (bean.getFinishedTime() == 0) {
                                DialogNewPeopleTaskForRed dialog = new DialogNewPeopleTaskForRed(mContext, "如何完成热搜", R.mipmap.pic_03);
                                dialog.setGotoLink(bean.getGotoLink());
                                dialog.show();
                            } else if (bean.getAwardsTime() <= bean.getFinishedTime()) {
                                if (null == mSubmitTaskProtocol) {
                                    mSubmitTaskProtocol = new PutNewTaskProtocol(mContext, "first_resou", new BaseModel.OnResultListener<DoneTaskResBean>() {
                                        @Override
                                        public void onResultListener(DoneTaskResBean response) {
                                            CustomToast.showToast(mContext, response.getGoldAmount() + "", response.getDescription());
                                            mSubmitTaskProtocol = null;
                                            EventBus.getDefault().post(new TaskBean());

                                            EventBus.getDefault().post(new TaskNewPeopleEvent(true));//任务列表-新手福利做完-要刷新主页的新手福利标识
                                        }

                                        @Override
                                        public void onFailureListener(int code, String error) {
                                            ToastUtils.showShortToast(mContext, error);
                                            mSubmitTaskProtocol = null;
                                        }
                                    });
                                    mSubmitTaskProtocol.postRequest();
                                }
                            } else {
                                //跳转热搜页面
//                                ActivityUtils.startToMainActivity(mContext, 0, 10000000);
                                EventBus.getDefault().post(new ChaiBaoxiangEvent(true));
                            }

                        } else {
                            if("幸运任务".equals(bean.getDisplayName())){
                                MobclickAgent.onEvent(mContext, "293-xinshoulucky");
                            }
                            ActivityUtils.startToTaskWebViewActivity(mContext, bean);
                        }
                    }
                });
            } else {
                mTaskItemView.getDoTaskTV().setSelected(true);
                mTaskItemView.getDoTaskTV().setOnClickListener(null);
            }
            setProgressDrawable(mTaskItemView.getProcessBar(), R.drawable.process_task_can_click);
        } else {
            mTaskItemView.getDoTaskTV().setSelected(true);
            setProgressDrawable(mTaskItemView.getProcessBar(), R.drawable.process_task_cannot_click);
            mTaskItemView.getDoTaskTV().setOnClickListener(null);
            mTaskItemView.getProcessView().setOnClickListener(null);
        }
    }

    @SuppressLint("NewApi")
    public static void setProgressDrawable(@NonNull ProgressBar bar, @DrawableRes int resId) {
        Drawable layerDrawable = bar.getResources().getDrawable(resId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable d = getMethod("tileify", bar, new Object[]{layerDrawable, false});
            bar.setProgressDrawable(d);
        } else {
            bar.setProgressDrawableTiled(layerDrawable);
        }
    }

    private static Drawable getMethod(String methodName, Object o, Object[] paras) {
        Drawable newDrawable = null;
        try {
            Class<?> c[] = new Class[2];
            c[0] = Drawable.class;
            c[1] = boolean.class;
            Method method = ProgressBar.class.getDeclaredMethod(methodName, c);
            method.setAccessible(true);
            newDrawable = (Drawable) method.invoke(o, paras);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return newDrawable;
    }

    public void setOnClickItemListener(TaskAdatper.OnClickItemListener onClickItemListener) {
        mOnClickItemListener = onClickItemListener;
    }

    class ItemTaskHolder extends RecyclerView.ViewHolder {

        public ItemTaskHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnClickItemListener {
        void onClickItemListener(String name, int position);

        //获取领取金币所需时间（没有则没有领取金币）
        void onClickGetGoldTime(int typeId, String name, int position);

        void onClickGetGoldNow(int typeId, String name, int position);
    }

    private void baoQuGame(){
        ActivityUtils.startToQuBaoGame(mContext);
    }
}
