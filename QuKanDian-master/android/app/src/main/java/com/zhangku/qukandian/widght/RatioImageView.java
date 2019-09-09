package com.zhangku.qukandian.widght;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zhangku.qukandian.R;


public class RatioImageView extends AppCompatImageView {
	
	protected int mRatio_x = -1;
	protected int mRatio_y = -1;
	
	public RatioImageView(Context context) {
		super(context);
	}

	public RatioImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
		mRatio_x = typedArray.getInt(R.styleable.RatioImageView_RatioImageView_X, -1);
		mRatio_y = typedArray.getInt(R.styleable.RatioImageView_RatioImageView_Y, -1);
		typedArray.recycle();
	}

	public void setRatioXAndY(int x,int y){
		mRatio_x = x;
		mRatio_y = y;
		invalidate();
	}
	
	public void setImageBitmap(Bitmap bm, String url) {
		try {
			super.setImageBitmap(bm);
		} catch (Exception e) {
		}
	}
	
	@Override
	public void setImageDrawable(Drawable drawable) {
		try {
			super.setImageDrawable(drawable);
		} catch (Exception e) {
		}
	}
	
	@Override
	public void setImageURI(Uri uri) {
		try {
			super.setImageURI(uri);
		} catch (Exception e) {
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		if(mRatio_y > 0 && mRatio_x > 0){
			if (widthMode == MeasureSpec.EXACTLY) {
				heightMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize * mRatio_y / mRatio_x, MeasureSpec.EXACTLY);
			}else if (heightMode == MeasureSpec.EXACTLY) {
				widthMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize * mRatio_x / mRatio_y, MeasureSpec.EXACTLY);
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		try {
			super.onDraw(canvas);
		} catch (Exception e) {
		}
	}
}
