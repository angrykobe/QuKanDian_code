package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class WithdrawalsRecordBean {
    /**
     * userId : 10001
     * coinGift : {"title":"微信红包20元","coverSrc":"http://sinhol.oss-cn-shenzhen.aliyuncs.com/images/currency/haobao/20%402x.png","fee":20,"isActive":true,"id":1}
     * fee : 20
     * applyStatus : 0
     * statusMemo : 申请中
     * creationTime : 2017-05-11T01:53:49.103
     * id : 12
     */

    private int userId;
    private CoinGiftBean coinGift;
    private double fee;
    private int applyStatus;
    private String statusMemo;
    private String creationTime;
    private int id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CoinGiftBean getCoinGift() {
        return coinGift;
    }

    public void setCoinGift(CoinGiftBean coinGift) {
        this.coinGift = coinGift;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public int getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getStatusMemo() {
        return statusMemo;
    }

    public void setStatusMemo(String statusMemo) {
        this.statusMemo = statusMemo;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public class CoinGiftBean {
        /**
         * title : 微信红包20元
         * coverSrc : http://sinhol.oss-cn-shenzhen.aliyuncs.com/images/currency/haobao/20%402x.png
         * fee : 20
         * isActive : true
         * id : 1
         */

        private String title;
        private String coverSrc;
        private double fee;
        private boolean isActive;
        private int id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoverSrc() {
            return coverSrc;
        }

        public void setCoverSrc(String coverSrc) {
            this.coverSrc = coverSrc;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public boolean isIsActive() {
            return isActive;
        }

        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
