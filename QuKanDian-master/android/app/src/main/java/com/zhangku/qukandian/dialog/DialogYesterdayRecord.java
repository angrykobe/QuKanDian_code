package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.utils.ActivityUtils;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/27
 * 昨日战报
 */
public class DialogYesterdayRecord extends BaseDialog {

    private String goldNum;

    public DialogYesterdayRecord(Context context, String goldNum) {
        super(context);
        this.goldNum = goldNum;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_yesterday_record;
    }

    @Override
    protected void initView() {
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        TextView contentTV = findViewById(R.id.contentTV);
        contentTV.setText(getContext().getString(R.string.new_people_done_task,goldNum));
        findViewById(R.id.doneOtherTaskBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }
}
