package com.zhangku.qukandian.utils.Img;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.SaveFile;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mine on 2015/9/24.
 */
public class ImagPagerUtil {
    private List<String> mPicList;
    private Context mActivity;
    private Dialog dialog;
    private LazyViewPager mViewPager;
    private LinearLayout mLL_progress;
    private TextView tv_loadingmsg;
    private TextView tv_img_current_index;
    private TextView tv_img_count;
    private TextView mTvSave;
    private int mCurrentPosition = 0;

    public ImagPagerUtil(Context activity, List<String> mPicList) {
        this.mPicList = mPicList;
        this.mActivity = activity;
        init(activity);
    }

    public ImagPagerUtil(Context activity, String[] picarr) {
        if (null == picarr) return;
        mPicList = new ArrayList<>();
        for (int i = 0; i < picarr.length; i++) {
            mPicList.add(picarr[i]);
        }
        this.mActivity = activity;
        init(activity);
    }


    public void show() {
        dialog.show();
    }

    private void init(final Context context) {
        dialog = new Dialog(mActivity, R.style.fullDialog);
        RelativeLayout contentView = (RelativeLayout) View.inflate(mActivity, R.layout.view_dialogpager_img, null);
        mTvSave = getView(contentView, R.id.save_pic);
        mViewPager = getView(contentView, R.id.view_pager);
        mLL_progress = getView(contentView, R.id.vdi_ll_progress);
        tv_loadingmsg = getView(contentView, R.id.tv_loadingmsg);
        tv_img_current_index = getView(contentView, R.id.tv_img_current_index);
        tv_img_count = getView(contentView, R.id.tv_img_count);
        dialog.setContentView(contentView);

        tv_img_count.setText(mPicList.size() + "");
        tv_img_current_index.setText("1");

        int size = mPicList.size();
        ArrayList<ZoomImageView> imageViews = new ArrayList<>();
        ZoomImageView imageView = new ZoomImageView(mActivity);
        imageView.measure(0, 0);
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        imageView.setLayoutParams(marginLayoutParams);
        imageView.setOnClickListener(new View.OnClickListener() {//如果不需要点击图片关闭的需求，可以去掉这个点击事件
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        for (int i = 0; i < size; i++) {
            imageViews.add(imageView);
        }
        initViewPager(imageViews);
        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1);
                    } else {
                        saveImg(context);
                    }
                } else {
                    saveImg(context);
                }
            }
        });
    }

    private void saveImg(Context context) {
        String url = mPicList.get(mCurrentPosition);
        SaveFile helper = new SaveFile(context);
        helper.savePicture(url.replace("http://cdn.qu.fi.pqmnz.com/test/img/", "").replace("/", ""), url);
    }

    private void initViewPager(ArrayList<ZoomImageView> list) {
        mViewPager.setOnPageChangeListener(new LazyViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                tv_img_current_index.setText("" + (position + 1));

            }
        });

        MyImagPagerAdapter myImagPagerAdapter = new MyImagPagerAdapter(list);
        mViewPager.setAdapter(myImagPagerAdapter);
    }

    public void setPosition(String url) {
        if (mPicList == null) return;
        mViewPager.setCurrentItem(mPicList.indexOf(url.replace("?x-oss-process=style/gif_lazy", "")));
    }

    class MyImagPagerAdapter extends PagerAdapter {
        ArrayList<ZoomImageView> mList;

        public MyImagPagerAdapter(ArrayList<ZoomImageView> mList) {
            this.mList = mList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ZoomImageView imageView = mList.get(position);
            showPic(imageView, mPicList.get(position));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

        @Override
        public int getCount() {
            if (null == mList || mList.size() <= 0) {
                return 0;
            }
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    private void showPic(final ZoomImageView imageView, String url) {
        imageView.setImageBitmap(null);
        mLL_progress.setVisibility(View.VISIBLE);
        if (url.contains(".gif")) {
            Glide.with(mActivity).asGif().load(url).into(new SimpleTarget<GifDrawable>() {
                @Override
                public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                    Glide.with(mActivity).asGif().load(resource).into(imageView);
                    mLL_progress.setVisibility(View.GONE);
                }
            });
        } else {
            GlideUtils.loadImage(mActivity, url, new GlideUtils.OnLoadImageListener() {
                @Override
                public void onSucess(Bitmap bitmap, String url) {
                    imageView.setImageBitmap(bitmap);
                    mLL_progress.setVisibility(View.GONE);
                }

                @Override
                public void onFail(Drawable errorDrawable) {

                }
            });
        }
        dialog.show();
    }


    @SuppressWarnings("unchecked")
    public static final <E extends View> E getView(View parent, int id) {
        try {
            return (E) parent.findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }


}
