package com.zhangku.qukandian.activitys.additional;

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
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogFace2Face;
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

public class ChbyxjFaceToFaceInviteActivity extends BaseTitleActivity implements DialogFace2Face.OnClickSaveListener {
    private GrayBgActionBar mGrayBgActionBar;
    private ImageView mImageView;
    private DialogFace2Face mDialogFace2Face;
    private File mFile;
    private String mQRCodeContent;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {

        if (null != getIntent().getExtras()) {
            mQRCodeContent = getIntent().getExtras().getString(Constants.QRCODE_CONTENT);
        }

        mGrayBgActionBar = (GrayBgActionBar) findViewById(R.id.gray_actionbar_layout);
        mImageView = (ImageView) findViewById(R.id.face_to_face_img);
        mDialogFace2Face = new DialogFace2Face(this, this);

        mGrayBgActionBar.setTvTitle("面对面收徒");

        UMImage mImage = new UMImage(ChbyxjFaceToFaceInviteActivity.this, R.mipmap.app_icon);
        mFile = QRCodeUtil.createQRImage(ChbyxjFaceToFaceInviteActivity.this
                , mQRCodeContent,
                DisplayUtils.dip2px(ChbyxjFaceToFaceInviteActivity.this, 220),
                DisplayUtils.dip2px(ChbyxjFaceToFaceInviteActivity.this, 220),
                mImage.asBitmap(), R.mipmap.face_to_face_bg, "QRface.jpg");
        GlideUtils.displayFileImage(this, mFile.getPath(), mImageView);

        mImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDialogFace2Face.show();
                return true;
            }
        });
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
