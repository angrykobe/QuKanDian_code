package com.zhangku.qukandian.config;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/23
 * 你不注释一下？
 */

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

public class AnnoCon {
    //  type:1首页,2视频详情页,3文章详情页,4视频列表,全部 ？？？
    public static final int AD_TYPE_HOME = 1;//1首页
    public static final int AD_TYPE_VIDEO_DETAILS = 2;//2视频详情页
    public static final int AD_TYPE_NEWS_DETAILS = 3;//3文章详情页
    public static final int AD_TYPE_VIDEO = 4;//4视频列表
    public static final int AD_TYPE_OPEN = 6;//
    public static final int AD_TYPE_SIGN = 7;//每日签到广告
    public static final int AD_TYPE_CHAI = 8;//拆宝箱广告
    public static final int AD_TYPE_AGAIN = 9;//补量
    public static final int AD_TYPE_READ_PROGRESS = 10;//阅读进度
    @IntDef({AD_TYPE_HOME, AD_TYPE_VIDEO, AD_TYPE_NEWS_DETAILS,AD_TYPE_VIDEO_DETAILS,AD_TYPE_OPEN,AD_TYPE_SIGN,AD_TYPE_CHAI,AD_TYPE_AGAIN,AD_TYPE_READ_PROGRESS})
    //本地广告类型位置
    public @interface LocAdverType {
    }

    public static final String AD_TYPT_BAIDU      = "百度";//百度
    public static final String AD_TYPT_GDT        = "广点通";//广点通
    public static final String AD_TYPT_360        = "360";//360
    public static final String AD_TYPT_JRTT       = "todaynews";//今日头条
    public static final String AD_TYPT_XW         = "推啊";//推啊
    public static final String AD_TYPT_LC         = "";//
    public static final String AD_TYPT_MYSELF     = "自主";//自主
    public static final String AD_TYPT_HUITOUTIAO = "惠头条API";//"惠头条";
    public static final String AD_TYPT_VLION      = "瑞狮API";     //"瑞狮";
    public static final String AD_TYPT_HUZHONG    = "互众API";   //"互众";
    public static final String AD_TYPT_YUEMENG    = "阅盟API";   //"阅盟";
    public static final String AD_TYPT_ADHUB    = "adhub2018";   //"adhub";
    public static final String AD_TYPT_JIGUANG    = "极光API";   //"极光";
    public static final String AD_TYPT_CHANGJIAN    = "畅江API";   //"畅江";
    public static final String AD_TYPT_SHAIBO    = "晒铂API";   //"晒铂";
    public static final String AD_TYPT_RUANGAO    = "软告API";
    public static final String AD_TYPT_MAIYOU   = "麦游API";
    public static final String AD_TYPT_YITAN   = "艺坛SDK";
    public static final String AD_TYPT_FENGLING="风灵SDK";//风灵广告
    @StringDef({AD_TYPT_BAIDU, AD_TYPT_GDT, AD_TYPT_360,AD_TYPT_JRTT,AD_TYPT_XW,AD_TYPT_LC
            ,AD_TYPT_HUITOUTIAO,AD_TYPT_VLION,AD_TYPT_HUZHONG,AD_TYPT_YUEMENG,AD_TYPT_MYSELF
            ,AD_TYPT_ADHUB,AD_TYPT_JIGUANG,AD_TYPT_CHANGJIAN,AD_TYPT_SHAIBO,AD_TYPT_RUANGAO,
            AD_TYPT_MAIYOU,AD_TYPT_YITAN,AD_TYPT_FENGLING
    })
    //广告类型位置
    public @interface AdverFrom {
    }


    public static final int FROM_WithdrawalsActivity   = 1;
    public static final int FROM_NewMeFragment3        = 2;
    @IntDef({FROM_WithdrawalsActivity,FROM_NewMeFragment3})
    //广告类型位置
    public @interface ActivityFrom {
    }

    public static final int ART_DETAIL_FROM_PUSH = 100;//推送
    public static final int ART_DETAIL_FROM_ORDINARY = -1;//普通
    public static final int ART_DETAIL_FROM_XIGUANG = 102;//犀光
    public static final int ART_DETAIL_FROM_HIGH_PRICE = 103;//高价文
    @IntDef({ART_DETAIL_FROM_PUSH,ART_DETAIL_FROM_ORDINARY,ART_DETAIL_FROM_XIGUANG,ART_DETAIL_FROM_HIGH_PRICE})
    //广告类型位置
    public @interface ArticleFrom {
    }

    /**
     * 用户权限
     */
    public static class UserPower{
        public static final String OPERETION_RIGHTICON = "Induct_Icon";//右下角活动icon
        public static final String OPERATION_TOAST = "operation_toast";//活动入口
        public static final String BOTTOM_MISSION = "answer_missoin";//底部入口
        public static final String REGISTER_COIN = "register_coin";//注册送红包
        public static final String HIGH_PRICE_NEWS = "highprice_news";//高价文权限
        public static final String USER_LEVEL = "user_level";//用户等级权限
        public static final String JURISICTION_FOR_USER_LEVEL = "user_level";//用户等级权限  字段要跟后台一样

        @StringDef({JURISICTION_FOR_USER_LEVEL,OPERETION_RIGHTICON,OPERATION_TOAST,BOTTOM_MISSION
                ,REGISTER_COIN,HIGH_PRICE_NEWS,USER_LEVEL})
        //广告类型位置
        public @interface JurisictionStr {
        }
    }
}
