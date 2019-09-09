package com.zhangku.qukandian.bean;

/**
 * 搜狗热词
 * 2019-6-11 09:37:51
 * ljs
 */
public class SougouHotwordBean {

    /**
     * kwd : 椹榫欐偅鐧岀梾鏁�
     * pvid : 0d456c6abd9f9009
     * type : 0
     * url : http://ts.mobile.sogou.com/redirect?keyword=%E9%A9%AC%E5%A6%82%E9%BE%99%E6%82%A3%E7%99%8C%E7%97%85%E6%95%85&pid=sogou-appi-e3958a8c7218de84&v=5&hottype=ckexposure&pvid=0d456c6abd9f9009
     */

    private String kwd;
    private String pvid;
    private String type;
    private String url;

    public String getKwd() {
        return kwd;
    }

    public void setKwd(String kwd) {
        this.kwd = kwd;
    }

    public String getPvid() {
        return pvid;
    }

    public void setPvid(String pvid) {
        this.pvid = pvid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
