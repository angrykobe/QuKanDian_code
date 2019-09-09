package com.zhangku.qukandian.activitys.task;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.TimeUtils;
import com.zhangku.qukandian.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

public class SdwWebActivity extends BaseAct {
    private WebView webview;
    private String url="http://www.shandw.com/auth/?";//闪电玩请求的授权地址
    private String openid;//第三方用户id
    private String nick;//第三方用户昵称
    private String avatar;//第三方用户头像
    private String sex;//第三方渠道的性别，1：男；2：女；0：其他未知
    private String phone;//第三方渠道的手机号，如果没有手机号，请填写""
    private String time;//当前服务器的时间戳（单位：秒）
    private String channel;//闪电玩平台分配的渠道号
    private String sign;//验签信息
    private String key;//闪电玩平台分配的key值

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews() {
        webview = findViewById(R.id.webview);
        channel="12537";
        key="d9897e8c00a241aa9ef514866f6f75e8";
        UserBean userBeam = UserManager.getInst().getUserBeam();
        if (userBeam!=null){
            openid=userBeam.getId()+"";
            nick=userBeam.getNickName();
            avatar=userBeam.getAvatarUrl();
            if (UserManager.getInst().hadLogin() && UserManager.getInst().getUserBeam().getWechatUser() != null) {
               sex=UserManager.getInst().getUserBeam().getWechatUser().getSex()+ "";//性别，1-男，2-女
            }else {
                sex="0";
            }
            String phone1 = UserSharedPreferences.getInstance().getString(Constants.PHONE, "");
            if (!TextUtils.isEmpty(phone1)){
                phone=phone1;
            }else{
                phone="";
            }
            String longTime = TimeUtils.getTime();
            time=longTime;
        }
        String ping="channel="+channel+"&openid="+openid+"&time="+time+
                "&nick="+nick+"&avatar="+avatar+"&sex="+sex+"&phone="+phone+key ;
        sign = CommonHelper.md5(ping);
        String ping1="channel="+channel+"&openid="+openid+"&time="+time+
                "&nick="+nick+"&avatar="+avatar+"&sex="+sex+"&phone="+phone+"&sign="+sign+"&sdw_simple=1"+"&sdw_ld=1";
        url=url+ping1;
       // SetWebSettings.setWebviewNotAd(this,webview);
        webview.canGoBack();
        WebSettings webSetting = webview.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());
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
        String uaNew=ua.replace("MQQBrowser/6.2","");
        webSetting.setUserAgentString(uaNew + ";qukandian");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Context context = view.getContext();
                Uri nextUri = Uri.parse(url);
                if ("alipays".equals(nextUri.getScheme())||"alipay".equals(nextUri.getScheme())) {
                    try{
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                        return true;
                    }catch (Exception e) {
                        // 启动支付宝失败，换成网页支付
                        return true;
                    }

                }
                if ("http".equals(nextUri.getScheme()) || "https".equals(nextUri.getScheme())) {
                    String originalUrl = view.getOriginalUrl();
                    Map<String, String> extraHeaders = new HashMap<>();
                    //微信支付的时候手动添加Referer
                    if (!TextUtils.isEmpty(originalUrl) && url.startsWith("https://wx.tenpay.com/")) {
                        extraHeaders.put("Referer", originalUrl);
                        view.loadUrl(url, extraHeaders);
                    } else {
                        view.loadUrl(url);
                    }
                    return true;
                } else if ("weixin".equals(nextUri.getScheme())) {
                    if (CommonHelper.isAppInstalled("com.tencent.mm")) {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    } else {
                        ToastUtils.showShortToast(context, "您未安装微信");
                    }
                } else {
                    return false;
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

        });
        webview.loadUrl(url);
    }

    @Override
    protected int getLayoutRes() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_sdw_web;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void myOnClick(View v) {

    }

 /*   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode==KeyEvent.KEYCODE_BACK&&webview.canGoBack()){
            webview.goBack();
            return true;
        }else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            finish();
        }
    }
}
