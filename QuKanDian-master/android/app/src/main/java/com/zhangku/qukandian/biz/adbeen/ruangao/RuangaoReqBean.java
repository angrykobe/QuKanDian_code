package com.zhangku.qukandian.biz.adbeen.ruangao;

import com.zhangku.qukandian.biz.adcore.AdConfig;

import static com.zhangku.qukandian.biz.adcore.AdConfig.ruangao_siteid;

/**
 * 创建者          xuzhida
 * 创建日期        2019/3/21
 * 你不注释一下？
 */
public class RuangaoReqBean {
    /**
     * id : 123456789
     * version : 1.1
     * site : {"id":"800012","name":"site 名称"}
     * imp : {"id":"35664444","tagid":1000335,"banner":{"w":230,"h":130,"pos":1}}
     * app : {"id":"com.apk.package","name":"app 名称","ver":"5.0.3.112"}
     * device : {"id":"51B8E1C5-4925-4AE4-B757-0DFC0BF6C442","dpid":"51B8E1C5-4925-4AE4-B757-0DFC0BF6C442","ua":"Mozilla/5.0 (Macintosh;U;Intel Mac OS X10.6;en-US;rv:1.9.2.16) Gecko/20110319 Firefox/3.6.16","ip":"192.168.1.8","model":"apple-iPhone4S","os":"iOS","osv":"6.2.1","connectiontype":2,"devicetype":1,"mac":"fc:3f:7c:72:27:4c","w":"1080","h":"1920","screenorientation":1,"geo":{"lat":28.20524,"lon":112.857274}}
     */
    private String id = AdConfig.ruangao_tagid;//  bid request的唯一id
    private String version="1.6.3";//
    private SiteBean site;//
    private ImpBean imp;//
    private AppBean app;//
    private DeviceBean device;//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SiteBean getSite() {
        return site;
    }

    public void setSite(SiteBean site) {
        this.site = site;
    }

    public ImpBean getImp() {
        return imp;
    }

    public void setImp(ImpBean imp) {
        this.imp = imp;
    }

    public AppBean getApp() {
        return app;
    }

    public void setApp(AppBean app) {
        this.app = app;
    }

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public static class SiteBean {
        /**
         * id : 800012
         * name : site 名称
         */

        private String id;//  媒体id,由软告提供
        private String name;//  媒体名称

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ImpBean {
        /**
         * id : 35664444
         * tagid : 1000335
         * banner : {"w":230,"h":130,"pos":1}
         */

        private String id = ruangao_siteid;//Impression对象唯一id
        private String tagid;// 广告位ID,由软告提供
        private BannerBean banner;//banner类型的广告位 注意：banner和信息流均用此对象

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTagid() {
            return tagid;
        }

        public void setTagid(String tagid) {
            this.tagid = tagid;
        }

        public BannerBean getBanner() {
            return banner;
        }

        public void setBanner(BannerBean banner) {
            this.banner = banner;
        }

        public static class BannerBean {
            /**
             * w : 230
             * h : 130
             * pos : 1
             */

            private int w;
            private int h;
            private int pos = 4;

            public int getW() {
                return w;
            }

            public void setW(int w) {
                this.w = w;
            }

            public int getH() {
                return h;
            }

            public void setH(int h) {
                this.h = h;
            }

            public int getPos() {
                return pos;
            }

            public void setPos(int pos) {
                this.pos = pos;
            }
        }
    }

    public static class AppBean {
        /**
         * id : com.apk.package
         * name : app 名称
         * ver : 5.0.3.112
         */

        private String id;
        private String name;
        private String ver;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }
    }

    public static class DeviceBean {
        /**
         * id : 51B8E1C5-4925-4AE4-B757-0DFC0BF6C442
         * dpid : 51B8E1C5-4925-4AE4-B757-0DFC0BF6C442
         * ua : Mozilla/5.0 (Macintosh;U;Intel Mac OS X10.6;en-US;rv:1.9.2.16) Gecko/20110319 Firefox/3.6.16
         * ip : 192.168.1.8
         * model : apple-iPhone4S
         * os : iOS
         * osv : 6.2.1
         * connectiontype : 2
         * devicetype : 1
         * mac : fc:3f:7c:72:27:4c
         * w : 1080
         * h : 1920
         * screenorientation : 1
         * geo : {"lat":28.20524,"lon":112.857274}
         */

        private String id;//安卓设备填安卓ID或imei，优先安卓ID；ios设备填idfa
        private String dpid;//IMEI（android）或IDFA（iphone,ipad）
        private String ua;//
        private String ip;//
        private String model;//设备型号
        private String os;//设备操作系统
        private String osv;//设备操作系统版本号
        private int connectiontype;//设备联网方式，0=无；1=wifi；2=2/3/4G
        private int devicetype;//设备类型 ： PC 端 为 0; 1=iPhone ； 2=iPad ； 3=android；4=android_pad
        private String mac;//
        private String w;//
        private String h;//
        private int screenorientation = 1;//
        private GeoBean geo;//用户当前位置信息

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDpid() {
            return dpid;
        }

        public void setDpid(String dpid) {
            this.dpid = dpid;
        }

        public String getUa() {
            return ua;
        }

        public void setUa(String ua) {
            this.ua = ua;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getOsv() {
            return osv;
        }

        public void setOsv(String osv) {
            this.osv = osv;
        }

        public int getConnectiontype() {
            return connectiontype;
        }

        public void setConnectiontype(int connectiontype) {
            this.connectiontype = connectiontype;
        }

        public int getDevicetype() {
            return devicetype;
        }

        public void setDevicetype(int devicetype) {
            this.devicetype = devicetype;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
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

        public int getScreenorientation() {
            return screenorientation;
        }

        public void setScreenorientation(int screenorientation) {
            this.screenorientation = screenorientation;
        }

        public GeoBean getGeo() {
            return geo;
        }

        public void setGeo(GeoBean geo) {
            this.geo = geo;
        }

        public static class GeoBean {
            /**
             * lat : 28.20524
             * lon : 112.857274
             */

            private double lat=0;
            private double lon=0;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }
        }
    }
}
