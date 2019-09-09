package com.zhangku.qukandian.bean;

import android.content.Context;

import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.MachineInfoUtil;

/**
 * Created by yuzuoning on 2017/12/13.
 */

public class AdMissionBean {
    /**
     * userId : 1
     * missionType : 1
     * imei : sample string 2
     * clientIp : sample string 3
     */

    private int userId;
    private int missionType;
    private String imei;
    private String clientIp;

    public AdMissionBean(int missionType, Context context) {
        this.userId = UserManager.getInst().getUserBeam().getId();
        this.imei = MachineInfoUtil.getInstance().getIMEI(context);
        this.clientIp = UserManager.getInst().getQukandianBean().getClientIp();
        this.missionType = missionType;
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
