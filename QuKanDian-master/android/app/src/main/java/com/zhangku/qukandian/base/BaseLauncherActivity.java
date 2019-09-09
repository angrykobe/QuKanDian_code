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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.aitangba.swipeback.SwipeBackActivity;
import com.bumptech.glide.Glide;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.wanjian.cockroach.Cockroach;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.PushBean;
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
public abstract class BaseLauncherActivity extends SwipeBackActivity implements NetOberver.OnNetTypeChangeListener, PushOberver.OnPushListener {
    protected ActionBarLayout mActionBarLayout;
    private DlgLoading mDlgLoading;
    public MyHandler mHandler;
    protected DialogPrograss mDialogPrograss;
    protected PushDialog mPushDialog;
    protected FrameLayout mFrameLayout;
    protected View mView;
    private Activity mActivity;
    private AddLogProtocol mAddLogProtocol;

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
        loadData();
        mDialogPrograss = new DialogPrograss(this);
        mPushDialog = new PushDialog(this);

        NetOberver.getInstance().addNetTypeChangeListener(this);
        NetOberver.getInstance().notityNetChange(CommonHelper.isNetworkAvailable(this), CommonHelper.isWifi(this));
        PushOberver.getInstance().addOnPushListener(this);

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

        public MyHandler(BaseLauncherActivity activity) {

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

        // 装载异常处理
//        LogUtils.LogE("Cockroach.installed");
//        Cockroach.install(new Cockroach.ExceptionHandler() {
//
//            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException
//
//            @Override
//            public void handlerException(final Thread thread, final Throwable throwable) {
//                //开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
//                //由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，
//                //所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。
//                //new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
////                            String elog = "--->CockroachException:" + thread + "<---", throwable;
////                            Log.e("AndroidRuntime", elog);
////                            Toast.makeText(QuKanDianApplication.getAppContext(), "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
////                        throw new RuntimeException("..."+(i++));
////                            LogUtils.LogE("----------------------thread="+thread.getId());
//                            CrashReport.postCatchedException(throwable, thread);  // bugly会将这个throwable上报
//                            if (mActivity != null) {
//                                mActivity.finish();
//                            }
//                        } catch (Throwable e) {
//
//                        }
//                    }
//                });
//            }
//        });
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
//        LogUtils.LogE("Cockroach.uninstalled");
//        Cockroach.uninstall();
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
        if(Build.VERSION.SDK_INT >= 17&&!this.isDestroyed()){
            Glide.with(this).onDestroy();
        }
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
        closeDlgLoading();
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            viewGroup = null;
        }
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
