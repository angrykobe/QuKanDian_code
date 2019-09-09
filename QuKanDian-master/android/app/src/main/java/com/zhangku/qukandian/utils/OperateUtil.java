package com.zhangku.qukandian.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.RuleBean;
import com.zhangku.qukandian.manager.UserManager;

import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;

public class OperateUtil {

    public static void shareMiniAppMain(String url, final Activity activity, Dialog dialogPrograss) {
        // 分享小程序
        String[] tmpArr = url.split("α");
        RuleBean.XcxShareConfigItemBean tmpXcxShareConfigItemBean = null;
        if (tmpArr.length > 1 && UserManager.getInst().getmRuleBean().getXcxShareConfig() != null && UserManager.getInst().getmRuleBean().getXcxShareConfig().size() > 0) {
            for (RuleBean.XcxShareConfigItemBean item : UserManager.getInst().getmRuleBean().getXcxShareConfig()) {
                if (!TextUtils.isEmpty(item.getXcxId()) && item.getXcxId().equals(tmpArr[1])) {
                    OperateUtil.shareMINApp(activity, dialogPrograss, item, new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            LogUtils.LogE("onStart");
                            ToastUtils.showShortToast(activity,"onStart");
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            LogUtils.LogE("onResult");

                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            LogUtils.LogE("onError");

                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            LogUtils.LogE("onCancel");

                        }
                    });
                    break;
                }
            }
        }
    }

    public static void shareMINApp(final Activity activity, final Dialog dialog, final RuleBean.XcxShareConfigItemBean xcxShareConfigItemBean, final UMShareListener listener) {
        if (activity == null) return;
        if (xcxShareConfigItemBean == null || TextUtils.isEmpty(xcxShareConfigItemBean.getImg())) return;
        if (dialog != null) {
            dialog.show();
        }
        GlideUtils.loadImage(activity, xcxShareConfigItemBean.getImg(), new GlideUtils.OnLoadImageListener() {
            @Override
            public void onSucess(final Bitmap bitmap, String url) {
                if (activity == null) return;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dialog != null) dialog.hide();
                        sendMiniApps(xcxShareConfigItemBean.getPath(), xcxShareConfigItemBean.getTitle(), xcxShareConfigItemBean.getContent(), xcxShareConfigItemBean.getUrl(), bitmap, xcxShareConfigItemBean.getXcxId());
                    }
                });
            }

            @Override
            public void onFail(Drawable errorDrawable) {
                if (dialog != null)
                    dialog.hide();
            }
        });
    }

    /**
     * 废弃不能用，友盟分享小程序在新版微信客户端分享不成功
     * @param activity
     * @param userName
     * @param title
     * @param desc
     * @param url
     * @param icon
     * @param path
     * @param listener
     */
    @Deprecated
    public static void shareMINApp(Activity activity, String userName, String title, String desc,
                              String url, Bitmap icon, String path, UMShareListener listener) {
        if (activity == null || TextUtils.isEmpty(url) || TextUtils.isEmpty(userName)) return;
        try {
            LogUtils.LogE("url="+url+":path="+path+":userName="+userName+":title="+title+":desc="+desc);
            // url一定要不为空，不然UMMin会报错
            UMMin umMin = new UMMin(url);
            UMImage thumb =  new UMImage(activity, icon);
            umMin.setThumb(thumb);
            umMin.setTitle(title);
            umMin.setDescription(desc);
        umMin.setPath("pages/index/index");
//        umMin.setUserName("xx_xxx");
            if (!TextUtils.isEmpty(path)) {
                LogUtils.LogE("----------Path="+path);
                umMin.setPath(path);
            }
            umMin.setUserName(userName);
//            umMin.setPath("pages/page10007/page10007");
//            umMin.setUserName("gh_3ac2059ac66f");
            new ShareAction(activity)
                    .withMedia(umMin)
                    .setPlatform(SHARE_MEDIA.WEIXIN)
                    .setCallback(listener).share();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分享小程序到微信，用的新版微信sdk, "com.tencent.mm.opensdk:wechat-sdk-android-with-mta:+"
     * @param articlePk
     * @param title
     * @param content
     * @param url
     * @param icon
     * @param userName
     */
    public static void sendMiniApps(String articlePk, String title, String content,
                      String url, Bitmap icon, String userName) {

        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(userName) || icon == null) return;

        WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        //低版本微信打开 URL
        miniProgram.webpageUrl = url;
        //跳转的小程序的原始 ID
        miniProgram.userName = userName;
        //小程序的 Path
        miniProgram.path = articlePk;//拉起小程序页面的可带参路径，不填默认拉起小程序首页

        WXMediaMessage msg = new WXMediaMessage(miniProgram);
        final String shareTitle = title;
        if (!TextUtils.isEmpty(shareTitle)) {
            msg.title = title;
        }

        final String shareDescription = content;
        if (!TextUtils.isEmpty(shareDescription)) {
            msg.description = shareDescription;
        }

        if (icon != null) {
            msg.setThumbImage(icon);
        } else {
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("miniProgram");
        req.message = msg;
        req.scene = WXSceneSession;
        if (QuKanDianApplication.wxApi != null) {
            QuKanDianApplication.wxApi.sendReq(req);
        }

    }

    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
