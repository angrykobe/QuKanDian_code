package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.EventBusBean.TaskChestEvent;
import com.zhangku.qukandian.bean.EventBusBean.TaskChestRefreshEvent;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.bean.TaskChestBean;
import com.zhangku.qukandian.dialog.DialogTaskChestProcess;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutNewTaskchestBoxProtocol;
import com.zhangku.qukandian.utils.AnimUtil;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/10/30
 * 任务 宝箱
 */
public class TaskChestView extends FrameLayout implements View.OnClickListener {
    private TextView titleTV;
    //    private TextView mTaskNumTV;
    private TextView mOneTaskTV;
    private TextView mTwoTaskTV;
    private TextView mThreeTaskTV;

    private View mThreeTaskAnimImg;
    private View mTwoTaskAnimImg;
    private View mOneTaskAnimImg;
    //    private LinearLayout mOneTaskView;
    private LinearLayout mTwoTaskView;
    private LinearLayout mThreeTaskView;
    private List<TaskChestBean> mList;
    private boolean isAllOpen = false;//是否所有宝箱已经开启，（为了减少数据请求次数定义的变量）
    private View mOneTaskImg;
    private View mTwoTaskImg;
    private View mThreeTaskImg;

    public TaskChestView(@NonNull Context context) {
        super(context);
    }

    public TaskChestView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskChestView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_task_chest, this);

//        this.mOneTaskView = (LinearLayout) findViewById(R.id.mOneTaskView);
        this.mTwoTaskView = (LinearLayout) findViewById(R.id.mTwoTaskView);
        this.mThreeTaskView = (LinearLayout) findViewById(R.id.mThreeTaskView);

        this.mOneTaskTV = findViewById(R.id.mOneTaskTV);
        this.mTwoTaskTV = findViewById(R.id.mTwoTaskTV);
        this.mThreeTaskTV = findViewById(R.id.mThreeTaskTV);

        this.mOneTaskAnimImg = findViewById(R.id.mOneTaskAnimImg);
        this.mTwoTaskAnimImg = findViewById(R.id.mTwoTaskAnimImg);
        this.mThreeTaskAnimImg = findViewById(R.id.mThreeTaskAnimImg);

//        this.mTaskNumTV = (TextView) findViewById(R.id.mTaskNumTV);
        this.titleTV = findViewById(R.id.titleTV);
        titleTV.setText(Html.fromHtml(getContext().getString(R.string.task_chest_title)));

        mOneTaskImg = findViewById(R.id.mOneTaskImg);
        mOneTaskImg.setOnClickListener(this);
        mTwoTaskImg = findViewById(R.id.mTwoTaskImg);
        mTwoTaskImg.setOnClickListener(this);
        mThreeTaskImg = findViewById(R.id.mThreeTaskImg);
        mThreeTaskImg.setOnClickListener(this);

        findViewById(R.id.mOneTaskTV).setOnClickListener(this);
        findViewById(R.id.mTwoTaskTV).setOnClickListener(this);
        findViewById(R.id.mThreeTaskTV).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mOneTaskImg:
            case R.id.mOneTaskTV:
//            case R.id.mOneTaskView:
                if (mList != null && mList.size() > 0) {
                    click(mList.get(0), mOneTaskTV, mOneTaskImg, mOneTaskAnimImg);
                }
                break;
            case R.id.mTwoTaskImg:
            case R.id.mTwoTaskTV:
//            case R.id.mTwoTaskView:
                if (mList != null && mList.size() > 1) {
                    click(mList.get(1), mTwoTaskTV, mTwoTaskImg, mTwoTaskAnimImg);
                }
                break;
            case R.id.mThreeTaskImg:
            case R.id.mThreeTaskTV:
//            case R.id.mThreeTaskView:
                if (mList != null && mList.size() > 2) {
                    click(mList.get(2), mThreeTaskTV, mThreeTaskImg, mOneTaskAnimImg);
                }
                break;
        }
    }

    private void click(final TaskChestBean bean, final TextView v, final View imgView, final View animView) {
        switch (bean.getChestBoxType()) {
            case 0://未开启
                ToastUtils.showLongToast(getContext(), "请先打开前面的宝箱~");
                break;
            case 1://查看进度
                MobclickAgent.onEvent(getContext(), "293-baoxiangjindu");
//                DialogTaskChestProcess dialogTaskChestProcess = new DialogTaskChestProcess(getContext());
//                dialogTaskChestProcess.show();
//                dialogTaskChestProcess.setData(bean);

                EventBus.getDefault().post(new TaskChestEvent(bean));
                break;
            case 2://已完成
                //开启宝箱
                MobclickAgent.onEvent(getContext(), "293-renwubaoxiang_kai");
                new PutNewTaskchestBoxProtocol(getContext(), bean, new BaseModel.OnResultListener<DoneTaskResBean>() {
                    @Override
                    public void onResultListener(DoneTaskResBean response) {
                        CustomToast.showToast(getContext(), "" + response.getGoldAmount(), "");
                        bean.setChestBoxType(3);
                        v.setText(R.string.task_chest_open);
                        EventBus.getDefault().post(new TaskBean());
                        imgView.setSelected(true);
                        UserManager.getInst().goldChangeNofity(response.getGoldAmount());
                        AnimUtil.animRotation(animView);

                        EventBus.getDefault().post(new TaskChestRefreshEvent(true));
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                    }
                }).postRequest();
                break;
            case 3://已领取
                break;
        }
    }

    public void setData(List<TaskChestBean> response) {
        mList = response;
        if (response.size() >= 3) {
            // 宝箱状态，0:未开启，1：查看进度（未完成），2：可领取，3：已领取
            init(response.get(0), mOneTaskTV, null, mOneTaskImg);
            init(response.get(1), mTwoTaskTV, mTwoTaskView, mTwoTaskImg);
            init(response.get(2), mThreeTaskTV, mThreeTaskView, mThreeTaskImg);
        }
        if (getContext().getString(R.string.task_chest_open).equals(mThreeTaskTV.getText().toString())) {
            isAllOpen = true;
        }
    }

    private void init(TaskChestBean taskBean, TextView taskTV, View taskView, View imgView) {
        switch (taskBean.getChestBoxType()) {
            case 0:
                taskTV.setText(R.string.task_chest_no_open);//文字
                if (taskView != null) taskView.setSelected(false);//进度是否已经进行
                if (imgView != null) imgView.setSelected(false);
                break;
            case 1:
                taskTV.setText(Html.fromHtml(getContext().getString(R.string.task_chest_doing)));
                if (taskView != null) taskView.setSelected(true);
                if (imgView != null) imgView.setSelected(false);
                break;
            case 2:
                taskTV.setText(R.string.task_chest_can_open);
                if (taskView != null) taskView.setSelected(true);
                if (imgView != null) imgView.setSelected(false);
                break;
            case 3:
                taskTV.setText(R.string.task_chest_open);
                if (taskView != null) taskView.setSelected(true);
                if (imgView != null) imgView.setSelected(true);
                break;
        }
    }

    public boolean isAllOpen() {
        return isAllOpen;
    }
}