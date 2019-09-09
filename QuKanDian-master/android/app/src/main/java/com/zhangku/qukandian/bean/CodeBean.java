package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2019/1/27
 * 你不注释一下？
 */
public class CodeBean {
    private String contentType;
    private String fileContents;
    private String fileDownloadName;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFileContents() {
        return fileContents;
    }

    public void setFileContents(String fileContents) {
        this.fileContents = fileContents;
    }

    public String getFileDownloadName() {
        return fileDownloadName;
    }

    public void setFileDownloadName(String fileDownloadName) {
        this.fileDownloadName = fileDownloadName;
    }
}
