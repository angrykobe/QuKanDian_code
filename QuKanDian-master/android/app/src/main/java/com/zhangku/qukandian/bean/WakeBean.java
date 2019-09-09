package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/7/26.
 */

public class WakeBean {
    /**
     * awakenEndTime : 2017-08-03T10:01:43.14
     * awakenType : 4
     * tel : 18859381711
     * nickName : 趣友10194
     * id : 10194
     */

    private String awakenEndTime;
    private int awakenType;
    private String tel;
    private String nickName;
    private int id;

    public String getAwakenEndTime() {
        return awakenEndTime;
    }

    public void setAwakenEndTime(String awakenEndTime) {
        this.awakenEndTime = awakenEndTime;
    }

    public int getAwakenType() {
        return awakenType;
    }

    public void setAwakenType(int awakenType) {
        this.awakenType = awakenType;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
