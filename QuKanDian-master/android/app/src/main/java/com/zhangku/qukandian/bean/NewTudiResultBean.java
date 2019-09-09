package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/8/14.
 */

public class NewTudiResultBean {
    /**
     * endTime : 2017-08-16T19:57:03.3961361+08:00
     * makespanDate : 2
     * isFailure : true
     * isGot : true
     * gotTime : 2017-08-16T19:57:03.3961361+08:00
     * gotMentee : 5
     * gotFineMentee : 6
     * gotCoin : 1.0
     * id : 7
     */

    private String endTime;
    private int makespanDate;
    private boolean isFailure;
    private boolean isGot;
    private String gotTime;
    private int gotMentee;
    private int gotFineMentee;
    private int leftMenteeSum;
    private double gotCoin;
    private int id;

    public int getLeftMenteeSum() {
        return leftMenteeSum;
    }

    public void setLeftMenteeSum(int leftMenteeSum) {
        this.leftMenteeSum = leftMenteeSum;
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

    public boolean isIsFailure() {
        return isFailure;
    }

    public void setIsFailure(boolean isFailure) {
        this.isFailure = isFailure;
    }

    public boolean isIsGot() {
        return isGot;
    }

    public void setIsGot(boolean isGot) {
        this.isGot = isGot;
    }

    public String getGotTime() {
        return gotTime;
    }

    public void setGotTime(String gotTime) {
        this.gotTime = gotTime;
    }

    public int getGotMentee() {
        return gotMentee;
    }

    public void setGotMentee(int gotMentee) {
        this.gotMentee = gotMentee;
    }

    public int getGotFineMentee() {
        return gotFineMentee;
    }

    public void setGotFineMentee(int gotFineMentee) {
        this.gotFineMentee = gotFineMentee;
    }

    public double getGotCoin() {
        return gotCoin;
    }

    public void setGotCoin(double gotCoin) {
        this.gotCoin = gotCoin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
