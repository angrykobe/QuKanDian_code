package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutGiftProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class DialogWithdrawals extends BaseDialog implements View.OnClickListener {
    private TextView mTvTitle;
    private TextView mTvCancelBtn;
    private TextView mTvConfirmBtn;

    private LinearLayout mTitleLayout;

    private OnCliclBtnListener mOnCliclBtnListener;
    private PutGiftProtocol mPutGiftProtocol;

    public void setId(int id) {
        mId = id;
        if (id == -1) {
            mTvCancelBtn.setVisibility(View.GONE);
        } else {
            mTvCancelBtn.setVisibility(View.VISIBLE);
        }
    }

    public void hideCancelBtn() {
        mTvCancelBtn.setVisibility(View.GONE);
    }

    private int mId = -1;

    public DialogWithdrawals(Context context, OnCliclBtnListener onCliclBtnListener) {
        super(context, R.style.zhangku_dialog);
        mOnCliclBtnListener = onCliclBtnListener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_withdrawals_view;
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.dialog_withdrawals_title);
        mTvCancelBtn = (TextView) findViewById(R.id.dialog_withdrawals_cancel);
        mTvConfirmBtn = (TextView) findViewById(R.id.dialog_withdrawals_confirm);
        mTitleLayout = (LinearLayout) findViewById(R.id.dialog_title_layout);

        mTvCancelBtn.setOnClickListener(this);
        mTvConfirmBtn.setOnClickListener(this);

    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void showTitle() {
        mTitleLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_withdrawals_cancel:
                dismiss();
                break;
            case R.id.dialog_withdrawals_confirm:
                if (mId == -1) {
                    dismiss();
                } else if (mId == -2) {
                    if (null != mOnCliclBtnListener) {
                        mOnCliclBtnListener.onCliclSkipListener();
                    }
                    dismiss();
                } else {
                    if (null == mPutGiftProtocol) {
                        if (null != mOnCliclBtnListener) {
                            mOnCliclBtnListener.onClickConfirmListener(true);
                        }
                        mPutGiftProtocol = new PutGiftProtocol(getContext(), new BaseModel.OnResultListener<Boolean>() {
                            @Override
                            public void onResultListener(Boolean response) {
                                UserManager.ANSWERED = true;
                                if (null != mOnCliclBtnListener) {
                                    mOnCliclBtnListener.onClickConfirmListener(false);
                                }
                                mPutGiftProtocol = null;
                            }

                            @Override
                            public void onFailureListener(int code, String error) {
                                if (null != mOnCliclBtnListener) {
                                    mOnCliclBtnListener.onClickConfirmListener(false);
                                }
                                mPutGiftProtocol = null;
                            }
                        });
                        mPutGiftProtocol.putGift(mId);
                    }
                    dismiss();
                }
                break;
        }
    }

    public void setTitles(String s) {
        mTvTitle.setText(s);
    }

    public void setTitles(int id) {
        mTvTitle.setText(id);
    }

    public void setBtnText(String s) {
        mTvConfirmBtn.setText(s);
    }

    public interface OnCliclBtnListener {
        void onCliclSkipListener();

        void onClickConfirmListener(boolean start);
    }

}
