package com.zhangku.qukandian.activitys.information;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.blankj.utilcode.util.EncodeUtils;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.EventBusBean.SougouEvent;
import com.zhangku.qukandian.javascript.DialogShareInterface;
import com.zhangku.qukandian.manager.UserManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * 热搜（SougouFragment 改 SougouActivity）
 * 2019-6-10 15:42:00
 * ljs
 */
public class SougouActivity extends BaseAct {
    private WebView mWebView;
    private TextView titleTV;
    private String hotword;
    private String redNum;
    private int rscnt;

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_webview_sougou;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews() {
        titleTV = findViewById(R.id.titleTV);
        titleTV.setText("热搜");
        findViewById(R.id.titleCloseTV).setOnClickListener(this);
        findViewById(R.id.titleBackIV).setOnClickListener(this);

        mWebView = findViewById(R.id.webview);

//        SetWebSettings.setWebviewBase(getContext(),mWebView);
        mWebView.addJavascriptInterface(new DialogShareInterface(null), "Share");

        WebSettings webSettings = mWebView.getSettings();
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
//                if(url.startsWith("http")){
//                    view.loadUrl(url);
//                }
//                ActivityUtils.startToUrlActivity(view.getContext(), url, Constants.URL_FROM_SOUGOU, true, null);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        EventBus.getDefault().register(this);


        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setDomStorageEnabled(true);
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
//        mWebView.loadUrl(getString(R.string.sougou_fragment_html)
//                + (UserManager.getInst().getSougouGoldRule().getDayMaxCount() > 0 ? 1 : 0));
        String string = UserManager.getInst().getQukandianBean().getPubwebUrl();


        if (null != getIntent().getExtras()) {
            hotword = getIntent().getExtras().getString("hotword");
            redNum = getIntent().getExtras().getString("redNum");
            rscnt = getIntent().getExtras().getInt("rscnt");
            hotword = EncodeUtils.urlEncode(hotword);

        }

        String url = string + getString(R.string.sougou_fragment_html_for_test)
                + (UserManager.getInst().getmRuleBean().getSougouGoldRule().getDayMaxCount() > 0 ? 1 : 0)
                + "&t=" + System.currentTimeMillis()
                + "&hotword=" + hotword
                + "&redNum=" + redNum
                + "&rscnt=" + rscnt;
        mWebView.loadUrl(url);
    }


    @Override
    protected String setTitle() {
        return "";
    }

    @Override
    protected void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.titleBackIV:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(SougouEvent event) {
        if (event.getRedNum() > 0) {
            putRedSumToWeb(event.getRedNum());
        }
    }

    private void putRedSumToWeb(int gold) {
        String javascriptStr = "javascript:HotSearch(" + gold + ")";
        mWebView.loadUrl(javascriptStr);
    }
}
