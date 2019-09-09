package com.zhangku.qukandian.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by yuzuoning on 2017/4/1.
 */

public class DetailsBean extends DataSupport {
    /**
     * title : sample string 1
     * summary : sample string 2
     * textType : 0
     * channel : {"id":1,"name":"sample string 2","displayName":"sample string 3","kindType":0,"orderId":4}
     * viewSeedNumber : 3
     * viewNumber : 4
     * postTextImages : [{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6},{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6}]
     * postTextVideos : [{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6},{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6}]
     * postTextDatas : [{"postId":1,"content":"sample string 2","id":3},{"postId":1,"content":"sample string 2","id":3}]
     * labelTerms : [{"name":"sample string 1","id":2},{"name":"sample string 1","id":2}]
     * authorName : sample string 5
     * releaseTime : 2017-04-05T15:49:42.3990047+08:00
     * releaseTimeMemo : 1秒前
     * isActive : true
     * isFavorite : true
     * durationTime : 00:00:00.1234567
     * id : 9
     */

    private String title;
    private String summary;
    private int textType;
    private ChannelBean channel;
    private int viewSeedNumber;
    private int viewNumber;
    private int commentNumber;
    private String authorName;
    private String releaseTime;
    private String releaseTimeMemo;
    private boolean isActive;
    private boolean isFavorite;
    private String durationTime;
    private int id;
    private SourceTypeBean sourceType;
    private List<PostTextImagesBean> postTextImages;
    private List<PostTextVideosBean> postTextVideos;
    private List<PostTextDatasBean> postTextDatas;
    private List<LabelTermsBean> labelTerms;
    /////////////
    private boolean localIsFavorte;
    private int newId;
    private int channelId;
    /////////292
    private String zyId;
    private int contentDisType;//0原生   1  h5+原生 2 h5 (唔哩文章)
    private String staticUrl;//内容静态页

    public DetailsBean(InformationBean bean) {
        setTitle(bean.getTitle());
        setChannelId(bean.getChannelId());
        setNewId(bean.getPostId());
        setChannel(bean.getChannel());
        setPostTextImages(bean.getPostTextImages());
    }

    public int getContentDisType() {
        return contentDisType;
    }

    public void setContentDisType(int contentDisType) {
        this.contentDisType = contentDisType;
    }

    public String getZyId() {
        return zyId;
    }

    public void setZyId(String zyId) {
        this.zyId = zyId;
    }

    public String getStaticUrl() {
        return staticUrl;
    }

    public void setStaticUrl(String staticUrl) {
        this.staticUrl = staticUrl;
    }

    public boolean isLocalIsFavorte() {
        return localIsFavorte;
    }

    public void setLocalIsFavorte(boolean localIsFavorte) {
        this.localIsFavorte = localIsFavorte;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getNewId() {
        return newId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    public String getTitle() {
        return title;
    }

    public int getCommentNumber() {
        return commentNumber;
    }

    public void setCommentNumber(int commentNumber) {
        this.commentNumber = commentNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getTextType() {
        return textType;
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public ChannelBean getChannel() {
        return channel;
    }

    public void setChannel(ChannelBean channel) {
        this.channel = channel;
    }

    public int getViewSeedNumber() {
        return viewSeedNumber;
    }

    public void setViewSeedNumber(int viewSeedNumber) {
        this.viewSeedNumber = viewSeedNumber;
    }

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getReleaseTimeMemo() {
        return releaseTimeMemo;
    }

    public void setReleaseTimeMemo(String releaseTimeMemo) {
        this.releaseTimeMemo = releaseTimeMemo;
    }

    public SourceTypeBean getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceTypeBean sourceType) {
        this.sourceType = sourceType;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PostTextImagesBean> getPostTextImages() {
        return postTextImages;
    }

    public void setPostTextImages(List<PostTextImagesBean> postTextImages) {
        this.postTextImages = postTextImages;
    }

    public List<PostTextVideosBean> getPostTextVideos() {
        return postTextVideos;
    }

    public void setPostTextVideos(List<PostTextVideosBean> postTextVideos) {
        this.postTextVideos = postTextVideos;
    }

    public List<PostTextDatasBean> getPostTextDatas() {
        return postTextDatas;
    }

    public void setPostTextDatas(List<PostTextDatasBean> postTextDatas) {
        this.postTextDatas = postTextDatas;
    }

    public List<LabelTermsBean> getLabelTerms() {
        return labelTerms;
    }

    public void setLabelTerms(List<LabelTermsBean> labelTerms) {
        this.labelTerms = labelTerms;
    }

    public static class SourceTypeBean {
        /**
         * sourceType : 1
         * sourceName : 今日头条
         * "sourceId": 0
         * id : 290611
         */

        private int sourceType;
        private String sourceName;
        private int id;
        private int sourceId;

        public int getSourceId() {
            return sourceId;
        }

        public void setSourceId(int sourceId) {
            this.sourceId = sourceId;
        }

        public int getSourceType() {
            return sourceType;
        }

        public void setSourceType(int sourceType) {
            this.sourceType = sourceType;
        }

        public String getSourceName() {
            return sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
