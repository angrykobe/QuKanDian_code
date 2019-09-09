package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;


/**
 * 退出APP的弹窗：阅读进度没做完才提示
 */
public class DialogExitTip extends BaseDialog implements View.OnClickListener {
    private TextView mTvCancelBtn;
    private TextView mTvConfirmBtn;
    private TextView mTvRemind;
    private OnFinishListener mOnFinishListener;

    private int sum;//还需阅读数量
    private int gold;//可领取金币

    public DialogExitTip(Context context, int sum, int gold, OnFinishListener onFinishListener) {
        super(context, R.style.zhangku_dialog);
        mOnFinishListener = onFinishListener;
        this.sum = sum;
        this.gold = gold;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_exit_tip;
    }

    @Override
    protected void initView() {
        mTvCancelBtn = (TextView) findViewById(R.id.dialog_exit_cancel);
        mTvConfirmBtn = (TextView) findViewById(R.id.dialog_exit_confirm);
        mTvRemind = (TextView) findViewById(R.id.dialog_exit_remind);
        mTvCancelBtn.setOnClickListener(this);
        mTvConfirmBtn.setOnClickListener(this);

        mTvRemind.setText("今日再阅读" + sum + "篇红包文章即可领取" + gold + "金币奖励，是否继续阅读？");
    }


    @Override
    protected void release() {
        mTvCancelBtn = null;
        mTvConfirmBtn = null;
        mTvRemind = null;
    }

    @Override
    protected void setPosition() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_exit_cancel:
                if (null != mOnFinishListener) {
                    mOnFinishListener.onFinishListener();
                }
                break;
            case R.id.dialog_exit_confirm:
                dismiss();
                break;
        }
    }

    public interface OnFinishListener {
        void onFinishListener();
    }
}
