package com.zhangku.qukandian.bean;

import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.manager.UserManager;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuzuoning on 2017/10/23.
 */

public class AdLocationBeans {

    /**
     * id : 0
     * adPageLocationName : 首页
     * adPageType : 1
     * adLocations : [{"id":62,"adPageLocationId":1,"pageIndex":1,"channelId":0,"adDi
     * splayStyle":1,"isEnable":true,"clientAdvertises":[{"referId":157,"adverId":50,"adverName":"雷群1","adType":1,"title":"兰博基尼","materialName":"（文章列表页）兰博基尼","mbLink":"http://qukandian.com/ads/test/articleList/index.html","pv":1,"order":1,"isEnableTraffic":true,"clickGold":20,"isShowAdsIcon":true,"isSecondClickGoldEnable":true,"userHistoryIncomeVPT":50000,"giftPresentRate":100}]},{"id":67,"adPageLocationId":1,"pageIndex":2,"channelId":0,"adDisplayStyle":1,"isEnable":true,"clientAdvertises":[{"referId":155,"adverId":49,"adverName":"酷狗广告1","adType":1,"title":"酷狗音乐广告","materialName":"酷狗音乐广告","mbLink":"https://www.baidu.com/","pv":1,"order":1,"isEnableTraffic":true,"clickGold":0,"isShowAdsIcon":true,"isSecondClickGoldEnable":false,"userHistoryIncomeVPT":0,"giftPresentRate":0},{"referId":144,"adverId":40,"adverName":"雷群2","adType":1,"title":"路虎汽车","materialName":"（文章列表多图）路虎汽车","mbLink":"http://qukandian.com/ads/test/shouyeduotu/index.html","pv":1,"order":2,"isEnableTraffic":true,"clickGold":20,"isShowAdsIcon":true,"isSecondClickGoldEnable":true,"userHistoryIncomeVPT":50000,"giftPresentRate":100},{"referId":145,"adverId":39,"adverName":"雷群3","adType":1,"title":"非常帅气的休闲裤","materialName":"（文章列表页）非常帅气的休闲裤","mbLink":"http://qukandian.com/ads/test/articleList/index.html","pv":1,"order":3,"isEnableTraffic":true,"clickGold":60,"isShowAdsIcon":true,"isSecondClickGoldEnable":true,"userHistoryIncomeVPT":50000,"giftPresentRate":100},{"referId":96,"adverId":15,"adverName":"百度","adType":2,"pv":1,"order":4,"isEnableTraffic":true,"clickGold":0,"isShowAdsIcon":true,"isSecondClickGoldEnable":false,"userHistoryIncomeVPT":0,"giftPresentRate":0},{"referId":93,"adverId":39,"adverName":"测试合作链接5","adType":3,"mbLink":"https://www.baidu.com/","pv":1,"order":5,"isEnableTraffic":true,"clickGold":10,"isShowAdsIcon":true,"isSecondClickGoldEnable":true,"userHistoryIncomeVPT":5000,"giftPresentRate":100}]},{"id":85,"adPageLocationId":1,"pageIndex":4,"channelId":0,"adDisplayStyle":1,"isEnable":true,"clientAdvertises":[{"referId":109,"adverId":7,"adverName":"联盟1","adType":2,"pv":20,"order":3,"isEnableTraffic":true,"clickGold":12,"isShowAdsIcon":true,"isSecondClickGoldEnable":false,"userHistoryIncomeVPT":0,"giftPresentRate":0},{"referId":110,"adverId":8,"adverName":"联盟2","adType":2,"pv":20,"order":4,"isEnableTraffic":true,"clickGold":12,"isShowAdsIcon":true,"isSecondClickGoldEnable":false,"userHistoryIncomeVPT":0,"giftPresentRate":0},{"referId":90,"adverId":10,"adverName":"合作15","adType":3,"mbLink":"hasuifdhu","pv":20,"order":5,"isEnableTraffic":true,"clickGold":10,"isShowAdsIcon":true,"isSecondClickGoldEnable":false,"userHistoryIncomeVPT":0,"giftPresentRate":0},{"referId":91,"adverId":11,"adverName":"合作26","adType":3,"mbLink":"hasuifdhu","pv":20,"order":6,"isEnableTraffic":true,"clickGold":10,"isShowAdsIcon":true,"isSecondClickGoldEnable":false,"userHistoryIncomeVPT":0,"giftPresentRate":0}]}]
     * locRangeStartIndex : 1
     * locRangeEndIndex : 10
     */

    private int id;//
    private String adPageLocationName;//广告页面位置名称
    private int adPageType;//广告页面类型：首页，文章详情页，视频列表，视频详情页
    private int locRangeStartIndex;//位置范围开始值
    private int locRangeEndIndex;//位置范围最大值
    private List<AdLocationsBean> adLocations;//广告位

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdPageLocationName() {
        return adPageLocationName;
    }

    public void setAdPageLocationName(String adPageLocationName) {
        this.adPageLocationName = adPageLocationName;
    }

    public int getAdPageType() {
        return adPageType;
    }

    public void setAdPageType(int adPageType) {
        this.adPageType = adPageType;
    }

    public int getLocRangeStartIndex() {
        return locRangeStartIndex;
    }

    public void setLocRangeStartIndex(int locRangeStartIndex) {
        this.locRangeStartIndex = locRangeStartIndex;
    }

    public int getLocRangeEndIndex() {
        return locRangeEndIndex;
    }

    public void setLocRangeEndIndex(int locRangeEndIndex) {
        this.locRangeEndIndex = locRangeEndIndex;
    }

    public List<AdLocationsBean> getAdLocations() {
        return adLocations;
    }

    public void setAdLocations(List<AdLocationsBean> adLocations) {
        this.adLocations = adLocations;
    }

    public static class AdLocationsBean {
        /**
         * id : 62
         * adPageLocationId : 1
         * pageIndex : 1
         * channelId : 0
         * adDisplayStyle : 1
         * isEnable : true
         * clientAdvertises : [{"referId":157,"adverId":50,"adverName":"雷群1","adType":1,"title":"兰博基尼","materialName":"（文章列表页）兰博基尼","mbLink":"http://qukandian.com/ads/test/articleList/index.html","pv":1,"order":1,"isEnableTraffic":true,"clickGold":20,"isShowAdsIcon":true,"isSecondClickGoldEnable":true,"userHistoryIncomeVPT":50000,"giftPresentRate":100}]
         */

        private int id;
        private int adPageLocationId;
        private int pageIndex;
        private List<Integer> channelIds;
        private int adDisplayStyle;
        private boolean isEnable;
        private List<ClientAdvertisesBean> clientAdvertises;

        @Override
        public String toString() {
            return "AdLocationsBean{" +
                    "id=" + id +
                    ", adPageLocationId=" + adPageLocationId +
                    ", pageIndex=" + pageIndex +
                    ", channelIds=" + channelIds +
                    ", adDisplayStyle=" + adDisplayStyle +
                    ", isEnable=" + isEnable +
                    ", clientAdvertises=" + clientAdvertises +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAdPageLocationId() {
            return adPageLocationId;
        }

        public void setAdPageLocationId(int adPageLocationId) {
            this.adPageLocationId = adPageLocationId;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public List<Integer> getChannelIds() {
            return channelIds;
        }

        public void setChannelIds(List<Integer> channelIds) {
            this.channelIds = channelIds;
        }

        public int getAdDisplayStyle() {
            return adDisplayStyle;
        }

        public void setAdDisplayStyle(int adDisplayStyle) {
            this.adDisplayStyle = adDisplayStyle;
        }

        public boolean isIsEnable() {
            return isEnable;
        }

        public void setIsEnable(boolean isEnable) {
            this.isEnable = isEnable;
        }

        public List<ClientAdvertisesBean> getClientAdvertises() {
            return clientAdvertises;
        }

        public void setClientAdvertises(List<ClientAdvertisesBean> clientAdvertises) {
            this.clientAdvertises = clientAdvertises;
        }

        public static class ClientAdvertisesBean extends DataSupport implements Serializable {
            /**
             * referId : 157
             * adverId : 50
             * adverName : 雷群1
             * adType : 1
             * title : 兰博基尼
             * materialName : （文章列表页）兰博基尼
             * mbLink : http://qukandian.com/ads/test/articleList/index.html
             * pv : 1
             * order : 1
             * isEnableTraffic : true
             * clickGold : 20
             * isShowAdsIcon : true
             * isSecondClickGoldEnable : true
             * userHistoryIncomeVPT : 50000
             * giftPresentRate : 100
             */
            // 广告页面类型：首页，文章详情页，视频列表，视频详情页
            private int adPageType;
            private int adPageLocationId;
            // 页面所在位置
            private int pageIndex;
            // 广告关联表Id
            private int referId;
            // 广告位Id
            private int adLocId;
            // 广告商类型 1 自主广告 2 联盟广告 3 合作链接
            private int adType;
            // 广告商Id
            private int adverId;
            // 广告商名称
            private String adverName;
            // 点击一次可以获取到的金币量
            private int clickGold;

            // 广告位id
            private String adsCode;
            // 是否显示广告页标签
            private boolean isShowAdsIcon;

            // 允许唤醒其他APP，false：不唤醒，true：唤醒
            private boolean isAwaken;

            //  广告标题
            private String title;
            // 广告物料名称
            private String materialName;
            // 广告链接
            private String mbLink;
            ////////////291  新增自主广告原生UI///////////////
            private String adLink = "";//

            // 广告物料图片
            private List<AdMaterialImagesBean> adMaterialImages;
            /// 广告商点击规则
            public List<AdClRulesBean> adverClickRules;

            //广告所在位置 ("视频详情页大图","视频详情页列表"等)
            private String belong;

            private boolean isSecondClickGoldEnable;

            ////////////292
            private int duration;//广告时间`
            ///////////293
            private int deliveryMode = 1;   //投放方式 0

            public boolean isAwaken() {
                return isAwaken;
            }

            public void setAwaken(boolean awaken) {
                isAwaken = awaken;
            }

            public static class AdClRulesBean implements Serializable {
                private int clickCount;
                private String duration;
                private double presentRate;

                public int getClickCount() {
                    return clickCount;
                }

                public void setClickCount(int clickCount) {
                    this.clickCount = clickCount;
                }

                public String getDuration() {
                    return duration;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public double getPresentRate() {
                    return presentRate;
                }

                public void setPresentRate(double presentRate) {
                    this.presentRate = presentRate;
                }
            }

            public boolean isShowAdsIcon() {
                return isShowAdsIcon;
            }

            public void setShowAdsIcon(boolean showAdsIcon) {
                isShowAdsIcon = showAdsIcon;
            }

            public boolean isSecondClickGoldEnable() {
                return isSecondClickGoldEnable;
            }

            public void setSecondClickGoldEnable(boolean secondClickGoldEnable) {
                isSecondClickGoldEnable = secondClickGoldEnable;
            }

            public int getDeliveryMode() {
                return deliveryMode;
            }

            public void setDeliveryMode(int deliveryMode) {
                this.deliveryMode = deliveryMode;
            }

            public List<AdClRulesBean> getAdverClickRules() {
                return adverClickRules;
            }

            public void setAdverClickRules(List<AdClRulesBean> adverClickRules) {
                this.adverClickRules = adverClickRules;
            }

            public int getDuration() {
                if (duration == 0) {
                    duration = (int) (Math.random() * 3) + 1;
                }
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getPageIndex() {
                return pageIndex;
            }

            public void setPageIndex(int pageIndex) {
                this.pageIndex = pageIndex;
            }

            public int getAdPageLocationId() {
                return adPageType;
            }

            public void setAdPageLocationId(int adPageLocationId) {
                this.adPageLocationId = adPageLocationId;
            }

            public int getAdLocId() {
                return adLocId;
            }

            public void setAdLocId(int adLocId) {
                this.adLocId = adLocId;
            }

            public String getAdLink() {
                return adLink;
            }

            public void setAdLink(String adLink) {
                this.adLink = adLink;
            }

            public List<AdMaterialImagesBean> getAdMaterialImages() {
                return adMaterialImages;
            }

            public void setAdMaterialImages(List<AdMaterialImagesBean> adMaterialImages) {
                this.adMaterialImages = adMaterialImages;
            }

            public String getAdsCode() {
                return adsCode;
            }

            public void setAdsCode(String adsCode) {
                this.adsCode = adsCode;
            }

            public String getBelong() {
                return AdUtil.getBelong(pageIndex, adPageType);
            }

            public void setBelong(String belong) {
                this.belong = belong;
            }

            public ClientAdvertisesBean(int referId, int advertiserId, int adType) {
                this.referId = referId;
                this.adverId = advertiserId;
                this.adType = adType;
            }

            public ClientAdvertisesBean() {
            }

            public int getReferId() {
                return referId;
            }

            public void setReferId(int referId) {
                this.referId = referId;
            }

            public int getAdverId() {
                return adverId;
            }

            public void setAdverId(int adverId) {
                this.adverId = adverId;
            }

            public String getAdverName() {
                return adverName;
            }

            public void setAdverName(String adverName) {
                this.adverName = adverName;
            }

            public int getAdType() {
                return adType;
            }

            public void setAdType(int adType) {
                this.adType = adType;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMaterialName() {
                return materialName;
            }

            public void setMaterialName(String materialName) {
                this.materialName = materialName;
            }

            public String getMbLink() {
                return mbLink;
            }

            public void setMbLink(String mbLink) {
                this.mbLink = mbLink;
            }

            public int getClickGold() {
                if (AdUtil.mAdsCache.contains(toString())) {
                    return 0;
                }
                return clickGold;
            }

            public void setClickGold(int clickGold) {
                this.clickGold = clickGold;
            }

            public boolean isIsShowAdsIcon() {
                return isShowAdsIcon;
            }

            public void setIsShowAdsIcon(boolean isShowAdsIcon) {
                this.isShowAdsIcon = isShowAdsIcon;
            }

            public boolean isIsSecondClickGoldEnable() {
                return isSecondClickGoldEnable;
            }

            public void setIsSecondClickGoldEnable(boolean isSecondClickGoldEnable) {
                this.isSecondClickGoldEnable = isSecondClickGoldEnable;
            }

            public int getAdPageType() {
                return adPageType;
            }

            public void setAdPageType(int adPageType) {
                this.adPageType = adPageType;
            }

            public static class AdMaterialImagesBean implements Serializable {
                private String adMaterialId;
                private String imageName;
                private String src;

                public String getAdMaterialId() {
                    return adMaterialId;
                }

                public void setAdMaterialId(String adMaterialId) {
                    this.adMaterialId = adMaterialId;
                }

                public String getImageName() {
                    return imageName;
                }

                public void setImageName(String imageName) {
                    this.imageName = imageName;
                }

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }
            }

            @Override
            public String toString() {
                // 广告商类型 1 自主广告 2 联盟广告 3 合作链接
                if (adType == 2) {
                    return "adverId=" + adverId;
                }
                return "adverId=" + adverId +
                        ", adLink='" + adLink;
            }
        }
    }
}