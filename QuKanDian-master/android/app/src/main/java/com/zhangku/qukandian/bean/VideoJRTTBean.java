package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/11/21.
 */

public class VideoJRTTBean {
    /**
     * definition : 360p
     * vtype : mp4
     * vwidth : 640
     * vheight : 360
     * bitrate : 248069
     * size : 24691467
     * codec_type : h264
     * logo_type :
     * main_url : aHR0cDovL3YzLXR0Lml4aWd1YS5jb20vYzZmY2RlMjFmZDc5YzI4ZWIzODgxMzJkMDU2ZGY5ZGMvNWExM2Q1MzUvdmlkZW8vbS8yMjA5YTY4OTcxYzI4ZDE0MWMzYWEyODUwNDRmODkwYTZkODExNDY5Y2UwMDAwMjNiNjg3NjNkNDI4Lw==
     * backup_url_1 : aHR0cDovL3YxLXR0Lml4aWd1YXZpZGVvLmNvbS9jOTFiN2I0YmQwNTI2MGViNzc4YWEwZGYzNzM4MWY0OC81YTEzZDUzNS92aWRlby9tLzIyMDlhNjg5NzFjMjhkMTQxYzNhYTI4NTA0NGY4OTBhNmQ4MTE0NjljZTAwMDAyM2I2ODc2M2Q0Mjgv
     * user_video_proxy : 1
     * socket_buffer : 5581440
     * preload_size : 327680
     * preload_interval : 60
     * preload_min_step : 5
     * preload_max_step : 10
     */

    private String definition;
    private String vtype;
    private int vwidth;
    private int vheight;
    private int bitrate;
    private int size;
    private String codec_type;
    private String logo_type;
    private String main_url;
    private String backup_url_1;
    private int user_video_proxy;
    private int socket_buffer;
    private int preload_size;
    private int preload_interval;
    private int preload_min_step;
    private int preload_max_step;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getVtype() {
        return vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
    }

    public int getVwidth() {
        return vwidth;
    }

    public void setVwidth(int vwidth) {
        this.vwidth = vwidth;
    }

    public int getVheight() {
        return vheight;
    }

    public void setVheight(int vheight) {
        this.vheight = vheight;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getCodec_type() {
        return codec_type;
    }

    public void setCodec_type(String codec_type) {
        this.codec_type = codec_type;
    }

    public String getLogo_type() {
        return logo_type;
    }

    public void setLogo_type(String logo_type) {
        this.logo_type = logo_type;
    }

    public String getMain_url() {
        return main_url;
    }

    public void setMain_url(String main_url) {
        this.main_url = main_url;
    }

    public String getBackup_url_1() {
        return backup_url_1;
    }

    public void setBackup_url_1(String backup_url_1) {
        this.backup_url_1 = backup_url_1;
    }

    public int getUser_video_proxy() {
        return user_video_proxy;
    }

    public void setUser_video_proxy(int user_video_proxy) {
        this.user_video_proxy = user_video_proxy;
    }

    public int getSocket_buffer() {
        return socket_buffer;
    }

    public void setSocket_buffer(int socket_buffer) {
        this.socket_buffer = socket_buffer;
    }

    public int getPreload_size() {
        return preload_size;
    }

    public void setPreload_size(int preload_size) {
        this.preload_size = preload_size;
    }

    public int getPreload_interval() {
        return preload_interval;
    }

    public void setPreload_interval(int preload_interval) {
        this.preload_interval = preload_interval;
    }

    public int getPreload_min_step() {
        return preload_min_step;
    }

    public void setPreload_min_step(int preload_min_step) {
        this.preload_min_step = preload_min_step;
    }

    public int getPreload_max_step() {
        return preload_max_step;
    }

    public void setPreload_max_step(int preload_max_step) {
        this.preload_max_step = preload_max_step;
    }
}
