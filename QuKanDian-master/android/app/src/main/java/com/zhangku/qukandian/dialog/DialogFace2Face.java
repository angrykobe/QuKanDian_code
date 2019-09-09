package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.config.Config;

/**
 * Created by yuzuoning on 2017/7/28.
 */

public class DialogFace2Face extends BaseDialog implements View.OnClickListener {
    private TextView mTvSaveBtn;
    private TextView mTvCancelBtn;
    private OnClickSaveListener mOnClickSaveListener;

    public DialogFace2Face(Context context, OnClickSaveListener onClickSaveListener) {
        super(context, R.style.zhangku_dialog);
        mOnClickSaveListener = onClickSaveListener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_face2face_layout;
    }

    @Override
    protected void initView() {
        mTvSaveBtn = (TextView) findViewById(R.id.dialog_f2f_save);
        mTvCancelBtn = (TextView) findViewById(R.id.dialog_f2f_cancel);

        mTvSaveBtn.setOnClickListener(this);
        mTvCancelBtn.setOnClickListener(this);
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
            case R.id.dialog_f2f_save:
                if (null != mOnClickSaveListener) {
                    mOnClickSaveListener.onClickSaveListener();
                }
                dismiss();
                break;
            case R.id.dialog_f2f_cancel:
                dismiss();
                break;
        }
    }

    public interface OnClickSaveListener {
        void onClickSaveListener();
    }
}
