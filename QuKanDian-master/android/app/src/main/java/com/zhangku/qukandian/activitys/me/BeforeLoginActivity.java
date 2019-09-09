package com.zhangku.qukandian.activitys.me;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.LoginByWechatProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.Map;

/**
 * Created by yuzuoning on 2018/3/14.
 * 微信快速登录界面
 */

public class BeforeLoginActivity extends BaseTitleActivity implements View.OnClickListener {
    private View mTvWechatLogin;
    private ImageView mIvCancle;

    private View loginWxBtn;
    private View loginPhoneBtn;
    private View loginPswBtn;
    private View loginNoneBtn;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        mTvWechatLogin = findViewById(R.id.before_login_wechat);
        mIvCancle = findViewById(R.id.before_login_cancel);

        loginWxBtn = findViewById(R.id.loginWxBtn);
        loginPswBtn = findViewById(R.id.loginPswBtn);
        loginPhoneBtn = findViewById(R.id.loginPhoneBtn);
        loginNoneBtn = findViewById(R.id.loginNoneBtn);

        loginWxBtn.setOnClickListener(this);
        loginPhoneBtn.setOnClickListener(this);
        loginPswBtn.setOnClickListener(this);
        loginNoneBtn.setOnClickListener(this);

        mTvWechatLogin.setOnClickListener(this);
        mIvCancle.setOnClickListener(this);


        String loginOrder = UserManager.getInst().getQukandianBean().getLoginOrder();
        char[] c = loginOrder.toCharArray();

        if(c.length > 4){
            loginWxBtn.setVisibility(View.GONE);
            loginPswBtn.setVisibility('0'==(c[2])? View.GONE:View.VISIBLE );
            loginPhoneBtn.setVisibility('0'==(c[3])? View.GONE:View.VISIBLE );
            loginNoneBtn.setVisibility('0'==(c[4])? View.GONE:View.VISIBLE );
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_beforelogin_layout;
    }

    @Override
    public String setPagerName() {
        return "登录前";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.before_login_wechat:
                bindWeChat();
                break;
            case R.id.before_login_cancel:
                finish();
                break;
            case R.id.loginPhoneBtn:
                ActivityUtils.startToLoginDynamicActivity(this);
                finish();
                break;
            case R.id.loginPswBtn:
                ActivityUtils.startToLogingActivity(this);
                finish();
                break;
            case R.id.loginNoneBtn:
                finish();
                break;
        }
    }
    private void bindWeChat() {
        UMShareAPI.get(QuKanDianApplication.getmContext()).deleteOauth(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                UMShareAPI.get(QuKanDianApplication.getmContext()).getPlatformInfo(BeforeLoginActivity.this,
                        SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                ToastUtils.showLongToast(BeforeLoginActivity.this, "正在打开授权页面");
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA share_media, int i, final Map<String, String> map) {
//                                mDialogPrograss.show();
                                UserManager.getInst().setWeChatBean(new WeChatBean(map.get("name"), map.get("uid"), map.get("openid")
                                        , map.get("gender").equals("男") ? 1 : 2, map.get("iconurl")
                                        , map.get("prvinice"), map.get("city"), map.get("country")
                                        , UserManager.getInst().getUserBeam().getId()));
                                UserManager.getInst().getWeChatBean().setOpenId(map.get("openid"));
                                new LoginByWechatProtocol(BeforeLoginActivity.this, map.get("openid"), new BaseModel.OnResultListener<Object>() {
                                    @Override
                                    public void onResultListener(Object response) {
//                                        mDialogPrograss.dismiss();
                                        setResult(Activity.RESULT_OK);
                                        finish();
                                    }

                                    @Override
                                    public void onFailureListener(int code, String error) {
//                                        mDialogPrograss.dismiss();

                                    }
                                }).getResult();
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                                if (throwable.toString().contains("没有安装应用")) {
                                    ToastUtils.showLongToast(BeforeLoginActivity.this, "没有安装应用");
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
}
