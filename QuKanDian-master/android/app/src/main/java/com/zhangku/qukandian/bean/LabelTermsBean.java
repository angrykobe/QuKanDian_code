package com.zhangku.qukandian.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by yuzuoning on 2017/4/18.
 */

public class LabelTermsBean extends DataSupport implements Serializable {
    /**
     * name : sample string 1
     * id : 2
     */

    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
