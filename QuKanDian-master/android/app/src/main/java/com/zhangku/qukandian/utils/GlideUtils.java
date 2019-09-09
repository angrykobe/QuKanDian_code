package com.zhangku.qukandian.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.util.Util;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.NativeAdInfo;
import com.zhangku.qukandian.biz.adcore.huitoutiao.HuitoutiaoZkNativeAd;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by yzn on 2016/11/8.
 * Glide图片加载
 */

public class GlideUtils {

    //显示本地文件图片
    public static void displayFileImage(Context context, String filePath, ImageView imageView) {
        if (null == context) {
            return;
        }
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.NONE);

        if (Util.isOnMainThread()){
            Glide.with(context)
                    .asBitmap()
                    .load(filePath)
                    .apply(mOptions)
                    .into(imageView);
        }

    }

    public static void displayImage(Context context, String url, ImageView imageView, int width, int height) {
        if (null == context) {
            return;
        }
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(getIconNormalOptions())
                .error(getIconNormalOptions())
                .override(width, height);
        if (Util.isOnMainThread()){
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(mOptions)
                    .into(imageView);
        }

    }

    public static void displayImage(Context context, String url, ImageView imageView) {
        if (context == null) return;
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(getIconNormalOptions())
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(mOptions)
                    .into(imageView);
        }

    }

    public static void preloadImage(Context context, String url, ImageView imageView) {
        if (context == null) return;
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(getIconNormalOptions())
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(context)
                    .load(url)
                    .apply(mOptions)
                    .into(imageView);
        }

    }

    public static void preload(Context context, String url) {
        if (context == null) return;
       //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(getIconNormalOptions())
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(context)
                    .load(url)
                    .apply(mOptions)
                    .preload();
        }

    }

    public static void displayImageForBg(Context context, String url, final ImageView imageView) {
        if (context == null) return;
       //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(getIconNormalOptions())
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(mOptions)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                            imageView.setBackground(new BitmapDrawable(resource));
                        }
                    });
        }

    }

    public static void displayImageForBg(Context context, String url, final View imageView) {
        if (context == null) return;
       //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(getIconNormalOptions())
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(mOptions)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                            imageView.setBackground(new BitmapDrawable(resource));
                        }
                    });
        }

    }

    public static void displayImage2(Context context, final String url, final ImageView imageView, final OnLoadImageListener onLoadImageListener) {
        if (context == null) return;
       //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(getIconNormalOptions())
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .apply(mOptions)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                            imageView.setImageBitmap(bitmap);
                            if (onLoadImageListener != null) {
                                onLoadImageListener.onSucess(bitmap, url);
                            } else {
                                if (bitmap != null) {
                                    bitmap.recycle();
                                }
                            }
                        }
                    });
        }

    }

    /**
     * @param context
     * @param imageUrl
     * @param imageView
     * @param view
     * @param been
     */
    public static void displayImageNice(Context context, String imageUrl, ImageView imageView, final View view, NativeAdInfo been) {
        if (context == null || been == null || TextUtils.isEmpty(imageUrl) || imageView == null)
            return;
        if (been.getOrigin() instanceof HuitoutiaoZkNativeAd) {
            final HuitoutiaoZkNativeAd tmpZkNativeAd = (HuitoutiaoZkNativeAd) been.getOrigin();
            GlideUtils.displayImage2(context, imageUrl, imageView, new GlideUtils.OnLoadImageListener() {
                @Override
                public void onSucess(Bitmap bitmap, String url) {
                    if (tmpZkNativeAd != null) {
                        tmpZkNativeAd.onAdShowed(view);
                    }
                }

                @Override
                public void onFail(Drawable errorDrawable) {

                }
            });
        } else {
            GlideUtils.displayImage(context, imageUrl, imageView);
        }
    }

    public static void displayImage(Context context, String url, ImageView imageView, int defaultRes) {
        if (null == context) {
            return;
        }
       //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(getIconNormalOptions())
                .fitCenter()
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(context)
                    .asBitmap()
                    .apply(mOptions)
                    .load(url)
                    .into(imageView);
        }

    }


    //圆形图片
    public static void displayCircleImage(Context context, String url, ImageView imageView, int width, int color, int defaultRes, boolean skipMemoryCache) {
        if (null == context) {
            return;
        }
        RequestOptions mOptions = RequestOptions.circleCropTransform();
        mOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(getIconNormalOptions())
                .transform(new GlideCircleTransform(context, width, color))
                .skipMemoryCache(skipMemoryCache)
                .fitCenter()
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(context)
                    .load(url)
                    .apply(mOptions)
                    .into(imageView);
        }

    }


    //圆角图片
    public static void displayRoundImage(Context context, String url, ImageView imageView, int round) {
        if (null == context) {
            return;
        }
       //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(getIconNormalOptions())
                .transform(new GlideRoundTransform(context, round))
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(context)
                    .load(url)
                    .apply(mOptions)
                    .into(imageView);
        }

    }




    public static void diaplayLocalGif(Context context, int url, ImageView imageView) {
        if (null == context) {
            return;
        }
       //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(getIconNormalOptions())
                .priority(Priority.IMMEDIATE)
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(context).asGif()
                    .load(url)
                    .apply(mOptions)
                    .into(imageView);
        }

    }

    public static void loadImage(Context activity, final String url, final OnLoadImageListener onLoadImageListener) {
        if (null == activity) {
            return;
        }
        if (activity instanceof Activity) {
            Activity activity2 = (Activity) activity;
            if (Build.VERSION.SDK_INT >= 17) {
                if (activity2.isDestroyed()) {
                    return;
                }
            } else {
                if (activity2.isFinishing()) {
                    return;
                }
            }
        }
        Glide.with(activity).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                if (onLoadImageListener != null) {
                    onLoadImageListener.onSucess(bitmap, url);
                }
            }

        });
    }

    public static void circleAvatar(Context activity, final String url, int width, int height, final OnLoadImageListener onLoadImageListener) {
        if (null == activity) {
            return;
        }
       //设置图片圆角角度
        RequestOptions mOptions = RequestOptions.circleCropTransform();
        mOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(getIconNormalOptions())
                .priority(Priority.IMMEDIATE)
                .skipMemoryCache(true)
                .override(width, height)
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(activity).asBitmap()
                    .load(url)
                    .apply(mOptions)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                            LogUtils.LogE("---------------------url2222==" + (url));
                            if (onLoadImageListener != null) {
                                onLoadImageListener.onSucess(bitmap, url);
                            }
                        }


                    });
        }

    }


    public static void loadImage(Context activity, final String url, int width, int height, final OnLoadImageListener onLoadImageListener) {
        if (null == activity) {
            return;
        }
        if (width <= 0) {
            width = 1;
        }
        if (height <= 0) {
            height = 1;
        }
       //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(getIconNormalOptions())
                .override(width, height)
                .error(getIconNormalOptions());
        if (Util.isOnMainThread()){
            Glide.with(activity).asBitmap()
                    .load(url)
                    .apply(mOptions)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                            if (onLoadImageListener != null) {
                                onLoadImageListener.onSucess(bitmap, url);
                            }
                        }

                    });
        }

    }

    public static int getIconNormalOptions() {
        return R.mipmap.img_load_placeholder;
    }

    public static int getErrorIconNormalOptions() {
        return R.mipmap.img_load_placeholder;
    }

    public static int getUserNormalOptions() {
        return R.mipmap.default_user_header_icon;
    }

    public interface OnLoadImageListener {
        void onSucess(Bitmap bitmap, String url);

        void onFail(Drawable errorDrawable);
    }

    /**
     * 清除图片磁盘缓存
     */
    public static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    public static void clearImageMemoryCache(Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(context).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片所有缓存
     */
    public static void clearImageAllCache(Context context, boolean bo) {
        if (null == context) {
            return;
        }
        clearImageDiskCache(context);
        clearImageMemoryCache(context);
        String ImageExternalCatchDir = context.getExternalCacheDir() + ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
        deleteFolderFile(ImageExternalCatchDir, true);
        if (bo) {
            if ("0M".equals(getCacheSize(context))) {
                ToastUtils.showShortToast(context, "清除缓存成功");
            } else {
                clearImageAllCache(context, true);
            }
        }
    }

    private static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public static String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/" + InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (size == 0) {
            return 0 + "M";
        }
        if (kiloByte < 1) {
            return size + "btye";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        return true;
    }
}
