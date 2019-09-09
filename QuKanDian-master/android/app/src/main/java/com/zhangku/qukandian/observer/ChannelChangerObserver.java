package com.zhangku.qukandian.observer;

import com.zhangku.qukandian.bean.ChannelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/3/28.
 */

public class ChannelChangerObserver {
    private static ArrayList<IChannelChangerListener> mLists = new ArrayList<>();
    private static ChannelChangerObserver mChannelChangerObserver = null;
    private ChannelChangerObserver(){}
    public static ChannelChangerObserver getInstance(){
        if(null == mChannelChangerObserver){
            synchronized (ChannelChangerObserver.class){
                if(null == mChannelChangerObserver){
                    mChannelChangerObserver = new ChannelChangerObserver();
                }
            }
        }
        return mChannelChangerObserver;
    }

    public void addChannerListener(IChannelChangerListener iChannelChangerListener){
        if(!mLists.contains(iChannelChangerListener)){
            mLists.add(iChannelChangerListener);
        }
    }
    public void removeChannerListener(IChannelChangerListener iChannelChangerListener){
        if(mLists.contains(iChannelChangerListener)){
            mLists.remove(iChannelChangerListener);
        }
    }

    public void notifys(List<ChannelBean> tab, boolean remove){
        for (IChannelChangerListener changer:mLists) {
            if(null != changer){
                changer.onChannelChangerListener(tab,remove);
            }
        }
    }

    public interface IChannelChangerListener{
        void onChannelChangerListener(List<ChannelBean> tab, boolean remove);
    }

}
