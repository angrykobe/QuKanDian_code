package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2018/4/4.
 */

public class EncryptBean {

    private String unionId;
    private int gold;

    public EncryptBean(String s, int i) {
        unionId = s;
        gold = i;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
