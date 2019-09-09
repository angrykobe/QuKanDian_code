package com.zhangku.qukandian.bean.EventBusBean;

public class ChaiBaoxiangEvent {
    private boolean isOnclick;//
    public ChaiBaoxiangEvent(boolean isOnclick) {
        this.isOnclick = isOnclick;
    }
    public boolean isOnclick(){
        return isOnclick;
    }
}
