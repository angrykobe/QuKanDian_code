package com.zhangku.qukandian.biz.adbeen.huzhong;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 创建者          xuzhida
 * 创建日期        2018/6/25
 * 互众请求
 */
public class HuzhongAdRequestBean {
    private String ip;//
    private String ua;//用户请求的User-Agent header
    private String si;//平台分配的广告位ID
    private HuzhongAdRequestBean.Device device;//
    private String app_version;//移动流量的App版本号  2.1.0
    private String package_name;//移动流量包名  com.company.view_level
    private String mimes;//允许的素材类型， 多个mime类型用逗号分隔。  txt-文字链，icon-图文，c-富媒体 img-等价于jpg,gif,png,webp	jpg,gif,png,mp4,webp,flv,swf,txt,icon,c
    private String v;//版本号   1.3.6

    @Override
    public String toString() {
        try {
            return "ip=" + ip +
                    "&ua=" + ua +
                    "&si=" + si +
                    "&device=" + (URLEncoder.encode(new Gson().toJson(device), "UTF-8")) +
                    "&app_version=" + app_version +
                    "&package_name=" + package_name +
                    "&mimes=" + mimes +
                    "&v=" + v;
        } catch (UnsupportedEncodingException e) {
            return "ip=" + ip +
                    "&ua=" + ua +
                    "&si=" + si +
                    "&device=" + new Gson().toJson(device) +
                    "&app_version=" + app_version +
                    "&package_name=" + package_name +
                    "&mimes=" + mimes +
                    "&v=" + v;
        }
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

    public String getSi() {
        return si;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getMimes() {
        return mimes;
    }

    public void setMimes(String mimes) {
        this.mimes = mimes;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public static class Device {
        private String udid;//移动设备ID号   686779178957650
        private String identify_type;//udid的类型，idfa或imei
        private String android_id;//安卓android_id    9774d56d682e549c
        private String vendor;//移动设备品牌    Apple
        private String model;//型号(见model映射表)    iPhone 5s
        private String os;//操作系统1：android系统2：ios系统
        private String os_version;//操作系统版本号
        private String network;//联网方式 0: 其它 1: WIFI  2: 2G  3: 3G  4: 4G
        private String operator;//运营商0: 其它1: 移动2: 联通3: 电信
        private String width;//屏幕宽。取设备物理像素
        private String height;//屏幕高。取设备物理像素

        ////////////选填
        private String mac;//移动设备MAC地址   b2:e2:35:ad:18:9c
        private String density;//屏幕密度，一个逻辑像素等于几个实际像素

        public String getUdid() {
            return udid;
        }

        public void setUdid(String udid) {
            this.udid = udid;
        }

        public String getIdentify_type() {
            return identify_type;
        }

        public void setIdentify_type(String identify_type) {
            this.identify_type = identify_type;
        }

        public String getAndroid_id() {
            return android_id;
        }

        public void setAndroid_id(String android_id) {
            this.android_id = android_id;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOs_version() {
            return os_version;
        }

        public void setOs_version(String os_version) {
            this.os_version = os_version;
        }

        public String getNetwork() {
            return network;
        }

        public void setNetwork(String network) {
            this.network = network;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getDensity() {
            return density;
        }

        public void setDensity(String density) {
            this.density = density;
        }
    }
}
