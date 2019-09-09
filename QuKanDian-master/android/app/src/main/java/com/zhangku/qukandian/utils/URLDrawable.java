package com.zhangku.qukandian.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by yuzuoning on 2016/7/20.
 */
public class URLDrawable extends BitmapDrawable {

	protected Bitmap bitmap;
	protected int left;

	@Override
	public void draw(Canvas canvas) {
		if (bitmap != null) {
			canvas.drawBitmap(bitmap, left, 0, getPaint());
		}
	}

}
