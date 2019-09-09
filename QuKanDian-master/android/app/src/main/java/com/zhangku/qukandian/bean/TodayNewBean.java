package com.zhangku.qukandian.bean;

import java.util.List;

/**
 * Created by yuzuoning on 2017/8/24.
 */

public class TodayNewBean {
    /**
     * postId : 1
     * postText : {"title":"sample string 1","summary":"sample string 2","textType":0,"channel":{"id":1,"name":"sample string 2","displayName":"sample string 3","kindType":0,"yesNo":true,"orderId":5},"viewSeedNumber":3,"viewNumber":4,"postTextImages":[{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6},{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6}],"firstDefaultImage":{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6},"postTextVideos":[{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"durationTime":"00:00:00.1234567","id":7},{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"durationTime":"00:00:00.1234567","id":7}],"labelTerms":[{"name":"sample string 1","id":2},{"name":"sample string 1","id":2}],"authorName":"sample string 5","releaseTime":"2017-08-29T14:56:15.533+08:00","releaseTimeMemo":"1秒前","isActive":true,"id":7}
     * androidMsgId : sample string 2
     * iosMsgId : sample string 3
     * isPushed : true
     * isCanceled : true
     * userId : 1
     * auditTime : 2017-08-29T14:56:15.534+08:00
     * pushTime : 2017-08-29T14:56:15.534+08:00
     * id : 5
     */

    private int postId;
    private PostTextBean postText;
    private String androidMsgId;
    private String iosMsgId;
    private boolean isPushed;
    private boolean isCanceled;
    private int userId;
    private String auditTime;
    private String pushTime;
    private int id;
    private String staticUrl;

    public String getStaticUrl() {
        return staticUrl;
    }

    public void setStaticUrl(String staticUrl) {
        this.staticUrl = staticUrl;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public PostTextBean getPostText() {
        return postText;
    }

    public void setPostText(PostTextBean postText) {
        this.postText = postText;
    }

    public String getAndroidMsgId() {
        return androidMsgId;
    }

    public void setAndroidMsgId(String androidMsgId) {
        this.androidMsgId = androidMsgId;
    }

    public String getIosMsgId() {
        return iosMsgId;
    }

    public void setIosMsgId(String iosMsgId) {
        this.iosMsgId = iosMsgId;
    }

    public boolean isIsPushed() {
        return isPushed;
    }

    public void setIsPushed(boolean isPushed) {
        this.isPushed = isPushed;
    }

    public boolean isIsCanceled() {
        return isCanceled;
    }

    public void setIsCanceled(boolean isCanceled) {
        this.isCanceled = isCanceled;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class PostTextBean {
        /**
         * title : sample string 1
         * summary : sample string 2
         * textType : 0
         * channel : {"id":1,"name":"sample string 2","displayName":"sample string 3","kindType":0,"yesNo":true,"orderId":5}
         * viewSeedNumber : 3
         * viewNumber : 4
         * postTextImages : [{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6},{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6}]
         * firstDefaultImage : {"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"id":6}
         * postTextVideos : [{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"durationTime":"00:00:00.1234567","id":7},{"postId":1,"src":"sample string 2","alt":"sample string 3","isCdn":true,"orderId":5,"durationTime":"00:00:00.1234567","id":7}]
         * labelTerms : [{"name":"sample string 1","id":2},{"name":"sample string 1","id":2}]
         * authorName : sample string 5
         * releaseTime : 2017-08-29T14:56:15.533+08:00
         * releaseTimeMemo : 1秒前
         * isActive : true
         * id : 7
         */

        private String title;
        private String summary;
        private int textType;
        private ChannelBean channel;
        private int viewSeedNumber;
        private int viewNumber;
        private FirstDefaultImageBean firstDefaultImage;
        private String authorName;
        private String releaseTime;
        private String releaseTimeMemo;
        private boolean isActive;
        private int id;
        private List<PostTextImagesBean> postTextImages;
        private List<PostTextVideosBean> postTextVideos;
        private List<LabelTermsBean> labelTerms;

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

        public FirstDefaultImageBean getFirstDefaultImage() {
            return firstDefaultImage;
        }

        public void setFirstDefaultImage(FirstDefaultImageBean firstDefaultImage) {
            this.firstDefaultImage = firstDefaultImage;
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

        public List<LabelTermsBean> getLabelTerms() {
            return labelTerms;
        }

        public void setLabelTerms(List<LabelTermsBean> labelTerms) {
            this.labelTerms = labelTerms;
        }

        public static class ChannelBean {
            /**
             * id : 1
             * name : sample string 2
             * displayName : sample string 3
             * kindType : 0
             * yesNo : true
             * orderId : 5
             */

            private int id;
            private String name;
            private String displayName;
            private int kindType;
            private boolean yesNo;
            private int orderId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }

            public int getKindType() {
                return kindType;
            }

            public void setKindType(int kindType) {
                this.kindType = kindType;
            }

            public boolean isYesNo() {
                return yesNo;
            }

            public void setYesNo(boolean yesNo) {
                this.yesNo = yesNo;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }
        }

        public static class FirstDefaultImageBean {
            /**
             * postId : 1
             * src : sample string 2
             * alt : sample string 3
             * isCdn : true
             * orderId : 5
             * id : 6
             */

            private int postId;
            private String src;
            private String alt;
            private boolean isCdn;
            private int orderId;
            private int id;

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public boolean isIsCdn() {
                return isCdn;
            }

            public void setIsCdn(boolean isCdn) {
                this.isCdn = isCdn;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class PostTextImagesBean {
            /**
             * postId : 1
             * src : sample string 2
             * alt : sample string 3
             * isCdn : true
             * orderId : 5
             * id : 6
             */

            private int postId;
            private String src;
            private String alt;
            private boolean isCdn;
            private int orderId;
            private int id;

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public boolean isIsCdn() {
                return isCdn;
            }

            public void setIsCdn(boolean isCdn) {
                this.isCdn = isCdn;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class PostTextVideosBean {
            /**
             * postId : 1
             * src : sample string 2
             * alt : sample string 3
             * isCdn : true
             * orderId : 5
             * durationTime : 00:00:00.1234567
             * id : 7
             */

            private int postId;
            private String src;
            private String alt;
            private boolean isCdn;
            private int orderId;
            private String durationTime;
            private int id;

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public boolean isIsCdn() {
                return isCdn;
            }

            public void setIsCdn(boolean isCdn) {
                this.isCdn = isCdn;
            }

            public int getOrderId() {
                return orderId;
            }

            public void setOrderId(int orderId) {
                this.orderId = orderId;
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
        }

        public static class LabelTermsBean {
            /**
             * name : sample string 1
             * id : 2
             */

            private String name;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}