package com.zhangku.qukandian.biz.adbeen.huitoutiao;

public class App {
    private String name; // 建议填写
    private String packName; // 建议填写
    private String version; // 建议填写
    private int category;
    private int dlinkSupport; //是否⽀支持deeplink，1表示⽀支持；0表示不不⽀支持

    public void setName(String name) {
        this.name = name;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setDlinkSupport(int dlinkSupport) {
        this.dlinkSupport = dlinkSupport;
    }

    public String getName() {
        return name;
    }

    public String getPackName() {
        return packName;
    }

    public String getVersion() {
        return version;
    }

    public int getCategory() {
        return category;
    }

    public int getDlinkSupport() {
        return dlinkSupport;
    }
}
