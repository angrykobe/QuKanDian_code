package com.zhangku.qukandian.biz.adbeen.maiyou;

import com.google.gson.Gson;

/**
 * 创建者          xuzhida
 * 创建日期        2019/1/14
 * 请求bean
 */
public class MaiyouReqBean {
//    private HeadBean head = new HeadBean();
//    private BodyBean body;
    private String head;
    private String body;

    public static class HeadBean{
        private String mcd = "105001";
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * isapi : 默认1，int，必填
         * av : api版本，默认1.0，String，必填
         * aid : 广告位ID，由左唐分配，String，必填
         * adsh : 广告位高度， int，必填
         * adsw : 广告位宽度，int，必填
         * ti : {"appid":"应用ID，申请媒体时生成的APPID，String，必填",
         * "pkg":"应用包名，申请媒体时填写的包名， String，必填",
         * "apnm":"应用名称，String，必填",
         * "bn":"手机品牌，String，必填",
         * "hm":"手机厂商，String，必填",
         * "ht":"手机机型，String，必填",
         * "os":"操作系统， int， 0：Android 1：IOS 2：Windows Phpne 3：Others，必填",
         * "ov":"操作系统版本，String，必填","sw":"设备屏宽，int，必填",
         * "sh":"设备屏高， int，必填",
         * "ch":"媒体渠道标识，默认公司拼音简称，String，必填",
         * "ei":"Android设备的IMEI，String，必填",
         * "si":"Imsi号，String，必填",
         * "ip":"公网ip地址，String，必填",
         * "nt":"网络类型，int，1:2g 2:3g 3:wifi 4:unknown 5:4G 6:5G，必填",
         * "mac":"MAC地址，String，安卓必填",
         * "andid":"Android设备的Androidid，String，安卓必填",
         * "idfa":"IDFA，String，IOS必填",
         * "oid":"IOS终端设备的OpenUDID，String",
         * "ua":"User-Agent，String，必填","dpi":"手机 dpi, int",
         * "smd":"设备屏幕密度，String"}
         */
        private String isapi;
        private String av;
        private String aid;
        private String adsh;
        private String adsw;
        private TiBean ti;

        public String getIsapi() {
            return isapi;
        }

        public void setIsapi(String isapi) {
            this.isapi = isapi;
        }

        public String getAv() {
            return av;
        }

        public void setAv(String av) {
            this.av = av;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getAdsh() {
            return adsh;
        }

        public void setAdsh(String adsh) {
            this.adsh = adsh;
        }

        public String getAdsw() {
            return adsw;
        }

        public void setAdsw(String adsw) {
            this.adsw = adsw;
        }

        public TiBean getTi() {
            return ti;
        }

        public void setTi(TiBean ti) {
            this.ti = ti;
        }

        public static class TiBean {
            /**
             * appid : 应用ID，申请媒体时生成的APPID，String，必填
             * pkg : 应用包名，申请媒体时填写的包名， String，必填
             * apnm : 应用名称，String，必填
             * bn : 手机品牌，String，必填
             * hm : 手机厂商，String，必填
             * ht : 手机机型，String，必填
             * os : 操作系统， int， 0：Android 1：IOS 2：Windows Phpne 3：Others，必填
             * ov : 操作系统版本，String，必填
             * sw : 设备屏宽，int，必填
             * sh : 设备屏高， int，必填
             * ch : 媒体渠道标识，默认公司拼音简称，String，必填
             * ei : Android设备的IMEI，String，必填
             * si : Imsi号，String，必填
             * ip : 公网ip地址，String，必填
             * nt : 网络类型，int，1:2g 2:3g 3:wifi 4:unknown 5:4G 6:5G，必填
             * mac : MAC地址，String，安卓必填
             * andid : Android设备的Androidid，String，安卓必填
             * idfa : IDFA，String，IOS必填
             * oid : IOS终端设备的OpenUDID，String
             * ua : User-Agent，String，必填
             * dpi : 手机 dpi, int
             * smd : 设备屏幕密度，String
             */

            private String appid;
            private String pkg;
            private String apnm;
            private String bn;
            private String hm;
            private String ht;
            private String os;
            private String ov;
            private String sw;
            private String sh;
            private String ch;
            private String ei;
            private String si;
            private String ip;
            private String nt;
            private String mac;
            private String andid;
            private String idfa;
            private String oid;
            private String ua;
            private String dpi;
            private String smd;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getPkg() {
                return pkg;
            }

            public void setPkg(String pkg) {
                this.pkg = pkg;
            }

            public String getApnm() {
                return apnm;
            }

            public void setApnm(String apnm) {
                this.apnm = apnm;
            }

            public String getBn() {
                return bn;
            }

            public void setBn(String bn) {
                this.bn = bn;
            }

            public String getHm() {
                return hm;
            }

            public void setHm(String hm) {
                this.hm = hm;
            }

            public String getHt() {
                return ht;
            }

            public void setHt(String ht) {
                this.ht = ht;
            }

            public String getOs() {
                return os;
            }

            public void setOs(String os) {
                this.os = os;
            }

            public String getOv() {
                return ov;
            }

            public void setOv(String ov) {
                this.ov = ov;
            }

            public String getSw() {
                return sw;
            }

            public void setSw(String sw) {
                this.sw = sw;
            }

            public String getSh() {
                return sh;
            }

            public void setSh(String sh) {
                this.sh = sh;
            }

            public String getCh() {
                return ch;
            }

            public void setCh(String ch) {
                this.ch = ch;
            }

            public String getEi() {
                return ei;
            }

            public void setEi(String ei) {
                this.ei = ei;
            }

            public String getSi() {
                return si;
            }

            public void setSi(String si) {
                this.si = si;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public String getNt() {
                return nt;
            }

            public void setNt(String nt) {
                this.nt = nt;
            }

            public String getMac() {
                return mac;
            }

            public void setMac(String mac) {
                this.mac = mac;
            }

            public String getAndid() {
                return andid;
            }

            public void setAndid(String andid) {
                this.andid = andid;
            }

            public String getIdfa() {
                return idfa;
            }

            public void setIdfa(String idfa) {
                this.idfa = idfa;
            }

            public String getOid() {
                return oid;
            }

            public void setOid(String oid) {
                this.oid = oid;
            }

            public String getUa() {
                return ua;
            }

            public void setUa(String ua) {
                this.ua = ua;
            }

            public String getDpi() {
                return dpi;
            }

            public void setDpi(String dpi) {
                this.dpi = dpi;
            }

            public String getSmd() {
                return smd;
            }

            public void setSmd(String smd) {
                this.smd = smd;
            }
        }
    }
}
