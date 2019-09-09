package com.zhangku.qukandian.bean;

import java.util.ArrayList;
import java.util.List;

public class ReadProgessBean {
    private ArrayList<Integer> finished;
    private List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> ads;

    public List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> getAds() {
        return ads;
    }

    public void setAds(List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> ads) {
        this.ads = ads;
    }

    public ArrayList<Integer> getFinished() {
        return finished;
    }

    public void setFinished(ArrayList<Integer> finished) {
        this.finished = finished;
    }
}
