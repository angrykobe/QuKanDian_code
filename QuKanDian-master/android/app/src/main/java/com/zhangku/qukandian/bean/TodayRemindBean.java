package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/5/26.
 */

public class TodayRemindBean {
    /**
     * isVester : true
     * avatarUrl : sample string 2
     * goldSum : 3
     * menteeSum : 4
     * pushGoldSum : 5
     * nickName : sample string 6
     * id : 7
     */

    private boolean isVester;
    private String avatarUrl;
    private int goldSum;
    private int menteeSum;
    private int pushGoldSum;
    private String nickName;
    private int id;

    public boolean isIsVester() {
        return isVester;
    }

    public void setIsVester(boolean isVester) {
        this.isVester = isVester;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getGoldSum() {
        return goldSum;
    }

    public void setGoldSum(int goldSum) {
        this.goldSum = goldSum;
    }

    public int getMenteeSum() {
        return menteeSum;
    }

    public void setMenteeSum(int menteeSum) {
        this.menteeSum = menteeSum;
    }

    public int getPushGoldSum() {
        return pushGoldSum;
    }

    public void setPushGoldSum(int pushGoldSum) {
        this.pushGoldSum = pushGoldSum;
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
