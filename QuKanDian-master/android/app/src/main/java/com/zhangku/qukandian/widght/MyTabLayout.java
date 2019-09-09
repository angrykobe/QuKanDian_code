package com.zhangku.qukandian.widght;

import android.content.Context;
import android.util.AttributeSet;

import com.androidkun.xtablayout.XTabLayout;

/**
 * Created by yuzuoning on 2017/12/16.
 */

public class MyTabLayout extends XTabLayout {
    private OnScrollChangeListener mOnScrollChangeListener;
    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(null != mOnScrollChangeListener){
            mOnScrollChangeListener.onScrollChange(l,t,oldl,oldt);
        }
    }

    public void setOnScrollChangeListeners(OnScrollChangeListener onScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener;
    }

    public interface OnScrollChangeListener {
        void onScrollChange(int i, int i1, int i2, int i3);
    }
}
