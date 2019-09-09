package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.config.Config;

/**
 * 创建者          xuzhida
 * 创建日期        2018/6/12
 * 提现失败弹框
 */
public class WithdrawalsFailDialog extends BaseDialog implements View.OnClickListener {
    private String failStr;//文章id
    private TextView failTV;

    public WithdrawalsFailDialog(Context context, int themeResId, String failStr) {
        super(context, R.style.zhangku_dialog);
        this.failStr = failStr;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_withdrawals_fail;
    }

    @Override
    protected void initView() {
        findViewById(R.id.submitTV).setOnClickListener(this);
        failTV = findViewById(R.id.failTV);
        failTV.setText(Html.fromHtml(failStr));
        setToBottom();
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = Config.SCREEN_WIDTH;
        window.setWindowAnimations(R.style.popupAnimation);
        window.setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitTV:
                dismiss();
                break;
        }
    }
}
