package com.zhangku.qukandian.observer;

import com.zhangku.qukandian.bean.WithdrawalsRemindBean;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/6/21.
 */

public class PingObserver {

    private PingObserver() {
    }

    private ArrayList<OnPintSuccessListener> mOnPintSuccessListeners = new ArrayList<>();

    private static PingObserver mPingObserver = null;

    public static PingObserver getInstance() {
        if (null == mPingObserver) {
            synchronized (PingObserver.class) {
                if (null == mPingObserver) {
                    mPingObserver = new PingObserver();
                }
            }
        }
        return mPingObserver;
    }

    public void addOOnPintSuccessListener(OnPintSuccessListener onPintSuccessListener) {
        if (!mOnPintSuccessListeners.contains(onPintSuccessListener)) {
            mOnPintSuccessListeners.add(onPintSuccessListener);
        }
    }

    public void removeOnPintSuccessListener(OnPintSuccessListener onPintSuccessListener) {
        if (mOnPintSuccessListeners.contains(onPintSuccessListener)) {
            mOnPintSuccessListeners.remove(onPintSuccessListener);
        }
    }

    public void notifySuccess(ArrayList<WithdrawalsRemindBean> withdrawalsRemindBean,int grayscaleNum,int channelVersion){
        for (OnPintSuccessListener o:mOnPintSuccessListeners) {
            if(null != o){
                o.onPintSuccessListener(withdrawalsRemindBean, grayscaleNum,channelVersion);
            }
        }
    }

    public interface OnPintSuccessListener {
        void onPintSuccessListener(ArrayList<WithdrawalsRemindBean> withdrawalsRemindBean,int grayscaleNum,int channelVersion);
    }


}
