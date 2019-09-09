package com.zhangku.qukandian.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by yuzuoning on 2017/5/10.
 */

public class LocalMessageBean extends DataSupport{
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * userId : 1
     * title : sample string 1
     * content : sample string 2
     * tpltName : sample string 3
     * messageType : 0
     * creationTime : 2017-05-10T07:34:04.0316206+08:00
     * id : 89427d4d-8a96-488d-b4be-4561e37cca85
     * isReading : true
     */
    private long id;
    private int userId;
    private String title;
    private String content;
    private String tpltName;
    private int messageType;
    private String creationTime;
    private String newId;
    private boolean isReading;
    private String linkTo;
    private String actionUrl;

    public boolean isReading() {
        return isReading;
    }

    public void setReading(boolean reading) {
        isReading = reading;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(String linkTo) {
        this.linkTo = linkTo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTpltName() {
        return tpltName;
    }

    public void setTpltName(String tpltName) {
        this.tpltName = tpltName;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
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
    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }
}
