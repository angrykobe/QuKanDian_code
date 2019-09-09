package com.zhangku.qukandian.bean;

import com.zhangku.qukandian.manager.UserManager;

/**
 * Created by yuzuoning on 2018/1/12.
 */

public class AdMissionOtherBean {
    private String missionName;
    private int userId;
    private int missionType;
    private String imei;
    private String clientIp;
    private boolean isMore = false;//是否多任务；多任务情况传true
    private String adsName;//广告名称；多任务情况必须传广告名称

    public AdMissionOtherBean(String missionName, int missionType, String imei) {
        this.missionName = missionName;
        this.userId = UserManager.getInst().getUserBeam().getId();
        this.missionType = missionType;
        this.imei = imei;
        this.clientIp = UserManager.getInst().getQukandianBean().getClientIp();
    }

    public boolean getIsMore() {
        return isMore;
    }

    public void setIsMore(boolean isMore) {
        this.isMore = isMore;
    }

    public String getAdsName() {
        return adsName;
    }

    public void setAdsName(String adsName) {
        this.adsName = adsName;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMissionType() {
        return missionType;
    }

    public void setMissionType(int missionType) {
        this.missionType = missionType;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
