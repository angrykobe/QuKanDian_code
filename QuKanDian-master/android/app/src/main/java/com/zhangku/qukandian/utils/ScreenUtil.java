package com.zhangku.qukandian.utils;

import android.content.Context;
import android.graphics.PointF;
import android.view.ViewConfiguration;

public class ScreenUtil {

	/**
	 * 获取Dialog的宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getDialogWidth(Context context) {
		int sw = context.getResources().getDisplayMetrics().widthPixels;
		return (int) (sw * 0.9);
	}
	/**
	 * 720标准下的 将px值转换为sp值，保证文字大小不变
	 *
	 * @param pxValue
	 * @param fontScale
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
//	public static int px2sp(Context context, float pxValue) {
//		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//		return (int) (pxValue / fontScale + 0.5f);
//	}
//
//	/**
//	 * 将sp值转换为px值，保证文字大小不变
//	 *
//	 * @param spValue
//	 * @param fontScale
//	 *            （DisplayMetrics类中属性scaledDensity）
//	 * @return
//	 */
//	public static int sp2px(Context context, float spValue) {
//		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//		return (int) (spValue * fontScale + 0.5f);
//	}
	/**
	 * 计算两点的距离
	 * 
	 * @param point1
	 * @param point2
	 * @return
	 */
	public static int distance(PointF point1, PointF point2) {
		int disX = (int) Math.abs(point1.x - point2.x);
		int disY = (int) Math.abs(point1.y - point2.y);
		return Math.abs((int) Math.sqrt(Math.pow(disX, 2) + Math.pow(disY, 2)));
	}

	/**
	 * 获取系统识别的最小滑动距离
	 * 
	 * @param context
	 * @return
	 */
	public static int getScaledTouchSlop(Context context) {
		return ViewConfiguration.get(context).getScaledTouchSlop();
	}
	
	public static int getPixelWith720(Context context, int pixel) {
		int w = context.getResources().getDisplayMetrics().widthPixels;
		int h = context.getResources().getDisplayMetrics().heightPixels;
		int screenW = w < h ? w : h;
		if(screenW==720)
			return pixel;
		return (int) (screenW * (pixel / 720f));
	}


	public static int getPixelWith1080(Context context, int pixel) {
		int w = context.getResources().getDisplayMetrics().widthPixels;
		int h = context.getResources().getDisplayMetrics().heightPixels;
		int screenW = w < h ? w : h;
		if(screenW==1080)
			return pixel;
		return (int) (screenW * (pixel / 1080f));
	}
	
}
