package com.zhangku.qukandian.bean.EventBusBean;

import com.zhangku.qukandian.bean.TaskChestBean;

public class TaskChestEvent {
    private TaskChestBean bean;

    public TaskChestEvent(TaskChestBean msg) {
        this.bean = msg;
    }
    public TaskChestBean getMsg(){
        return bean;
    }
}
