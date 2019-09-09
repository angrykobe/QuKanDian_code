package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class MenteeBean {
    /**
     * menteeId : 10279
     * mobileNumber : 159****6465
     * isRegistered : true
     * indexNo : 20
     * mentorThankTime : 0
     * mentorThankGold : 0
     * pushDoubleGold : 0
     * nickName : 趣友50
     * id : 50
     */

    private int menteeId;
    private String mobileNumber;
    private boolean isRegistered;
    private int indexNo;
    private int mentorThankTime;
    private int mentorThankGold;
    private int pushDoubleGold;
    private String nickName;
    private int id;

    public int getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(int menteeId) {
        this.menteeId = menteeId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(boolean isRegistered) {
        this.isRegistered = isRegistered;
    }

    public int getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }

    public int getMentorThankTime() {
        return mentorThankTime;
    }

    public void setMentorThankTime(int mentorThankTime) {
        this.mentorThankTime = mentorThankTime;
    }

    public int getMentorThankGold() {
        return mentorThankGold;
    }

    public void setMentorThankGold(int mentorThankGold) {
        this.mentorThankGold = mentorThankGold;
    }

    public int getPushDoubleGold() {
        return pushDoubleGold;
    }

    public void setPushDoubleGold(int pushDoubleGold) {
        this.pushDoubleGold = pushDoubleGold;
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
