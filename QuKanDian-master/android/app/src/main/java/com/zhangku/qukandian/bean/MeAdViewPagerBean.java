package com.zhangku.qukandian.bean;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/10
 * 你不注释一下？
 */
public class MeAdViewPagerBean {

    /**
     * bannerConfigs : [{"name":"userpage_banner_1","imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180810/44b3d8fd03e647b797ce10ed33f0a6b4_1533893338.jpg","gotoLink":"com.zhangku.qukandian.activitys.MallUrlActivity","iosGotoLink":"ExchangeMallVC"},{"name":"userpage_banner_2","imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180810/926f7a2cf75548a0b814a2fc194dde01_1533892294.jpg","gotoLink":"www.wjnweb.top","iosGotoLink":"www.wjnweb.top"},{"name":"userpage_banner_3","imageLink":"http://cdn.qu.fi.pqmnz.com/backend/images/20180810/591d07d81a4b431f95ca939321bbe8c3_1533887401.jpg","gotoLink":"com.zhangku.qukandian.activitys.MallUrlActivity","iosGotoLink":"ExchangeMallVC"}]
     * statusForLogin : 1
     */

    private int statusForLogin;
    private List<BannerConfigsBean> bannerConfigs;

    public int getStatusForLogin() {
        return statusForLogin;
    }

    public void setStatusForLogin(int statusForLogin) {
        this.statusForLogin = statusForLogin;
    }

    public List<BannerConfigsBean> getBannerConfigs() {
        return bannerConfigs;
    }

    public void setBannerConfigs(List<BannerConfigsBean> bannerConfigs) {
        this.bannerConfigs = bannerConfigs;
    }

    public static class BannerConfigsBean {
        /**
         * name : userpage_banner_1
         * imageLink : http://cdn.qu.fi.pqmnz.com/backend/images/20180810/44b3d8fd03e647b797ce10ed33f0a6b4_1533893338.jpg
         * gotoLink : com.zhangku.qukandian.activitys.MallUrlActivity
         * iosGotoLink : ExchangeMallVC
         */

        private String name;
        private String imageLink;
        private String gotoLink;
        private String iosGotoLink;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImageLink() {
            return imageLink;
        }

        public void setImageLink(String imageLink) {
            this.imageLink = imageLink;
        }

        public String getGotoLink() {
            return gotoLink;
        }

        public void setGotoLink(String gotoLink) {
            this.gotoLink = gotoLink;
        }

        public String getIosGotoLink() {
            return iosGotoLink;
        }

        public void setIosGotoLink(String iosGotoLink) {
            this.iosGotoLink = iosGotoLink;
        }
    }
}
