package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/4/27.
 */

public class UpdateBean {
    /**
     * title : 测试版本0.1
     * description : 测试版本0.1，更新了~~数据
     * updateUrl : https://www.baidu.com/img/bd_logo1.png
     * level : 0
     * platform : 3
     * buildAndroidNo : 20170325
     * isAndroidForce : true
     * buildIosNo : 0.01
     * isIosForce : true
     * creationTime : 2017-04-26T21:04:29.23
     * id : 1
     */

    private String title;
    private String description;
    private String updateUrl;
    private int level;
    private int platform;
    private int buildAndroidNo;
    private boolean isAndroidForce;//是否强制升级
    private double buildIosNo;
    private boolean isIosForce;
    private String creationTime;
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public int getBuildAndroidNo() {
        return buildAndroidNo;
    }

    public void setBuildAndroidNo(int buildAndroidNo) {
        this.buildAndroidNo = buildAndroidNo;
    }

    public boolean isIsAndroidForce() {
        return isAndroidForce;
    }

    public void setIsAndroidForce(boolean isAndroidForce) {
        this.isAndroidForce = isAndroidForce;
    }

    public double getBuildIosNo() {
        return buildIosNo;
    }

    public void setBuildIosNo(double buildIosNo) {
        this.buildIosNo = buildIosNo;
    }

    public boolean isIsIosForce() {
        return isIosForce;
    }

    public void setIsIosForce(boolean isIosForce) {
        this.isIosForce = isIosForce;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
