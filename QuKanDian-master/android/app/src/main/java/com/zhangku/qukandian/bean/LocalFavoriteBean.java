package com.zhangku.qukandian.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by yuzuoning on 2017/5/11.
 */

public class LocalFavoriteBean extends DataSupport {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private int postId;
    private boolean isFavorite;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
