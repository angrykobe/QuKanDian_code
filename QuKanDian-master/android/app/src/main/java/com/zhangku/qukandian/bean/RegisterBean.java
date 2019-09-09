package com.zhangku.qukandian.bean;

import android.text.TextUtils;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

/**
 * Created by yuzuoning on 2017/3/31.
 */

public class RegisterBean {

    public RegisterBean(String code, String userName, String tel, String password, String captcha) {
        this.code = code;
        this.userName = userName;
        this.tel = tel;
        this.password = password;
        this.captcha = captcha;
        this.regSource = !TextUtils.isEmpty(UserSharedPreferences.getInstance().getString(Constants.INVITATION_SOURCE, "")) ?
                UserSharedPreferences.getInstance().getString(Constants.INVITATION_SOURCE, "") : QuKanDianApplication.mUmen;
    }

    /**
     * code : sample string 1
     * userName : sample string 2
     * tel : sample string 3
     * password : sample string 4
     * captcha : sample string 5
     */


    private String code;
    private String userName;
    private String tel;
    private String password;
    private String captcha;
    private String regSource;

    public String getRegSource() {
        return regSource;
    }

    public void setRegSource(String regSource) {
        this.regSource = regSource;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
