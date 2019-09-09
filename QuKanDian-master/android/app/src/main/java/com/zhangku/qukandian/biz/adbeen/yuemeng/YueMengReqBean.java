package com.zhangku.qukandian.biz.adbeen.yuemeng;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/24
 * 你不注释一下？
 */
public class YueMengReqBean {
    /**
     * version : 2.1
     * code : 0
     * adChannalCode : ydx20170810cp
     * ip : 120.239.146.223
     * os : 1
     * imei : 353918051707400
     * androidId : a90d99993373c4e4
     * mac : e8:92:a4:99:6f:00
     * imsi : 460005790727022
     * timeStamp : 1479958133
     * validateCode : a6f2a9cbe1cda2576ff2b3296667947d
     * num : 1
     * network : 1
     * model : Nexus 4
     * dt : phone
     * release : 4.4.4
     * appversion : 80
     * manufacturer : LEG
     * brand : google
     * ua : Mozilla/5.0 (Linux; Android 6.0.1; MI 5s Plus Build/MXB48T; wv)  AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/55.0.2883.91 Mobile Safari/537.36
     * board : MAKO
     * hardware : mako
     * display : cm_mako-userdebug 4.4.4 KTU84Q 7b9aa21a34 view_level-keys
     * totalRam : 1914499072
     * totalRom : 1789972480
     * screenWidth  : 1280
     * screenHeight  : 768
     * screenInches  : 4.7
     * userObj : {"lat":39.9960702,"lon":116.4736795,"gender":"M","age":25,"keywords":"白富美;耽美"}
     * extra : {"bookName":"斗破苍穹","author":"天蚕土豆","category":"玄幻"}
     */
    private String code;//String（必填） 渠道编码（由阅盟广告广告 管理员提供）
    private String adChannalCode;//String(必填) 广告位 ID，向阅盟广告申请
    private String version;//String(必填) 接口协议版本号(固定值： 2.1)
    private String ip;//String(必填) 当前移动设备的公网 IP
    private int os;//Integer(必填) 移 动 平 台 （ 1:Android; 2:IOS）
    private String imei;//String（android 必填） Android 为 imei (ios 可以 填写 idfa)
    private String androidId;//String(android 必填) Android id
    private String mac;//String(android 必填) 客户端的 mac 地址
    private String imsi;//String(android 必填) 标识运营商
    private long timeStamp;//Long（必填） Unix 时间戳（10 位长度）
    private String validateCode;//String（必填） 校验码（timeStamp+code 的32 位 MD5 小写值）
    private int network;//Integer(必填) 网络类型 (0:unkown;  1:wifi;2:gprs;3:3g;4:4g)
    private String model;//String(必填) 手机型号
    private String release;//String(必填) 操作系统版本号(如:6.0.1)
    private String appversion;//String(必填) APP 版本号(如 3.0.2)
    private String manufacturer;//String(必填) 制造商
    private String brand;//String(必填) 品牌
    private String ua;//String(必填) 设备 WEBVIEW 的 UA
    private String board;//String(android 必填) 主板
    private String hardware;//String(android 必填) 硬件名称
    private String display;//String(android 必填) 显示屏参数
    private String totalRam;//String(必填) 总 RAM 大小(字节数)
    private String totalRom;//String(必填) 总 ROM 大小(字节数)
    private String screenWidth;//String(必填) 屏幕宽度(像素)
    private String screenHeight;//String(必填) 屏幕高度(像素)
    private String screenInches;//String(必填) 屏幕尺寸(英寸)
    private UserObjBean userObj;//Object(必填) 用户属性信息
    private String device;//String(android 必填) 设备参数

    private ExtraBean extra;//阅读类 APP 必选
    private String dt;//String(可选) 平板（ pad ） 或 手 机 （phone）
    private int num;//Integer(可选) 请求的广告数（默认 1）

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdChannalCode() {
        return adChannalCode;
    }

    public void setAdChannalCode(String adChannalCode) {
        this.adChannalCode = adChannalCode;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getOs() {
        return os;
    }

    public void setOs(int os) {
        this.os = os;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNetwork() {
        return network;
    }

    public void setNetwork(int network) {
        this.network = network;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getTotalRam() {
        return totalRam;
    }

    public void setTotalRam(String totalRam) {
        this.totalRam = totalRam;
    }

    public String getTotalRom() {
        return totalRom;
    }

    public void setTotalRom(String totalRom) {
        this.totalRom = totalRom;
    }

    public String getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(String screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getScreenInches() {
        return screenInches;
    }

    public void setScreenInches(String screenInches) {
        this.screenInches = screenInches;
    }

    public UserObjBean getUserObj() {
        return userObj;
    }

    public void setUserObj(UserObjBean userObj) {
        this.userObj = userObj;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public static class UserObjBean {
        /**
         * lat : 39.9960702
         * lon : 116.4736795
         * gender : M
         * age : 25
         * keywords : 白富美;耽美
         */

        private double lat;//String (必填) 经度
        private double lon;//String (必填) 维度
        private String gender;//
        private int age;//
        private String keywords;//String 分号隔开的用户特征描述

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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
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
    }

    public static class ExtraBean {
        /**
         * bookName : 斗破苍穹
         * author : 天蚕土豆
         * category : 玄幻
         */

        private String bookName;
        private String author;
        private String category;

        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}
