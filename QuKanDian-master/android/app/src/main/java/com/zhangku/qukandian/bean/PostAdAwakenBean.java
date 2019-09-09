package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/26
 * 你不注释一下？
 */
public class PostAdAwakenBean {
    private String referId;
    private String advertiserId;
    private String advertiserName;// 广告商
    private String adPageType;// 所在页面（根据ads/AdPageLocationNew/GetList?Page=1&PageSize=500接口的adPageType字段匹配，adPageLocationName为显示名称。枚举：1-首页，2-视频详情页，3-文章详情页，4-视频列表，6-开屏页，7-签到页，8-宝箱页）
    private String pageIndex;// 所在位置
    private String adType;// 广告类型（枚举：1-自主广告，2-联盟广告，3-合作链接）
    private String adsName;// 广告名称

    public String getReferId() {
        return referId;
    }

    public void setReferId(String referId) {
        this.referId = referId;
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(String advertiserId) {
        this.advertiserId = advertiserId;
    }

    public String getAdvertiserName() {
        return advertiserName;
    }

    public void setAdvertiserName(String advertiserName) {
        this.advertiserName = advertiserName;
    }

    public String getAdPageType() {
        return adPageType;
    }

    public void setAdPageType(String adPageType) {
        this.adPageType = adPageType;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getAdsName() {
        return adsName;
    }

    public void setAdsName(String adsName) {
        this.adsName = adsName;
    }
}
