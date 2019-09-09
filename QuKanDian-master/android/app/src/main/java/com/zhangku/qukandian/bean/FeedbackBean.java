package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/4/19.
 */

public class FeedbackBean {
    public FeedbackBean(String content, String contactWay, String phoneVersion) {
        this.content = content;
        this.contactWay = contactWay;
        this.phoneVersion = phoneVersion;
    }

    /**
     * content : sample string 1
     * contactWay : sample string 2
     * id : 3
     */

    private String content;
    private String contactWay;
    private String phoneVersion;

    public String getPhoneVersion() {
        return phoneVersion;
    }

    public void setPhoneVersion(String phoneVersion) {
        this.phoneVersion = phoneVersion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }
}
