package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.TaskChestBean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/10/31
 * 任务宝箱进度
 */
public class DialogTaskChestProcess extends BaseDialog implements View.OnClickListener {

    private TextView mGoldNumTV;
    private View mOneView;
    private View mTwoView;
    private View mThreeView;
    private View mFourView;

    public DialogTaskChestProcess(Context context) {
        super(context);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_task_chest_process;
    }

    @Override
    protected void initView() {
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        findViewById(R.id.suerTV).setOnClickListener(this);
        mGoldNumTV = findViewById(R.id.mGoldNumTV);

        mOneView = findViewById(R.id.mOneView);
        mTwoView = findViewById(R.id.mTwoView);
        mThreeView = findViewById(R.id.mThreeView);
        mFourView = findViewById(R.id.mFourView);

    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    public DialogTaskChestProcess setData(TaskChestBean bean) {
        //有效阅读
        setItemData(bean.getReadFinishedNum(), bean.getReadNum() , mOneView);
        //阅读红包
        setItemData(bean.getAdsFinishedNum(), bean.getAdsNum() , mTwoView);
        //热搜
        setItemData(bean.getResouFinishedNum(), bean.getResouNum() , mThreeView);
        //幸运任务
        setItemData(bean.getLuckyFinishedNum(), bean.getLuckyNum() , mFourView);

        if (bean.getMinGold() == bean.getMaxGold()) {
            mGoldNumTV.setText(bean.getMaxGold() + "金币");
        } else {
            mGoldNumTV.setText(bean.getMinGold() + "~" + bean.getMaxGold() + "金币");
        }
        return this;
    }

    private void setItemData(int finishCount, int allCount, View view) {
        if (allCount == 0) view.setVisibility(View.GONE);
            else view.setVisibility(View.VISIBLE);

        String str1 = finishCount == allCount ? "完成" : "未完成";
        TextView tv = view.findViewById(R.id.task_chest_tv);
        tv.setText(Html.fromHtml(mContext.getString(R.string.task_chest_dialog_process, str1, finishCount, allCount)));

    }
}
