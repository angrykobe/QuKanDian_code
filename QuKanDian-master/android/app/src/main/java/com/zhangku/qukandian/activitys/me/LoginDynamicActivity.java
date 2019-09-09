package com.zhangku.qukandian.activitys.me;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.CodeBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewLoginCodeProtocol;
import com.zhangku.qukandian.protocol.GetNewUserInfoProtocol;
import com.zhangku.qukandian.protocol.GetLoginImgCodePro;
import com.zhangku.qukandian.protocol.GetTokenProtocol;
import com.zhangku.qukandian.protocol.LoginByWechatProtocol;
import com.zhangku.qukandian.protocol.LoginDynamicProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CommonUtil;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.widght.CountWidght;

import java.util.Map;

/**
 * Created by yuzuoning on 2017/3/24.
 * <p>
 * 手机验证码登录
 */

public class LoginDynamicActivity extends BaseTitleActivity implements View.OnClickListener, CountWidght.OnCountClickListener {
    private EditText mEtPhone;
    private Button mBtbLogin;
    private ImageView mIvCancelBtn;
    private CountWidght mCountWidght;
    private EditText mEtVerificationCode;
    private CheckBox mChbProtocol;
    private TextView mTvProtocol;

    private LoginDynamicProtocol mLoginDynamicProtocol;
//    private DynamicVerificationCodeProtocol mDynamicVerificationCodeProtocol;

    private View loginWxBtn;
    private View loginPhoneBtn;
    private View loginPswBtn;
    private View loginNoneBtn;
    private View codeView;
    private ImageView codeIV;
    private EditText codeEdit;
    private GetNewLoginCodeProtocol mGetNewLoginCodeProtocol;
    private GetLoginImgCodePro mGetLoginImgCodePro;

    private boolean isReadProtocol = true;//是否已经阅读协议勾选
    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        mEtVerificationCode = (EditText) findViewById(R.id.regist_username_verification_code_edit);
        mIvCancelBtn = (ImageView) findViewById(R.id.login_cancel_btn);
        mEtPhone = (EditText) findViewById(R.id.login_username_edit);
        mBtbLogin = (Button) findViewById(R.id.login_login_btn);
        codeView = findViewById(R.id.codeView);
        codeIV = findViewById(R.id.codeIV);
        codeEdit = findViewById(R.id.codeEdit);
        mTvProtocol = (TextView) findViewById(R.id.tv_protocol);
        mChbProtocol = (CheckBox) findViewById(R.id.chb_protocol);
        String phone = UserSharedPreferences.getInstance().getString(Constants.PHONE, "");
        if (!TextUtils.isEmpty(phone)) {
            mEtPhone.setText(phone);
            mEtPhone.setSelection(phone.length());
        }

        mBtbLogin.setOnClickListener(this);
        mIvCancelBtn.setOnClickListener(this);

        mCountWidght = (CountWidght) findViewById(R.id.register_count);
        mCountWidght.setOnCountClickListener(this);

        mTvProtocol.setOnClickListener(this);
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

        mEtPhone.addTextChangedListener(watcher);

        loginWxBtn = findViewById(R.id.loginWxBtn);
        loginPswBtn = findViewById(R.id.loginPswBtn);
        loginPhoneBtn = findViewById(R.id.loginPhoneBtn);
        loginNoneBtn = findViewById(R.id.loginNoneBtn);

        loginWxBtn.setOnClickListener(this);
        loginPhoneBtn.setOnClickListener(this);
        loginPswBtn.setOnClickListener(this);
        loginNoneBtn.setOnClickListener(this);
        codeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGetLoginImgCodePro ==null){
                    mGetLoginImgCodePro = new GetLoginImgCodePro(LoginDynamicActivity.this, mEtPhone.getText().toString().trim(), new BaseModel.OnResultListener<CodeBean>() {
                        @Override
                        public void onResultListener(CodeBean response) {
                            try {
                                byte[] encodeByte = Base64.decode(response.getFileContents(), Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                                codeIV.setImageBitmap(bitmap);
                            } catch (Exception e) {
                                e.getMessage();
                            } finally {
                                mGetLoginImgCodePro = null;
                            }
                        }

                        @Override
                        public void onFailureListener(int code, String error) {
                            mGetLoginImgCodePro = null;
                        }
                    });
                    mGetLoginImgCodePro.postRequest();
                }
            }
        });

        String loginOrder = UserManager.getInst().getQukandianBean().getLoginOrder();
        char[] c = loginOrder.toCharArray();
        if (c.length > 4) {
            loginWxBtn.setVisibility('0' == c[1] ? View.GONE : View.VISIBLE);
            loginPswBtn.setVisibility('0' == c[2] ? View.GONE : View.VISIBLE);
            loginPhoneBtn.setVisibility(View.GONE);
            loginNoneBtn.setVisibility('0' == c[4] ? View.GONE : View.VISIBLE);
        }
        controlKeyboardLayout(findViewById(R.id.rootView), mBtbLogin);
    }

    private void loging(final String tel, final String code) {
        if (isNicePhone(tel) && !TextUtils.isEmpty(code)) {
            mDialogPrograss.show();
            mBtbLogin.setClickable(false);
            if (null == mLoginDynamicProtocol) {
                mLoginDynamicProtocol = new LoginDynamicProtocol(LoginDynamicActivity.this, tel, code, new BaseModel.OnResultListener<Integer>() {
                    @Override
                    public void onResultListener(Integer response) {
                        mBtbLogin.setClickable(true);
                        mDialogPrograss.dismiss();
                        if (response > 0) {
                            String username = response + "";
                            String psd = CommonUtil.encrypt(tel + "_" + username);
//                            String username = "275745";
//                            String psd = "15259248116"+username;
                            UserSharedPreferences.getInstance()
                                    .putString(Constants.USERNAME, username)
                                    .putString(Constants.PWD, psd);
                            new GetTokenProtocol(LoginDynamicActivity.this, new BaseModel.OnResultListener<Boolean>() {
                                @Override
                                public void onResultListener(Boolean response) {
                                    new GetNewUserInfoProtocol(LoginDynamicActivity.this, new BaseModel.OnResultListener<UserBean>() {
                                        @Override
                                        public void onResultListener(UserBean response) {
                                            UserManager.getInst().updateLoginStatus(true);
                                            setResult(Activity.RESULT_OK);
                                            finish();
                                        }

                                        @Override
                                        public void onFailureListener(int code, String error) {
                                        }
                                    }).postRequest();
                                }

                                @Override
                                public void onFailureListener(int code, String error) {
                                }
                            }).getUserTokenDynamic(username, psd);
                        }
                        mLoginDynamicProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        mBtbLogin.setClickable(true);
                        mDialogPrograss.dismiss();
                        mLoginDynamicProtocol = null;
                    }
                });
                mLoginDynamicProtocol.getRusult();
            }
        } else {
            if (tel.length() < 11 || !CommonHelper.checkCellphone(tel)) {
                ToastUtils.showLongToast(this, "请输入11位手机号");
            } else if (TextUtils.isEmpty(code)) {
                ToastUtils.showLongToast(this, "请输入短信验证码");
            }
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login_dynamic_layout;
    }

    @Override
    public String setPagerName() {
        return "登录";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_btn:
                if(!isReadProtocol){
                    ToastUtils.showLongToast(this, "请先阅读并勾选同意协议");
                    return;
                }
                MobclickAgent.onEvent(this, "293_loginbtn");
                loging(mEtPhone.getText().toString().trim()
                        , mEtVerificationCode.getText().toString().trim());
                break;
            case R.id.login_cancel_btn:
                MobclickAgent.onEvent(this, "294-dengluguanbi");
                finish();
                break;
            case R.id.loginWxBtn:
                MobclickAgent.onEvent(this, "293_loginbtn");
                bindWeChat();
                break;
            case R.id.loginPswBtn:
                MobclickAgent.onEvent(this, "294-mimadenglu");
                ActivityUtils.startToLogingActivity(this);
                finish();
                break;
            case R.id.loginNoneBtn:
                MobclickAgent.onEvent(this, "293_loginbtn_finish");
                finish();
                break;
            case R.id.tv_protocol://隐私政策
                String protocolUrl = UserManager.getInst().getQukandianBean().getProtocolUrl();
                ActivityUtils.startToAboutWebviewAct(this,protocolUrl,"隐私政策");
                break;
        }
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mEtPhone = null;
        mEtVerificationCode = null;
        mBtbLogin = null;

        mLoginDynamicProtocol = null;
    }

    private void bindWeChat() {
        UMShareAPI.get(QuKanDianApplication.getmContext()).deleteOauth(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                UMShareAPI.get(QuKanDianApplication.getmContext()).getPlatformInfo(LoginDynamicActivity.this,
                        SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                ToastUtils.showLongToast(LoginDynamicActivity.this, "正在打开授权页面");
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA share_media, int i, final Map<String, String> map) {
                                UserManager.getInst().setWeChatBean(new WeChatBean(map.get("name"), map.get("uid"), map.get("openid")
                                        , map.get("gender").equals("男") ? 1 : 2, map.get("iconurl")
                                        , map.get("prvinice"), map.get("city"), map.get("country")
                                        , UserManager.getInst().getUserBeam().getId()));
                                if (!isFinishing())
                                    mDialogPrograss.show();
                                new LoginByWechatProtocol(LoginDynamicActivity.this, map.get("openid"), new BaseModel.OnResultListener<Object>() {
                                    @Override
                                    public void onResultListener(Object response) {
                                        mDialogPrograss.dismiss();
                                        setResult(Activity.RESULT_OK);
                                        finish();
                                    }

                                    @Override
                                    public void onFailureListener(int code, String error) {
                                        mDialogPrograss.dismiss();

                                    }
                                }).getResult();
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                                if (throwable.toString().contains("没有安装应用")) {
                                    ToastUtils.showLongToast(LoginDynamicActivity.this, "没有安装应用");
                                }
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media, int i) {
                                Log.e("TAG", "onCancel:" + i);
                            }
                        });
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });

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
            if (isNicePhone(mEtPhone.getText().toString().trim())) {
                if (CommonHelper.checkCellphone(mEtPhone.getText().toString().trim())) {
//                    ajaxCheckPhone(mEdtPhone.getText().toString().trim());
                } else {
                    ToastUtils.showLongToast(LoginDynamicActivity.this, "请输入11位手机号");
                }
            }
        }
    };

    @Override
    public void OnCountClickListener() {
        MobclickAgent.onEvent(this, "294-huoquyanzhengma");
        if (isNicePhone(mEtPhone.getText().toString())) {
            getVerificationCodeState();
        } else {
            ToastUtils.showLongToast(this, "请输入正确的手机号");
        }
    }

    @Override
    public void onCountCancelListener() {
    }

    public boolean isNicePhone(String tel) {
        return CommonHelper.checkCellphone(tel);
    }

    private void getVerificationCodeState() {
        if(codeView.getVisibility() == View.VISIBLE){
            if(TextUtils.isEmpty(codeEdit.getText().toString().trim())){
                ToastUtils.showLongToast(this,"请先输入图像验证码");
            }else{
                requestImageCode();
            }
        }else{
            requestImageCode();
        }
    }

    private void requestImageCode(){
        if(mGetNewLoginCodeProtocol == null){
            mGetNewLoginCodeProtocol = new GetNewLoginCodeProtocol(this,
                    mEtPhone.getText().toString().trim(),
                    codeEdit.getText().toString().trim(), new BaseModel.OnResultListener<Object>() {
                @Override
                public void onResultListener(Object response) {
                    mCountWidght.startCount();
                    mGetNewLoginCodeProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    if (code == 1004 || code == 1002 || code == 1001 ) {
                        codeView.setVisibility(View.VISIBLE);
                        new GetLoginImgCodePro(LoginDynamicActivity.this, mEtPhone.getText().toString().trim(), new BaseModel.OnResultListener<CodeBean>() {
                            @Override
                            public void onResultListener(CodeBean response) {
                                try {
                                    byte[] encodeByte = Base64.decode(response.getFileContents(), Base64.DEFAULT);
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                                    codeIV.setImageBitmap(bitmap);
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            }

                            @Override
                            public void onFailureListener(int code, String error) {

                            }
                        }).postRequest();
                    } else if (code == 1000 ) {
                        mCountWidght.startCount();
                    } if( code == 1003 ){
                        new GetLoginImgCodePro(LoginDynamicActivity.this, mEtPhone.getText().toString().trim(), new BaseModel.OnResultListener<CodeBean>() {
                            @Override
                            public void onResultListener(CodeBean response) {
                                try {
                                    byte[] encodeByte = Base64.decode(response.getFileContents(), Base64.DEFAULT);
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                                    codeIV.setImageBitmap(bitmap);
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            }

                            @Override
                            public void onFailureListener(int code, String error) {

                            }
                        }).postRequest();
                        ToastUtils.showLongToast(LoginDynamicActivity.this,error);
                    } else{
                        ToastUtils.showLongToast(LoginDynamicActivity.this,error);
                    }
                    mGetNewLoginCodeProtocol = null;
                }
            });
            mGetNewLoginCodeProtocol.postRequest();
        }
    }

    /**
     * @param root 最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                if (rootInvisibleHeight > 300) {
                    int[] location = new int[2];
                    //获取scrollToView在窗体的坐标
                    scrollToView.getLocationInWindow(location);
                    //计算root滚动高度，使scrollToView在可见区域的底部
                    int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                    if(srollHeight>0)
                    root.scrollTo(0, srollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }
}
