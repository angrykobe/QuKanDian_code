package com.zhangku.qukandian.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

/**
 * Created by yuzuoning on 2017/4/13.
 */

public class WeChatBean implements Parcelable {

    public WeChatBean(String nickName,String unionId, String openId, int sex, String headimgUrl,
                      String province, String city, String country, int id) {
        this.unionId = unionId;
        this.openId = openId;
        this.nickName = nickName;
        this.sex = sex;
        this.headimgUrl = headimgUrl;
        this.province = province;
        this.city = city;
        this.country = country;
        this.id = id;
        this.RegSource = !TextUtils.isEmpty(UserSharedPreferences.getInstance().getString(Constants.INVITATION_SOURCE, "")) ?
                UserSharedPreferences.getInstance().getString(Constants.INVITATION_SOURCE, "") : QuKanDianApplication.mUmen;
    }

    /**
     * unionId : sample string 1
     * openId : sample string 2
     * nickName : sample string 3
     * sex : 0
     * headimgUrl : sample string 4
     * province : sample string 5
     * city : sample string 6
     * country : sample string 7
     * id : 8
     */

    private String unionId;
    private String openId;
    private String nickName;
    private int sex;
    private String headimgUrl;
    private String province;
    private String city;
    private String country;
    private int id;

    private String tel;
    private String code;
    private String RegSource = QuKanDianApplication.mUmen;

    private long inviterId;


    public long getInviterId() {
        return inviterId;
    }

    public void setInviterId(long inviterId) {
        this.inviterId = inviterId;
    }

    public String getRegSource() {
        return RegSource;
    }

    public void setRegSource(String regSource) {
        RegSource = regSource;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTel() {
        return tel;
    }

    public String getCode() {
        return code;
    }

    private boolean isSelected;

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadimgUrl() {
        return headimgUrl;
    }

    public void setHeadimgUrl(String headimgUrl) {
        this.headimgUrl = headimgUrl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.unionId);
        dest.writeString(this.openId);
        dest.writeString(this.nickName);
        dest.writeInt(this.sex);
        dest.writeString(this.headimgUrl);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeInt(this.id);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
        dest.writeString(this.RegSource);
    }

    protected WeChatBean(Parcel in) {
        this.unionId = in.readString();
        this.openId = in.readString();
        this.nickName = in.readString();
        this.sex = in.readInt();
        this.headimgUrl = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.country = in.readString();
        this.id = in.readInt();
        this.isSelected = in.readByte() != 0;
        this.RegSource = in.readString();
    }

    public static final Creator<WeChatBean> CREATOR = new Creator<WeChatBean>() {
        @Override
        public WeChatBean createFromParcel(Parcel source) {
            return new WeChatBean(source);
        }

        @Override
        public WeChatBean[] newArray(int size) {
            return new WeChatBean[size];
        }
    };

    @Override
    public String toString() {
        return "WeChatBean{" +
                "unionId='" + unionId + '\'' +
                ", openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex=" + sex +
                ", headimgUrl='" + headimgUrl + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", id=" + id +
                ", tel='" + tel + '\'' +
                ", inviterId='" + inviterId + '\'' +
                ", code='" + code + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
