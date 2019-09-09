package com.zhangku.qukandian.bean;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/19
 * 你不注释一下？
 */
public class UpLevelResBean {
    /**
     * level : 1
     * tLevel : 3
     * levelAwardGold : 200
     * tqList : ["阅读奖励","幸运任务奖励"]
     */
    private int level;
    private int tLevel;
    private int levelAwardGold;
    private List<String> tqList;
    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTLevel() {
        return tLevel;
    }

    public void setTLevel(int tLevel) {
        this.tLevel = tLevel;
    }

    public int getLevelAwardGold() {
        return levelAwardGold;
    }

    public void setLevelAwardGold(int levelAwardGold) {
        this.levelAwardGold = levelAwardGold;
    }

    public List<String> getTqList() {
        return tqList;
    }

    public void setTqList(List<String> tqList) {
        this.tqList = tqList;
    }
//    private int level;
//    private int tLevel;
//    private int levelAwardGold;
//
//    public int getLevel() {
//        return level;
//    }
//
//    public void setLevel(int level) {
//        this.level = level;
//    }
//
//    public int gettLevel() {
//        return tLevel;
//    }
//
//    public void settLevel(int tLevel) {
//        this.tLevel = tLevel;
//    }

}
