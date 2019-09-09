package com.zhangku.qukandian.widght;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zhangku.qukandian.R;

/**
 * 创建者          xuzhida
 * 创建日期        2018/6/13
 * 宝箱闪
 */
@SuppressLint("AppCompatCustomView")
public class BoxLightView extends ImageView {

    private int mWidth;
    private int mHeight;
    private float mMoveX;

    public BoxLightView(Context context) {
        super(context);
    }

    public BoxLightView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BoxLightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private Paint mPaint;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPaint = new Paint();
        mPaint.setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(false);// 去锯齿
        mPaint.setDither(false);// 防抖动
        mPaint.setFilterBitmap(false);// 图像过滤
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mWidth == 0 && getMeasuredWidth() != 0){
            mWidth = getMeasuredWidth();
            mHeight = getMeasuredHeight();
//            mWidth = bitmap.getWidth();
//            mHeight = bitmap.getHeight();
            startAnim(mWidth);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景图层(这里位置大小很重要，应该根据覆盖层来定义)(如果要实现SRC-OUT效果：那么左上点与覆盖层一样)
        canvas.saveLayer(0,0, mWidth, mWidth,  null,
                Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
                        | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                        | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                        | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        mPaint.setColor(ContextCompat.getColor(getContext(),R.color.main_red));
        canvas.drawCircle(mWidth/2,mHeight/2,mWidth/2,mPaint);
        super.onDraw(canvas);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mPaint.setColor(Color.WHITE);
//        BlurMaskFilter maskFilter = new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID);
//        mPaint.setMaskFilter(maskFilter);
        canvas.drawLine(0 + mMoveX, 0 ,  -mWidth + mMoveX, mHeight, mPaint);// 斜线
        mPaint.setXfermode(null);
    }

    private void startAnim(int move){
        ValueAnimator animator = ValueAnimator.ofFloat(0, 2*move);  //定义动画
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                mMoveX = (Float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(2000).start();
    }
}
