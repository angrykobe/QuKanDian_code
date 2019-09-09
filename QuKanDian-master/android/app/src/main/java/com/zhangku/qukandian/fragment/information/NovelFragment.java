package com.zhangku.qukandian.fragment.information;

import android.content.Context;
import android.view.View;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.zhangku.qukandian.BaseNew.BaseFra;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.SetWebSettings;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/17
 * 你不注释一下？
 */
public class NovelFragment extends BaseFra {

    @Override
    protected void loadData(Context context) {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.view_x5_webview;
    }

    @Override
    protected void initViews(View convertView) {
        WebView mWebView = convertView.findViewById(R.id.x5Webview);
        SetWebSettings.setWebviewBase(getContext(),mWebView);
        WebSettings webSettings = mWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        String novelUrl = UserManager.getInst().getQukandianBean().getNovelUrl();

//        mWebView.loadUrl("http://t1.candy-zheng.com/index.php/cms/column/index.html");
        mWebView.loadUrl(novelUrl + "/index.php/cms/column/index.html");
    }
}
