package com.zhangku.qukandian.activitys.me;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewUserInfoProtocol;
import com.zhangku.qukandian.protocol.GetTokenProtocol;
import com.zhangku.qukandian.protocol.NewVerificationCodeProtocol;
import com.zhangku.qukandian.protocol.RegisterProtocol;
import com.zhangku.qukandian.protocol.VerificationShenmijinCodeProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.CountWidght;
import com.zhangku.qukandian.widght.VerificationImg;

/**
 * Created by yuzuoning on 2017/3/25.
 */

public class RegisterActivity extends BaseTitleActivity implements CountWidght.OnCountClickListener,
        UserManager.IOnLoginStatusLisnter {
    private VerificationImg mVerificationImg;
    private CountWidght mCountWidght;
    protected EditText mEtPhone;
    protected EditText mEtVerificationCode;
    protected EditText mEtVerificationCodeImg;
    protected EditText mEtPassword;
    protected Button mBtnRegister;
    private TextView mTvUserProtocolBtn;
    protected LinearLayout mLayoutRemind;
    private LinearLayout mVerificationLayout;
    private NewVerificationCodeProtocol mNewVerificationCodeProtocol;

    @Override
    protected void initActionBarData() {
        setTitle("注册");
    }

    @Override
    protected void initViews() {
        mVerificationLayout = (LinearLayout) findViewById(R.id.regist_username_verification_code_edit_img_layout);
        mVerificationImg = (VerificationImg) findViewById(R.id.dialog_verification_code_layout);
        mTvUserProtocolBtn = (TextView) findViewById(R.id.user_protocol);
        mLayoutRemind = (LinearLayout) findViewById(R.id.regist_password_remind);
        mEtPhone = (EditText) findViewById(R.id.regist_username_edit);
        mEtPassword = (EditText) findViewById(R.id.regist_password_edit);
        mEtVerificationCode = (EditText) findViewById(R.id.regist_username_verification_code_edit);
        mEtVerificationCodeImg = (EditText) findViewById(R.id.regist_username_verification_code_edit_img);
        mCountWidght = (CountWidght) findViewById(R.id.register_count);
        mBtnRegister = (Button) findViewById(R.id.register_register_btn);

        mCountWidght.setOnCountClickListener(this);
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
            }
        });
        mTvUserProtocolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startToWebviewAct(RegisterActivity.this, getString(R.string.register_activity_url), "用户协议");
            }
        });
        mVerificationImg.getCodeImg(mEtPhone.getText().toString());
        mEtPhone.addTextChangedListener(watcher);
        UserManager.getInst().addLoginListener(this);
    }

    protected void clickButton() {
        final String username = mEtPhone.getText().toString().trim();
        final String pwd = mEtPassword.getText().toString().trim();
        if ( CommonHelper.checkCellphone(username)) {
            mBtnRegister.setClickable(false);
            mDialogPrograss.show();
            new RegisterProtocol(RegisterActivity.this,
                    mEtPhone.getText().toString().trim(),
                    mEtPassword.getText().toString().trim(),
                    mEtVerificationCode.getText().toString().trim(), null)
                    .getRusult(new BaseModel.OnResultListener<Boolean>() {
                        @Override
                        public void onResultListener(Boolean response) {
                            mBtnRegister.setClickable(true);
                            if (response) {
                                new GetTokenProtocol(RegisterActivity.this, new BaseModel.OnResultListener<Boolean>() {
                                    @Override
                                    public void onResultListener(Boolean response) {
                                        mDialogPrograss.dismiss();
                                        new GetNewUserInfoProtocol(RegisterActivity.this, new BaseModel.OnResultListener<UserBean>() {
                                            @Override
                                            public void onResultListener(UserBean response) {
                                                UserManager.getInst().setUserInfor(response);
                                                UserManager.getInst().updateNickName(response.getNickName());
                                                UserManager.getInst().updateUserIcon(response.getAvatarUrl());
                                                UserManager.getInst().updateLoginStatus(true);
                                            }

                                            @Override
                                            public void onFailureListener(int code, String error) {

                                            }
                                        }).postRequest();
                                    }

                                    @Override
                                    public void onFailureListener(int code, String error) {

                                    }
                                }).getUserToken(username, pwd);

                            }
                        }

                        @Override
                        public void onFailureListener(int code, String error) {
                            mDialogPrograss.dismiss();
                            mBtnRegister.setClickable(true);
                        }
                    });
        } else {
            if (username.length() < 11 || !CommonHelper.checkCellphone(username)) {
                ToastUtils.showLongToast(this, "请输入11位手机号");
            } else if (pwd.length() < 6) {
                ToastUtils.showLongToast(this, "请输入6位以上密码");
            }
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_register_layout;
    }

    @Override
    public String setPagerName() {
        return "注册";
    }

    @Override
    public void OnCountClickListener() {
        if (!TextUtils.isEmpty(mEtPhone.getText().toString())) {
            if (mVerificationImg.isShown()) {
                String code = mEtVerificationCodeImg.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showShortToast(this, "请输入图形验证码");
                } else {
                    getVerificationCodeState();
                }
            } else {
                getVerificationCodeState();
            }
        } else {
            ToastUtils.showLongToast(this, "请输入手机号");
        }
    }

    private void getVerificationCodeState() {
        if (null == mNewVerificationCodeProtocol) {
            mNewVerificationCodeProtocol = new NewVerificationCodeProtocol(RegisterActivity.this, mEtPhone.getText().toString().trim(),mEtVerificationCodeImg.getText().toString());
            mNewVerificationCodeProtocol.getResult(new BaseModel.OnResultListener<Boolean>() {
                @Override
                public void onResultListener(Boolean response) {
                    if (!response) {
                        mVerificationImg.getCodeImg(mEtPhone.getText().toString());
                        mVerificationLayout.setVisibility(View.VISIBLE);
                    } else {
                        mCountWidght.startCount();
                    }
                    mNewVerificationCodeProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mNewVerificationCodeProtocol = null;
                }
            });
        }
    }

    @Override
    public void onCountCancelListener() {
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        UserManager.getInst().removeLoginListener(this);
        mCountWidght = null;
        mEtPhone = null;
        mEtVerificationCode = null;
        mEtPassword = null;
        mBtnRegister = null;
        mLayoutRemind = null;
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mEtPhone.getText().toString().trim() != null && mEtPhone.getText().toString().trim().length() == 11) {
                if (CommonHelper.checkCellphone(mEtPhone.getText().toString().trim())) {
//                    ajaxCheckPhone(mEdtPhone.getText().toString().trim());
                } else {
                    ToastUtils.showLongToast(RegisterActivity.this, "请输入11位手机号");
                }
            }
        }
    };

    @Override
    public void onLoginStatusListener(boolean state) {
        if (state) {
            finish();
        }
    }
}
