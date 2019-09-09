package com.zhangku.qukandian.observer;

import com.zhangku.qukandian.activitys.LauncherActivity;
import com.zhangku.qukandian.bean.QkdPushBean;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/8/23.
 */

public class PushOberver {
    private PushOberver() {
    }

    private ArrayList<OnPushListener> mOnPushListeners = new ArrayList<>();
    private ArrayList<OnPushOperateListener> mPushOperateListeners = new ArrayList<>();
    private static PushOberver mPushOberver = null;

    public static PushOberver getInstance() {
        if (null == mPushOberver) {
            synchronized (PushOberver.class) {
                if (null == mPushOberver) {
                    mPushOberver = new PushOberver();
                }
            }
        }
        return mPushOberver;
    }

    public void addOnPushListener(OnPushListener onPushListener) {
        if (!mOnPushListeners.contains(onPushListener)) {
            if(onPushListener instanceof LauncherActivity){
                return;
            }
            mOnPushListeners.add(onPushListener);
        }
    }

    public void removePushalistener(OnPushListener onPushListener) {
        if (mOnPushListeners.contains(onPushListener)) {
            mOnPushListeners.remove(onPushListener);
        }
    }

    public void showPush(int postId, String title, int type) {
        OnPushListener o = mOnPushListeners.get(mOnPushListeners.size()-1);
        if (null != o) {
            o.onPushListener(postId, title, type);
            mOnPushListeners.clear();
        }
    }

    public void addPushOperateListener(OnPushOperateListener onPushOperateListener) {
        if (!mPushOperateListeners.contains(onPushOperateListener)) {
            mPushOperateListeners.add(onPushOperateListener);
        }
    }

    public void removePushOperateListener(OnPushOperateListener onPushOperateListener) {
        if (mPushOperateListeners.contains(onPushOperateListener)) {
            mPushOperateListeners.remove(onPushOperateListener);
        }
    }

    public void notifyUp(QkdPushBean qkdPushBean){
        for (OnPushOperateListener o: mPushOperateListeners) {
            if(null != o){
                o.onPushOperateListener(qkdPushBean);
            }
        }
    }

    public interface OnPushListener {
        void onPushListener(int postId, String title, int type);
    }

    public interface OnPushOperateListener {
        void onPushOperateListener(QkdPushBean qkdPushBean);
    }
}
