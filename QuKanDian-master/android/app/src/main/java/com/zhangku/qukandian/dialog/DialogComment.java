package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.PushCommentBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.SubmitCommentProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.ToastUtils;

/**
 * Created by yuzuoning on 2017/9/13.
 */

public class DialogComment extends BaseDialog implements View.OnClickListener {
    private TextView mTvCancelBtn;
    private TextView mTvPushBtn;
    private EditText mEdEdit;
    private int mPostId;
    private int mParentId;
    private SubmitCommentProtocol mSubmitCommentProtocol;
    private OnPushSuccessListener mOnPushSuccessListener;
    private final String mStr;

    // 暂时关闭评论
    public DialogComment(Context context) {
        super(context, R.style.zhangku_dialog);
        // 评论敏感词文件，在assets中
//        mStr = CommonHelper.readAssetsTxt(getContext(), "key");
        //由于暂时关闭评论，所以先放空
        mStr = "";
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_comment_layout;
    }

    @Override
    protected void initView() {
        mTvCancelBtn = findViewById(R.id.dialog_comment_cancle);
        mTvPushBtn = findViewById(R.id.dialog_comment_push);
        mEdEdit = findViewById(R.id.dialog_comment_edit);

        mTvCancelBtn.setOnClickListener(this);
        mTvPushBtn.setOnClickListener(this);
    }

    @Override
    protected void release() {

    }

    public void shows(int postId, int parentId, String hint, OnPushSuccessListener onPushSuccessListener) {
        mPostId = postId;
        mParentId = parentId;
        mOnPushSuccessListener = onPushSuccessListener;
        show();
        if (!TextUtils.isEmpty(hint)) {
            mEdEdit.setHint("回复" + hint + ":");
        }
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
            case R.id.dialog_comment_cancle:
                dismiss();
                break;
            case R.id.dialog_comment_push:
                if(UserManager.getInst().hadLogin()){
                    String content = mEdEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        ToastUtils.showShortToast(getContext(), "评论内容不能为空");
                    } else {
                        boolean isContains = false;
                        for (int i = 0; i < content.length(); i++) {
                            for (int j = i; j < content.length(); j++) {
                                String temp = content.substring(i, j + 1).toString();
                                if (mStr.contains("|" + temp + "|")) {
                                    isContains = true;
                                }
                            }
                        }

                        if (!isContains) {
                            if (null == mSubmitCommentProtocol) {
                                PushCommentBean bean = new PushCommentBean();
                                bean.setPostId(mPostId);
                                bean.setContent(content);
                                if (mParentId != -1) {
                                    bean.setParentId(mParentId);
                                }

                                mSubmitCommentProtocol = new SubmitCommentProtocol(getContext(), bean, new BaseModel.OnResultListener<Boolean>() {
                                    @Override
                                    public void onResultListener(Boolean response) {
                                        if (response) {
                                            ToastUtils.showShortToast(getContext(), "发表成功");
                                            dismiss();
                                            mEdEdit.setText("");
                                            if (null != mOnPushSuccessListener) {
                                                mOnPushSuccessListener.onPushSuccessListener();
                                            }
                                        }
                                        mSubmitCommentProtocol = null;
                                    }

                                    @Override
                                    public void onFailureListener(int code, String error) {
                                        mSubmitCommentProtocol = null;
                                    }
                                });
                                mSubmitCommentProtocol.postRequest();
                            }
                        } else {
                            ToastUtils.showShortToast(getContext(), "您评论的内容包含敏感词！");
                        }
                    }
                }else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }

                break;
        }
    }

    public interface OnPushSuccessListener {
        void onPushSuccessListener();
    }
}
