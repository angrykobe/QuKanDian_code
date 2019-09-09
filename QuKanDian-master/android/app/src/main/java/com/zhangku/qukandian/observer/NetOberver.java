package com.zhangku.qukandian.observer;


import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.utils.CommonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2016/5/18.
 * 网络类型监听
 */
public class NetOberver {
    private static NetOberver mNetOberver;
    private List<OnNetTypeChangeListener> mOnNetTypeChangeListeners = new ArrayList<>();
    private boolean  mNerTypeIsWifi;
    private boolean  mIsNetAvailable;

    public static NetOberver getInstance(){
        if(null == mNetOberver)synchronized (NetOberver.class){
            if(null == mNetOberver){
                mNetOberver = new NetOberver();
            }
        }
        return mNetOberver;
    }

    public NetOberver(){
        mIsNetAvailable = CommonHelper.isNetworkAvailable(QuKanDianApplication.getAppContext());
        mNerTypeIsWifi  = CommonHelper.isWifi(QuKanDianApplication.getAppContext());
    }
    public interface OnNetTypeChangeListener{
        void onNetChange(boolean isAvailable, boolean isWifi);
    }

    public void addNetTypeChangeListener(OnNetTypeChangeListener onNetTypeChangeListener){
        if(onNetTypeChangeListener != null && !mOnNetTypeChangeListeners.contains(onNetTypeChangeListener)){
            mOnNetTypeChangeListeners.add(onNetTypeChangeListener);
        }
    }

    public void removeNetTypeChangeListener(OnNetTypeChangeListener onNetTypeChangeListener){
        if(mOnNetTypeChangeListeners.contains(onNetTypeChangeListener)){
            mOnNetTypeChangeListeners.remove(onNetTypeChangeListener);
        }
    }

    public void  notityNetChange(boolean isAvailable, boolean isWifi){
        mNerTypeIsWifi   = isWifi;
        mIsNetAvailable  = isAvailable;
        for(int i = 0 ; i < mOnNetTypeChangeListeners.size(); i ++){
            mOnNetTypeChangeListeners.get(i).onNetChange(isAvailable, isWifi);
        }
    }

    public boolean isWifi(){
        return mNerTypeIsWifi;
    }

    public boolean isNetAvailable(){
        return  mIsNetAvailable;
    }
}
