package com.zhangku.qukandian.bean;

public class PostTextDtl {
    private DetailsBean postText;
    private DisLikeNumBean userBehavior;
    private boolean isFavourite;

    public DetailsBean getPostText() {
        return postText;
    }

    public void setPostText(DetailsBean postText) {
        postText = postText;
    }

    public DisLikeNumBean getUserBehavior() {
        return userBehavior;
    }

    public void setUserBehavior(DisLikeNumBean userBehavior) {
        userBehavior = userBehavior;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}