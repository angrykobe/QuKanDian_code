package com.zhangku.qukandian.widght;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.MainActivity;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.bean.QkdPushBean;
import com.zhangku.qukandian.bean.RuleBean;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.bean.TaskChestBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogShowLevel;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.LeftAndRightAnimation;
import com.zhangku.qukandian.utils.OperateUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/21
 * 首页小icon
 */
public class SmartView extends LinearLayout implements View.OnClickListener {

    private Object data;
    private ImageView mSmartIV;
    private View mSmartClostIV;
    private View mActView;//其他小图标（红包，活动）
    private View mNewPeopleTaskView;//新手福利布局
//    private TextView numTV;//新手福利
    private LeftAndRightAnimation mLeftAndRightAnimationHongbao;
    //    private List<TaskBean> response;
    private ImageView mLevelImg;
    private TextView mLevelProcessTV;
    private DialogShowLevel mDialogShowLevel;

    public SmartView(Context context) {
        super(context);
    }

    public SmartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_smart, this);

        mLeftAndRightAnimationHongbao = new LeftAndRightAnimation();
        mActView = findViewById(R.id.mActView);
        mSmartIV = findViewById(R.id.mSmartIV);
        mNewPeopleTaskView = findViewById(R.id.mNewPeopleTaskView);
//        numTV = findViewById(R.id.numTV);

        mLevelImg = findViewById(R.id.mLevelImg);
        mLevelProcessTV = findViewById(R.id.mLevelProcessTV);

        mSmartClostIV = findViewById(R.id.mSmartClostIV);
        mSmartClostIV.setOnClickListener(this);
//        this.setOnClickListener(this);
        findViewById(R.id.mSmartClostLevelIV).setOnClickListener(this);
        mLevelProcessTV.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mSmartIV:
                if (UserManager.getInst().hadLogin()) {
                    Map<String, String> map = new ArrayMap<>();
                    map.put("count", "click");
                    MobclickAgent.onEvent(getContext(), "FloatIcon", map);
                    if (data instanceof RuleBean.OperationConfigBean) {
                        RuleBean.OperationConfigBean bean = (RuleBean.OperationConfigBean) data;
                        openUrl(bean.getToastGotoLink());
                    } else if (data instanceof RuleBean.InductIconConfigBean) {
                        RuleBean.InductIconConfigBean bean = (RuleBean.InductIconConfigBean) data;
                        //小icon展示次数保存
                        AdsRecordUtils.getInstance().saveHomeSmartEverydayCount(AdsRecordUtils.getInstance().getHomeSmartEverydayCount() + 1);
                        UserSharedPreferences.getInstance().saveHomeSmartDayCount();
                        openUrl(bean.getGotoLink());
                    } else if (data instanceof QkdPushBean) {
                        QkdPushBean bean = (QkdPushBean) data;
                        openUrl(bean.getUrl());
                    }
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
//                this.setVisibility(View.GONE);
                findViewById(R.id.mOtherView).setVisibility(View.GONE);
                break;
            case R.id.mSmartClostIV:
                if (data instanceof RuleBean.InductIconConfigBean) {
                    //小icon展示次数保存
                    AdsRecordUtils.getInstance().saveHomeSmartEverydayCount(AdsRecordUtils.getInstance().getHomeSmartEverydayCount() + 1);
                    UserSharedPreferences.getInstance().saveHomeSmartDayCount();
                }
//                this.setVisibility(View.GONE);
                findViewById(R.id.mOtherView).setVisibility(View.GONE);
                break;
            case R.id.mSmartClostLevelIV:
                setGone();
                MobclickAgent.onEvent(getContext(), "295-lvtubiaoguanbi");
                break;
            case R.id.mLevelView:
            case R.id.mLevelImg:
            case R.id.mLevelProcessTV:
                mDialogShowLevel.show();
                MobclickAgent.onEvent(getContext(), "295-dengjitubiao");
                MobclickAgent.onEvent(getContext(), "296-dengjitubiao1");
                MobclickAgent.onEvent(getContext(), "296-dengjitubiao2");
                break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    public void showLevelView() {
        mNewPeopleTaskView.setVisibility(View.GONE);
        mActView.setVisibility(View.GONE);
        findViewById(R.id.mOtherView).setVisibility(View.VISIBLE);
        findViewById(R.id.mLevelView).setVisibility(View.VISIBLE);
        findViewById(R.id.mLevelView).setOnClickListener(this);
        mLevelImg.setOnClickListener(this);
        if (mDialogShowLevel == null) {
            mDialogShowLevel = new DialogShowLevel(getContext(), mLevelProcessTV);
            mDialogShowLevel.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    //2.9.6.4 去掉进度
                    mLevelProcessTV.setVisibility(View.GONE);
                    mLevelProcessTV.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mLevelProcessTV == null) return;
                            mLevelProcessTV.setVisibility(View.GONE);
                        }
                    }, 5000);
                }
            });
        }
        changeLevelImg();
        startAnim();
        mLevelProcessTV.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mLevelProcessTV == null) return;
                mLevelProcessTV.setVisibility(View.INVISIBLE);
            }
        }, 5000);
    }


    public void changeLevelImg() {
        UserLevelView.setLevelBigImg(mLevelImg);
    }

    public void setData(Object data) {
        this.data = data;
        if (data instanceof RuleBean.OperationConfigBean) {
            mNewPeopleTaskView.setVisibility(View.GONE);
            mActView.setVisibility(View.VISIBLE);
            RuleBean.OperationConfigBean bean = (RuleBean.OperationConfigBean) data;
            GlideUtils.displayImage(getContext(), bean.getThumbNailImage(), mSmartIV);

            Map<String, String> map = new ArrayMap<>();
            map.put("count", "show");
            MobclickAgent.onEvent(getContext(), "FloatIcon", map);
            mSmartIV.setOnClickListener(this);
            mSmartClostIV.setVisibility(View.VISIBLE);
        } else if (data instanceof RuleBean.InductIconConfigBean) {
            mNewPeopleTaskView.setVisibility(View.GONE);
            mActView.setVisibility(View.VISIBLE);
            RuleBean.InductIconConfigBean bean = (RuleBean.InductIconConfigBean) data;
            GlideUtils.displayImage(getContext(), bean.getIcon(), mSmartIV);

            Map<String, String> map = new ArrayMap<>();
            map.put("count", "show");
            MobclickAgent.onEvent(getContext(), "FloatIcon", map);
            mSmartIV.setOnClickListener(this);
            mSmartClostIV.setVisibility(View.VISIBLE);
        } else if (data == null) {
            mNewPeopleTaskView.setVisibility(View.GONE);
            mActView.setVisibility(View.VISIBLE);
            mSmartIV.setImageResource(R.mipmap.chai_hongbao_icon);
            mSmartClostIV.setVisibility(View.GONE);
        } else if (data instanceof QkdPushBean) {
            mNewPeopleTaskView.setVisibility(View.GONE);
            mActView.setVisibility(View.VISIBLE);
            QkdPushBean bean = (QkdPushBean) data;
            GlideUtils.displayImage(getContext(), bean.getImgurl(), mSmartIV);
        } else if (data instanceof List) {
            List<NewPeopleTaskBean> response = (List<NewPeopleTaskBean>) data;
            mNewPeopleTaskView.setVisibility(View.VISIBLE);
            mActView.setVisibility(View.GONE);
            int num = 0;//任务完成数量
            boolean b = false;
            for (TaskBean bean : response) {
                if (bean.isIsFinished()) {
                    num++;
                } else if (Constants.FIRST_RESOU.equals(bean.getName())) {
                    b = bean.getFinishedTime() == bean.getAwardsTime();
                }
            }
//            numTV.setVisibility(View.VISIBLE);
//            if (b) {
//                numTV.setText("金币待领取");
//            } else {
//                numTV.setText("进度" + num + "/" + response.size());
//            }
//            numTV.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (numTV == null) return;
//                    numTV.setVisibility(View.INVISIBLE);
//                }
//            }, 5000);
        }
        this.setVisibility(View.VISIBLE);
        findViewById(R.id.mOtherView).setVisibility(View.VISIBLE);
        findViewById(R.id.mLevelView).setVisibility(View.GONE);
        mLeftAndRightAnimationHongbao.startAnimation(this);
    }

    public void startAnim() {
        mLeftAndRightAnimationHongbao.startAnimation(this);
    }

    public void setGone() {
        this.data = null;
//      this.setVisibility(View.GONE);
        findViewById(R.id.mOtherView).setVisibility(View.GONE);
        mLeftAndRightAnimationHongbao.stopAnimation();
    }

    public void setNewLevleDialog() {
        mDialogShowLevel = new DialogShowLevel(getContext(), mLevelProcessTV);
    }

    private void openUrl(String urls) {
        if (!TextUtils.isEmpty(urls)) {
            if (urls.contains("http")) {
                if (urls.contains("flag=chbyxj")) {
                    ActivityUtils.startToChbyxjUrlActivity(getContext(), urls);
                } else {
                    ActivityUtils.startToWebviewAct(getContext(), urls);
                }
            } else if (urls.startsWith("xcxα")) {
                // 分享小程序
                Context context = getContext();
                if (context instanceof MainActivity) {
                    OperateUtil.shareMiniAppMain(urls, (MainActivity) context, null);
                }
            } else {
                if (urls.contains("|")) {
                    String[] url = urls.split("\\|");
                    if (url.length > 1) {
                        ActivityUtils.startToAssignActivity(getContext(), url[0], Integer.valueOf(url[1]));
                    } else {
                        ActivityUtils.startToAssignActivity(getContext(), urls, -1);
                    }
                } else {
                    ActivityUtils.startToAssignActivity(getContext(), urls, -1);
                }
            }
        }
    }

    //刷新item
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReflashItem(TaskChestBean msg) {
        // switch (msg.getOrder()) {
        //     case 1:
        //         mLevelProcessTV.setText("进度：1/3");
        //         break;
        //     case 2:
        //         mLevelProcessTV.setText("进度：2/3");
        //         break;
        //     case 3:
        //         mLevelProcessTV.setText("进度：3/3");
        //         break;
        // }
    }

    /**
     * postUserBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLevelChange(UserBean userBean) {
        if (mLevelImg != null) {
            changeLevelImg();
        }
    }
}