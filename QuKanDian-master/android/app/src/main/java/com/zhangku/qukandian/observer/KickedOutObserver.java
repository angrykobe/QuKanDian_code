package com.zhangku.qukandian.observer;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/5/27.
 */

public class KickedOutObserver {
    private ArrayList<OnKickedOutListener> mOnKickedOutListeners = new ArrayList<>();

    private KickedOutObserver(){}

    private static KickedOutObserver mKickedOutObserver = null;
    public static KickedOutObserver getInstance(){
        if(mKickedOutObserver == null){
            synchronized (KickedOutObserver.class){
                if(mKickedOutObserver == null){
                    mKickedOutObserver = new KickedOutObserver();
                }
            }
        }
        return mKickedOutObserver;
    }

    public void addOnKickedOutListener(OnKickedOutListener onKickedOutListener){
        if(!mOnKickedOutListeners.contains(onKickedOutListener)){
            mOnKickedOutListeners.add(onKickedOutListener);
        }
    }

    public void removeOnKickedOutListener(OnKickedOutListener onKickedOutListener){
        if(mOnKickedOutListeners.contains(onKickedOutListener)){
            mOnKickedOutListeners.remove(onKickedOutListener);
        }
    }

    public void notifys(){
        for (OnKickedOutListener onKickedOutListener: mOnKickedOutListeners) {
            if(onKickedOutListener != null){
                onKickedOutListener.onKickedOutListener();
            }
        }
    }

    public interface OnKickedOutListener{
        void onKickedOutListener();
    }
}
