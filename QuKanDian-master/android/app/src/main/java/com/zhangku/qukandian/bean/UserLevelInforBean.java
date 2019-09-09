package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2019/4/17
 */
public class UserLevelInforBean {
    /**
     * exp : 10
     * creationTime : 2019-04-17T10:35:14.32
     * date : 2019-04-17
     * time : 10:35:14
     */
    private int exp;
    private String creationTime = "";
    private String date = "";
    private String time = "";
    private String displayDesc = "";

    public String getDisplayDesc() {
        return displayDesc;
    }

    public void setDisplayDesc(String displayDesc) {
        this.displayDesc = displayDesc;
    }
    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
