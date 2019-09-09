package com.zhangku.qukandian.biz.adbeen.huzhong;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/6/26
 * 互众请求回调bean
 */
public class HuzhongAdResponseBean {
    /**
     * pt : 3
     * ad : [{"width":220,"sid":"5001026","app":{"icon_url":"http://sx.pic.fastapi.net/upload/2016/06/d7ada0276744162efb83ff627557df21.webp","name":"测试包"},"src":"http://sx.pic.fastapi.net/upload/2016/06/d7ada0276744162efb83ff627557df21.webp","template_type":"1","height":150,"aid":"12426","imp":{"0":["http://sx.l.fastapi.net/imp?e=0zenVsnYvTUtiq_32d8FYQeiz-vRn-JfPD1cUymursoTRc2YTbp2hKAq6bCVVgKkZTxO4AZYoVKJYJqaUctfcsDGrL70WgxnetQ_mYe2MrD54kY-i8zAlTke0dcswCfkb3E8V8yPdw.4.ed701d7c&pid=5001026.235-40.whvprj.fj.2.pax38u.1ea8&sid=5001026","http://www.bing.com?imp=123","http://www.bing.com?imp=456","http://www.bing.com?imp=789"]},"clk":["http://www.bing.com?clk=123","http://www.bing.com?clk=456","http://www.bing.com?clk=789"],"desc":"这是测试图文webp","url":"http://sx.c.fastapi.net/j?e=wyu6M4eSj2NgyavE3vgRYQei8-PQmOVeLHwHPSnG-8IUTMSQSbxxlsM-_uSTJVL4NSgPhBAE93UYmkNB4gkdMpv0iJ_YG0hhZt1oioi5fOeqoVg705zLnDke0tEpnXrkbXM8Us6DdLBbCgINMIQFE7iVxrF0MjDB1os8z6h-0d9-4ejY.4.4db1a9a7&pvid=5001026.235-40.whvprj.fj.2.pax38u.1ea8&cpt_cp=__AZCX__%2C__AZCY__&cpt_fmp=__AZMX__%2C__AZMY__&cpt_pixel=__WIDTH__%2C__HEIGHT__&url=http%3A%2F%2Fwww.baidu.com","action":1,"title":"图文webp","mime":"icon"}]
     * version : 1
     */
    private int pt;// AdExchange系统内部处理花费的时间
    private String version; // 数据协议版本号
    private List<AdBean> ad; // 广告素材，数组形式，目前最多只有一个素材，后期可能会扩展支持多个

    public int getPt() {
        return pt;
    }

    public void setPt(int pt) {
        this.pt = pt;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<AdBean> getAd() {
        return ad;
    }

    public void setAd(List<AdBean> ad) {
        this.ad = ad;
    }

    public static class AdBean {
        /**
         * width : 220
         * sid : 5001026
         * app : {"icon_url":"http://sx.pic.fastapi.net/upload/2016/06/d7ada0276744162efb83ff627557df21.webp","name":"测试包"}
         * src : http://sx.pic.fastapi.net/upload/2016/06/d7ada0276744162efb83ff627557df21.webp
         * template_type : 1
         * height : 150
         * aid : 12426
         * imp : {"0":["http://sx.l.fastapi.net/imp?e=0zenVsnYvTUtiq_32d8FYQeiz-vRn-JfPD1cUymursoTRc2YTbp2hKAq6bCVVgKkZTxO4AZYoVKJYJqaUctfcsDGrL70WgxnetQ_mYe2MrD54kY-i8zAlTke0dcswCfkb3E8V8yPdw.4.ed701d7c&pid=5001026.235-40.whvprj.fj.2.pax38u.1ea8&sid=5001026","http://www.bing.com?imp=123","http://www.bing.com?imp=456","http://www.bing.com?imp=789"]}
         * clk : ["http://www.bing.com?clk=123","http://www.bing.com?clk=456","http://www.bing.com?clk=789"]
         * desc : 这是测试图文webp
         * url : http://sx.c.fastapi.net/j?e=wyu6M4eSj2NgyavE3vgRYQei8-PQmOVeLHwHPSnG-8IUTMSQSbxxlsM-_uSTJVL4NSgPhBAE93UYmkNB4gkdMpv0iJ_YG0hhZt1oioi5fOeqoVg705zLnDke0tEpnXrkbXM8Us6DdLBbCgINMIQFE7iVxrF0MjDB1os8z6h-0d9-4ejY.4.4db1a9a7&pvid=5001026.235-40.whvprj.fj.2.pax38u.1ea8&cpt_cp=__AZCX__%2C__AZCY__&cpt_fmp=__AZMX__%2C__AZMY__&cpt_pixel=__WIDTH__%2C__HEIGHT__&url=http%3A%2F%2Fwww.baidu.com
         * action : 1
         * title : 图文webp
         * mime : icon
         */

        private int width;// 广告位宽度
        private String sid;  // 广告位ID
        private AppBean app;
        private String src;// 物料URL
        private String template_type;////参考template_type取值表,当mime为icon时有意义
        private int height; // 广告位高度
        private String aid;//响应的素材ID
        private ImpBean imp;//该字段是数组，可以发送第三方展现监测
        private String desc;
        private String url;
        private int action;//广告对应的动作，移动类型的Ad使用。 0：下载， 1：浏览器打开， 2：通知栏推送
        private String title;
        private String mime;// 物料类型
        private List<String> clk; // 点击监测地址，当广告被点击时应当额外触发对这个数组中的URL的请求，以便监测点击事件。该字段是数组，希望支持多个点击监测地址

        private List<String> ext_urls = new ArrayList<>();// 额外的图片物料（用于三图）
        private String dp_url; //deeplink地址，用户点击时，如果用户设备安装有支持该deeplink的广告主App，则通过此链接跳转到广告主App的指定页面
        private ArrayList<String> dp_clk;//dp_clk点击监控url列表，当用户点击deeplink广告时，发送此点击监控列表

        private List<String> download_urls; //该字段返回时，app开始下载时必须上报，该字段是数组，需要支持多个监测地址。
        private List<String> downloaded_urls;//该字段返回时，app下载完成时必须上报，该字段是数组，需要支持多个监测地址。
        private List<String> install_urls;//该字段返回时，app安装开始后上报，该字段是数组，需要支持多个监测地址。
        private List<String> installed_urls;//该字段返回时，app安装完成后上报，该字段是数组，需要支持多个监测地址。

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

        public List<String> getInstall_urls() {
            return install_urls;
        }

        public void setInstall_urls(List<String> install_urls) {
            this.install_urls = install_urls;
        }

        public List<String> getInstalled_urls() {
            return installed_urls;
        }

        public void setInstalled_urls(List<String> installed_urls) {
            this.installed_urls = installed_urls;
        }

        public List<String> getExt_urls() {
            return ext_urls;
        }

        public void setExt_urls(List<String> ext_urls) {
            this.ext_urls = ext_urls;
        }

        public String getDp_url() {
            return dp_url;
        }

        public void setDp_url(String dp_url) {
            this.dp_url = dp_url;
        }

        public ArrayList<String> getDp_clk() {
            return dp_clk;
        }

        public void setDp_clk(ArrayList<String> dp_clk) {
            this.dp_clk = dp_clk;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public AppBean getApp() {
            return app;
        }

        public void setApp(AppBean app) {
            this.app = app;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getTemplate_type() {
            return template_type;
        }

        public void setTemplate_type(String template_type) {
            this.template_type = template_type;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public ImpBean getImp() {
            return imp;
        }

        public void setImp(ImpBean imp) {
            this.imp = imp;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMime() {
            return mime;
        }

        public void setMime(String mime) {
            this.mime = mime;
        }

        public List<String> getClk() {
            return clk;
        }

        public void setClk(List<String> clk) {
            this.clk = clk;
        }

        public static class AppBean {
            /**
             * icon_url : http://sx.pic.fastapi.net/upload/2016/06/d7ada0276744162efb83ff627557df21.webp
             * name : 测试包
             */

            private String icon_url;//应用图标
            private String name;//"应用名称"

            public String getIcon_url() {
                return icon_url;
            }

            public void setIcon_url(String icon_url) {
                this.icon_url = icon_url;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ImpBean {
            @SerializedName("0")
            private List<String> impList;

            public List<String> getimpList() {
                return impList;
            }

            public void setimpList(List<String> impList) {
                this.impList = impList;
            }
        }
    }
}
