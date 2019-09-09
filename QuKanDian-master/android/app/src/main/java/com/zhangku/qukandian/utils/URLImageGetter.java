package com.zhangku.qukandian.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

/**
 * Created by yuzuon on 2016/7/20.
 */
public class URLImageGetter implements Html.ImageGetter{
    private TextView mTextView;
    private Context context;
    private int screenWidth  ;
    private int mMarginWith;//textview 间隔屏幕左边/右边的距离（dp）
    private float mMultiple = 1.0f;//图片所占宽度为屏幕的多少倍（0--1），默认0.8
    public URLImageGetter(Context context, TextView textView, int marginWidth) {
        this.context = context;
        mTextView    = textView;
        mMarginWith  = marginWidth;
    }
    public URLImageGetter(Context context, TextView textView, int marginWidth, float multiple) {
        this.context = context;
        mTextView    = textView;
        mMarginWith  = marginWidth;
        mMultiple    = multiple;
    }
    @Override
    public Drawable getDrawable(final String source) {
        final URLDrawable urlDrawable = new URLDrawable();
        GlideUtils.loadImage(context, source, new GlideUtils.OnLoadImageListener() {
            @Override
            public void onSucess(Bitmap bitmap, String url) {
                int  height =  bitmap.getHeight();
                int  width  =  bitmap.getWidth();
                if(null!=context){
                    screenWidth  = (context.getResources().getDisplayMetrics().widthPixels);
                    int showWidth = (int)(screenWidth*mMultiple);
                    int showHeight;
                    showHeight = ((height*showWidth)/width);
                    urlDrawable.bitmap = zoomImage(bitmap,showWidth,showHeight);
                    urlDrawable.left = (screenWidth-showWidth)/2-DisplayUtils.dip2px(context,mMarginWith);
                    urlDrawable.setBounds(0, 0, showWidth, showHeight);
                }
                if(mTextView!=null){
                    mTextView.setText(mTextView.getText()); // 解决图文重叠
                }
            }

            @Override
            public void onFail(Drawable errorDrawable) {

            }
        });
        return urlDrawable;
    }

    /***
     * 图片的缩放方法
     *
     * @param bm
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    public static Bitmap zoomImage(Bitmap bm, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bm.getWidth();
        float height = bm.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bm, 0, 0, (int) width,
                (int) height, matrix, true);
    }
}
