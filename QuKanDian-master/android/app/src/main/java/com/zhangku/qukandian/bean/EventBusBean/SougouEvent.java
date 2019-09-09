package com.zhangku.qukandian.bean.EventBusBean;
//热搜 - 搜狗  的红包数量变化
public class SougouEvent {
    private int redNum;//
    public SougouEvent(int redNum) {
        this.redNum = redNum;
    }
    public int getRedNum(){
        return redNum;
    }
}
