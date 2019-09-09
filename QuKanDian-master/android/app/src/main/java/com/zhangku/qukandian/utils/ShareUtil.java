package com.zhangku.qukandian.utils;

import android.app.Activity;
import android.graphics.Bitmap;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.NewShareBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetArtShareHostProtocol;
import com.zhangku.qukandian.protocol.GetShareUrlProtocol;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/22
 * 你不注释一下？
 */
public class ShareUtil {
    private static GetShareUrlProtocol mGetShareUrlProtocol;
    private static GetArtShareHostProtocol mGetArtShareHostProtocol;
    //f分享文章详情  获取分享链接
    public static void shareArtDetailsToGetUrl(final Activity ac, final DetailsBean mDetailsBean, final SHARE_MEDIA platform) {
        if (IsShowShareState.isShow()) {
            if (null == mGetShareUrlProtocol) {
                mGetShareUrlProtocol = new GetShareUrlProtocol(ac, UserManager.getInst().getUserBeam().getId(), mDetailsBean.getNewId(), new BaseModel.OnResultListener<NewShareBean>() {
                    @Override
                    public void onResultListener(NewShareBean response) {
                        String shareUrl = "http://" + response.getDomain() + "/index.html"
                                + "?key=bs" + AES.encryUserId(UserManager.getInst().getUserBeam().getId())
                                + "%foot" + AES.encryPostId(mDetailsBean.getNewId())
                                + "%pp" + AES.encryLog(response.getSharearticlelog());
                        shareArtDetails(ac,mDetailsBean,shareUrl,platform);
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
            mGetArtShareHostProtocol = new GetArtShareHostProtocol(ac, mDetailsBean.getNewId(), new BaseModel.OnResultListener<String>() {
                @Override
                public void onResultListener(String response) {
                    shareArtDetails(ac,mDetailsBean,response,platform);
                    mGetArtShareHostProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetArtShareHostProtocol = null;
                }
            });
            mGetArtShareHostProtocol.postRequest();

        }
    }


    //f分享文章详情
    public static void shareArtDetails(final Activity ac, DetailsBean mDetailsBean, String shareUrl, final SHARE_MEDIA platform) {
        UMImage mUMImage;
        if (mDetailsBean != null && mDetailsBean.getPostTextImages() != null && mDetailsBean.getPostTextImages().size() > 0)
            mUMImage = new UMImage(ac, mDetailsBean.getPostTextImages().get(0).getSrc());
        else
            mUMImage = new UMImage(ac, R.mipmap.app_icon);
        mUMImage.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        mUMImage.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
        mUMImage.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

        UMWeb web = new UMWeb(shareUrl);
        web.setTitle(mDetailsBean.getTitle());//标题

        UMImage umImage;
        if (mDetailsBean != null && mDetailsBean.getPostTextImages() != null && mDetailsBean.getPostTextImages().size() > 0)
            umImage = new UMImage(ac, mDetailsBean.getPostTextImages().get(0).getSrc());
        else
            umImage = new UMImage(ac, R.mipmap.app_icon);
        umImage.setThumb(mUMImage);

        web.setThumb(umImage);  //缩略图
        web.setDescription(UserManager.mShareBean.getWechatDesc().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + ""));//描述

        new ShareAction(ac).setPlatform(platform).withMedia(web).setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                ToastUtils.showLongToast(ac, "正在打开应用");
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        }).share();
    }
}
