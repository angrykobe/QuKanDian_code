package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/11/1
 * 你不注释一下？
 */
public class TaskChestBean {
    private int order; // 第几个宝箱
    private int minGold; // 最小金币数（最小金币数>=最大金币时，取最小金币数）
    private long maxGold; // 最大金币数

    private int resouNum; //热搜需完成次数
    private int readNum; //有效阅读需完成次数
    private int luckyNum; //幸运任务需完成次数
    private int adsNum; //广告红包需完成次数

    private int resouFinishedNum; //热搜需完成次数
    private int readFinishedNum; //有效阅读已需完成次数
    private int luckyFinishedNum; //幸运任务已完成次数
    private int adsFinishedNum; //广告红包已完成次数
    private int chestBoxType; //宝箱状态，0:未开启，1：查看进度（未完成），2：已完成，3：已领取     }

    @Override
    public String toString() {
        return "TaskChestBean{" +
                "order=" + order +
                ", minGold=" + minGold +
                ", maxGold=" + maxGold +
                ", resouNum=" + resouNum +
                ", readNum=" + readNum +
                ", luckyNum=" + luckyNum +
                ", adsNum=" + adsNum +
                ", resouFinishedNum=" + resouFinishedNum +
                ", readFinishedNum=" + readFinishedNum +
                ", luckyFinishedNum=" + luckyFinishedNum +
                ", adsFinishedNum=" + adsFinishedNum +
                ", chestBoxType=" + chestBoxType +
                '}';
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getMinGold() {
        return minGold;
    }

    public void setMinGold(int minGold) {
        this.minGold = minGold;
    }

    public long getMaxGold() {
        return maxGold;
    }

    public void setMaxGold(long maxGold) {
        this.maxGold = maxGold;
    }

    public int getResouNum() {
        return resouNum;
    }

    public void setResouNum(int resouNum) {
        this.resouNum = resouNum;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getLuckyNum() {
        return luckyNum;
    }

    public void setLuckyNum(int luckyNum) {
        this.luckyNum = luckyNum;
    }

    public int getAdsNum() {
        return adsNum;
    }

    public void setAdsNum(int adsNum) {
        this.adsNum = adsNum;
    }

    public int getResouFinishedNum() {
        return resouFinishedNum;
    }

    public void setResouFinishedNum(int resouFinishedNum) {
        this.resouFinishedNum = resouFinishedNum;
    }

    public int getReadFinishedNum() {
        return readFinishedNum;
    }

    public void setReadFinishedNum(int readFinishedNum) {
        this.readFinishedNum = readFinishedNum;
    }

    public int getLuckyFinishedNum() {
        return luckyFinishedNum;
    }

    public void setLuckyFinishedNum(int luckyFinishedNum) {
        this.luckyFinishedNum = luckyFinishedNum;
    }

    public int getAdsFinishedNum() {
        return adsFinishedNum;
    }

    public void setAdsFinishedNum(int adsFinishedNum) {
        this.adsFinishedNum = adsFinishedNum;
    }

    public int getChestBoxType() {
        return chestBoxType;
    }

    public void setChestBoxType(int chestBoxType) {
        this.chestBoxType = chestBoxType;
    }
}
