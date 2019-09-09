package com.zhangku.qukandian.observer;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/5/15.
 */

public class HongbaoShowObserver {
    private ArrayList<OnHongbaoListener> mOnHongbaoListeners = new ArrayList<>();
    private HongbaoShowObserver(){

    }
    private static HongbaoShowObserver mHongbaoShowObserver = null;
    public static HongbaoShowObserver getInstance(){
        if(null == mHongbaoShowObserver){
            synchronized (HongbaoShowObserver.class){
                if(null == mHongbaoShowObserver){
                    mHongbaoShowObserver = new HongbaoShowObserver();
                }
            }
        }
        return mHongbaoShowObserver;
    }

    public void addOnHongbaoShowObserver(OnHongbaoListener hongbaoShowObserver){
        if(!mOnHongbaoListeners.contains(hongbaoShowObserver)){
            mOnHongbaoListeners.add(hongbaoShowObserver);
        }
    }

    public void removeOnHongbaoListener(OnHongbaoListener onHongbaoListener){
        if(mOnHongbaoListeners.contains(onHongbaoListener)){
            mOnHongbaoListeners.remove(onHongbaoListener);
        }
    }

    public void notifyUp(String type){
        for (OnHongbaoListener o:mOnHongbaoListeners) {
            if(null != o){
                o.onHongbaoListener(type);
            }
        }
    }

    public interface OnHongbaoListener{
        void onHongbaoListener(String type);
    }
}
