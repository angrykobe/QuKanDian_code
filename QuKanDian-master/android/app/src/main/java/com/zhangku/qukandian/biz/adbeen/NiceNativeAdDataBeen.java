package com.zhangku.qukandian.biz.adbeen;

import com.zhangku.qukandian.biz.adcore.ZKAdTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NiceNativeAdDataBeen {

    private Object origin;
    private String iconUrl;
    private int iconWidth;
    private int iconHeight;
    private String title; // 标题
    private String description; // 描述

    private String link;
    private String imageUrl = "";
    private List<String> imageUrls = new ArrayList<>();
    private int imageWidth;
    private int imageHeight;
    private ZKAdTypeEnum templateType; // 模版类型，⽬前只有三种，1为三图加⽂字，2为⼤图加⽂字，3为左图右⽂，0 为原⽣。前三种类型的图⽚字段为imageUrls, ⽂字字段为description
    private List<String> clc = new ArrayList<>();
    private List<String> exp = new ArrayList<>();
    private String clickUrl; // 点击⾏为地址
    private int clickAction;// 交互类型，1为下载, 2为打开落地⻚。
    private String deeplink; //deeplink地址，如该字段为null，则不执⾏deeplink
    private List<String> clc_deeplink = new ArrayList<>();

    private String downUrl;
    private HashMap<String ,List<String>> downUpUrlMap = new HashMap<>();

    public HashMap<String, List<String>> getDownUpUrlMap() {
        return downUpUrlMap;
    }

    public void setDownUpUrlMap(HashMap<String, List<String>> downUpUrlMap) {
        this.downUpUrlMap = downUpUrlMap;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public List<String> getClc_deeplink() {
        return clc_deeplink;
    }

    public void setClc_deeplink(List<String> clc_deeplink) {
        this.clc_deeplink = clc_deeplink;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public List<String> getClc() {
        return clc;
    }

    public List<String> getExp() {
        return exp;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public int getClickAction() {
        return clickAction;
    }

    public void setClc(List<String> clc) {
        this.clc = clc;
    }

    public void setExp(List<String> exp) {
        this.exp = exp;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public void setClickIsDown(int clickAction) {
        this.clickAction = clickAction;
    }

    public void setClickIsDown(boolean isDown) {
        this.clickAction = isDown?1:2;// 交互类型，1为下载, 2为打开落地⻚。
    }

    public ZKAdTypeEnum getTemplateType() {
        return templateType;
    }

    public void setTemplateType(ZKAdTypeEnum templateType) {
        this.templateType = templateType;
    }

    public Object getOrigin() {
        return origin;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public int getIconWidth() {
        return iconWidth;
    }

    public int getIconHeight() {
        return iconHeight;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setOrigin(Object origin) {
        this.origin = origin;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
    }

    public void setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

}
