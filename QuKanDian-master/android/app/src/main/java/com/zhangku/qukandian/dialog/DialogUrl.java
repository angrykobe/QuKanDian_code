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
 * Created by yuzuoning on 2017/5/5.
 */

public class DialogUrl extends BaseDialog implements View.OnClickListener {
    private TextView mTvRefresh;
    private TextView mTvCopy;
    private TextView mTvCancel;

    private OnBtnClickListener mOnBtnClickListener;

    public DialogUrl(Context context, OnBtnClickListener onBtnClickListener) {
        super(context, R.style.zhangku_dialog);
        mOnBtnClickListener = onBtnClickListener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_url_view;
    }

    @Override
    protected void initView() {
        mTvRefresh = (TextView) findViewById(R.id.dialog_url_view_refresh);
        mTvCopy = (TextView) findViewById(R.id.dialog_url_view_copy);
        mTvCancel = (TextView) findViewById(R.id.dialog_url_view_cancel);

        mTvRefresh.setOnClickListener(this);
        mTvCopy.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);


    }

    @Override
    protected void release() {
        mTvRefresh = null;
        mTvCopy = null;
        mTvCancel = null;
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

            case R.id.dialog_url_view_refresh:
                if (null != mOnBtnClickListener) {
                    mOnBtnClickListener.onClickRefreshListener();
                }
                break;
            case R.id.dialog_url_view_copy:
                if (null != mOnBtnClickListener) {
                    mOnBtnClickListener.onClickCopyListener();
                }
                break;
            case R.id.dialog_url_view_cancel:
                dismiss();
                break;

        }
    }

    public interface OnBtnClickListener {
        void onClickRefreshListener();

        void onClickCopyListener();
    }
}
