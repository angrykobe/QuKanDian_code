package com.zhangku.qukandian.bean;

import java.util.List;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class RankBean {

    /**
     * mentorUserSumMentee : 3
     * coinAccountSum : 1.5
     * indexNo : 2
     * topList : [{"nickName":"林子","mentorUserSumMentee":2,"coinAccountSum":2,"id":18},{"nickName":"礼物af","mentorUserSumMentee":3,"coinAccountSum":1.5,"id":10036},{"nickName":"dsf","mentorUserSumMentee":0,"coinAccountSum":1,"id":10040},{"mentorUserSumMentee":0,"coinAccountSum":1,"id":10038},{"nickName":"龙王","mentorUserSumMentee":2,"coinAccountSum":1,"id":10026},{"nickName":"趣友20062","mentorUserSumMentee":0,"coinAccountSum":1,"id":20062},{"nickName":"趣友20061","mentorUserSumMentee":0,"coinAccountSum":1,"id":20061},{"nickName":"哼哼","mentorUserSumMentee":0,"coinAccountSum":1,"id":20060},{"nickName":"趣友20059","coinAccountSum":0.5,"id":20059},{"nickName":"趣友20058","coinAccountSum":0.5,"id":20058},{"nickName":"趣友20057","coinAccountSum":0.5,"id":20057},{"nickName":"趣友20056","coinAccountSum":0.5,"id":20056},{"nickName":"趣友20054","coinAccountSum":0.5,"id":20054},{"nickName":"趣友20053","coinAccountSum":0.5,"id":20053},{"nickName":"趣友20052","coinAccountSum":0.5,"id":20052},{"nickName":"趣友20063","id":20063}]
     * id : 10036
     */

    private int mentorUserSumMentee;
    private double coinAccountSum;
    private int indexNo;
    private int id;
    private List<TopListBean> topList;

    public int getMentorUserSumMentee() {
        return mentorUserSumMentee;
    }

    public void setMentorUserSumMentee(int mentorUserSumMentee) {
        this.mentorUserSumMentee = mentorUserSumMentee;
    }

    public double getCoinAccountSum() {
        return coinAccountSum;
    }

    public void setCoinAccountSum(double coinAccountSum) {
        this.coinAccountSum = coinAccountSum;
    }

    public int getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(int indexNo) {
        this.indexNo = indexNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<TopListBean> getTopList() {
        return topList;
    }

    public void setTopList(List<TopListBean> topList) {
        this.topList = topList;
    }

    public class TopListBean {
        /**
         * nickName : 林子
         * mentorUserSumMentee : 2
         * coinAccountSum : 2
         * id : 18
         */

        private String nickName;
        private int mentorUserSumMentee;
        private double coinAccountSum;
        private int id;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getMentorUserSumMentee() {
            return mentorUserSumMentee;
        }

        public void setMentorUserSumMentee(int mentorUserSumMentee) {
            this.mentorUserSumMentee = mentorUserSumMentee;
        }

        public double getCoinAccountSum() {
            return coinAccountSum;
        }

        public void setCoinAccountSum(int coinAccountSum) {
            this.coinAccountSum = coinAccountSum;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
