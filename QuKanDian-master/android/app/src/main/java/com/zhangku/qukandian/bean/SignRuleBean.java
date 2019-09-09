package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2018/1/25.
 */

public class SignRuleBean {

    /**
     * firstDayGold : 5
     * incrementGold : 5
     * isRestart : true
     * toastType : 2
     */

    private int firstDayGold;
    private int incrementGold;
    private boolean isRestart;
    private int toastType;//1广告 2任务
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getFirstDayGold() {
        return firstDayGold;
    }

    public void setFirstDayGold(int firstDayGold) {
        this.firstDayGold = firstDayGold;
    }

    public int getIncrementGold() {
        return incrementGold;
    }

    public void setIncrementGold(int incrementGold) {
        this.incrementGold = incrementGold;
    }

    public boolean isIsRestart() {
        return isRestart;
    }

    public void setIsRestart(boolean isRestart) {
        this.isRestart = isRestart;
    }

    public int getToastType() {
        return toastType;
    }

    public void setToastType(int toastType) {
        this.toastType = toastType;
    }
}
