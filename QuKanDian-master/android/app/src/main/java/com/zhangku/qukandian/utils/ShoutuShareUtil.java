package com.zhangku.qukandian.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.ShareBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetShareImgProtocol;

import java.io.File;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/26
 * 收徒分享复制过来的，
 */
public class ShoutuShareUtil {

    private Activity ac;
    protected Bitmap mAvatar;

    public ShoutuShareUtil(Activity ac) {
        this.ac = ac;
    }

    private UMShareListener mUMShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(ac, "正在打开应用");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(ac, "分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (throwable.toString().contains("没有安装应用")) {
                ToastUtils.showLongToast(ac, "没有安装应用");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
        }
    };

    public void sharedTransit(final SHARE_MEDIA p) {
        if (UserManager.mBitmaps == null || UserManager.mBitmaps.size() <= 0) {
            new GetShareImgProtocol(ac, new BaseModel.OnResultListener<ShareBean>() {
                @Override
                public void onResultListener(ShareBean response) {
                    UserManager.mShareBean = response;
                    if (response.getShareFrientPosterItems().size() == 0) {
                        sharedTransit2(p);
                        return;
                    }
                    for (int i = 0; i < response.getShareFrientPosterItems().size(); i++) {
                        // TODO: 2019/6/4 替换 上线前注释
                        GlideUtils.loadImage(ac, response.getShareFrientPosterItems().get(i).getImageLink().replace("http://cdn.qu.fi.pqmnz.com", "http://static.funnykandian.com"), new GlideUtils.OnLoadImageListener() {
                            @Override
                            public void onSucess(Bitmap bitmap, String url) {
                                UserManager.mBitmaps.add(bitmap);
                                if (UserManager.mBitmaps.size() == 1) {
                                    sharedTransit2(p);
                                }
                            }

                            @Override
                            public void onFail(Drawable errorDrawable) {
                                sharedTransit2(p);
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

    private void sharedTransit2(final SHARE_MEDIA p) {
        if (mAvatar == null) {
            GlideUtils.circleAvatar(ac, UserManager.getInst().getUserBeam().getAvatarUrl(), 50, 50, new GlideUtils.OnLoadImageListener() {
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
        } else {
            shared(p);
        }
    }

    private void shared(final SHARE_MEDIA p) {
        if (p == SHARE_MEDIA.WEIXIN) {
            new ThirdWeChatShare().shareShoutuWechatUrlByUmeng(UserManager.getInst().getmRuleBean().getShoutuPosterConfig().getWechatType(), ac, SHARE_MEDIA.WEIXIN, mUMShareListener);
        } else if (p == SHARE_MEDIA.WEIXIN_CIRCLE) {
            new ThirdWeChatShare().shareShoutuWechatUrlByUmeng(UserManager.getInst().getmRuleBean().getShoutuPosterConfig().getShareFriendType(), ac, SHARE_MEDIA.WEIXIN_CIRCLE, mUMShareListener);
        } else if (p == SHARE_MEDIA.SMS) {
            ShareAction shareAction = new ShareAction(ac);
            shareAction.setPlatform(p).withText(UserManager.mShareBean.getShareFriendText())
                    .setDisplayList(p).share();
        } else if (p == SHARE_MEDIA.QQ) {
            new ThirdWeChatShare().shareShoutuWechatUrlByUmeng(UserManager.getInst().getmRuleBean().getShoutuPosterConfig().getQqType(), ac, SHARE_MEDIA.QQ, mUMShareListener);
        } else {
            if (mAvatar == null) return;
            if (UserManager.mBitmaps == null || UserManager.mBitmaps.size() <= 0) return;
            File file = Draw281QR.drawQR(ac, UserManager.mBitmaps.get(0), mAvatar, "QR1.jpg", "http://"
                    + UserManager.getInst().getQukandianBean().getHost()
                    + "/web/quyou/agent/" + UserManager.getInst().getUserBeam().getId()
                    + "?source=h5_invitation_android_" + QuKanDianApplication.mUmen);
            UMImage mUMImage = new UMImage(ac, file);
            ShareContent shareContent = new ShareContent();
            shareContent.mText = UserManager.mShareBean.getShareFriendText().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + "");
            shareContent.mMedia = mUMImage;
            new ShareAction(ac).setPlatform(p)
                    .setShareContent(shareContent)
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            ToastUtils.showLongToast(ac, "正在打开应用");
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            ToastUtils.showLongToast(ac, "分享成功");
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            if (throwable.toString().contains("没有安装应用")) {
                                ToastUtils.showLongToast(ac, "没有安装应用");
                            }
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            Log.e("shared", "onCancel");
                        }
                    }).share();
        }
    }
}
