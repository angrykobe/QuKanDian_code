package com.zhangku.qukandian.dialog;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;

/**
 * Created by yuzuoning on 2017/4/27.
 */

public class DialogPrograss extends BaseDialog {
    private ImageView mImageView;
    private ObjectAnimator mAnimation;

    public DialogPrograss(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_prograss_view;
    }

    @Override
    protected void initView() {
        mImageView = (ImageView) findViewById(R.id.progress);

        mAnimation = ObjectAnimator.ofFloat(mImageView, "rotation", 0.0f, 360.0f);
        mAnimation.setDuration(1500);
        mAnimation.setRepeatCount(ObjectAnimator.INFINITE);
        mAnimation.setRepeatMode(ObjectAnimator.RESTART);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.start();
    }

    @Override
    protected void release() {
        mAnimation.end();
    }


    @Override
    protected void setPosition() {

    }
}
