package com.zhangku.qukandian.PopupWindow;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BasePop;

import java.lang.reflect.Method;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/20
 * 你不注释一下？
 */
public class FloatPopWindow extends BasePop {

    public FloatPopWindow(Context context) {
        super(context);
    }

    @Override
    public int getResLayoutID() {
        return R.layout.pop_imageview;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void iniView(View view) {
        // 点击外面的控件也可以使得PopUpWindow dimiss
        this.setOutsideTouchable(false);

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
    }
}
