package com.zhangku.qukandian.biz.adpackage.lezhenshiwan;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.biz.adcore.AdConfig;

import java.io.File;

public class WowanIndex extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private WebView mWebView;

    private SwipeRefreshLayout mRefreshLayout;

    private String mStringUrl;

    private String md5Str;

    private String keycode;

    // 标题的设置
    private FrameLayout mFrameLayout;

    private ImageButton mButton;

    private TextView mTextTitle;

    public static final String mStringVer = "1.0";

    // 第一次不刷新、
    private boolean mBooleanPageNeedLoad;

    private BroadcastReceiver downLoadBroadcast;
    private DownloadManager downloadManager;

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
                        //开始安装
//                        final Uri downIdUri = downloadManager.getUriForDownloadedFile(downId);
                        SharedPreferences sp = getSharedPreferences("wowan", Activity.MODE_PRIVATE);
                        String path = sp.getString(downId + "path", "");
                        File apkFile = new File(path);
                        if (null != apkFile && apkFile.exists()) {
                            //开始安装
                            PlayMeUtils.install(WowanIndex.this, apkFile);
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


    private String mStringCid = "";//我方提供
    private String mStringCuid = "";//用户识别号，userid
    private String mStringDeviceid = "";//手机设备号，获取手机设备号

    public static final String mStringKey = AdConfig.lezhen_game_StringKey;//秘钥，我方提供//测试秘钥fdsmkfshdik423432


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wowan);

        mStringCid = getIntent().getStringExtra("cid");
        mStringCuid = getIntent().getStringExtra("cuid");
        mStringDeviceid = getIntent().getStringExtra("deviceid");

        if (TextUtils.isEmpty(mStringCid) || TextUtils.isEmpty(mStringCuid) || TextUtils.isEmpty(mStringDeviceid)
                || TextUtils.isEmpty(mStringKey)) {
            finish();
        }

        mBooleanPageNeedLoad = false;

        md5Str = "t=2&cid=" + mStringCid + "&cuid=" + mStringCuid + "&deviceid=" + mStringDeviceid + "&unixt="
                + System.currentTimeMillis();

        keycode = PlayMeUtils.encrypt(md5Str + mStringKey);

        md5Str = md5Str + "&keycode=" + keycode + "&issdk=1&sdkver=" + mStringVer;

        mStringUrl = "https://m.playmy.cn/View/Wall_AdList.aspx?" + md5Str;
        // mStringUrl = "http://apptran61.dandanz.com/test.htm?"+md5Str;
        initView();
    }

    private void initView() {

        mFrameLayout = (FrameLayout) findViewById(R.id.frameLayoutId);
        mTextTitle = (TextView) findViewById(R.id.tv_wowan_title);
        mButton = (ImageButton) findViewById(R.id.top_back);


        mWebView = (WebView) findViewById(R.id.webview);
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
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
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
                if (!TextUtils.isEmpty(url)) {
                    PlayMeUtils.openAdDetail(WowanIndex.this,mStringCid, url);
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }

            }
        });


        mWebView.addJavascriptInterface(new X5JavaScriptInterface(WowanIndex.this, mWebView), "android");


        if (!TextUtils.isEmpty(mStringUrl)) {
            mWebView.loadUrl(mStringUrl);

        }

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != mWebView && mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }

            }
        });

        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_srl);
        mRefreshLayout.setOnRefreshListener(this);

        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        registerBroadcast();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBooleanPageNeedLoad = false;
        unregisterBroadcast();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        // super.onResume();

        X5JavaScriptInterface.mWebView = mWebView;

        if (!mBooleanPageNeedLoad) {
            mBooleanPageNeedLoad = true;
            super.onResume();
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
        super.onResume();

    }

    @Override
    public void onRefresh() {

        if (!TextUtils.isEmpty(mStringUrl)) {
            mWebView.loadUrl(mStringUrl);
        }

    }
}
