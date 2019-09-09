package com.zhangku.qukandian.biz.adbeen.xiaodu;

import java.util.List;

public class XiaoDuResBean {

    /**
     * id : dd2fb82d-26a5-48d6-abf9-6d8ea7889b3f
     * status : 0
     * msg : success
     * bid : [{"impid":"1965","action":1,"bundle":"","admnative":{"assets":[{"id":0,"required":1,"title":{"text":"陪我一起闯码头"}},{"id":1,"required":1,"img":{"url":"http://pcad.video.baidu.com/cc1b70d3c2300880c0eefca3d2ef3e5e.jpeg","w":"500","h":"300"}},{"id":2,"required":0,"data":{"value":""}},{"id":3,"required":1,"img":{"url":"http://pcad.video.baidu.com/7066561083805d5b43e1acee9a543eb6.jpeg","w":"500","h":"300"}},{"id":4,"required":1,"img":{"url":"http://pcad.video.baidu.com/4870a9368194f9836cd5382fb1077ee8.jpeg","w":"500","h":"300"}}],"link":{"url":"http://v.baidu.com","clicktrackers":["http://app.video.baidu.com/adver/?a=12761&d=click&transaction_id=d0a829406c824605aac58 9aedf3a4479&sellerid=6050&impid=1965&p=vs&bw=1&b=358&m=3850&terminal=adnative&os =android&os_version=7.35.3&vendor=Xiaomi&model=7+Plus&idfa=&android_id=&imei=862224 036233396&connection_type=2&ref="]},"imptrackers":["http://app.video.baidu.com/adver/?a=12761&d=show&transaction_id=d0a829406c824605aac5 89aedf3a4479&sellerid=6050&impid=1965&p=vs&bw=1&b=358&m=3850&terminal=adnative&o s=android&os_version=7.35.3&vendor=Xiaomi&model=7+Plus&idfa=&android_id=&imei=86222 4036233396&connection_type=2&ref="]}}]
     */

    private String id;
    private int status;
    private String msg;
    private List<BidBean> bid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<BidBean> getBid() {
        return bid;
    }

    public void setBid(List<BidBean> bid) {
        this.bid = bid;
    }

    public static class BidBean {
        /**
         * impid : 1965
         * action : 1
         * bundle :
         * admnative : {"assets":[{"id":0,"required":1,"title":{"text":"陪我一起闯码头"}},{"id":1,"required":1,"img":{"url":"http://pcad.video.baidu.com/cc1b70d3c2300880c0eefca3d2ef3e5e.jpeg","w":"500","h":"300"}},{"id":2,"required":0,"data":{"value":""}},{"id":3,"required":1,"img":{"url":"http://pcad.video.baidu.com/7066561083805d5b43e1acee9a543eb6.jpeg","w":"500","h":"300"}},{"id":4,"required":1,"img":{"url":"http://pcad.video.baidu.com/4870a9368194f9836cd5382fb1077ee8.jpeg","w":"500","h":"300"}}],"link":{"url":"http://v.baidu.com","clicktrackers":["http://app.video.baidu.com/adver/?a=12761&d=click&transaction_id=d0a829406c824605aac58 9aedf3a4479&sellerid=6050&impid=1965&p=vs&bw=1&b=358&m=3850&terminal=adnative&os =android&os_version=7.35.3&vendor=Xiaomi&model=7+Plus&idfa=&android_id=&imei=862224 036233396&connection_type=2&ref="]},"imptrackers":["http://app.video.baidu.com/adver/?a=12761&d=show&transaction_id=d0a829406c824605aac5 89aedf3a4479&sellerid=6050&impid=1965&p=vs&bw=1&b=358&m=3850&terminal=adnative&o s=android&os_version=7.35.3&vendor=Xiaomi&model=7+Plus&idfa=&android_id=&imei=86222 4036233396&connection_type=2&ref="]}
         */

        private String impid;//该次竞价所关联的impid
        private int action;//跳转类型 1-app内加载  2-外跳,系统默认浏览器打开 3-app内下载，会询问是否下载 4-app内直接下载，不作提示
        private String bundle;//android为应用包名
        private AdmnativeBean admnative;

        public String getImpid() {
            return impid;
        }

        public void setImpid(String impid) {
            this.impid = impid;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public String getBundle() {
            return bundle;
        }

        public void setBundle(String bundle) {
            this.bundle = bundle;
        }

        public AdmnativeBean getAdmnative() {
            return admnative;
        }

        public void setAdmnative(AdmnativeBean admnative) {
            this.admnative = admnative;
        }

        public static class AdmnativeBean {
            /**
             * assets : [{"id":0,"required":1,"title":{"text":"陪我一起闯码头"}},{"id":1,"required":1,"img":{"url":"http://pcad.video.baidu.com/cc1b70d3c2300880c0eefca3d2ef3e5e.jpeg","w":"500","h":"300"}},{"id":2,"required":0,"data":{"value":""}},{"id":3,"required":1,"img":{"url":"http://pcad.video.baidu.com/7066561083805d5b43e1acee9a543eb6.jpeg","w":"500","h":"300"}},{"id":4,"required":1,"img":{"url":"http://pcad.video.baidu.com/4870a9368194f9836cd5382fb1077ee8.jpeg","w":"500","h":"300"}}]
             * link : {"url":"http://v.baidu.com","clicktrackers":["http://app.video.baidu.com/adver/?a=12761&d=click&transaction_id=d0a829406c824605aac58 9aedf3a4479&sellerid=6050&impid=1965&p=vs&bw=1&b=358&m=3850&terminal=adnative&os =android&os_version=7.35.3&vendor=Xiaomi&model=7+Plus&idfa=&android_id=&imei=862224 036233396&connection_type=2&ref="]}
             * imptrackers : ["http://app.video.baidu.com/adver/?a=12761&d=show&transaction_id=d0a829406c824605aac5 89aedf3a4479&sellerid=6050&impid=1965&p=vs&bw=1&b=358&m=3850&terminal=adnative&o s=android&os_version=7.35.3&vendor=Xiaomi&model=7+Plus&idfa=&android_id=&imei=86222 4036233396&connection_type=2&ref="]
             */

            private LinkBean link;//点击链接
            private List<AssetsBean> assets;//原生广告元素列表
            private List<String> imptrackers;//展示监控url列表

            public LinkBean getLink() {
                return link;
            }

            public void setLink(LinkBean link) {
                this.link = link;
            }

            public List<AssetsBean> getAssets() {
                return assets;
            }

            public void setAssets(List<AssetsBean> assets) {
                this.assets = assets;
            }

            public List<String> getImptrackers() {
                return imptrackers;
            }

            public void setImptrackers(List<String> imptrackers) {
                this.imptrackers = imptrackers;
            }

            public static class LinkBean {
                /**
                 * url : http://v.baidu.com  //点击的落地页地址
                 * clicktrackers : ["http://app.video.baidu.com/adver/?a=12761&d=click&transaction_id=d0a829406c824605aac58 9aedf3a4479&sellerid=6050&impid=1965&p=vs&bw=1&b=358&m=3850&terminal=adnative&os =android&os_version=7.35.3&vendor=Xiaomi&model=7+Plus&idfa=&android_id=&imei=862224 036233396&connection_type=2&ref="]
                 * 点击监控的url
                 */

                private String url;
                private List<String> clicktrackers;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public List<String> getClicktrackers() {
                    return clicktrackers;
                }

                public void setClicktrackers(List<String> clicktrackers) {
                    this.clicktrackers = clicktrackers;
                }
            }

            public static class AssetsBean {
                /**
                 * id : 0   //assetid 必须与请求中的assessid保持一致
                 * required : 1  //设置为1表示该元素必须被展现 默认为0
                 * title : {"text":"陪我一起闯码头"} //标题
                 * img : {"url":"http://pcad.video.baidu.com/cc1b70d3c2300880c0eefca3d2ef3e5e.jpeg","w":"500","h":"300"}
                 * data : {"value":""}//其他原生元素
                 */

                private int id;
                private int required;
                private TitleBean title;
                private ImgBean img;
                private DataBean data;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getRequired() {
                    return required;
                }

                public void setRequired(int required) {
                    this.required = required;
                }

                public TitleBean getTitle() {
                    return title;
                }

                public void setTitle(TitleBean title) {
                    this.title = title;
                }

                public ImgBean getImg() {
                    return img;
                }

                public void setImg(ImgBean img) {
                    this.img = img;
                }

                public DataBean getData() {
                    return data;
                }

                public void setData(DataBean data) {
                    this.data = data;
                }

                public static class TitleBean {
                    /**
                     * text : 陪我一起闯码头
                     */

                    private String text;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }
                }

                public static class ImgBean {
                    /**
                     * url : http://pcad.video.baidu.com/cc1b70d3c2300880c0eefca3d2ef3e5e.jpeg
                     * w : 500
                     * h : 300
                     */

                    private String url;
                    private String w;
                    private String h;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public String getW() {
                        return w;
                    }

                    public void setW(String w) {
                        this.w = w;
                    }

                    public String getH() {
                        return h;
                    }

                    public void setH(String h) {
                        this.h = h;
                    }
                }

                public static class DataBean {
                    /**
                     * value :
                     */

                    private String value;

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
                }
            }
        }
    }
}
