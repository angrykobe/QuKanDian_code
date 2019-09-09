package com.zhangku.qukandian.bean.EventBusBean;

/**
 * 任务列表-新手福利做完-要刷新主页的新手福利标识
 */
public class TaskNewPeopleEvent {
    private boolean isRefresh;//
    public TaskNewPeopleEvent(boolean msg) {
        this.isRefresh = msg;
    }
    public boolean getRefresh(){
        return isRefresh;
    }
}
