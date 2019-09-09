package com.zhangku.qukandian.utils;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by yuzuoning on 2017/8/24.
 */

public class LeftAndRightAnimation {

    private ObjectAnimator mMAnimation;
    private int mCount = 5;

    public void startAnimation(View view){
        mMAnimation = ObjectAnimator.ofFloat(view, "rotation", 0,-15f,15f);
        mMAnimation.setDuration(300);
        mMAnimation.setRepeatCount(mCount);
        mMAnimation.setRepeatMode(ObjectAnimator.REVERSE);
        mMAnimation.setInterpolator(new LinearInterpolator());
        mMAnimation.start();
    }

    public void setRepeatCount(int count){
        mCount = count;
    }

    public void stopAnimation(){
        if(null != mMAnimation){
            if(mMAnimation.isStarted()){
                mMAnimation.cancel();
            }
        }
    }
}
