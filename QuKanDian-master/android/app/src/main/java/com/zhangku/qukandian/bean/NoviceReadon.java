package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/4/17.
 */

public class NoviceReadon {
    /**
     * userId : 1
     * stepTime : 2
     * stepLevel : 3
     * postId : 4
     * durationSecond : 5
     * creationTime : 2017-04-17T13:38:25.2225+08:00
     * isReading : true
     * id : 9c932f42-34bc-4901-8a57-c0e0d4acbcba
     */

    private int userId;
    private int stepTime;
    private int stepLevel;
    private int postId;
    private int durationSecond;
    private String creationTime;
    private boolean isReading;
    private String id;

    private boolean isOverdue;

    public boolean isOverdue() {
        return isOverdue;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
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

    public int getStepLevel() {
        return stepLevel;
    }

    public void setStepLevel(int stepLevel) {
        this.stepLevel = stepLevel;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getDurationSecond() {
        return durationSecond;
    }

    public void setDurationSecond(int durationSecond) {
        this.durationSecond = durationSecond;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isIsReading() {
        return isReading;
    }

    public void setIsReading(boolean isReading) {
        this.isReading = isReading;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
