package com.zhangku.qukandian.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by yuzuoning on 2017/4/18.
 */

public class PostTextDatasBean extends DataSupport {
    /**
     * postId : 1
     * content : sample string 2
     * id : 3
     */

    private int postId;
    private String content;
    private int id;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
