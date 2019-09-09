package com.zhangku.qukandian.dialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.NewShareBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewShareUrlForGold2Protocol;
import com.zhangku.qukandian.utils.AES;
import com.zhangku.qukandian.utils.ToastUtils;

public class DialogSharedForGold extends BaseDialog implements View.OnClickListener {
    private TextView mTvCircel;
    private TextView mTvChat;
    private View mIvCancle;
    private DetailsBean mDetailsBean;
    private Activity ac;
    private int zfType; // zfType==1 高价文分享  0 普通金币文章分享

    public DialogSharedForGold(Activity context, DetailsBean detailsBean ,int zfType) {
        super(context, R.style.zhangku_dialog);
        ac = context;
        mDetailsBean = detailsBean;
        this.zfType = zfType;
    }

    public void setData(DetailsBean detailsBean) {
        mDetailsBean = detailsBean;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_shared_new;
    }

    @Override
    protected void initView() {
        mTvCircel = findViewById(R.id.dialog_shared_circel_new);
        mTvChat = findViewById(R.id.dialog_shared_chat_new);
        mIvCancle = findViewById(R.id.dialog_shared_cancel_new);
        TextView highPriceTV = findViewById(R.id.mHighPriceTV);

        mTvCircel.setOnClickListener(this);
        mTvChat.setOnClickListener(this);
        mIvCancle.setOnClickListener(this);

        if(zfType == 1){
            findViewById(R.id.mHighPriceView).setVisibility(View.VISIBLE);
            findViewById(R.id.mHighPriceLine).setVisibility(View.VISIBLE);
            highPriceTV.setText(UserManager.getInst().getQukandianBean().getHighPriceGold()+"金币/次");
        }else{
            findViewById(R.id.mHighPriceView).setVisibility(View.GONE);
            findViewById(R.id.mHighPriceLine).setVisibility(View.GONE);
        }
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {
        setToBottom();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_shared_circel_new:
                startShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                dismiss();
                break;
            case R.id.dialog_shared_chat_new:
                startShare(SHARE_MEDIA.WEIXIN);
                dismiss();
                break;
            case R.id.dialog_shared_cancel_new:
                dismiss();
                break;
        }
    }

    private void startShare(final SHARE_MEDIA platform){
        new GetNewShareUrlForGold2Protocol(ac, UserManager.getInst().getUserBeam().getId(), mDetailsBean.getNewId(), zfType, new BaseModel.OnResultListener<NewShareBean>() {
            @Override
            public void onResultListener(NewShareBean response) {
                String shareUrl;
                if(response.getDomain().endsWith("index.html")){
                    shareUrl = "http://" + response.getDomain()
                            + "?key=bs" + AES.encryUserId(UserManager.getInst().getUserBeam().getId())
                            + "%foot" + AES.encryPostId(mDetailsBean.getNewId())
                            + "%pp" + AES.encryLog(response.getSharearticlelog());
                }else{
                    shareUrl = "http://" + response.getDomain() + "/index.html"
                            + "?key=bs" + AES.encryUserId(UserManager.getInst().getUserBeam().getId())
                            + "%foot" + AES.encryPostId(mDetailsBean.getNewId())
                            + "%pp" + AES.encryLog(response.getSharearticlelog());
                }
                if(mDetailsBean.getContentDisType()!=0){
                    shareUrl += "&url=" + mDetailsBean.getStaticUrl() + "&wuliflag=1";
                }
                UMImage mUMImage;
                if (mDetailsBean != null && mDetailsBean.getPostTextImages() != null && mDetailsBean.getPostTextImages().size() > 0)
                    mUMImage = new UMImage(mContext, mDetailsBean.getPostTextImages().get(0).getSrc());
                else
                    mUMImage = new UMImage(mContext, R.mipmap.app_icon);
                mUMImage.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                mUMImage.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                mUMImage.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

                UMWeb web = new UMWeb(shareUrl);
                web.setTitle(mDetailsBean.getTitle());//标题

                UMImage umImage;
                if (mDetailsBean != null && mDetailsBean.getPostTextImages() != null && mDetailsBean.getPostTextImages().size() > 0)
                    umImage = new UMImage(mContext, mDetailsBean.getPostTextImages().get(0).getSrc());
                else
                    umImage = new UMImage(mContext, R.mipmap.app_icon);
                umImage.setThumb(mUMImage);

                web.setThumb(umImage);  //缩略图
                web.setDescription(UserManager.mShareBean.getWechatDesc().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + ""));//描述

                new ShareAction(ac).setPlatform(platform).setCallback(mUMShareListener)
                        .withMedia(web)
                        .share();
            }

            @Override
            public void onFailureListener(int code, String error) {
            }
        }).postRequest();
    }

    private UMShareListener mUMShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(mContext, "正在打开应用");
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
    };
}
