package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.EventBusBean.ShowNewPeopleTaskDialogBean;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/27
 * 新用户首次领取奖励弹窗
 */
public class DialogNewPeopleDoneTask extends BaseDialog {
    private String goldNum;
    private TextView mTitleTV;
    private TextView mBottomBtn;

    public DialogNewPeopleDoneTask(Context context, String goldNum) {
        super(context);
        this.goldNum = goldNum;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_for_new_people_done_task;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().post(new NewPeopleTaskBean());//
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        TextView contentTV = findViewById(R.id.contentTV);
        mTitleTV = findViewById(R.id.titleTV);
        findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(mContext, "293-qtrwguanb");
                dismiss();
            }
        });
        contentTV.setText(getContext().getString(R.string.new_people_done_task, goldNum));
        mBottomBtn = findViewById(R.id.doneOtherTaskBtn);
        mBottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(mContext, "293-wanchengqtrw");
                ActivityUtils.startToMainActivity(getContext(), 2, 0);
                dismiss();
            }
        });
    }

    @Override
    protected boolean isCanceledOnTouchOutside() {
        return false;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().post(new ShowNewPeopleTaskDialogBean());
    }

    @Override
    public void show() {
        super.show();
        MobclickAgent.onEvent(mContext, "294-showfirstreward");
    }

    public void setTitle(String title) {
        mTitleTV.setText("" + title);
        mBottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBottomBtn.setText("知道了");
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }
}
