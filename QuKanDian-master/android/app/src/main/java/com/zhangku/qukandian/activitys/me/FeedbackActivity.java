package com.zhangku.qukandian.activitys.me;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yzs.imageshowpickerview.ImageLoader;
import com.yzs.imageshowpickerview.ImageShowPickerBean;
import com.yzs.imageshowpickerview.ImageShowPickerListener;
import com.yzs.imageshowpickerview.ImageShowPickerView;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.FeedbackBean;
import com.zhangku.qukandian.bean.ImageBean;
import com.zhangku.qukandian.biz.adbeen.saibo.SaiboResBean;
import com.zhangku.qukandian.dialog.DialogFeedback;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PostFeedbackPhotoProtocol;
import com.zhangku.qukandian.protocol.PostFeedbackProtocol;
import com.zhangku.qukandian.utils.Img.GlideEngineFeedback;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.GrayBgActionBar;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/4/18.
 */

public class FeedbackActivity extends BaseTitleActivity {
    private GrayBgActionBar mGrayBgActionBar;
    private EditText mEtContent;
    private EditText mEtContact;
    private TextView mTvSubmitBtn;
    //    private PostFeedbackProtocol mPostFeedbackProtocol;
    private PostFeedbackPhotoProtocol mPostFeedbackPhotoProtocol;

    private static final int REQUEST_CODE_CHOOSE = 233;
    List<ImageBean> list;
    ImageShowPickerView pickerView;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new ArrayMap<>();
        map.put("to", "意见反馈");
        MobclickAgent.onEvent(this, "AllPv", map);
    }

    @Override
    protected void initViews() {
        mGrayBgActionBar = (GrayBgActionBar) findViewById(R.id.gray_actionbar_layout);
        mEtContent = (EditText) findViewById(R.id.activity_feedback_content);
        mEtContact = (EditText) findViewById(R.id.activity_feedback_contact);
        mTvSubmitBtn = (TextView) findViewById(R.id.activity_feedback_submit);

        mGrayBgActionBar.setTvTitle("用户反馈");
        mTvSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogPrograss.show();
                if (TextUtils.isEmpty(mEtContent.getText().toString().trim())) {
                    ToastUtils.showLongToast(FeedbackActivity.this, "请输入反馈内容");
                    mDialogPrograss.dismiss();
                } else {
                    if (null == mPostFeedbackPhotoProtocol) {
                        mPostFeedbackPhotoProtocol = new PostFeedbackPhotoProtocol(FeedbackActivity.this, new BaseModel.OnResultListener<Boolean>() {
                            @Override
                            public void onResultListener(Boolean response) {
                                mDialogPrograss.dismiss();
                                if (response) {
                                    finish();
                                }
                                mPostFeedbackPhotoProtocol = null;
                            }

                            @Override
                            public void onFailureListener(int code, String error) {
                                mDialogPrograss.dismiss();
                                mPostFeedbackPhotoProtocol = null;
                            }
                        });
                        String phoneVersion = "";
                        if (QuKanDianApplication.mDeviceInfo != null) {
                            phoneVersion = "[" + QuKanDianApplication.mDeviceInfo.brand + " " + QuKanDianApplication.mDeviceInfo.model + "][" + QuKanDianApplication.mDeviceInfo.version_release + "]";
                        }
                        if (pickerView.getDataList() != null && pickerView.getDataList().size() > 0) {
                            List<ImageShowPickerBean> list = pickerView.getDataList();
                            mPostFeedbackPhotoProtocol.postFeedback(
                                    new FeedbackBean(mEtContent.getText().toString().trim()
                                            , mEtContact.getText().toString().trim(), phoneVersion), list);
                        }else{
                            mPostFeedbackPhotoProtocol.postFeedback(
                                    new FeedbackBean(mEtContent.getText().toString().trim()
                                            , mEtContact.getText().toString().trim(), phoneVersion), null);
                        }

                    }
                }
            }
        });

        new DialogFeedback(this).show();


        pickerView = (ImageShowPickerView) findViewById(R.id.it_picker_view);
        list = new ArrayList<>();

        Log.e("list", "======" + list.size());
        pickerView.setImageLoaderInterface(new Loader());
        pickerView.setNewData(list);
        //展示有动画和无动画

        pickerView.setShowAnim(true);

        pickerView.setPickerListener(new ImageShowPickerListener() {
            @Override
            public void addOnClickListener(int remainNum) {
                Matisse.from(FeedbackActivity.this)
                        .choose(MimeType.ofImage())
                        .theme(R.style.Matisse_Dracula)
                        .countable(true)
                        .maxSelectable(remainNum + 1)
                        .gridExpectedSize(300)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngineFeedback())
                        .forResult(REQUEST_CODE_CHOOSE);
//                Toast.makeText(FeedbackActivity.this, "remainNum" + remainNum, Toast.LENGTH_SHORT).show();

//                list.add(new ImageBean("http://pic78.huitu.com/res/20160604/1029007_20160604114552332126_1.jpg"));
            }

            @Override
            public void picOnClickListener(List<ImageShowPickerBean> list, int position, int remainNum) {
//                Toast.makeText(FeedbackActivity.this, list.size() + "========" + position + "remainNum" + remainNum, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void delOnClickListener(int position, int remainNum) {
//                Toast.makeText(FeedbackActivity.this, "delOnClickListenerremainNum" + remainNum, Toast.LENGTH_SHORT).show();
            }
        });
        pickerView.show();

        // Activity:
        AndPermission.with(this)
                .requestCode(300)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale(rationaleListener)
                .callback(this)
                .start();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_feedback_layout;
    }

    @Override
    public String setPagerName() {
        return "用户反馈";
    }

    public class Loader extends ImageLoader {

        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);

        }

        @Override
        public void displayImage(Context context, @DrawableRes Integer resId, ImageView imageView) {
            imageView.setImageResource(resId);
        }

    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int i, final Rationale rationale) {
            // 自定义对话框。
            AlertDialog.newBuilder(FeedbackActivity.this)
                    .setTitle("请求权限")
                    .setMessage("请求权限")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            mSelected = Matisse.obtainResult(data);
            List<Uri> uriList = Matisse.obtainResult(data);
            if (uriList.size() == 1) {
                pickerView.addData(new ImageBean(getRealFilePath(FeedbackActivity.this, uriList.get(0))));
            } else {
                List<ImageBean> list = new ArrayList<>();
                for (Uri uri : uriList) {
                    list.add(new ImageBean(getRealFilePath(FeedbackActivity.this, uri)));
                }
                pickerView.addData(list);
            }
        }
    }


    public String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
