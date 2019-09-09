package com.zhangku.qukandian.observer;

import java.util.ArrayList;

/**
 * 用户每天可获取最大红包个数改变监听
 */

public class RedMaxNumEverydayChangeObserver {
    private ArrayList<OnRedChangeListener> mOnBgChangeListeners = new ArrayList<>();
    private static RedMaxNumEverydayChangeObserver mBaoxiangStateObserver = null;
    private RedMaxNumEverydayChangeObserver(){}
    public static RedMaxNumEverydayChangeObserver getInstance(){
        if(null == mBaoxiangStateObserver){
            synchronized (RedMaxNumEverydayChangeObserver.class){
                if(null == mBaoxiangStateObserver){
                    mBaoxiangStateObserver = new RedMaxNumEverydayChangeObserver();
                }
            }
        }
        return mBaoxiangStateObserver;
    }

    public void addOnBgChangeListener(OnRedChangeListener onBgChangeListener){
        if(!mOnBgChangeListeners.contains(onBgChangeListener)){
            mOnBgChangeListeners.add(onBgChangeListener);
        }
    }

    public void removeOnBgChangeListener(OnRedChangeListener onBgChangeListener){
        if(mOnBgChangeListeners.contains(onBgChangeListener)){
            mOnBgChangeListeners.remove(onBgChangeListener);
        }
    }

    public void updateState(boolean is){
        for (OnRedChangeListener o: mOnBgChangeListeners) {
            if(null != o){
              o.onRedChangeListener(is);
            }
        }
    }

    public interface OnRedChangeListener{
        void onRedChangeListener(boolean isShow);
    }

}
