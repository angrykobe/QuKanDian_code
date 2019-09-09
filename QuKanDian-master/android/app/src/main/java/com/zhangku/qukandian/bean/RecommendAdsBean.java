package com.zhangku.qukandian.bean;

import java.util.List;

public class RecommendAdsBean {
    private List<InformationBean> postTexts;
    private List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> ads;

    public List<InformationBean> getPostTexts() {
        return postTexts;
    }

    public void setPostTexts(List<InformationBean> postTexts) {
        this.postTexts = postTexts;
    }

    public List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> getAds() {
        return ads;
    }

    public void setAds(List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> ads) {
        this.ads = ads;
    }
}
