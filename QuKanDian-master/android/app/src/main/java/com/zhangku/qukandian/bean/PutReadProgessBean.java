package com.zhangku.qukandian.bean;

public class PutReadProgessBean {

    /**
     * "id": "77a57b0a-375c-4365-90a5-b8e24b0c0611",
     * 　　　　"userId": 357386,
     * 　　　　"missionId": 160,
     * 　　　　"missionRuleId": 198,
     * 　　　　"missionName": "read_progress@default",
     * 　　　　"description": "阅读进度1",
     * 　　　　"goldAmount": 10,
     * 　　　　"cnt": 0,
     * 　　　　"creationTimeStamp": 1558677524,
     * 　　　　"classifyId": 0,
     * 　　　　"adsCnt": -2,
     * 　　　　"rsCnt": 0,
     * 　　　　"creationUser": 0,
     * 　　　　"isError": false
     */
    private String id;
    private int userId;
    private int missionId;
    private int missionRuleId;
    private String missionName;//??
    private String description;//任务名称
    private int goldAmount;//金币
    private int cnt;//
    private long creationTimeStamp;//
    private int classifyId;//
    private int adsCnt;//
    private int rsCnt;//
    private int creationUser;//
    private boolean isError;//

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

    public int getGoldAmount() {
        return goldAmount;
    }

    public void setGoldAmount(int goldAmount) {
        this.goldAmount = goldAmount;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public long getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(long creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public int getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(int classifyId) {
        this.classifyId = classifyId;
    }

    public int getAdsCnt() {
        return adsCnt;
    }

    public void setAdsCnt(int adsCnt) {
        this.adsCnt = adsCnt;
    }

    public int getRsCnt() {
        return rsCnt;
    }

    public void setRsCnt(int rsCnt) {
        this.rsCnt = rsCnt;
    }

    public int getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(int creationUser) {
        this.creationUser = creationUser;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
