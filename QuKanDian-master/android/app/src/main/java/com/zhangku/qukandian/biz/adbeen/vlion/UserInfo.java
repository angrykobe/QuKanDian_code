package com.zhangku.qukandian.biz.adbeen.vlion;

public class UserInfo {
    protected String cel;//手机号码，需要将用户的手机号码按照如下文档加密，加密使用的key由puma平台提供
    protected String sex;//性别，0-女，1-男
    protected String age;//年龄
    protected String edu;//学历：1-博士，2-硕士，3-本科，4-专科，5-高中，6-初中，7-小学及以下
    protected String mrg;//婚恋状况：1-已婚，2-未婚，3-单身
    protected String job;//工作，传入职位汉字，如 "产品经理"

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getMrg() {
        return mrg;
    }

    public void setMrg(String mrg) {
        this.mrg = mrg;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
