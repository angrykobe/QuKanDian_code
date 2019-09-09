package com.zhangku.qukandian.base;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.lang.reflect.Method;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/17
 * 还没想好怎么规划，先完成需求吧
 */
public abstract class BasePop extends PopupWindow implements View.OnClickListener {

    protected View view;

    public BasePop(Context context,int resLayout){
        iniUI(context,resLayout);
    }

    public BasePop(Context context){
        iniUI(context,getResLayoutID());
    }

    private void iniUI(Context context, int resLayout) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(resLayout, null);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());

        //给下级传递事件
        Method method;
        try {
            method = PopupWindow.class.getDeclaredMethod("setTouchModal", boolean.class);
            method.setAccessible(true);
            method.invoke(this, false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        iniView(view);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public abstract int getResLayoutID();
    public abstract void onClick(View v);
    public abstract void iniView(View view);
}
