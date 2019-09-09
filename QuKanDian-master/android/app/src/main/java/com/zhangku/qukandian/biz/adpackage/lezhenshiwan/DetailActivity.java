package com.zhangku.qukandian.biz.adpackage.lezhenshiwan;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhangku.qukandian.R;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class DetailActivity extends Activity implements OnRefreshListener {

    private WebView mWebView;

    private SwipeRefreshLayout mRefreshLayout;

    private String mStringUrl;

    // 标题的设置
    private FrameLayout mFrameLayout;

    private ImageButton mButton;

    private TextView mTextTitle;

    // 第一次不刷新、
    private boolean mBooleanPageNeedLoad;

    private DownloadManager downloadManager;
    private Handler mHandler;
    private String mStringAdid;
    private String mStringCid;//渠道id

    public String getmStringCid() {
        return mStringCid;
    }

    public void setmStringCid(String mStringCid) {
        this.mStringCid = mStringCid;
    }

    //下载id
    private Long mLongDownLoadId = 0L;
    private static final int seconds = 1000;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //查询进度，并通知页面
            int[] bytesAndStatus = PlayMeUtils.getBytesAndStatus(mLongDownLoadId, downloadManager);
            int hasDownSize = bytesAndStatus[0];
            int totalSize = bytesAndStatus[1];
            int status = bytesAndStatus[2];

            if (mWebView != null && hasDownSize >= 0 && totalSize > 0) {
                final int percent = (int) ((Float.parseFloat(hasDownSize + "") / Float.parseFloat(totalSize + "")) * 100);

                mWebView.post(new Runnable() {
                    @Override
                    public void run() {
                        X5JavaScriptInterface.mWebView.loadUrl("javascript:downloadApkFileProcessListener("
                                + mStringAdid + "," + percent + ")");
                    }
                });
            }
			mHandler.postDelayed(this, seconds);

        }
    };


    private BroadcastReceiver downLoadBroadcast;

    /**
     * 接受下载完成广播
     */
    private class DownLoadBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            switch (intent.getAction()) {
                case DownloadManager.ACTION_DOWNLOAD_COMPLETE:
                    try {
                        //移除对应的下载信息查询
                        if (mLongDownLoadId == downId) {
                            if (null != mHandler && null != runnable) {
                                mHandler.removeCallbacks(runnable);
                            }
                        }

                        //通知js下载成功
                        if (null != mWebView) {
                            SharedPreferences sp = getSharedPreferences("wowan", Activity.MODE_PRIVATE);
                            final String path = sp.getString(downId + "path", "");
                            final String adid = sp.getString(downId + "adid", "");

                            mWebView.post(new Runnable() {

                                @Override
                                public void run() {
                                    X5JavaScriptInterface.mWebView.loadUrl("javascript:downloadApkFileFinishListener("
                                            + adid + ",'" + path + "')");

                                }
                            });
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case DownloadManager.ACTION_NOTIFICATION_CLICKED:
//                    ToastUitl.showToast("我被点击啦！");
                    break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wowan_detail);

        mBooleanPageNeedLoad = false;

        mStringUrl = getIntent().getStringExtra("url");
        mStringCid = getIntent().getStringExtra("cid");


        try {
            //将String类型的地址转变为URI类型
            Uri uri = Uri.parse(mStringUrl);
            //通过URI的getQueryParameter()获取参数值
            mStringAdid = uri.getQueryParameter("adid");
            SharedPreferences sp = getSharedPreferences("wowan", Activity.MODE_PRIVATE);
            mLongDownLoadId = sp.getLong(mStringAdid, 0);//获取此次下载ID

        } catch (Exception e) {
            e.printStackTrace();
        }


        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("JavascriptInterface")
    private void initView() {


        mFrameLayout = (FrameLayout) findViewById(R.id.frameLayoutId_detail);
        mTextTitle = (TextView) findViewById(R.id.tv_wowan_title_detail);
        mButton = (ImageButton) findViewById(R.id.top_back_detail);

        mWebView = (WebView) findViewById(R.id.webview_detail);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);

        // 允许混合模式（http与https）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

		/*
         * NORMAL：正常显示，没有渲染变化。 SINGLE_COLUMN：把所有内容放到WebView组件等宽的一列中。
		 * //这个是强制的，把网页都挤变形了 NARROW_COLUMNS：可能的话，使所有列的宽度不超过屏幕宽度。 //好像是默认的
		 */
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDefaultTextEncodingName("UTF-8");
        // 提高渲染的优先级
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setTextZoom(100);

        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (null != mRefreshLayout) {
                    // 关闭加载进度条
                    mRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // super.onReceivedSslError(view, handler, error);
                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (!TextUtils.isEmpty(url) && !url.contains("Wall_Adinfo.aspx")) {
                    // 跳转外部浏览器
                    try {
                        Uri content_url = Uri.parse(url);
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.setData(content_url);
                        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }

            }
        });
        mWebView.addJavascriptInterface(new X5JavaScriptInterface(DetailActivity.this, mWebView), "android");

        if (!TextUtils.isEmpty(mStringUrl)) {
            mWebView.loadUrl(mStringUrl);
        }

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();

            }
        });

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_srl_detail);
        mRefreshLayout.setOnRefreshListener(this);


        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        mHandler = new Handler(Looper.getMainLooper());

        //注册广播
        registerBroadcast();
        //查询下载状态
        int[] bytesAndStatus = PlayMeUtils.getBytesAndStatus(mLongDownLoadId, downloadManager);
        int status = bytesAndStatus[2];


        if (status == DownloadManager.STATUS_RUNNING || status == DownloadManager.STATUS_PENDING) {
            //下载正在处理,启动handler开始查询
            mHandler.postDelayed(runnable, seconds);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBooleanPageNeedLoad = false;
        unregisterBroadcast();
        if (null != mHandler && null != runnable) {
            mHandler.removeCallbacks(runnable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        X5JavaScriptInterface.mWebView = mWebView;

        if (!mBooleanPageNeedLoad) {
            mBooleanPageNeedLoad = true;
            return;
        }

        if (mWebView != null) {
            mWebView.post(new Runnable() {

                @Override
                public void run() {
                    mWebView.loadUrl("javascript:pageViewDidAppear()");
                }
            });
        }
    }

    @Override
    public void onRefresh() {

        if (!TextUtils.isEmpty(mStringUrl)) {
            mWebView.loadUrl(mStringUrl);
        }

    }


    /**
     * 注册广播
     */
    private void registerBroadcast() {
        /**注册service 广播 1.任务完成时 2.进行中的任务被点击*/
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        registerReceiver(downLoadBroadcast = new DownLoadBroadcast(), intentFilter);
    }

    /**
     * 注销广播
     */
    private void unregisterBroadcast() {
        if (downLoadBroadcast != null) {
            unregisterReceiver(downLoadBroadcast);
            downLoadBroadcast = null;
        }
    }

    /**
     * js调用下载方法的时候，调用此方法告诉页面开始查询下载进度和状态
     */
    public void startCheckProgressStates() {
        if (mHandler == null || runnable == null) {
            return;
        }
        SharedPreferences sp = getSharedPreferences("wowan", Activity.MODE_PRIVATE);
        mLongDownLoadId = sp.getLong(mStringAdid, 0);//获取此次下载ID
        mHandler.postDelayed(runnable, seconds);
    }

}
