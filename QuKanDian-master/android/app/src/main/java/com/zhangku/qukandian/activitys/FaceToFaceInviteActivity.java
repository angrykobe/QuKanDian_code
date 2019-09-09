package com.zhangku.qukandian.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;

import com.umeng.socialize.media.UMImage;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.dialog.DialogFace2Face;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetShoutuShareHostProtocol;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.QRCodeUtil;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.GrayBgActionBar;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by yuzuoning on 2017/7/21.
 */

public class FaceToFaceInviteActivity extends BaseTitleActivity implements DialogFace2Face.OnClickSaveListener {
    private GrayBgActionBar mGrayBgActionBar;
    private ImageView mImageView;
    private DialogFace2Face mDialogFace2Face;
    private File mFile;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        mGrayBgActionBar = (GrayBgActionBar) findViewById(R.id.gray_actionbar_layout);
        mImageView = (ImageView) findViewById(R.id.face_to_face_img);
        mDialogFace2Face = new DialogFace2Face(this, this);

        mGrayBgActionBar.setTvTitle("面对面收徒");
        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDialogFace2Face.show();
                return true;
            }
        });

        new GetShoutuShareHostProtocol(this, new BaseModel.OnResultListener<String>() {
            @Override
            public void onResultListener(final String shareHost) {
//                String shareUrl = shareHost + "?inviterId="
//                        + UserManager.getInst().getUserBeam().getId()
//                        + "&source=h5_invitation_android_" + QuKanDianApplication.mUmen;
                String unBase64String = "inviterId="
                        + UserManager.getInst().getUserBeam().getId()
                        + "&source=h5_invitation_android_" + QuKanDianApplication.mUmen;

                final String shareUrl = shareHost +"?"+ CommonHelper.encodeBase64(unBase64String);
                UMImage mImage = new UMImage(FaceToFaceInviteActivity.this, R.mipmap.app_icon);
                mFile = QRCodeUtil.createQRImage(FaceToFaceInviteActivity.this
                        , shareUrl,
                        DisplayUtils.dip2px(FaceToFaceInviteActivity.this, 220),
                        DisplayUtils.dip2px(FaceToFaceInviteActivity.this, 220),
                        mImage.asBitmap(), R.mipmap.face_to_face_bg, "QRface.jpg");
                GlideUtils.displayFileImage(FaceToFaceInviteActivity.this, mFile.getPath(), mImageView);
            }

            @Override
            public void onFailureListener(int code, String error) {
            }
        }).postRequest();


    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_face_to_face_layout;
    }

    @Override
    public String setPagerName() {
        return "面对面收徒";
    }

    @Override
    public void onClickSaveListener() {
        mDialogPrograss.show();
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            } else {
                saveImg();
            }
        } else {
            saveImg();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImg();
            }
        }
    }

    private void saveImg() {
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    mFile.getAbsolutePath(), mFile.getName(), null);
            // 最后通知图库更新
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(mFile)));
            mDialogPrograss.dismiss();
            ToastUtils.showShortToast(this, "保存成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        if (mFile.exists()) {
            mFile.delete();
        }
    }
}
