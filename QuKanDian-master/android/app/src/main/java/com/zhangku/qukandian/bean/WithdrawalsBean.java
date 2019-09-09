package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/9/26
 * 你不注释一下？
 */
public class WithdrawalsBean {
    /**
     * id : 67ad809c-6be3-4dc3-92ce-756d2eff71a6
     * userId : 0
     * missionId : 0
     * missionRuleId : 0
     * missionName : coin_heben_gift_consume
     * description : 兑换红包1元
     * coinAmount : 0.0
     * goldAmount : 0
     * creationTime : 0001-01-01T00:00:00
     * creationTimeStamp : -6.21355968E10
     */
    private String id;
    private int userId;
    private int missionId;
    private int missionRuleId;
    private String missionName;
    private String description;
    private double coinAmount;
    private int goldAmount;
    private String creationTime;
    private double creationTimeStamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getCoinAmount() {
        return coinAmount;
    }

    public void setCoinAmount(double coinAmount) {
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

    public double getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(double creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }
}
