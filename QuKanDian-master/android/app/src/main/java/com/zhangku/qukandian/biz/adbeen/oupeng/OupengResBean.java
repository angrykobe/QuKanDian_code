package com.zhangku.qukandian.biz.adbeen.oupeng;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/19
 * 反应实体
 */
public class OupengResBean {
    private double expiration_time;
    private int error_code;
    private String request_id;
    private List<AdsBean> ads;

    public double getExpiration_time() {
        return expiration_time;
    }

    public void setExpiration_time(double expiration_time) {
        this.expiration_time = expiration_time;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public static class AdsBean {
        public String adslot_id;
        public List<MetaGroupBean> meta_group = new ArrayList<>();
        public List<AdTrackingBean> ad_tracking = new ArrayList<>();//上报 URL 列表，必须由客户端上 报

        public static class MetaGroupBean {
            public int video_duration;
            public String interaction_type;//当广告被点击后，发生的交互行 为类型。对不同交互类型的广告， 处理方式有差异：
                                            // 1）打开网页（WB）类型，建议  使用应用内 Webview 打开，以免跳出应用；
                                            // 2）直接下载类 （DOWNLOAD），此类广告 clk_url 字段就是 APP 下载地址； 下载类建议页面显示下载按钮， 下载类广告只出现在安卓设备
            public String video;//视频播放地址
            public boolean clk_url_have_macro;//true 表示 clk_url 中存在需要替 换的点击坐标宏定义，否则表示 没有。宏定义请参考 附录 5 点击  坐标宏定义
            public String material_type;//物料类型，参见 附录 2 物料类型  material_type=SPLASH
            public String video_ldpg_html;//
            public String clk_url;//点击行为地址，会经过多次 302 跳转最终到达目标地址
            public String video_click_mode;//视频广告必须返回，视频广告的 点击模式
                                           //DEFAULT 播放过程中全屏可点，落地页全屏可点
                                           //LANDING_PAGE_FULL_SCREEN 播放过程中不可点击，落地页全屏可点
                                           //LANDING_PAGE_BUTTON 播放过程中不可点击，落地页下载按钮可点
            public String app_package_name;//下载类广告包名
            public String title = "";//
            public String icon ;//
            public String advertisement = "";//
        }

        public static class AdTrackingBean {
            public String have_macro;//表示事件监控 URL 中是否存在需 要替换的宏定义，YES 表示存在， NO 表示不存在。
            public List<String> tracking_url;//事件监控 URL
            public int point;//tracking_event 是 VIDEO_AD_PLAY 时有效
            public String tracking_event;//广告展示过程事件类型
            /**
             * AD_IMPRESSION 展现上报
             * AD_CLICK 点击上报
             * DOWN_LOAD_START 下载开始上报
             * DOWN_LOAD_END 下载完成上报
             * INSTALL_START 安装开始上报
             * INSTALL_END 安装完成上报
             * VIDEO_AD_START 视频开始播放上报
             * VIDEO_AD_END 视频正常播放结束上报
             * VIDEO_AD_PLAY 视频播放中上报
             * VIDEO_AD_CLOSE 视频关闭上报
             * VIDEO_LDP_PV 视频广告落地页展示上报
             * VIDEO_LDP_CLICK 视频广告落地页点击上报
             * VIDEO_LDP_CLOSE 视频广告落地页关闭上报
             * ACTIVE_END 应用激活上报信息
             * */
        }
    }
}
