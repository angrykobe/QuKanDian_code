package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;

/**
 * Created by yuzuoning on 2017/9/19.
 */

public class DialogBIndPhone extends BaseDialog implements View.OnClickListener {
    private TextView mTvCancelBtn;
    private TextView mTvBindBtn;
    private OnFinishListener mOnFinishListener;

    public DialogBIndPhone(Context context,OnFinishListener onFinishListener) {
        super(context, R.style.zhangku_dialog);
        mOnFinishListener = onFinishListener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_bind_phone_view;
    }

    @Override
    protected void initView() {
        mTvCancelBtn = findViewById(R.id.dialog_bind_finish_cancel);
        mTvBindBtn = findViewById(R.id.dialog_bind_finish_bind);

        mTvBindBtn.setOnClickListener(this);
        mTvCancelBtn.setOnClickListener(this);
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
            case R.id.dialog_bind_finish_cancel:
                if(null != mOnFinishListener){
                    mOnFinishListener.onFinishListener();
                }
                break;
            case R.id.dialog_bind_finish_bind:
                dismiss();
                break;
        }
    }

    public interface OnFinishListener{
        void onFinishListener();
    }
}
