package com.zhangku.qukandian.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/19
 * 你不注释一下？
 */
public class UserLevelBean {
    /**
     * grade : 0
     * tGrade : 0
     * exp : 0
     * levelInfo : {"grade":0,"displayName":"小学生","levelAwardGold":100,"exp":100,"headwearFlag":true,"betaFlag":true,"quickFlag":true,"ydFlag":false,"xyFlag":false,"hbFlag":false,"rsFlag":false,"qdFlag":false,"bxFlag":false,"goldFlag":false}
     * nextLevelInfo : {"grade":1,"displayName":"初中生","levelAwardGold":200,"exp":200}
     * levelDescUrl :
     * upTime : 2018-12-19T10:59:08.6367375+08:00
     * id : 10094
     */
    private int grade;//当前等级
    private int tGrade;//可升等级
    private float exp;//当前经验值
    private LevelInfoBean levelInfo;// 当前等级信息
    private NextLevelInfoBean nextLevelInfo;// 下一等级信息
    private String levelDescUrl;//等级详细规则页面
    private String upTime;//
    private int id;//
    private String upLevelDesc;//
    private List<String> newPrivilege = new ArrayList<>();

    public List<String> getNewPrivilege() {
        return newPrivilege;
    }

    public void setNewPrivilege(List<String> newPrivilege) {
        this.newPrivilege = newPrivilege;
    }

    public String getUpLevelDesc() {
        return upLevelDesc;
    }

    public void setUpLevelDesc(String upLevelDesc) {
        this.upLevelDesc = upLevelDesc;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getTGrade() {
        return tGrade;
    }

    public void setTGrade(int tGrade) {
        this.tGrade = tGrade;
    }

    public float getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public LevelInfoBean getLevelInfo() {
        return levelInfo;
    }

    public void setLevelInfo(LevelInfoBean levelInfo) {
        this.levelInfo = levelInfo;
    }

    public NextLevelInfoBean getNextLevelInfo() {
        return nextLevelInfo;
    }

    public void setNextLevelInfo(NextLevelInfoBean nextLevelInfo) {
        this.nextLevelInfo = nextLevelInfo;
    }

    public String getLevelDescUrl() {
        return levelDescUrl;
    }

    public void setLevelDescUrl(String levelDescUrl) {
        this.levelDescUrl = levelDescUrl;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class LevelInfoBean {
        /**
         * grade : 0
         * displayName : 小学生
         * levelAwardGold : 100
         * exp : 100
         * headwearFlag : true
         * betaFlag : true
         * quickFlag : true
         * ydFlag : false
         * xyFlag : false
         * hbFlag : false
         * rsFlag : false
         * qdFlag : false
         * bxFlag : false
         * goldFlag : false
         */

        private int grade;//
        private String displayName;//显示名称
        private int levelAwardGold;//
        private float exp;//要求经验值
        private boolean headwearFlag;//专属头饰
        private boolean betaFlag;//内测资格
        private boolean quickFlag;//次日到账
        private boolean ydFlag;//阅读奖励
        private boolean xyFlag;//幸运任务奖励
        private boolean hbFlag;//阅读红包奖励
        private boolean rsFlag;// 热搜奖励
        private boolean qdFlag;// 签到奖励
        private boolean bxFlag;//宝箱奖励
        private boolean goldFlag;//每日金币奖励
        private boolean ewtxFlag;

        public boolean isEwtxFlag() {
            return ewtxFlag;
        }

        public void setEwtxFlag(boolean ewtxFlag) {
            this.ewtxFlag = ewtxFlag;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public int getLevelAwardGold() {
            return levelAwardGold;
        }

        public void setLevelAwardGold(int levelAwardGold) {
            this.levelAwardGold = levelAwardGold;
        }

        public float getExp() {
            return exp;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }

        public boolean isHeadwearFlag() {
            return headwearFlag;
        }

        public void setHeadwearFlag(boolean headwearFlag) {
            this.headwearFlag = headwearFlag;
        }

        public boolean isBetaFlag() {
            return betaFlag;
        }

        public void setBetaFlag(boolean betaFlag) {
            this.betaFlag = betaFlag;
        }

        public boolean isQuickFlag() {
            return quickFlag;
        }

        public void setQuickFlag(boolean quickFlag) {
            this.quickFlag = quickFlag;
        }

        public boolean isYdFlag() {
            return ydFlag;
        }

        public void setYdFlag(boolean ydFlag) {
            this.ydFlag = ydFlag;
        }

        public boolean isXyFlag() {
            return xyFlag;
        }

        public void setXyFlag(boolean xyFlag) {
            this.xyFlag = xyFlag;
        }

        public boolean isHbFlag() {
            return hbFlag;
        }

        public void setHbFlag(boolean hbFlag) {
            this.hbFlag = hbFlag;
        }

        public boolean isRsFlag() {
            return rsFlag;
        }

        public void setRsFlag(boolean rsFlag) {
            this.rsFlag = rsFlag;
        }

        public boolean isQdFlag() {
            return qdFlag;
        }

        public void setQdFlag(boolean qdFlag) {
            this.qdFlag = qdFlag;
        }

        public boolean isBxFlag() {
            return bxFlag;
        }

        public void setBxFlag(boolean bxFlag) {
            this.bxFlag = bxFlag;
        }

        public boolean isGoldFlag() {
            return goldFlag;
        }

        public void setGoldFlag(boolean goldFlag) {
            this.goldFlag = goldFlag;
        }
    }

    public static class NextLevelInfoBean {
        /**
         * grade : 1
         * displayName : 初中生
         * levelAwardGold : 200
         * exp : 200
         */

        private int grade;
        private String displayName; // 名称
        private int levelAwardGold;
        private float exp;// 要求经验值

        private boolean headwearFlag;//专属头饰
        private boolean betaFlag;//内测资格
        private boolean quickFlag;//次日到账
        private boolean ydFlag;//阅读奖励
        private boolean xyFlag;//幸运任务奖励
        private boolean hbFlag;//阅读红包奖励
        private boolean rsFlag;// 热搜奖励
        private boolean qdFlag;// 签到奖励
        private boolean bxFlag;//宝箱奖励
        private boolean goldFlag;//每日金币奖励
        private boolean ewtxFlag;

        public void setExp(float exp) {
            this.exp = exp;
        }

        public boolean isHeadwearFlag() {
            return headwearFlag;
        }

        public void setHeadwearFlag(boolean headwearFlag) {
            this.headwearFlag = headwearFlag;
        }

        public boolean isBetaFlag() {
            return betaFlag;
        }

        public void setBetaFlag(boolean betaFlag) {
            this.betaFlag = betaFlag;
        }

        public boolean isQuickFlag() {
            return quickFlag;
        }

        public void setQuickFlag(boolean quickFlag) {
            this.quickFlag = quickFlag;
        }

        public boolean isYdFlag() {
            return ydFlag;
        }

        public void setYdFlag(boolean ydFlag) {
            this.ydFlag = ydFlag;
        }

        public boolean isXyFlag() {
            return xyFlag;
        }

        public void setXyFlag(boolean xyFlag) {
            this.xyFlag = xyFlag;
        }

        public boolean isHbFlag() {
            return hbFlag;
        }

        public void setHbFlag(boolean hbFlag) {
            this.hbFlag = hbFlag;
        }

        public boolean isRsFlag() {
            return rsFlag;
        }

        public void setRsFlag(boolean rsFlag) {
            this.rsFlag = rsFlag;
        }

        public boolean isQdFlag() {
            return qdFlag;
        }

        public void setQdFlag(boolean qdFlag) {
            this.qdFlag = qdFlag;
        }

        public boolean isBxFlag() {
            return bxFlag;
        }

        public void setBxFlag(boolean bxFlag) {
            this.bxFlag = bxFlag;
        }

        public boolean isGoldFlag() {
            return goldFlag;
        }

        public void setGoldFlag(boolean goldFlag) {
            this.goldFlag = goldFlag;
        }

        public boolean isEwtxFlag() {
            return ewtxFlag;
        }

        public void setEwtxFlag(boolean ewtxFlag) {
            this.ewtxFlag = ewtxFlag;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public int getLevelAwardGold() {
            return levelAwardGold;
        }

        public void setLevelAwardGold(int levelAwardGold) {
            this.levelAwardGold = levelAwardGold;
        }

        public float getExp() {
            return exp;
        }

        public void setExp(int exp) {
            this.exp = exp;
        }
    }
}
