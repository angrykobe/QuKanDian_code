package com.zhangku.qukandian.activitys.me;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogBIndPhone;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.RegisterProtocol;
import com.zhangku.qukandian.protocol.UploadWeChatInfoProtocol;
import com.zhangku.qukandian.protocol.VerificationCodeProtocol;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.CountWidght;

/**
 * Created by yuzuoning on 2017/9/15.
 */

public class BindPhoneActivityBak extends BaseTitleActivity implements CountWidght.OnCountClickListener, DialogBIndPhone.OnFinishListener {
    private EditText mEtUsername;
    private EditText mEtCode;
    private EditText mEtPassword;
    private CountWidght mCountWidght;
    private TextView mTvLoginBtn;
    private RegisterProtocol mRegisterProtocol;
    private UploadWeChatInfoProtocol mUploadWeChatInfoProtocol;
    private DialogBIndPhone mDialogBIndPhone;

    @Override
    protected void initActionBarData() {
        setTitle("手机号绑定");
    }

    @Override
    protected void initViews() {
        mDialogBIndPhone = new DialogBIndPhone(this,this);
        mEtUsername = (EditText) findViewById(R.id.bind_phone_edit);
        mEtCode = (EditText) findViewById(R.id.bind_phone_code_edit);
//        mEtPassword = (EditText) findViewById(R.id.bind_phone_password_edit);
        mCountWidght = (CountWidght) findViewById(R.id.bind_phone_count);
        mTvLoginBtn = (TextView) findViewById(R.id.bind_phone_login_btn);

        mCountWidght.setOnCountClickListener(this);
        mTvLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton();
            }
        });
    }

    protected void clickButton() {
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_bind_phone_layout;
    }

    @Override
    public String setPagerName() {
        return null;
    }

    @Override
    public void OnCountClickListener() {
        if (!TextUtils.isEmpty(mEtUsername.getText().toString().trim())) {
            if (mEtUsername.getText().toString().trim().substring(0, 1).equals("1") && mEtUsername.getText().toString().trim().length() == 11) {
                mCountWidght.startCount();
                new VerificationCodeProtocol(this, mEtUsername.getText().toString().trim()).getResult(new BaseModel.OnResultListener<Boolean>() {
                    @Override
                    public void onResultListener(Boolean response) {

                    }

                    @Override
                    public void onFailureListener(int code, String error) {

                    }
                });
            } else {
                ToastUtils.showShortToast(this, "请输入正确的手机号");
            }
        } else {
            ToastUtils.showShortToast(this, "请输入手机号");
        }
    }

    @Override
    public void onCountCancelListener() {

    }

    @Override
    protected void onBackAction() {
        mDialogBIndPhone.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            mDialogBIndPhone.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFinishListener() {
        finish();
    }
}
