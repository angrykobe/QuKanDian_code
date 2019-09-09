package com.zhangku.qukandian.fragment;

import android.content.Context;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseFragment;
import com.zhangku.qukandian.javascript.DialogShareInterface;
import com.zhangku.qukandian.manager.UserManager;

/**
 * Created by yuzuoning on 2017/12/13.
 */

public class SougouFragment extends BaseFragment {
    private WebView mWebView;

    @Override
    protected void noNetword() {

    }

    @Override
    public void loadData(Context context) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.view_webview;
    }

    @Override
    protected void initViews(View convertView) {
        mWebView = convertView.findViewById(R.id.webview);

//        SetWebSettings.setWebviewBase(getContext(),mWebView);
        mWebView.addJavascriptInterface(new DialogShareInterface(null), "Share");

        WebSettings webSettings = mWebView.getSettings();
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                if(url.startsWith("http")){
//                    view.loadUrl(url);
//                }
//                ActivityUtils.startToUrlActivity(view.getContext(), url, Constants.URL_FROM_SOUGOU, true, null);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoadingLayout();
            }
        });

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
        mWebView.loadUrl(string + getString(R.string.sougou_fragment_html_for_test)
                + (UserManager.getInst().getmRuleBean().getSougouGoldRule().getDayMaxCount() > 0 ? 1 : 0) + "&t=" +System.currentTimeMillis());
    }

    @Override
    public String setPagerName() {
        return null;
    }

    public void reflash(){
        mWebView.clearCache(true);
        String string = UserManager.getInst().getQukandianBean().getPubwebUrl();
        mWebView.loadUrl(string + getString(R.string.sougou_fragment_html_for_test)
                + (UserManager.getInst().getmRuleBean().getSougouGoldRule().getDayMaxCount() > 0 ? 1 : 0) + "&t=" +System.currentTimeMillis());

    }
}
