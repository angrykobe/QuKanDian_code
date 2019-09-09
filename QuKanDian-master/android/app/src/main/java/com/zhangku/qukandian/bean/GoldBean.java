package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/4/12.
 */

public class GoldBean {
    /**
     * userId : 1
     * missionName : sample string 2
     * description : sample string 3
     * amount : 4.0
     * creationTime : 2017-04-13T15:46:57.1552705+08:00
     * currentAmount : 6.0
     * currentAmountSum : 7.0
     * id : 9f56e4e6-449a-448a-b796-21a1eacab3fc
     */

    private int userId;
    private String missionName;
    private String description;
    private double amount;
    private String creationTime;
    private double currentAmount;
    private double currentAmountSum;
    private String id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public double getCurrentAmountSum() {
        return currentAmountSum;
    }

    public void setCurrentAmountSum(double currentAmountSum) {
        this.currentAmountSum = currentAmountSum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
