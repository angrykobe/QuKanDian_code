package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/8/23.
 */

public class PushBean {
    private int type;
    private int postId;
    private String title;

    public PushBean(int postId, String title, int type) {
        this.postId = postId;
        this.title = title;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
