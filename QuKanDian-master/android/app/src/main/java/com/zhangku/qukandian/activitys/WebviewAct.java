package com.zhangku.qukandian.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.EventBusBean.LuckTurntableEvent;
import com.zhangku.qukandian.bean.ShareBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.ShareForWebView;
import com.zhangku.qukandian.interfaces.OnSharedListener;
import com.zhangku.qukandian.javascript.DialogShareInterface2;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetShareImgProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.Draw281QR;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.GsonUtil;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.PermissionHelper;
import com.zhangku.qukandian.utils.SetWebSettings;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ThirdWeChatShare;
import com.zhangku.qukandian.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 创建者          xuzhida
 * 创建日期        2018/10/9
 * 活动h5页面
 * (我的弹窗-收徒)
 */
public class WebviewAct extends BaseAct implements DialogShareInterface2.OnShareListener {
    private TextView titleTV;
    private ProgressBar webprogressBar;
    private WebView webview;
    private String url;
    private String title;
    private String APP_MARKET_URL = "";
    protected Bitmap mAvatar;
    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews() {
        APP_MARKET_URL = UserManager.getInst().getQukandianBean().getPakUrl();
        if (!UserManager.getInst().hadLogin()) {
            ActivityUtils.startToBeforeLogingActivity(this);
            finish();
            return;
        }
        EventBus.getDefault().register(this);
        if (null != getIntent().getExtras()) {
            url = getIntent().getExtras().getString("url");
            title = getIntent().getExtras().getString("title");
        }
        if (TextUtils.isEmpty(url)) {
            url = UserManager.mUrl;
        }
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

        webview = findViewById(R.id.webview);
        webprogressBar = findViewById(R.id.webprogressBar);
        titleTV = findViewById(R.id.titleTV);
        url = url +"&appflag=qksj";
        webview.loadUrl(url);
        SetWebSettings.setWebviewForActivity(this, webview, webprogressBar);

        webview.addJavascriptInterface(new DialogShareInterface2(WebviewAct.this), "Share");
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

        findViewById(R.id.titleCloseTV).setOnClickListener(this);
        findViewById(R.id.titleBackIV).setOnClickListener(this);
        View StatusBar = findViewById(R.id.StatusBar);
        setStatusView(this, StatusBar);
        if (!TextUtils.isEmpty(title)) {
            titleTV.setText(title);
        }

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

    private void getShareImg2() {
        GlideUtils.circleAvatar(this, UserManager.getInst().getUserBeam().getAvatarUrl(), 50, 50, new GlideUtils.OnLoadImageListener() {
            @Override
            public void onSucess(Bitmap avatar, String url) {
                try {
                    mAvatar = avatar;
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
    public void onShareListener(int type, String title, String desc, String tel) {
        switch (type) {
            case 8:
                MobclickAgent.onEvent(this, "04_06_10_smsyaoqing");
                String mSmsBody = desc;
                sendSMS(mSmsBody, tel);
                MobclickAgent.onEvent(this, "295-yaoqingqinyou");
                break;
        }
    }

    private void sendSMS(String smsBody, String tel) {
        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("smsto:" + tel));
        sendIntent.putExtra("sms_body", smsBody);
        startActivity(sendIntent);
    }

    @Override
    public void onShareListener(int type, String shareUrl, String bitmapUrl, String title, String content) {
        Map<String, String> map = new HashMap<>();
        switch (type) {
            case 0:
                map.put("type", "微信");
                if(TextUtils.isEmpty(shareUrl)){
                    sharedTransit(SHARE_MEDIA.WEIXIN);
                }else {
                    share(SHARE_MEDIA.WEIXIN, shareUrl, bitmapUrl, title, content);
                }
                break;
            case 1:
                map.put("type", "朋友圈");
                if(TextUtils.isEmpty(shareUrl)){
                    sharedTransit(SHARE_MEDIA.WEIXIN_CIRCLE);
                }else {
                    share(SHARE_MEDIA.WEIXIN_CIRCLE, shareUrl, bitmapUrl, title, content);
                }
                break;
            case 2:
                map.put("type", "QQ");
                if(TextUtils.isEmpty(shareUrl)){
                    sharedTransit(SHARE_MEDIA.QQ);
                }else {
                    share(SHARE_MEDIA.QQ, shareUrl, bitmapUrl, title, content);
                }
                break;
            case 3:
                map.put("type", "QQ空间");
                if(TextUtils.isEmpty(shareUrl)){
                    sharedTransit(SHARE_MEDIA.QZONE);
                }else {
                    share(SHARE_MEDIA.QZONE, shareUrl, bitmapUrl, title, content);
                }
                break;
            case 4:
                map.put("type", "面对面邀请");
//                Bundle extras = new Bundle();
//                extras.putString(Constants.QRCODE_CONTENT, shareUrl);
//                ActivityUtils.startToChbyxjFaceToFaceInviteActivity(this, extras);
                ActivityUtils.startToFace2FaceInviteActivity(this);
                break;
            case 8:
                map.put("type", "短信");
                String mSmsBody = content + " " + UserManager.getInst().getQukandianBean().getPakUrl();
                share(SHARE_MEDIA.SMS, shareUrl, bitmapUrl, title, mSmsBody);
                break;
        }
        MobclickAgent.onEvent(this, "activityShare", map);
    }

    public void sharedTransit(final SHARE_MEDIA p) {
        if (UserManager.mBitmaps == null || UserManager.mBitmaps.size() <= 0) {
            new GetShareImgProtocol(this, new BaseModel.OnResultListener<ShareBean>() {
                @Override
                public void onResultListener(ShareBean response) {
                    UserManager.mShareBean = response;
                    if (response.getShareFrientPosterItems().size() == 0) {
                        sharedTransit2(p);
                        return;
                    }
                    for (int i = 0; i < response.getShareFrientPosterItems().size(); i++) {
                        // TODO: 2019/6/4 替换 上线前注释 域名替换
                        GlideUtils.loadImage(WebviewAct.this, response.getShareFrientPosterItems().get(i).getImageLink().replace("http://cdn.qu.fi.pqmnz.com","http://static.funnykandian.com"), new GlideUtils.OnLoadImageListener() {
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
            WebviewAct.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GlideUtils.circleAvatar(WebviewAct.this, UserManager.getInst().getUserBeam().getAvatarUrl(), 50, 50, new GlideUtils.OnLoadImageListener() {
                        @Override
                        public void onSucess(Bitmap avatar, String url) {
                            try {
                                mAvatar = avatar;
                                shared(p);
                            } catch (Exception e) {
                                e.printStackTrace();
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
            new ThirdWeChatShare().shareShoutuWechatUrlByUmeng(UserManager.getInst().getmRuleBean().getShoutuPosterConfig().getWechatType(), this, SHARE_MEDIA.WEIXIN, mUMShareListener);
        } else if (p == SHARE_MEDIA.WEIXIN_CIRCLE) {
            new ThirdWeChatShare().shareShoutuWechatUrlByUmeng(UserManager.getInst().getmRuleBean().getShoutuPosterConfig().getShareFriendType(), this, SHARE_MEDIA.WEIXIN_CIRCLE, mUMShareListener);
        } else if (p == SHARE_MEDIA.SMS) {
            ShareAction shareAction = new ShareAction(WebviewAct.this);
            shareAction.setPlatform(p).withText(UserManager.mShareBean.getShareFriendText())
                    .setDisplayList(p).share();
        } else if (p == SHARE_MEDIA.QQ) {
            new ThirdWeChatShare().shareShoutuWechatUrlByUmeng(UserManager.getInst().getmRuleBean().getShoutuPosterConfig().getQqType(), this, SHARE_MEDIA.QQ, mUMShareListener);
        } else {
            if (mAvatar == null) return;
            if (UserManager.mBitmaps == null || UserManager.mBitmaps.size() <= 0) return;
            File file = Draw281QR.drawQR(WebviewAct.this, UserManager.mBitmaps.get(0), mAvatar, "QR1.jpg", "http://"
                    + UserManager.getInst().getQukandianBean().getHost()
                    + "/web/quyou/agent/" + UserManager.getInst().getUserBeam().getId()
                    + "?source=h5_invitation_android_" + QuKanDianApplication.mUmen);
            UMImage mUMImage = new UMImage(WebviewAct.this, file);
            ShareContent shareContent = new ShareContent();
            shareContent.mText = UserManager.mShareBean.getShareFriendText().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + "");
            shareContent.mMedia = mUMImage;
            new ShareAction(WebviewAct.this).setPlatform(p)
                    .setShareContent(shareContent)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            ToastUtils.showLongToast(WebviewAct.this, "正在打开应用");
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            ToastUtils.showLongToast(WebviewAct.this, "分享成功");
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            if (throwable.toString().contains("没有安装应用")) {
                                ToastUtils.showLongToast(WebviewAct.this, "没有安装应用");
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
    public void onShareDialog(final String title, final String desc, final String imgUrl, final String url) {
        ShareForWebView shareDialog = new ShareForWebView(this);
        shareDialog.setOnSharedListener(new OnSharedListener() {
            @Override
            public void onSharedListener(DetailsBean mDetailsBean, SHARE_MEDIA share_media) {
                UMImage sImg = new UMImage(WebviewAct.this, imgUrl);

                sImg.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                sImg.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                sImg.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

                UMWeb web = new UMWeb(url);
                web.setTitle(title);//标题

                UMImage umImage = new UMImage(WebviewAct.this, UserManager.mShareBean.getWechatIcon());
                umImage.setThumb(sImg);

                web.setThumb(umImage);  //缩略图
                web.setDescription(desc);//描述

                if (TextUtils.isEmpty(title)) {
//                    new ShareAction(this)
//                            .setPlatform(shareType)
//                            .setCallback(mUMShareListener)
//                            .withMedia(umImage)
//                            .share();
                    new ShareAction(WebviewAct.this)
                            .setPlatform(share_media)
                            .setCallback(mUMShareListener)
                            .withMedia(umImage)
                            .share();
                } else {
                    new ShareAction(WebviewAct.this)
                            .setPlatform(share_media)
                            .setCallback(mUMShareListener)
                            .withMedia(web)
                            .share();
                }
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
            case 5://我的
                ActivityUtils.startToMainActivity(this, 3, 0);
                break;
            case 6://首页热搜
                ActivityUtils.startToMainActivity(this, 0, 10000000);
                break;
        }

    }

    @Override
    public void onToAnywhereListener(String deeplink) {

    }

    @Override
    public void onBackListener() {
        finish();
    }

    private UMShareListener mUMShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(WebviewAct.this, "正在打开应用");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(WebviewAct.this, "分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (throwable.toString().contains("没有安装应用")) {
                // ToastUtils.showLongToast(WebviewAct.this, "没有安装应用");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
        }
    };

    private void share(SHARE_MEDIA shareType, String shareUrl, String bitmapUrl, String title, String content) {
        ToastUtils.showShortToast(this, "正在打开应用");
        MobclickAgent.onEvent(this, "SharedRecord");

        UMImage sImg;
        if (!TextUtils.isEmpty(bitmapUrl)) {
            // TODO: 2019/6/5  测试地址 图片会挂的域名，替换域名
            sImg = new UMImage(this, bitmapUrl.replace("http://cdn.qu.fi.pqmnz.com","http://static.funnykandian.com"));
        } else {
            sImg = new UMImage(this, R.mipmap.app_icon);
        }
        sImg.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        sImg.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
        sImg.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
        UMImage umImage;
        if (!TextUtils.isEmpty(bitmapUrl)) {
            umImage = new UMImage(this, bitmapUrl);
        } else {
            umImage = new UMImage(this, R.mipmap.app_icon);
        }
        umImage.setThumb(sImg);
        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(title);//标题

        web.setThumb(umImage);  //缩略图
        web.setDescription(content);//描述
        if (TextUtils.isEmpty(title)) {
            new ShareAction(this)
                    .setPlatform(shareType)
                    .setCallback(mUMShareListener)
                    .withMedia(umImage)
                    .share();
        } else {
            new ShareAction(this)
                    .setPlatform(shareType)
                    .setCallback(mUMShareListener)
                    .withMedia(web)
                    .share();
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
        webview.loadUrl(javascriptStr);
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

    private void putGoldToWeb(int gold){
        String javascriptStr = "javascript:AdRedSuccess(" + gold + ")";
        webview.loadUrl(javascriptStr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(LuckTurntableEvent event) {
        if (event.getGold() > 0) {
            putGoldToWeb(event.getGold());
        }
    }

}
