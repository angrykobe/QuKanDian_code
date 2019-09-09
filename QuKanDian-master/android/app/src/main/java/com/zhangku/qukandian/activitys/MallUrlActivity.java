package com.zhangku.qukandian.activitys;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.dialog.ShareForWebView;
import com.zhangku.qukandian.interfaces.OnSharedListener;
import com.zhangku.qukandian.javascript.DialogShareInterface;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.GsonUtil;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.PermissionHelper;
import com.zhangku.qukandian.widght.LoadingLayout;

import java.util.List;

/**
 * Created by yuzuoning on 2018/1/31.
 */

public class MallUrlActivity extends BaseTitleActivity implements DialogShareInterface.OnShareListener {
    private String url;
    private WebView mWebView;
    private LoadingLayout mLoadingLayout;

    @Override
    protected void initActionBarData() {
        setTitle("兑换商城");
    }

    @Override
    protected void initViews() {
        if (!UserManager.getInst().hadLogin()) {
            ActivityUtils.startToBeforeLogingActivity(this);
            finish();
            return;
        }
        if (Config.BASE_URL.equals("https://s.api.qukandian.com/")
                || Config.BASE_URL.contains(".qukandian.com")) {
            url = getString(R.string.mall_url_activity_url)
                    + UserManager.getInst().getUserBeam().getId()
                    + "&regSource=android";
        } else {
            url = getString(R.string.mall_url_activity_url_test)
                    + UserManager.getInst().getUserBeam().getId()
                    + "&regSource=android";
        }
        if (getIntent().getExtras() != null) {
            if (null != getIntent().getExtras().getString("url")) {
                String url1 = getIntent().getExtras().getString("url");
                if (!TextUtils.isEmpty(url1)) {
                    url = url1;
                    setTitle("赚赏金");
                }
            }
        }

        mWebView = findViewById(R.id.activity_new_url_web);
        mLoadingLayout = findViewById(R.id.loading_layout);
        mLoadingLayout.showLoading();
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                mLoadingLayout.hideLoadingLayout();
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

        mWebView.loadUrl(url);
        mWebView.addJavascriptInterface(new DialogShareInterface(this), "Share");
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_new_url_layout;
    }

    @Override
    public String setPagerName() {
        return "兑换商城";
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.loadUrl(url);
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        if (null != mWebView) {
            mWebView.clearHistory();
            mWebView.clearCache(true);
            mWebView.removeAllViews();
            mWebView = null;
        }
    }

    @Override
    public void onShareListener(int type, String title, String desc, String tel) {

    }

    @Override
    public void onShareListener(int type, String shareUrl, String bitmapUrl, String title, String content) {

    }


    @Override
    public void onShareDialog(final String title, final String desc, final String imgUrl, final String url) {
        ShareForWebView shareDialog = new ShareForWebView(this);
        shareDialog.setOnSharedListener(new OnSharedListener() {
            @Override
            public void onSharedListener(DetailsBean mDetailsBean, SHARE_MEDIA share_media) {
                UMImage sImg = new UMImage(MallUrlActivity.this, imgUrl);

                sImg.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                sImg.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                sImg.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

                UMWeb web = new UMWeb(url);
                web.setTitle(title);//标题

                UMImage umImage = new UMImage(MallUrlActivity.this, UserManager.mShareBean.getWechatIcon());
                umImage.setThumb(sImg);

                web.setThumb(umImage);  //缩略图
                web.setDescription(desc);//描述

                new ShareAction(MallUrlActivity.this).setPlatform(share_media).setCallback(null)
                        .withMedia(web)
                        .share();
            }
        });
        shareDialog.show();
    }

    @Override
    public void onToMainListener(int type) {
        switch (type) {
            case 0://首页
                ActivityUtils.startToMainActivity(this, 0, 0);
                break;
            case 1://收徒
                ActivityUtils.startToShoutuActivity(this);
                break;
            case 2://提现
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToWithdrawalsActivity(this);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(this);
                }
                break;
            case 3://任务中心
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToMainActivity(this, 2, 0);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(this);
                }
                break;
            case 4://兑换商城
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToMallActivity(this, "");
                } else {
                    ActivityUtils.startToBeforeLogingActivity(this);
                }
                break;
        }
    }

    @Override
    public void onToAnywhereListener(String deeplink) {

    }

    @Override
    public void onBackListener() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                finish();
                return true;
            }

        }
        return false;
    }

    @Override
    public void onGetPhone() {
        if (!PermissionHelper.check(PermissionHelper.READ_CONTACTS)) {
            PermissionHelper.request(this, PermissionHelper.READ_CONTACTS_CODE, PermissionHelper.READ_CONTACTS);
            return;
        }
        getPhone();
    }

    private void getPhone() {
        List list = CommonHelper.getPhoneList(this);
        String json = GsonUtil.toJson(list);
        String javascriptStr = "javascript:getPhoneBook(" + json + ")";
        mWebView.loadUrl(javascriptStr);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 用户同意了操作权限
            switch (requestCode) {
                case PermissionHelper.READ_CONTACTS_CODE:
                    getPhone();
                    break;
            }
        } else {
            // 用户拒绝了操作权限
            switch (requestCode) {
                case PermissionHelper.READ_CONTACTS_CODE:
                    PermissionHelper.showAlert(this, "通讯录");
                    break;
            }

        }
    }
}
