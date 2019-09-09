package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2018/1/25.
 * 拆宝箱规则
 */

public class BoxRuleBean {
    /**
     * isEnable : false
     * minGoldAmount : 3
     * maxGoldAmount : 11
     * duration : 5
     */

    private boolean isEnable;
    private int minGoldAmount;
    private int maxGoldAmount;
    private int duration;
    private int toastType;//1：广告，2：任务推荐  判断签到弹框是要任务还是广告

    public boolean isIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public int getMinGoldAmount() {
        return minGoldAmount;
    }

    public void setMinGoldAmount(int minGoldAmount) {
        this.minGoldAmount = minGoldAmount;
    }

    public int getMaxGoldAmount() {
        return maxGoldAmount;
    }

    public void setMaxGoldAmount(int maxGoldAmount) {
        this.maxGoldAmount = maxGoldAmount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getToastType() {
        return toastType;
    }

    public void setToastType(int toastType) {
        this.toastType = toastType;
    }
}
