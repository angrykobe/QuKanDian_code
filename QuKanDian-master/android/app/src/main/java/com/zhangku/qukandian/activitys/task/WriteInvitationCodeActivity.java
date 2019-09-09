package com.zhangku.qukandian.activitys.task;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.dialog.DialogShowGold;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.MenuChangeOberver;
import com.zhangku.qukandian.protocol.PutMentorIdProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.GrayBgActionBar;

/**
 * Created by yuzuoning on 2017/4/7.
 * 输入邀请码界面
 */

public class WriteInvitationCodeActivity extends BaseTitleActivity implements View.OnClickListener, PutMentorIdProtocol.OnClickPutListener, DialogShowGold.OnClickConfirmListener {
    private GrayBgActionBar mGrayBgActionBar;
    private TextView mTvSubmitBtn;
    private TextView mTvInviteBtn;
    private EditText mEtCode;
    private PutMentorIdProtocol mPutMentorIdProtocol;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        if (!UserManager.getInst().hadLogin()) {
            ActivityUtils.startToBeforeLogingActivity(this);
            finish();
            return;
        }
        mGrayBgActionBar = (GrayBgActionBar) findViewById(R.id.gray_actionbar_layout);
        mTvSubmitBtn = (TextView) findViewById(R.id.activity_write_invite_code_submit);
        mTvInviteBtn = (TextView) findViewById(R.id.activity_write_invite_code_invite);
        mEtCode = (EditText) findViewById(R.id.activity_write_invite_code_edit);

        mGrayBgActionBar.setTvTitle("输入邀请码");

        mGrayBgActionBar.setOnClickListener(this);
        mTvSubmitBtn.setOnClickListener(this);
        mTvInviteBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_write_invite_code_layout;
    }

    @Override
    public String setPagerName() {
        return "输入邀请码";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_write_invite_code_submit:
                mDialogPrograss.show();
                if(TextUtils.isEmpty(mEtCode.getText().toString())){
                    mDialogPrograss.dismiss();
                    ToastUtils.showLongToast(WriteInvitationCodeActivity.this,"请输入邀请码");
                } else if(Integer.valueOf(mEtCode.getText().toString()) <10000){
                    mDialogPrograss.dismiss();
                    ToastUtils.showLongToast(WriteInvitationCodeActivity.this,"请输入正确的邀请码");
                } else {
                    // 拜师后，用户界面需要刷新
                    mTvSubmitBtn.setClickable(false);
                    if(null == mPutMentorIdProtocol){
                        mPutMentorIdProtocol = new PutMentorIdProtocol(WriteInvitationCodeActivity.this, this, new BaseModel.OnResultListener<Boolean>() {
                            @Override
                            public void onResultListener(Boolean response) {
                                MenuChangeOberver.getInstance().updateStateChange();
                                mTvSubmitBtn.setClickable(true);
                                mDialogPrograss.dismiss();
                                mPutMentorIdProtocol = null;
                            }

                            @Override
                            public void onFailureListener(int code,String error) {
                                mPutMentorIdProtocol = null;
                            }
                        });
                        mPutMentorIdProtocol.putMentorId(Long.valueOf(mEtCode.getText().toString()));
                    }
                }
                break;
            case R.id.activity_write_invite_code_invite:
                ActivityUtils.startToShoutuActivity(this);
                break;
        }
    }

    @Override
    public void onClickPutListener(boolean success) {
        mTvSubmitBtn.setClickable(!success);
    }

    @Override
    public void onCancelListener() {

    }

    @Override
    public void onClickConfirmListener() {
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mGrayBgActionBar = null;
        mTvSubmitBtn = null;
        mTvInviteBtn = null;
        mEtCode = null;
    }
}
