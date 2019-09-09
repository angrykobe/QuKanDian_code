package com.zhangku.qukandian.widght;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.utils.DisplayUtils;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/13
 * 你不注释一下？
 */
@SuppressLint("AppCompatCustomView")
public class ReadGoldProcessView extends ImageView {
    private Paint mPaint;
    private Bitmap bitmap;
    private int mHeight;
    private int mWidth;
    private int yinyingWidth = 8;//阴影宽度
    private int mBitmapWidth;
    private int mBitmapHeight;
    private float mLintWidth = 0.06f;//圈圈宽度
    private Shader mShader;//进度圈圈渐变
    private Rect mSrcRect;//除去阴影实际作图区域
    private Rect mDestRect;//中间红包作图区域
    private Paint mPaintShadow;//阴影画笔
    private Paint mPaintCircle;//进度条圈圈画笔，渐变
    private RectF mRectFCircle;//进度圈圈绘制区域
    private int mMaxProcess = 10000;//最大进度
    private int mCurProcess;//当前进度
    private boolean isDestory;//界面是否摧毁
    private int stayTime;//停留时间 根据需求 --》倒计时停止后用户在页面继续停留时间超过2分钟 弹提示
    private int readTime;//阅读时常，后台统计
    private boolean isStopRun = false;//暂停转圈圈  视频详情页使用
    private static final float mRounded = 0.05f;//转圈圈转动频率 0.0~1.0
    private static final int time = (int) (1000 * mRounded);

    private int COLOR_SHADOW = 0xbb999999;//阴影
    private int COLOR_CIRCLE_BIG = 0xFFFFFFFF;//大圈圈白色背景
    private int COLOR_CIRCLE_LITTLE = 0xffFFF7E9;//小圈圈背景

    private int COLOR_PROCESS_DEF = 0xfff4f4f4;//进度默认颜色
    private int COLOR_PROCESS_START = 0xFFFE8C5E;//进度渐变开始颜色
    private int COLOR_PROCESS_END = 0xFFF14619;//进度渐变结束颜色
//    private int mDisForShadow = DisplayUtils.dip2px(QuKanDianApplication.getmContext(),5);
    private int mDisForShadow = DisplayUtils.dip2px(QuKanDianApplication.getmContext(),5);

    public ReadGoldProcessView(@NonNull Context context) {
        this(context, null);
    }

    public ReadGoldProcessView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    boolean b;

    public ReadGoldProcessView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        isDestory = false;
        setWillNotDraw(false);
        mPaint = new Paint();
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//支持硬件  阴影
        mPaint.setAntiAlias(true);//防止锯齿
        mPaint.setFilterBitmap(true);//防止锯齿
//        mPaint.setDither(true);防抖动 图柔和点
        mPaint.setStyle(Paint.Style.FILL);//实心
        mPaint.setColor(COLOR_CIRCLE_LITTLE);
        TypedValue value = new TypedValue();
        getResources().openRawResource(R.drawable.read_gold_process_center, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.read_gold_process_center,opts);

        //阴影画笔
        mPaintShadow = new Paint();
        //绘制阴影，param1：模糊半径；param2：x轴大小：param3：y轴大小；param4：阴影颜色
        mPaintShadow.setShadowLayer(20F, yinyingWidth, yinyingWidth, COLOR_SHADOW);
        //最外围背景白色圈圈
        mPaintShadow.setColor(COLOR_CIRCLE_BIG);
        mPaintShadow.setAntiAlias(true);//防止锯齿
        mPaintShadow.setFilterBitmap(true);//防止锯齿

        //进度条圈圈画笔，渐变
        mPaintCircle = new Paint();
        mPaintCircle.setStrokeCap(Paint.Cap.ROUND);//线帽，圆形线冒
        mPaintCircle.setStyle(Paint.Style.STROKE);//
        mPaintCircle.setAntiAlias(true);//防止锯齿
        mPaintCircle.setFilterBitmap(true);//防止锯齿
        mPaintCircle.setColor(COLOR_PROCESS_DEF);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mHeight == 0 && getMeasuredWidth() != 0){
                mHeight = getMeasuredHeight() - yinyingWidth*3;
                mWidth = getMeasuredHeight() - yinyingWidth*3;
                mBitmapWidth = mWidth/2;
                mBitmapHeight = mHeight/2;

                //进度圈圈渐变
                mShader = new LinearGradient(0, 0, mHeight, mHeight, new int[] { COLOR_PROCESS_START, COLOR_PROCESS_END}, null, Shader.TileMode.REPEAT);
                mSrcRect = new Rect(0, 0, mHeight, mHeight);
                //中间红包作图区域
                mDestRect = new Rect(mWidth/4, mHeight/4, mWidth/4 + mBitmapWidth, mHeight/4 + mBitmapHeight);

                //进度圈圈左边距和上边距  一个画笔的宽度在加上自己画笔宽度一半
                float dis = mHeight * (mLintWidth) * 3 / 2 + mDisForShadow;
                //进度圈圈绘制区域
                mRectFCircle = new RectF(dis, dis, mHeight - dis, mHeight - dis);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //最外围白色阴影圈圈
        canvas.drawCircle(mHeight/2,mHeight/2,mHeight/2-mDisForShadow, mPaintShadow);

        //第二个实体圈圈
        canvas.drawCircle(mHeight/2,mHeight/2,mHeight/2*(1-mLintWidth*2)-mDisForShadow,mPaint);
        //绘制进度圈圈宽度
        mPaintCircle.setStrokeWidth(mHeight * mLintWidth);
        //绘制进度默认背景
        mPaintCircle.setShader(null);
        canvas.drawArc(mRectFCircle, -90, 360, false, mPaintCircle);
        //绘制进度 加渐变
        mPaintCircle.setShader(mShader);// 用Shader中定义定义的颜色来话
        canvas.drawArc(mRectFCircle, -90, 360 * mCurProcess / mMaxProcess, false, mPaintCircle);
        //绘制红包图片
        if(bitmap == null) return;
        canvas.drawBitmap(bitmap, mSrcRect, mDestRect, mPaint);
    }

    public int getmMaxProcess() {
        return mMaxProcess;
    }

    public void setmMaxProcess(int mMaxProcess) {
        if(mMaxProcess != 0)
            this.mMaxProcess = mMaxProcess * 1000;
    }

    public int getmCurProcess() {
        return mCurProcess;
    }

    public void setmCurProcess(int mCurProcess) {
        this.mCurProcess = mCurProcess;
        postInvalidate();
    }

    public void run(){
        run(4);
    }

    /**
     * @param post 转圈圈平分，比如传入4，表示圈圈每转到1/4的时候会回调
     */
    public void run(final int post){
        mCurProcess = 0;
        final int ceil = mMaxProcess / post;
        this.post(new Runnable() {
            @Override
            public void run() {
                if(isDestory) return;
                if(mCurProcess<=mMaxProcess){
                    readTime+=time;
                    if(isStopRun){
                        ReadGoldProcessView.this.postDelayed(this,time);
                    }else if(mCurProcess == mMaxProcess-1){//进度转到上一个立马回调  要不在转完会有一秒停顿
                        mCurProcess+=time;
                        listener.onTouchView(post);
                        ReadGoldProcessView.this.post(this);
                    }else if(mCurProcess%ceil == 0 && mCurProcess!=0){//每次转到1/4的位置停顿
                        if(listener.onTouchView(mCurProcess/ceil)){
                            mCurProcess+=time;
                            ReadGoldProcessView.this.postDelayed(this,time);
                        }else{
                            ReadGoldProcessView.this.postDelayed(this,time);
                        }
                    }else{
                        mCurProcess+=time;
                        ReadGoldProcessView.this.postDelayed(this,time);
                    }
                    postInvalidate();
                }else if(stayTime < 120* 1000){
                    stayTime +=time;
                    ReadGoldProcessView.this.postDelayed(this,time);
                }else{
                    if(listener != null) listener.stayTimeTooLong();
                    return;
                }
            }
        });
    }
    /**
     * 广告红包转圈圈逻辑
     */
    public void runForAdRed(){
        mCurProcess = 0;
        final int three = mMaxProcess * 3 / 5;  //(初体验和新用户)走到60%时，计时暂停，在计时器的右上角弹出气泡“请在当前页面中选择感兴趣任意内容，并点击阅读”，用户点击当前页面的任意链接后气泡框渐变消失，并继续触发计时
        final int four = mMaxProcess * 4 / 5; //(初体验用户)当进计时进度条走到第80%时，在计时器的右上角弹出气泡“即将获得金币奖励，请继续保持阅读”，2S后消失  2-5、计时结束后，弹出金币奖励
        this.post(new Runnable() {
            @Override
            public void run() {
                if(isDestory) return;
                if(mCurProcess<=mMaxProcess){
                    readTime+=time;
                    if(isStopRun){
                    }else if(mCurProcess%three == 0 && mCurProcess!=0){//转到3/5给回调 弹框用
                        if(listener.onTouchView(3)){
                            mCurProcess+= time;
                        }
                    }else if(mCurProcess%four == 0 && mCurProcess!=0){//转到4/5给回调 弹框用
                        if(listener.onTouchView(4)){
                            mCurProcess+= time;
                        }
                    }else if(mCurProcess >= mMaxProcess){//
                        mCurProcess+= time;
                        listener.onTouchView(5);
                    }else if(listener.onTouchView(-1)){//保持回调监听 需求（3S内未产生滑动行为，计时暂停）
                        mCurProcess+= time;
                    }
                    ReadGoldProcessView.this.postDelayed(this,time);
                    postInvalidate();
                }else if(stayTime < 120 * 1000){
                    stayTime +=time;
                    ReadGoldProcessView.this.postDelayed(this,time);
                }else{
                    if(listener != null) listener.stayTimeTooLong();
                    return;
                }
            }
        });
    }
    /**
     * 293 转圈圈
     */
    public void runForProcess(){
        mCurProcess = 0;
        this.post(new Runnable() {
            @Override
            public void run() {
                if(isDestory) return;
                if(mCurProcess<=mMaxProcess){
                    readTime+=time;
                    if(isStopRun){
                    } if(mCurProcess >= mMaxProcess){//
                        mCurProcess+= time;
                        listener.onTouchView(1);//转动结束
                    }else if(listener.onTouchView(-1)){//保持回调监听 需求（3S内未产生滑动行为，计时暂停）
                        mCurProcess+= time;
                    }
                    ReadGoldProcessView.this.postDelayed(this,time);
                    postInvalidate();
                }else{
                    return;
                }
            }
        });
    }

    public void setListener(OnTouchViewListen listener) {
        this.listener = listener;
    }

    private OnTouchViewListen listener;

    public interface OnTouchViewListen {
        /**
         * @param position 第几个1/4
         * @return
         */
        boolean onTouchView(int position);
        //页面停留时间过久
        void stayTimeTooLong();
    }

    public boolean isStopRun() {
        return isStopRun;
    }

    public void setStopRun(boolean stopRun) {
        isStopRun = stopRun;
    }

    public int getReadTime() {
        return readTime/1000;
    }

    public void setDestory(){
        if(bitmap!=null){
            bitmap.recycle();
            bitmap = null;
            System.gc();
        }
        isDestory = true;
    }

    public void setDestory(boolean destory) {
        isDestory = destory;
    }
}
