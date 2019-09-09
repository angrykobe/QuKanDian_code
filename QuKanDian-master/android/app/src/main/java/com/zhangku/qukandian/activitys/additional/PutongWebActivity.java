package com.zhangku.qukandian.activitys.additional;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseNoActionBarActivity;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.ShareBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogPrograss;
import com.zhangku.qukandian.dialog.ShareForWebView;
import com.zhangku.qukandian.interfaces.OnSharedListener;
import com.zhangku.qukandian.javascript.DialogShareInterface;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetShareImgProtocol;
import com.zhangku.qukandian.protocol.GetShoutuShareHostProtocol;
import com.zhangku.qukandian.protocol.GetStadomainProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.Draw281QR;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.GsonUtil;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.OperateUtil;
import com.zhangku.qukandian.utils.PermissionHelper;
import com.zhangku.qukandian.utils.ThirdWeChatShare;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.widght.LoadingLayout;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by qiujianwen on 2018-4-19 14:46:39.
 * 收徒
 */

public class PutongWebActivity extends BaseNoActionBarActivity implements DialogShareInterface.OnShareListener {
    private String pageTitle = "";
    private String url;
    private WebView mWebView;
    private String mWebpageUrl;
    private ProgressBar webprogressBar;
    private LoadingLayout mLoadingLayout;
    public static List<Bitmap> mShareBitmaps = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;
    private static String APP_MARKET_URL = "http://dwz.cn/78a1KZ";
    private static String mLoadingText = "页面加载中...";
    protected DialogPrograss mDialogPrograss;
    protected Bitmap mAvatar;

    @Override
    protected void initViews() {
        if (!UserManager.getInst().hadLogin()) {
            LogUtils.LogE("您未登录...");
            ActivityUtils.startToBeforeLogingActivity(this);
            finish();
            return;
        }
        if (UserManager.getInst().getmRuleBean() == null || UserManager.getInst().getmRuleBean().getActiveShoutuConfig() == null) {
            finish();
            return;
        }
        if (null != getIntent().getExtras()) {
            url = getIntent().getExtras().getString("url");
        }
        LogUtils.LogE("url=" + url);
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("?")) {
                url += "&token=" + "Bearer "
                        + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU) + (new Random().nextInt(90) + 10);
            } else {
                url += "?token=" + "Bearer "
                        + UserSharedPreferences.getInstance().getString(Constants.TOKEN, Config.AU) + (new Random().nextInt(90) + 10);
            }
            if (UserManager.getInst().hadLogin()) {
                url += "&invitationCode=" + UserManager.getInst().getUserBeam().getId();
            }
        }

        mContext = this;
        mActivity = this;
        mDialogPrograss = new DialogPrograss(this);

        mWebView = findViewById(R.id.activity_new_url_web);
        webprogressBar = findViewById(R.id.webprogressBar);
        mLoadingLayout = findViewById(R.id.loading_layout);
//        mLoadingLayout.showLoading();
        mLoadingLayout.hideLoadingLayout();

        webprogressBar.setVisibility(View.VISIBLE);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100) {
                    webprogressBar.setVisibility(View.GONE);
                } else {
                    webprogressBar.setVisibility(View.VISIBLE);
                    webprogressBar.setProgress(i);
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
//                mLoadingLayout.hideLoadingLayout();
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

        String ua = webSetting.getUserAgentString();
        webSetting.setUserAgentString(ua + ";qukandian");

        setTitle(mLoadingText);
        mWebView.loadUrl(url);
        mWebView.addJavascriptInterface(new DialogShareInterface(this), "Share");

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            } else {
                getShareImg2();
            }
        } else {
            getShareImg2();
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

    /**
     * 1获取背景图
     * 2获取用户头像
     * 3获取用户id
     * 4获取二维码地址
     * 5合成海报
     *
     * @param type
     */
    @Override
    public void onShareListener(int type, String title, String desc, String tel) {
        switch (type) {
            case 0:
                MobclickAgent.onEvent(mContext, "04_06_01_wxyaoqing");
                if (UserManager.getInst().hadLogin()) {
                    sharedTransit(SHARE_MEDIA.WEIXIN);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(mContext);
                }
                break;
            case 1:
                MobclickAgent.onEvent(mContext, "04_06_02_pyqyaoqing");
                if (UserManager.getInst().hadLogin()) {
//                    if ("1".equals(UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getShareFriendType())) {
//                        sharedTransit(SHARE_MEDIA.WEIXIN_CIRCLE);
//                    } else {
//                        shareWechatUrlTransit(this, 1);
//                    }
                } else {
                    ActivityUtils.startToBeforeLogingActivity(mContext);
                }
                break;
            case 2:
                MobclickAgent.onEvent(mContext, "04_06_03_qqyaoqing");
                if (UserManager.getInst().hadLogin()) {
                    sharedTransit(SHARE_MEDIA.QQ);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(mContext);
                }
                break;
            case 3:
                MobclickAgent.onEvent(mContext, "04_06_05_qqzoomyaoqing");
                if (UserManager.getInst().hadLogin()) {
                    sharedTransit(SHARE_MEDIA.QZONE);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(mContext);
                }
                break;
            case 4:
                MobclickAgent.onEvent(mContext, "04_06_04_facetofaceyaoqing");
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToFace2FaceInviteActivity(mContext);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(mContext);
                }
                break;
            case 5:
                MobclickAgent.onEvent(mContext, "04_06_08_clickwxhuanxing");
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(desc + " " + APP_MARKET_URL)
                        .setCallback(mUMShareListener)
                        .share();
                break;
            case 6:
                MobclickAgent.onEvent(mContext, "04_06_08_clickpyqhuanxing");
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText(desc + " " + APP_MARKET_URL)
                        .setCallback(mUMShareListener)
                        .share();
                break;
            case 7:
                MobclickAgent.onEvent(mContext, "04_06_09_smsyaoqing");
                UMWeb umWeb = new UMWeb(APP_MARKET_URL);
                umWeb.setTitle(title);
                umWeb.setDescription(desc);
                umWeb.setThumb(new UMImage(mContext, R.mipmap.app_icon));
                new ShareAction(mActivity)
                        .setPlatform(SHARE_MEDIA.QQ)
                        .withMedia(umWeb)
                        .setCallback(mUMShareListener)
                        .share();
                break;
            case 8:
                MobclickAgent.onEvent(mContext, "04_06_10_smsyaoqing");
                String mSmsBody = desc + " " + APP_MARKET_URL;
                sendSMS(mSmsBody, tel);
                break;
        }
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
                UMImage sImg = new UMImage(PutongWebActivity.this, imgUrl);

                sImg.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                sImg.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                sImg.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

                UMWeb web = new UMWeb(url);
                web.setTitle(title);//标题

                UMImage umImage = new UMImage(PutongWebActivity.this, UserManager.mShareBean.getWechatIcon());
                umImage.setThumb(sImg);

                web.setThumb(umImage);  //缩略图
                web.setDescription(desc);//描述

                new ShareAction(PutongWebActivity.this).setPlatform(share_media).setCallback(mUMShareListener)
                        .withMedia(web)
                        .share();
            }
        });
        shareDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getShareImg();
                }
            }
        }
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void sendSMS(String smsBody, String tel) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("smsto:" + tel));
        sendIntent.putExtra("sms_body", smsBody);
        startActivity(sendIntent);
    }

    private void getShareImg2() {
        GlideUtils.circleAvatar(mContext, UserManager.getInst().getUserBeam().getAvatarUrl(), 50, 50, new GlideUtils.OnLoadImageListener() {
            @Override
            public void onSucess(Bitmap avatar, String url) {
                try {
                    mAvatar = avatar;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(Drawable errorDrawable) {

            }
        });
    }

    private void getShareImg() {
        mShareBitmaps.clear();
        new GetStadomainProtocol(this, new BaseModel.OnResultListener<String>() {
            @Override
            public void onResultListener(String response) {
                try {
                    mWebpageUrl = "http://" + response + "/" +
                            "?avatarUrl=" + URLEncoder.encode(UserManager.getInst().getUserBeam().getAvatarUrl(), "UTF-8") +
                            "&nickName=" + URLEncoder.encode(UserManager.getInst().getUserBeam().getNickName(), "UTF-8") +
                            "&userID=" + UserManager.getInst().getUserBeam().getId();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();

        if (UserManager.getInst().getmRuleBean().getActiveShoutuConfig() != null
                && UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getShareFrientPosterItems() != null) {
            if (UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getShareFrientPosterItems().size() > 0) {
                mShareBitmaps.add(null);
                String imgUrl001 = UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getShareFrientPosterItems().get(0).getImageLink();
                if (!TextUtils.isEmpty(imgUrl001)) {
                    GlideUtils.loadImage(mContext, imgUrl001, 720, 1280, new GlideUtils.OnLoadImageListener() {
                        @Override
                        public void onSucess(final Bitmap bitmap1, String url) {
                            GlideUtils.circleAvatar(mContext, UserManager.getInst().getUserBeam().getAvatarUrl(), 50, 50, new GlideUtils.OnLoadImageListener() {
                                @Override
                                public void onSucess(Bitmap avatar, String url) {
                                    try {
                                        File file = Draw281QR.drawQR(mContext, bitmap1, avatar, "QR21.jpg", mWebpageUrl);
                                        Bitmap tmpBitmap = BitmapFactory.decodeFile(file.getPath());
                                        if (mShareBitmaps.size() > 0) {
                                            mShareBitmaps.set(0, tmpBitmap);
                                        } else {
                                            mShareBitmaps.add(tmpBitmap);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        LogUtils.LogE(e.getMessage());
                                    }
                                }

                                @Override
                                public void onFail(Drawable errorDrawable) {

                                }
                            });

                        }

                        @Override
                        public void onFail(Drawable errorDrawable) {

                        }
                    });
                }

                for (int i = 1; i < UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getShareFrientPosterItems().size(); i++) {
                    final int index = i;
                    mShareBitmaps.add(null);
                    GlideUtils.loadImage(this, UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getShareFrientPosterItems().get(i).getImageLink(), new GlideUtils.OnLoadImageListener() {
                        @Override
                        public void onSucess(Bitmap bitmap, String url) {
                            mShareBitmaps.set(index, bitmap);
                        }

                        @Override
                        public void onFail(Drawable errorDrawable) {

                        }
                    });
                }
            }


        }
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
        String url = deeplink;
        if (UserManager.getInst().hadLogin()) {
            if (url.contains("http")) {
                ActivityUtils.startToPutongBrowserActivity(mContext, url);
            } else if (url.startsWith("xcxα")) {
                // 分享小程序
                OperateUtil.shareMiniAppMain(url, mActivity, mDialogPrograss);
            } else if (url.startsWith("activity")) {
                if (url.startsWith("activitys")) {
                    url = url.replace("activitys", "https");
                } else if (url.startsWith("activity")) {
                    url = url.replace("activity", "http");
                }
                ActivityUtils.startToPutongWebActivity(this, url);
            } else {
                if (url.contains("|")) {
                    String[] urlTmp = url.split("\\|");
                    if (urlTmp.length > 1) {
                        ActivityUtils.startToAssignActivity(mContext, urlTmp[0], Integer.valueOf(urlTmp[1]));
                    } else {
                        ActivityUtils.startToAssignActivity(mContext, url, -1);
                    }
                } else {
                    ActivityUtils.startToAssignActivity(mContext, url, -1);
                }
            }
        } else {
            ActivityUtils.startToBeforeLogingActivity(mContext);
        }

    }

    @Override
    public void onBackListener() {
        finish();
    }

    private UMShareListener mUMShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(PutongWebActivity.this, "正在打开应用");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(PutongWebActivity.this, "分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (throwable.toString().contains("没有安装应用")) {
                ToastUtils.showLongToast(PutongWebActivity.this, "没有安装应用");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.e("onCancel", "cancel");

        }
    };


    public void sharedTransit(final SHARE_MEDIA p) {
        if (UserManager.mBitmaps == null || UserManager.mBitmaps.size() <= 0) {
            new GetShareImgProtocol(mContext, new BaseModel.OnResultListener<ShareBean>() {
                @Override
                public void onResultListener(ShareBean response) {
                    UserManager.mShareBean = response;
                    if (response.getShareFrientPosterItems().size() == 0) {
                        sharedTransit2(p);
                        return;
                    }
                    for (int i = 0; i < response.getShareFrientPosterItems().size(); i++) {
                        GlideUtils.loadImage(mContext, response.getShareFrientPosterItems().get(i).getImageLink(), new GlideUtils.OnLoadImageListener() {
                            @Override
                            public void onSucess(Bitmap bitmap, String url) {
                                UserManager.mBitmaps.add(bitmap);
                                if (UserManager.mBitmaps.size() == 1) {
                                    sharedTransit2(p);
                                }
                            }

                            @Override
                            public void onFail(Drawable errorDrawable) {

                            }
                        });
                    }
                }

                @Override
                public void onFailureListener(int code, String error) {

                }
            }).postRequest();
        } else {
            sharedTransit2(p);
        }
    }

    public void sharedTransit2(final SHARE_MEDIA p) {
        if (mAvatar == null) {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GlideUtils.circleAvatar(mContext, UserManager.getInst().getUserBeam().getAvatarUrl(), 50, 50, new GlideUtils.OnLoadImageListener() {
                        @Override
                        public void onSucess(Bitmap avatar, String url) {
                            try {
                                mAvatar = avatar;
                                shared(p);
                            } catch (Exception e) {
                                e.printStackTrace();
                                LogUtils.LogE(e.getMessage());
                            }
                        }

                        @Override
                        public void onFail(Drawable errorDrawable) {

                        }
                    });
                }
            });
        } else {
            shared(p);
        }
    }

    public void shared(final SHARE_MEDIA p) {
        if (p == SHARE_MEDIA.WEIXIN) {
            new ThirdWeChatShare().shareShoutuWechatUrl(mContext, 0);
        } else if (p == SHARE_MEDIA.WEIXIN_CIRCLE) {
            new ThirdWeChatShare().shareShoutuWechatUrl(mContext, 1);
        } else if (p == SHARE_MEDIA.SMS) {
            ShareAction shareAction = new ShareAction(mActivity);
            shareAction.setPlatform(p).withText(UserManager.mShareBean.getShareFriendText())
                    .setDisplayList(p).share();
        } else if (p == SHARE_MEDIA.QQ) {
            new GetShoutuShareHostProtocol(mContext, new BaseModel.OnResultListener<String>() {
                @Override
                public void onResultListener(String response) {
                    MobclickAgent.onEvent(mContext, "SharedRecord");
//                    response = response.replace("shoutunormal/", "qwweb/shoutunormal/");
//                    response = response.replace("shoutunormaltest/", "qwweb/shoutunormaltest/");
                    UMImage mUMImage = new UMImage(mContext, R.mipmap.app_icon);
                    UMWeb umWeb = new UMWeb(response
                            + UserManager.getInst().getUserBeam().getId()
                            + "?source=h5_invitation_android_" + QuKanDianApplication.mUmen);
                    umWeb.setTitle(UserManager.mShareBean.getWechatTitle().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + ""));
                    umWeb.setDescription(UserManager.mShareBean.getWechatDesc().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + ""));
                    umWeb.setThumb(mUMImage);

                    new ShareAction(mActivity).setPlatform(SHARE_MEDIA.QQ)
                            .withMedia(umWeb).share();
                }

                @Override
                public void onFailureListener(int code, String error) {
                }
            }).postRequest();
        } else {
            if (mAvatar == null) return;
            if (UserManager.mBitmaps == null || UserManager.mBitmaps.size() <= 0) return;
            File file = Draw281QR.drawQR(mContext, UserManager.mBitmaps.get(0), mAvatar, "QR1.jpg", "http://"
                    + UserManager.getInst().getQukandianBean().getHost()
                    + "/web/quyou/agent/" + UserManager.getInst().getUserBeam().getId()
                    + "?source=h5_invitation_android_" + QuKanDianApplication.mUmen);
            UMImage mUMImage = new UMImage(mContext, file);
            ShareContent shareContent = new ShareContent();
            shareContent.mText = UserManager.mShareBean.getShareFriendText().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + "");
            shareContent.mMedia = mUMImage;
            new ShareAction(mActivity).setPlatform(p)
                    .setShareContent(shareContent)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            ToastUtils.showLongToast(mContext, "正在打开应用");
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            ToastUtils.showLongToast(mContext, "分享成功");
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            if (throwable.toString().contains("没有安装应用")) {
                                ToastUtils.showLongToast(mContext, "没有安装应用");
                            }
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            Log.e("shared", "onCancel");
                        }
                    }).share();
        }
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

}
