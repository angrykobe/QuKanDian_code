package com.zhangku.qukandian.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetArtShareHostProtocol;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.ToastUtils;

/**
 * Created by yuzuoning on 2017/4/13.
 * 文章分享无金币
 */

public class DialogSharedArtNoGold extends BaseDialog implements View.OnClickListener {
    private TextView mTvCircel;
    private TextView mTvChat;
    private TextView mTvQzone;
    private TextView mTvQQ;
    private TextView mTvWb;
    private TextView mTvMessage;
    private TextView mTvSystem;
    private TextView mTvCopy;
    private TextView mTvCancel;
    private Activity mContext;
    private DetailsBean mDetailsBean;

    public DialogSharedArtNoGold(Activity context, DetailsBean detailsBean) {
        super(context, R.style.zhangku_dialog);
        mContext = context;
        mDetailsBean = detailsBean;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_shared_layout;
    }

    @Override
    protected void initView() {
        mTvCircel = (TextView) findViewById(R.id.dialog_shared_circel);
        mTvChat = (TextView) findViewById(R.id.dialog_shared_chat);
        mTvQzone = (TextView) findViewById(R.id.dialog_shared_qzone);
        mTvQQ = (TextView) findViewById(R.id.dialog_shared_qq);
        mTvWb = (TextView) findViewById(R.id.dialog_shared_wb);
        mTvMessage = (TextView) findViewById(R.id.dialog_shared_message);
        mTvSystem = (TextView) findViewById(R.id.dialog_shared_system);
        mTvCopy = (TextView) findViewById(R.id.dialog_shared_copy);
        mTvCancel = (TextView) findViewById(R.id.dialog_shared_cancel);

        mTvCircel.setOnClickListener(this);
        mTvChat.setOnClickListener(this);
        mTvQzone.setOnClickListener(this);
        mTvQQ.setOnClickListener(this);
        mTvWb.setOnClickListener(this);
        mTvMessage.setOnClickListener(this);
        mTvSystem.setOnClickListener(this);
        mTvCopy.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    protected void release() {
        mContext = null;
    }

    @Override
    protected void setPosition() {
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = Config.SCREEN_WIDTH;
        window.setWindowAnimations(R.style.popupAnimation);
        window.setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_shared_circel:
                startShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                dismiss();
                break;
            case R.id.dialog_shared_chat:
                startShare(SHARE_MEDIA.WEIXIN);
                dismiss();
                break;
            case R.id.dialog_shared_qzone:
                startShare(SHARE_MEDIA.QZONE);
                dismiss();
                break;
            case R.id.dialog_shared_qq:
                startShare(SHARE_MEDIA.QQ);
                dismiss();
                break;
            case R.id.dialog_shared_wb:
                startShare(SHARE_MEDIA.SINA);
                dismiss();
                break;
            case R.id.dialog_shared_message:
                startShare(SHARE_MEDIA.SMS);
                dismiss();
                break;
            case R.id.dialog_shared_system:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, mDetailsBean.getTitle() + "http://" + UserManager.getInst().getQukandianBean().getPostHost() + "/web/article/to/" + mDetailsBean.getNewId());
                shareIntent.setType("text/plain");
                getContext().startActivity(Intent.createChooser(shareIntent, "分享到"));
                dismiss();
                break;
            case R.id.dialog_shared_copy:
                if (!TextUtils.isEmpty(UserManager.getInst().getQukandianBean().getPostHost())) {
                    CommonHelper.copy(getContext(), "http://" + UserManager.getInst().getQukandianBean().getPostHost() + "/web/article/to/" + mDetailsBean.getNewId(), "复制链接成功");
                }
                dismiss();
                break;
            case R.id.dialog_shared_cancel:
                dismiss();
                break;
        }
    }


    private void startShare(final SHARE_MEDIA platform) {
        //请求分享地址
        new GetArtShareHostProtocol(mContext, mDetailsBean.getNewId(), new BaseModel.OnResultListener<String>() {
            @Override
            public void onResultListener(String response) {
                if(mDetailsBean.getContentDisType()!=0){
                    response += "&url=" + mDetailsBean.getStaticUrl() + "&wuliflag=2";
                }
                UMImage mUMImage;
                if (mDetailsBean != null && mDetailsBean.getPostTextImages() != null && mDetailsBean.getPostTextImages().size() > 0)
                    mUMImage = new UMImage(mContext, mDetailsBean.getPostTextImages().get(0).getSrc());
                else
                    mUMImage = new UMImage(mContext, R.mipmap.app_icon);
                mUMImage.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                mUMImage.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                mUMImage.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

                UMWeb web = new UMWeb(response);
                web.setTitle(mDetailsBean.getTitle());//标题

                UMImage umImage;
                if (mDetailsBean != null && mDetailsBean.getPostTextImages() != null && mDetailsBean.getPostTextImages().size() > 0)
                    umImage = new UMImage(mContext, mDetailsBean.getPostTextImages().get(0).getSrc());
                else
                    umImage = new UMImage(mContext, R.mipmap.app_icon);
                umImage.setThumb(mUMImage);

                web.setThumb(umImage);  //缩略图
                web.setDescription(UserManager.mShareBean.getWechatDesc().replace("{yqm}", UserManager.getInst().getUserBeam().getId() + ""));//描述

                new ShareAction(mContext).setPlatform(platform).setCallback(mUMShareListener)
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
//            DialogPrograss mDialogPrograss;
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
//            ToastUtils.showLongToast(InformationDetailsAtivity.this, "分享成功:");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };

}
