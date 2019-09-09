package com.zhangku.qukandian.biz.adcore;

public class AdConfig {
    /**
     * 正式环境
     * http://api.adxhi.com/frontend/api/ad/pull
     * 正式环境  渠道ID：2104839339   广告位ID：2203741139
     * <p>
     * 测试环境
     * http://211.95.56.10:9980/frontend/api/ad/pull
     * 测试环境  渠道ID：2108793756   广告位ID：2208980748
     */
    // 惠头条广告配置信息
    public static final String huitoutiao_address = "http://api.adxhi.com/frontend/api/ad/pull";
    // 渠道ID
    public static final String huitoutiao_cids = "2104839339";
    // 广告位ID
    public static final String huitoutiao_pids = "2203741139";

    // 瑞狮广告配置信息
    public static final String vlion_address = "http://a.vlion.cn/ssp?";
    // 渠道ID
    public static final String vlion_tagid = "21468";//广告位id 由vlion提供。请到瑞狮SSP后台获取。
    // 广告位ID
    public static final String vlion_appid = "20469";//appid 由vlion分配的唯一id。请到瑞狮SSP后台获取

    /**
     * 正式环境  http://x.fastapi.net/s2s?{PARAMS}   1029819
     * 测试环境  http://sx.g.fastapi.net/s2s?{PARAMS} 。    5001026
     * {PARAMS}是HTTP GET参数，格式如：key1=value1&key2=value2，所有的value需要urlencode。
     */
    // 互众
    public static final String huzhong_address = "http://x.fastapi.net/s2s?";
    //    public  static final String huzhong_address = "http://sx.g.fastapi.net/s2s?";
    // 广告位ID
    public static final String huzhong_appid = "1029819";

    //阅盟
    public static final String yuemeng_address = "http://ad.ipadview.com/dspapi/ad/getAd/";//
    public static final String yuemeng_appid_for_code = "10671";//广告APPID
    public static final String yuemeng_for_channal_small_img = "qkda2018072401xxl";//小图
    public static final String yuemeng_appid = "10671";//广告APPID
    public static final String yuemeng_read_appid = "8023";//阅读奖励APPID

    //旺脉
    public static final String wangmai_address = "http://api.mssp.adwangmai.com/v1.api";//
    //yomob
//    public  static final String yomob_address = "https://adxapitest.yomob.com/adx/adsense/yomobAdServing";////测试请求
    public static final String yomob_address = "https://adservingapi.yomob.com/adx/adsense/yomobAdServing";////yomob正式请求

    //畅江
    public static final String changjiang_address = "http://adapi.star-galaxy.cn/apis/ssp/infomation/";//
    public static final String changjiang_id = "800718";//
    public static final String changjiang_key = "XlCpZimll1qRkwVRiUDt";//
    //晒铂
//    信息流_安卓
//    媒体ID：100575
//    key：574af2fe8bf2d63116d5f9dd33d49267
//    广告位ID：10001603  10001604
    public static final String saibo_address = "http://a.sellbuyads.net/shuttle";//
    public static final String saibo_id = "10001603";//
    public static final String saibo_key = "574af2fe8bf2d63116d5f9dd33d49267";//
    public static final String saibo_appid = "100575";//
    //软告
//    测试地址：http://uattest1.bfsspadserver.8le8le.com/common/adrequest9
//    正式地址：http://uat1.bfsspadserver.8le8le.com/common/adrequest
    public static final String ruangao_address = "http://uat1.bfsspadserver.8le8le.com/common/adrequest";//
//    public static final String ruangao_address = "http://uattest1.bfsspadserver.8le8le.com/common/adrequest";//
    public static final String ruangao_tagid = "1001743";//
    public static final String ruangao_siteid = "800136";//appid

    //麦游
    public static final String maiyou_address = "http://api.winnershang.com:5500";//
    public static final String maiyou_appid = "1ab564bfac8923da2810b7f1756315b8";//
    public static final String maiyou_codeid = "vEE6lpzI";//
    public static final String maiyou_codeid_for_splash = "PsKJghk1";//
//    138 开屏
//    139 信息流
    public static final String yitan_appid = "id8aea743fe56e77";//
    public static final String yitan_codeid = "139";//
    public static final String yitan_splash_codeid = "138";//
    //欧朋
    public static final String oupeng_address = "http://c.adbxb.cn/b/ads";//
    public static final String oupeng_appid_for_code = "929778674";//广告APPID
//    public static final String oupeng_appid_for_code = "-338550034";//广告APPID---测试


    //云告-搜索赚
    public static final String yungao_string = "搜索赚";//
//    public static final String yungao_key = "59f82e69d44df";//测试
    public static final String yungao_key = "5ce600833dcd5";//正式

    //乐真试玩
    public static final String lezhen_game_cid = "1494";//正式
//    public static final String lezhen_game_cid = "1001";//测试
    public static final String lezhen_game_StringKey= "dTOsiyYRQOGZxgoIBtq1JsZFlTUZqDgz";//秘钥 正式
//    public static final String lezhen_game_StringKey= "fdsmkfshdik423432";//秘钥 测试

    //阅盟小说
    public static final String yuemeng_novel_appid = "8023";//小说appid

   //风灵聚合广告
   public static final String touTiao_AdAppid = ""; // 头条广告平台的 appid（如果没有使用头条广告平台传入""即可）
   public static final String felink_AdAppid = "429d0c2a9f1e4b37aa02ae6c8af40585"; // 风灵聚合广告appid
   public static final String felink_bannerid="101042";//banner广告位id
   public static final String felink_spalshid="101041";//开屏广告位id
   public static final String felink_infoflowid="101043";//信息流广告位id
}
