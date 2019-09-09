package com.zhangku.qukandian.activitys.me;

import android.app.Activity;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fm.openinstall.OpenInstall;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewUserInfoProtocol;
import com.zhangku.qukandian.protocol.GetTokenProtocol;
import com.zhangku.qukandian.protocol.LoginByWechatProtocol;
import com.zhangku.qukandian.protocol.LoginProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.Map;

/**
 * Created by yuzuoning on 2017/3/24.
 * 账号密码登录
 */

public class LoginActivity extends BaseTitleActivity implements View.OnClickListener {
    private EditText mEtUserName;
    private EditText mEtPassword;
    private Button mBtbLogin;
    private TextView mBtForget;
    private ImageView mIvCancelBtn;
    private CheckBox mChbProtocol;
    private TextView mTvProtocol;

    private LoginProtocol mLoginProtocol;

    private View loginRegisterTV;
    private View loginWxBtn;
    private View loginPhoneBtn;
    private View loginPswBtn;
    private View loginNoneBtn;

    private boolean isReadProtocol = true;//是否已经阅读协议勾选

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        mIvCancelBtn = (ImageView) findViewById(R.id.login_cancel_btn);
        mEtUserName = (EditText) findViewById(R.id.login_username_edit);
        mEtPassword = (EditText) findViewById(R.id.login_psssword_edit);
        mBtbLogin = (Button) findViewById(R.id.login_login_btn);
        mBtForget = (TextView) findViewById(R.id.login_forget_password_btn);
        mTvProtocol = (TextView) findViewById(R.id.tv_protocol);
        mChbProtocol = (CheckBox) findViewById(R.id.chb_protocol);
        loginRegisterTV = findViewById(R.id.loginRegisterTV);
        String phone = UserSharedPreferences.getInstance().getString(Constants.PHONE, "");
        if (!TextUtils.isEmpty(phone)) {
            mEtUserName.setText(phone);
            mEtUserName.setSelection(phone.length());
        }

        mBtbLogin.setOnClickListener(this);
        mIvCancelBtn.setOnClickListener(this);
        mBtForget.setOnClickListener(this);
        loginRegisterTV.setOnClickListener(this);
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

        loginWxBtn = findViewById(R.id.loginWxBtn);
        loginPswBtn = findViewById(R.id.loginPswBtn);
        loginPhoneBtn = findViewById(R.id.loginPhoneBtn);
        loginNoneBtn = findViewById(R.id.loginNoneBtn);

        loginWxBtn.setOnClickListener(this);
        loginPhoneBtn.setOnClickListener(this);
        loginPswBtn.setOnClickListener(this);
        loginNoneBtn.setOnClickListener(this);

        String loginOrder = UserManager.getInst().getQukandianBean().getLoginOrder();
        char[] c = loginOrder.toCharArray();

        if (c.length > 4) {
            loginWxBtn.setVisibility('0' == (c[1]) ? View.GONE : View.VISIBLE);
            loginPswBtn.setVisibility(View.GONE);
            loginPhoneBtn.setVisibility('0' == (c[3]) ? View.GONE : View.VISIBLE);
            loginNoneBtn.setVisibility('0' == (c[4]) ? View.GONE : View.VISIBLE);
        }
        controlKeyboardLayout(findViewById(R.id.rootView), mBtbLogin);
    }

    private void loging(final String username, final String pwd) {
        if (CommonHelper.checkCellphone(username)) {
            mDialogPrograss.show();
            mBtbLogin.setClickable(false);
            if (null == mLoginProtocol) {
                mLoginProtocol = new LoginProtocol(LoginActivity.this, username, pwd, new BaseModel.OnResultListener<Boolean>() {
                    @Override
                    public void onResultListener(Boolean response) {
                        mBtbLogin.setClickable(true);
                        mDialogPrograss.dismiss();
                        if (response) {
                            UserSharedPreferences.getInstance()
                                    .putString(Constants.USERNAME, username)
                                    .putString(Constants.PWD, pwd);

                            //用户注册成功后调用
                            OpenInstall.reportRegister();

                            new GetTokenProtocol(LoginActivity.this, new BaseModel.OnResultListener<Boolean>() {
                                @Override
                                public void onResultListener(Boolean response) {
                                    new GetNewUserInfoProtocol(LoginActivity.this, new BaseModel.OnResultListener<UserBean>() {
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
                            }).getUserToken(username, pwd);
                        }
                        mLoginProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        mBtbLogin.setClickable(true);
                        mDialogPrograss.dismiss();
                        mLoginProtocol = null;
                    }
                });
                mLoginProtocol.getRusult();
            }
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
        return R.layout.activity_login_layout;
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
                loging(mEtUserName.getText().toString().trim()
                        , mEtPassword.getText().toString().trim());
                break;
            case R.id.login_forget_password_btn:
                ActivityUtils.startToForgetPasswordActivity(LoginActivity.this);
                finish();
                break;
            case R.id.login_cancel_btn:
                MobclickAgent.onEvent(this, "294-dengluguanbi");
                finish();
                break;
            case R.id.loginWxBtn:
                MobclickAgent.onEvent(this, "294-weixindenglu");
                bindWeChat();
                break;
            case R.id.loginRegisterTV:
            case R.id.loginPhoneBtn:
                ActivityUtils.startToLoginDynamicActivity(this);
                finish();
                break;
            case R.id.loginPswBtn:
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
        mEtUserName = null;
        mEtPassword = null;
        mBtbLogin = null;
        mBtForget = null;

        mLoginProtocol = null;
    }

    private void bindWeChat() {
        UMShareAPI.get(QuKanDianApplication.getmContext()).deleteOauth(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                UMShareAPI.get(QuKanDianApplication.getmContext()).getPlatformInfo(LoginActivity.this,
                        SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                ToastUtils.showLongToast(LoginActivity.this, "正在打开授权页面");
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA share_media, int i, final Map<String, String> map) {
                                mDialogPrograss.show();
                                UserManager.getInst().setWeChatBean(new WeChatBean(map.get("name"), map.get("uid"), map.get("openid")
                                        , map.get("gender").equals("男") ? 1 : 2, map.get("iconurl")
                                        , map.get("prvinice"), map.get("city"), map.get("country")
                                        , UserManager.getInst().getUserBeam().getId()));
                                UserManager.getInst().getWeChatBean().setOpenId(map.get("openid"));
                                new LoginByWechatProtocol(LoginActivity.this, map.get("openid"), new BaseModel.OnResultListener<Object>() {
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
                                    ToastUtils.showLongToast(LoginActivity.this, "没有安装应用");
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

    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    private void controlKeyboardLayout(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
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
                    if (srollHeight > 0)
                        root.scrollTo(0, srollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }
}
