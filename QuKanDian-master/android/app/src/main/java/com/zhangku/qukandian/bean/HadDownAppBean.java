package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 我参与的列表
 */
public class HadDownAppBean {
    /// logo
    private String logoImgSrc;
    /// AppId
    private int appId;
    /// App名称
    private String appName;
    /// 任务金币数量
    private int gold;
    /// 领取时间
    private String awardsTime;

    public String getLogoImgSrc() {
        return logoImgSrc;
    }

    public void setLogoImgSrc(String logoImgSrc) {
        this.logoImgSrc = logoImgSrc;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getAwardsTime() {
        return awardsTime;
    }

    public void setAwardsTime(String awardsTime) {
        this.awardsTime = awardsTime;
    }
}
