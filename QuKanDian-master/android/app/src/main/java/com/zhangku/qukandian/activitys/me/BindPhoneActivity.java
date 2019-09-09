package com.zhangku.qukandian.activitys.me;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogBIndPhone;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.HongbaoShowObserver;
import com.zhangku.qukandian.observer.MenuChangeOberver;
import com.zhangku.qukandian.protocol.GetNewUserInfoProtocol;
import com.zhangku.qukandian.protocol.GetTokenProtocol;
import com.zhangku.qukandian.protocol.RegisterProtocol;
import com.zhangku.qukandian.protocol.UploadWeChatInfoProtocol;
import com.zhangku.qukandian.protocol.VerificationCodeProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CommonUtil;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.CountWidght;

/**
 * Created by yuzuoning on 2017/9/15.
 */

public class BindPhoneActivity extends BaseTitleActivity implements CountWidght.OnCountClickListener, DialogBIndPhone.OnFinishListener {
    private EditText mEtUsername;
    private EditText mEtCode;
    private EditText mEtPassword;
    private CountWidght mCountWidght;
    private TextView mTvLoginBtn;
    private RegisterProtocol mRegisterProtocol;
    private UploadWeChatInfoProtocol mUploadWeChatInfoProtocol;
    private DialogBIndPhone mDialogBIndPhone;
    private CheckBox mChbProtocol;
    private TextView mTvProtocol;

    private boolean isReadProtocol = true;//是否已经阅读协议勾选

    @Override
    protected void initActionBarData() {
        setTitle("手机号绑定");
    }

    @Override
    protected void initViews() {
        mDialogBIndPhone = new DialogBIndPhone(this, this);
        mEtUsername = (EditText) findViewById(R.id.bind_phone_edit);
        mEtCode = (EditText) findViewById(R.id.bind_phone_code_edit);
        mCountWidght = (CountWidght) findViewById(R.id.bind_phone_count);
        mTvLoginBtn = (TextView) findViewById(R.id.bind_phone_login_btn);
        mTvProtocol = (TextView) findViewById(R.id.tv_protocol);
        mChbProtocol = (CheckBox) findViewById(R.id.chb_protocol);

        mCountWidght.setOnCountClickListener(this);
        mTvLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickButton();
            }
        });

        mTvProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String protocolUrl = UserManager.getInst().getQukandianBean().getProtocolUrl();
                ActivityUtils.startToAboutWebviewAct(QuKanDianApplication.getmContext(),protocolUrl,"隐私政策");
            }
        });
        mChbProtocol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isReadProtocol = true;
                } else {
                    isReadProtocol = false;
                }
            }
        });
    }

    protected void clickButton() {
        if(!isReadProtocol){
            ToastUtils.showLongToast(this, "请先阅读并勾选同意协议");
            return;
        }
        final String tel = mEtUsername.getText().toString().trim();
        final String code = mEtCode.getText().toString().trim();
        if (isNicePhone(tel)) {
            mDialogPrograss.show();
            if (null == mRegisterProtocol) {
                mRegisterProtocol = new RegisterProtocol(BindPhoneActivity.this,
                        tel,
                        "",
                        code,
                        UserManager.getInst().getWeChatBean().getOpenId());
                mRegisterProtocol.getRusult2(new BaseModel.OnResultListener<Integer>() {
                    @Override
                    public void onResultListener(Integer response) {
                        mDialogPrograss.dismiss();
                        if (response > 0) {
                            String username = response + "";
                            String pwd = CommonUtil.encrypt(tel + "_" + username);
                            new GetTokenProtocol(BindPhoneActivity.this, new BaseModel.OnResultListener<Boolean>() {
                                @Override
                                public void onResultListener(Boolean response) {
                                    if (null == mUploadWeChatInfoProtocol) {
                                        mUploadWeChatInfoProtocol = new UploadWeChatInfoProtocol(BindPhoneActivity.this, new BaseModel.OnResultListener<Boolean>() {
                                            @Override
                                            public void onResultListener(Boolean response) {
                                                if (response) {
                                                    new GetNewUserInfoProtocol(BindPhoneActivity.this, new BaseModel.OnResultListener<UserBean>() {
                                                        @Override
                                                        public void onResultListener(UserBean response) {
                                                            mDialogPrograss.dismiss();
                                                            HongbaoShowObserver.getInstance().notifyUp(Constants.REGISTER_COIN);

                                                            UserManager.getInst().updateLoginStatus(true);
                                                            UserManager.getInst().updateNickName(response.getNickName());
                                                            UserManager.getInst().updateUserIcon(response.getAvatarUrl());
                                                            finish();
                                                        }

                                                        @Override
                                                        public void onFailureListener(int code, String error) {
                                                            mDialogPrograss.dismiss();
                                                        }
                                                    }).postRequest();
                                                } else {
                                                    MenuChangeOberver.getInstance().updateStateChange();
                                                    mDialogPrograss.dismiss();
                                                }
                                                mUploadWeChatInfoProtocol = null;
                                            }

                                            @Override
                                            public void onFailureListener(int code, String error) {
                                                mUploadWeChatInfoProtocol = null;
                                            }
                                        });
                                        mUploadWeChatInfoProtocol.uploadWeChatInfo(UserManager.getInst().getWeChatBean());
                                    }
                                }

                                @Override
                                public void onFailureListener(int code, String error) {
                                    mDialogPrograss.dismiss();
                                }
                            }).getUserTokenDynamic(username, pwd);
                        }
                        mRegisterProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        mDialogPrograss.dismiss();
                        mRegisterProtocol = null;
                    }
                });
            }
        } else {
            ToastUtils.showLongToast(this, "请输入11位手机号");
        }
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
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mDialogBIndPhone.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFinishListener() {
        finish();
    }

    public boolean isNicePhone(String tel) {
        return CommonHelper.checkCellphone(tel);
    }
}
