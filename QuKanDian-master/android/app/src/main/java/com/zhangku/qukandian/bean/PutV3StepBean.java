package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/9/27.
 */

public class PutV3StepBean {
    /**
     * userId : 1
     * openId : sample string 2
     * endTime : 2017-09-27T13:36:49.5878638+08:00
     * makespanDate : 1
     * isSuccess : true
     * memo : sample string 4
     * gotCoin : 1.0
     * partnerTradeNo : sample string 5
     * creationTime : 2017-09-27T13:36:49.5878638+08:00
     * id : 7
     */

    private int userId;
    private String openId;
    private String endTime;
    private int makespanDate;
    private boolean isSuccess;
    private String memo;
    private double gotCoin;
    private String partnerTradeNo;
    private String creationTime;
    private int id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getMakespanDate() {
        return makespanDate;
    }

    public void setMakespanDate(int makespanDate) {
        this.makespanDate = makespanDate;
    }

    public boolean isIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getGotCoin() {
        return gotCoin;
    }

    public void setGotCoin(double gotCoin) {
        this.gotCoin = gotCoin;
    }

    public String getPartnerTradeNo() {
        return partnerTradeNo;
    }

    public void setPartnerTradeNo(String partnerTradeNo) {
        this.partnerTradeNo = partnerTradeNo;
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
