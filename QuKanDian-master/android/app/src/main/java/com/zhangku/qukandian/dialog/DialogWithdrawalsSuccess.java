package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.utils.ActivityUtils;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/27
 */
public class DialogWithdrawalsSuccess extends BaseDialog {

    public DialogWithdrawalsSuccess(Context context) {
        super(context);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_withdrawals_success_remind;
    }

    @Override
    protected void initView() {
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        findViewById(R.id.knowTV).setOnClickListener(new View.OnClickListener() {
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
