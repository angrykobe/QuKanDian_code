package com.zhangku.qukandian.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2018/2/3.
 */

public class RuleBean {

    /**
     * sougouGoldRule : {"goldMin":1,"goldMax":5,"dayMaxCount":6,"hourMaxCount":10,"isEnable":true,"pidList":[],"timeRate":{"time":0,"rate":0}}
     * adsClickGiftRule : {"singleUserDayMaxCount":30,"getGiftRate":100,"getGiftDuration":5,"giftPresentRate":100,"goldGradeLimit":50,"isNeedReadingFirst":false,"userContributeStandard":2,"scGetGiftDuration":10,"scGetGiftRate":100,"scGetGiftRateNoClick":0}
     * adsClickInductionRule : {"firstInTime":1,"newbieDay":2,"scTipLastDuration":5,"scAdsTip":"在页面点选感兴趣的内容并认真阅读一段时间，才更有机会获得金币","scAdsGotGoldFailedTip":"在当前页面中选择感兴趣任意内容并点击阅读，才更有机会获得金币","newbieTip":"即将获得金币奖励，认真选择感兴趣的内容并点击阅读，才更有机会获得金币"}
     * newbieConfig : {"statusForLogin":1,"statusForUnlogin":1,"toastPriority":1}
     * bottomIconConfig : {"statusForLogin":0,"statusForUnlogin":0,"icon":"http://cdn.qu.fi.pqmnz.com/test/img/bottomicons/bottomicon.png","statusIcon":"http://cdn.qu.fi.pqmnz.com/backend/images/20180206/c9206555b6fa408bb6e5637d90929353_1517897964.gif","gotoLink":"http://www.sunyungou.com/severbusy/"}
     * operationConfig : {"statusForLogin":0,"statusForUnlogin":0,"toastLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180208/6803d226ac8a427cb8da0d2b8eda3f83_1518077942.png","toastGotoLink":"http://www.sunyungou.com/severbusy/","isShowThumbNail":false,"thumbNailImage":"http://cdn.qu.fi.pqmnz.com/backend/images/20180301/4ae874b94a0640edb6ca176af1e97fb0_1519895803.png","lastTime":3,"lastDay":14,"toastPriority":0}
     * answerQuesConfig : {"shoutuPosterImage":"http://cdn.qu.fi.pqmnz.com/backend/images/20180212/3e70e4f5f49042f4b826216d74c3b385_1518417867.jpg","hasGoto":false,"shoutuPosterText":"海量头条资讯，有趣有料，账。 我的邀请码：{yqm}","shoutuPosterDesc":"设置或返回当浏览器不支持文本域时供显示的替代文本。","qqType":"1","wechatType":"1","shareFriendType":"1","qqSpaceType":"1"}
     * shoutuPosterConfig : {"shareFriendText":"免费activeShoutuConfig送你的好福利，免费看资讯能赚零花，可以直接提现哦～立刻扫码加入，多重惊喜等你领，填我邀请码{yqm}","shareIncomeImageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180208/363614889ea648339a5a3ebb0782ba78_1518090023.png","wechatIcon":"http://cdn.qu.fi.pqmnz.com/backend/images/20180210/632590973010431cb197c72c408aab83_1518228971.png","wechatTitle":"趣看视界，边看新闻边挣零花钱，赶快来下载！","wechatDesc":"不是优惠，是真现金！","shareFrientPosterItems":[{"order":1,"imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180212/2d1020bb26924208928f213d5545347b_1518407441.png"}]}
     * inductIconConfig : {"statusForLogin":1,"statusForUnlogin":1,"icon":"http://cdn.qu.fi.pqmnz.com/backend/images/20180301/4ae874b94a0640edb6ca176af1e97fb0_1519895803.png","gotoLink":"http://www.sunyungou.com/severbusy/","lastTime":1,"lastDay":1}
     * redIconConfigs : [{"type":0,"isShow":true,"lastTime":1,"lastDay":1},{"type":1,"isShow":true,"lastTime":1,"lastDay":1}]
     * activeShoutuConfig : {"qqType":"1","qqSpaceType":"1","wechatType":"1","shareFriendType":"1","wechatIcon":"http://cdn.qu.fi.pqmnz.com/backend/images/20180203/6c1a05cda0a145b69a4bbf701eba83be_1517629088.jpg","wechatTitle":"分享收徒赚红包","wechatDesc":"好看的新闻欢迎大家分享","shareFriendText":"收徒活动分享到朋友圈文案{yqm}","shareFriendDesc":"好看的新闻欢迎大家分享{yqm}","shareFrientPosters":[{"order":1,"imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180205/abe8fc2a61bd4eb38e614c62727d1ea3_1517826735.png"},{"order":2,"imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180206/1a637ee32df04cea81ef27a38a3f9b33_1517903310.jpg"},{"order":3,"imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180206/021b311920ec41f39d0bae65e0c0bb95_1517903430.jpg"},{"order":4,"imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180206/239e7f0dffe94e368dbb84da84893c36_1517903433.jpg"},{"order":5,"imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180206/b0581baa11b24cea81c4f794a8c46772_1517903439.jpg"},{"order":6,"imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180206/1ef56cf221de454eb7ed6c0d9f1c400b_1517903441.jpg"},{"order":7,"imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180206/afa1890e4eb84c7f919d98de5e60b4b1_1517903444.jpg"},{"order":8,"imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180206/d7a2e2135b3c43aba180f8938f9b9c40_1517903446.jpg"},{"order":9,"imageLink":"http://cdn.qu.fi.pqmnz.com/prod/img/201802/9139bce2f16b4876830d583d14d043ca.jpg"}]}
     */

    private MyDialogBean myPageToast = new MyDialogBean();
    private SougouGoldRuleBean sougouGoldRule = new SougouGoldRuleBean();
    private AdsClickGiftRuleBean adsClickGiftRule = new AdsClickGiftRuleBean();
    private AdsClickInductionRuleBean adsClickInductionRule = new AdsClickInductionRuleBean();
    private NewbieConfigBean newbieConfig = new NewbieConfigBean();//新手红包的配置
    private BottomIconConfigBean bottomIconConfig = new BottomIconConfigBean();//
    private OperationConfigBean operationConfig = new OperationConfigBean();//好像是海报活动的样子
    private InductIconConfigBean inductIconConfig = new InductIconConfigBean();//?? 小icon信息
    private ShoutuPosterConfigBean shoutuPosterConfig = new ShoutuPosterConfigBean();
    private List<RedIconConfigsBean> redIconConfigs = new ArrayList<>();//
    private ActiveShoutuConfigBean activeShoutuConfig = new ActiveShoutuConfigBean();
    private List<XcxShareConfigItemBean> xcxShareConfig = new ArrayList<>();
    private List<ReadTipsBean> readTipList = new ArrayList();

    @Override
    public String toString() {
        return "RuleBean{" +
                "sougouGoldRule=" + sougouGoldRule +
                ", adsClickGiftRule=" + adsClickGiftRule +
                ", adsClickInductionRule=" + adsClickInductionRule +
                ", newbieConfig=" + newbieConfig +
                ", bottomIconConfig=" + bottomIconConfig +
                ", operationConfig=" + operationConfig +
                ", shoutuPosterConfig=" + shoutuPosterConfig +
                ", inductIconConfig=" + inductIconConfig +
                ", redIconConfigs=" + redIconConfigs +
                ", activeShoutuConfig=" + activeShoutuConfig +
                ", xcxShareConfig=" + xcxShareConfig +
                ", readTipList=" + readTipList +
                '}';
    }

    public MyDialogBean getMyPageToast() {
        return myPageToast;
    }

    public void setMyPageToast(MyDialogBean myPageToast) {
        this.myPageToast = myPageToast;
    }

    public List<XcxShareConfigItemBean> getXcxShareConfig() {
        return xcxShareConfig;
    }

    public SougouGoldRuleBean getSougouGoldRule() {
        return sougouGoldRule;
    }

    public void setSougouGoldRule(SougouGoldRuleBean sougouGoldRule) {
        this.sougouGoldRule = sougouGoldRule;
    }

    public List<ReadTipsBean> getReadTipList() {
        return readTipList;
    }

    public void setReadTipList(List<ReadTipsBean> readTipList) {
        this.readTipList = readTipList;
    }

    public AdsClickGiftRuleBean getAdsClickGiftRule() {
        return adsClickGiftRule;
    }

    public void setAdsClickGiftRule(AdsClickGiftRuleBean adsClickGiftRule) {
        this.adsClickGiftRule = adsClickGiftRule;
    }

    public AdsClickInductionRuleBean getAdsClickInductionRule() {
        return adsClickInductionRule;
    }

    public void setAdsClickInductionRule(AdsClickInductionRuleBean adsClickInductionRule) {
        this.adsClickInductionRule = adsClickInductionRule;
    }

    public NewbieConfigBean getNewbieConfig() {
        return newbieConfig;
    }

    public void setNewbieConfig(NewbieConfigBean newbieConfig) {
        this.newbieConfig = newbieConfig;
    }

    public BottomIconConfigBean getBottomIconConfig() {
        return bottomIconConfig;
    }

    public void setBottomIconConfig(BottomIconConfigBean bottomIconConfig) {
        this.bottomIconConfig = bottomIconConfig;
    }

    public OperationConfigBean getOperationConfig() {
        return operationConfig;
    }

    public void setOperationConfig(OperationConfigBean operationConfig) {
        this.operationConfig = operationConfig;
    }

    public ShoutuPosterConfigBean getShoutuPosterConfig() {
        return shoutuPosterConfig;
    }

    public void setShoutuPosterConfig(ShoutuPosterConfigBean shoutuPosterConfig) {
        this.shoutuPosterConfig = shoutuPosterConfig;
    }

    public InductIconConfigBean getInductIconConfig() {
        return inductIconConfig;
    }

    public void setInductIconConfig(InductIconConfigBean inductIconConfig) {
        this.inductIconConfig = inductIconConfig;
    }

    public List<RedIconConfigsBean> getRedIconConfigs() {
        return redIconConfigs;
    }

    public void setRedIconConfigs(List<RedIconConfigsBean> redIconConfigs) {
        this.redIconConfigs = redIconConfigs;
    }

    public ActiveShoutuConfigBean getActiveShoutuConfig() {
        return activeShoutuConfig;
    }

    public void setActiveShoutuConfig(ActiveShoutuConfigBean activeShoutuConfig) {
        this.activeShoutuConfig = activeShoutuConfig;
    }

    /**
     * 后台管理系统中的搜狗广告配置类
     */
    public static class SougouGoldRuleBean {
        /**
         * goldMin : 1   红包最小
         * goldMax : 5   红包最大
         * dayMaxCount : 6   一天获取最大红包数
         * hourMaxCount : 10  一小时
         * isEnable : true  是否打开热搜
         * pidList : []
         * timeRate : {"time":0,"rate":0}
         */

        private int goldMin;
        private int goldMax;
        private int dayMaxCount;
        private int hourMaxCount;
        private boolean isEnable;
        private TimeRateBean timeRate;
        private List<?> pidList;
        private int duration;
        private int dpt;
        private int dptMax;

        public int getDptMax() {
            return dptMax;
        }

        public void setDptMax(int dptMax) {
            this.dptMax = dptMax;
        }

        public int getDPT() {
            return dpt;
        }

        public void setDPT(int DPT) {
            this.dpt = DPT;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        @Override
        public String toString() {
            return "SougouGoldRuleBean{" +
                    "goldMin=" + goldMin +
                    ", goldMax=" + goldMax +
                    ", dayMaxCount=" + dayMaxCount +
                    ", hourMaxCount=" + hourMaxCount +
                    ", isEnable=" + isEnable +
                    ", timeRate=" + timeRate +
                    ", pidList=" + pidList +
                    '}';
        }

        public boolean isEnable() {
            return isEnable;
        }

        public void setEnable(boolean enable) {
            isEnable = enable;
        }

        public int getGoldMin() {
            return goldMin;
        }

        public void setGoldMin(int goldMin) {
            this.goldMin = goldMin;
        }

        public int getGoldMax() {
            return goldMax;
        }

        public void setGoldMax(int goldMax) {
            this.goldMax = goldMax;
        }

        public int getDayMaxCount() {
            return dayMaxCount;
        }

        public void setDayMaxCount(int dayMaxCount) {
            this.dayMaxCount = dayMaxCount;
        }

        public int getHourMaxCount() {
            return hourMaxCount;
        }

        public void setHourMaxCount(int hourMaxCount) {
            this.hourMaxCount = hourMaxCount;
        }

        public boolean isIsEnable() {
            return isEnable;
        }

        public void setIsEnable(boolean isEnable) {
            this.isEnable = isEnable;
        }

        public TimeRateBean getTimeRate() {
            return timeRate;
        }

        public void setTimeRate(TimeRateBean timeRate) {
            this.timeRate = timeRate;
        }

        public List<?> getPidList() {
            return pidList;
        }

        public void setPidList(List<?> pidList) {
            this.pidList = pidList;
        }

        public static class TimeRateBean {
            @Override
            public String toString() {
                return "TimeRateBean{" +
                        "time=" + time +
                        ", rate=" + rate +
                        '}';
            }

            /**
             * time : 0
             * rate : 0
             */

            private int time;
            private int rate;

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public int getRate() {
                return rate;
            }

            public void setRate(int rate) {
                this.rate = rate;
            }
        }
    }

    public static class AdsClickGiftRuleBean {
        /**
         * 一次点击
         * singleUserDayMaxCount : 30
         * getGiftRate : 100
         * getGiftDuration : 5
         * giftPresentRate : 100
         * goldGradeLimit : 50   预值
         * isNeedReadingFirst : false  有效阅读
         * 2次点击
         * userContributeStandard : 2
         * scGetGiftDuration : 10
         * scGetGiftRate : 100
         * scGetGiftRateNoClick : 0
         */

        private int singleUserDayMaxCount;//广告点击红包最大1天领取数量  (作废 2.8.8 现在由客户端计算)
        private int getGiftRate;//
        private int getGiftDuration;////  无需二次点击类广告有效阅读时间
        private int giftPresentRate;//
        private int goldGradeLimit;//升级到金色红包的额度
        private boolean isNeedReadingFirst;// 有效阅读
        private int userContributeStandard;//用户贡献度标准值??不确定
        private int scGetGiftDuration;//需二次点击类广告有效阅读时间
        private int scGetGiftRate;
        private int scGetGiftRateNoClick;//

        public boolean isNeedReadingFirst() {
            return isNeedReadingFirst;
        }

        public void setNeedReadingFirst(boolean needReadingFirst) {
            isNeedReadingFirst = needReadingFirst;
        }

        public int getSingleUserDayMaxCount() {
            return singleUserDayMaxCount;
        }

        public void setSingleUserDayMaxCount(int singleUserDayMaxCount) {
            this.singleUserDayMaxCount = singleUserDayMaxCount;
        }

        public int getGetGiftRate() {
            return getGiftRate;
        }

        public void setGetGiftRate(int getGiftRate) {
            this.getGiftRate = getGiftRate;
        }

        public int getGetGiftDuration() {
            return getGiftDuration;
        }

        public void setGetGiftDuration(int getGiftDuration) {
            this.getGiftDuration = getGiftDuration;
        }

        public int getGiftPresentRate() {
            return giftPresentRate;
        }

        public void setGiftPresentRate(int giftPresentRate) {
            this.giftPresentRate = giftPresentRate;
        }

        public int getGoldGradeLimit() {
            return goldGradeLimit;
        }

        public void setGoldGradeLimit(int goldGradeLimit) {
            this.goldGradeLimit = goldGradeLimit;
        }

        public boolean isIsNeedReadingFirst() {
            return isNeedReadingFirst;
        }

        public void setIsNeedReadingFirst(boolean isNeedReadingFirst) {
            this.isNeedReadingFirst = isNeedReadingFirst;
        }

        public int getUserContributeStandard() {
            return userContributeStandard;
        }

        public void setUserContributeStandard(int userContributeStandard) {
            this.userContributeStandard = userContributeStandard;
        }

        public int getScGetGiftDuration() {
            return scGetGiftDuration;
        }

        public void setScGetGiftDuration(int scGetGiftDuration) {
            this.scGetGiftDuration = scGetGiftDuration;
        }

        public int getScGetGiftRate() {
            return scGetGiftRate;
        }

        public void setScGetGiftRate(int scGetGiftRate) {
            this.scGetGiftRate = scGetGiftRate;
        }

        public int getScGetGiftRateNoClick() {
            return scGetGiftRateNoClick;
        }

        public void setScGetGiftRateNoClick(int scGetGiftRateNoClick) {
            this.scGetGiftRateNoClick = scGetGiftRateNoClick;
        }

        @Override
        public String toString() {
            return "AdsClickGiftRuleBean{" +
                    "singleUserDayMaxCount=" + singleUserDayMaxCount +
                    ", getGiftRate=" + getGiftRate +
                    ", getGiftDuration=" + getGiftDuration +
                    ", giftPresentRate=" + giftPresentRate +
                    ", goldGradeLimit=" + goldGradeLimit +
                    ", isNeedReadingFirst=" + isNeedReadingFirst +
                    ", userContributeStandard=" + userContributeStandard +
                    ", scGetGiftDuration=" + scGetGiftDuration +
                    ", scGetGiftRate=" + scGetGiftRate +
                    ", scGetGiftRateNoClick=" + scGetGiftRateNoClick +
                    '}';
        }
    }

    /**
     * 后台管理系统中的引导配置对应类
     */
    public static class AdsClickInductionRuleBean {
        @Override
        public String toString() {
            return "AdsClickInductionRuleBean{" +
                    "firstInTime=" + firstInTime +
                    ", newbieDay=" + newbieDay +
                    ", scTipLastDuration=" + scTipLastDuration +
                    ", scAdsTip='" + scAdsTip + '\'' +
                    ", scAdsGotGoldFailedTip='" + scAdsGotGoldFailedTip + '\'' +
                    ", newbieTip='" + newbieTip + '\'' +
                    '}';
        }

        /**
         * firstInTime : 1
         * newbieDay : 2
         * scTipLastDuration : 5
         * scAdsTip : 在页面点选感兴趣的内容并认真阅读一段时间，才更有机会获得金币
         * scAdsGotGoldFailedTip : 在当前页面中选择感兴趣任意内容并点击阅读，才更有机会获得金币
         * newbieTip : 即将获得金币奖励，认真选择感兴趣的内容并点击阅读，才更有机会获得金币
         */

        private int firstInTime;//次定义为初体验用户，进入二次点击红包广告，进行新手引导提示
        private int newbieDay;//天定义为新手用户，进入二次点击红包广告，进行广告点击引导
        private int scTipLastDuration;//二次点击类广告提示框显示时长：
        private String scAdsTip;//用户进入二次点击类广告红包页面-提示语：
        private String scAdsGotGoldFailedTip;//用户在二次点击类广告，获取金币失败时-提示语：
        private String newbieTip;//新手用户引导-提示语
        private int firstInTimeRead;//

        public int getFirstInTimeRead() {
            return firstInTimeRead;
        }

        public void setFirstInTimeRead(int firstInTimeRead) {
            this.firstInTimeRead = firstInTimeRead;
        }

        public int getFirstInTime() {
            return firstInTime;
        }

        public void setFirstInTime(int firstInTime) {
            this.firstInTime = firstInTime;
        }

        public int getNewbieDay() {
            return newbieDay;
        }

        public void setNewbieDay(int newbieDay) {
            this.newbieDay = newbieDay;
        }

        public int getScTipLastDuration() {
            return scTipLastDuration;
        }

        public void setScTipLastDuration(int scTipLastDuration) {
            this.scTipLastDuration = scTipLastDuration;
        }

        public String getScAdsTip() {
            return scAdsTip;
        }

        public void setScAdsTip(String scAdsTip) {
            this.scAdsTip = scAdsTip;
        }

        public String getScAdsGotGoldFailedTip() {
            return scAdsGotGoldFailedTip;
        }

        public void setScAdsGotGoldFailedTip(String scAdsGotGoldFailedTip) {
            this.scAdsGotGoldFailedTip = scAdsGotGoldFailedTip;
        }

        public String getNewbieTip() {
            return newbieTip;
        }

        public void setNewbieTip(String newbieTip) {
            this.newbieTip = newbieTip;
        }
    }

    /**
     * ？？？
     */
    public static class NewbieConfigBean {
        @Override
        public String toString() {
            return "NewbieConfigBean{" +
                    "statusForLogin=" + statusForLogin +
                    ", statusForUnlogin=" + statusForUnlogin +
                    ", toastPriority=" + toastPriority +
                    '}';
        }

        /**
         * statusForLogin : 1
         * statusForUnlogin : 1
         * toastPriority : 1
         */

        private int statusForLogin;
        private int statusForUnlogin;
        private int toastPriority;

        public int getStatusForLogin() {
            return statusForLogin;
        }

        public void setStatusForLogin(int statusForLogin) {
            this.statusForLogin = statusForLogin;
        }

        public int getStatusForUnlogin() {
            return statusForUnlogin;
        }

        public void setStatusForUnlogin(int statusForUnlogin) {
            this.statusForUnlogin = statusForUnlogin;
        }

        public int getToastPriority() {
            return toastPriority;
        }

        public void setToastPriority(int toastPriority) {
            this.toastPriority = toastPriority;
        }
    }

    /**
     * 底部中间图标
     */
    public static class BottomIconConfigBean {
        @Override
        public String toString() {
            return "BottomIconConfigBean{" +
                    "statusForLogin=" + statusForLogin +
                    ", statusForUnlogin=" + statusForUnlogin +
                    ", icon='" + icon + '\'' +
                    ", statusIcon='" + statusIcon + '\'' +
                    ", gotoLink='" + gotoLink + '\'' +
                    '}';
        }

        /**
         * statusForLogin : 0
         * statusForUnlogin : 0
         * icon : http://cdn.qu.fi.pqmnz.com/test/img/bottomicons/bottomicon.png
         * statusIcon : http://cdn.qu.fi.pqmnz.com/backend/images/20180206/c9206555b6fa408bb6e5637d90929353_1517897964.gif
         * gotoLink : http://www.sunyungou.com/severbusy/
         */

        private int statusForLogin;
        private int statusForUnlogin;
        private String icon;
        private String statusIcon;
        private String gotoLink;

        public int getStatusForLogin() {
            return statusForLogin;
        }

        public void setStatusForLogin(int statusForLogin) {
            this.statusForLogin = statusForLogin;
        }

        public int getStatusForUnlogin() {
            return statusForUnlogin;
        }

        public void setStatusForUnlogin(int statusForUnlogin) {
            this.statusForUnlogin = statusForUnlogin;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getStatusIcon() {
            return statusIcon;
        }

        public void setStatusIcon(String statusIcon) {
            this.statusIcon = statusIcon;
        }

        public String getGotoLink() {
            return gotoLink;
        }

        public void setGotoLink(String gotoLink) {
            this.gotoLink = gotoLink;
        }
    }

    /**
     * 专属福利红包（弹框红包）
     */
    public static class OperationConfigBean {
        @Override
        public String toString() {
            return "OperationConfigBean{" +
                    "statusForLogin=" + statusForLogin +
                    ", statusForUnlogin=" + statusForUnlogin +
                    ", toastLink='" + toastLink + '\'' +
                    ", toastGotoLink='" + toastGotoLink + '\'' +
                    ", isShowThumbNail=" + isShowThumbNail +
                    ", thumbNailImage='" + thumbNailImage + '\'' +
                    ", lastTime=" + lastTime +
                    ", lastDay=" + lastDay +
                    ", toastPriority=" + toastPriority +
                    '}';
        }

        /**
         * statusForLogin : 0
         * statusForUnlogin : 0
         * toastLink : http://cdn.qu.fi.pqmnz.com/backend/images/20180208/6803d226ac8a427cb8da0d2b8eda3f83_1518077942.png
         * toastGotoLink : http://www.sunyungou.com/severbusy/
         * isShowThumbNail : false
         * thumbNailImage : http://cdn.qu.fi.pqmnz.com/backend/images/20180301/4ae874b94a0640edb6ca176af1e97fb0_1519895803.png
         * lastTime : 3
         * lastDay : 14
         * toastPriority : 0
         */

        private int statusForLogin;
        private int statusForUnlogin;
        private String toastLink;
        private String toastGotoLink;
        private boolean isShowThumbNail;
        private String thumbNailImage;
        private int lastTime;
        private int lastDay;
        private int toastPriority;

        public boolean isShowThumbNail() {
            return isShowThumbNail;
        }

        public void setShowThumbNail(boolean showThumbNail) {
            isShowThumbNail = showThumbNail;
        }

        public int getStatusForLogin() {
            return statusForLogin;
        }

        public void setStatusForLogin(int statusForLogin) {
            this.statusForLogin = statusForLogin;
        }

        public int getStatusForUnlogin() {
            return statusForUnlogin;
        }

        public void setStatusForUnlogin(int statusForUnlogin) {
            this.statusForUnlogin = statusForUnlogin;
        }

        public String getToastLink() {
            return toastLink;
        }

        public void setToastLink(String toastLink) {
            this.toastLink = toastLink;
        }

        public String getToastGotoLink() {
            return toastGotoLink;
        }

        public void setToastGotoLink(String toastGotoLink) {
            this.toastGotoLink = toastGotoLink;
        }

        public boolean isIsShowThumbNail() {
            return isShowThumbNail;
        }

        public void setIsShowThumbNail(boolean isShowThumbNail) {
            this.isShowThumbNail = isShowThumbNail;
        }

        public String getThumbNailImage() {
            return thumbNailImage;
        }

        public void setThumbNailImage(String thumbNailImage) {
            this.thumbNailImage = thumbNailImage;
        }

        public int getLastTime() {
            return lastTime;
        }

        public void setLastTime(int lastTime) {
            this.lastTime = lastTime;
        }

        public int getLastDay() {
            return lastDay;
        }

        public void setLastDay(int lastDay) {
            this.lastDay = lastDay;
        }

        public int getToastPriority() {
            return toastPriority;
        }

        public void setToastPriority(int toastPriority) {
            this.toastPriority = toastPriority;
        }
    }

    /**
     * ？？？？？
     */
    public static class AnswerQuesConfigBean {
        @Override
        public String toString() {
            return "AnswerQuesConfigBean{" +
                    "shoutuPosterImage='" + shoutuPosterImage + '\'' +
                    ", hasGoto=" + hasGoto +
                    ", shoutuPosterText='" + shoutuPosterText + '\'' +
                    ", shoutuPosterDesc='" + shoutuPosterDesc + '\'' +
                    ", qqType='" + qqType + '\'' +
                    ", wechatType='" + wechatType + '\'' +
                    ", shareFriendType='" + shareFriendType + '\'' +
                    ", qqSpaceType='" + qqSpaceType + '\'' +
                    '}';
        }

        /**
         * shoutuPosterImage : http://cdn.qu.fi.pqmnz.com/backend/images/20180212/3e70e4f5f49042f4b826216d74c3b385_1518417867.jpg
         * hasGoto : false
         * shoutuPosterText : 海量头条资讯，有趣有料，账。 我的邀请码：{yqm}
         * shoutuPosterDesc : 设置或返回当浏览器不支持文本域时供显示的替代文本。
         * qqType : 1
         * wechatType : 1
         * shareFriendType : 1
         * qqSpaceType : 1
         */

        private String shoutuPosterImage;
        private boolean hasGoto;
        private String shoutuPosterText;
        private String shoutuPosterDesc;
        private String qqType;
        private String wechatType;
        private String shareFriendType;
        private String qqSpaceType;

        public String getShoutuPosterImage() {
            return shoutuPosterImage;
        }

        public void setShoutuPosterImage(String shoutuPosterImage) {
            this.shoutuPosterImage = shoutuPosterImage;
        }

        public boolean isHasGoto() {
            return hasGoto;
        }

        public void setHasGoto(boolean hasGoto) {
            this.hasGoto = hasGoto;
        }

        public String getShoutuPosterText() {
            return shoutuPosterText;
        }

        public void setShoutuPosterText(String shoutuPosterText) {
            this.shoutuPosterText = shoutuPosterText;
        }

        public String getShoutuPosterDesc() {
            return shoutuPosterDesc;
        }

        public void setShoutuPosterDesc(String shoutuPosterDesc) {
            this.shoutuPosterDesc = shoutuPosterDesc;
        }

        public String getQqType() {
            return qqType;
        }

        public void setQqType(String qqType) {
            this.qqType = qqType;
        }

        public String getWechatType() {
            return wechatType;
        }

        public void setWechatType(String wechatType) {
            this.wechatType = wechatType;
        }

        public String getShareFriendType() {
            return shareFriendType;
        }

        public void setShareFriendType(String shareFriendType) {
            this.shareFriendType = shareFriendType;
        }

        public String getQqSpaceType() {
            return qqSpaceType;
        }

        public void setQqSpaceType(String qqSpaceType) {
            this.qqSpaceType = qqSpaceType;
        }
    }

    public static class ShoutuPosterConfigBean {
        @Override
        public String toString() {
            return "ShoutuPosterConfigBean{" +
                    "shareFriendText='" + shareFriendText + '\'' +
                    ", shareIncomeImageLink='" + shareIncomeImageLink + '\'' +
                    ", wechatIcon='" + wechatIcon + '\'' +
                    ", wechatTitle='" + wechatTitle + '\'' +
                    ", wechatDesc='" + wechatDesc + '\'' +
                    ", shareFrientPosterItems=" + shareFrientPosterItems +
                    '}';
        }

        /**
         * shareFriendText : 免费送你的好福利，免费看资讯能赚零花，可以直接提现哦～立刻扫码加入，多重惊喜等你领，填我邀请码{yqm}
         * shareIncomeImageLink : http://cdn.qu.fi.pqmnz.com/backend/images/20180208/363614889ea648339a5a3ebb0782ba78_1518090023.png
         * wechatIcon : http://cdn.qu.fi.pqmnz.com/backend/images/20180210/632590973010431cb197c72c408aab83_1518228971.png
         * wechatTitle : 趣看视界，边看新闻边挣零花钱，赶快来下载！
         * wechatDesc : 不是优惠，是真现金！
         * shareFrientPosterItems : [{"order":1,"imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180212/2d1020bb26924208928f213d5545347b_1518407441.png"}]
         */

        private String shareFriendText;
        private String shareIncomeImageLink;
        private String wechatIcon;
        private String wechatTitle;
        private String wechatDesc;
        private List<ShareFrientPosterItemsBean> shareFrientPosterItems;

        private int shareFriendType; // 0链接分享 1 海报分享
        private int qqType;  // 0链接分享 1 海报分享
        private int wechatType;// 0链接分享 1 海报分享
        private int qqSpaceType;// 0链接分享 1 海报分享

        public int getShareFriendType() {
            return shareFriendType;
        }

        public void setShareFriendType(int shareFriendType) {
            this.shareFriendType = shareFriendType;
        }

        public int getQqType() {
            return qqType;
        }

        public void setQqType(int qqType) {
            this.qqType = qqType;
        }

        public int getWechatType() {
            return wechatType;
        }

        public void setWechatType(int wechatType) {
            this.wechatType = wechatType;
        }

        public int getQqSpaceType() {
            return qqSpaceType;
        }

        public void setQqSpaceType(int qqSpaceType) {
            this.qqSpaceType = qqSpaceType;
        }

        public String getShareFriendText() {
            return shareFriendText;
        }

        public void setShareFriendText(String shareFriendText) {
            this.shareFriendText = shareFriendText;
        }

        public String getShareIncomeImageLink() {
            return shareIncomeImageLink;
        }

        public void setShareIncomeImageLink(String shareIncomeImageLink) {
            this.shareIncomeImageLink = shareIncomeImageLink;
        }

        public String getWechatIcon() {
            return wechatIcon;
        }

        public void setWechatIcon(String wechatIcon) {
            this.wechatIcon = wechatIcon;
        }

        public String getWechatTitle() {
            return wechatTitle;
        }

        public void setWechatTitle(String wechatTitle) {
            this.wechatTitle = wechatTitle;
        }

        public String getWechatDesc() {
            return wechatDesc;
        }

        public void setWechatDesc(String wechatDesc) {
            this.wechatDesc = wechatDesc;
        }

        public List<ShareFrientPosterItemsBean> getShareFrientPosterItems() {
            return shareFrientPosterItems;
        }

        public void setShareFrientPosterItems(List<ShareFrientPosterItemsBean> shareFrientPosterItems) {
            this.shareFrientPosterItems = shareFrientPosterItems;
        }

        public static class ShareFrientPosterItemsBean {
            @Override
            public String toString() {
                return "ShareFrientPosterItemsBean{" +
                        "order=" + order +
                        ", imageLink='" + imageLink + '\'' +
                        '}';
            }

            /**
             * order : 1
             * imageLink : http://cdn.qu.fi.pqmnz.com/backend/images/20180212/2d1020bb26924208928f213d5545347b_1518407441.png
             */

            private int order;
            private String imageLink;

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public String getImageLink() {
                return imageLink;
            }

            public void setImageLink(String imageLink) {
                this.imageLink = imageLink;
            }
        }
    }

    public static class XcxShareConfigItemBean {

        /*
        "xcxShareConfig": [
      {
        "xcxId": "3254545562",
        "xcxName": "小程序1",
        "title": "小程序分享",
        "content": "小程序分享内容",
        "img": "http://cdn.qu.fi.pqmnz.com/backend/images/20180427/9f5080e0ba904c4d83e99fdb075f5eed_1524809943.jpg"
        "url": ""
        "path": ""
      }
    ]
         */
        private String xcxId;
        private String xcxName;
        private String title;
        private String content;
        private String img;
        private String url;
        private String path;

        public String getPath() {
            return path;
        }

        public String getUrl() {
            return url;
        }

        public String getXcxId() {
            return xcxId;
        }

        public String getXcxName() {
            return xcxName;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getImg() {
            return img;
        }

        @Override
        public String toString() {
            return "XcxShareConfigItemBean{" +
                    "xcxId='" + xcxId + '\'' +
                    ", xcxName='" + xcxName + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", img='" + img + '\'' +
                    ", url='" + url + '\'' +
                    ", path='" + path + '\'' +
                    '}';
        }
    }

    public static class ActiveShoutuConfigBean {
        /**
         * isActive: true,
         * qqType: "0",
         * qqSpaceType: "0",
         * wechatType: "0",
         * shareFriendType: "0",
         * wechatIcon: "http://cdn.qu.fi.pqmnz.com/backend/images/20180509/71cd256c27e24d4689c609dfca9df59a_1525864081.png",
         * wechatTitle: "送你20元，名额有限，先到先得",
         * wechatDesc: "如果无法打开，在应用商店搜索“趣看视界”登录依然可以领取",
         * shareFriendText: "测试neural",
         * shareFriendDesc: "威海市",
         * shareFrientPosterItems: [
         * {
         * order: 1,
         * imageLink: "http://cdn.qu.fi.pqmnz.com/backend/images/20180509/68b2ce38e7af487cbd48062415fc0b82_1525878678.png"
         * }
         * ]
         */
        @Override
        public String toString() {
            return "ActiveShoutuConfigBean{" +
                    "isActive=" + isActive +
                    ", qqType='" + qqType + '\'' +
                    ", qqSpaceType='" + qqSpaceType + '\'' +
                    ", wechatType='" + wechatType + '\'' +
                    ", shareFriendType='" + shareFriendType + '\'' +
                    ", wechatIcon='" + wechatIcon + '\'' +
                    ", wechatTitle='" + wechatTitle + '\'' +
                    ", wechatDesc='" + wechatDesc + '\'' +
                    ", shareFriendText='" + shareFriendText + '\'' +
                    ", shareFriendDesc='" + shareFriendDesc + '\'' +
                    ", shareFrientPosterItems=" + shareFrientPosterItems +
                    '}';
        }

        private boolean isActive;//
        private String qqType;//1 海报分享 0 链接分享
        private String qqSpaceType;//1 海报分享 0 链接分享
        private String wechatType;//1 海报分享 0 链接分享
        private String shareFriendType;//1 海报分享 0 链接分享
        private String wechatIcon;
        private String wechatTitle;
        private String wechatDesc;
        private String shareFriendText;
        private String shareFriendDesc;
        private List<ShareFrientPosterItemsBean> shareFrientPosterItems;

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public String getQqType() {
            return qqType;
        }

        public String getQqSpaceType() {
            return qqSpaceType;
        }

        public String getWechatType() {
            return wechatType;
        }

        public String getShareFriendType() {
            return shareFriendType;
        }

        public String getWechatIcon() {
            return wechatIcon;
        }

        public String getWechatTitle() {
            return wechatTitle;
        }

        public String getWechatDesc() {
            return wechatDesc;
        }

        public String getShareFriendText() {
            return shareFriendText;
        }

        public String getShareFriendDesc() {
            return shareFriendDesc;
        }

        public List<ShareFrientPosterItemsBean> getShareFrientPosterItems() {
            return shareFrientPosterItems;
        }

        public void setQqType(String qqType) {
            this.qqType = qqType;
        }

        public void setQqSpaceType(String qqSpaceType) {
            this.qqSpaceType = qqSpaceType;
        }

        public void setWechatType(String wechatType) {
            this.wechatType = wechatType;
        }

        public void setShareFriendType(String shareFriendType) {
            this.shareFriendType = shareFriendType;
        }

        public void setWechatIcon(String wechatIcon) {
            this.wechatIcon = wechatIcon;
        }

        public void setWechatTitle(String wechatTitle) {
            this.wechatTitle = wechatTitle;
        }

        public void setWechatDesc(String wechatDesc) {
            this.wechatDesc = wechatDesc;
        }

        public void setShareFriendText(String shareFriendText) {
            this.shareFriendText = shareFriendText;
        }

        public void setShareFriendDesc(String shareFriendDesc) {
            this.shareFriendDesc = shareFriendDesc;
        }

        public void setShareFrientPosterItems(List<ShareFrientPosterItemsBean> shareFrientPosterItems) {
            this.shareFrientPosterItems = shareFrientPosterItems;
        }

        public static class ShareFrientPosterItemsBean {
            @Override
            public String toString() {
                return "ShareFrientPosterItemsBean{" +
                        "order=" + order +
                        ", imageLink='" + imageLink + '\'' +
                        '}';
            }

            /**
             * order : 1
             * imageLink : http://cdn.qu.fi.pqmnz.com/backend/images/20180212/2d1020bb26924208928f213d5545347b_1518407441.png
             */

            private int order;
            private String imageLink;

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public String getImageLink() {
                return imageLink;
            }

            public void setImageLink(String imageLink) {
                this.imageLink = imageLink;
            }
        }
    }

    public static class InductIconConfigBean {
        @Override
        public String toString() {
            return "InductIconConfigBean{" +
                    "statusForLogin=" + statusForLogin +
                    ", statusForUnlogin=" + statusForUnlogin +
                    ", icon='" + icon + '\'' +
                    ", gotoLink='" + gotoLink + '\'' +
                    ", lastTime=" + lastTime +
                    ", lastDay=" + lastDay +
                    '}';
        }

        /**
         * statusForLogin : 1
         * statusForUnlogin : 1
         * icon : http://cdn.qu.fi.pqmnz.com/backend/images/20180301/4ae874b94a0640edb6ca176af1e97fb0_1519895803.png
         * gotoLink : http://www.sunyungou.com/severbusy/
         * lastTime : 1
         * lastDay : 1
         */

        private int statusForLogin;//
        private int statusForUnlogin;//
        private String icon;
        private String gotoLink = "";
        private int lastTime;
        private int lastDay;

        public int getStatusForLogin() {
            return statusForLogin;
        }

        public void setStatusForLogin(int statusForLogin) {
            this.statusForLogin = statusForLogin;
        }

        public int getStatusForUnlogin() {
            return statusForUnlogin;
        }

        public void setStatusForUnlogin(int statusForUnlogin) {
            this.statusForUnlogin = statusForUnlogin;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getGotoLink() {
            return gotoLink;
        }

        public void setGotoLink(String gotoLink) {
            this.gotoLink = gotoLink;
        }

        public int getLastTime() {
            return lastTime;
        }

        public void setLastTime(int lastTime) {
            this.lastTime = lastTime;
        }

        public int getLastDay() {
            return lastDay;
        }

        public void setLastDay(int lastDay) {
            this.lastDay = lastDay;
        }
    }

    public static class RedIconConfigsBean {
        @Override
        public String toString() {
            return "RedIconConfigsBean{" +
                    "type=" + type +
                    ", isShow=" + isShow +
                    ", lastTime=" + lastTime +
                    ", lastDay=" + lastDay +
                    '}';
        }

        /**
         * type : 0
         * isShow : true
         * lastTime : 1
         * lastDay : 1
         */

        private int type;//1 我的  2任务
        private boolean isShow;
        private int lastTime;//显示几次？
        private int lastDay;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isIsShow() {
            return isShow;
        }

        public void setIsShow(boolean isShow) {
            this.isShow = isShow;
        }

        public int getLastTime() {
            return lastTime;
        }

        public void setLastTime(int lastTime) {
            this.lastTime = lastTime;
        }

        public int getLastDay() {
            return lastDay;
        }

        public void setLastDay(int lastDay) {
            this.lastDay = lastDay;
        }
    }

    public static class MyDialogBean{
        private int statusForLogin;//
        private int statusForUnlogin;//
        private String toastLink;//弹窗图片地址
        private String toastGotoLink;//跳转链接
        private boolean isShowThumbNail;//
        private int lastTime;//每天弹几次
        private int lastDay;//弹几天
        private int toastPriority;//
        private int minute;//
        private List<String> imgs = new ArrayList<>();//

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public int getStatusForLogin() {
            return statusForLogin;
        }

        public void setStatusForLogin(int statusForLogin) {
            this.statusForLogin = statusForLogin;
        }

        public int getStatusForUnlogin() {
            return statusForUnlogin;
        }

        public void setStatusForUnlogin(int statusForUnlogin) {
            this.statusForUnlogin = statusForUnlogin;
        }

        public String getToastLink() {
            return toastLink;
        }

        public void setToastLink(String toastLink) {
            this.toastLink = toastLink;
        }

        public String getToastGotoLink() {
            return toastGotoLink;
        }

        public void setToastGotoLink(String toastGotoLink) {
            this.toastGotoLink = toastGotoLink;
        }

        public boolean isIsShowThumbNail() {
            return isShowThumbNail;
        }

        public void setIsShowThumbNail(boolean isShowThumbNail) {
            this.isShowThumbNail = isShowThumbNail;
        }

        public int getLastTime() {
            return lastTime;
        }

        public void setLastTime(int lastTime) {
            this.lastTime = lastTime;
        }

        public int getLastDay() {
            return lastDay;
        }

        public void setLastDay(int lastDay) {
            this.lastDay = lastDay;
        }

        public int getToastPriority() {
            return toastPriority;
        }

        public void setToastPriority(int toastPriority) {
            this.toastPriority = toastPriority;
        }
    }

}
