package com.zhangku.qukandian.observer;

import java.util.ArrayList;

/**
 * 菜单改变
 */
public class MenuChangeOberver {
    private MenuChangeOberver() {
    }

    private ArrayList<OnMenuChangeListener> mOnChangeStateListeners = new ArrayList<>();
    private static MenuChangeOberver mNewTudiOberver = null;

    public static MenuChangeOberver getInstance() {
        if (null == mNewTudiOberver) {
            synchronized (MenuChangeOberver.class) {
                if (null == mNewTudiOberver) {
                    mNewTudiOberver = new MenuChangeOberver();
                }
            }
        }
        return mNewTudiOberver;
    }

    public void addOnChangeStateListener(OnMenuChangeListener onChangeStateListener) {
        if (!mOnChangeStateListeners.contains(onChangeStateListener)) {
            mOnChangeStateListeners.add(onChangeStateListener);
        }
    }

    public void removeOnChangeStateListener(OnMenuChangeListener onChangeStateListener) {
        if (mOnChangeStateListeners.contains(onChangeStateListener)) {
            mOnChangeStateListeners.remove(onChangeStateListener);
        }
    }

    public void updateStateChange(){
        for (int i = 0; i < mOnChangeStateListeners.size(); i++) {
            if(null != mOnChangeStateListeners.get(i)){
                mOnChangeStateListeners.get(i).onMenuChangeListener();
            }
        }
    }

    public interface OnMenuChangeListener {
        void onMenuChangeListener();
    }
}
