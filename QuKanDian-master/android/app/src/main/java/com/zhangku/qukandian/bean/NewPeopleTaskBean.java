package com.zhangku.qukandian.bean;

/**
 * Created by yuzuoning on 2017/4/7.
 */

public class NewPeopleTaskBean extends TaskBean {
    private boolean isChange;//新手任务是否改变状态
    private boolean ishide;//是否隐藏（热搜透明度）

    public boolean isIshide() {
        return ishide;
    }

    public void setIshide(boolean ishide) {
        this.ishide = ishide;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }
}
