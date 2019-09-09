package com.zhangku.qukandian.bean;

import java.util.List;

/**
 * Created by yuzuoning on 2017/4/19.
 */

public class HelpBean {

    /**
     * docHelpers : [{"title":"什么是金币","docImages":[{"src":"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2710528812,1128460096&fm=117&gp=0.jpg"}],"description":"金币内容是，金币内容是金币内容是金币内容是金币内容是","creationTime":"0001-01-01T00:00:00","id":1},{"title":"如何兑换零钱","docImages":[],"description":"金币内容是，金币内容是金币内容是金币内容是金币内容是","creationTime":"0001-01-01T00:00:00","id":2},{"title":"为什么看了文章没有金币","docImages":[],"description":"金币内容是，金币内容是金币内容是金币内容是金币内容是","creationTime":"0001-01-01T00:00:00","id":3},{"title":"如何查看自己收益","docImages":[],"description":"金币内容是，金币内容是金币内容是金币内容是金币内容是","creationTime":"0001-01-01T00:00:00","id":4},{"title":"如何赚取金币","docImages":[],"description":"金币内容是，金币内容是金币内容是金币内容是金币内容是","creationTime":"0001-01-01T00:00:00","id":5}]
     * name : q_gold
     * displayName : 金币问题
     * orderId : 0
     * id : 1
     */

    private String name;
    private String displayName;
    private int orderId;
    private int id;
    private List<DocHelpersBean> docHelpers;

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

    public List<DocHelpersBean> getDocHelpers() {
        return docHelpers;
    }

    public void setDocHelpers(List<DocHelpersBean> docHelpers) {
        this.docHelpers = docHelpers;
    }

    public static class DocHelpersBean {
        /**
         * title : 什么是金币
         * docImages : [{"src":"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2710528812,1128460096&fm=117&gp=0.jpg"}]
         * description : 金币内容是，金币内容是金币内容是金币内容是金币内容是
         * creationTime : 0001-01-01T00:00:00
         * id : 1
         */

        private String title;
        private String description;
        private String creationTime;
        private int id;
        private List<DocImagesBean> docImages;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        private boolean isSelected;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<DocImagesBean> getDocImages() {
            return docImages;
        }

        public void setDocImages(List<DocImagesBean> docImages) {
            this.docImages = docImages;
        }

        public static class DocImagesBean {
            /**
             * src : https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2710528812,1128460096&fm=117&gp=0.jpg
             */

            private String src;

            public String getSrc() {
                return src;
            }

            public void setSrc(String src) {
                this.src = src;
            }
        }
    }
}
