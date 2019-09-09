package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/8/29.
 */

public class ChestBoxBean {

    /**
     * userId : 1
     * stepTime : 2
     * creationTime : 2017-08-29T15:29:26.819+08:00
     * id : 1f22748e-6913-4001-8b4e-013f2bf8d9c9
     */

    private int userId;
    private int stepTime;
    private String creationTime;
    private String id;

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    private Boolean isEnable;
    private double currentTimeStamp;//当前时间
    private double creationTimeStamp;//上次拆宝箱时间
    private long secondsLeft;

    public BoxRuleBean getRule() {
        return rule;
    }

    public void setRule(BoxRuleBean rule) {
        this.rule = rule;
    }

    private BoxRuleBean rule;

    public long getSecondsLeft() {
        return secondsLeft;
    }

    public void setSecondsLeft(long secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    public void setCurrentTimeStamp(double currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public double getCurrentTimeStamp() {
        return currentTimeStamp;
    }
    public double getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(double creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStepTime() {
        return stepTime;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ChestBoxBean{" +
                "userId=" + userId +
                ", stepTime=" + stepTime +
                ", creationTime='" + creationTime + '\'' +
                ", id='" + id + '\'' +
                ", currentTimeStamp=" + currentTimeStamp +
                ", creationTimeStamp=" + creationTimeStamp +
                '}';
    }
}
