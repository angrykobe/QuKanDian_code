package com.zhangku.qukandian.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.wwangliw.wxshare.share.WXShare;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.NewShareBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetShareUrlProtocol;
import com.zhangku.qukandian.protocol.GetShoutuShareHostProtocol;
import com.zhangku.qukandian.protocol.GetUserShareProtocol;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by yuzuoning on 2017/5/24.
 */

public class ThirdWeChatShare {
    private GetShareUrlProtocol mGetShareUrlProtocol;

    public void share(final Context context, File file, int flag) {
        ToastUtils.showShortToast(context, "正在打开应用");
        WXMediaMessage msg = new WXMediaMessage();

        Bitmap temp = BitmapFactory.decodeFile(file.getPath());
        Bitmap bitmap9 = compress(temp);
        WXImageObject imageObject = new WXImageObject(bitmap9);
        msg.mediaObject = imageObject;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = Math.random() + "";
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
                : SendMessageToWX.Req.WXSceneTimeline;

        WXShare.share(req, new WeakReference<Activity>((Activity) context),
                new WXShare.OnShareListener() {
                    @Override
                    public void onSuccess() {
                        Log.e("code", "onSuccess");
                    }

                    @Override
                    public void onFailure(int code) {// 10001：参数错误，10002：请求安装QQ，UC浏览器或者QQ浏览器，10003：未知错误
                        Log.e("code", code + "");
                        if (code == 1003) {
                            ToastUtils.showLongToast(context, "没有安装应用");
                        }
                    }
                });
    }

    private GetUserShareProtocol mGetUserShareProtocol;

    public void shareWechatUrl(final Context context, final int flag) {
        if (null == mGetUserShareProtocol) {
            mGetUserShareProtocol = new GetUserShareProtocol(context, new BaseModel.OnResultListener<String>() {
                @Override
                public void onResultListener(final String response) {
                    ToastUtils.showShortToast(context, "正在打开应用");
                    GlideUtils.loadImage(context, UserManager.mShareBean.getWechatIcon(), new GlideUtils.OnLoadImageListener() {
                        @Override
                        public void onSucess(Bitmap bitmap, String url) {
                            MobclickAgent.onEvent(context, "SharedRecord");
                            WXWebpageObject webpageObject = new WXWebpageObject();
                            webpageObject.webpageUrl = "http://"
                                    + UserManager.getInst().getQukandianBean().getHost()
                                    + "/web/quyou/agent/" + UserManager.getInst().getUserBeam().getId()
                                    + "?source=h5_invitation_android_" + QuKanDianApplication.mUmen + "&shareId=" + response +
                                    "&missonTypeName=" + Constants.SHARED_INVITE_SUTDENT;
                            WXMediaMessage msg = new WXMediaMessage(webpageObject);
                            msg.title = UserManager.mShareBean.getWechatTitle().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + "");
                            msg.description = UserManager.mShareBean.getWechatDesc().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + "");
                            msg.setThumbImage(bitmap);

                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = Math.random() + "";
                            req.message = msg;
                            req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
                                    : SendMessageToWX.Req.WXSceneTimeline;

                            WXShare.share(req, new WeakReference<Activity>((Activity) context),
                                    new WXShare.OnShareListener() {
                                        @Override
                                        public void onSuccess() {
                                            Log.e("code", "onSuccess");
                                        }

                                        @Override
                                        public void onFailure(int code) {// 10001：参数错误，10002：请求安装QQ，UC浏览器或者QQ浏览器，10003：未知错误
                                            Log.e("code", code + "");
                                            if (code == 1003) {
                                                ToastUtils.showLongToast(context, "没有安装应用");
                                            }
                                        }
                                    });
                        }

                        @Override
                        public void onFail(Drawable errorDrawable) {

                        }
                    });
                    mGetUserShareProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetUserShareProtocol = null;
                }
            });
            mGetUserShareProtocol.postRequest();
        }
    }

    private GetShoutuShareHostProtocol getShoutuShareHostProtocol;

    public void shareShoutuWechatUrl(final Context context, final int flag) {
        if (getShoutuShareHostProtocol == null) {
            getShoutuShareHostProtocol = new GetShoutuShareHostProtocol(context, new BaseModel.OnResultListener<String>() {
                @Override
                public void onResultListener(final String shareHost) {
                    ToastUtils.showShortToast(context, "正在打开应用");
                    GlideUtils.loadImage(context, UserManager.mShareBean.getWechatIcon(), new GlideUtils.OnLoadImageListener() {
                        @Override
                        public void onSucess(Bitmap bitmap, String url) {
                            MobclickAgent.onEvent(context, "SharedRecord");
                            WXWebpageObject webpageObject = new WXWebpageObject();

//                            webpageObject.webpageUrl = shareHost + "?inviterId="
//                                    + UserManager.getInst().getUserBeam().getId()
//                                    + "&source=h5_invitation_android_" + QuKanDianApplication.mUmen;

                            String unBase64String = "inviterId="
                                    + UserManager.getInst().getUserBeam().getId()
                                    + "&source=h5_invitation_android_" + QuKanDianApplication.mUmen;

                            webpageObject.webpageUrl = shareHost +"?"+ CommonHelper.encodeBase64(unBase64String);

                            WXMediaMessage msg = new WXMediaMessage(webpageObject);
                            msg.title = UserManager.mShareBean.getWechatTitle().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + "");
                            msg.description = UserManager.mShareBean.getWechatDesc().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + "");
                            msg.setThumbImage(bitmap);

                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = Math.random() + "";
                            req.message = msg;
                            req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
                                    : SendMessageToWX.Req.WXSceneTimeline;

                            WXShare.share(req, new WeakReference<Activity>((Activity) context),
                                    new WXShare.OnShareListener() {
                                        @Override
                                        public void onSuccess() {
                                            getShoutuShareHostProtocol = null;
                                        }

                                        @Override
                                        public void onFailure(int code) {// 10001：参数错误，10002：请求安装QQ，UC浏览器或者QQ浏览器，10003：未知错误
                                            if (code == 1003) {
                                                ToastUtils.showLongToast(context, "没有安装应用");
                                            }
                                            getShoutuShareHostProtocol = null;
                                        }
                                    });
                        }

                        @Override
                        public void onFail(Drawable errorDrawable) {
                            getShoutuShareHostProtocol = null;
                        }
                    });
                }

                @Override
                public void onFailureListener(int code, String error) {
                    getShoutuShareHostProtocol = null;
                }
            });
            getShoutuShareHostProtocol.postRequest();
        }
    }


    /**
     * umeng分享
     * @param context
     * @param shareType
     * @param umShareListener
     */
    public void shareShoutuWechatUrlByUmeng(final int shareFriendType, final Activity context, final SHARE_MEDIA shareType, final UMShareListener umShareListener) {
        if (getShoutuShareHostProtocol == null) {
            getShoutuShareHostProtocol = new GetShoutuShareHostProtocol(context, new BaseModel.OnResultListener<String>() {
                @Override
                public void onResultListener(final String shareHost) {
//                    final String shareUrl = shareHost + "?inviterId="
//                            + UserManager.getInst().getUserBeam().getId()
//                            + "&source=h5_invitation_android_" + QuKanDianApplication.mUmen;
                    String unBase64String = "inviterId="
                            + UserManager.getInst().getUserBeam().getId()
                            + "&source=h5_invitation_android_" + QuKanDianApplication.mUmen;

                    final String shareUrl = shareHost +"?"+ CommonHelper.encodeBase64(unBase64String);


                    if (shareFriendType == 1) {//海报分享
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(context)
                                        .asBitmap()
                                        .load(UserManager.getInst().getmRuleBean().getActiveShoutuConfig().getShareFrientPosterItems().get(0).getImageLink())
                                        .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> glideAnimation) {
                                        File file = Draw274QR.drawQR(context, bitmap, "QR2.jpg", shareUrl);

                                        UMImage sImg = new UMImage(context, file);

                                        sImg.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                                        sImg.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                                        sImg.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
                                        UMImage umImage = new UMImage(context, file);
                                        umImage.setThumb(sImg);

                                        new ShareAction(context)
                                                .setPlatform(shareType)
                                                .setCallback(umShareListener)
                                                .withMedia(umImage)
                                                .share();
                                    }


                                });

                            }
                        });
                    } else {//链接分享
                        UMImage sImg;
                        if (UserManager.mShareBean.getWechatIcon() == null || "".equals(UserManager.mShareBean.getWechatIcon()))
                            sImg = new UMImage(context, R.mipmap.app_icon);
                        else
                            sImg = new UMImage(context, UserManager.mShareBean.getWechatIcon());

                        sImg.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                        sImg.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                        sImg.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

                        UMWeb web = new UMWeb(shareUrl);
                        web.setTitle(UserManager.mShareBean.getWechatTitle().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + ""));//标题

                        UMImage umImage = new UMImage(context, UserManager.mShareBean.getWechatIcon());
                        umImage.setThumb(sImg);

                        web.setThumb(umImage);  //缩略图
                        web.setDescription(UserManager.mShareBean.getWechatDesc().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + ""));//描述

                        new ShareAction(context).setPlatform(shareType).setCallback(umShareListener)
                                .withMedia(web)
                                .share();
                    }

                }

                @Override
                public void onFailureListener(int code, String error) {
                    getShoutuShareHostProtocol = null;
                }
            });
            getShoutuShareHostProtocol.postRequest();
        }
    }

    public void weChatShare(int type, final Activity activity, final DetailsBean mDetailsBean, final Bitmap bitmap, final int flag) {
        ToastUtils.showLongToast(activity, "正在打开应用");
        if (isShow() && type == Constants.TYPE_NEW) {
            if (null == mGetShareUrlProtocol) {
                mGetShareUrlProtocol = new GetShareUrlProtocol(activity, UserManager.getInst().getUserBeam().getId(), mDetailsBean.getNewId(), new BaseModel.OnResultListener<NewShareBean>() {
                    @Override
                    public void onResultListener(NewShareBean response) {
                        WXWebpageObject webpageObject = new WXWebpageObject();
                        webpageObject.webpageUrl = "http://" + response.getDomain() + "/index.html"
                                + "?key=bs" + AES.encryUserId(UserManager.getInst().getUserBeam().getId())
                                + "%foot" + AES.encryPostId(mDetailsBean.getNewId())
                                + "%pp" + AES.encryLog(response.getSharearticlelog());
                        Log.e("webpageUrl", webpageObject.webpageUrl + "/" + mDetailsBean.getNewId());
                        WXMediaMessage msg = new WXMediaMessage(webpageObject);
                        msg.title = mDetailsBean.getTitle();
                        msg.description = TextUtils.isEmpty(mDetailsBean.getSummary()) ? mDetailsBean.getTitle() : mDetailsBean.getSummary();
                        Bitmap bitmap9 = compress(bitmap);
                        msg.setThumbImage(bitmap9);

                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = Math.random() + "";
                        req.message = msg;
                        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
                                : SendMessageToWX.Req.WXSceneTimeline;

                        WXShare.share(req, new WeakReference<Activity>(activity),
                                new WXShare.OnShareListener() {
                                    @Override
                                    public void onSuccess() {
                                    }

                                    @Override
                                    public void onFailure(int code) {// 10001：参数错误，10002：请求安装QQ，UC浏览器或者QQ浏览器，10003：未知错误
                                        Log.e("code", code + "");
                                    }
                                });
                        mGetShareUrlProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        mGetShareUrlProtocol = null;
                    }
                });
                mGetShareUrlProtocol.postRequest();
            }

        } else {
            WXWebpageObject webpageObject = new WXWebpageObject();
            webpageObject.webpageUrl = "http://" + UserManager.getInst().getQukandianBean().getPostHost() + "/web/article/to/" + mDetailsBean.getNewId();

            WXMediaMessage msg = new WXMediaMessage(webpageObject);
            msg.title = mDetailsBean.getTitle();
            msg.description = TextUtils.isEmpty(mDetailsBean.getSummary()) ? mDetailsBean.getTitle() : mDetailsBean.getSummary();
            Bitmap bitmap9 = compress(bitmap);
            msg.setThumbImage(bitmap9);

            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = Math.random() + "";
            req.message = msg;
            req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
                    : SendMessageToWX.Req.WXSceneTimeline;

            WXShare.share(req, new WeakReference<Activity>(activity),
                    new WXShare.OnShareListener() {
                        @Override
                        public void onSuccess() {
//                        ToastUtils.showLongToast(MainActivity.this, "分享成功");
                        }

                        @Override
                        public void onFailure(int code) {// 10001：参数错误，10002：请求安装QQ，UC浏览器或者QQ浏览器，10003：未知错误
                            Log.e("code", code + "");
                        }
                    });
        }
    }

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

    private boolean isShow() {
        return null != UserManager.getInst().getUserBeam().getMissionGrarntedUsers()
                && UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size() > 0
                && UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(0).isIsActive();
    }

    public void shared(int type, final Activity activity, final DetailsBean mDetailsBean, final SHARE_MEDIA p, final UMShareListener mUMShareListener) {
        MobclickAgent.onEvent(activity, "SharedRecord");
        final UMImage image = new UMImage(activity, mDetailsBean.getPostTextImages().get(0).getSrc());
//        if (mDetailsBean.getPostTextImages().size() > 0) {
//            image = new UMImage(this, mDetailsBean.getPostTextImages().get(0).getSrc());
//        } else {
//            image = new UMImage(this, R.mipmap.app_icon_new);
//        }

        final ShareAction shareAction = new ShareAction(activity);
        if (p == SHARE_MEDIA.SMS) {
            shareAction.setPlatform(p).withText(mDetailsBean.getTitle() + "\nhttp://" + UserManager.getInst().getQukandianBean().getPostHost() + "/web/article/to/" + mDetailsBean.getNewId())
                    .setCallback(mUMShareListener).setDisplayList(p).share();
        } else if (p == SHARE_MEDIA.WEIXIN_CIRCLE) {
            share(type, activity, mDetailsBean, image.asBitmap(), 1);
        } else if (p == SHARE_MEDIA.WEIXIN) {
            share(type, activity, mDetailsBean, image.asBitmap(), 0);
        } else {
            UMWeb umWeb = new UMWeb("http://" + UserManager.getInst().getQukandianBean().getPostHost() + "/web/article/to/" + mDetailsBean.getNewId());
            umWeb.setTitle(mDetailsBean.getTitle());
            umWeb.setThumb(image);
            umWeb.setDescription(TextUtils.isEmpty(mDetailsBean.getSummary()) ? mDetailsBean.getTitle() : mDetailsBean.getSummary());

            shareAction.setPlatform(p).withMedia(umWeb)
                    .setCallback(mUMShareListener).share();
        }
    }

    private void share(final int type, final Activity activity, final DetailsBean mDetailsBean, Bitmap image, final int flag) {
        ToastUtils.showLongToast(activity, "正在打开应用");
        if (null == image) {
            GlideUtils.loadImage(activity, mDetailsBean.getPostTextImages().get(0).getSrc(), new GlideUtils.OnLoadImageListener() {
                @Override
                public void onSucess(Bitmap bitmap, String url) {
                    weChatShare(type, activity, mDetailsBean, bitmap, flag);
                }

                @Override
                public void onFail(Drawable errorDrawable) {

                }
            });
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.mipmap.app_icon);
            weChatShare(type, activity, mDetailsBean, bitmap, flag);
        }
    }
}
