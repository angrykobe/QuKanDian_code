package com.zhangku.qukandian.bean;


import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by yuzuoning on 17/p1/3.
 */

public class ChannelBean extends DataSupport implements Serializable {
    /**
     * id : 1
     * name : sample string 2
     * displayName : sample string 3
     * kindType : 0
     * orderId : 4
     */

    private int id;
    private String displayName;
    private int kindType;
    private int orderId;
    private int channelId;
    private boolean yesNo;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public boolean isYesNo() {
        return yesNo;
    }

    public void setYesNo(boolean yesNo) {
        this.yesNo = yesNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getKindType() {
        return kindType;
    }

    public void setKindType(int kindType) {
        this.kindType = kindType;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
