package com.zhangku.qukandian.biz.adbeen.huitoutiao;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

public class AdInfoVO {
    private String trackingId; // 广告行为跟踪ID，方便对账
    private String clickUrl; // 点击行为地址
    private short clickAction; // 交互类型，1为下载, 2为打开落地页。
    private AdCreative adCreative; // 广告创意信息，详情见下文。
    private Map<String, List<String>> eventTracking; // 效果监控信息，key为exp表示曝光的监控链接列列表，key为clc表示点击的监控链接列表。客户端需要在相应的行为触发之后对每个链接进行请求发送，否则将影响数据和收入。
    private double price; // 价格
    private String deeplink; //deeplink地址，如该字段为null，则不执行deeplink
    private int expirationTime; // 过期时间，单位为秒

    public String getTrackingId() {
        return trackingId;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public short getClickAction() {
        return clickAction;
    }

    public AdCreative getAdCreative() {
        return adCreative;
    }

    public Map<String, List<String>> getEventTracking() {
        return eventTracking;
    }

    public double getPrice() {
        return price;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public void setClickAction(short clickAction) {
        this.clickAction = clickAction;
    }

    public void setAdCreative(AdCreative adCreative) {
        this.adCreative = adCreative;
    }

    public void setEventTracking(Map<String, List<String>> eventTracking) {
        this.eventTracking = eventTracking;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
