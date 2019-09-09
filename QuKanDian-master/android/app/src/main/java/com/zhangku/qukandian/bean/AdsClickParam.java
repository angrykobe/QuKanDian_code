package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/12/7.
 */

public class AdsClickParam {
    public int ReferId;
    public int AdLocId;
    public int AdType;
    public int AdvertiserId;
    public String AdvertiserName;
    public int Amount;
    public int AdsBagType;
    private String unionId;
    private int adPageType;//枚举：1-首页，2-视频详情页，3-文章详情页，4-视频列表，6-开屏页，7-签到页，8-宝箱页）
    private String adLink;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAdPageType() {
        return adPageType;
    }

    public void setAdPageType(int adPageType) {
        this.adPageType = adPageType;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public int getAdsBagType() {
        return AdsBagType;
    }

    public void setAdsBagType(int adsBagType) {
        AdsBagType = adsBagType;
    }

    public int getReferId() {
        return ReferId;
    }

    public void setReferId(int referId) {
        ReferId = referId;
    }

    public int getAdLocId() {
        return AdLocId;
    }

    public void setAdLocId(int adLocId) {
        AdLocId = adLocId;
    }

    public int getAdType() {
        return AdType;
    }

    public void setAdType(int adType) {
        AdType = adType;
    }

    public int getAdvertiserId() {
        return AdvertiserId;
    }

    public void setAdvertiserId(int advertiserId) {
        AdvertiserId = advertiserId;
    }

    public String getAdvertiserName() {
        return AdvertiserName;
    }

    public void setAdvertiserName(String advertiserName) {
        AdvertiserName = advertiserName;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getAdLink() {
        return adLink;
    }

    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }
}
