package com.zhangku.qukandian.biz.adbeen.saibo;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2019/1/14
 * 你不注释一下？
 */
public class SaiboResBean {

    /**
     * code : 200
     * msg : SUCCESS
     * imp : [{"impid":"60a9b20ce5ec11e5af66a45e60c539c5","win_price":20,"adid":"103204","aduser":"43435","compid":"20031","crid":"100345","image":[{"type":301,"iurl":"http://js.snmi.cn/images/zufang1.png"},{"type":101,"iurl":"http://js.snmi.cn/images/zufang2.png"}],"word":[{"type":1,"text":"hello world"},{"type":2,"text":"hello shuttle"}],"video":[{"type":901,"duration":15,"bitrate":30.8,"vurl":"http://xxx.com/video.mp4"}],"link":"http://www.google.com/click","deeplink":"http://www.google.com/click","action":1,"adsource":"广告","logo":"http://js.snmi.cn/images/logo.png","imptk":["http://displayreport1","http://displayreport2"],"clicktk":["http://clickreport1","http://clickreport2"],"playtk":["http://playvideoreport1","http://playvideoreport2"],"ext":{}}]
     */
    private int code;
    private String msg;
    private List<ImpBean> imp;

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

    public List<ImpBean> getImp() {
        return imp;
    }

    public void setImp(List<ImpBean> imp) {
        this.imp = imp;
    }

    public static class ImpBean {
        /**
         * impid : 60a9b20ce5ec11e5af66a45e60c539c5
         * win_price : 20
         * adid : 103204
         * aduser : 43435
         * compid : 20031
         * crid : 100345
         * image : [{"type":301,"iurl":"http://js.snmi.cn/images/zufang1.png"},{"type":101,"iurl":"http://js.snmi.cn/images/zufang2.png"}]
         * word : [{"type":1,"text":"hello world"},{"type":2,"text":"hello shuttle"}]
         * video : [{"type":901,"duration":15,"bitrate":30.8,"vurl":"http://xxx.com/video.mp4"}]
         * link : http://www.google.com/click
         * deeplink : http://www.google.com/click
         * action : 1
         * adsource : 广告
         * logo : http://js.snmi.cn/images/logo.png
         * imptk : ["http://displayreport1","http://displayreport2"]
         * clicktk : ["http://clickreport1","http://clickreport2"]
         * playtk : ["http://playvideoreport1","http://playvideoreport2"]
         * ext : {}
         */
        private String impid;
        private int win_price;
        private String adid;
        private String aduser;
        private String compid;
        private String crid;
        private String link;//广告落地页或资源下载地址
        private String deeplink;
        private int action;//link类型,1:落地页,2:资源下载。
        private String adsource;
        private String logo;
        private ExtBean ext;
        private List<ImageBean> image = new ArrayList<>();
        private List<WordBean> word = new ArrayList<>();
        private List<String> imptk;//广告曝光回调地址数组。
        private List<String> clicktk;//广告点击回调地址,(clicktk数组可能为空)。

        public String getImpid() {
            return impid;
        }

        public void setImpid(String impid) {
            this.impid = impid;
        }

        public int getWin_price() {
            return win_price;
        }

        public void setWin_price(int win_price) {
            this.win_price = win_price;
        }

        public String getAdid() {
            return adid;
        }

        public void setAdid(String adid) {
            this.adid = adid;
        }

        public String getAduser() {
            return aduser;
        }

        public void setAduser(String aduser) {
            this.aduser = aduser;
        }

        public String getCompid() {
            return compid;
        }

        public void setCompid(String compid) {
            this.compid = compid;
        }

        public String getCrid() {
            return crid;
        }

        public void setCrid(String crid) {
            this.crid = crid;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDeeplink() {
            return deeplink;
        }

        public void setDeeplink(String deeplink) {
            this.deeplink = deeplink;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public String getAdsource() {
            return adsource;
        }

        public void setAdsource(String adsource) {
            this.adsource = adsource;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public ExtBean getExt() {
            return ext;
        }

        public void setExt(ExtBean ext) {
            this.ext = ext;
        }

        public List<ImageBean> getImage() {
            return image;
        }

        public void setImage(List<ImageBean> image) {
            this.image = image;
        }

        public List<WordBean> getWord() {
            return word;
        }

        public void setWord(List<WordBean> word) {
            this.word = word;
        }

        public List<String> getImptk() {
            return imptk;
        }

        public void setImptk(List<String> imptk) {
            this.imptk = imptk;
        }

        public List<String> getClicktk() {
            return clicktk;
        }

        public void setClicktk(List<String> clicktk) {
            this.clicktk = clicktk;
        }

        public static class ExtBean {
            private List<String> ds;//app下载开始时回调,只针对安卓下载广告
            private List<String> df;//app下载完成时回调,只针对安卓下载广告
            private List<String> inst;//,app安装完成时回调,只针对安卓下载广告
            private List<String> open;//app应用打开时回调,只针对安卓下载广告

            public List<String> getDs() {
                return ds;
            }

            public void setDs(List<String> ds) {
                this.ds = ds;
            }

            public List<String> getDf() {
                return df;
            }

            public void setDf(List<String> df) {
                this.df = df;
            }

            public List<String> getInst() {
                return inst;
            }

            public void setInst(List<String> inst) {
                this.inst = inst;
            }

            public List<String> getOpen() {
                return open;
            }

            public void setOpen(List<String> open) {
                this.open = open;
            }
        }

        public static class ImageBean {
            /**
             * type : 301
             * iurl : http://js.snmi.cn/images/zufang1.png
             */

            private int type;
            private String iurl;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getIurl() {
                return iurl;
            }

            public void setIurl(String iurl) {
                this.iurl = iurl;
            }
        }

        public static class WordBean {
            /**
             * type : 1
             * text : hello world
             */

            private int type;//文字类型,标题(1),广告语(2),广告描述(3)。
            private String text;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }
        }

        public static class VideoBean {
            /**
             * type : 901
             * duration : 15
             * bitrate : 30.8
             * vurl : http://xxx.com/video.mp4
             */

            private int type;
            private int duration;
            private double bitrate;
            private String vurl;

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public double getBitrate() {
                return bitrate;
            }

            public void setBitrate(double bitrate) {
                this.bitrate = bitrate;
            }

            public String getVurl() {
                return vurl;
            }

            public void setVurl(String vurl) {
                this.vurl = vurl;
            }
        }
    }
}
