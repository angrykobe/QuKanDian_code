package com.zhangku.qukandian.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DisplayUtils {
	/**
	 * 获取屏幕宽度的像素
	 *
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}
	/**
	 * dp转换成px
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	/**
	 * sp转换成px
	 */
	public static float sp2px(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return pxValue * scale;
	}
	/**
	 * px转换成dp
	 */
	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 *
	 * @param context ScreenUtil
	 * @param tv     TextView 控件
	 * @param size   px为单位的值
     */
	public static void texSizeSet(Context context,TextView tv,int size){
		//设计图基于720的分辨率
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,ScreenUtil.getPixelWith720(context,size));
	}

	/**  不需设置Margin 且外围布局为RelativeLayout的
	 *   RelativeLayout.LayoutParams
	 * @param mContext
	 * @param view    控件
	 * @param width   宽
     * @param height  高
     */
	public static void setRlLayoutParams(Context mContext, View view,int width,int height){
		RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) view.getLayoutParams();
		linearParams.width = ScreenUtil.getPixelWith720(mContext,width);
		linearParams.height = ScreenUtil.getPixelWith720(mContext,height);
		view.setLayoutParams(linearParams);
	}

	/**  需要设置Margin的
	 *   RelativeLayout.LayoutParams
	 * @param mContext
	 * @param view    控件
	 * @param width   宽
	 * @param height  高
	 * @param left    Margins  left
	 * @param top    Margins  top
     * @param right  Margins  right
     * @param bottom Margins  bottom
     */
	public static void setRlLayoutParams(Context mContext, View view,int width,int height,int left,int top,int right,int bottom){

		RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) view.getLayoutParams();
		linearParams.width = ScreenUtil.getPixelWith720(mContext,width);
		linearParams.height = ScreenUtil.getPixelWith720(mContext,height);
		int leftNum = ScreenUtil.getPixelWith720(mContext,left);
		int topNum  = ScreenUtil.getPixelWith720(mContext,top);
		int rightNum = ScreenUtil.getPixelWith720(mContext,right);
		int bottomNum  = ScreenUtil.getPixelWith720(mContext,bottom);
		linearParams.setMargins(leftNum,topNum,rightNum,bottomNum);
		view.setLayoutParams(linearParams);
	}

	/**  不需设置Margin 且外围布局为LinearLayout的
	 *   LinearLayout.LayoutParams
	 * @param mContext
	 * @param view    控件
	 * @param width   宽
	 * @param height  高
	 */
	public static void setLlLayoutParams(Context mContext, View view,int width,int height){

		LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) view.getLayoutParams();
		linearParams.width = ScreenUtil.getPixelWith720(mContext,34);
		linearParams.height = ScreenUtil.getPixelWith720(mContext,34);
		view.setLayoutParams(linearParams);

	}
	/**
	 *   LinearLayout.LayoutParams
	 * @param mContext
	 * @param view    控件
	 * @param width   宽
	 * @param height  高
	 * @param left    Margins  left
	 * @param top    Margins  top
	 * @param right  Margins  right
	 * @param bottom Margins  bottom
	 */
	public static void setLlLayoutParams(Context mContext, View view,int width,int height,int left,int top,int right,int bottom){
		LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) view.getLayoutParams();
		linearParams.width = ScreenUtil.getPixelWith720(mContext,width);
		linearParams.height = ScreenUtil.getPixelWith720(mContext,height);
		int leftNum = ScreenUtil.getPixelWith720(mContext,left);
		int topNum  = ScreenUtil.getPixelWith720(mContext,top);
		int rightNum = ScreenUtil.getPixelWith720(mContext,right);
		int bottomNum  = ScreenUtil.getPixelWith720(mContext,bottom);
		linearParams.setMargins(leftNum,topNum,rightNum,bottomNum);
		view.setLayoutParams(linearParams);
	}

	/**RelativeLayout.LayoutParams
	 * 根据图片宽高比例设置控件大小
	 * @param view
	 * @param scale
     */
	public static void setRlViewByScale(final View view, final double scale){
		ViewTreeObserver vto2 = view.getViewTreeObserver();

		vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int width = view.getWidth();
				RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) view.getLayoutParams();

				linearParams.width = width;
				linearParams.height = (int)(width /scale);

				view.setLayoutParams(linearParams);
			}
		});
	}
	/**LinearLayout.LayoutParams
	 * 根据图片宽高比例设置控件大小
	 * @param view
	 * @param scale
	 */
	public static void setLlViewByScale(final View view, final double scale){
		ViewTreeObserver vto2 = view.getViewTreeObserver();

		vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int width = view.getWidth();
				LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) view.getLayoutParams();

				linearParams.width = width;
				linearParams.height = (int)(width /scale);

				view.setLayoutParams(linearParams);
			}
		});
	}

	/**FrameLayout.LayoutParams
	 * 根据图片宽高比例设置控件大小
	 * @param view
	 * @param scale
	 */
	public static void setFlViewByScale(final View view, final double scale){
		ViewTreeObserver vto2 = view.getViewTreeObserver();

		vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				int width = view.getWidth();
				FrameLayout.LayoutParams linearParams =(FrameLayout.LayoutParams) view.getLayoutParams();

				linearParams.width = width;
				linearParams.height = (int)(width /scale);

				view.setLayoutParams(linearParams);
			}
		});
	}
}
