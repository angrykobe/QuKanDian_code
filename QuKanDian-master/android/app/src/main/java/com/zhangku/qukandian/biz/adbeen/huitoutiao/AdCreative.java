package com.zhangku.qukandian.biz.adbeen.huitoutiao;

import java.util.List;

public class AdCreative {
    private Short templateType; // 模版类型，目前只有三种，1为三图加文字，2为大图加文字，3为左图右文，0 为原生。前三种类型的图片字段为imageUrls, 文字字段为description
    private String title; // 标题
    private String subTitle; // 子标题
    private String description; // 描述
    private String appName; // app名字
    private String packageName; // 包名，下载广告才有
    private String category; // 广告类别
    private String videoUrl; // 视频广告才有
    private int videoDuration; // 视频广告才有
    private String icon; // icon，下载
    private String imageUrls; // 已废弃字段，请使用imgList
    private List<String> imgList;
    private int materialWidth; // 素材宽度，暂时用不着
    private int materialHeight; // 素材⾼高度，暂时用不着

    public Short getTemplateType() {
        return templateType;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getCategory() {
        return category;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public int getVideoDuration() {
        return videoDuration;
    }

    public String getIcon() {
        return icon;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public int getMaterialWidth() {
        return materialWidth;
    }

    public int getMaterialHeight() {
        return materialHeight;
    }

    public void setTemplateType(Short templateType) {
        this.templateType = templateType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setVideoDuration(int videoDuration) {
        this.videoDuration = videoDuration;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public void setMaterialWidth(int materialWidth) {
        this.materialWidth = materialWidth;
    }

    public void setMaterialHeight(int materialHeight) {
        this.materialHeight = materialHeight;
    }
}
