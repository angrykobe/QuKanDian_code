package com.zhangku.qukandian.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/4
 * 动画
 */
public class AnimUtil {
    private static boolean isPlayAnim = false;

    //为您推荐头部提醒动画
    public static void animHeadView(Context context, final View view) {
        if (isPlayAnim) return;
        view.setVisibility(View.VISIBLE);
        final int height = DisplayUtils.dip2px(context, 37);//高度

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
        ValueAnimator animator = ValueAnimator.ofFloat(height, 0);  //定义动画
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                try {
                    float height = (Float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams lp = view.getLayoutParams();
                    lp.height = Integer.valueOf((int) height);
                    view.setLayoutParams(lp);
                } catch (Exception e) {
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isPlayAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isPlayAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isPlayAnim = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.setStartDelay(800);
        animator.setDuration(700);
        animator.start();
    }

    ///点赞上漂 渐变动画
    public static void animPraise(View view) {
        //取消点赞
        view.setVisibility(View.VISIBLE);
        int height = DisplayUtils.dip2px(view.getContext(), 20);//高度
        //组合动画方式2
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("translationY", 0.0f, -height);
        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, valuesHolder, valuesHolder1);
        objectAnimator.setDuration(1000).start();
    }
    //旋转动画
    public static void animRotation(final View view) {
        PropertyValuesHolder valuesHolder5 = PropertyValuesHolder.ofFloat("rotation", 0, 1080);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, valuesHolder5);

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        objectAnimator.setDuration(2000).start();
    }
    //放大动效
    public static void animBig(View view) {
        //取消点赞
        //组合动画方式2
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f,1f);
        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.2f,1f);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, valuesHolder, valuesHolder1);
        objectAnimator.setDuration(1000).start();
    }
    //放大动效
    public static void animBigMore(View view) {
        //取消点赞
        //组合动画方式2
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f,1f);
        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.2f,1f);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, valuesHolder, valuesHolder1);
        objectAnimator.setRepeatCount(5);
        objectAnimator.setDuration(1000).start();
    }

    //渐变消失透明动画
    public static void animAlpha(final View view) {
        //组合动画方式2
        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 1.0f, 0f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, valuesHolder1);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.setDuration(3000).start();
    }

//    /**
//     * 旋转动画  0-180
//     * @param time  时间  mm
//     */
//    public static void animRotateOpen(View view,int time) {
//        Animation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(time);
//        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
//        view.startAnimation(animation);//開始动画
//    }
//
//    /**
//     * 旋转动画  180 - 0
//     * @param time  时间  mm
//     */
//    public static void animRotateClose(View view,int time) {
//        Animation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(time);
//        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
//        view.startAnimation(animation);//開始动画
//    }
//
//    /**
//     * 布局显示
//     * @param view
//     */
//    public static void animTaskShow(final View view,int time) {
//        view.setVisibility(View.VISIBLE);
//        ValueAnimator animator;
//        animator = ValueAnimator.ofFloat(0, (int) view.getTag());  //定义动画
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                try {
//                    float height = (Float) animation.getAnimatedValue();
//                    ViewGroup.LayoutParams lp = view.getLayoutParams();
//                    lp.height = Integer.valueOf((int) height);
//                    view.setLayoutParams(lp);
//                    view.setRotation(height);
//                } catch (Exception e) {
//                }
//            }
//        });
//        animator.setDuration(time);
//        animator.start();
//    }
//
//    /**
//     * 布局隐藏消失
//     * @param view
//     */
//    public static void animTaskClose(final View view,int time) {
//        ValueAnimator animator;
//        animator = ValueAnimator.ofFloat((int) view.getTag(), 0);  //定义动画
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                try {
//                    float height = (Float) animation.getAnimatedValue();
//                    ViewGroup.LayoutParams lp = view.getLayoutParams();
//                    lp.height = Integer.valueOf((int) height);
//                    view.setLayoutParams(lp);
//                    view.setRotation(height);
//                    if (height == 0) {
//                        view.setVisibility(View.GONE);
//                    }
//                } catch (Exception e) {
//                }
//            }
//        });
//        animator.setDuration(time);
//        animator.start();
//    }

    /**
     * @param goneView   布局gone的view
     * @param rotationView   旋转的view
     * @param time
     */
    public static void animTaskClose(final View goneView,final View rotationView,int time,final int height) {
        ValueAnimator animator;
        animator = ValueAnimator.ofFloat(100, 0);  //定义动画
        rotationView.setClickable(false);
        goneView.setVisibility(View.GONE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                try {
                    Float animatedValue = (Float) animation.getAnimatedValue() /100;
                    if (animatedValue == 0) {
                        rotationView.setClickable(true);
                    }

//                    int height = (int) goneView.getTag();
                    int rotation = 180;
                    float nowHeigh = animatedValue * height;
                    float nowRotation = animatedValue * rotation;

//                    ViewGroup.LayoutParams lp = goneView.getLayoutParams();
//                    lp.height = Integer.valueOf((int) nowHeigh);
//                    goneView.setLayoutParams(lp);
                    rotationView.setRotation(nowRotation);
                } catch (Exception e) {
                }
            }
        });
        animator.setDuration(time);
        animator.start();
    }

    /**
     * @param showView   布局gone的view
     * @param rotationView   旋转的view
     * @param time
     */
    public static void animTaskOpen(final View showView,final View rotationView,int time,final int height) {
        ValueAnimator animator;
        animator = ValueAnimator.ofFloat(0, 100);  //定义动画
        showView.setVisibility(View.VISIBLE);
        rotationView.setClickable(false);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                try {
                    Float animatedValue = (Float) animation.getAnimatedValue()/100;

//                    int height = (int) showView.getTag();
                    int rotation = 180;
                    float nowHeigh = animatedValue * height;
                    float nowRotation = animatedValue * rotation;
                    if(animatedValue == 1){
                        rotationView.setClickable(true);
                    }

//                    ViewGroup.LayoutParams lp = showView.getLayoutParams();
//                    lp.height = Integer.valueOf((int) nowHeigh);
//                    showView.setLayoutParams(lp);
                    rotationView.setRotation(nowRotation);
                } catch (Exception e) {
                }
            }
        });
        animator.setDuration(time);
        animator.start();
    }

}
