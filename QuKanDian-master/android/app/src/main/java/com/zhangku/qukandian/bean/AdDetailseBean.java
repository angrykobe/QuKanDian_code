package com.zhangku.qukandian.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yuzuoning on 2017/7/13.
 */

public class AdDetailseBean implements Parcelable {
    private int ReferId;
    private int AdLocId;
    private int AdType;
    private int AdvertiserId;
    private String AdvertiserName;
    private int Amount;
    private boolean isShowed;
    private String url;
    private boolean isSecondClickGoldEnable;
    private int getGiftRate;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public boolean isShowed() {
        return isShowed;
    }

    public void setShowed(boolean showed) {
        isShowed = showed;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSecondClickGoldEnable() {
        return isSecondClickGoldEnable;
    }

    public void setSecondClickGoldEnable(boolean secondClickGoldEnable) {
        isSecondClickGoldEnable = secondClickGoldEnable;
    }

    public int getGetGiftRate() {
        return getGiftRate;
    }

    public void setGetGiftRate(int getGiftRate) {
        this.getGiftRate = getGiftRate;
    }

    public AdDetailseBean(String url, boolean isShowed, int ReferId, int AdLocId, int AdType,
                          int AdvertiserId, String AdvertiserName, int Amount, boolean isSecondClickGoldEnable, int getGiftRate,int index) {
        this.url = url;
        this.isShowed = isShowed;
        this.ReferId = ReferId;
        this.AdLocId = AdLocId;
        this.AdType = AdType;
        this.AdvertiserId = AdvertiserId;
        this.AdvertiserName = AdvertiserName;
        this.Amount = Amount;
        this.isSecondClickGoldEnable = isSecondClickGoldEnable;
        this.getGiftRate = getGiftRate;
        this.index = index;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ReferId);
        dest.writeInt(this.AdLocId);
        dest.writeInt(this.AdType);
        dest.writeInt(this.AdvertiserId);
        dest.writeString(this.AdvertiserName);
        dest.writeInt(this.Amount);
        dest.writeByte(this.isShowed ? (byte) 1 : (byte) 0);
        dest.writeString(this.url);
        dest.writeByte(this.isSecondClickGoldEnable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.getGiftRate);
        dest.writeInt(this.index);
    }

    protected AdDetailseBean(Parcel in) {
        this.ReferId = in.readInt();
        this.AdLocId = in.readInt();
        this.AdType = in.readInt();
        this.AdvertiserId = in.readInt();
        this.AdvertiserName = in.readString();
        this.Amount = in.readInt();
        this.isShowed = in.readByte() != 0;
        this.url = in.readString();
        this.isSecondClickGoldEnable = in.readByte() != 0;
        this.getGiftRate = in.readInt();
        this.index = in.readInt();
    }

    public static final Parcelable.Creator<AdDetailseBean> CREATOR = new Parcelable.Creator<AdDetailseBean>() {
        @Override
        public AdDetailseBean createFromParcel(Parcel source) {
            return new AdDetailseBean(source);
        }

        @Override
        public AdDetailseBean[] newArray(int size) {
            return new AdDetailseBean[size];
        }
    };
}
