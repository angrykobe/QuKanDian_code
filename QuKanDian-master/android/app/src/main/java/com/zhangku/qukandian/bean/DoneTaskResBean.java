package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/23
 * 任务接口返回参数 ResponseBean
 */
public class DoneTaskResBean {
    /**
     * 注册送红包
     * id : 8126e66d-bf5a-478f-8404-cd9533e7b820
     * userId : 1109315
     * missionId : 2
     * missionRuleId : 2
     * missionName : register_coin@default
     * description : 注册送红包
     * coinAmount : 0.0
     * goldAmount : 5000
     * creationTime : 2018-07-23T11:14:51.5275315+08:00
     * creationTimeStamp : 1.5323444915275316E9
     *
     *  每日签到返回事例
     * "id": "cc9f1e2c-ce1c-4116-ae41-5c156b1e4a4a",
     * "userId": 1109449,
     * "missionId": 11,
     * "missionRuleId": 42,
     * "missionName": "sign_continued@d1",
     * "description": "连续签到",
     * "coinAmount": 0,
     * "goldAmount": 50,
     * "creationTime": "2018-08-07T11:47:57.8457341+08:00",
     * "creationTimeStamp": 1533642477.84573
     */
    private String id;
    private int userId;
    private int missionId;
    private int missionRuleId;
    private String missionName;//??
    private String description;//任务名称
    private double coinAmount;
    private int goldAmount;//金币
    private String creationTime;
    private double creationTimeStamp;
    private BoxRuleBean rule;
    private String msg;

    private int rsCnt;

    public int getRsCnt() {
        return rsCnt;
    }

    public void setRsCnt(int rsCnt) {
        this.rsCnt = rsCnt;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BoxRuleBean getRule() {
        return rule;
    }

    public void setRule(BoxRuleBean rule) {
        this.rule = rule;
    }

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
