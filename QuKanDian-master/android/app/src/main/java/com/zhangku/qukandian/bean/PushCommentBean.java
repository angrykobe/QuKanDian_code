package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/9/14.
 */

public class PushCommentBean {

    /**
     * postId : 1
     * parentId : 2
     * content : sample string 3
     * likeNum : 4
     * id : 5
     */

    private int postId;
    private int parentId;
    private String content;
    private int likeNum;
    private int id;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
