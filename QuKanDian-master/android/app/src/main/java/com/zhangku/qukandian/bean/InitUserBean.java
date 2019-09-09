package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2018/1/12.
 */

public class InitUserBean {
    private long Id;
    private int UserAdsType;
    private int Count;

    public InitUserBean(int id, int userAdsType, int count) {
        Id = id;
        UserAdsType = userAdsType;
        Count = count;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public int getUserAdsType() {
        return UserAdsType;
    }

    public void setUserAdsType(int userAdsType) {
        UserAdsType = userAdsType;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
