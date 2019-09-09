package com.zhangku.qukandian.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by yuzuoning on 2017/3/27.
 */

public class InformationBean extends DataSupport {

    /**
     * title : sample string 1
     * summary : sample string 2
     * textType : 0
     * channel : {"id":1,"name":"sample string 2","displayName":"sample string 3","kindType":0,"orderId":4}
     * viewSeedNumber : 3
     * viewNumber : 4
     * postTextImages : [{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6},{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6}]
     * labelTerms : [{"name":"sample string 1","id":2},{"name":"sample string 1","id":2}]
     * authorName : sample string 5
     * releaseTime : 2017-04-05T09:57:38.2580047+08:00
     * releaseTimeMemo : 1秒前
     * isActive : true
     * durationTime : 00:00:00.1234567
     * id : 8
     * isTop
     */

//    @SerializedName("id")
    private int postId;
    private String title;
    private String summary;
    private int textType;
    private ChannelBean channel;
    private int viewSeedNumber;
    private int viewNumber;
    private String authorName;
    private String releaseTime;
    private String releaseTimeMemo;
    private boolean isActive;
    private boolean isTop;
    private String durationTime;
    private int id;
    private List<PostTextImagesBean> postTextImages;
    private List<LabelTermsBean> labelTerms;
    private List<PostTextVideosBean> postTextVideos;
    private int channelId;
    private String staticUrl;//内容静态页
    ///////////////291
    private String channelName;//文章类型  0：  highprice_news：高价，xiguang开头：犀光
    /////////////292
    private String zyId;
    private int contentDisType;//0原生   1  h5+原生 2 h5
    private int articleType;// 文章标识，（0：原文，1：高价文，2：犀光，3：唔哩/盖范，4：广告）

    public int getContentDisType() {
        return contentDisType;
    }

    public void setContentDisType(int contentDisType) {
        this.contentDisType = contentDisType;
    }

    public int getArticleType() {
        return articleType;
    }

    public void setArticleType(int articleType) {
        this.articleType = articleType;
    }

    public String getZyId() {
        return zyId;
    }

    public void setZyId(String zyId) {
        this.zyId = zyId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getStaticUrl() {
        return staticUrl;
    }

    public void setStaticUrl(String staticUrl) {
        this.staticUrl = staticUrl;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public List<PostTextVideosBean> getPostTextVideos() {
        return postTextVideos;
    }

    public void setPostTextVideos(List<PostTextVideosBean> postTextVideos) {
        this.postTextVideos = postTextVideos;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
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

    public List<LabelTermsBean> getLabelTerms() {
        return labelTerms;
    }

    public void setLabelTerms(List<LabelTermsBean> labelTerms) {
        this.labelTerms = labelTerms;
    }

    @Override
    public String toString() {
        return "InformationBean{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", textType=" + textType +
                ", channel=" + channel +
                ", viewSeedNumber=" + viewSeedNumber +
                ", viewNumber=" + viewNumber +
                ", authorName='" + authorName + '\'' +
                ", releaseTime='" + releaseTime + '\'' +
                ", releaseTimeMemo='" + releaseTimeMemo + '\'' +
                ", isActive=" + isActive +
                ", isTop=" + isTop +
                ", durationTime='" + durationTime + '\'' +
                ", id=" + id +
                ", postTextImages=" + postTextImages +
                ", labelTerms=" + labelTerms +
                ", postTextVideos=" + postTextVideos +
                ", channelId=" + channelId +
                ", isSelected=" + isSelected +
                '}';
    }
}
