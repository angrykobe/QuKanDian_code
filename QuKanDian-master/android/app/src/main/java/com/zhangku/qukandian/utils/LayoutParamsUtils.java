package com.zhangku.qukandian.utils;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhangku.qukandian.application.QuKanDianApplication;

/**
 * 创建者          xuzhida
 * 创建日期        2018/9/18
 * 你不注释一下？
 */
public class LayoutParamsUtils {

    public static void changeHeight(View view, int height) {
        ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) view.getLayoutParams();
        linearParams.height = height;
        view.setLayoutParams(linearParams);
    }

    public static void changeMargins(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams linearParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        linearParams.setMargins(left, top, right, bottom);
        view.setLayoutParams(linearParams);
    }

    //广告转圈圈
    public static void changeAdProcess(View view, int left, int top, int right, int bottom){
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        //此处相当于布局文件中的Android:layout_gravity属性  
        lp.gravity = Gravity.TOP;
        lp.setMargins(left, top, right, bottom);
        view.setLayoutParams(lp);
//        // 此处相当于布局文件中的Android：gravity属性  
//        view.setGravity(Gravity.CENTER);
    }
}
