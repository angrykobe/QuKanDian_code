package com.zhangku.qukandian.bean;

import java.util.List;

/**
 * Created by yuzuoning on 2018/3/15.
 */

public class TuanInfoBean {
    /**
     * activity_id : 480795
     * group_alias : 1c3uuvpa6
     * goods_title : 测试商品二
     * goods_alias : 36949b5rqef8s
     * join_num : 2
     * remind_time : 84785
     * is_limit : false
     * gap_num : 1
     * buy_num : 0
     * goods_limit : 100
     * thumb_url : https://img.yzcdn.cn/upload_files/2018/01/22/FnlHRufXMtUI_AbAEP0tux_nDL1T.png
     * origin_price : 1.00
     * price : 0.01
     * is_joined_group : false
     * is_groupon_success : false
     * is_groupon_failed : false
     * order_no : null
     * is_invalid : false
     * join_records : [{"fans_nickname":"11298","fans_picture":"http://cdn.qu.fi.pqmnz.com/test/user/avatar/2/wximg_11298.jpg?t=f068a4abced84f6599bde82ec15d3cca?t=a8eb57a48fd34e72b54a0e4342e8bffe?t=83ca19bb3ea84dd785f6486f582be80f","is_head":true,"is_mock":false,"is_agency_receive":false}]
     * new_group : false
     * is_head : null
     * is_agency_receive : null
     * attachment_url : https://img.yzcdn.cn/upload_files/2018/01/22/FnlHRufXMtUI_AbAEP0tux_nDL1T.png
     * group_type : 0
     * view :
     */

    private String activity_id;
    private String group_alias;
    private String goods_title;
    private String goods_alias;
    private String join_num;
    private int remind_time;
    private boolean is_limit;
    private int gap_num;
    private int buy_num;
    private String goods_limit;
    private String thumb_url;
    private String origin_price;
    private String price;
    private boolean is_joined_group;
    private boolean is_groupon_success;
    private boolean is_groupon_failed;
    private Object order_no;
    private boolean is_invalid;
    private boolean new_group;
    private Object is_head;
    private Object is_agency_receive;
    private String attachment_url;
    private int group_type;
    private String view;
    private List<JoinRecordsBean> join_records;

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getGroup_alias() {
        return group_alias;
    }

    public void setGroup_alias(String group_alias) {
        this.group_alias = group_alias;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getGoods_alias() {
        return goods_alias;
    }

    public void setGoods_alias(String goods_alias) {
        this.goods_alias = goods_alias;
    }

    public String getJoin_num() {
        return join_num;
    }

    public void setJoin_num(String join_num) {
        this.join_num = join_num;
    }

    public int getRemind_time() {
        return remind_time;
    }

    public void setRemind_time(int remind_time) {
        this.remind_time = remind_time;
    }

    public boolean isIs_limit() {
        return is_limit;
    }

    public void setIs_limit(boolean is_limit) {
        this.is_limit = is_limit;
    }

    public int getGap_num() {
        return gap_num;
    }

    public void setGap_num(int gap_num) {
        this.gap_num = gap_num;
    }

    public int getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(int buy_num) {
        this.buy_num = buy_num;
    }

    public String getGoods_limit() {
        return goods_limit;
    }

    public void setGoods_limit(String goods_limit) {
        this.goods_limit = goods_limit;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(String origin_price) {
        this.origin_price = origin_price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isIs_joined_group() {
        return is_joined_group;
    }

    public void setIs_joined_group(boolean is_joined_group) {
        this.is_joined_group = is_joined_group;
    }

    public boolean isIs_groupon_success() {
        return is_groupon_success;
    }

    public void setIs_groupon_success(boolean is_groupon_success) {
        this.is_groupon_success = is_groupon_success;
    }

    public boolean isIs_groupon_failed() {
        return is_groupon_failed;
    }

    public void setIs_groupon_failed(boolean is_groupon_failed) {
        this.is_groupon_failed = is_groupon_failed;
    }

    public Object getOrder_no() {
        return order_no;
    }

    public void setOrder_no(Object order_no) {
        this.order_no = order_no;
    }

    public boolean isIs_invalid() {
        return is_invalid;
    }

    public void setIs_invalid(boolean is_invalid) {
        this.is_invalid = is_invalid;
    }

    public boolean isNew_group() {
        return new_group;
    }

    public void setNew_group(boolean new_group) {
        this.new_group = new_group;
    }

    public Object getIs_head() {
        return is_head;
    }

    public void setIs_head(Object is_head) {
        this.is_head = is_head;
    }

    public Object getIs_agency_receive() {
        return is_agency_receive;
    }

    public void setIs_agency_receive(Object is_agency_receive) {
        this.is_agency_receive = is_agency_receive;
    }

    public String getAttachment_url() {
        return attachment_url;
    }

    public void setAttachment_url(String attachment_url) {
        this.attachment_url = attachment_url;
    }

    public int getGroup_type() {
        return group_type;
    }

    public void setGroup_type(int group_type) {
        this.group_type = group_type;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public List<JoinRecordsBean> getJoin_records() {
        return join_records;
    }

    public void setJoin_records(List<JoinRecordsBean> join_records) {
        this.join_records = join_records;
    }

    public static class JoinRecordsBean {
        /**
         * fans_nickname : 11298
         * fans_picture : http://cdn.qu.fi.pqmnz.com/test/user/avatar/2/wximg_11298.jpg?t=f068a4abced84f6599bde82ec15d3cca?t=a8eb57a48fd34e72b54a0e4342e8bffe?t=83ca19bb3ea84dd785f6486f582be80f
         * is_head : true
         * is_mock : false
         * is_agency_receive : false
         */

        private String fans_nickname;
        private String fans_picture;
        private boolean is_head;
        private boolean is_mock;
        private boolean is_agency_receive;

        public String getFans_nickname() {
            return fans_nickname;
        }

        public void setFans_nickname(String fans_nickname) {
            this.fans_nickname = fans_nickname;
        }

        public String getFans_picture() {
            return fans_picture;
        }

        public void setFans_picture(String fans_picture) {
            this.fans_picture = fans_picture;
        }

        public boolean isIs_head() {
            return is_head;
        }

        public void setIs_head(boolean is_head) {
            this.is_head = is_head;
        }

        public boolean isIs_mock() {
            return is_mock;
        }

        public void setIs_mock(boolean is_mock) {
            this.is_mock = is_mock;
        }

        public boolean isIs_agency_receive() {
            return is_agency_receive;
        }

        public void setIs_agency_receive(boolean is_agency_receive) {
            this.is_agency_receive = is_agency_receive;
        }
    }
}
