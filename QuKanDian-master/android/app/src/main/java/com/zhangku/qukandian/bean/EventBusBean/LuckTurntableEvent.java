package com.zhangku.qukandian.bean.EventBusBean;
//幸运宝箱的 EventBus事件
public class LuckTurntableEvent {
    private int gold;//是否刷新宝箱
    public LuckTurntableEvent(int gold) {
        this.gold = gold;
    }
    public int getGold(){
        return gold;
    }
}
