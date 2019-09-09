package com.zhangku.qukandian.observer;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/12/23.
 */

public class ScrollListenerObserver {
    private ArrayList<OnScrollListener> mOnScrollListeners = new ArrayList<>();

    private static ScrollListenerObserver mScrollListenerObserver = null;
    private ScrollListenerObserver(){}
    public static ScrollListenerObserver getmScrollListenerObserver(){
        if(null == mScrollListenerObserver){
            synchronized (ScrollListenerObserver.class){
                if(null == mScrollListenerObserver){
                    mScrollListenerObserver = new ScrollListenerObserver();
                }
            }
        }
        return mScrollListenerObserver;
    }

    public void addScrollListener(OnScrollListener nScrollListener){
        if(!mOnScrollListeners.contains(nScrollListener)){
            mOnScrollListeners.add(nScrollListener);
        }
    }

    public void removeScrollListener(OnScrollListener onScrollListener){
        if(mOnScrollListeners.contains(onScrollListener)){
            mOnScrollListeners.remove(onScrollListener);
        }
    }

    public void updateState(int size){
        for (int i = 0; i < mOnScrollListeners.size(); i++) {
            if(mOnScrollListeners.get(i) != null){
                mOnScrollListeners.get(i).onScrollListener(size);
            }
        }
    }


    public interface OnScrollListener{
        void onScrollListener(int size);
    }
}
