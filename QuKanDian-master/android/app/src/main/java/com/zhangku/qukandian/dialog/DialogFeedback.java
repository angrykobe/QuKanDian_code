package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.NewPeopleWithdrawalBean;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewPeopleWitTipsPro;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 创建者          xuzhida
 * 创建日期        2019/4/18
 * 你不注释一下？
 */
public class DialogFeedback extends BaseDialog implements View.OnClickListener {

    private TextView mTitleTV;
    private ImageView mCodeIV;
    private TextView mDescTV;
    private Bitmap mBitmap;

    public DialogFeedback(Context context) {
        super(context);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_feedback;
    }

    @Override
    protected void initView() {
        setTranBg();
        findViewById(R.id.closeBtn).setOnClickListener(this);
        findViewById(R.id.mCodeIV).setOnClickListener(this);
        findViewById(R.id.mSaveImgTV).setOnClickListener(this);
        mTitleTV = findViewById(R.id.mTitleTV);
        mCodeIV = findViewById(R.id.mCodeIV);
        mDescTV = findViewById(R.id.mDescTV);
        new GetNewPeopleWitTipsPro(getContext(), new BaseModel.OnResultListener<NewPeopleWithdrawalBean>() {
            @Override
            public void onResultListener(NewPeopleWithdrawalBean response) {
                mTitleTV.setText(response.getTop());
                mDescTV.setText(response.getBottom());
                GlideUtils.displayImage(getContext(),response.getQrCode(),mCodeIV);
                GlideUtils.displayImage2(getContext(),response.getQrCode(),mCodeIV,new GlideUtils.OnLoadImageListener(){

                    @Override
                    public void onSucess(Bitmap bitmap, String url) {
                        mBitmap = bitmap;
                    }

                    @Override
                    public void onFail(Drawable errorDrawable) {

                    }
                });
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.closeBtn:
                dismiss();
                break;
//            case R.id.mCodeIV:
            case R.id.mSaveImgTV:
                String path = Environment.getExternalStorageDirectory() + "/DCIM/Camera";
                new File(path).mkdirs();
                File file = new File(path, "poster" + ".jpg");
                if (file.exists()) {
                    file.delete();
                }

                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(file);
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(file)));//result是下载保存的文件
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        out.flush();
                        out.close();
                        if (null != mBitmap) {
                            if (mBitmap.isRecycled()) {
                                mBitmap.recycle();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.showLongToast(mContext,"保存出错");
                    }
                }


                if(file.exists()) {
                    ToastUtils.showLongToast(mContext,"成功保存到："+file);
                }
                break;
        }
    }
}
