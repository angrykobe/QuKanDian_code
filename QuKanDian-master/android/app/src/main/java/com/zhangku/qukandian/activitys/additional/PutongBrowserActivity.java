package com.zhangku.qukandian.activitys.additional;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.widght.LoadingLayout;

/**
 * Created by qiujianwen on 2018-5-22 09:49:47.
 * 广告浏览
 */

public class PutongBrowserActivity extends BaseTitleActivity {
    private String pageTitle = "页面加载中...";
    private String url;
    private WebView mWebView;
    private LoadingLayout mLoadingLayout;
    private Context mContext;
    private boolean isDownload = false;

    @Override
    protected void initActionBarData() {
        setTitle(pageTitle);
    }

    @Override
    protected void initViews() {
        if (null != getIntent().getExtras()) {
            url = getIntent().getExtras().getString("url");
            isDownload = getIntent().getExtras().getBoolean("isDownload");
        }
        LogUtils.LogE("url="+url);

        mContext = this;

        mWebView = findViewById(R.id.activity_new_url_web);
        mLoadingLayout = findViewById(R.id.loading_layout);
        mLoadingLayout.showLoading();
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.LogE("+++++++++++url="+url);
                LogUtils.LogE("+++++++++++isDownload="+isDownload);
                if (url.startsWith("http") && url.contains(".apk")) {
                    try {
                        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(viewIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                mLoadingLayout.hideLoadingLayout();
                String title = webView.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    setTitle(title);
                }
            }
        });
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSetting.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSetting.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSetting.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSetting.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSetting.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSetting.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT); //关闭webview中缓存
        webSetting.setAllowFileAccess(true); //设置可以访问文件
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSetting.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSetting.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);

        // 不设置UserAgent
//        String ua = webSetting.getUserAgentString();
//        webSetting.setUserAgentString(ua + ";qukandian");

        if (!TextUtils.isEmpty(url)) {
            if (url.contains(".apk") || isDownload) {
                try {
                    Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(viewIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                mWebView.loadUrl(url);
            }
        } else {
            mLoadingLayout.hideLoadingLayout();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_new_url_layout;
    }

    @Override
    public String setPagerName() {
        return pageTitle;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.clearHistory();
            mWebView.clearCache(true);
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                close();
                return true;
            }
        }
        return false;
    }

    private void close()
    {
        finish();
    }

}
