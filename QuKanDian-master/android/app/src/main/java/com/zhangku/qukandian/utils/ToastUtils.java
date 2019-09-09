package com.zhangku.qukandian.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.zhangku.qukandian.application.QuKanDianApplication;

public class ToastUtils {

	private static Toast textToast;
	private static String toastText;
	private static long textShowTime;

	public static synchronized void showShortToast(Context context, int resId) {
		showToast(context, context.getString(resId), Toast.LENGTH_SHORT);
	}

	public static synchronized void showShortToast(Context context, String text) {
		showToast(context, text, Toast.LENGTH_SHORT);
	}
	
	public static synchronized void showLongToast(Context context, int resId) {
		showToast(context, context.getString(resId), Toast.LENGTH_LONG);
	}
	
	
	public static synchronized void showLongToast(Context context, String text) {
		showToast(context, text, Toast.LENGTH_LONG);
	}

	public synchronized static void showToast(final Context context, final String text, final int duration) {
		if(TextUtils.isEmpty(text)) return;

		if (context == null || !(context instanceof Activity)) return;
		if(context instanceof Activity){
			Activity activity = (Activity) context;
			activity.runOnUiThread(new Runnable()
			{
				public void run()
				{
					if (textToast != null) {
						if (toastText.equals(text)) {
							long interval = System.currentTimeMillis() - textShowTime;
							if (interval < 1000 * 2) {
								return;
							}
						}
						textToast.setText(text);
					} else {
						textToast = Toast.makeText(QuKanDianApplication.getmContext(), text, duration);
					}
					toastText = text;
					textToast.show();
					textShowTime = System.currentTimeMillis();
				}

			});
		}else{
			if (textToast != null) {
				if (toastText.equals(text)) {
					long interval = System.currentTimeMillis() - textShowTime;
					if (interval < 1000 * 2) {
						return;
					}
				}
				textToast.setText(text);
			} else {
				textToast = Toast.makeText(QuKanDianApplication.getmContext(), text, duration);
			}
			toastText = text;
			textToast.show();
			textShowTime = System.currentTimeMillis();

		}
	}

	public static void showTostCentent(Context context, String text){
		if(TextUtils.isEmpty(text))return;
		Toast toast = Toast.makeText(context,text,Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER,0,0);
		toast.show();
	}

	public static void showTostCententShort(Context context, String text){
		if(TextUtils.isEmpty(text))return;
		Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER,0,0);
		toast.show();
	}

	public static void showTostShortCentent(Context context, String text){
		if(TextUtils.isEmpty(text))return;
		Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER,0,0);
		toast.show();
	}

//	public static Toast mToast;
//
//	public static void show(String msg) {
//		show(QuKanDianApplication.getmContext(), msg);
//	}
//	public static void show(int resId) {
//		if (mToast == null) {
//			mToast = Toast.makeText(QavsdkApplication.getContext(), resId, Toast.LENGTH_SHORT);
//		} else {
//			mToast.setText(resId);
//		}
//		mToast.show();
//	}
//	public static void show(Context context, String msg) {
//		if (mToast == null) {
//			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
//		} else {
//			mToast.setText(msg);
//		}
//		mToast.show();
//	}
}
