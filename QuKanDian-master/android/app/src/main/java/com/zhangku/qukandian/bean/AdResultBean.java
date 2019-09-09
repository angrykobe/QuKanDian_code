package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2018/1/16.
 */

public class AdResultBean {

    /**
     * userId : 10229
     * missionId : 31
     * missionRuleId : 70
     * missionName : ads_click_gift@default
     * description : 广告红包
     * coinAmount : 0
     * goldAmount : 100
     * creationTime : 2018-01-16T14:13:06.0541825+08:00
     * creatorUserId : 10229
     * id : 2e8f3b8f-5c71-4f8f-890b-ad12a663cb32
     */

    private int userId;
    private int missionId;
    private int missionRuleId;
    private String missionName;
    private String description;
    private int coinAmount;
    private int goldAmount;
    private String creationTime;
    private int creatorUserId;
    private String id;
    private int adsCnt;
    private int adsProgressCnt;//阅读进度已获取红包数（可能为空）

    @Override
    public String toString() {
        return "AdResultBean{" +
                "userId=" + userId +
                ", missionId=" + missionId +
                ", missionRuleId=" + missionRuleId +
                ", missionName='" + missionName + '\'' +
                ", description='" + description + '\'' +
                ", coinAmount=" + coinAmount +
                ", goldAmount=" + goldAmount +
                ", creationTime='" + creationTime + '\'' +
                ", creatorUserId=" + creatorUserId +
                ", id='" + id + '\'' +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMissionId() {
        return missionId;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public int getMissionRuleId() {
        return missionRuleId;
    }

    public void setMissionRuleId(int missionRuleId) {
        this.missionRuleId = missionRuleId;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCoinAmount() {
        return coinAmount;
    }

    public void setCoinAmount(int coinAmount) {
        this.coinAmount = coinAmount;
    }

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public int getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(int creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAdsCnt() {
        return adsCnt;
    }

    public void setAdsCnt(int adsCnt) {
        this.adsCnt = adsCnt;
    }

    public int getAdsProgressCnt() {
        return adsProgressCnt;
    }

    public void setAdsProgressCnt(int adsProgressCnt) {
        this.adsProgressCnt = adsProgressCnt;
    }
}
