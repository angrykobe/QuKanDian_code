package com.zhangku.qukandian.observer;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/5/6.
 * 消息改变监听
 */

public class ClickTipObserver {
    private ArrayList<OnClickTipListener> mOnClickTipListeners = new ArrayList<>();
    private ClickTipObserver(){}

    private static ClickTipObserver mClickTipObserver = null;
    public static ClickTipObserver getInstance(){
        if(mClickTipObserver == null){
            synchronized (ClickTipObserver.class){
                if(mClickTipObserver == null){
                    mClickTipObserver = new ClickTipObserver();
                }
            }
        }
        return mClickTipObserver;
    }

    public void addListener(OnClickTipListener onClickTipListener){
        if(!mOnClickTipListeners.contains(onClickTipListener)){
            mOnClickTipListeners.add(onClickTipListener);
        }
    }
    public void removeListener(OnClickTipListener onClickTipListener){
        if(mOnClickTipListeners.contains(onClickTipListener)){
            mOnClickTipListeners.remove(onClickTipListener);
        }
    }

    public void notifyUpdate(int type,int number){
        for (OnClickTipListener o:mOnClickTipListeners) {
            if(null != o){
                o.onClickListener(type, number);
            }
        }
    }

    public interface OnClickTipListener{
        void onClickListener(int type,int number);
    }
}
