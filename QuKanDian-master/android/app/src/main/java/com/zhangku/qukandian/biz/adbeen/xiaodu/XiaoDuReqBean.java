package com.zhangku.qukandian.biz.adbeen.xiaodu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class XiaoDuReqBean {


    /**
     * id : dd2fb82d-26a5-48d6-abf9-6d8ea7889b3f
     * imp : [{"id":"1965","native":{"assets":[{"id":0,"required":1,"title":{"len":50}},{"id":1,"required":1,"img":{"w":500,"h":300,"type":3}},{"id":2,"required":0,"data":{"type":2}},{"id":3,"required":1,"img":{"w":500,"h":300,"type":3}},{"id":4,"required":1,"img":{"w":500,"h":300,"type":3}}]}}]
     * app : {"id":"testid","name":"baiduvideo","bundle":"com.baidu.video","ver":"8.0.0"}
     * device : {"ua":"testua","geo":{"lat":39.99559783935547,"lon":116.40010070800781},"ip":"58.24.0.17","deviceType":4,"make":"Xiaomi","model":"7Plus","os":"Android","osv":"7.35.3","h":1136,"w":640,"carrier":1,"connectiontype":2,"did":"862224036233396","dpid":"bf908f5a193294f2","mac":"02:00:00:00:00:00"}
     */

    private String id;
    private AppBean app;
    private DeviceBean device;
    private List<ImpBean> imp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<ImpBean> getImp() {
        return imp;
    }

    public void setImp(List<ImpBean> imp) {
        this.imp = imp;
    }

    public static class AppBean {
        /**
         * id : testid
         * name : baiduvideo
         * bundle : com.baidu.video
         * ver : 8.0.0
         */

        private String id;
        private String name;
        private String bundle;
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

        public String getBundle() {
            return bundle;
        }

        public void setBundle(String bundle) {
            this.bundle = bundle;
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
         * ua : testua   //⍿浏览器user agent
         * geo : {"lat":39.99559783935547,"lon":116.40010070800781}//地域信息（经纬度）
         * ip : 58.24.0.17 //ip地址
         * deviceType : 4  //设备类型  2   –PC    3–TV   4–Phone
         * make : Xiaomi //设备制造商
         * model : 7Plus //设备型号
         * os : Android  //操作系统
         * osv : 7.35.3  //操作系统版本号
         * h : 1136   //物理屏幕高度
         * w : 640    //物理屏幕宽度
         * carrier : 1 //运营商  0–未知     1–China    Mobile 2–China    Unicom 3–China    Telecom
         * connectiontype : 2 //网络类型  0-未知  1-有线网络  2-wifi  3-移动网络  4-移动2g  5-移动3g  6-移动4g
         * did : 862224036233396  //设备号(IMEI)
         * dpid : bf908f5a193294f2 //androidid
         * mac : 02:00:00:00:00:00  //mac物理地址
         */


        private String ua;
        private GeoBean geo;
        private String ip;
        private int deviceType;
        private String make;
        private String model;
        private String os;
        private String osv;
        private int h;
        private int w;
        private int carrier;
        private int connectiontype;
        private String did;
        private String dpid;
        private String mac;

        public String getUa() {
            return ua;
        }

        public void setUa(String ua) {
            this.ua = ua;
        }

        public GeoBean getGeo() {
            return geo;
        }

        public void setGeo(GeoBean geo) {
            this.geo = geo;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
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

        public int getCarrier() {
            return carrier;
        }

        public void setCarrier(int carrier) {
            this.carrier = carrier;
        }

        public int getConnectiontype() {
            return connectiontype;
        }

        public void setConnectiontype(int connectiontype) {
            this.connectiontype = connectiontype;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

        public String getDpid() {
            return dpid;
        }

        public void setDpid(String dpid) {
            this.dpid = dpid;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public static class GeoBean {
            /**
             * lat : 39.99559783935547
             * lon : 116.40010070800781
             */

            private double lat;
            private double lon;

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

    public static class ImpBean {
        /**
         * id : 1965
         * native : {"assets":[{"id":0,"required":1,"title":{"len":50}},{"id":1,"required":1,"img":{"w":500,"h":300,"type":3}},{"id":2,"required":0,"data":{"type":2}},{"id":3,"required":1,"img":{"w":500,"h":300,"type":3}},{"id":4,"required":1,"img":{"w":500,"h":300,"type":3}}]}
         */

        private String id;
        @SerializedName("native")
        private NativeBean nativeX;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public NativeBean getNativeX() {
            return nativeX;
        }

        public void setNativeX(NativeBean nativeX) {
            this.nativeX = nativeX;
        }

        public static class NativeBean {
            private List<AssetsBean> assets;

            public List<AssetsBean> getAssets() {
                return assets;
            }

            public void setAssets(List<AssetsBean> assets) {
                this.assets = assets;
            }

            public static class AssetsBean {
                /**
                 * id : 0
                 * required : 1
                 * title : {"len":50}
                 * img : {"w":500,"h":300,"type":3}
                 * data : {"type":2}
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
                     * len : 50
                     */

                    private int len;

                    public int getLen() {
                        return len;
                    }

                    public void setLen(int len) {
                        this.len = len;
                    }
                }

                public static class ImgBean {
                    /**
                     * w : 500
                     * h : 300
                     * type : 3
                     */

                    private int w;
                    private int h;
                    private int type;

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

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }
                }

                public static class DataBean {
                    /**
                     * type : 2
                     */

                    private int type;

                    public int getType() {
                        return type;
                    }

                    public void setType(int type) {
                        this.type = type;
                    }
                }
            }
        }
    }
}
