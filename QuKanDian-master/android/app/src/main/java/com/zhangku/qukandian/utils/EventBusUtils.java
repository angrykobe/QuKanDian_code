package com.zhangku.qukandian.utils;

import com.zhangku.qukandian.bean.UserBean;

import org.greenrobot.eventbus.EventBus;

public class EventBusUtils {
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void postUserBean(UserBean userBean) {
        EventBus.getDefault().post(userBean);
    }

}
