package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/9/25.
 */

public class DialogCommentBean {
    private int header;
    private String time;
    private String name;
    private String content;

    public DialogCommentBean(int header,String time,String name,String content){
        this.header = header;
        this.time = time;
        this.name = name;
        this.content = content;
    }

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
