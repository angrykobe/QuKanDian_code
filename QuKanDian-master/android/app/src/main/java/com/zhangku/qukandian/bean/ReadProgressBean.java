package com.zhangku.qukandian.bean;

import java.util.ArrayList;

//阅读进度
public class ReadProgressBean {
    private int adsProgressCnt;// 已完成广告红包数
    private ArrayList<ProgressRuleBean> progressRule;

    public int getAdsProgressCnt() {
        return adsProgressCnt;
    }

    public void setAdsProgressCnt(int adsProgressCnt) {
        this.adsProgressCnt = adsProgressCnt;
    }

    public ArrayList<ProgressRuleBean> getProgressRule() {
        return progressRule;
    }

    public void setProgressRule(ArrayList<ProgressRuleBean> progressRule) {
        this.progressRule = progressRule;
    }

    public class ProgressRuleBean{
        private int orderId;// 排序
        private int adsCnt;// 需完成广告红包数
        private int gold;// 奖励金币数

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getAdsCnt() {
            return adsCnt;
        }

        public void setAdsCnt(int adsCnt) {
            this.adsCnt = adsCnt;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }
    }
}
