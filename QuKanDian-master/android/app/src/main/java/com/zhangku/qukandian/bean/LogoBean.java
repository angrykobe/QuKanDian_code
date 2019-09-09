package com.zhangku.qukandian.bean;

import java.util.List;

/**
 * Created by yuzuoning on 2017/8/30.
 */

public class LogoBean {
    /**
     * userId : 1
     * images : [{"src":"sample string 1","id":2},{"src":"sample string 1","id":2}]
     * number : 2
     * imageUrls : ["sample string 1","sample string 2"]
     * memoName : sample string 3
     * gotGold : 1
     * gotTime : 2017-08-31T19:18:22.0501659+08:00
     * creationTime : 2017-08-31T19:18:22.0501659+08:00
     * isUploaded : true
     * sharePostUrl : http://ader365.com/web/article/110022
     * id : 5
     */

    private int userId;
    private int number;
    private String memoName;
    private int gotGold;
    private String gotTime;
    private String creationTime;
    private boolean isUploaded;
    private String sharePostUrl;
    private int id;
    private List<ImagesBean> images;
    private List<String> imageUrls;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMemoName() {
        return memoName;
    }

    public void setMemoName(String memoName) {
        this.memoName = memoName;
    }

    public int getGotGold() {
        return gotGold;
    }

    public void setGotGold(int gotGold) {
        this.gotGold = gotGold;
    }

    public String getGotTime() {
        return gotTime;
    }

    public void setGotTime(String gotTime) {
        this.gotTime = gotTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(boolean isUploaded) {
        this.isUploaded = isUploaded;
    }

    public String getSharePostUrl() {
        return sharePostUrl;
    }

    public void setSharePostUrl(String sharePostUrl) {
        this.sharePostUrl = sharePostUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public static class ImagesBean {
        /**
         * src : sample string 1
         * id : 2
         */

        private String src;
        private int id;

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
