package com.zhangku.qukandian.biz.adbeen.huitoutiao;

import com.google.gson.Gson;

public class AdRequestBeen {

    String requestId; // 广告请求ID，必填，由接入方自定义，需确保唯一性。推荐使用channel_id + positionId + 若干随机位构成。
    String apiVersion; // API版本号，当前版本1.0
    String channelId; // 渠道ID，由平台提供，选填
    String positionId; // 广告位ID，由平台提供
    DeviceInfo deviceInfo; // 设备信息，详情见下文
    Network network; //网络信息，详情见下文
    UserProfile userProfile; // 用户基本信息，详情见下文
    Location location; // 地理位置信息，详情见下文
    App app; //app信息，详情见下文
    int adCount; // 请求广告数
    short mode; // 服务端api请求为0，客户端api请求为1,客户端sdk为2

    public String getRequestId() {
        return requestId;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getPositionId() {
        return positionId;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public Network getNetwork() {
        return network;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public Location getLocation() {
        return location;
    }

    public App getApp() {
        return app;
    }

    public int getAdCount() {
        return adCount;
    }

    public short getMode() {
        return mode;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public void setAdCount(int adCount) {
        this.adCount = adCount;
    }

    public void setMode(short mode) {
        this.mode = mode;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
/*

广告请求样例
{
  "requestId": "602abb58b0d24549bf34d54f3bd45a2c",
  "apiVersion": "1.0",
  "positionId": "2206668971",
  "channelId": "2109124107",
  "deviceInfo": {
    "os": "Android",
    "osVersion": "6.0",
    "imei": "862484035593421",
    "androidId": "3684daa8487cf202",
    "deviceType": 0,
    "brand": "Meizu",
    "model": "M5",
    "userAgent": "Mozilla/5.0 (Linux; Android 6.0;M5 Build/MRA58K; wv) AppleWe bKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.140 Mobile Safari/537 .36",
    "screenHeight": 1280,
    "screenWidth": 720
  },
  "network": {
    "ip": "211.95.56.10",
    "connectionType": 1,
    "operatorType": 1
  },
  "app": {
    "name": "apollo",
    "packName": "com.apollo",
    "version": "1.0.0",
    "category": 0,
    "dlinkSupport": 0
  },
  "mode": 1,
  "adCount": 2
}

 */
