package com.zhangku.qukandian.bean;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/13
 * 你不注释一下？
 */
public class EveryDaySignBean {

    /**
     * name : sign_continued
     * displayName : 连续签到
     * missionImages : []
     * missionRules : [{"missionId":11,"name":"d1","displayName":"一天","minCoinAmount":0,"minGoldAmount":100,"expType":0,"isFinished":false,"id":42},{"missionId":11,"name":"d2","displayName":"二天","minCoinAmount":0,"minGoldAmount":200,"expType":0,"isFinished":false,"id":43},{"missionId":11,"name":"d3","displayName":"三天","minCoinAmount":0,"minGoldAmount":300,"expType":0,"isFinished":false,"id":44},{"missionId":11,"name":"d4","displayName":"四天","minCoinAmount":0,"minGoldAmount":400,"expType":0,"isFinished":false,"id":45},{"missionId":11,"name":"d5","displayName":"五天","minCoinAmount":0,"minGoldAmount":500,"expType":0,"isFinished":false,"id":46},{"missionId":11,"name":"d6","displayName":"六天","minCoinAmount":0,"minGoldAmount":600,"expType":0,"isFinished":false,"id":47},{"missionId":11,"name":"d7","displayName":"七天","minCoinAmount":0,"minGoldAmount":700,"expType":0,"isFinished":false,"id":48},{"missionId":11,"name":"d8","displayName":"大于7天","minCoinAmount":0,"minGoldAmount":700,"expType":0,"isFinished":false,"id":49}]
     * description : 第一天签到奖励10金币,连续签到奖励逐日递增，签到最高奖励70金币。签到过程中，如有中断，奖励将从第一天开始重新计算。
     * kindType : 2
     * goldAmountScope : 50-350
     * isVisible : false
     * orderId : 11
     * isButtonEnable : true
     * typeId : 0
     * classifyId : 0
     * isFinished : true
     * id : 11
     */

    private String name;
    private String displayName;
    private String description;
    private int kindType;
    private String goldAmountScope;
    private boolean isVisible;
    private int orderId;
    private boolean isButtonEnable;
    private int typeId;
    private int classifyId;
    private boolean isFinished;//是否已经签到
    private int id;
    private List<?> missionImages;
    private List<MissionRulesBean> missionRules;//每日签到bean

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKindType() {
        return kindType;
    }

    public void setKindType(int kindType) {
        this.kindType = kindType;
    }

    public String getGoldAmountScope() {
        return goldAmountScope;
    }

    public void setGoldAmountScope(String goldAmountScope) {
        this.goldAmountScope = goldAmountScope;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean isIsButtonEnable() {
        return isButtonEnable;
    }

    public void setIsButtonEnable(boolean isButtonEnable) {
        this.isButtonEnable = isButtonEnable;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(int classifyId) {
        this.classifyId = classifyId;
    }

    public boolean isIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<?> getMissionImages() {
        return missionImages;
    }

    public void setMissionImages(List<?> missionImages) {
        this.missionImages = missionImages;
    }

    public List<MissionRulesBean> getMissionRules() {
        return missionRules;
    }

    public void setMissionRules(List<MissionRulesBean> missionRules) {
        this.missionRules = missionRules;
    }

    public static class MissionRulesBean {
        /**
         * missionId : 11
         * name : d1
         * displayName : 一天
         * minCoinAmount : 0.0
         * minGoldAmount : 100
         * expType : 0
         * isFinished : false
         * id : 42
         */

        private int missionId;
        private String name;
        private String displayName;
        private double minCoinAmount;
        private int minGoldAmount;
        private int expType;
        private boolean isFinished;
        private int id;

        public int getMissionId() {
            return missionId;
        }

        public void setMissionId(int missionId) {
            this.missionId = missionId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public double getMinCoinAmount() {
            return minCoinAmount;
        }

        public void setMinCoinAmount(double minCoinAmount) {
            this.minCoinAmount = minCoinAmount;
        }

        public int getMinGoldAmount() {
            return minGoldAmount;
        }

        public void setMinGoldAmount(int minGoldAmount) {
            this.minGoldAmount = minGoldAmount;
        }

        public int getExpType() {
            return expType;
        }

        public void setExpType(int expType) {
            this.expType = expType;
        }

        public boolean isIsFinished() {
            return isFinished;
        }

        public void setIsFinished(boolean isFinished) {
            this.isFinished = isFinished;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
