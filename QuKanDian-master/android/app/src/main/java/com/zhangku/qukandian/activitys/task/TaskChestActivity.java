package com.zhangku.qukandian.activitys.task;


import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.EventBusBean.TaskChestEvent;
import com.zhangku.qukandian.bean.EventBusBean.TaskChestRefreshEvent;
import com.zhangku.qukandian.bean.TaskChestBean;
import com.zhangku.qukandian.dialog.DialogPrograss;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewTaskChestProtocol;
import com.zhangku.qukandian.widght.GrayBgActionBar;
import com.zhangku.qukandian.widght.TaskChestView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


/**
 * 2019年5月21日17:14:15
 * ljs
 * 任务宝箱详情页
 */
public class TaskChestActivity extends BaseTitleActivity implements View.OnClickListener {
    private GrayBgActionBar mGrayBgActionBar;

    private TaskChestView mTaskChestView;//任务宝箱
    private GetNewTaskChestProtocol mGetNewTaskChestProtocol;

    private LinearLayout mLlChestSituation;//任务状态
    private TextView mGoldNumTV;
    private View mOneView;
    private View mTwoView;
    private View mThreeView;
    private View mFourView;


    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        //注册EventBus
        EventBus.getDefault().register(this);
        mGrayBgActionBar = (GrayBgActionBar) findViewById(R.id.gray_actionbar_layout);
        mGrayBgActionBar.setBackgroundColor(this.getResources().getColor(R.color.white));
        mGrayBgActionBar.setTvTitle("任务宝箱");
        mTaskChestView = findViewById(R.id.TaskChestView);
        mLlChestSituation = findViewById(R.id.ll_chest_situation);
        mGoldNumTV = findViewById(R.id.mGoldNumTV);
        mOneView = findViewById(R.id.mOneView);
        mTwoView = findViewById(R.id.mTwoView);
        mThreeView = findViewById(R.id.mThreeView);
        mFourView = findViewById(R.id.mFourView);


    }

    @Override
    protected void loadData() {
        super.loadData();
        reflashTaskChestBox();
    }

    //刷新宝箱数据  宝箱数据要更新频繁些，防止任务完成进度没刷新(幸运任务完成 ，热搜获取奖励，文章阅读奖励，广告红包奖励时刷新)
    private void reflashTaskChestBox() {
        mDialogPrograss.show();
        //宝箱进度刷新
        if (!UserManager.getInst().hadLogin()) return;
        if (UserManager.getInst().getQukandianBean().getIsShowTaskChestBox() == 1
                && mGetNewTaskChestProtocol == null
                && mTaskChestView != null
                && !mTaskChestView.isAllOpen()) {
            mGetNewTaskChestProtocol = new GetNewTaskChestProtocol(this, new BaseModel.OnResultListener<List<TaskChestBean>>() {
                @Override
                public void onResultListener(List<TaskChestBean> response) {
                    mDialogPrograss.dismiss();
                    mTaskChestView.setVisibility(View.VISIBLE);
                    mTaskChestView.setData(response);
                    mGetNewTaskChestProtocol = null;
                    if (response.size() > 0)
                        for (TaskChestBean bean : response) {
                            int type = bean.getChestBoxType();
                            if (type == 1 || type == 2) {//宝箱处于查看进度、可领取，显示任务进度详情
                                mLlChestSituation.setVisibility(View.VISIBLE);
                                setData(bean);
                                return;
                            } else {
                                mLlChestSituation.setVisibility(View.GONE);
                            }
                        }
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetNewTaskChestProtocol = null;
                    mDialogPrograss.dismiss();
                }
            });
            mGetNewTaskChestProtocol.postRequest();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_task_chest;
    }

    @Override
    public String setPagerName() {
        return "任务宝箱";
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mGrayBgActionBar = null;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.activity_share_income_layout_edit:
//
//                break;
        }
    }

    private void setData(TaskChestBean bean) {
        //有效阅读
        setItemData(bean.getReadFinishedNum(), bean.getReadNum(), mOneView);
        //阅读红包
        setItemData(bean.getAdsFinishedNum(), bean.getAdsNum(), mTwoView);
        //热搜
        setItemData(bean.getResouFinishedNum(), bean.getResouNum(), mThreeView);
        //幸运任务
        setItemData(bean.getLuckyFinishedNum(), bean.getLuckyNum(), mFourView);

        if (bean.getMinGold() == bean.getMaxGold()) {
            mGoldNumTV.setText(bean.getMaxGold() + "金币");
        } else {
            mGoldNumTV.setText(bean.getMinGold() + "~" + bean.getMaxGold() + "金币");
        }
    }


    private void setItemData(int finishCount, int allCount, View view) {
        if (allCount == 0) view.setVisibility(View.GONE);
        else view.setVisibility(View.VISIBLE);

        String str1 = finishCount == allCount ? "完成" : "未完成";
        TextView tv = view.findViewById(R.id.task_chest_tv);
        tv.setText(Html.fromHtml(this.getString(R.string.task_chest_dialog_process, str1, finishCount, allCount)));

    }

    @Subscribe
    public void onEventMainThread(TaskChestEvent event) {
        if (event != null) {
            setData(event.getMsg());
        }
    }

    @Subscribe
    public void onEventMainThread(TaskChestRefreshEvent event) {
        if (event.getRefresh()) {
            reflashTaskChestBox();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
