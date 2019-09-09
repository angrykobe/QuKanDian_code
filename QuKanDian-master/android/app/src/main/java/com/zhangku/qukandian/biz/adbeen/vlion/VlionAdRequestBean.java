package com.zhangku.qukandian.biz.adbeen.vlion;

public class VlionAdRequestBean {

    private String tagid="";//广告位id  由vlion提供。请到瑞狮SSP后台获取。
    private String appid="";//appid   由vlion分配的唯一id。请到瑞狮SSP后台获取
    private String appname="";//APP名称(utf-8)，需要UrlEncode
    private String pkgname="";//  APP包名
    private String appversion="";//APP版本
    private String osv="";//系统版本
    private String ip="";//设备真实IP地址
    private String make="";//设备制造商  如： samsang
    private String model="";//设备型号
    private String imei="";//对于Android设备，为imei
    private String idfa="";//对于Android设备，填空
    private String anid="";//安卓id
    private String ua="";//手机实际 User-Agent 值 ，需要Urlencode
    private int sw;//设备屏幕宽度
    private int sh;//设备屏幕高度
    private int devicetype;//设备类型   1：手机  2：平板
    private int os;//操作系统  0：未知1：Android2：iOS3：Windows Phone
    private int carrier;//运营商  0：其他1：移动2：联通3：电信
    private int conn;//网络连接类型  0：未知 1：wifi 2：2G 3：3G 4：4G
    private int adt;//创意类型 0：banner，1：插屏，2：开屏，3：原生  11：前贴片，12：中贴片，13：后贴片，              20：内容联盟；30：APP下载列表
    //非必填
    protected int news_pagesize;//内容联盟新闻数量  当广告位是内容联盟类型时，该字段有效。 最大数量不超过20，不填写则默认20。
    protected int news_pageindex;//  内容联盟新闻分页页码  当广告位是内容联盟类型时，该字段有效
    protected String news_cat="";  //内容联盟类目id  当广告位是内容联盟类型时，该字段有效。  填写表示请求一个或多个类目的新闻，不填写则随机返回一个类目的新闻。 可选择多个类目，使用英文逗号分隔，如 ”1001,1002“详细类目id见《内容联盟类目id》
    protected String app_down_nextcur="";//APP下载列表下一页索引  当广告位类型是“APP下载列表”时，该字段有效 第一页填空字符串，第二页索引按照第一页返回的字段 app_down_nextcur 填写，以此类推
    protected String uinfo=""; //用户信息  传入参数需按如下方式编码 url编码 ( base64.encode ( json.encode ( object ) ) )  object字段见下“用户信息”
    protected String mac="";//MAC地址  对于Android设备，建议填
    protected String lon="";//设备所在地理位置经度
    protected String lat="";//设备所在地理位置维度


    public class UserInfo{
        protected String cel;//手机号码，需要将用户的手机号码按照如下文档加密，加密使用的key由puma平台提供
        protected String sex;//性别，0-女，1-男
        protected String age;//年龄
        protected String edu;//学历：1-博士，2-硕士，3-本科，4-专科，5-高中，6-初中，7-小学及以下
        protected String mrg;//婚恋状况：1-已婚，2-未婚，3-单身
        protected String job;//工作，传入职位汉字，如 "产品经理"


    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPkgname() {
        return pkgname;
    }

    public void setPkgname(String pkgname) {
        this.pkgname = pkgname;
    }

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
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

    public int getCarrier() {
        return carrier;
    }

    public void setCarrier(int carrier) {
        this.carrier = carrier;
    }

    public int getConn() {
        return conn;
    }

    public void setConn(int conn) {
        this.conn = conn;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIdfa() {
        return idfa;
    }

    public void setIdfa(String idfa) {
        this.idfa = idfa;
    }

    public String getAnid() {
        return anid;
    }

    public void setAnid(String anid) {
        this.anid = anid;
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

    public int getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(int devicetype) {
        this.devicetype = devicetype;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public int getAdt() {
        return adt;
    }

    public void setAdt(int adt) {
        this.adt = adt;
    }

    public int getNews_pagesize() {
        return news_pagesize;
    }

    public void setNews_pagesize(int news_pagesize) {
        this.news_pagesize = news_pagesize;
    }

    public int getNews_pageindex() {
        return news_pageindex;
    }

    public void setNews_pageindex(int news_pageindex) {
        this.news_pageindex = news_pageindex;
    }

    public String getNews_cat() {
        return news_cat;
    }

    public void setNews_cat(String news_cat) {
        this.news_cat = news_cat;
    }

    public String getApp_down_nextcur() {
        return app_down_nextcur;
    }

    public void setApp_down_nextcur(String app_down_nextcur) {
        this.app_down_nextcur = app_down_nextcur;
    }

    public String getUinfo() {
        return uinfo;
    }

    public void setUinfo(String uinfo) {
        this.uinfo = uinfo;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return  "tagid=" + tagid +
                "&appid=" + appid +
                "&appname=" + appname +
                "&pkgname=" + pkgname +
                "&appversion=" + appversion +
                "&os=" + os +
                "&osv=" + osv +
                "&carrier=" + carrier +
                "&conn=" + conn +
                "&ip=" + ip +
                "&make=" + make +
                "&model=" + model +
                "&imei=" + imei +
                "&idfa=" + idfa +
                "&anid=" + anid +
                "&sw=" + sw +
                "&sh=" + sh +
                "&devicetype=" + devicetype +
                "&ua=" + ua +
                "&adt=" + adt +
                "&news_pagesize=" + news_pagesize +
                "&news_pageindex=" + news_pageindex +
                "&news_cat=" + news_cat +
                "&app_down_nextcur=" + app_down_nextcur +
                "&uinfo=" + uinfo +
                "&mac=" + mac +
                "&lon=" + lon +
                "&lat=" + lat;
    }
}