package com.zhangku.qukandian.bean;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/17
 * 你不注释一下？
 */
public class MyBannerBean {
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
         * imageLink : http://cdn.qu.fi.pqmnz.com/backend/images/20180810/145abdf31f524298997f4b493b729a26_1533867527.jpeg
         * gotoLink : http://m.cudaojia.com?appKey=02e9962064374d82a7b030f1dd5f69a7&appType=app&appEntrance=5&business=money
         * iosGotoLink : http://m.cudaojia.com?appKey=02e9962064374d82a7b030f1dd5f69a7&appType=app&appEntrance=5&business=money
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
