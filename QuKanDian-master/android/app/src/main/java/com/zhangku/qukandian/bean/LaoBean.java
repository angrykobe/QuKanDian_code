package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/12/14.
 */

public class LaoBean {
    /**
     * duration : 60
     * oneTimeAmount : 20
     * isEnableTimeSpan : false
     * startTime : 8
     * endTime : 22
     */

    private int duration;
    private int oneTimeAmount;
    private boolean isEnableTimeSpan;
    private int startTime;
    private int endTime;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getOneTimeAmount() {
        return oneTimeAmount;
    }

    public void setOneTimeAmount(int oneTimeAmount) {
        this.oneTimeAmount = oneTimeAmount;
    }

    public boolean isIsEnableTimeSpan() {
        return isEnableTimeSpan;
    }

    public void setIsEnableTimeSpan(boolean isEnableTimeSpan) {
        this.isEnableTimeSpan = isEnableTimeSpan;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
}
