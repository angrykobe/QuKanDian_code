package com.zhangku.qukandian.biz.adbeen.ruangao;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2019/3/21
 * 你不注释一下？
 */
public class RuangaoResBean {
    /**
     * id : efc893a193414ff41c96177a2b01aebb60bee12d
     * ad_type : 1
     * materialtype : 0
     * title : title
     * desc : desc
     * apkname : apkname
     * package : apppackagename
     * app_store_id : 538248967
     * price : 100
     * pv : [{"type_mma":0,"url":"http://d3f.houyi.baofeng.net/id/15665","time":1},{"type_mma":1,"url":"http://vbaof.admaster.com.cn/i/a17943","time":1}]
     * click : [{"type_mma":0,"url":"http://d3f.houyi.baofeng.net/id/15665"},{"type_mma":1,"url":"http://c.admaster.com.cn/c/a17943"}]
     * target : http://www.baofeng.net/
     * dplurl : openapp.bfmobile://
     * width : 230
     * height : 130
     * ad_url : http://wl.houyi.baofeng.net/media/img/houyi/web/720-480.jpg
     * nurl : http://www.test.com/?winprice={AUCTION_PRICE}
     */
    private String id;
    private int ad_type;
    private String materialtype;
    private String title;
    private String desc;
    private String apkname;
    @SerializedName("package")
    private String packageX;
    private String app_store_id;
    private String price;
    private String target;
    private String dplurl;
    private int width;
    private int height;
    private String ad_url;
    private String nurl;
    private List<PvBean> pv;
    private List<ClickBean> click;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAd_type() {
        return ad_type;
    }

    public void setAd_type(int ad_type) {
        this.ad_type = ad_type;
    }

    public String getMaterialtype() {
        return materialtype;
    }

    public void setMaterialtype(String materialtype) {
        this.materialtype = materialtype;
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

    public String getApkname() {
        return apkname;
    }

    public void setApkname(String apkname) {
        this.apkname = apkname;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getApp_store_id() {
        return app_store_id;
    }

    public void setApp_store_id(String app_store_id) {
        this.app_store_id = app_store_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getDplurl() {
        return dplurl;
    }

    public void setDplurl(String dplurl) {
        this.dplurl = dplurl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public String getNurl() {
        return nurl;
    }

    public void setNurl(String nurl) {
        this.nurl = nurl;
    }

    public List<PvBean> getPv() {
        return pv;
    }

    public void setPv(List<PvBean> pv) {
        this.pv = pv;
    }

    public List<ClickBean> getClick() {
        return click;
    }

    public void setClick(List<ClickBean> click) {
        this.click = click;
    }

    public static class PvBean {
        /**
         * type_mma : 0
         * url : http://d3f.houyi.baofeng.net/id/15665
         * time : 1
         */

        private int type_mma;
        private String url;
        private int time;

        public int getType_mma() {
            return type_mma;
        }

        public void setType_mma(int type_mma) {
            this.type_mma = type_mma;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    public static class ClickBean {
        /**
         * type_mma : 0
         * url : http://d3f.houyi.baofeng.net/id/15665
         */

        private int type_mma;
        private String url;

        public int getType_mma() {
            return type_mma;
        }

        public void setType_mma(int type_mma) {
            this.type_mma = type_mma;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
