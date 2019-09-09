package com.zhangku.qukandian.activitys;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.utils.SetWebSettings;

/**
 * 2019年5月20日16:33:38
 * ljs
 * 用户协议、更多福利、webview
 */
public class ProtocolWebViewAct extends BaseAct {
    private TextView titleTV;
    private ProgressBar webprogressBar;
    private WebView webview;
    private String url;
    private String title;
    private String APP_MARKET_URL = "";

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews() {
//        APP_MARKET_URL = UserManager.getInst().getQukandianBean().getPakUrl();
//        if (!UserManager.getInst().hadLogin()) {
//            ActivityUtils.startToBeforeLogingActivity(this);
//            finish();
//            return;
//        }
        if (null != getIntent().getExtras()) {
            url = getIntent().getExtras().getString("url");
            title = getIntent().getExtras().getString("title");
        }
        if (TextUtils.isEmpty(url)) {
//            url = UserManager.mUrl;
            url = "http://www.qukandian.com/static/aboutus.html";//默认地址
        }
//        if (!TextUtils.isEmpty(url)) {
//            if (url.contains("?")) {
//                url += "&token=" + "Bearer "
//                        + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU) + (new Random().nextInt(90) + 10);
//            } else {
//                url += "?token=" + "Bearer "
//                        + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU) + (new Random().nextInt(90) + 10);
//            }
//            if (UserManager.getInst().hadLogin()) {
//                url += "&invitationCode=" + UserManager.getInst().getUserBeam().getId();
//            }
//        }

        webview = findViewById(R.id.webview);
        webprogressBar = findViewById(R.id.webprogressBar);
        titleTV = findViewById(R.id.titleTV);
        url = url +"?appflag=qksj";
        webview.loadUrl(url);
        SetWebSettings.setWebviewForActivity(this, webview, webprogressBar);


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http") && url.contains(".apk")) {
                    try {
                        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(viewIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (!url.startsWith("http")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
//                String title = webView.getTitle();
//                if (!TextUtils.isEmpty(title)) {
//                    titleTV.setText(title);
//                }
            }
        });

        findViewById(R.id.titleCloseTV).setVisibility(View.GONE);
//        findViewById(R.id.titleCloseTV).setOnClickListener(this);
        findViewById(R.id.titleBackIV).setOnClickListener(this);
        View StatusBar = findViewById(R.id.StatusBar);
        setStatusView(this, StatusBar);
        if (!TextUtils.isEmpty(title)) {
            titleTV.setText(title);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_webview;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
