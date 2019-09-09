package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/8/11.
 */

public class NewTudiBean {
    /**
     * userId : 1
     * avatarUrl : sample string 2
     * tel : sample string 3
     * level : 4
     * isFine : true
     * creationTime : 2017-09-06T10:19:05.316313+08:00
     * isGot : true
     * gotCoin : 1.0
     * gotTime : 2017-09-06T10:19:05.316313+08:00
     * nickName : sample string 8
     * id : 9
     */

    private int userId;
    private String avatarUrl;
    private String tel;
    private int level;
    private boolean isFine;
    private String creationTime;
    private boolean isGot;
    private double gotCoin;
    private String gotTime;
    private String nickName;
    private int id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isIsFine() {
        return isFine;
    }

    public void setIsFine(boolean isFine) {
        this.isFine = isFine;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isIsGot() {
        return isGot;
    }

    public void setIsGot(boolean isGot) {
        this.isGot = isGot;
    }

    public double getGotCoin() {
        return gotCoin;
    }

    public void setGotCoin(double gotCoin) {
        this.gotCoin = gotCoin;
    }

    public String getGotTime() {
        return gotTime;
    }

    public void setGotTime(String gotTime) {
        this.gotTime = gotTime;
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
