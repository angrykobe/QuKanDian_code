package com.zhangku.qukandian.biz.adbeen.huitoutiao;

public class UserProfile {
    private String age; // 选填，年年龄数值。如：18
    private String gender; // 性别，选填，强烈烈建议填写。M为男，F为⼥女女。
    private String topic; //选填，app⻚页⾯面频道id
    private String keywords; // ⽤用户标签，选填。
    private String extra;

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getTopic() {
        return topic;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getExtra() {
        return extra;
    }
}
