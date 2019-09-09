package com.zhangku.qukandian.bean;

/**
 * 2019-5-27 15:38:36
 * ljs
 * 上传日志
 */
public class LogUpBean {
    private String save_time;//保存时间
    private String phone_imei;//手机IMEI
    private String phone_user ;//
    private String ad_company;//广告商
    private String ad_title;//标题
    private String ad_type;//类型
    private String ad_remarks;//备注

    public String getPhone_user() {
        return phone_user;
    }

    public void setPhone_user(String phone_user) {
        this.phone_user = phone_user;
    }

    public String getSave_time() {
        return save_time;
    }

    public void setSave_time(String save_time) {
        this.save_time = save_time;
    }

    public String getPhone_imei() {
        return phone_imei;
    }

    public void setPhone_imei(String phone_imei) {
        this.phone_imei = phone_imei;
    }

    public String getAd_company() {
        return ad_company;
    }

    public void setAd_company(String ad_company) {
        this.ad_company = ad_company;
    }

    public String getAd_title() {
        return ad_title;
    }

    public void setAd_title(String ad_title) {
        this.ad_title = ad_title;
    }

    public String getAd_type() {
        return ad_type;
    }

    public void setAd_type(String ad_type) {
        this.ad_type = ad_type;
    }

    public String getAd_remarks() {
        return ad_remarks;
    }

    public void setAd_remarks(String ad_remarks) {
        this.ad_remarks = ad_remarks;
    }

    @Override
    public String toString() {
        return "LogUpBean{" +
                "save_time='" + save_time + '\'' +
                ", phone_imei='" + phone_imei + '\'' +
                ", ad_company='" + ad_company + '\'' +
                ", ad_title='" + ad_title + '\'' +
                ", ad_type='" + ad_type + '\'' +
                ", ad_remarks='" + ad_remarks + '\'' +
                '}';
    }
}
