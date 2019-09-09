package com.zhangku.qukandian.biz.adbeen.changjiang;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/20
 * 你不注释一下？
 */
public class ChangjiangResBean {
    private int code;//	int	是	相应状态码 1=>成功 其他=>失败
    private String msg;//String	是	返回信息
    private AdBean ad;//	否	当code为1时，该参数存在

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AdBean getAd() {
        return ad;
    }

    public void setAd(AdBean ad) {
        this.ad = ad;
    }

    public static class AdBean{
        private int ad_type;//Int	是	广告类型 0-下载类 1-跳转类
        private String title;//String	是	标题
        private String desc;//String	是	描述
        private List<String> image_urls;//Array of String	是	数组类型，素材图片地址
        private String ad_url;//String	否	落地页url 跳转类广告一点有此字段
        private List<String> show_urls;//Array of String	是	曝光上报地址，数组格式，当广告位曝光时请求上报地址
        private List<String> click_urls;//Array of String	是	点击上报地址，数组格式，当点击广告位时请求上报地址
        private String download_url;//String	否	下载类广告有此字段
        private List<String> download_urls;//Array of String	否	下载类广告开始下载上报url
        private List<String> downloaded_urls;//Array of String	否	下载类广告下载完成上报url
        private List<String> installed_urls;//Array of String	否	下载类广告安装完成上报url
        private AppBean app;//	否	app下载类可能包含的信息

        public int getAd_type() {
            return ad_type;
        }

        public void setAd_type(int ad_type) {
            this.ad_type = ad_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<String> getImage_urls() {
            return image_urls;
        }

        public void setImage_urls(List<String> image_urls) {
            this.image_urls = image_urls;
        }

        public String getAd_url() {
            return ad_url;
        }

        public void setAd_url(String ad_url) {
            this.ad_url = ad_url;
        }

        public List<String> getShow_urls() {
            return show_urls;
        }

        public void setShow_urls(List<String> show_urls) {
            this.show_urls = show_urls;
        }

        public List<String> getClick_urls() {
            return click_urls;
        }

        public void setClick_urls(List<String> click_urls) {
            this.click_urls = click_urls;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public List<String> getDownload_urls() {
            return download_urls;
        }

        public void setDownload_urls(List<String> download_urls) {
            this.download_urls = download_urls;
        }

        public List<String> getDownloaded_urls() {
            return downloaded_urls;
        }

        public void setDownloaded_urls(List<String> downloaded_urls) {
            this.downloaded_urls = downloaded_urls;
        }

        public List<String> getInstalled_urls() {
            return installed_urls;
        }

        public void setInstalled_urls(List<String> installed_urls) {
            this.installed_urls = installed_urls;
        }

        public AppBean getApp() {
            return app;
        }

        public void setApp(AppBean app) {
            this.app = app;
        }

        public static class AppBean{
            private String icon;//String	否	app 图标icon地址
            private String app_name;//String	否	app名称
            private String package_name;//String	否	app包名

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getApp_name() {
                return app_name;
            }

            public void setApp_name(String app_name) {
                this.app_name = app_name;
            }

            public String getPackage_name() {
                return package_name;
            }

            public void setPackage_name(String package_name) {
                this.package_name = package_name;
            }
        }

    }
}
