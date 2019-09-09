package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.utils.ToastUtils;

/**
 * Created by yuzuoning on 2017/6/14.
 */

public class DialogShareIncome extends BaseDialog implements View.OnClickListener {
    private EditText mEditText;
    private TextView mTvCancel;
    private TextView mTvConfirm;
    private OnConfirmLinstener mOnConfirmLinstener;

    public DialogShareIncome(Context context,OnConfirmLinstener onConfirmLinstener) {
        super(context, R.style.zhangku_dialog);
        mOnConfirmLinstener = onConfirmLinstener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_share_income_view;
    }

    @Override
    protected void initView() {
        mEditText = (EditText) findViewById(R.id.dialog_share_income_view_edit);
        mTvCancel = (TextView) findViewById(R.id.dialog_share_income_view_cancel);
        mTvConfirm = (TextView) findViewById(R.id.dialog_share_income_view_confirm);

        mTvCancel.setOnClickListener(this);
        mTvConfirm.setOnClickListener(this);

    }

    @Override
    protected void release() {
        mTvCancel = null;
        mEditText = null;
        mTvConfirm = null;
    }

    @Override
    protected void setPosition() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_share_income_view_cancel:
                dismiss();
                break;
            case R.id.dialog_share_income_view_confirm:
                if(null != mOnConfirmLinstener){
                    if(!TextUtils.isEmpty(mEditText.getText().toString().trim())){
                        mOnConfirmLinstener.onConfirmListener(mEditText.getText().toString().trim());
                        dismiss();
                    }else{
                        ToastUtils.showShortToast(getContext(),"请输入海报金额");
                    }
                }
                break;
        }
    }
    public interface OnConfirmLinstener{
        void onConfirmListener(String money);
    }
}
