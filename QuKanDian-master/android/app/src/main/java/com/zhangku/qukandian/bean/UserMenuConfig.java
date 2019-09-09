package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/9/7
 * 你不注释一下？
 */

public class UserMenuConfig {
    private String name = "";
    private String description = "";
    private String imgSrc;
    private int menuType; //-1 头部
    private String orderId;
    private String gotoLink;
    private String iosGotoLink;
    //是否有未读消息提示  前端自己添加判断
    private boolean isShow;


    @Override
    public String toString() {
        return "UserMenuConfig{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", menuType='" + menuType + '\'' +
                ", orderId='" + orderId + '\'' +
                ", gotoLink='" + gotoLink + '\'' +
                ", iosGotoLink='" + iosGotoLink + '\'' +
                '}';
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGotoLink() {
        return gotoLink;
    }

    public void setGotoLink(String gotoLink) {
        this.gotoLink = gotoLink;
    }

    public String getIosGotoLink() {
        return iosGotoLink;
    }

    public void setIosGotoLink(String iosGotoLink) {
        this.iosGotoLink = iosGotoLink;
    }
}

