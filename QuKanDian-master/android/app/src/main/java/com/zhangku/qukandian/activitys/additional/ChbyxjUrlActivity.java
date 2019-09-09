package com.zhangku.qukandian.activitys.additional;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.wwangliw.wxshare.share.WXShare;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseNoActionBarActivity;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.ShareForWebView;
import com.zhangku.qukandian.interfaces.OnSharedListener;
import com.zhangku.qukandian.javascript.DialogShareInterface;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetStadomainProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.Draw281QR;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.GsonUtil;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.PermissionHelper;
import com.zhangku.qukandian.utils.SaveBitmapToFile;
import com.zhangku.qukandian.utils.SetWebSettings;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.widght.LoadingLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by qiujianwen on 2018-4-19 14:46:39.
 * 拆红包，赢现金
 */
@Deprecated
public class ChbyxjUrlActivity extends BaseNoActionBarActivity implements DialogShareInterface.OnShareListener {
    private String pageTitle = "";
    private String url;
    private WebView mWebView;
    private Bitmap mShareBitmapIcon;
    private String mWebpageUrl;
    private LoadingLayout mLoadingLayout;
    public static List<Bitmap> mShareBitmaps = new ArrayList<>();
    private Context mContext;
    private static String APP_MARKET_URL = "http://dwz.cn/78a1KZ";

    @Override
    protected void initViews() {
        if (!UserManager.getInst().hadLogin()) {
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

        mContext = this;

        findViewById(R.id.url_gray_actionbar_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.backIV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
            }
        });
        final TextView titleTV = findViewById(R.id.url_gray_actionbar_title);
        mWebView = findViewById(R.id.activity_new_url_web);
        mLoadingLayout = findViewById(R.id.loading_layout);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mLoadingLayout.showLoading();
        mLoadingLayout.hideLoadingLayout();
        ProgressBar webprogressBar = findViewById(R.id.webprogressBar);
        SetWebSettings.setWebviewForActivity(this, mWebView, webprogressBar);
        mWebView.addJavascriptInterface(new DialogShareInterface(this), "Share");
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
                mLoadingLayout.hideLoadingLayout();
                mLoadingLayout.setVisibility(View.GONE);
                String title = webView.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    titleTV.setText(title);
                    if ("每日一阅".equals(title)) {//埋点
                        MobclickAgent.onEvent(ChbyxjUrlActivity.this, "meiriyiyue1");
                    }
                }
            }
        });
        mWebView.loadUrl(url);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            } else {
                getShareImg();
            }
        } else {
            getShareImg();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_webview_with_title;
    }

    @Override
    public String setPagerName() {
        return pageTitle;
    }

    @Override
    public void onShareDialog(final String title, final String desc, final String imgUrl, final String url) {
        ShareForWebView shareDialog = new ShareForWebView(this);
        shareDialog.setOnSharedListener(new OnSharedListener() {
            @Override
            public void onSharedListener(DetailsBean mDetailsBean, SHARE_MEDIA share_media) {
                UMImage sImg = new UMImage(ChbyxjUrlActivity.this, imgUrl);

                sImg.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                sImg.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                sImg.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

                UMWeb web = new UMWeb(url);
                web.setTitle(title);//标题

                UMImage umImage = new UMImage(ChbyxjUrlActivity.this, UserManager.mShareBean.getWechatIcon());
                umImage.setThumb(sImg);

                web.setThumb(umImage);  //缩略图
                web.setDescription(desc);//描述

                new ShareAction(ChbyxjUrlActivity.this).setPlatform(share_media).setCallback(mUMShareListener)
                        .withMedia(web)
                        .share();
                MobclickAgent.onEvent(ChbyxjUrlActivity.this, "meiriyiyueShare1");
            }
        });
        shareDialog.show();
    }

    /**
     * 1获取背景图
     * 2获取用户头像
     * 3获取用户id
     * 4获取二维码地址
     * 5合成海报
     */
    public void onShareListener(int type, String title, String desc, String tel) {
        Map<String, String> map = new HashMap<>();

        switch (type) {
            case 0:
                map.put("type", "微信");
                if ("1".equals(UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatType())) {////1 海报分享 0 链接分享
                    shareWechatTransit(0);
                } else {
                    shareWechatUrlTransit(this, 0);
                }
                break;
            case 1:
                map.put("type", "朋友圈");
                if ("1".equals(UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getShareFriendType())) {
                    shareCircle();
                } else {
                    shareWechatUrlTransit(this, 1);
                }
                break;
            case 2:
                map.put("type", "QQ");
                if ("1".equals(UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getQqType())) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.QQ)
                            .withMedia(new UMImage(this, mShareBitmapIcon))
                            .setCallback(mUMShareListener).share();
                } else {
                    shareOther(SHARE_MEDIA.QQ);
                }
                break;
            case 3:
                map.put("type", "QQ空间");
                if ("1".equals(UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getQqSpaceType())) {
                    Bitmap tmpBitmap = mShareBitmapIcon;
                    if (mShareBitmaps.size() > 0) {
                        tmpBitmap = mShareBitmaps.get(0);
                    }
                    new ShareAction(this).setPlatform(SHARE_MEDIA.QZONE)
                            .withMedia(new UMImage(this, tmpBitmap))
                            .setCallback(mUMShareListener).share();
                } else {
                    shareOther(SHARE_MEDIA.QZONE);
                }
                break;
            case 4:
                map.put("type", "面对面邀请");
                if (UserManager.getInst().hadLogin()) {
                    Bundle extras = new Bundle();
                    extras.putString(Constants.QRCODE_CONTENT, mWebpageUrl);
                    ActivityUtils.startToChbyxjFaceToFaceInviteActivity(this, extras);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(this);
                }
                break;
            case 5:
                map.put("type", "微信");
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(desc + " " + UserManager.getInst().getQukandianBean().getPakUrl())
                        .setCallback(mUMShareListener)
                        .share();

                break;
            case 6:
                map.put("type", "朋友圈");
                new ShareAction(this)
                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText(desc + " " + UserManager.getInst().getQukandianBean().getPakUrl())
                        .setCallback(mUMShareListener)
                        .share();
                break;
            case 7:
                map.put("type", "QQ");
                if (mShareBitmapIcon != null) {
                    UMImage mUMImage = new UMImage(this, mShareBitmapIcon);
                    shareTextOther(this, SHARE_MEDIA.QQ, mUMImage, desc, desc);
                } else {
                    final String fDesc = desc;
                    GlideUtils.loadImage(this, UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatIcon(), new GlideUtils.OnLoadImageListener() {
                        @Override
                        public void onSucess(Bitmap bitmap, String url) {
                            mShareBitmapIcon = bitmap;
                            UMImage mUMImage = new UMImage(mContext, mShareBitmapIcon);
                            shareTextOther((Activity) mContext, SHARE_MEDIA.QQ, mUMImage, fDesc, fDesc);
                        }

                        @Override
                        public void onFail(Drawable errorDrawable) {
                            showShareFail(mContext);
                        }
                    });
                }
//                new ShareAction(this)
//                        .setPlatform(SHARE_MEDIA.QQ)
//                        .withText(desc + " " + APP_MARKET_URL)
//                        .setCallback(mUMShareListener)
//                        .share();
                break;
            case 8:
                map.put("type", "短信");
//                shareTextOther(this, SHARE_MEDIA.WEIXIN_CIRCLE, null, title, desc);
//                new ShareAction(this)
//                        .setPlatform(SHARE_MEDIA.SMS)
//                        .withText(desc + " " + APP_MARKET_URL)
//                        .setCallback(mUMShareListener)
//                        .share();
                String mSmsBody = desc + " " + UserManager.getInst().getQukandianBean().getPakUrl();
                sendSMS(mSmsBody, tel);
                break;
        }
        MobclickAgent.onEvent(this, "activityShare", map);
    }

    @Override
    public void onShareListener(int type, String shareUrl, String bitmapUrl, String title, String content) {

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

        GlideUtils.loadImage(this, UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatIcon(), new GlideUtils.OnLoadImageListener() {
            @Override
            public void onSucess(Bitmap bitmap, String url) {
                mShareBitmapIcon = bitmap;
            }

            @Override
            public void onFail(Drawable errorDrawable) {

            }
        });

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

    private void shareCircle() {
        ToastUtils.showLongToast(this, "正在打开应用");
        ArrayList<Uri> uris = new ArrayList<>();
        if (mShareBitmaps.size() > 0) {
            for (int i = 0; i < mShareBitmaps.size(); i++) {
                Bitmap tmpBitmap = mShareBitmaps.get(i);
                if (tmpBitmap != null) {
                    Uri uri2 = Uri.fromFile(SaveBitmapToFile.save(this, tmpBitmap, "share_" + i));
                    uris.add(uri2);
                }
            }
        } else {
            ToastUtils.showLongToast(this, "图片加载中，稍后再试");
            return;
//            Uri uri2 = Uri.fromFile(SaveBitmapToFile.save(this, mShareBitmapIcon, "share"));
//            uris.add(uri2);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"); // 朋友圈
        intent.setComponent(comp); // 不添加就是所有可分享的都出现
        intent.setAction(Intent.ACTION_SEND_MULTIPLE); // 传多张图片
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*"); // 可传图片
        intent.putExtra("Kdescription", UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getShareFriendText().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + ""));// 标题
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris); // 传多张图片
        try {
            startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showLongToast(this, "没有安装应用");
        }
    }

    private void shareOther(SHARE_MEDIA qq) {
        UMImage mUMImage = null;
        if (mShareBitmapIcon != null) {
            mUMImage = new UMImage(this, mShareBitmapIcon);
        } else {
            mUMImage = new UMImage(this, R.mipmap.app_icon);
        }
        if (mUMImage == null) {
            ToastUtils.showLongToast(this, "分享失败，稍等片刻");
        }
        UMWeb umWeb = new UMWeb(mWebpageUrl);
        umWeb.setTitle(UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatTitle().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + ""));
        umWeb.setDescription(UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatDesc().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + ""));
        umWeb.setThumb(mUMImage);

        new ShareAction(this).setPlatform(qq)
                .withMedia(umWeb).share();
    }

    private void shareTextOther(Activity activity, SHARE_MEDIA sm, UMImage uMImage, String title, String desc) {
        UMWeb umWeb = new UMWeb(UserManager.getInst().getQukandianBean().getPakUrl());
        if (!TextUtils.isEmpty(title)) {
            umWeb.setTitle(title);
        }
        if (!TextUtils.isEmpty(desc)) {
            umWeb.setDescription(desc);
        }
        if (uMImage != null) {
            umWeb.setThumb(uMImage);
        }
        new ShareAction(activity).setPlatform(sm)
                .withMedia(umWeb)
                .setCallback(mUMShareListener)
                .share();
    }

    public void shareWechatUrlTransit(final Context context, final int flag) {
        if (mShareBitmapIcon != null) {
            shareWechatUrl(context, flag);
        } else {
            GlideUtils.loadImage(this, UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatIcon(), new GlideUtils.OnLoadImageListener() {
                @Override
                public void onSucess(Bitmap bitmap, String url) {
                    mShareBitmapIcon = bitmap;
                    if (TextUtils.isEmpty(mWebpageUrl)) {
                        new GetStadomainProtocol(context, new BaseModel.OnResultListener<String>() {
                            @Override
                            public void onResultListener(String response) {
                                try {
                                    mWebpageUrl = "http://" + response + "/" +
                                            "?avatarUrl=" + URLEncoder.encode(UserManager.getInst().getUserBeam().getAvatarUrl(), "UTF-8") +
                                            "&nickName=" + URLEncoder.encode(UserManager.getInst().getUserBeam().getNickName(), "UTF-8") +
                                            "&userID=" + UserManager.getInst().getUserBeam().getId();
                                    shareWechatUrl(context, flag);
                                } catch (UnsupportedEncodingException e) {
                                    showShareFail(context);
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailureListener(int code, String error) {
                                showShareFail(context);
                            }
                        }).postRequest();
                    } else {
                        showShareFail(context);
                    }
                }

                @Override
                public void onFail(Drawable errorDrawable) {
                    showShareFail(context);
                }
            });
        }
    }

    public void showShareFail(Context context) {
        ToastUtils.showLongToast(this, "图片加载中，稍后再试");
    }

    public void shareWechatUrl(final Context context, final int flag) {
        ToastUtils.showShortToast(context, "正在打开应用");
        MobclickAgent.onEvent(context, "SharedRecord");
        String title = UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatTitle().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + "");
        String description = UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatDesc().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + "");

        UMImage sImg;
        if (UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatIcon() != null) {
            sImg = new UMImage(mContext, UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatIcon());
        } else {
            sImg = new UMImage(mContext, R.mipmap.app_icon);
        }
        sImg.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        sImg.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
        sImg.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
        UMImage umImage;
        if (UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatIcon() != null) {
            umImage = new UMImage(mContext, UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getWechatIcon());
        } else {
            umImage = new UMImage(mContext, R.mipmap.app_icon);
        }
        umImage.setThumb(sImg);
        UMWeb web = new UMWeb(mWebpageUrl);
        web.setTitle(title);//标题

        web.setThumb(umImage);  //缩略图
        web.setDescription(description);//描述

        new ShareAction((Activity) mContext)
                .setPlatform(flag == 0 ? SHARE_MEDIA.WEIXIN : SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(null)
                .withMedia(web)
                .share();
    }

    private void shareWechatTransit(final int flag) {
        if (mShareBitmaps.size() > 0 && mShareBitmaps.get(0) != null) {
            shareWechat(flag);
        } else {
            ToastUtils.showLongToast(this, "图片加载中，稍后再试");
        }
    }

    private void shareWechat(int flag) {
        ToastUtils.showShortToast(this, "正在打开应用");
        WXMediaMessage msg = new WXMediaMessage();

        Bitmap bitmap9 = null;
        if (mShareBitmaps.size() > 0 && mShareBitmaps.get(0) != null) {
            bitmap9 = compress(mShareBitmaps.get(0));
        } else {
            ToastUtils.showLongToast(this, "图片加载中，稍后再试");
            return;
        }
        WXImageObject imageObject = new WXImageObject(bitmap9);
        msg.title = UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getShareFriendText().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + "");
        msg.mediaObject = imageObject;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = Math.random() + "";
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
                : SendMessageToWX.Req.WXSceneTimeline;

        WXShare.share(req, new WeakReference<Activity>(this),
                new WXShare.OnShareListener() {
                    @Override
                    public void onSuccess() {
                        Log.e("code", "onSuccess");
                    }

                    @Override
                    public void onFailure(int code) {// 10001：参数错误，10002：请求安装QQ，UC浏览器或者QQ浏览器，10003：未知错误
                        Log.e("code", code + "");
                        if (code == 1003) {
                            ToastUtils.showLongToast(ChbyxjUrlActivity.this, "没有安装应用");
                        }
                    }
                });
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

    }

    @Override
    public void onBackListener() {
        finish();
    }

    private UMShareListener mUMShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(ChbyxjUrlActivity.this, "正在打开应用");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(ChbyxjUrlActivity.this, "分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (throwable.toString().contains("没有安装应用")) {
                ToastUtils.showLongToast(ChbyxjUrlActivity.this, "没有安装应用");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.e("onCancel", "cancel");

        }
    };

    private Bitmap compress(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (bitmap.isRecycled()) {
            bitmap.recycle();
        }
        Bitmap bitmapByte = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmapByte;
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
}
