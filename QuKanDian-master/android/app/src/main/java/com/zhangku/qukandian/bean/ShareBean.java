package com.zhangku.qukandian.bean;

import java.util.List;

/**
 * Created by yuzuoning on 2018/2/2.
 */

public class ShareBean {
    /**
     * shareFriendText : 海量头条资讯，有趣有料有看点，随便刷刷新闻就有零花钱赚 ~  还支持微信提现秒到账。
     我的邀请码：{yqm}
     * shareIncomeImageLink : http://qutout.oss-cn-shanghai.aliyuncs.com/backend/images/20180203/601d649777fd41839dd9bf0a92fd2854_1517629086.jpg
     * wechatIcon : http://qutout.oss-cn-shanghai.aliyuncs.com/backend/images/20180203/6c1a05cda0a145b69a4bbf701eba83be_1517629088.jpg
     * wechatTitle : 使用type、plain和round属性来定义 Button 的样式。
     * wechatDesc : 没有边框和背景色的按钮。
     * shareFrientPosterItems : [{"order":1,"imageLink":"http://cdn.qu.fi.pqmnz.com/shoutu/qrcode_bg1.png"},{"order":2,"imageLink":"http://cdn.qu.fi.pqmnz.com/shoutu/qrcode_bg.png"},{"order":3,"imageLink":"http://cdn.qu.fi.pqmnz.com/shoutu/share_income_bg.png"},{"order":4,"imageLink":"http://qutout.oss-cn-shanghai.aliyuncs.com/backend/images/20180203/18b62fe927414edbb2ccef8c342edafc_1517629002.png"},{"order":5,"imageLink":"http://qutout.oss-cn-shanghai.aliyuncs.com/backend/images/20180203/d0fe864866144fabb87f9028950e05c4_1517629002.jpg"},{"order":6,"imageLink":"http://cdn.qu.fi.pqmnz.com/prod/img/201802/ab9670b9dccc4a148f02027524aea458.jpg"},{"order":7,"imageLink":"http://cdn.qu.fi.pqmnz.com/prod/img/201802/6fa5ae36de3b46a5b126d7da8f85dcc8.jpg"}]
     */

    private String shareFriendText;
    private String shareIncomeImageLink;
    private String wechatIcon;
    private String wechatTitle;
    private String wechatDesc;
    private List<ShareFrientPosterItemsBean> shareFrientPosterItems;

    public String getShareFriendText() {
        return shareFriendText;
    }

    public void setShareFriendText(String shareFriendText) {
        this.shareFriendText = shareFriendText;
    }

    public String getShareIncomeImageLink() {
        return shareIncomeImageLink;
    }

    public void setShareIncomeImageLink(String shareIncomeImageLink) {
        this.shareIncomeImageLink = shareIncomeImageLink;
    }

    public String getWechatIcon() {
        return wechatIcon;
    }

    public void setWechatIcon(String wechatIcon) {
        this.wechatIcon = wechatIcon;
    }

    public String getWechatTitle() {
        return wechatTitle;
    }

    public void setWechatTitle(String wechatTitle) {
        this.wechatTitle = wechatTitle;
    }

    public String getWechatDesc() {
        return wechatDesc;
    }

    public void setWechatDesc(String wechatDesc) {
        this.wechatDesc = wechatDesc;
    }

    public List<ShareFrientPosterItemsBean> getShareFrientPosterItems() {
        return shareFrientPosterItems;
    }

    public void setShareFrientPosterItems(List<ShareFrientPosterItemsBean> shareFrientPosterItems) {
        this.shareFrientPosterItems = shareFrientPosterItems;
    }

    public static class ShareFrientPosterItemsBean {
        /**
         * order : 1
         * imageLink : http://cdn.qu.fi.pqmnz.com/shoutu/qrcode_bg1.png
         */

        private int order;
        private String imageLink;

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getImageLink() {
            return imageLink;
        }

        public void setImageLink(String imageLink) {
            this.imageLink = imageLink;
        }
    }
}
