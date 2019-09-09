package com.zhangku.qukandian.biz.adbeen.vlion;

import java.util.ArrayList;
import java.util.List;

public class VlionAdResponseBean {
    /**
     * cid : 101_1002
     * tagid : 10002
     * status : 0
     * nativead : {"desc":"瑞狮网络，精准营销","ldp":"http://test.vliQkJKg==&g=","title":"瑞狮网络","icon":[{"url":"http://a.com/logo.jpg","h":20,"w":20}],"button":"打开","img":[{"url":"http://a.com/img.jpg","h":100,"w":100}],"rating":5,"download_count":10000}
     * adt : 3
     * imp_tracking : ["uOTBPxKx6AwXw==","bVZVCWJUWkNVVVpTVFZWdE4zcFpU&t=10002"]
     * interact_type : 1
     * conv_tracking : [{"track_type":5,"url":"http://test.vlion.cn:8041/sconv?a=1"},{"track_type":7,"url":"http://test.vlion.cn:8041/sconv?a=2"}]
     */
    private String cid;//物料ID  由vlion分配的创意id
    private String tagid;
    private int status;
    private NativeadBean nativead;
    private int adt;//广告类型
    private int interact_type;//  点击行为   0-打开网页，1-下载；不填写默认为打开网页
    private List<String> imp_tracking;  //曝光监测地址，多个   adt=30时该字段不填写，媒体无需处理此字段。
    private List<String> clk_tracking;  //点击监测地址，多个   adt=30时该字段不填写，媒体无需处理此字段。
    private List<ConvTrackingBean> conv_tracking;//转化监测地址	interact_type=1时有效，下载类广告详情见下

    private List<String> dp_tracking = new ArrayList<>();//调用deeplink监测	当 deeplink 或 native.deeplink 有值时，调用deeplink后发起监测。可能为空。

    @Override
    public String toString() {
        return "VlionAdResponseBean{" +
                "cid='" + cid + '\'' +
                ", tagid='" + tagid + '\'' +
                ", status=" + status +
                ", nativead=" + nativead +
                ", adt=" + adt +
                ", interact_type=" + interact_type +
                ", imp_tracking=" + imp_tracking +
                ", conv_tracking=" + conv_tracking +
                '}';
    }

    public List<String> getDp_tracking() {
        return dp_tracking;
    }

    public void setDp_tracking(List<String> dp_tracking) {
        this.dp_tracking = dp_tracking;
    }

    public List<String> getClk_tracking() {
        return clk_tracking;
    }

    public void setClk_tracking(List<String> clk_tracking) {
        this.clk_tracking = clk_tracking;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public NativeadBean getNativead() {
        return nativead;
    }

    public void setNativead(NativeadBean nativead) {
        this.nativead = nativead;
    }

    public int getAdt() {
        return adt;
    }

    public void setAdt(int adt) {
        this.adt = adt;
    }

    public int getInteract_type() {
        return interact_type;
    }

    public void setInteract_type(int interact_type) {
        this.interact_type = interact_type;
    }

    public List<String> getImp_tracking() {
        return imp_tracking;
    }

    public void setImp_tracking(List<String> imp_tracking) {
        this.imp_tracking = imp_tracking;
    }

    public List<ConvTrackingBean> getConv_tracking() {
        return conv_tracking;
    }

    public void setConv_tracking(List<ConvTrackingBean> conv_tracking) {
        this.conv_tracking = conv_tracking;
    }

    public static class NativeadBean {
        /**
         * desc : 瑞狮网络，精准营销
         * ldp : http://test.vliQkJKg==&g=
         * title : 瑞狮网络
         * icon : [{"url":"http://a.com/logo.jpg","h":20,"w":20}]
         * button : 打开
         * img : [{"url":"http://a.com/img.jpg","h":100,"w":100}]
         * rating : 5
         * download_count : 10000
         */

        private String desc = "";//描述
        private String ldp = "";//新闻点击地址
        private String title = "";//标题
        private String button = "";
        private String deeplink = ""; //deeplink链接，如果有deeplink则优先调用，调用时需要上报 conv_tracking 中相应的监测链接。具体逻辑见下方 ”Deeplink调用逻辑”
        private int rating;//评定星级
        private int download_count;//下载次数
        private List<IconBean> icon = new ArrayList<>();//logo/头像
        private List<ImgBean> img = new ArrayList<>();//大图，参照下面img对象
        private String app_download_url;//下载地址

        public String getApp_download_url() {
            return app_download_url;
        }

        public void setApp_download_url(String app_download_url) {
            this.app_download_url = app_download_url;
        }

        public String getDeeplink() {
            return deeplink;
        }

        public void setDeeplink(String deeplink) {
            this.deeplink = deeplink;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getLdp() {
            return ldp;
        }

        public void setLdp(String ldp) {
            this.ldp = ldp;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getButton() {
            return button;
        }

        public void setButton(String button) {
            this.button = button;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public int getDownload_count() {
            return download_count;
        }

        public void setDownload_count(int download_count) {
            this.download_count = download_count;
        }

        public List<IconBean> getIcon() {
            return icon;
        }

        public void setIcon(List<IconBean> icon) {
            this.icon = icon;
        }

        public List<ImgBean> getImg() {
            return img;
        }

        public void setImg(List<ImgBean> img) {
            this.img = img;
        }

        public static class IconBean {
            /**
             * url : http://a.com/logo.jpg
             * h : 20
             * w : 20
             */

            private String url;
            private int h;
            private int w;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getH() {
                return h;
            }

            public void setH(int h) {
                this.h = h;
            }

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }
        }

        public static class ImgBean {
            /**
             * url : http://a.com/img.jpg
             * h : 100
             * w : 100
             */

            private String url;//图片地址
            private int h;
            private int w;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getH() {
                return h;
            }

            public void setH(int h) {
                this.h = h;
            }

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }
        }
    }

    public static class ConvTrackingBean {
        /**
         * track_type : 5
         * url : http://test.vlion.cn:8041/sconv?a=1
         */

        private int track_type; //5-下载开始  6-安装完成  7-下载完成  8-安装开始  9-应用激活
        private String url;//上报的url

        public int getTrack_type() {
            return track_type;
        }

        public void setTrack_type(int track_type) {
            this.track_type = track_type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
 //信息流响应示例
//      {
//        "cid": "101_1002",
//        "tagid": "10002",
//        "status": 0,
//        "nativead": {
//        "desc": "瑞狮网络，精准营销",
//        "ldp": "http://test.vliQkJKg==&g=",
//        "title": "瑞狮网络",
//        "icon": [
//        {
//        "url": "http://a.com/logo.jpg",
//        "h": 20,
//        "w": 20
//        }
//        ],
//        "button": "打开",
//        "img": [
//        {
//        "url": "http://a.com/img.jpg",
//        "h": 100,
//        "w": 100
//        }
//        ],
//        "rating": 5,
//        "download_count": 10000
//        },
//        "adt": 3,
//        "imp_tracking": [
//        "uOTBPxKx6AwXw==",
//        "bVZVCWJUWkNVVVpTVFZWdE4zcFpU&t=10002"
//        ],
//        "interact_type": 1,
//        "conv_tracking": [
//        {
//        "track_type": 5,
//        "url": "http://test.vlion.cn:8041/sconv?a=1"
//        },
//        {
//        "track_type": 7,
//        "url": "http://test.vlion.cn:8041/sconv?a=2"
//        }
//        ]
//        }
