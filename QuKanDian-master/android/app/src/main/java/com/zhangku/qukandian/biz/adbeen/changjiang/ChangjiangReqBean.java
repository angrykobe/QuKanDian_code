package com.zhangku.qukandian.biz.adbeen.changjiang;

import com.google.gson.Gson;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/20
 * 你不注释一下？
 */
public class ChangjiangReqBean {
    private String appKey;//	String	是	申请的广告位appKey
    private String slotId;//	String	是	申请的广告位ID
    private DeviceBean device;//		是	设备参数

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public static class DeviceBean{
        private String imei;//	String	是	Android必填 IOS填空	868227022234384
        private String imsi;//	String	否	国际用户识别代码，Android 可填
        private String idfa;//	String	是	IOS必填 Android填空	8287B2C7-5037-4B6B-A8A3-8BBFE7CDD338
        private String idfv;//	String	否	供应商标识符，iOS可填
        private String device_id;//	String	是	设备唯一标识
        private String ip;//	String	是	设备Ip地址，注意获取外网ip地址	116.226.214.25
        private String mac;//	String	是	mac地址	b2 : e2 : 35 : ad : 18 : 9c
        private String user_agent;//	String	是	设备浏览信息，app获取webview的浏览器信息	Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.101 Safari/537.36
        private int os;//	Int	是	操作系统: 1:Android  2:iOS	1
        private String os_version;//	String	是	操作系统版本号	6.0.1
        private int net_type;//	Int	是	网络类型: 1 - wifi、2 - 2G、3 - 3G、4 - 4G	1
        private int operator;//	Int	是	运营商： 0：其他 1：移动 2：联通 3：电信	1
        private String vendor;//	String	是	移动设备品牌	HuaWei
        private String model;//	String	是	型号	EVA-AL00
        private String longitude="";//	String	是	GPS坐标经度	145.78
        private String latitude="";//	String	是	GPS坐标纬度	29.10
        private String package_name;//	String	是	包名	com.test.t
        private String app_version;//	String	是	app版本号	2.3.10
        private String width;//	String	是	广告位宽度	600
        private String height;// String	是	广告位高度	200
        private String screen_width;// String	是	屏幕宽度	720
        private String screen_height;//	String	是	屏幕高度	1080

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

        public String getIdfa() {
            return idfa;
        }

        public void setIdfa(String idfa) {
            this.idfa = idfa;
        }

        public String getIdfv() {
            return idfv;
        }

        public void setIdfv(String idfv) {
            this.idfv = idfv;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getUser_agent() {
            return user_agent;
        }

        public void setUser_agent(String user_agent) {
            this.user_agent = user_agent;
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

        public int getNet_type() {
            return net_type;
        }

        public void setNet_type(int net_type) {
            this.net_type = net_type;
        }

        public int getOperator() {
            return operator;
        }

        public void setOperator(int operator) {
            this.operator = operator;
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

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
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

        public String getScreen_width() {
            return screen_width;
        }

        public void setScreen_width(String screen_width) {
            this.screen_width = screen_width;
        }

        public String getScreen_height() {
            return screen_height;
        }

        public void setScreen_height(String screen_height) {
            this.screen_height = screen_height;
        }
//        @Override
//        public String toString() {
//            return
//                    "imei='" + imei +
//                    "&imsi='" + imsi +
//                    "&idfa='" + idfa +
//                    "&idfv='" + idfv +
//                    "&device_id='" + device_id +
//                    "&ip='" + ip +
//                    "&mac='" + mac +
//                    "&user_agent='" + user_agent +
//                    "&os=" + os +
//                    "&os_version='" + os_version +
//                    "&net_type=" + net_type +
//                    "&operator=" + operator +
//                    "&vendor='" + vendor +
//                    "&model='" + model +
//                    "&longitude='" + longitude +
//                    "&latitude='" + latitude +
//                    "&package_name='" + package_name +
//                    "&app_version='" + app_version +
//                    "&width='" + width +
//                    "&height='" + height +
//                    "&screen_width='" + screen_width +
//                    "&screen_height='" + screen_height + '\''  ;
//        }
    }

    @Override
    public String toString() {
        return
                "appKey=" + appKey +
                "&slotId=" + slotId +
                "&device=" + new Gson().toJson(device);
    }
}
