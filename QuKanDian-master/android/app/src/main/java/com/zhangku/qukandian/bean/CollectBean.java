package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/5
 * 你不注释一下？
 */
public class CollectBean  {
    private int postId;
    private String zyId;
    private int textType;//0文章，1视频

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getZyId() {
        return zyId;
    }

    public void setZyId(String zyId) {
        this.zyId = zyId;
    }

    public int getTextType() {
        return textType;
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }
}
