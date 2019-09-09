package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/7/26.
 */

public class RankingBean {

    /**
     * ranking : 0
     * yesterdayGold : 0
     * yesterdayMentee : 0
     */

    private int ranking;
    private int yesterdayGold;
    private int yesterdayMentee;

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getYesterdayGold() {
        return yesterdayGold;
    }

    public void setYesterdayGold(int yesterdayGold) {
        this.yesterdayGold = yesterdayGold;
    }

    public int getYesterdayMentee() {
        return yesterdayMentee;
    }

    public void setYesterdayMentee(int yesterdayMentee) {
        this.yesterdayMentee = yesterdayMentee;
    }
}
