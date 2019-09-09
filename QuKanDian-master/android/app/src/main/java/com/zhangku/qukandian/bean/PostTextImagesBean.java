package com.zhangku.qukandian.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by yuzuoning on 2017/4/18.
 */

public class PostTextImagesBean extends DataSupport implements Serializable {
    /**
     * postId : 1
     * src : sample string 2
     * alt : sample string 3
     * isCdn : true
     * orderId : 5
     * id : 6
     */

    private int postId;
    private String src;
    private String alt;
    private boolean isCdn;
    private int orderId;
    private int id;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public boolean isIsCdn() {
        return isCdn;
    }

    public void setIsCdn(boolean isCdn) {
        this.isCdn = isCdn;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
