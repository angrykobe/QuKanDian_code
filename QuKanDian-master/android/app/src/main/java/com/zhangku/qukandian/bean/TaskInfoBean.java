package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/4/10.
 */

public class TaskInfoBean {

    /**
     * missionId : 1   missionId=11
     * name : default   name=d1
     * displayName : default  displayName=一天
     * minCoinAmount : 0.5  minCoinAmount=0
     * minGoldAmount : 0  minGoldAmount=100
     * expType : 0   expType=0
     * isFinished : false
     * id : 1
     */

    private int missionId;
    private String name;
    private String displayName;
    private double minCoinAmount;
    private int minGoldAmount;
    private int expType;
    private boolean isFinished;
    private int id;
    private String coinAmountScope;

    public String getCoinAmountScope() {
        return coinAmountScope;
    }

    public void setCoinAmountScope(String coinAmountScope) {
        this.coinAmountScope = coinAmountScope;
    }


    public int getMissionId() {
        return missionId;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public double getMinCoinAmount() {
        return minCoinAmount;
    }

    public void setMinCoinAmount(double minCoinAmount) {
        this.minCoinAmount = minCoinAmount;
    }

    public int getMinGoldAmount() {
        return minGoldAmount;
    }

    public void setMinGoldAmount(int minGoldAmount) {
        this.minGoldAmount = minGoldAmount;
    }

    public int getExpType() {
        return expType;
    }

    public void setExpType(int expType) {
        this.expType = expType;
    }

    public boolean isIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
