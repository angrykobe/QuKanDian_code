package com.zhangku.qukandian.bean;

import java.io.Serializable;

/**
 * 创建者          xuzhida
 * 创建日期        2019/1/21
 * 热搜规则
 */
public class ResouRuleBean implements Serializable {
    /**
     * resouItemId : 34
     * order : 2
     * rateMin : 70
     * rateMax : 80
     * rate : 79
     * stayTimeStr : 2,6/3,9
     * id : 38
     */
    private int resouItemId;
    private int order;
    private float rateMin;
    private float rateMax;
    private float rate;
    private String stayTimeStr;
    private int id;

    private int type;//1：来源于 幸运转盘   0：默认旧版本的广告

    public int getResouItemId() {
        return resouItemId;
    }

    public void setResouItemId(int resouItemId) {
        this.resouItemId = resouItemId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public float getRateMin() {
        return rateMin;
    }

    public void setRateMin(float rateMin) {
        this.rateMin = rateMin;
    }

    public float getRateMax() {
        return rateMax;
    }

    public void setRateMax(float rateMax) {
        this.rateMax = rateMax;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getStayTimeStr() {
        return stayTimeStr;
    }

    public void setStayTimeStr(String stayTimeStr) {
        this.stayTimeStr = stayTimeStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
