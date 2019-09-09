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
 * Created by yuzuoning on 2017/3/31.
 */

public class DialogUserHeader extends BaseDialog implements View.OnClickListener {
    private TextView mTvCamera;
    private TextView mTvAlbum;
    private TextView mTvCancel;
    private OnBtnClickListener mOnBtnClickListener;

    public DialogUserHeader(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_user_header_layout;
    }

    @Override
    protected void initView() {
        mTvCamera = (TextView) findViewById(R.id.dialog_user_header_layout_camera);
        mTvAlbum = (TextView) findViewById(R.id.dialog_user_header_layout_ablum);
        mTvCancel = (TextView) findViewById(R.id.dialog_user_header_layout_cancel);

        mTvCamera.setOnClickListener(this);
        mTvAlbum.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
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
            case R.id.dialog_user_header_layout_camera:
                if (null != mOnBtnClickListener) {
                    mOnBtnClickListener.onCameraClickListener();
                }
                break;
            case R.id.dialog_user_header_layout_ablum:
                if (null != mOnBtnClickListener) {
                    mOnBtnClickListener.onAlbumClickListener();
                }
                break;
            case R.id.dialog_user_header_layout_cancel:
                dismiss();
                break;

        }
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        mOnBtnClickListener = onBtnClickListener;
    }

    public interface OnBtnClickListener {
        void onCameraClickListener();

        void onAlbumClickListener();
    }
}
