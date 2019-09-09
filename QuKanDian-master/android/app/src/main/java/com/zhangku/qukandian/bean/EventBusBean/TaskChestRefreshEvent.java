package com.zhangku.qukandian.bean.EventBusBean;

public class TaskChestRefreshEvent {
    private boolean isRefresh;//是否刷新宝箱
    public TaskChestRefreshEvent(boolean msg) {
        this.isRefresh = msg;
    }
    public boolean getRefresh(){
        return isRefresh;
    }
}
