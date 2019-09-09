package com.zhangku.qukandian.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.ad.AdWebViewAct;
import com.zhangku.qukandian.adCommon.natives.AdZhiZuNative;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogConfirm;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.protocol.PostNewAwakenInfor;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

/**
 * Created by yuzuoning on 2017/7/13.
 */
public class SetWebSettings {
    public static void setWebview(final Context context, WebView webview, final AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains(".apk")) {
                    Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(viewIntent);
                } else if(url.startsWith("http")){
                    if(AdZhiZuNative.getRedState(bean)==0){//无红包链接  直接跳转
                        ActivityUtils.startToUrlActivity(context, url, Constants.URL_FROM_ADS, false, bean);
                    }else if(UserManager.getInst().hadLogin()){
                        //有红包 且登录状态 直接跳链接
                        if (bean.getDeliveryMode() == 1) {
                            ActivityUtils.startToAdWebViewAct(context,url,bean);
                        } else {
                            ActivityUtils.startToUrlActivity(context, url, Constants.URL_FROM_ADS, true, bean);
                        }
                    }else{
                        new DialogConfirm(context).setMessage(R.string.goto_login_for_red_str)
                                .setYesBtnText(R.string.goto_login_for_red_btn)
                                .setListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //有红包 且无登录状态 跳登录
                                        ActivityUtils.startToBeforeLogingActivity(context);
                                    }
                                }).show();
                    }
                }
                return true;
            }
        });
        WebSettings webSetting = webview.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(context.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(context.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(context.getDir("geolocation", 0)
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
//        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE); //webview中缓存
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT); //关闭webview中缓存
        webSetting.setAllowFileAccess(true); //设置可以访问文件
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSetting.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSetting.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webview.setHorizontalScrollBarEnabled(false);//水平不显示
        webview.setVerticalScrollBarEnabled(false); //垂直不显示
        //下面方法去掉
        IX5WebViewExtension ix5 = webview.getX5WebViewExtension();
        if (null != ix5) {
            ix5.setScrollBarFadingEnabled(false);
        }
//        CookieManager cookieManager = CookieManager.getInstance();
//        com.tencent.smtt.sdk.CookieManager cm=com.tencent.smtt.sdk.CookieManager.getInstance();
//        cm.setAcceptCookie(false);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            cookieManager.removeSessionCookie();// 移除
//        } else {
//            cookieManager.removeSessionCookies(null);// 移除
//        }
    }

    /**
     * 普通h5，不是广告，不用上报
     *
     * @param context
     * @param webview
     */
    public static void setWebviewNotAd(final Context context, WebView webview) {
        setWebviewBase(context,webview);
    }

    /**
     * 文章详情页内容h5
     * 文字大小可改变
     * @param context
     * @param webview
     */
    public static void setWebviewForDetails(final Context context, WebView webview) {
        WebSettings webSetting = webview.getSettings();
        int size = UserSharedPreferences.getInstance().getInt(Constants.ART_DETAIL_WORD_SIZE,2);
        //1 small TextSize.SMALLER 2 middle TextSize.NORMAL 3 big TextSize.LARGER
        switch (size){
            case 1:
                webSetting.setTextSize(WebSettings.TextSize.SMALLER);
                break;
            case 2:
                webSetting.setTextSize(WebSettings.TextSize.NORMAL);
                break;
            case 3:
                webSetting.setTextSize(WebSettings.TextSize.LARGER);
                break;
        }
        setWebviewBase(context,webview);
    }
    /**
     * 广告红包
     * @param context
     * @param webview
     */
    public static void setWebviewForAd(Context context, WebView webview, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(i);
                }
            }
        });
        setWebviewBase(context,webview);
    }

    /**
     * 活动h5
     * @param context
     * @param webview
     * @param progressBar
     */
    public static void setWebviewForActivity(Context context, WebView webview, final ProgressBar progressBar){
        progressBar.setVisibility(View.VISIBLE);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(i);
                }
            }
        });
        setWebviewBase(context,webview);
    }


    /**
     */
    public static void setWebviewBase(final Context context, WebView webview) {
        WebSettings webSetting = webview.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(context.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(context.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(context.getDir("geolocation", 0).getPath());
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
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webview.setVerticalScrollBarEnabled(false);
        webview.setVerticalScrollbarOverlay(false);
        webview.setHorizontalScrollBarEnabled(false);
        webview.setHorizontalScrollbarOverlay(false);
        String ua = webSetting.getUserAgentString();
        webSetting.setUserAgentString(ua + ";qukandian");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }


}
