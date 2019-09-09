package com.zhangku.qukandian.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.adapter.SignDialogAdapter;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.bean.UserLevelBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetShoutuShareHostProtocol;
import com.zhangku.qukandian.protocol.GetUserLevelProtocol;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.Draw274QR;
import com.zhangku.qukandian.utils.RecyclerViewDivider;
import com.zhangku.qukandian.widght.UserLevelView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/11/27.
 * 签到成功页
 */
public class DialogSign extends BaseDialog implements View.OnClickListener {
    private TextView mTvGoldText;
    private TextView mTvCloseBtn;
    //    private TextView mTvShareBtn;
    private ImageView mIvCancelBtn;
    private RecyclerView mRecyclerView;
    private ArrayList<Object> mDatas = new ArrayList<>();
    private SignDialogAdapter mAdapter;

    public DialogSign(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_sign_view;
    }

    @Override
    protected void initView() {
        mTvGoldText = findViewById(R.id.sign_gold_number);
        mIvCancelBtn = findViewById(R.id.sign_cancel_btn);
        mTvCloseBtn = findViewById(R.id.sign_close_btn);
//        mTvShareBtn = findViewById(R.id.sign_share_btn);
        mRecyclerView = findViewById(R.id.sign_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), 1, LinearLayoutManager.HORIZONTAL, 0.5f, ContextCompat.getColor(getContext(), R.color.grey_e5)));
        mAdapter = new SignDialogAdapter(getContext(), mDatas);
        mRecyclerView.setAdapter(mAdapter);
        if (UserManager.getInst().getUserBeam().isLevelGrand()) {
            new GetUserLevelProtocol(getContext(), new BaseModel.OnResultListener<UserLevelBean>() {
                @Override
                public void onResultListener(UserLevelBean response) {
                    ImageView sign_gold_icon = findViewById(R.id.sign_gold_icon);
                    TextView sign_exp = findViewById(R.id.sign_exp);
                    UserBean userBean = UserManager.getInst().getUserBeam();

                    UserLevelView.setLevelBigImg(sign_gold_icon);
                    if (userBean.getNextExp() <= 0) {
                        sign_exp.setText((int) userBean.getStartExp() + "/" + (int) userBean.getStartExp());
                        sign_exp.setVisibility(View.VISIBLE);
                    } else {
                        int exp = (int) userBean.getExp();
                        int nextExp = (int) userBean.getNextExp();
                        if (exp > nextExp) {
                            exp = nextExp;
                        }
                        sign_exp.setText(exp + "/" + nextExp);
                        sign_exp.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailureListener(int code, String error) {

                }
            }).postRequest();
        }
        mTvCloseBtn.setOnClickListener(this);
        mIvCancelBtn.setOnClickListener(this);
//      mTvShareBtn.setOnClickListener(this);
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    public void setGold(String gold) {
        if (!gold.isEmpty()) {
            if (null != mTvGoldText) {
                CommonHelper.setTextMultiColor(mTvGoldText, gold + "金币",
                        "恭喜您获得" + gold + "金币", CommonHelper.parseColor("#fcba07"));
            }
        }

        AdUtil.fetchAd(getContext(), AnnoCon.AD_TYPE_SIGN, new AdUtil.AdCallBack() {
            @Override
            public void getAdContent(Object object, int adIndex) {
                mDatas.add(object);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_cancel_btn:
            case R.id.sign_close_btn:
                MobclickAgent.onEvent(getContext(), "03_02_03_clickclosebtn");
                dismiss();
                break;
//            case R.id.sign_share_btn:
//                MobclickAgent.onEvent(getContext(), "03_02_02_clickpyqyq");
//                new GetShoutuShareHostProtocol(mContext, new BaseModel.OnResultListener<String>() {
//                    @Override
//                    public void onResultListener(final String shareHost) {
//                        final String shareUrl = shareHost + "?inviterId="
//                                + UserManager.getInst().getUserBeam().getId()
//                                + "&source=h5_invitation_android_" + QuKanDianApplication.mUmen;
//                        if (UserManager.mBitmaps.size() == 0) return;
//                        File file = Draw274QR.drawQR(getContext(), UserManager.mBitmaps.get(0), "QR1.jpg", shareUrl);
//                        UMImage sImg = new UMImage(mContext, file);
//
//                        sImg.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
//                        sImg.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//                        sImg.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
//                        UMImage umImage = new UMImage(mContext, file);
//                        umImage.setThumb(sImg);
//
//                        new ShareAction((Activity) mContext)
//                                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
//                                .setCallback(null)
//                                .withMedia(umImage)
//                                .share();
//                    }
//
//                    @Override
//                    public void onFailureListener(int code, String error) {
//                    }
//                }).postRequest();
//                break;
        }

    }

    public void refresh() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mAdapter != null && hasFocus)
            mAdapter.notifyDataSetChanged();
    }
}
