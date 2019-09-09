package com.zhangku.qukandian.bean.EventBusBean;

public class DaySignViewEvent {
    private boolean isSignSuccess;//
    public DaySignViewEvent(boolean isSignSuccess) {
        this.isSignSuccess = isSignSuccess;
    }
    public boolean isSignSuccess(){
        return isSignSuccess;
    }
}
