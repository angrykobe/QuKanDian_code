package com.zhangku.qukandian.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.PushBean;
import com.zhangku.qukandian.dialog.PushDialog;
import com.zhangku.qukandian.observer.ExitActivityObserver;
import com.zhangku.qukandian.observer.PushOberver;
import com.zhangku.qukandian.utils.HuaweiNavigationBar;
import com.zhangku.qukandian.widght.ActionBarLayout;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import static com.zhangku.qukandian.R.id.layout_title_layout;

public abstract class BaseFragmentActivity extends FragmentActivity implements PushOberver.OnPushListener, ViewTreeObserver.OnGlobalLayoutListener {
    protected ActionBarLayout mActionBarLayout;
    protected FragmentManager mFragmentManager;
    protected MyHandler mHandler;
    protected PushDialog mPushDialog;
    protected FrameLayout mFrameLayout;
    protected View mView;

    public void onCreate(Bundle savedInstanceState) {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        initConfig();
        mFragmentManager = getSupportFragmentManager();
        mPushDialog = new PushDialog(this);
        addFragments();
        setContentView(R.layout.activity_base_layout);
        mFrameLayout = (FrameLayout) findViewById(R.id.base_layout);
        mActionBarLayout = (ActionBarLayout)findViewById(layout_title_layout);

        mHandler = new MyHandler(this);
        int resId = getLayoutRes();
        if (resId > 0) {
            mFrameLayout.addView(LayoutInflater.from(this).inflate(resId, mFrameLayout, false));
        }
        mView = findViewById(R.id.base_layout_placeholder);

        attachView();
        initViews();
        loadData();
        mFrameLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
        PushOberver.getInstance().addOnPushListener(this);
    }
    protected void attachView(){}
    public void addFragments() {
    }

    public void initViews() {
    }

    public void loadData() {
    }

    public void initConfig() {
    }

    public abstract int getLayoutRes();

    @Override
    public void onPushListener(int postId, String title, int type) {
        Message message = mHandler.obtainMessage();
        message.obj = new PushBean(postId, title, type);
        mHandler.sendMessage(message);
    }

    @Override
    public void onGlobalLayout() {
        ViewGroup.LayoutParams layoutParams = mView.getLayoutParams();
        layoutParams.height = HuaweiNavigationBar.getNavigationBarHeight(this);
        mView.setLayoutParams(layoutParams);
    }


    protected static class MyHandler extends Handler {

        private WeakReference<BaseFragmentActivity> wr;

        public MyHandler(BaseFragmentActivity activity) {
            wr = new WeakReference<BaseFragmentActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (wr != null && wr.get() != null) {
                wr.get().handleMessage(msg);
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        String pName = setPageName();
        if (!TextUtils.isEmpty(pName)) {
            MobclickAgent.onPageStart(pName);
        }
        MobclickAgent.onResume(this);
        Glide.with(this).resumeRequests();
    }

    public void handleMessage(Message msg) {
        if (!mPushDialog.isShowing()) {
            mPushDialog.show();
            PushBean bean = (PushBean) msg.obj;
            mPushDialog.setContent(bean.getPostId(), bean.getTitle(), bean.getType());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String pName = setPageName();
        if (!TextUtils.isEmpty(pName)) {
            MobclickAgent.onPageEnd(pName);
        }
        MobclickAgent.onPause(this);
        Glide.with(this).pauseRequests();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Glide.with(this).onStop();
    }

    public abstract String setPageName();

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PushOberver.getInstance().removePushalistener(this);
        ExitActivityObserver.getInst().onActivityDestory(this);
        mFrameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        mFragmentManager = null;
        releaseRes();
    }

    protected void releaseRes() {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            viewGroup = null;
        }
        try {
            Field field = mFragmentManager.getClass().getDeclaredField("mAdded");
            field.setAccessible(true);
            Object object = field.get(mFragmentManager);
            if (object != null) {
                object.getClass().getMethod("clear").invoke(object);
                object = null;
            }
            field = null;
        } catch (Exception e) {
        }
    }

    protected void onBackAction() {
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
//        Glide.with(this).onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        Glide.with(this).onLowMemory();
    }
}
