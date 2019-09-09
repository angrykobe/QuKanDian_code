package com.zhangku.qukandian.biz.adbeen.saibo;

/**
 * 创建者          xuzhida
 * 创建日期        2019/1/14
 * 请求bean
 */
public class SaiboReqBean {
    /**
     * version : 2.0
     * time : 1414764477867
     * token : 2c38ec54777641d9687aa8b65e7fa621
     * reqid : 2c38ec54777641d9687aa8bgrefef343
     * appid : 100002
     * adspotid : 100000433
     * impsize : 1
     * ip : 58.53.67.42
     * ua : Mozilla/5.0 (iPhone; CPU iPhone OS 9_2 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Mobile/13C75
     * lat : 31.167
     * lon : 112.582
     * sw : 1080
     * sh : 1920
     * ppi : 428
     * make : HUAWEI
     * model : HUAWEI Y600-U00
     * os : 1
     * osv : 7.1
     * imei : 862033031965161
     * mac : 38:bc:1a:c7:3b:11
     * androidid : 69f2d801e804f908
     * idfa : E5C21290-B2D4-46D4-B9B7-37B59DD78684
     * carrier : 46000
     * network : 2
     * devicetype : 1
     * res_type : 0
     */
    private String version = "2.0";//接口版本号,目前为2.0。
    private String time;//请求的unix时间戳,精确到毫秒(13位),见示例请求。
    private String token;//流量校验码,见2.2。
    private String reqid;//此次请求的id,uuid,见2.2。
    private String appid;//APP的id。
    private String appver;//APP的版本号。
    private String adspotid;//Y	广告位的id。
    private int impsize = 1;//Y	请求曝光的数量。
    private String ip;//Y	设备的ip地址。
    private String ua;//Y	user agent信
    private double lat;//	纬度。
    private double lon;//	经度。
    private int sw;//设备屏幕宽度,物理像素。
    private int sh;//设备屏幕高度,物理像素。
    private int ppi;//设备像素密度,物理像素。
    private String make;// 制造商。
    private String model;// 机型。
    private int os = 2;//Y	操作系统类型,0:未识别, 1:ios, 2:android。
    private String osv;//操作系统版本号,只取前两位数字表示。如:5.5/10.8。
    private String imei;//imei(只适用于android)。
    private String mac;//mac地址(只适用于android)。注:若获取不到请传空字符串。
    private String androidid;//androidid(只适用于android)。
    private String carrier = "";//运营商信息, 使用标准MCC/MNC码。注:若获取不到请传空字符串。示例编码请参考4.5。
    private int network;//网络连接类型, 0:未识别,1:WIFI,2:2G,3:3G,4:4G。
    private int devicetype;//设备类型, 0:未识别,1:手机,2:平板,3:机顶盒。

    private String idfa;//idfa(只适用于ios)。
    private int res_type;//N banner广告位要求返回的广告格式, 0:图片 1:html 2:html/图片 都支持, 默认值为0, 可以不传

    public String getAppver() {
        return appver;
    }

    public void setAppver(String appver) {
        this.appver = appver;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAdspotid() {
        return adspotid;
    }

    public void setAdspotid(String adspotid) {
        this.adspotid = adspotid;
    }

    public int getImpsize() {
        return impsize;
    }

    public void setImpsize(int impsize) {
        this.impsize = impsize;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getSw() {
        return sw;
    }

    public void setSw(int sw) {
        this.sw = sw;
    }

    public int getSh() {
        return sh;
    }

    public void setSh(int sh) {
        this.sh = sh;
    }

    public int getPpi() {
        return ppi;
    }

    public void setPpi(int ppi) {
        this.ppi = ppi;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getOs() {
        return os;
    }

    public void setOs(int os) {
        this.os = os;
    }

    public String getOsv() {
        return osv;
    }

    public void setOsv(String osv) {
        this.osv = osv;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAndroidid() {
        return androidid;
    }

    public void setAndroidid(String androidid) {
        this.androidid = androidid;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public int getNetwork() {
        return network;
    }

    public void setNetwork(int network) {
        this.network = network;
    }

    public int getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(int devicetype) {
        this.devicetype = devicetype;
    }

    public int getRes_type() {
        return res_type;
    }

    public void setRes_type(int res_type) {
        this.res_type = res_type;
    }
}
