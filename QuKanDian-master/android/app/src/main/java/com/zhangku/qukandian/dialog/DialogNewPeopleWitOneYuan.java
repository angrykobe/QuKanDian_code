package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.UserLevelBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetUserLevelProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.widght.UserLevelView;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/27
 * 新手一元提现
 */
public class DialogNewPeopleWitOneYuan extends BaseDialog implements View.OnClickListener {

    private TextView mLevelNameTV;
    private ImageView mLevelImg;

    public DialogNewPeopleWitOneYuan(Context context) {
        super(context);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_for_new_people_one_yuan;
    }

    @Override
    protected void initView() {
        setTranBg();
        findViewById(R.id.closeBtn).setOnClickListener(this);
        findViewById(R.id.mMoreDesTV).setOnClickListener(this);
        mLevelNameTV = findViewById(R.id.mLevelNameTV);
        mLevelImg = findViewById(R.id.mLevelImg);
        new GetUserLevelProtocol(mContext, new BaseModel.OnResultListener<UserLevelBean>() {
            @Override
            public void onResultListener(UserLevelBean response) {
                if (mLevelNameTV == null) return;
                UserLevelView.setLevelBigImg(mLevelImg);
                mLevelNameTV.setText(UserManager.getInst().getUserBeam().getLevelDisplayName());
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
        switch (v.getId()) {
            case R.id.closeBtn:
                dismiss();
                break;
            case R.id.mMoreDesTV:
                ActivityUtils.startToUserLevelAct(mContext);
                dismiss();
                break;
//            case R.id.mSaveImgTV:
//                String path = Environment.getExternalStorageDirectory() + "/DCIM/Camera";
//
//                File file = new File(path, "poster" + ".jpg");
//                if (file.exists()) {
//                    file.delete();
//                }
//
//                FileOutputStream out = null;
//                try {
//                    out = new FileOutputStream(file);
//                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//                    getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(file)));//result是下载保存的文件
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        out.flush();
//                        out.close();
//                        if (null != mBitmap) {
//                            if (mBitmap.isRecycled()) {
//                                mBitmap.recycle();
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        ToastUtils.showLongToast(mContext,"保存出错");
//                    }
//                }
//
//
//                if(file.exists()) {
//                    ToastUtils.showLongToast(mContext,"成功保存到："+file);
//                }
//                break;
        }
    }
}
