package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2018/3/28.
 */

public class AppErrorLogDto {
    private long UserId;
    private String MobileType;
    private String MobileOSVersion;
    private String AppVersion;
    private String ErrorSource;
    private String ExceptionDetails;

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getMobileType() {
        return MobileType;
    }

    public void setMobileType(String mobileType) {
        MobileType = mobileType;
    }

    public String getMobileOSVersion() {
        return MobileOSVersion;
    }

    public void setMobileOSVersion(String mobileOSVersion) {
        MobileOSVersion = mobileOSVersion;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getErrorSource() {
        return ErrorSource;
    }

    public void setErrorSource(String errorSource) {
        ErrorSource = errorSource;
    }

    public String getExceptionDetails() {
        return ExceptionDetails;
    }

    public void setExceptionDetails(String exceptionDetails) {
        ExceptionDetails = exceptionDetails;
    }
}
