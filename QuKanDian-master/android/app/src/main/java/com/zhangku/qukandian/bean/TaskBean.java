package com.zhangku.qukandian.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/4/7.
 */

public class TaskBean implements Serializable {
    /**
     * name : sample string 1
     * displayName : sample string 2
     * missionImages : [{"src":"sample string 1","orderId":2,"id":3},{"src":"sample string 1","orderId":2,"id":3}]
     * missionRules : [{"missionId":1,"name":"sample string 2","displayName":"sample string 3","minCoinAmount":4,"maxCoinAmount":1,"minGoldAmount":5,"maxGoldAmount":1,"expType":0,"expIndex":1,"isFinished":true,"id":7},{"missionId":1,"name":"sample string 2","displayName":"sample string 3","minCoinAmount":4,"maxCoinAmount":1,"minGoldAmount":5,"maxGoldAmount":1,"expType":0,"expIndex":1,"isFinished":true,"id":7}]
     * description : sample string 3
     * kindType : 1
     * bindingButton : sample string 4
     * coinAmountScope : sample string 5
     * goldAmountScope : sample string 6
     * isVisible : true
     * orderId : 8
     * "isButtonEnable": true,
     * "gotoLink": "sample string 10",
     * "typeId": 11,
     * isFinished : true
     * id : 10
     */

    private boolean isButtonEnable;//是否可以跳转
    private String gotoLink;
    private int typeId;//
    private String name;
    private String displayName;
    private String description = "";
    private int kindType;//4为多任务
    private int classifyId;//1新手 2幸运 3收徒 4阅读
    private String bindingButton;
    private String coinAmountScope;
    private String goldAmountScope;
    private boolean isVisible;
    private int orderId;
    private boolean isFinished;//是否完成任务
    private int id;
    private List<MissionImagesBean> missionImages;
    private List<MissionRulesBean> missionRules;
    ///////289需求
    private int awardsTime;//奖励次数  可完成次数
    private boolean isRepeat;//是否可重复进入
    private String newPeopleInfor;//用户余额满1元且没有提现记录时，显示该引导；如已提现过，不再显示
    //////290
    private int contentHeight;//任务详情内容高度
    private boolean isOpen;//任务详情内容是否已经显示
    /////////291
    private List<MoreMissionBean> moreMission = new ArrayList<>();//多任务情况用到
    private int finishedTime;//已完成次数
    /////292
    private int duration;//任务时长

    /**
     * 1新手 2幸运 3收徒 4阅读
     **/
    public int getClassifyId() {
        return classifyId;
    }

    public int getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(int finishedTime) {
        this.finishedTime = finishedTime;
    }

    public int getAwardsTime() {
        return awardsTime;
    }

    public void setAwardsTime(int awardsTime) {
        this.awardsTime = awardsTime;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setClassifyId(int classifyId) {
        this.classifyId = classifyId;
    }

    public boolean isButtonEnable() {
        return isButtonEnable;
    }

    public void setButtonEnable(boolean buttonEnable) {
        isButtonEnable = buttonEnable;
    }

    public String getGotoLink() {
        return gotoLink;
    }

    public void setGotoLink(String gotoLink) {
        this.gotoLink = gotoLink;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
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

    public String getBindingButton() {
        return bindingButton;
    }

    public void setBindingButton(String bindingButton) {
        this.bindingButton = bindingButton;
    }

    public String getCoinAmountScope() {
        return coinAmountScope;
    }

    public void setCoinAmountScope(String coinAmountScope) {
        this.coinAmountScope = coinAmountScope;
    }

    public String getGoldAmountScope() {
        return goldAmountScope;
    }

    public void setGoldAmountScope(String goldAmountScope) {
        this.goldAmountScope = goldAmountScope;
    }

    public List<MoreMissionBean> getMoreMission() {
        return moreMission;
    }

    public void setMoreMission(List<MoreMissionBean> moreMission) {
        this.moreMission = moreMission;
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

    public List<MissionImagesBean> getMissionImages() {
        return missionImages;
    }

    public void setMissionImages(List<MissionImagesBean> missionImages) {
        this.missionImages = missionImages;
    }

    public List<MissionRulesBean> getMissionRules() {
        return missionRules;
    }

    public void setMissionRules(List<MissionRulesBean> missionRules) {
        this.missionRules = missionRules;
    }

    public String getNewPeopleInfor() {
        return newPeopleInfor;
    }

    public void setNewPeopleInfor(String newPeopleInfor) {
        this.newPeopleInfor = newPeopleInfor;
    }

    public int getContentHeight() {
        return contentHeight;
    }

    public void setContentHeight(int contentHeight) {
        this.contentHeight = contentHeight;
    }

    public class MissionImagesBean implements Serializable {
        /**
         * src : sample string 1
         * orderId : 2
         * id : 3
         */

        private String src;
        private int orderId;
        private int id;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public class MissionRulesBean implements Serializable {
        /**
         * missionId : 1
         * name : sample string 2
         * displayName : sample string 3
         * minCoinAmount : 4.0
         * maxCoinAmount : 1.0
         * minGoldAmount : 5
         * maxGoldAmount : 1
         * expType : 0
         * expIndex : 1
         * isFinished : true
         * id : 7
         */

        private int missionId;
        private String name;
        private String displayName;
        private double minCoinAmount;
        private double maxCoinAmount;
        private int minGoldAmount;
        private int maxGoldAmount;
        private int expType;
        private int expIndex;
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

        public double getMaxCoinAmount() {
            return maxCoinAmount;
        }

        public void setMaxCoinAmount(double maxCoinAmount) {
            this.maxCoinAmount = maxCoinAmount;
        }

        public int getMinGoldAmount() {
            return minGoldAmount;
        }

        public void setMinGoldAmount(int minGoldAmount) {
            this.minGoldAmount = minGoldAmount;
        }

        public int getMaxGoldAmount() {
            return maxGoldAmount;
        }

        public void setMaxGoldAmount(int maxGoldAmount) {
            this.maxGoldAmount = maxGoldAmount;
        }

        public int getExpType() {
            return expType;
        }

        public void setExpType(int expType) {
            this.expType = expType;
        }

        public int getExpIndex() {
            return expIndex;
        }

        public void setExpIndex(int expIndex) {
            this.expIndex = expIndex;
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

    public static class MoreMissionBean implements Serializable {
        private String orderId;
        private String link;
        private String gotoLink;
        private String bindingButton;
        private String adsName;
        private String threshold;
        private int awardsTime;//总次数
        private int finishedTime;//已完成次数
        private boolean isSecondClickEnable;
        private int duration;
        private List<MissionClickRulesBean> missionClickRules;

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getGotoLink() {
            return gotoLink;
        }

        public void setGotoLink(String gotoLink) {
            this.gotoLink = gotoLink;
        }

        public String getBindingButton() {
            return bindingButton;
        }

        public void setBindingButton(String bindingButton) {
            this.bindingButton = bindingButton;
        }

        public String getAdsName() {
            return adsName;
        }

        public void setAdsName(String adsName) {
            this.adsName = adsName;
        }

        public String getThreshold() {
            return threshold;
        }

        public void setThreshold(String threshold) {
            this.threshold = threshold;
        }

        public int getAwardsTime() {
            return awardsTime;
        }

        public void setAwardsTime(int awardsTime) {
            this.awardsTime = awardsTime;
        }

        public int getFinishedTime() {
            return finishedTime;
        }

        public void setFinishedTime(int finishedTime) {
            this.finishedTime = finishedTime;
        }

        public boolean getIsSecondClickEnable() {
            return isSecondClickEnable;
        }

        public void setIsSecondClickEnable(boolean isSecondClickEnable) {
            this.isSecondClickEnable = isSecondClickEnable;
        }

        public List<MissionClickRulesBean> getMissionClickRules() {
            return missionClickRules;
        }

        public void setMissionClickRules(List<MissionClickRulesBean> missionClickRules) {
            this.missionClickRules = missionClickRules;
        }

        public static class MissionClickRulesBean implements Serializable {
            private int clickCount;
            private String duration;
            private double presentRate;

            public int getClickCount() {
                return clickCount;
            }

            public void setClickCount(int clickCount) {
                this.clickCount = clickCount;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public double getPresentRate() {
                return presentRate;
            }

            public void setPresentRate(double presentRate) {
                this.presentRate = presentRate;
            }
        }
    }

}
