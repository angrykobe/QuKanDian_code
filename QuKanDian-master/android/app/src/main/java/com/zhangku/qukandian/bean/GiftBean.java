package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class GiftBean {
    /**
     * title : 微信红包20元
     * coverSrc : http://cdn.qu.fi.pqmnz.com/images/currency/haobao/20%402x.png
     * fee : 1
     * gold : 10000
     * isActive : true
     * id : 1
     */

    private String title;
    private String coverSrc;
    private float fee;
    private int gold;
    private boolean isActive;
    private int id;
    //290  区分一元提现和一元福利  OneYuan
    private String name;//
    //291  描述
    private String desc;
    private boolean isLevel;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isLevel() {
        return isLevel;
    }

    public void setLevel(boolean level) {
        isLevel = level;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverSrc() {
        return coverSrc;
    }

    public void setCoverSrc(String coverSrc) {
        this.coverSrc = coverSrc;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
