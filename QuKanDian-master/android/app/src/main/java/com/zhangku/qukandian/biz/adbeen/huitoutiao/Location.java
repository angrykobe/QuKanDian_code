package com.zhangku.qukandian.biz.adbeen.huitoutiao;

public class Location {
    private Double logLongitude; // 经度，选填。
    private Double logLatitude; // 纬度，选填。经纬度强烈烈建议填写。
    private String province; // 省份，选填
    private String city; // 城市，选填
    private String address; // 详细地址，选填

    public void setLogLongitude(Double logLongitude) {
        this.logLongitude = logLongitude;
    }

    public void setLogLatitude(Double logLatitude) {
        this.logLatitude = logLatitude;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLogLongitude() {
        return logLongitude;
    }

    public Double getLogLatitude() {
        return logLatitude;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }
}
