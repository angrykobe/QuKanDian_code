package com.zhangku.qukandian.biz.adbeen.oupeng;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/19
 * 请求实体
 */
public class OupengReqBean {
    private String request_id;// 必须 字符串 媒体自定义请求 ID，联调测试用， 建议使用 UUID
    private VersionBean api_version;// 必须 对象 API 版本，参见 version,填写 3.11.0
    private AppBean app;// 必须 对象 应用信息，参见 app
    private DeviceBean device;// 必须 对象 设备信息，参见 device
    private List<AdslotBean> adslots = new ArrayList<>();// 必须 数组 广告位信息，参见 adslot

    public static class SizeBean{
        public String width;// 必须 数值 宽度
        public String height;// 必须 数值 高度
    }

    public static class VersionBean{
        public String major = "3";// 必须 数值 3
        public String minor = "11";// 必须 数值 11
        public String micro;// 可选 数值 0
    }
    public static class AppBean{
        public String app_package_name;// 必须 字符串 应用包路径全称，如： com.oupeng.browser
        public String app_name;//可选 字符串 应用名称，如：欧朋浏览器
        public VersionBean app_version;//可选 对象 应用版本，参见 version
    }

    public static class DeviceBean{
        public String os;// 必须 字符串 ANDROID / IOS， 注意大写
        public String imei;// Android 必须 字符串 Android 设备的 IMEI
        public String android_id;// Android 必须 字符串 Android 设备的系统 ID
        public String ip;// 必须 字符串 用户设备的 IPv4，服务端转发请 务必保证 IP 的真实性，用于部分 地域定向广告
        public String ua;// 必须 字符串 user-agent,必须和广告展示、广 告点击的 user-agent 保持一致， 否则会被判定为作弊流量
        public String mac;// 可选 字符串 52:54:00:B0:2C:C3
        public String vendor;// 必须 字符串 厂商 Apple
        public String model;// 必须 字符串 型号 iPhone7,2
        public SizeBean screen_size;// 必须 对象 {width: 720, height: 1280}
        public String connection_type;// 必须 字符串 WIFI,4G,参见附录 3 注意大写
        public float longitude ;//可选 浮点数值 经度
        public float latitude;// 可选 浮点数值 纬度
        public float timestamp;// 可选 浮点数值 获取经纬度时间戳，单位秒
        public String operator_id;// 建议 字符串 运营商 ID，例如 46001，MCC+MNC
        public String imsi;// 建议 字符串 IMSI
        public VersionBean os_version;// iOS 9.3.2 / Android 5.1.0
    }

    public static class AdslotBean{
        public String id;// 必须 字符串 广告位 ID，由欧朋商务 BD 提供
        public SizeBean size;// 可选 对象 广告大图尺寸，目前不做严格匹 配，参见 size
        public String count = "1";// 可选 数值 需要返回的广告条数，上限 10 条
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public VersionBean getApi_version() {
        return api_version;
    }

    public void setApi_version(VersionBean api_version) {
        this.api_version = api_version;
    }

    public AppBean getApp() {
        return app;
    }

    public void setApp(AppBean app) {
        this.app = app;
    }

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public List<AdslotBean> getAdslots() {
        return adslots;
    }

    public void setAdslots(List<AdslotBean> adslots) {
        this.adslots = adslots;
    }

    public void addAdslots(AdslotBean adslots) {
        this.adslots.add(adslots);
    }
}
