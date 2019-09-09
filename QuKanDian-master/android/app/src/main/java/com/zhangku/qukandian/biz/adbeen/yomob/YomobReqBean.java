package com.zhangku.qukandian.biz.adbeen.yomob;

/**
 * 创建者          xuzhida
 * 创建日期        2019/1/14
 * 请求bean
 */
public class YomobReqBean {
    private int ad_type;	//广告类型：0-奖励视频；1-静态插屏；2-原生广告；3-Banner	无	是
    private int goal_type;	//推广内容类型，0：app；1：web；2：miniprogram	无	是
    private AppBean app;	//合作方媒体相关信息	无	是
    private DeviceBean device;	//用户设备信息	无	是
    private UserBean user;
    private String version;	//api接口版本信息（参见文档概述部分版本号），如："1.0.0"	无	是
    private double adw;	//广告位宽度(像素)	无(需要保证准确，否则影响广告展示效果)	是
    private double adh;	//广告位高度(像素)	无(需要保证准确，否则影响广告展示效果)	是
    private int ad_count;	//广告数量，暂时只支持1	1	是
    private String integration;	//接入方式：server、client	无	是

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
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

    public int getAd_type() {
        return ad_type;
    }

    public void setAd_type(int ad_type) {
        this.ad_type = ad_type;
    }

    public int getGoal_type() {
        return goal_type;
    }

    public void setGoal_type(int goal_type) {
        this.goal_type = goal_type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getAdw() {
        return adw;
    }

    public void setAdw(double adw) {
        this.adw = adw;
    }

    public double getAdh() {
        return adh;
    }

    public void setAdh(double adh) {
        this.adh = adh;
    }

    public int getAd_count() {
        return ad_count;
    }

    public void setAd_count(int ad_count) {
        this.ad_count = ad_count;
    }

    public String getIntegration() {
        return integration;
    }

    public void setIntegration(String integration) {
        this.integration = integration;
    }

    public static class UserBean{
        private int gender;
        private int age;
        private String keywords;
        private GeographyBean geography;
        private String  tgid= "03iXe1J88erS2p5855xu";

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public GeographyBean getGeography() {
            return geography;
        }

        public void setGeography(GeographyBean geography) {
            this.geography = geography;
        }

        public static class GeographyBean {
            /**
             * lat : 39.96
             * lng : 116.29
             * country_code : CN
             * city : null
             */

            private double lat;
            private double lng;
            private String country_code;
            private Object city;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public String getCountry_code() {
                return country_code;
            }

            public void setCountry_code(String country_code) {
                this.country_code = country_code;
            }

            public Object getCity() {
                return city;
            }

            public void setCity(Object city) {
                this.city = city;
            }
        }
    }

    public static class AppBean{
        private String app_id	;//	App唯一标识	无	是
        private String name	;//	媒体应用名称	无	是
        private String bundle	;//	包名或bundleid	无	是
        private String app_version	;//	应用版本号	无	是
        private String store_url	;//	应用商店链接	无	是

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBundle() {
            return bundle;
        }

        public void setBundle(String bundle) {
            this.bundle = bundle;
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public String getStore_url() {
            return store_url;
        }

        public void setStore_url(String store_url) {
            this.store_url = store_url;
        }

    }
    public static class DeviceBean{
        private int type	;//	设备类型， 0:其他； 1:手机； 2:平板；	无	是
        private String imei	;//	国际移动台设备识别码	无	否(如果有，可以提高填充和收益)
        private String imsi	;//	国际移动客户识别码	无	否(如果有，可以提高填充和收益)
        private String aaid	;//	Android Advertiser ID	无	否(如果有，可以提高填充和收益)
        private String anid	;//	Android id	无	否(Android时必传)
        private String mac	;//	设备 mac 地址	无	否(尽量获取)
        private String ip	;//	设备 IPv4 地址	无	否(在 integration 为 server 的时候必填)
        private String language	;//	终端语言	无	是
        private String maker	;//	制造商名称	无	否
        private String brand	;//	品牌名称	无	是
        private String model	;//	设备型号	无	是
        private String device_name	;//	设备名称	无	是
        private int os	;//	终端操作系统，0: iOS; 1: Android	无	是
        private String os_version	;//	操作系统版本	无	是
        private int conntype	;//	网络类型，1:wifi；2:2G；3:3G；4:4G；5:代理；6:其他	无	是
        private double screen_width	;//	屏幕宽度(像素)	无(需要保证准确，否则影响广告展示效果)	是
        private double screen_height	;//	屏幕高度(像素)	无(需要保证准确，否则影响广告展示效果)	是
        private double density	;//	设备屏幕密度	无	是
        private int orientation	;//	APP的方向 0:未知;1:竖屏;2:横屏	无(需要保证准确，否则影响广告展示效果)	是
        private String ua	;//	浏览器User-Agent字符串	无	是

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getImsi() {
            return imsi;
        }

        public void setImsi(String imsi) {
            this.imsi = imsi;
        }

        public String getAaid() {
            return aaid;
        }

        public void setAaid(String aaid) {
            this.aaid = aaid;
        }

        public String getAnid() {
            return anid;
        }

        public void setAnid(String anid) {
            this.anid = anid;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getMaker() {
            return maker;
        }

        public void setMaker(String maker) {
            this.maker = maker;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public int getOs() {
            return os;
        }

        public void setOs(int os) {
            this.os = os;
        }

        public String getOs_version() {
            return os_version;
        }

        public void setOs_version(String os_version) {
            this.os_version = os_version;
        }

        public int getConntype() {
            return conntype;
        }

        public void setConntype(int conntype) {
            this.conntype = conntype;
        }

        public double getScreen_width() {
            return screen_width;
        }

        public void setScreen_width(double screen_width) {
            this.screen_width = screen_width;
        }

        public double getScreen_height() {
            return screen_height;
        }

        public void setScreen_height(double screen_height) {
            this.screen_height = screen_height;
        }

        public double getDensity() {
            return density;
        }

        public void setDensity(double density) {
            this.density = density;
        }

        public int getOrientation() {
            return orientation;
        }

        public void setOrientation(int orientation) {
            this.orientation = orientation;
        }

        public String getUa() {
            return ua;
        }

        public void setUa(String ua) {
            this.ua = ua;
        }
    }

}
