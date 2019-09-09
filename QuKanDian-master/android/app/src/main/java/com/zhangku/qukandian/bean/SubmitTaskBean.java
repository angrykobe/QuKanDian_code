package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/17
 * 你不注释一下？
 */
public class SubmitTaskBean {

    /**
     * id : 41254eab-d431-4882-9354-3807954f84fa
     * userId : 1108797
     * missionId : 9
     * missionRuleId : 20
     * missionName : reading_on@level0
     * description : double
     * coinAmount : 0.0
     * goldAmount : 40
     * creationTime : 0001-01-01T00:00:00
     * creationTimeStamp : -6.21355968E10
     */
    private String id;
    private int userId;
    private int missionId;
    private int missionRuleId;
    private String missionName;
    /// description ::
    /// 验证结果:
    /// 0：表示无错误
    /// 1:当天阅读金币上限,不能再奖励了
    /// 2:无效的阅读任务格式
    /// 3:本次阅读无法获得奖励（概率决定）
    /// 4:接口调用次数过高，可能是刷量
    /// 5:一篇文章只能获得一次奖励
    /// double:双倍奖励
    /// 有效阅读:奖励（兼容旧版本和0是一样的）
    /// 6:本次阅读无法获得奖励（异常）
    private String description;

    private double coinAmount;
    private String goldAmount;
    private String creationTime;
    private double creationTimeStamp;
    private int adsCnt;

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

    public String getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(String goldAmount) {
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

    public int getAdsCnt() {
        return adsCnt;
    }

    public void setAdsCnt(int adsCnt) {
        this.adsCnt = adsCnt;
    }
}
