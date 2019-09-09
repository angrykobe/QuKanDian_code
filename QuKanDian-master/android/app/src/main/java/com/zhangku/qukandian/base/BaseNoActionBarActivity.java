package com.zhangku.qukandian.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.wanjian.cockroach.Cockroach;
import com.zhangku.qukandian.utils.LogUtils;

/**
 * Created by yuzuoning on 2018/1/30.
 */

public abstract class BaseNoActionBarActivity extends AppCompatActivity {
    public MyHandler mHandler;
    private Activity mActivity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = this;

//        Window window = getWindow();
//        //隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //隐藏状态栏
//        //定义全屏参数
//        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
//        //设置当前窗体为全屏显示
//        window.setFlags(flag, flag);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        mHandler = new MyHandler(this);
        int resId = getLayoutRes();
        if (resId > 0) {
            setContentView(resId);
        }
        initViews();
        LogUtils.LogI("ClassName == "+ this.getLocalClassName());
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

    protected class MyHandler extends Handler {

        public MyHandler(BaseNoActionBarActivity activity) {

        }

        @Override
        public void handleMessage(Message msg) {
            consumeMessage(msg);
        }
    }

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
        LogUtils.LogD("Cockroach.installed");
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
//                            String elog = "--->CockroachException:" + thread + "<---", throwable;
//                            Log.e("AndroidRuntime", elog);
//                            Toast.makeText(QuKanDianApplication.getAppContext(), "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
//                        throw new RuntimeException("..."+(i++));
//                            LogUtils.LogE("----------------------thread="+thread.getId());
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

    @Override
    protected void onPause() {
        super.onPause();
        String pName = setPagerName();
        if (!TextUtils.isEmpty(pName)) {
            MobclickAgent.onPageEnd(pName);
        }
        MobclickAgent.onPause(this);
        Glide.with(this).pauseRequests();

        LogUtils.LogD("Cockroach.uninstalled");
        Cockroach.uninstall();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Glide.with(this).onStop();
    }

    @Override
    protected void onDestroy() {
        if (Util.isOnMainThread() && !this.isFinishing()) {
            Glide.with(this).onDestroy();
//            pauseRequests
        }
        super.onDestroy();
    }

    public abstract String setPagerName();
}
