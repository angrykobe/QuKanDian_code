package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/8/28.
 */

public class NewMeItemBean {
    private String name;
    private boolean isShowHot;
    private int img;
    private boolean isShowImg;

    public boolean isShowImg() {
        return isShowImg;
    }

    public void setShowImg(boolean showImg) {
        isShowImg = showImg;
    }

    public NewMeItemBean(int id, String s, boolean hot) {
        name = s;
        img = id;
        isShowHot = hot;
    }

    public boolean isShowHot() {
        return isShowHot;
    }

    public void setShowHot(boolean showHot) {
        isShowHot = showHot;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
