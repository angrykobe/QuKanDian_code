package com.zhangku.qukandian.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;

/**
 * Created by yuzuoning on 2017/4/13.
 */

public class CustomToast {

    public static void showToast(Context context, String gold,String remind) {
        if (context == null) return;
        Context appContext = context.getApplicationContext();
        AdsRecordUtils.getInstance().putInt(Constants.ADS_CLICK_START,1);
        View toastRoot = LayoutInflater.from(appContext).inflate(R.layout.toast_layout, null);
        TextView tvGold = (TextView) toastRoot.findViewById(R.id.toast_gold);
        TextView tvRemind = (TextView) toastRoot.findViewById(R.id.toast_remind);
        //为控件设置属性
        tvGold.setText(gold.trim());
        tvRemind.setText(remind.trim());
        //Toast的初始化
        Toast toastStart = new Toast(appContext);
        //获取屏幕高度
        WindowManager wm = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
        toastStart.setGravity(Gravity.TOP, 0, height / 3);
        toastStart.setDuration(Toast.LENGTH_LONG);
        toastStart.setView(toastRoot);
        toastStart.show();
    }
    public static void showToastOther(Context context, String gold,String remind) {
        if (context == null) return;
        Context appContext = context.getApplicationContext();
        AdsRecordUtils.getInstance().putInt(Constants.ADS_CLICK_START,1);
        View toastRoot = LayoutInflater.from(appContext).inflate(R.layout.toast_layout, null);
        TextView tvGold = (TextView) toastRoot.findViewById(R.id.toast_gold);
        TextView tvRemind = (TextView) toastRoot.findViewById(R.id.toast_remind);
        //为控件设置属性
        tvGold.setText(gold);
        tvRemind.setText(remind);
        //Toast的初始化
        Toast toastStart = new Toast(appContext);
        //获取屏幕高度
        WindowManager wm = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        //Toast的Y坐标是屏幕高度的1/3，不会出现不适配的问题
        toastStart.setGravity(Gravity.TOP, 0, height / 3);
        toastStart.setDuration(Toast.LENGTH_LONG);
        toastStart.setView(toastRoot);
        toastStart.show();
    }

    public static void showLoadRemindToast(Context context, ViewGroup view, String remind){
        if (context == null) return;
        Context appContext = context.getApplicationContext();
        View toastRoot = LayoutInflater.from(appContext).inflate(R.layout.dialog_load_remind_view, view,false);
        TextView textView = (TextView) toastRoot.findViewById(R.id.dialog_load_remind_text);
        if(!TextUtils.isEmpty(remind)){
            textView.setText(remind);
        }

        ViewGroup.LayoutParams params = toastRoot.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        toastRoot.setLayoutParams(params);

        Toast toastStart = new Toast(appContext);
        toastStart.setGravity(Gravity.TOP, 0, DisplayUtils.dip2px(appContext,96));
        toastStart.setDuration(Toast.LENGTH_LONG);
        toastStart.setView(toastRoot);
        toastStart.show();
    }
}
