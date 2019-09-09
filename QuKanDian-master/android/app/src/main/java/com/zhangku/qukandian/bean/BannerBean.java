package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2018/2/2.
 */

public class BannerBean {
    /**
     * name : shoutu_pagetop_banner_1
     * imageLink : http://cdn.qu.fi.pqmnz.com//test/img/201704/002f11c23f4a40709cf22f1f1aca5cf5.jpg
     * gotoLink : http://www.baidu.com
     */

    private String name;
    private String imageLink;
    private String gotoLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getGotoLink() {
        return gotoLink;
    }

    public void setGotoLink(String gotoLink) {
        this.gotoLink = gotoLink;
    }
}
