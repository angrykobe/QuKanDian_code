package com.zhangku.qukandian.biz.adbeen.wangmai;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/12
 * 你不注释一下？
 */
public class WangMaiReqBean {

    private String sign;
    private String apptoken;
    private DataBean data;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getApptoken() {
        return apptoken;
    }

    public void setApptoken(String apptoken) {
        this.apptoken = apptoken;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * app : {"app_version":{"major":2,"minor":1,"micro":0}}
         * adslot : {"adslot_id":"200131","adslot_size":{"width":580,"height":90}}
         * device : {"device_type":1,"os_type":1,"os_version":{"major":4,"minor":2,"micro":0},"vendor":"mi","model":"xiaomi","screen_size":{"width":640,"height":960},"board":"MAKO","hardware":"mako","display":"cm_mako-userdebug 4.4.4 KTU84Q 7b9aa21a34 test-keys","totalRam":"1914499072","totalRom":"1789972480","udid":{"idfa":"","imei":"865648021794334","android_id":"46f082bfcd48ede9","mac":"68:3e:34:1b:6f:c3"}}
         * network : {"ipv4":"10.211.55.2","connection_type":100,"operator_type":0}
         * gps : {"coordinate_type":1,"longitude":"121.3498783898158","latitude":" 31.22964319283977","timestamp":"1500431825577"}
         */

        private AppBean app;
        private AdslotBean adslot;
        private DeviceBean device;
        private NetworkBean network;
        private GpsBean gps;

        public AppBean getApp() {
            return app;
        }

        public void setApp(AppBean app) {
            this.app = app;
        }

        public AdslotBean getAdslot() {
            return adslot;
        }

        public void setAdslot(AdslotBean adslot) {
            this.adslot = adslot;
        }

        public DeviceBean getDevice() {
            return device;
        }

        public void setDevice(DeviceBean device) {
            this.device = device;
        }

        public NetworkBean getNetwork() {
            return network;
        }

        public void setNetwork(NetworkBean network) {
            this.network = network;
        }

        public GpsBean getGps() {
            return gps;
        }

        public void setGps(GpsBean gps) {
            this.gps = gps;
        }

        public static class AppBean {
            /**
             * app_version : {"major":2,"minor":1,"micro":0}
             */

            private AppVersionBean app_version;//应用版本

            public AppVersionBean getApp_version() {
                return app_version;
            }

            public void setApp_version(AppVersionBean app_version) {
                this.app_version = app_version;
            }

            public static class AppVersionBean {
                /**
                 * major : 2
                 * minor : 1
                 * micro : 0
                 */

                private int major;//主版本号 必填，应用主版本号
                private int minor;//副版本号	选填，应用副版本号
                private int micro;//小版本号	选填，应用小版本号

                public int getMajor() {
                    return major;
                }

                public void setMajor(int major) {
                    this.major = major;
                }

                public int getMinor() {
                    return minor;
                }

                public void setMinor(int minor) {
                    this.minor = minor;
                }

                public int getMicro() {
                    return micro;
                }

                public void setMicro(int micro) {
                    this.micro = micro;
                }
            }
        }

        public static class AdslotBean {
            /**
             * adslot_id : 200131
             * adslot_size : {"width":580,"height":90}
             */

            private String adslot_id;//代码位id	必填，代码位id在ssp平台获取
            private AdslotSizeBean adslot_size;//代码位尺寸	必填，当前设备可展现广告区域的尺寸
            private int support_deeplink = 1;//int	是否支持deeplink	非必填，0=不支持，1=支持，默认为0

            public String getAdslot_id() {
                return adslot_id;
            }

            public void setAdslot_id(String adslot_id) {
                this.adslot_id = adslot_id;
            }

            public AdslotSizeBean getAdslot_size() {
                return adslot_size;
            }

            public void setAdslot_size(AdslotSizeBean adslot_size) {
                this.adslot_size = adslot_size;
            }

            public static class AdslotSizeBean {
                /**
                 * width : 580
                 * height : 90
                 */

                private int width;//代码位宽度	必填
                private int height;//代码位高度	必填

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
            }
        }

        public static class DeviceBean {
            private int device_type;//设备类型	必填，1=PHONE  2=TABLET
            private int os_type;//操作系统	必填，1=ANDROID  2=IOS
            private OsVersionBean os_version;//
            private String vendor;//设备厂商名称	必填，中文需要UTF-8 编码
            private String model;//设备型号	必填，中文需要UTF-8 编码
            private ScreenSizeBean screen_size;//设备屏幕尺寸	必填
            private String board;//
            private String hardware;//
            private String display;//
            private String totalRam;//
            private String totalRom;//
            private UdidBean udid;//设备唯一标识	必填，务必填写真实信息，否则无法保证变现效果
            private String user_agent;//设备浏览器信息	必填，广告请求中的ua需使用系统webview的ua

            public String getUser_agent() {
                return user_agent;
            }

            public void setUser_agent(String user_agent) {
                this.user_agent = user_agent;
            }

            public int getDevice_type() {
                return device_type;
            }

            public void setDevice_type(int device_type) {
                this.device_type = device_type;
            }

            public int getOs_type() {
                return os_type;
            }

            public void setOs_type(int os_type) {
                this.os_type = os_type;
            }

            public OsVersionBean getOs_version() {
                return os_version;
            }

            public void setOs_version(OsVersionBean os_version) {
                this.os_version = os_version;
            }

            public String getVendor() {
                return vendor;
            }

            public void setVendor(String vendor) {
                this.vendor = vendor;
            }

            public String getModel() {
                return model;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public ScreenSizeBean getScreen_size() {
                return screen_size;
            }

            public void setScreen_size(ScreenSizeBean screen_size) {
                this.screen_size = screen_size;
            }

            public String getBoard() {
                return board;
            }

            public void setBoard(String board) {
                this.board = board;
            }

            public String getHardware() {
                return hardware;
            }

            public void setHardware(String hardware) {
                this.hardware = hardware;
            }

            public String getDisplay() {
                return display;
            }

            public void setDisplay(String display) {
                this.display = display;
            }

            public String getTotalRam() {
                return totalRam;
            }

            public void setTotalRam(String totalRam) {
                this.totalRam = totalRam;
            }

            public String getTotalRom() {
                return totalRom;
            }

            public void setTotalRom(String totalRom) {
                this.totalRom = totalRom;
            }

            public UdidBean getUdid() {
                return udid;
            }

            public void setUdid(UdidBean udid) {
                this.udid = udid;
            }

            public static class OsVersionBean {
                /**
                 * major : 4
                 * minor : 2
                 * micro : 0
                 */

                private int major;//主版本号	必填，操作系统主版本号
                private int minor;//副版本号	选填，操作系统副版本号
                private int micro;//小版本号	选填，操作系统小版本号

                public int getMajor() {
                    return major;
                }

                public void setMajor(int major) {
                    this.major = major;
                }

                public int getMinor() {
                    return minor;
                }

                public void setMinor(int minor) {
                    this.minor = minor;
                }

                public int getMicro() {
                    return micro;
                }

                public void setMicro(int micro) {
                    this.micro = micro;
                }
            }

            public static class ScreenSizeBean {
                /**
                 * width : 640
                 * height : 960
                 */

                private int width;//设备屏幕宽度	必填
                private int height;//设备屏幕高度	必填

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
            }

            public static class UdidBean {
                /**
                 * idfa :
                 * imei : 865648021794334
                 * android_id : 46f082bfcd48ede9
                 * mac : 68:3e:34:1b:6f:c3
                 */

                private String idfa;//iOS 设备唯一标识码	iOS 必填，广告定向依赖ID
                private String imei;//Android设备唯一标识码	安卓必填，广告定向依赖ID
                private String android_id;//Android设备ID	安卓必填
                private String mac;//Mac地址	必填

                public String getIdfa() {
                    return idfa;
                }

                public void setIdfa(String idfa) {
                    this.idfa = idfa;
                }

                public String getImei() {
                    return imei;
                }

                public void setImei(String imei) {
                    this.imei = imei;
                }

                public String getAndroid_id() {
                    return android_id;
                }

                public void setAndroid_id(String android_id) {
                    this.android_id = android_id;
                }

                public String getMac() {
                    return mac;
                }

                public void setMac(String mac) {
                    this.mac = mac;
                }
            }
        }

        public static class NetworkBean {
            /**
             * ipv4 : 10.211.55.2
             * connection_type : 100
             * operator_type : 0
             */

            private String ipv4;//Ipv4地址	必填，公网Ipv4地址，确保填写的内容为用户设备的公网出口IP地址（app端直接请求广告可不填写，server端直接请求广告，需要填写）
            private int connection_type;//网络连接类型	必填，用于判断网速。
            private int operator_type;//CONNECTION_UNKNOWN = 0 无法探测当前网络状态;
            // CELL_UNKNOWN = 1 蜂窝数据接入; 未知网络类型
            // CELL_2G = 2 蜂窝数据2G网络;
            // CELL_3G = 3 蜂窝数据3G网络;
            // CELL_4G = 4 蜂窝数据4G网;
            // CELL_5G =5 蜂窝数据5G网络;
            // WIFI = 100 Wi-Fi网络接入;
            // ETHERNET = 101 以太网接入;
            // NEW_TYPE = 999 未知新类型[int 类型]

            public String getIpv4() {
                return ipv4;
            }

            public void setIpv4(String ipv4) {
                this.ipv4 = ipv4;
            }

            public int getConnection_type() {
                return connection_type;
            }

            public void setConnection_type(int connection_type) {
                this.connection_type = connection_type;
            }

            public int getOperator_type() {
                return operator_type;
            }

            public void setOperator_type(int operator_type) {
                this.operator_type = operator_type;
            }
        }

        public static class GpsBean {
            /**
             * coordinate_type : 1
             * longitude : 121.3498783898158
             * latitude :  31.22964319283977
             * timestamp : 1500431825577
             */

            private int coordinate_type;//GPS坐标类型	选填， WGS84 = 1 全球卫星定位系统坐标系,GCJ02 = 2 国家测绘局坐标系,BD09 = 3 百度坐标系
            private String longitude;//GPS坐标经度	选填
            private String latitude;//GPS坐标纬度	选填
            private String timestamp;//GPS时间戳信息	选填

            public int getCoordinate_type() {
                return coordinate_type;
            }

            public void setCoordinate_type(int coordinate_type) {
                this.coordinate_type = coordinate_type;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
