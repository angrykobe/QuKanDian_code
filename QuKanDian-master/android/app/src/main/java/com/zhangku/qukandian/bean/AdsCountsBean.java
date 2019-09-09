package com.zhangku.qukandian.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by yuzuoning on 2017/12/20.
 */

public class AdsCountsBean extends DataSupport {
    private int id;
    private int userId;
    private int adlocId;
    private int adType;
    private int advertiserId;
    private int referId;
    private int pv;
    private int firstClick;
    private int secondClick;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAdlocId() {
        return adlocId;
    }

    public void setAdlocId(int adlocId) {
        this.adlocId = adlocId;
    }

    public int getAdType() {
        return adType;
    }

    public void setAdType(int adType) {
        this.adType = adType;
    }

    public int getAdvertiserId() {
        return advertiserId;
    }

    public void setAdvertiserId(int advertiserId) {
        this.advertiserId = advertiserId;
    }

    public int getReferId() {
        return referId;
    }

    public void setReferId(int referId) {
        this.referId = referId;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getFirstClick() {
        return firstClick;
    }

    public void setFirstClick(int firstClick) {
        this.firstClick = firstClick;
    }

    public int getSecondClick() {
        return secondClick;
    }

    public void setSecondClick(int secondClick) {
        this.secondClick = secondClick;
    }
}
