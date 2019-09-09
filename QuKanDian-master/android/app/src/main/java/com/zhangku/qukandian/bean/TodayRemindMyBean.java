package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/5/26.
 */

public class TodayRemindMyBean {
    /**
     * yesterday : 1
     * eve : 2
     * yesterdayIndex : 3
     * eveIndex : 4
     */

    private int yesterday;
    private int eve;
    private int yesterdayIndex;
    private int eveIndex;

    public int getYesterday() {
        return yesterday;
    }

    public void setYesterday(int yesterday) {
        this.yesterday = yesterday;
    }

    public int getEve() {
        return eve;
    }

    public void setEve(int eve) {
        this.eve = eve;
    }

    public int getYesterdayIndex() {
        return yesterdayIndex;
    }

    public void setYesterdayIndex(int yesterdayIndex) {
        this.yesterdayIndex = yesterdayIndex;
    }

    public int getEveIndex() {
        return eveIndex;
    }

    public void setEveIndex(int eveIndex) {
        this.eveIndex = eveIndex;
    }
}
