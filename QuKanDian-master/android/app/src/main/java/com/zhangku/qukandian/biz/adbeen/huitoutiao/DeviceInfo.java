package com.zhangku.qukandian.biz.adbeen.huitoutiao;

public class DeviceInfo {
    private String os; // 操作系统，包括Android和iOS
    private String osVersion; // 系统版本，必填，未知填unknown
    private String imei; // Android设备必填
    private String mac;  // 选填
    private String idfa; // iOS 系统必填
    private String androidId; // Android设备必填
    private String idfaMd5;
    private String imeiMd5;
    private String androidIdMd5;
    private String userAgent; // 必填
    private Integer deviceType; // 0位⼿手机，1为平板， 未知为2，必填
    private String brand; // 手机品牌，必填 样例例：MEIZU, 未知填unknown
    private String model; // 设备型号，必填填, 未知填unknown
    private int screenWidth; // 选填
    private int screenHeight; // 选填
    private String extra;

    public void setOs(String os) {
        this.os = os;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public void setIdfaMd5(String idfaMd5) {
        this.idfaMd5 = idfaMd5;
    }

    public void setImeiMd5(String imeiMd5) {
        this.imeiMd5 = imeiMd5;
    }

    public void setAndroidIdMd5(String androidIdMd5) {
        this.androidIdMd5 = androidIdMd5;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getOs() {
        return os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getImei() {
        return imei;
    }

    public String getMac() {
        return mac;
    }

    public String getIdfa() {
        return idfa;
    }

    public String getAndroidId() {
        return androidId;
    }

    public String getIdfaMd5() {
        return idfaMd5;
    }

    public String getImeiMd5() {
        return imeiMd5;
    }

    public String getAndroidIdMd5() {
        return androidIdMd5;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public String getExtra() {
        return extra;
    }
}
