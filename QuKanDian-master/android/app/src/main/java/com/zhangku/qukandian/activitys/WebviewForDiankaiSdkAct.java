package com.zhangku.qukandian.activitys;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iBookStar.views.YmConfig;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.utils.SetWebSettings;

/**
 * 创建者          xuzhida
 * 创建日期        2018/10/9
 * 活动h5页面
 */
public class WebviewForDiankaiSdkAct extends BaseAct {
    private TextView titleTV;
    private ProgressBar webprogressBar;
    private WebView webview;
    private String url;
    private String title;

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews() {
        webview = (WebView) findViewById(R.id.webview);
        webprogressBar = (ProgressBar) findViewById(R.id.webprogressBar);
        titleTV = (TextView) findViewById(R.id.titleTV);
        webview.loadUrl("http://zku.i5read.com/?channel=zku01");
        SetWebSettings.setWebviewForActivity(this, webview, webprogressBar);

        webview.addJavascriptInterface(this, "YmNativeInterface");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                String title = webView.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    titleTV.setText(title);
                }
            }
        });

        findViewById(R.id.titleCloseTV).setOnClickListener(this);
        findViewById(R.id.titleBackIV).setOnClickListener(this);
        View StatusBar = findViewById(R.id.StatusBar);
        setStatusView(this, StatusBar);
        if (!TextUtils.isEmpty(title)) {
            titleTV.setText(title);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_webview_with_process;
    }

    @Override
    protected String setTitle() {
        return "";
    }

    @Override
    protected void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.titleBackIV:
                if (webview.canGoBack()) {
                    webview.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.titleCloseTV:
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (webview.canGoBack()) {
                webview.goBack();
                return true;
            } else {
                finish();
                return true;
            }
        }
        return false;
    }
    /**
     * 合作方如果直接使用阅盟的书城链接，必须实现这个本地接口，才能调用 SDK 的接口打开书籍
     * @param url
     */
    @JavascriptInterface
    public void openReader(String url)
    {
        //调用 SDK 的接口，打开书籍阅读
        YmConfig.openReader(url);
    }
}
