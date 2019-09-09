package com.zhangku.qukandian.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by yuzuoning on 2017/5/10.
 */

public class MessageBean extends DataSupport{

    /**
     * title : sample string 1
     * content : sample string 2
     * linkTo : sample string 3
     * creationTime : 2018-04-11T16:24:00.0334311+08:00
     * isReading : true
     * id : 5021f1f8-c7a1-4b44-9fa4-9208003738b4
     */

    private String title;
    private String content;
    private String linkTo;
    private String creationTime;
    private boolean isReading;
    private String id;
    private int type;
    private String actionUrl;

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        actionUrl = actionUrl;
    }

    public boolean isReading() {
        return isReading;
    }

    public void setReading(boolean reading) {
        isReading = reading;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(String linkTo) {
        this.linkTo = linkTo;
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
}
