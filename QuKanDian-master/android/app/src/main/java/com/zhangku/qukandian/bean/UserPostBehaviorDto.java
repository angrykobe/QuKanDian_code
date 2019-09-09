package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/6/14
 * 新闻、视频详情点赞，不喜欢请求bean
 */
public class UserPostBehaviorDto {
    private int postId;
    private int userId;
    private String imei;
    private int actionType;//8 点赞 9 不喜欢
    private String reason;
    //292
    private String zyId;
    private String textType;

    public String getZyId() {
        return zyId;
    }

    public void setZyId(String zyId) {
        this.zyId = zyId;
    }

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
