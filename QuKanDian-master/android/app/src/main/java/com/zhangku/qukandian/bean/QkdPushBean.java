package com.zhangku.qukandian.bean;

import java.io.Serializable;

/**
 * Created by yuzuoning on 2018/2/2.
 */

public class QkdPushBean implements Serializable {

    private static final long serialVersionUID = -6470415444539574316L;
    private int type;
    private String title;
    private String message;
    private String url;
    private String imgurl = "";
    private String deeplink;
    private int tab;

    public QkdPushBean(int type, String title, String message, String url, String imgurl, String deeplink, int tab) {
        this.type = type;
        this.title = title;
        this.message = message;
        this.url = url;
        this.imgurl = imgurl;
        this.deeplink = deeplink;
        this.tab = tab;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public String getImgurl() {
        return imgurl;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public int getTab() {
        return tab;
    }

    @Override
    public String toString() {
        return "QkdPushBean{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", deeplink='" + deeplink + '\'' +
                ", tab='" + tab + '\'' +
                '}';
    }
}
