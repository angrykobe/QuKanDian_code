package com.zhangku.qukandian.bean;

public class DeviceInfoBeen {

    public String imei;
    public String imsi;
    // 手机型号
    public String model;
    // 手机品牌
    public String brand;
    // 手机号码，有的可得，有的不可得
    public String tel;
    public String mac;
    // 设备SDK版本
    public int version_sdk;
    // 设备的系统版本
    public String version_release;
    public String user_agent;
    /**
     * 在设备首次启动时，系统会随机生成一个64位的数字，并把这个数字以16进制字符串的形式保存下来，这个16进制的字符串就是ANDROID_ID，当设备被wipe后该值会被重置。
     厂商定制系统的Bug：不同的设备可能会产生相同的ANDROID_ID：9774d56d682e549c。
     厂商定制系统的Bug：有些设备返回的值为null。
     设备差异：对于CDMA设备，ANDROID_ID和TelephonyManager.getDeviceId() 返回相同的值。
     它在Android <=2.1 or Android >=2.3的版本是可靠、稳定的，但在2.2的版本并不是100%可靠的。
     通常被认为不可信，因为它有时为null。开发文档中说明了：这个ID会改变如果进行了出厂设置。并且，如果某个Andorid手机被Root过的话，这个ID也可以被任意改变。


     [html] view plain copy
     public static String getAndroidId (Context context) {
     String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
     return ANDROID_ID;
     }

     在新版本中Setting.System.ANDROID_ID提示@Deprecated （方法可以调用但是不推荐)

     public static String getAndroidId (Context context) {
     String ANDROID_ID = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
     return ANDROID_ID;
     }
     */
    public String android_id;
    // 0为手机，1为平板，未知为2
    public int device_type;
    public int width;
    public int heigth;

    @Override
    public String toString() {
        return "DeviceInfoBeen{" +
                "imei='" + imei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", tel='" + tel + '\'' +
                ", mac='" + mac + '\'' +
                ", version_sdk=" + version_sdk +
                ", version_release='" + version_release + '\'' +
                ", user_agent='" + user_agent + '\'' +
                ", android_id='" + android_id + '\'' +
                ", device_type=" + device_type +
                ", width=" + width +
                ", heigth=" + heigth +
                '}';
    }
}
