package com.zhangku.qukandian.observer;

import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

/**
 * Created by xuzhida| on 2017/5/27.
 * 广告下载通知上报使用
 */

public class AdDownObserver {
    private ArrayMap<Long,OnAdDownListener> mOnAdDownListeners = new ArrayMap<>();

    private AdDownObserver(){}

    private static AdDownObserver mAdDownObserver = null;
    public static AdDownObserver getInstance(){
        if(mAdDownObserver == null){
            synchronized (AdDownObserver.class){
                if(mAdDownObserver == null){
                    mAdDownObserver = new AdDownObserver();
                }
            }
        }
        return mAdDownObserver;
    }

    public void addOnAdDownListener(Long downID ,OnAdDownListener onAdDownListener){
        mOnAdDownListeners.put(downID,onAdDownListener);
    }

    public void removeOnAdDownListener(Long downID){
        mOnAdDownListeners.remove(downID);
    }

    @Nullable
    public OnAdDownListener getListenerObserver(Long downID){
        return mOnAdDownListeners.get(downID);
    }

    public interface OnAdDownListener{
        //开始下载
        void onAdDownStartListener();
        //下载完成
        void onAdDownCompleteListener();
        //开始安装
        void onAdDownInstallStartListener();
        //完成安装
        void onAdDownInstallCompleteListener();
    }
}
