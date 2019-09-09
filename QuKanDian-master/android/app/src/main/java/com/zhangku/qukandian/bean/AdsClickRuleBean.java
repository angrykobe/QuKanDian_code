package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/12/7.
 */

public class AdsClickRuleBean {

    /**
     * singleUserDayMaxCount : 100
     * getGiftRate : 100
     * getGiftDuration : 5
     * giftPresentRate : 100
     * goldGradeLimit : 50
     * isNeedReadingFirst : false
     * userContributeStandard : 2
     * scGetGiftDuration : 32
     * scGetGiftRate : 100
     * scGetGiftRateNoClick
     */

    private int singleUserDayMaxCount;
    private int getGiftRate;
    private int getGiftDuration;
    private int giftPresentRate;
    private int goldGradeLimit;
    private int userContributeStandard;
    private int scGetGiftDuration;
    private int scGetGiftRate;
    private int scGetGiftRateNoClick;
    private boolean isNeedReadingFirst;//是否要有效阅读


    public int getScGetGiftRateNoClick() {
        return scGetGiftRateNoClick;
    }

    public void setScGetGiftRateNoClick(int scGetGiftRateNoClick) {
        this.scGetGiftRateNoClick = scGetGiftRateNoClick;
    }

    public boolean isNeedReadingFirst() {
        return isNeedReadingFirst;
    }

    public void setNeedReadingFirst(boolean needReadingFirst) {
        isNeedReadingFirst = needReadingFirst;
    }

    public int getSingleUserDayMaxCount() {
        return singleUserDayMaxCount;
    }

    public void setSingleUserDayMaxCount(int singleUserDayMaxCount) {
        this.singleUserDayMaxCount = singleUserDayMaxCount;
    }

    public int getGetGiftRate() {
        return getGiftRate;
    }

    public void setGetGiftRate(int getGiftRate) {
        this.getGiftRate = getGiftRate;
    }

    public int getGetGiftDuration() {
        return getGiftDuration;
    }

    public void setGetGiftDuration(int getGiftDuration) {
        this.getGiftDuration = getGiftDuration;
    }

    public int getGiftPresentRate() {
        return giftPresentRate;
    }

    public void setGiftPresentRate(int giftPresentRate) {
        this.giftPresentRate = giftPresentRate;
    }

    public int getGoldGradeLimit() {
        return goldGradeLimit;
    }

    public void setGoldGradeLimit(int goldGradeLimit) {
        this.goldGradeLimit = goldGradeLimit;
    }

    public boolean isIsNeedReadingFirst() {
        return isNeedReadingFirst;
    }

    public void setIsNeedReadingFirst(boolean isNeedReadingFirst) {
        this.isNeedReadingFirst = isNeedReadingFirst;
    }

    public int getUserContributeStandard() {
        return userContributeStandard;
    }

    public void setUserContributeStandard(int userContributeStandard) {
        this.userContributeStandard = userContributeStandard;
    }

    public int getScGetGiftDuration() {
        return scGetGiftDuration;
    }

    public void setScGetGiftDuration(int scGetGiftDuration) {
        this.scGetGiftDuration = scGetGiftDuration;
    }

    public int getScGetGiftRate() {
        return scGetGiftRate;
    }

    public void setScGetGiftRate(int scGetGiftRate) {
        this.scGetGiftRate = scGetGiftRate;
    }
}
