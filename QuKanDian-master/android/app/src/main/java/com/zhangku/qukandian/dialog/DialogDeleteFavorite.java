package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;

/**
 * Created by yuzuoning on 2017/8/9.
 */

public class DialogDeleteFavorite extends BaseDialog implements View.OnClickListener {
    private TextView mTvTite;
    private TextView mTvCancelBtn;
    private TextView mTvConfirmBtn;
    private int mPosition;
    private OnClcilConfirmListener mOnClcilConfirmListener;
    public DialogDeleteFavorite(Context context,OnClcilConfirmListener onClcilConfirmListener) {
        super(context, R.style.zhangku_dialog);
        mOnClcilConfirmListener = onClcilConfirmListener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_delete_favorite_layout;
    }

    @Override
    protected void initView() {
        mTvTite = (TextView) findViewById(R.id.dialog_detele_favorite_title);
        mTvCancelBtn = (TextView) findViewById(R.id.dialog_detele_favorite_cancel_btn);
        mTvConfirmBtn = (TextView) findViewById(R.id.dialog_detele_favorite_confirm_btn);

        mTvCancelBtn.setOnClickListener(this);
        mTvConfirmBtn.setOnClickListener(this);

    }

    public void setTitle(String title,int position){
        mTvTite.setText(title);
        mPosition = position;
    }


    public void setTitle(String title){
        mTvTite.setText(title);
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_detele_favorite_cancel_btn:
                dismiss();
                break;
            case R.id.dialog_detele_favorite_confirm_btn:
                if(null != mOnClcilConfirmListener){
                    mOnClcilConfirmListener.onClcilConfirmListener(mPosition);
                }
                dismiss();
                break;
        }
    }

    public interface OnClcilConfirmListener{
        void onClcilConfirmListener(int position);
    }
}
