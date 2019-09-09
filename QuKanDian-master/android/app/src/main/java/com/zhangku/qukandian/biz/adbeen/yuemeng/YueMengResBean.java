package com.zhangku.qukandian.biz.adbeen.yuemeng;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/24
 * 阅盟广告返回参数
 */
public class YueMengResBean {

    /**
     * ads : [{"bannerType":"0","content":"广告描述","text_title":"广告标题","ad-hot-action-type":"show","ad_pic":"http://ubmcmm.baidustatic.com/media/v1/0f0002sEjOGcGLRyNXP8Fs.jp\r\ng","id":"9476e760d0e09bd03270d2fb92205af2","clickurl":["http://ad.ipadview.com/dspapi/ad/clickurl/2002?uuid=1100376968&pro\r\nduct=505&imei=353918051707400&cc=7C6760044E9222D15C52140BF\r\n8F704B3&channalCode=03BA03708A78FB6055DF7969D280A717&sourc\r\ne=ydt&dspChannalCode=4356372"],"showurl":["http://wn.pos.baidu.com/adx.php?c=d25pZD0xMTUwYzQzZTQ0YWU5Y\r\nmM5XzAAcz0xMTUwYzQzZTQ0YWU5YmM5AHQ9MTUwNzYyMDEzNQBz\r\nZT0yAGJ1PTgAcHJpY2U9V2R4MUp3QUw0S2g3akVwZ1c1SUE4c05IYW\r\nF5RWpncnFuTlRRdEEAY2hhcmdlX3ByaWNlPTQ5NQBzaGFyaW5nX3Bya\r\nWNlPTQ5NTAwMAB3aW5fZHNwPTgAY2htZD0wAGJkaWQ9AGNwcm9pZ\r\nD0Ad2Q9ODI3NDQ0NwBwb3M9MABiY2htZD0wAHY9MQBpPTM2NzgxYT\r\nNi&ext=cHJpY2U9NDUwMCZwbGFuaWQ9Mjc4NjIz","http://ad.ipadview.com/dspapi/ad/showurl/2002?uuid=1100376968&prod\r\nuct=505&imei=353918051707400&cc=7C6760044E9222D15C52140BF8\r\nF704B3&channalCode=03BA03708A78FB6055DF7969D280A717&source\r\n=ydt&dspChannalCode=4356372"],"ad_id":"2002","ad-hot-action-param":"{\"url\":\"http://pos.baidu.com/zmclick.php?060000aOQtdPRPBMeOnXWP\r\nk6vDbJl5r8PTNtjC7MxOma1IpNcs94nq2nw6sskC2qDro4iDcSfiKIntnp_toLE2b35\r\niQ1CNLwQAiWGt7OvYsQ2V20TB0xjLrji0XFAGGPGrnCLIawn87LbQbRiT7\r\nJ-JC5YmY_hE8l18_KZVP37Tx3c0R0.DY_jI_wKLk_Y2hPH_LjN6u1fIt7jHzk8sHfGmWHG\r\n1L_3_Axv5swd5s4rh5ZGmH8uerMxktVH_LjN6u1u3TTrxVuxWxhs4rMKfYt_QrMAzONDkAhWws4rMKfYt8-\r\nP1tA-BZZjdEukmrgVHCmooLyfqM75uvnGLLsedBmH8uerMxk4mTTzs1f_Nq-XeTC0.mLKGujY0uhzTLwxIZF9uARqn1R0TvNWUv4bgLwzmyw5HndnjD0TLw4UANxpyfqn0KsThqvpy4WuHY1n6KWpgw45Hc3n0KWpAd\r\nb5H00TvPCUyfqn0K1TjYk0ZI_5HD0mv99Uh4-UjYs0ZN1ugFxIZsuHYs0AN3mv99UhI-gLw4TARqn0KVmgwWp7qWUvw5H00mywWUA71T1YYP6KYIHYYn1Rvn1Tz0ZwV5HcLn1R4nHbzPfKYujY\r\ns0Zw9TWYs0ZP1TjYz0Au85yRdmHfvmHb1gvPsT6KYIZ0qnsK8IZws5Hn\r\n0mMcqn0KbuguGmvNxpyfqnsKbIZ0qnsKdThsq0APC5fKhUMcqn0K1UW\r\nY1PHn4nH6sPHDLnjTYnj0VuH6lrHclmHflrHblPhmlnj00mgwEUNqGujYs0\r\nAd-uA-9gLw4TARqn0K8UL0qn0KbuguxIZ0qHDI7QR4-\r\nXZN1cjf0uAPxmLNGujY0Tvwo5HR8na3z0ZPbpdqv5Hf0uAPxThNkgLKou\r\n1dWUvY8IA7bIi4Ypy78UANzQh78uZFEpyf0myPWIjd9TAb0my4bThqGu7q\r\nGujd9rHKbrHb4rHn1P1PWPARY0A4-I7qYXgK-gv-\nb5HD0mgKxpyfqn0K9TZKGujdPyDYPhD4nsKETdqGujYzQHf8P0KBTh78ujd_uvR0Iv7sgvPEUvVGuHY0TL\r\nPsnWYs0APYp1Y0IvbqP6K9u7q15HDkPHKWPjP-Pjw9uH-Bm1-\r\nxn0K9TZKWmgw-gv-b5fKdThkxmgKsmv7YuNqGujY0UAF1gvb5fKBIgPGUhN1TdqGujY0uA-1IZFGmLwxpyfq0A7WIAvuNq9TZ0q0A7WIA-vuNqWmgw-uvqzXHY0uh-zTLwxpgfq0ZPmvq8u7qGIjY0TA9EUhRqn0KWUAV9IZws5yPCmgFMufK1pAqL5H00mvk\r\nGmv_qnfK8IjYz0AqsI7qYXgK5H00myPY5HD0TgwV5HDdnjTvnW0kn1R0Thnq0ZF9Uh_qn0K35H00TZF\r\nEujdzTLK_mgPC0AFMIZ0qn0KGIMwz5H00ugFsmvqbuHdfnW6dnfK9mLw\r\nGIh7YuHYs0Aw1TAb5Hm0TLFWgLIG5HDv0ZKWIMcqn0KMuy4bugcqn0K9uvRqn0KWThnqn\r\nHTLr00\",\"target\":\"0\"}"}]
     * resultCode : 0
     */
    private String resultCode;//状态码（1,失败；0,成功）
    private List<AdsBean> ads;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public static class AdsBean {
        /**
         * bannerType : 0
         * content : 广告描述
         * text_title : 广告标题
         * ad-hot-action-type : show
         * ad_pic : http://ubmcmm.baidustatic.com/media/v1/0f0002sEjOGcGLRyNXP8Fs.jpg
         * id : 9476e760d0e09bd03270d2fb92205af2
         * clickurl : ["http://ad.ipadview.com/dspapi/ad/clickurl/2002?uuid=1100376968&pro\r\nduct=505&imei=353918051707400&cc=7C6760044E9222D15C52140BF\r\n8F704B3&channalCode=03BA03708A78FB6055DF7969D280A717&sourc\r\ne=ydt&dspChannalCode=4356372"]
         * showurl : ["http://wn.pos.baidu.com/adx.php?c=d25pZD0xMTUwYzQzZTQ0YWU5Y\r\nmM5XzAAcz0xMTUwYzQzZTQ0YWU5YmM5AHQ9MTUwNzYyMDEzNQBz\r\nZT0yAGJ1PTgAcHJpY2U9V2R4MUp3QUw0S2g3akVwZ1c1SUE4c05IYW\r\nF5RWpncnFuTlRRdEEAY2hhcmdlX3ByaWNlPTQ5NQBzaGFyaW5nX3Bya\r\nWNlPTQ5NTAwMAB3aW5fZHNwPTgAY2htZD0wAGJkaWQ9AGNwcm9pZ\r\nD0Ad2Q9ODI3NDQ0NwBwb3M9MABiY2htZD0wAHY9MQBpPTM2NzgxYT\r\nNi&ext=cHJpY2U9NDUwMCZwbGFuaWQ9Mjc4NjIz","http://ad.ipadview.com/dspapi/ad/showurl/2002?uuid=1100376968&prod\r\nuct=505&imei=353918051707400&cc=7C6760044E9222D15C52140BF8\r\nF704B3&channalCode=03BA03708A78FB6055DF7969D280A717&source\r\n=ydt&dspChannalCode=4356372"]
         * ad_id : 2002
         * ad-hot-action-param :
         */

        private String bannerType;//样式类型(0:大图；1:图片+ 标题+描述)
        private String content;//广告描述
        private String text_title;//广告标题
        private String desc;//角标
        @SerializedName("ad-hot-action-type")//
        private String adhotactiontype;//广告类型（ install: 安 装;show:展示）

        private String ad_pic;//图片地址(单图广告)
//        private List<String> ad_pics;//ad_pics JSON 数组 图片地址列表(多图广告)
        private String id;//
        private String ad_id;//
        @SerializedName("ad-hot-action-param")//
        private String adhotactionparam;//JSON 格式动作参数
        private List<String> clickurl;//点击上报地址
        private List<String> showurl;//展示上报地址

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getAdhotactionparam() {
            return adhotactionparam;
        }

        public void setAdhotactionparam(String adhotactionparam) {
            this.adhotactionparam = adhotactionparam;
        }

        public String getBannerType() {
            return bannerType;
        }

        public void setBannerType(String bannerType) {
            this.bannerType = bannerType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getText_title() {
            return text_title;
        }

        public void setText_title(String text_title) {
            this.text_title = text_title;
        }

        public String getAdhotactiontype() {
            return adhotactiontype;
        }

        public void setAdhotactiontype(String adhotactiontype) {
            this.adhotactiontype = adhotactiontype;
        }

        public String getAd_pic() {
            return ad_pic;
        }

        public void setAd_pic(String ad_pic) {
            this.ad_pic = ad_pic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
        }

        public List<String> getClickurl() {
            return clickurl;
        }

        public void setClickurl(List<String> clickurl) {
            this.clickurl = clickurl;
        }

        public List<String> getShowurl() {
            return showurl;
        }

        public void setShowurl(List<String> showurl) {
            this.showurl = showurl;
        }


        public static class ActionParam{
            private String target;//show 类型广告才有（0:内置 浏览器；1:外置浏览器）
            private String url;//下载地址或者落地页地址
            private List<String> cpd_report_urls;//安装类（用于获取下载完成 上报）
            private List<String> cpa_report_urls;//安装类（用于获取激活完成 上报）
            private String app_name;//
            private long app_size;//Apk 包大小(APP 可以进行下 载提示)
            private String app_package;//

            public String getTarget() {
                return target;
            }

            public void setTarget(String target) {
                this.target = target;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public List<String> getCpd_report_urls() {
                return cpd_report_urls;
            }

            public void setCpd_report_urls(List<String> cpd_report_urls) {
                this.cpd_report_urls = cpd_report_urls;
            }

            public List<String> getCpa_report_urls() {
                return cpa_report_urls;
            }

            public void setCpa_report_urls(List<String> cpa_report_urls) {
                this.cpa_report_urls = cpa_report_urls;
            }

            public String getApp_name() {
                return app_name;
            }

            public void setApp_name(String app_name) {
                this.app_name = app_name;
            }

            public long getApp_size() {
                return app_size;
            }

            public void setApp_size(long app_size) {
                this.app_size = app_size;
            }

            public String getApp_package() {
                return app_package;
            }

            public void setApp_package(String app_package) {
                this.app_package = app_package;
            }
        }
    }
}
