package com.zhangku.qukandian.activitys.me;

import android.text.TextUtils;
import android.view.View;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.ForgetPasswordProtocol;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

/**
 * Created by yuzuoning on 2017/3/25.
 */

public class ForgetPasswordActivity extends RegisterActivity {
    private ForgetPasswordProtocol mForgetPasswordProtocol;

    @Override
    protected void initActionBarData() {
        setTitle("忘记密码");
    }

    @Override
    protected void initViews() {
        super.initViews();
        mLayoutRemind.setVisibility(View.GONE);
        String phoe = UserSharedPreferences.getInstance().getString(Constants.PHONE, "");
        if (!TextUtils.isEmpty(phoe)) {
            mEtPhone.setText(phoe);
            mEtPhone.setSelection(phoe.length());
        }
        mBtnRegister.setBackgroundResource(R.mipmap.yellow_fil_bg);
        mBtnRegister.setText("确认");
    }

    @Override
    protected void clickButton() {
        String username = mEtPhone.getText().toString().trim();
        String pwd = mEtPassword.getText().toString().trim();
        if ( CommonHelper.checkCellphone(username)) {
            if (null == mForgetPasswordProtocol) {
                mForgetPasswordProtocol = new ForgetPasswordProtocol(ForgetPasswordActivity.this,
                        mEtPhone.getText().toString().trim(),
                        mEtPassword.getText().toString().trim(),
                        mEtVerificationCode.getText().toString().trim(), new BaseModel.OnResultListener<Boolean>() {
                    @Override
                    public void onResultListener(Boolean response) {
                        finish();
                        mForgetPasswordProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code,String error) {
                        mForgetPasswordProtocol = null;
                    }
                });
                mForgetPasswordProtocol.getRusult();
            }
        } else {
            if (username.length() < 11 || !CommonHelper.checkCellphone(username)) {
                ToastUtils.showLongToast(this, "请输入11位手机号");
            } else if (pwd.length() < 6) {
                ToastUtils.showLongToast(this, "密码长度为6-12");
            }
        }
    }

    @Override
    public String setPagerName() {
        return "忘记密码";
    }
}
