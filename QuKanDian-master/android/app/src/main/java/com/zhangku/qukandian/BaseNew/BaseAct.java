package com.zhangku.qukandian.BaseNew;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.wanjian.cockroach.Cockroach;
import com.zhangku.qukandian.BuildConfig;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.dialog.DialogPrograss;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.SystemBarTintManager;

public abstract class BaseAct extends AppCompatActivity implements View.OnClickListener{
    protected DialogPrograss mDialogPrograss;
    private Activity mActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transparencyBar(this);
        mActivity = this;
        setConView();
        initViews();
        loadData();
        mDialogPrograss = new DialogPrograss(this);
        LogUtils.LogI("ClassName =="+getClass().getName());
    }

    @TargetApi(19)
    public void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintColor(Color.TRANSPARENT);
            tintManager.setStatusBarTintEnabled(true);
        }
    }



    protected void setConView(){
        setContentView(getLayoutRes());
    }

    /** 加载网络数据 */
    protected abstract void loadData();
    /** 初始化控 */
    protected abstract void initViews();
    /** 加载视图ID */
    protected abstract int getLayoutRes();
    /**设置标题*/
    protected abstract String setTitle();
    /**点击*/
    protected abstract void myOnClick(View v);

    @Override
    protected void onResume() {
        super.onResume();
        String pName = setTitle();
        if (!TextUtils.isEmpty(pName)) {
            MobclickAgent.onPageStart(pName);
        }
        MobclickAgent.onResume(this);
        Glide.with(this).resumeRequests();
        if (Config.isOpenCockroach && BuildConfig.DEBUG) {
            // 装载异常处理
            LogUtils.LogE("Cockroach.installed");
            Cockroach.install(new Cockroach.ExceptionHandler() {

                // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

                @Override
                public void handlerException(final Thread thread, final Throwable throwable) {
                    //开发时使用Cockroach可能不容易发现bug，所以建议开发阶段在handlerException中用Toast谈个提示框，
                    //由于handlerException可能运行在非ui线程中，Toast又需要在主线程，所以new了一个new Handler(Looper.getMainLooper())，
                    //所以千万不要在下面的run方法中执行耗时操作，因为run已经运行在了ui线程中。
                    //new Handler(Looper.getMainLooper())只是为了能弹出个toast，并无其他用途
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //建议使用下面方式在控制台打印异常，这样就可以在Error级别看到红色log
                                CrashReport.postCatchedException(throwable, thread);  // bugly会将这个throwable上报
                                if (mActivity != null) {
                                    mActivity.finish();
                                }
                            } catch (Throwable e) {
                                Log.e("way",""+e.toString());
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
        String pName = setTitle();
        if (!TextUtils.isEmpty(pName)) {
            MobclickAgent.onPageEnd(pName);
        }
        MobclickAgent.onPause(this);
        Glide.with(this).pauseRequests();
        if (Config.isOpenCockroach && BuildConfig.DEBUG) {
            Cockroach.uninstall();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Glide.with(this).onStop();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Build.VERSION.SDK_INT >= 17 && !this.isDestroyed()){
            Glide.with(this).onDestroy();
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

    @Override
    public void onClick(View v) {
        myOnClick(v);
    }

    protected void setStatusView(Activity mContext, View statusBar) {
        try {
            statusBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(mContext)));
            statusBar.setBackgroundColor(mContext.getResources().getColor(R.color.gray_tran));
        }catch (Exception e){
        }
    }

    protected int getStatusBarHeight(Activity a) {
        int result = 0;
        int resourceId = a.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = a.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
