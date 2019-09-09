package com.zhangku.qukandian.utils.Img;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhihu.matisse.engine.ImageEngine;

public class GlideEngineFeedback implements ImageEngine {

    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(GlideUtils.getIconNormalOptions())
                .override(resize, resize)
                .centerCrop()
                .error(GlideUtils.getIconNormalOptions());
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(mOptions)
                .into(imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView,
                                 Uri uri) {
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(GlideUtils.getIconNormalOptions())
                .override(resize, resize)
                .centerCrop()
                .error(GlideUtils.getIconNormalOptions());
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(mOptions)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(GlideUtils.getIconNormalOptions())
                .fitCenter()
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .error(GlideUtils.getIconNormalOptions());
        Glide.with(context)
                .load(uri)
                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        RoundedCorners roundedCorners= new RoundedCorners(6);
        RequestOptions mOptions = RequestOptions.bitmapTransform(roundedCorners);
        mOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .error(GlideUtils.getIconNormalOptions());
        Glide.with(context)
                .asGif()
                .load(uri)
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }
}
