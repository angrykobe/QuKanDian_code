package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/6/14.
 */

public class WithdrawalsRemindBean {
    /**
     * userId : 0
     * fee : 1
     * applyStatus : 600
     * statusMemo : 提现成功
     * creationTime : 0001-01-01T00:00:00
     * id : 0
     */

    private int userId;
    private double fee;
    private int applyStatus;
    private String statusMemo;
    private String creationTime;
    private int id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getStatusMemo() {
        return statusMemo;
    }

    public void setStatusMemo(String statusMemo) {
        this.statusMemo = statusMemo;
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
