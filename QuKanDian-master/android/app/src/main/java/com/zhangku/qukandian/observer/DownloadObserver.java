package com.zhangku.qukandian.observer;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/6/7.
 */

public class DownloadObserver {
    private ArrayList<OnDownloadObserverListener> mOnDownloadObserverListeners = new ArrayList<>();
    private static DownloadObserver mDownloadObserver = null;
    private DownloadObserver(){}
    public static DownloadObserver getInstance(){
        if(null == mDownloadObserver){
            synchronized (DownloadObserver.class){
                if(null == mDownloadObserver){
                    mDownloadObserver = new DownloadObserver();
                }
            }
        }
        return mDownloadObserver;
    }

    public void addOnDownloadObserverListener(OnDownloadObserverListener onDownloadObserverListener){
        if(!mOnDownloadObserverListeners.contains(onDownloadObserverListener)){
            mOnDownloadObserverListeners.add(onDownloadObserverListener);
        }
    }

    public void removeOnDownloadObserverListener(OnDownloadObserverListener onDownloadObserverListener){
        if(mOnDownloadObserverListeners.contains(onDownloadObserverListener)){
            mOnDownloadObserverListeners.remove(onDownloadObserverListener);
        }
    }

    public void notifyProgress(int c,int t){
        for (OnDownloadObserverListener o:mOnDownloadObserverListeners) {
            if(null != o){
                o.onProgress(c, t);
            }
        }
    }
    public interface OnDownloadObserverListener{
        void onProgress(int curren, int total);
    }
}
