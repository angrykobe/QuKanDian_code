package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;

/**
 * Created by yuzuoning on 2017/9/19.
 */

public class DialogBindRemind extends BaseDialog {
    private TextView mTvTitle;
    private TextView mTvConfirBtn;

    public DialogBindRemind(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_bind_remind_view;
    }

    @Override
    protected void initView() {
        mTvTitle = findViewById(R.id.dialog_bind_remind_title);
        mTvConfirBtn = findViewById(R.id.dialog_bind_remind_confirm);

        mTvConfirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public void shows(String title){
        show();
        mTvTitle.setText(title);
    }
}
