package com.zhangku.qukandian.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aitangba.swipeback.SwipeBackActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.wanjian.cockroach.Cockroach;
import com.zhangku.qukandian.BuildConfig;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.PushBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.dialog.DialogPrograss;
import com.zhangku.qukandian.dialog.DlgLoading;
import com.zhangku.qukandian.dialog.PushDialog;
import com.zhangku.qukandian.observer.ExitActivityObserver;
import com.zhangku.qukandian.observer.NetOberver;
import com.zhangku.qukandian.observer.PushOberver;
import com.zhangku.qukandian.protocol.AddLogProtocol;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.widght.ActionBarLayout;
@Deprecated
public abstract class BaseActivity extends SwipeBackActivity implements NetOberver.OnNetTypeChangeListener, PushOberver.OnPushListener {
    protected ActionBarLayout mActionBarLayout;
    private DlgLoading mDlgLoading;
    public MyHandler mHandler;
    protected DialogPrograss mDialogPrograss;
    protected PushDialog mPushDialog;
    protected FrameLayout mFrameLayout;
    protected View mView;
    private Activity mActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mActivity = this;
        mHandler = new MyHandler(this);
        int resId = getLayoutRes();
        if (resId > 0) {
            setContentView(R.layout.activity_base_layout);
        }
        mActionBarLayout = (ActionBarLayout) findViewById(R.id.layout_title_layout);
        mFrameLayout = (FrameLayout) findViewById(R.id.base_layout);
        mView = findViewById(R.id.base_layout_placeholder);

        mFrameLayout.addView(LayoutInflater.from(this).inflate(resId, mFrameLayout, false));

        attachView();
        initViews();
        initData();
        mDialogPrograss = new DialogPrograss(this);
        mPushDialog = new PushDialog(this);
        loadData();

        NetOberver.getInstance().addNetTypeChangeListener(this);
        NetOberver.getInstance().notityNetChange(CommonHelper.isNetworkAvailable(this), CommonHelper.isWifi(this));
        PushOberver.getInstance().addOnPushListener(this);
        LogUtils.LogI("ClassName ==" + getClass().getName());

        /**
         * 获取状态栏高度
         * */
//        try {
//            //获取status_bar_height资源的ID
//            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//            if (resourceId > 0) {
//                //根据资源ID获取响应的尺寸值
//                mStatusBarHeight = getResources().getDimensionPixelSize(resourceId);
//            }
//        } catch (Exception e) {
//
//        }
    }

    protected void attachView() {
    }

    public void hideActionBarView() {
        if (mActionBarLayout != null) {
            mActionBarLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    protected void onBackAction() {
        finish();
    }

    @Override
    public void onNetChange(boolean isAvailable, boolean isWifi) {
    }

    @Override
    public void onPushListener(int postId, String title, int type) {
        Message message = mHandler.obtainMessage();
        message.obj = new PushBean(postId, title, type);
        message.what = 0;
        mHandler.sendMessage(message);
    }

    protected class MyHandler extends Handler {

        public MyHandler(BaseActivity activity) {

        }

        @Override
        public void handleMessage(Message msg) {
            consumeMessage(msg);
        }
    }

    /**
     * 加载数据
     */
    protected void initData() {

    }

    /**
     * 加载网络数据
     */
    protected void loadData() {

    }

    /**
     * 初始化控�?
     */
    protected abstract void initViews();

    /**
     * 加载视图ID
     *
     * @return
     */
    protected abstract int getLayoutRes();

    protected boolean consumeMessage(Message msg) {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String pName = setPagerName();
        if (!TextUtils.isEmpty(pName)) {
            MobclickAgent.onPageStart(pName);
        }
        MobclickAgent.onResume(this);
        Glide.with(this).resumeRequests();

        if (Config.isOpenCockroach && BuildConfig.DEBUG) {
            // 装载异常处理
            Cockroach.install(new Cockroach.ExceptionHandler() {

                // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

                @Override
                public void handlerException(final Thread thread, final Throwable throwable) {
                    //开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
                    //由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
//                            String elog = "--->CockroachException:" + thread + "<---", throwable;
                                Toast.makeText(QuKanDianApplication.getAppContext(), "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
                                LogUtils.LogE("----------------------thread=" + thread.getId());
                                CrashReport.postCatchedException(throwable, thread);  // bugly会将这个throwable上报

                                if (mActivity != null) {
                                    mActivity.finish();
                                }
                            } catch (Throwable e) {
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String pName = setPagerName();
        if (!TextUtils.isEmpty(pName)) {
            MobclickAgent.onPageEnd(pName);
        }
        MobclickAgent.onPause(this);
        Glide.with(this).pauseRequests();
        if (Config.isOpenCockroach && BuildConfig.DEBUG) {
            LogUtils.LogE("Cockroach.uninstalled");
            Cockroach.uninstall();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Glide.with(this).onStop();
    }

    public abstract String setPagerName();

    @SuppressLint("NewApi")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PushOberver.getInstance().removePushalistener(this);
        NetOberver.getInstance().removeNetTypeChangeListener(this);
        ExitActivityObserver.getInst().onActivityDestory(this);
        if (Build.VERSION.SDK_INT >= 17 &&!this.isDestroyed()) {
            Glide.with(this).onDestroy();
        }
       /* if (Util.isOnMainThread()){
            Glide.with(this).pauseRequests();
        }*/
        releaseRes();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    /**
     * 资源释放
     */
    protected void releaseRes() {
        closeDialog();
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            viewGroup = null;
        }
    }

    public void closeDialog() {
        if (mDialogPrograss != null) {
            mDialogPrograss.dismiss();
        }
        if (mPushDialog != null) {
            mPushDialog.dismiss();
        }
        closeDlgLoading();
    }

    public void showDloLoading(String text) {
        if (mDlgLoading == null) {
            mDlgLoading = DlgLoading.createDlg(this, text);
            mDlgLoading.showDlg();
            postRequest();
        }
    }

    protected void postRequest() {

    }

    public void closeDlgLoading() {
        if (mDlgLoading != null) {
            mDlgLoading.closeDlg();
            mDlgLoading = null;
        }
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
