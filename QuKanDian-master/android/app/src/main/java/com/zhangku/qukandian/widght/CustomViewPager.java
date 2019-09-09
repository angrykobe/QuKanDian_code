package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义(控制可滑动，不可滑动)
 *
 */
public class CustomViewPager extends ViewPager{
	
	private setOnViewPagerScrollAction mAction;

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if(mAction != null && mAction.unAbleScroll()){
			return false;
		}
		return super.onInterceptTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(mAction != null && mAction.unAbleScroll()){
			return false;
		}
		return super.onTouchEvent(event);
	}
	
	public void setOnViewPagerScrollAction(setOnViewPagerScrollAction action) {
		this.mAction = action;
	}
	
	public interface setOnViewPagerScrollAction{
		boolean unAbleScroll();
	}
}
