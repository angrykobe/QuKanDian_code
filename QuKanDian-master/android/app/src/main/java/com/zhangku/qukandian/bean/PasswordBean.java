package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/4/5.
 */

public class PasswordBean {


    /**
     * tel : sample string 1
     * code : sample string 2
     * password : sample string 3
     */

    private String tel;
    private String code;
    private String password;

    public PasswordBean(String verificationCode, String userName, String pwd) {
        tel = userName;
        code = verificationCode;
        password = pwd;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
