package com.zhangku.qukandian.activitys;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseTitleActivity;

/**
 * Created by yuzuoning on 2018/4/10.
 */

public class AdNativeWebActivity extends BaseTitleActivity {
    private String url;
    private WebView mWebView;
    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        if (null != getIntent().getExtras()) {
            url = getIntent().getExtras().getString("url");
        }
        mWebView = findViewById(R.id.activity_ad_native_web_webview);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
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

        String ua = webSetting.getUserAgentString();
        webSetting.setUserAgentString(ua + ";qukandian");

        mWebView.loadUrl(url);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_ad_native_web_layout;
    }

    @Override
    public String setPagerName() {
        return "原生广告web";
    }
}
