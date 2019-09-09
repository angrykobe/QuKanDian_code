package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/26
 * 你不注释一下？
 */
public class ReadTipsBean {
    private String key;//  对应有效阅读返回的“description”的值（其中有三个非有效阅读奖励接口返回的信息：slide-滑动提醒，click-需多次点击，error-接口调用失败提示）
    private String value;// 提示语

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
