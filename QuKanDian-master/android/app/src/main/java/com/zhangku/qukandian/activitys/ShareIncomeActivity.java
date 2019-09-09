package com.zhangku.qukandian.activitys;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EncodeUtils;
import com.umeng.socialize.media.UMImage;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.dialog.DialogPermissions;
import com.zhangku.qukandian.dialog.DialogShareIncome;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetShoutuShareHostProtocol;
import com.zhangku.qukandian.protocol.GetUserShareProtocol;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.DrawQR;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.QRShare;
import com.zhangku.qukandian.utils.SharePoster;
import com.zhangku.qukandian.widght.GrayBgActionBar;

import java.io.File;

/**
 * Created by yuzuoning on 2017/6/13.
 */

public class ShareIncomeActivity extends BaseTitleActivity implements View.OnClickListener, DialogShareIncome.OnConfirmLinstener {
    private GrayBgActionBar mGrayBgActionBar;
    private TextView mTvMoney;
    private TextView mTvEdit;
    private TextView mTvShare;
    private ImageView mIvQr;
    private View mPlaceholder;
    private LinearLayout mLayout;
    private DialogShareIncome mDialogShareIncome;
    private GetUserShareProtocol mGetUserShareProtocol;
    private UMImage mLogoImg;
    private Bitmap mBitmap;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        mLogoImg = new UMImage(ShareIncomeActivity.this, R.mipmap.app_icon);
        mLayout = (LinearLayout) findViewById(R.id.share_layout);
        mDialogShareIncome = new DialogShareIncome(this, this);
        mGrayBgActionBar = (GrayBgActionBar) findViewById(R.id.gray_actionbar_layout);
        mGrayBgActionBar.setTvTitle("晒收入");
        mIvQr = (ImageView) findViewById(R.id.activity_share_income_layout_qr);
        mPlaceholder = findViewById(R.id.activity_share_income_layout_place);
        mTvMoney = (TextView) findViewById(R.id.activity_share_income_layout_money);
        mTvEdit = (TextView) findViewById(R.id.activity_share_income_layout_edit);
        mTvShare = (TextView) findViewById(R.id.activity_share_income_layout_share);


        if (UserManager.mShareBean != null) {
            GlideUtils.loadImage(this, UserManager.mShareBean.getShareIncomeImageLink(), new GlideUtils.OnLoadImageListener() {
                @Override
                public void onSucess(Bitmap bitmap, String url) {
                    mBitmap = bitmap;
                    mLayout.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onFail(Drawable errorDrawable) {

                }
            });
        }

        if (null != UserManager.getInst().getUserBeam().getCoinAccount()) {
            mTvMoney.setText(CommonHelper.form2(UserManager.getInst().getUserBeam().getGoldAccount().getSum() / 10000f) + "元");
        }

        new GetShoutuShareHostProtocol(this, new BaseModel.OnResultListener<String>() {
            @Override
            public void onResultListener(final String shareHost) {
//                final String shareUrl = shareHost + "?inviterId="
//                        + UserManager.getInst().getUserBeam().getId()
//                        + "&source=h5_invitation_android_" + QuKanDianApplication.mUmen;

                String unBase64String = "inviterId="
                        + UserManager.getInst().getUserBeam().getId()
                        + "&source=h5_invitation_android_" + QuKanDianApplication.mUmen;

                final String shareUrl = shareHost + "?" + CommonHelper.encodeBase64(unBase64String);

                mIvQr.setImageBitmap(DrawQR.createQRImage(ShareIncomeActivity.this, shareUrl, 240, 240, mLogoImg.asBitmap()));
            }

            @Override
            public void onFailureListener(int code, String error) {
            }
        }).postRequest();

        mTvEdit.setOnClickListener(this);
        mTvShare.setOnClickListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_share_income_layout;
    }

    @Override
    public String setPagerName() {
        return "晒收入";
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mGrayBgActionBar = null;
        mTvMoney = null;
        mTvEdit = null;
        mTvShare = null;
        mDialogShareIncome = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_share_income_layout_edit:
                mDialogShareIncome.show();
                break;
            case R.id.activity_share_income_layout_share:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                1);
                    } else {
                        sharePic();
                    }
                } else {
                    sharePic();
                }
                break;
        }
    }

    private void sharePic() {
        new GetShoutuShareHostProtocol(this, new BaseModel.OnResultListener<String>() {
            @Override
            public void onResultListener(final String shareHost) {
//                final String shareUrl = shareHost + "?inviterId="
//                        + UserManager.getInst().getUserBeam().getId()
//                        + "&source=h5_invitation_android_" + QuKanDianApplication.mUmen;

                String unBase64String = "inviterId="
                        + UserManager.getInst().getUserBeam().getId()
                        + "&source=h5_invitation_android_" + QuKanDianApplication.mUmen;

                final String shareUrl = shareHost + "?" + CommonHelper.encodeBase64(unBase64String);

                if (mTvMoney != null)
                    SharePoster.drawPoster(ShareIncomeActivity.this
                            , mTvMoney.getText().toString()
                            , shareUrl
                            , mBitmap,
                            new SharePoster.OnDrawFinishedListener() {
                                @Override
                                public void onDrawFinishedListener(File file) {
                                    new QRShare().shareIncome(ShareIncomeActivity.this, file, mTvMoney.getText().toString().trim());
                                }
                            });
            }

            @Override
            public void onFailureListener(int code, String error) {
            }
        }).postRequest();
    }

    @Override
    public void onConfirmListener(String money) {
        mTvMoney.setText(money + "元");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sharePic();
                } else {
                    new DialogPermissions(ShareIncomeActivity.this).show();
                }
            } else {
                new DialogPermissions(ShareIncomeActivity.this).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
