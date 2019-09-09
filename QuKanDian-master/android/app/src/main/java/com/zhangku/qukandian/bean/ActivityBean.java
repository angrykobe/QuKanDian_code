package com.zhangku.qukandian.bean;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/24
 * 页面跳转
 */
public class ActivityBean {
    private String activity;
    private List<Params> params;

    public static class Params{
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public List<Params> getParams() {
        return params;
    }

    public void setParams(List<Params> params) {
        this.params = params;
    }
}
