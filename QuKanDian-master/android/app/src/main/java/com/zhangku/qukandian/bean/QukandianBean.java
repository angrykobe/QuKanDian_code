package com.zhangku.qukandian.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/10/31
 * 你不注释一下？
 */
public class QukandianBean {
    /**
     * adHost : qukandian.com
     * adOption : {"nd_list_type":0,"vd_list_type":0}
     * postHost : view_level.api.sjgo58.com
     * host : zghgmyzz.net
     * channelVersion : 20180320
     * grayscaleNum : 3000
     * clientIp : 120.41.186.178
     * talbaoUrl : https://temai.m.taobao.com/index.htm?pid=mm_130941744_42870055_365226977
     * sharearticleamount : 80
     * zhifubaostr : ￥liNvbfkN7j7￥
     * isChestboxShow : 1
     * readingDuration : 10
     * vedioDuration : 10
     * isShowReadMaxTip : 1
     * shoutuposterimage : http://cdn.qu.fi.pqmnz.com/backend/images/20180212/051aeade1ad34fbfac5164f0472f115e_1518426450.jpg
     * shoutuostertext : 答题分钱，填邀请码:{yqm}，得复活卡，送新春红包
     * isGetInstallPacs : 1
     * dataKey : 8B1BE37432F64260A2D250C66269582A
     * currentTimeStamp : 1.540972692E9
     * pubwebUrl : http://pubweb.sjgo58.com/
     * pakUrl : http://t.cn/Rr6fUUR
     * loginOrder : 11111
     * registerCoinTxt : 打开最高得5元
     * pushType : 0
     * staticPageHost : http://spage.sjgo58.com
     * isOpen : 0
     * isShowYesterdayGold : 1
     * isShowTaskChestBox : 0
     */
//    private String adHost;//
//    private AdOptionBean adOption;//
    private String postHost;////分享文章域名
    private String host;////分享收徒域名
    private int channelVersion;//频道数据变化的版本
    private int grayscaleNum;//灰度userid小于 10000+grayscaleNum 更新
    private String clientIp;//客户端ip
    private String talbaoUrl;//淘宝地址
    private String sharearticleamount = "20";//
    private String zhifubaostr;//
    private String isChestboxShow;//是否显示宝箱 1显示 其他不显示
    private String readingDuration;//文章有效阅读时间
    private String vedioDuration;//视频有效阅读时间
    private String isShowReadMaxTip;//是否提示用户有效阅读已经达到最大次数，1 需要 其他不提示
    //    private String shoutuposterimage;//
//    private String shoutuostertext;//
    private String isGetInstallPacs;//是否获取竞品安装消息
    private String dataKey;////秘钥
    private double currentTimeStamp;////服务端时间
    private String pubwebUrl;////h5网页公共地址
    private String pakUrl;//安装包下载地址
    private String loginOrder = "11111";//loginOrder=11010
    private String registerCoinTxt = "打开最高得10元";//新手红包文案  registerCoinTxt=打开最高得5元
    private String staticPageHost;//静态页地址
    private int isOpen;//文章详情页面是否显示点击展开全文按钮  //1：展开；0或空：不自动展开
    private int isShowYesterdayGold;//判断是否显示昨日战报弹窗 0：不显示，1：显示
    private int isShowTaskChestBox;//判断是否显示昨日战报弹窗 0：不显示，1：显示
    private ArrayList<WithdrawalsRemindBean> heben;
    private int loadCount = 10;
    private boolean isShowMask;//（是否显示任务页的前两个蒙层，true：显示，false：不显示）
    ////////291
    private int highPriceGold;//高价文分享金币
    ////////292
    private String novelUrl;//小说域名
    //////////294
    private String userRegisterNum = "22928173";
    private String checkindesc = "";
    ///////295
//3.RsCnt = -1 阈值不在范围内，不显示热搜tab
//4.RsCnt = 0 ,置灰
//5.RsCnt > 0，正常显示红包数
    private int rSCnt;

    private String gyro;

    private String protocolUrl;//登录页-隐私政策
    private String henbenH5Url;//提现更多福利页面路径

    private ReadProgressBean readprogress;//阅读进度配置

    private int adsLog;// 控制广告日志是否记录和上传 为“1”时记录广告日志并上传

    public int getAdsLog() {
        return adsLog;
    }

    public void setAdsLog(int adsLog) {
        this.adsLog = adsLog;
    }

    public ReadProgressBean getReadprogress() {
        return readprogress;
    }

    public void setReadprogress(ReadProgressBean readprogress) {
        this.readprogress = readprogress;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }

    public void setProtocolUrl(String protocolUrl) {
        this.protocolUrl = protocolUrl;
    }

    public String getHenbenH5Url() {
        return henbenH5Url;
    }

    public void setHenbenH5Url(String henbenH5Url) {
        this.henbenH5Url = henbenH5Url;
    }

    public int getRscnt() {
        return rSCnt;
    }

    public void setRscnt(int rscnt) {
        this.rSCnt = rscnt;
    }

    public String getGyro() {
        return gyro;
    }

    public void setGyro(String gyro) {
        this.gyro = gyro;
    }

    private int adsCnt;

    public String getCheckindesc() {
        return checkindesc;
    }

    public void setCheckindesc(String checkindesc) {
        this.checkindesc = checkindesc;
    }

    public String getUserRegisterNum() {
        return userRegisterNum;
    }

    public void setUserRegisterNum(String userRegisterNum) {
        this.userRegisterNum = userRegisterNum;
    }

    public String getNovelUrl() {
        return novelUrl;
    }

    public void setNovelUrl(String novelUrl) {
        this.novelUrl = novelUrl;
    }

    public int getHighPriceGold() {
        return highPriceGold;
    }

    public void setHighPriceGold(int highPriceGold) {
        this.highPriceGold = highPriceGold;
    }

    public boolean isShowMask() {
        return isShowMask;
    }

    public void setShowMask(boolean showMask) {
        isShowMask = showMask;
    }

    public int getLoadCount() {
        return loadCount;
    }

    public void setLoadCount(int loadCount) {
        this.loadCount = loadCount;
    }

    public ArrayList<WithdrawalsRemindBean> getHeben() {
        return heben;
    }

    public void setHeben(ArrayList<WithdrawalsRemindBean> heben) {
        this.heben = heben;
    }

    public String getPostHost() {
        return postHost;
    }

    public void setPostHost(String postHost) {
        this.postHost = postHost;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getChannelVersion() {
        return channelVersion;
    }

    public void setChannelVersion(int channelVersion) {
        this.channelVersion = channelVersion;
    }

    public int getGrayscaleNum() {
        return grayscaleNum;
    }

    public void setGrayscaleNum(int grayscaleNum) {
        this.grayscaleNum = grayscaleNum;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getTalbaoUrl() {
        return talbaoUrl;
    }

    public void setTalbaoUrl(String talbaoUrl) {
        this.talbaoUrl = talbaoUrl;
    }

    public String getSharearticleamount() {
        return sharearticleamount;
    }

    public void setSharearticleamount(String sharearticleamount) {
        this.sharearticleamount = sharearticleamount;
    }

    public String getZhifubaostr() {
        return zhifubaostr;
    }

    public void setZhifubaostr(String zhifubaostr) {
        this.zhifubaostr = zhifubaostr;
    }

    public String getIsChestboxShow() {
        return isChestboxShow;
    }

    public void setIsChestboxShow(String isChestboxShow) {
        this.isChestboxShow = isChestboxShow;
    }

    public String getReadingDuration() {
        return readingDuration;
    }

    public void setReadingDuration(String readingDuration) {
        this.readingDuration = readingDuration;
    }

    public String getVedioDuration() {
        return vedioDuration;
    }

    public void setVedioDuration(String vedioDuration) {
        this.vedioDuration = vedioDuration;
    }

    public String getIsShowReadMaxTip() {
        return isShowReadMaxTip;
    }

    public void setIsShowReadMaxTip(String isShowReadMaxTip) {
        this.isShowReadMaxTip = isShowReadMaxTip;
    }

    public String getIsGetInstallPacs() {
        return isGetInstallPacs;
    }

    public void setIsGetInstallPacs(String isGetInstallPacs) {
        this.isGetInstallPacs = isGetInstallPacs;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public double getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(double currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public String getPubwebUrl() {
        return pubwebUrl;
    }

    public void setPubwebUrl(String pubwebUrl) {
        this.pubwebUrl = pubwebUrl;
    }

    public String getPakUrl() {
        return pakUrl;
    }

    public void setPakUrl(String pakUrl) {
        this.pakUrl = pakUrl;
    }

    public String getLoginOrder() {
        return loginOrder;
    }

    public void setLoginOrder(String loginOrder) {
        this.loginOrder = loginOrder;
    }

    public String getRegisterCoinTxt() {
        return registerCoinTxt;
    }

    public void setRegisterCoinTxt(String registerCoinTxt) {
        this.registerCoinTxt = registerCoinTxt;
    }

    public String getStaticPageHost() {
        return staticPageHost;
    }

    public void setStaticPageHost(String staticPageHost) {
        this.staticPageHost = staticPageHost;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getIsShowYesterdayGold() {
        return isShowYesterdayGold;
    }

    public void setIsShowYesterdayGold(int isShowYesterdayGold) {
        this.isShowYesterdayGold = isShowYesterdayGold;
    }

    public int getIsShowTaskChestBox() {
        return isShowTaskChestBox;
    }

    public void setIsShowTaskChestBox(int isShowTaskChestBox) {
        this.isShowTaskChestBox = isShowTaskChestBox;
    }

    public int getAdsCnt() {
        return adsCnt;
    }

    public void setAdsCnt(int adsCnt) {
        this.adsCnt = adsCnt;
    }

    public static class AdOptionBean {
        /**
         * nd_list_type : 0
         * vd_list_type : 0
         */

        private int nd_list_type;
        private int vd_list_type;

        public int getNd_list_type() {
            return nd_list_type;
        }

        public void setNd_list_type(int nd_list_type) {
            this.nd_list_type = nd_list_type;
        }

        public int getVd_list_type() {
            return vd_list_type;
        }

        public void setVd_list_type(int vd_list_type) {
            this.vd_list_type = vd_list_type;
        }
    }
}
