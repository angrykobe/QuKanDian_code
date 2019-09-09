package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/11/2
 * 你不注释一下？
 */
public class CityBean {
    private String cip;//公网ip 原生广告使用
    private String cid;//地区编码
    private String cname;

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "cip='" + cip + '\'' +
                ", cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                '}';
    }
}
