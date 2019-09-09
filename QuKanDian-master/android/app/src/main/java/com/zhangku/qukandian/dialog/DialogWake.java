package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;

/**
 * Created by yuzuoning on 2017/7/26.
 */

public class DialogWake extends BaseDialog implements View.OnClickListener {
    private TextView mTvTitle;
    private TextView mTvTitle1;
    private EditText mEditText;
    private ImageView mIvCancelBtn;
    private LinearLayout mLayoutShareChat;
    private LinearLayout mLayoutShareQQ;
    private LinearLayout mLayoutShareSMS;
    private OnClickShareListener mOnClickShareListener;
    public static final int TYPE_CHAT = 0;
    public static final int TYPE_QQ = 1;
    public static final int TYPE_SMS = 2;

    public DialogWake(Context context,OnClickShareListener onClickShareListener) {
        super(context, R.style.zhangku_dialog);
        mOnClickShareListener = onClickShareListener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_wake_layout;
    }

    @Override
    protected void initView() {
        mTvTitle = (TextView) findViewById(R.id.dialog_wake_title);
        mTvTitle1 = (TextView) findViewById(R.id.dialog_wake_title_1);
        mEditText = (EditText) findViewById(R.id.dialog_wake_content);
        mIvCancelBtn = (ImageView) findViewById(R.id.dialog_wake_share_cancel);
        mLayoutShareChat = (LinearLayout) findViewById(R.id.dialog_wake_share_chat);
        mLayoutShareQQ = (LinearLayout) findViewById(R.id.dialog_wake_share_qq);
        mLayoutShareSMS = (LinearLayout) findViewById(R.id.dialog_wake_share_sms);

        mLayoutShareChat.setOnClickListener(this);
        mIvCancelBtn.setOnClickListener(this);
        mLayoutShareQQ.setOnClickListener(this);
        mLayoutShareSMS.setOnClickListener(this);
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    public void setTitle(String title,String title1) {
        mTvTitle.setText(title);
        mTvTitle1.setText(title1);
    }
    public void setContent(String s) {
        mEditText.setText("    "+s);
        mEditText.setSelection(s.length());
    }
    @Override
    public void onClick(View v) {
        String content = mEditText.getText().toString();
        switch (v.getId()) {
            case R.id.dialog_wake_share_chat:
                if(null != mOnClickShareListener){
                    mOnClickShareListener.onClickShareListener(content,TYPE_CHAT);
                }
                break;
            case R.id.dialog_wake_share_qq:
                if(null != mOnClickShareListener){
                    mOnClickShareListener.onClickShareListener(content,TYPE_QQ);
                }
                break;
            case R.id.dialog_wake_share_sms:
                if(null != mOnClickShareListener){
                    mOnClickShareListener.onClickShareListener(content,TYPE_SMS);
                }
                break;
            case R.id.dialog_wake_share_cancel:
                dismiss();
                break;
        }
    }

    public interface OnClickShareListener{
        void onClickShareListener(String content,int type);
    }
}
