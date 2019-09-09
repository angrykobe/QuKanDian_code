package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;

/**
 * Created by yuzuoning on 2017/9/20.
 */

public class DialogBindOtherWechat extends BaseDialog implements View.OnClickListener {
    private TextView mTvCancelBtn;
    private TextView mTvBindBtn;
    private OnBindListener mOnBindListener;

    public DialogBindOtherWechat(Context context,OnBindListener onBindListener) {
        super(context, R.style.zhangku_dialog);
        mOnBindListener = onBindListener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_bind_other_wechat_view;
    }

    @Override
    protected void initView() {
        mTvCancelBtn = findViewById(R.id.dialog_bind_other_cancel);
        mTvBindBtn = findViewById(R.id.dialog_bind_other_bind);

        mTvCancelBtn.setOnClickListener(this);
        mTvBindBtn.setOnClickListener(this);
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_bind_other_cancel:
                dismiss();
                break;
            case R.id.dialog_bind_other_bind:
                if(null != mOnBindListener){
                    mOnBindListener.onBindListener();
                }
                dismiss();
                break;
        }
    }

    public interface OnBindListener{
        void onBindListener();
    }
}
