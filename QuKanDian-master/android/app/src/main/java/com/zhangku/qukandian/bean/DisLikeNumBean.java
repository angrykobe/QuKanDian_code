package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/6/15
 * 你不注释一下？
 */
public class DisLikeNumBean {
    private boolean isLike;
    private boolean isDislike;
    private int likeNumber;

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isDislike() {
        return isDislike;
    }

    public void setDislike(boolean dislike) {
        isDislike = dislike;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }
}
