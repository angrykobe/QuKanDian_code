package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutGiftProtocol;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class DialogConfirm extends BaseDialog implements View.OnClickListener {
    private TextView mTvTitle;
    private TextView mTvMessage;
    private TextView mTvNoBtn;
    private TextView mTvYesBtn;
    private View.OnClickListener listener;

    private String titleStr;
    private String messageStr;
    private String sureStr;
    private String cancleStr;

    public DialogConfirm(Context context, View.OnClickListener onDialogConfirmListener) {
        super(context, R.style.zhangku_dialog);
        listener = onDialogConfirmListener;
    }

    public DialogConfirm(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_confirm_view;
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.dialog_confirm_title);
        mTvMessage = (TextView) findViewById(R.id.dialog_confirm_message);
        mTvNoBtn = (TextView) findViewById(R.id.dialog_confirm_no);
        mTvYesBtn = (TextView) findViewById(R.id.dialog_confirm_yes);

        mTvNoBtn.setOnClickListener(this);
        mTvYesBtn.setOnClickListener(this);
        initContent();
    }

    private void initContent(){
        if(mTvTitle == null) return;

        if(TextUtils.isEmpty(titleStr)){
            mTvTitle.setVisibility(View.GONE);
        }else{
            mTvTitle.setText(titleStr);
        }

        if(TextUtils.isEmpty(messageStr)){
            mTvMessage.setVisibility(View.GONE);
        }else{
            mTvMessage.setText(messageStr);
        }

        if(TextUtils.isEmpty(sureStr)){
            mTvYesBtn.setVisibility(View.GONE);
        }else{
            mTvYesBtn.setText(sureStr);
        }

        if(TextUtils.isEmpty(cancleStr)){
            mTvNoBtn.setVisibility(View.GONE);
        }else{
            mTvNoBtn.setText(cancleStr);
        }
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_confirm_no:
                if (null != listener) {
                    listener.onClick(v);
                }
                dismiss();
                break;
            case R.id.dialog_confirm_yes:
                if (null != listener) {
                    listener.onClick(v);
                }
                dismiss();
                break;
        }
    }

    public DialogConfirm setTitles(String s) {
        titleStr = s;
        initContent();
        return this;
    }

    public DialogConfirm setMessage(String s) {
        messageStr = s;
        initContent();
        return this;
    }

    public DialogConfirm setNoBtnText(String s) {
        cancleStr = s;
        initContent();
        return this;
    }

    public DialogConfirm setYesBtnText(String s) {
        sureStr = s;
        initContent();
        return this;
    }

    public DialogConfirm setTitles(int strID) {
        return setTitles(mContext.getString(strID));
    }

    public DialogConfirm setMessage(int strID) {
        return setMessage(mContext.getString(strID));
    }

    public DialogConfirm setNoBtnText(int strID) {
        return setNoBtnText(mContext.getString(strID));
    }
    public DialogConfirm setYesBtnText(int strID) {
        return setYesBtnText(mContext.getString(strID));
    }

    public DialogConfirm setListener(View.OnClickListener listener) {
        this.listener = listener;
        return this;
    }
}
