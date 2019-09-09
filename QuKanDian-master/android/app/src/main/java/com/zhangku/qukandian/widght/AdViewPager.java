package com.zhangku.qukandian.widght;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.bean.MyBannerBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.OperateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/9
 * 你不注释一下？
 */
public class AdViewPager extends ViewPager {

    private int mWidth;
    private int mHeight;
    private Context mContext;
    private int mIndicatorBottomMargin;//指示器底部距
    private int mIndicatorLeftMargin;//指示器左边距
    private int mIndicatorHeight;
    private int mIndicatorWidth;//指示器宽度
    private int mIndicatorCircleDis;//指示器点和点间的距离
    private int mIndicatorCircleRadius;//指示器点大小
    private int num = 4;//圆点个数
    private int from;
    private Paint mPaint;
    private int lastPost = 0;
    private MyImagPagerAdapter mAdapter;

    public AdViewPager(Context context) {
        this(context, null);
    }

    public AdViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mIndicatorBottomMargin = DisplayUtils.dip2px(mContext, 10);
        mIndicatorHeight = DisplayUtils.dip2px(mContext, 7);
        mIndicatorCircleRadius = DisplayUtils.dip2px(mContext, 4);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//防止锯齿
        mPaint.setFilterBitmap(true);//防止锯齿
//        mPaint.setDither(true);防抖动 图柔和点
        mPaint.setStyle(Paint.Style.FILL);//实心
        mPaint.setColor(Color.YELLOW);
        runPager();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw) {
            mWidth = w;
            mHeight = h;
            mIndicatorWidth = mWidth / 6;
            mIndicatorLeftMargin = mWidth * 5 / 12;
        }
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        super.addOnPageChangeListener(listener);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        if (lastPost != position) {
            lastPost = position;
        }
    }

    int mX = 0;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mX = l;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        for (int i = 0; i < num; i++) {
            if (lastPost == i) {
                mPaint.setColor(0xffff7421);
            } else {
                mPaint.setColor(Color.WHITE);
            }
            canvas.drawCircle(mIndicatorLeftMargin + mIndicatorCircleDis * i + mX, mHeight - mIndicatorBottomMargin, mIndicatorCircleRadius, mPaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    class MyImagPagerAdapter extends PagerAdapter {
        HashMap<Integer, ImageView> mHashMap = new HashMap<>();
        List<MyBannerBean.BannerConfigsBean> mList;

        public MyImagPagerAdapter(List<MyBannerBean.BannerConfigsBean> list) {
            this.mList = list;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            if (mHashMap.get(position) == null) {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                GlideUtils.displayImage(mContext, mList.get(position).getImageLink(), imageView);
                mHashMap.put(position, imageView);

                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (UserManager.getInst().hadLogin()) {
                            String url = mList.get(position).getGotoLink();

                            if (from == AnnoCon.FROM_WithdrawalsActivity) {
                                MobclickAgent.onEvent(getContext(), "04_07_03_tixianbanner");
                            } else if (from == AnnoCon.FROM_NewMeFragment3) {
                                Map<String, String> map = new ArrayMap<>();
                                map.put("click", "banner" + (position + 1));
                                MobclickAgent.onEvent(getContext(), "MyBanner", map);
                            }
                            if (url.contains("http")) {//跳转h5页面
                                if (url.contains("flag=chbyxj")) {
                                    ActivityUtils.startToChbyxjUrlActivity(getContext(), url);
                                } else {
                                    ActivityUtils.startToWebviewAct(getContext(), url);
                                }
                                if ((url).contains("shoutu")) {
                                    MobclickAgent.onEvent(getContext(), "295-qinyouyaoqingbanner");
                                }
                            } else if (url.startsWith("xcxα")) {
                                // 分享小程序
                                if (mContext instanceof android.app.Activity)
                                    OperateUtil.shareMiniAppMain(url, (android.app.Activity) mContext, null);
                            } else {//跳转app自己页面
                                if (url.contains("|")) {//预防返回出现|导致奔溃
                                    String[] urls = url.split("\\|");
                                    if (urls.length > 1) {
                                        ActivityUtils.startToAssignActivity(getContext(), urls[0], Integer.valueOf(urls[1]));
                                    } else {
                                        ActivityUtils.startToAssignActivity(getContext(), url, -1);
                                    }
                                } else {
                                    ActivityUtils.startToAssignActivity(getContext(), url, -1);
                                }
                            }
                        } else {
                            ActivityUtils.startToBeforeLogingActivity(getContext());
                        }
                    }
                });

            }
            container.addView(mHashMap.get(position));
            return mHashMap.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mHashMap.get(position));
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

    /**
     * @param list 广告容器
     * @param from 页面来源，埋点使用
     */
    public void initData(List<MyBannerBean.BannerConfigsBean> list, @AnnoCon.ActivityFrom int from) {
        num = list.size();
        this.from = from;
        int i = num - 1;
        i = i > 0 ? i : 1;
        mIndicatorCircleDis = mIndicatorWidth / i - 80;//点和点之间的距离（4个点有三个空格）
        mAdapter = new MyImagPagerAdapter(list);
        setAdapter(mAdapter);
    }

//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        isDestory = true;
//    }


    private boolean isDestory = false;
    private boolean isTouch = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void runPager() {
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isDestory) return;
                if (!isTouch)
                    AdViewPager.this.setCurrentItem((lastPost + 1) % num);
                AdViewPager.this.postDelayed(this, 3000);
            }
        }, 3000);
    }
}
