package com.zhangku.qukandian.activitys.member;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogBindOtherWechat;
import com.zhangku.qukandian.dialog.DialogPermissions;
import com.zhangku.qukandian.dialog.DialogUserAge;
import com.zhangku.qukandian.dialog.DialogUserHeader;
import com.zhangku.qukandian.dialog.DialogWechatRemind;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.MenuChangeOberver;
import com.zhangku.qukandian.protocol.GetNewUserInfoProtocol;
import com.zhangku.qukandian.protocol.UpLoadAvatarProtocol;
import com.zhangku.qukandian.protocol.UploadWeChatInfoProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;

import static com.zhangku.qukandian.config.Constants.PHOTO_REQUEST_CAREMA;
import static com.zhangku.qukandian.config.Constants.PHOTO_REQUEST_CUT;
import static com.zhangku.qukandian.config.Constants.PHOTO_REQUEST_GALLERY;
import static org.litepal.LitePalApplication.getContext;

/**
 * Created by yuzuoning on 2017/3/31.
 * 完善用户信息
 */

public class PerfectInforActivity extends BaseTitleActivity implements View.OnClickListener,
        DialogUserHeader.OnBtnClickListener, DialogUserAge.OnClickBtnListener, UserManager.IOnUserInfoChange {
    private LinearLayout mLlUserHeader;
    private LinearLayout mLlNickname;
    private LinearLayout mLlAge;
    private LinearLayout mLlBinderWeixin;
    private LinearLayout mLlMentorNicname;
    private TextView mTvNickname;
    private TextView mTvAget;
    private ImageView mIvUserHeader;
    private TextView mTvBinderWeixinText;
    private ImageView mIvArrow;
    private TextView mTvId;
    private TextView mTvMyMaster;
    private TextView mTvCanceBtn;

    private File tempFile = null;
    private File cropFile = null;

    private DialogUserHeader mDialogUserHeader;
    private DialogUserAge mDialogUserAge;

    private boolean isCkickBinderWeChat = true;
    private UploadWeChatInfoProtocol mUploadWeChatInfoProtocol;
    private DialogWechatRemind mDialogWechatRemind;

    @Override
    protected void initActionBarData() {
        setTitle("我的资料");
    }

    @Override
    protected void initViews() {
        if (!UserManager.getInst().hadLogin()) {
            ActivityUtils.startToBeforeLogingActivity(this);
            finish();
            return;
        }
        mDialogUserHeader = new DialogUserHeader(this, R.style.zhangku_dialog);
        mDialogWechatRemind = new DialogWechatRemind(this);
        mDialogUserAge = new DialogUserAge(this, R.style.zhangku_dialog, this);
        mTvBinderWeixinText = findViewById(R.id.activity_setting_layout_binder_weixin_text);
        mIvArrow = (ImageView) findViewById(R.id.activity_setting_layout_binder_weixin_arraw);
        mLlUserHeader = (LinearLayout) findViewById(R.id.activity_perfect_infor_layout_user_header);
        mLlBinderWeixin = (LinearLayout) findViewById(R.id.activity_setting_layout_binder_weixin);
        mLlNickname = (LinearLayout) findViewById(R.id.activity_perfect_infor_layout_user_nickname);
        mLlAge = (LinearLayout) findViewById(R.id.activity_perfect_infor_layout_user_age_layout);
        mLlMentorNicname = (LinearLayout) findViewById(R.id.activity_perfect_infor_layout_my_master_layout);
        mTvNickname = findViewById(R.id.activity_perfect_infor_layout_user_nickname_text);
        mTvAget = (TextView) findViewById(R.id.activity_perfect_infor_layout_user_age_text);
        mIvUserHeader = (ImageView) findViewById(R.id.activity_perfect_infor_layout_user_header_img);
        mTvId = (TextView) findViewById(R.id.activity_perfect_infor_layout_user_id_text);
        mTvMyMaster = findViewById(R.id.activity_perfect_infor_layout_my_master);
        mTvCanceBtn = (TextView) findViewById(R.id.activity_perfect_layout_cancel_login);

        if (UserManager.getInst().hadLogin()) {
            GlideUtils.displayCircleImage(getContext(), UserManager.getInst().getUserBeam().getAvatarUrl()
                    , mIvUserHeader, 0, 0, GlideUtils.getUserNormalOptions(), true);
            mTvNickname.setText(UserManager.getInst().getUserBeam().getNickName());
            mTvAget.setText(mDialogUserAge.getCurrentAge(UserManager.getInst().getUserBeam().getBirthDay()));
        }
        mTvId.setText(UserManager.getInst().getUserBeam().getId() + "");
        if (TextUtils.isEmpty(UserManager.getInst().getUserBeam().getMentorUser().getMentorNickName())) {
            mLlMentorNicname.setVisibility(View.GONE);
        } else {
            mLlMentorNicname.setVisibility(View.VISIBLE);
            mTvMyMaster.setText(UserManager.getInst().getUserBeam().getMentorUser().getMentorNickName());
        }
        checkBindWeChat();

        mLlUserHeader.setOnClickListener(this);
        mLlNickname.setOnClickListener(this);
        mLlAge.setOnClickListener(this);
        mTvCanceBtn.setOnClickListener(this);
        mDialogUserHeader.setOnBtnClickListener(this);
        mLlBinderWeixin.setOnClickListener(this);
        UserManager.getInst().addUserInfoListener(this);
    }

    private void checkBindWeChat() {
        if (UserManager.getInst().hadLogin()) {
            if (null != UserManager.getInst().getUserBeam().getMission()) {
//                if (UserManager.getInst().getUserBeam().getMission().getFinished().contains(Constants.BINDING_WECHAT)) {
                    if (UserManager.getInst().getUserBeam().getWechatUser() != null) {
                        isCkickBinderWeChat = false;
                        mTvBinderWeixinText.setText(UserManager.getInst().getUserBeam().getWechatUser().getNickName());
                        mIvArrow.setVisibility(View.INVISIBLE);
                        mLlBinderWeixin.setVisibility(View.VISIBLE);
                    } else {
                        isCkickBinderWeChat = false;
                        mLlBinderWeixin.setVisibility(View.VISIBLE);
                        mTvBinderWeixinText.setText("绑定微信");
                        mIvArrow.setVisibility(View.VISIBLE);
                    }
//                } else {
//                    isCkickBinderWeChat = false;
//                    mLlBinderWeixin.setVisibility(View.VISIBLE);
//                    mTvBinderWeixinText.setText("绑定微信");
//                    mIvArrow.setVisibility(View.VISIBLE);
//                }
            }
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_perfect_infor_layout;
    }

    @Override
    public String setPagerName() {
        return "我的资料";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_perfect_infor_layout_user_header:
                mDialogUserHeader.show();
                break;
            case R.id.activity_perfect_infor_layout_user_age_layout:
                mDialogUserAge.show();
                break;
            case R.id.activity_perfect_layout_cancel_login:
                UserManager.getInst().logout(this);
                finish();
                break;
            case R.id.activity_perfect_infor_layout_user_nickname:
                ActivityUtils.startToUpdateNicknameActivity(PerfectInforActivity.this,
                        mTvNickname.getText().toString());
                break;
            case R.id.activity_setting_layout_binder_weixin:
                if (isCkickBinderWeChat) {
                } else {
                    new DialogBindOtherWechat(this, new DialogBindOtherWechat.OnBindListener() {
                        @Override
                        public void onBindListener() {
                            bindWeChat(true);
                        }
                    }).show();
                }
                break;
        }
    }

    private void bindWeChat(final boolean isBinder) {
        UMShareAPI.get(QuKanDianApplication.getmContext()).deleteOauth(this, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                UMShareAPI.get(QuKanDianApplication.getmContext()).getPlatformInfo(PerfectInforActivity.this,
                        SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                ToastUtils.showLongToast(PerfectInforActivity.this, "正在打开跳转页面");
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA share_media, int i, final Map<String, String> map) {
                                mDialogPrograss.show();
                                if (null == mUploadWeChatInfoProtocol) {
                                    mUploadWeChatInfoProtocol = new UploadWeChatInfoProtocol(PerfectInforActivity.this, new BaseModel.OnResultListener<Boolean>() {
                                        @Override
                                        public void onResultListener(Boolean response) {
                                            if (response) {
                                                new GetNewUserInfoProtocol(PerfectInforActivity.this, new BaseModel.OnResultListener<UserBean>() {
                                                    @Override
                                                    public void onResultListener(UserBean response) {
                                                        mDialogPrograss.dismiss();
                                                        ToastUtils.showShortToast(PerfectInforActivity.this, "恭喜~成功绑定微信");
                                                        if (UserManager.getInst().hadLogin()) {
                                                            GlideUtils.displayCircleImage(PerfectInforActivity.this,
                                                                    UserManager.getInst().getUserBeam().getAvatarUrl(), mIvUserHeader,
                                                                    0, 0, GlideUtils.getUserNormalOptions(), true);
                                                            mTvNickname.setText(UserManager.getInst().getUserBeam().getNickName());
                                                        }
                                                        mDialogPrograss.dismiss();
                                                        checkBindWeChat();
                                                    }

                                                    @Override
                                                    public void onFailureListener(int code, String error) {
                                                        mDialogPrograss.dismiss();
                                                    }
                                                }).postRequest();
                                            } else {
                                                mDialogPrograss.dismiss();
                                                if (!isBinder) {
                                                    MenuChangeOberver.getInstance().updateStateChange();
                                                }
                                            }
                                            mUploadWeChatInfoProtocol = null;
                                        }

                                        @Override
                                        public void onFailureListener(int code, String error) {
                                            ToastUtils.showShortToast(PerfectInforActivity.this, error);
                                            mUploadWeChatInfoProtocol = null;
                                        }
                                    });
                                    mUploadWeChatInfoProtocol.uploadWeChatInfo(new WeChatBean(map.get("name"), map.get("uid"), map.get("openid")
                                            , map.get("gender").equals("男") ? 1 : 2, map.get("iconurl")
                                            , map.get("prvinice"), map.get("city"), map.get("country")
                                            , UserManager.getInst().getUserBeam().getId()));
                                }

                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                                if (throwable.toString().contains("没有安装应用")) {
                                    ToastUtils.showLongToast(PerfectInforActivity.this, "没有安装应用");
                                }
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media, int i) {
                                Log.e("TAG", "onCancel:" + i);
                            }
                        });
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == PHOTO_REQUEST_GALLERY) {// 从相册返回的数据
            Uri uri = data.getData();
            cropFile = CommonHelper.crop(uri, cropFile, PerfectInforActivity.this, Constants.PHOTO_REQUEST_CUT);
        } else if (requestCode == PHOTO_REQUEST_CAREMA) {// 从相机返回的数据
            if (CommonHelper.checkSDCard(PerfectInforActivity.this)) {
                cropFile = CommonHelper.crop(Uri.fromFile(tempFile), cropFile, PerfectInforActivity.this, PHOTO_REQUEST_CUT);
            } else {
                Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {// 从剪切图片返回的数据
            if (data != null && null != data.getExtras() && null != data.getExtras().get("data")) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                try {
                    if (null != bitmap) {
                        mDialogPrograss.show();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(cropFile));
                        // 上传头像到服务器
                        new UpLoadAvatarProtocol(this, cropFile.getAbsolutePath(), new BaseModel.OnResultListener<Boolean>() {
                            @Override
                            public void onResultListener(Boolean response) {
                                if (response) {
                                    mDialogPrograss.dismiss();
                                }
                            }

                            @Override
                            public void onFailureListener(int code, String error) {

                            }
                        }).postRequest();

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                // 上传头像到服务器
                mDialogPrograss.show();
                new UpLoadAvatarProtocol(this, cropFile.getAbsolutePath(), new BaseModel.OnResultListener<Boolean>() {
                    @Override
                    public void onResultListener(Boolean response) {
                        if (response) {
                            mDialogPrograss.dismiss();
                        }
                    }

                    @Override
                    public void onFailureListener(int code, String error) {

                    }
                }).postRequest();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(QuKanDianApplication.getmContext()).onActivityResult(requestCode, resultCode, data);
    }

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;

    @Override
    public void onCameraClickListener() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

        } else {
            takePhoto();
        }
        mDialogUserHeader.dismiss();
    }

    private void takePhoto() {
        if (CommonHelper.checkSDCard(PerfectInforActivity.this)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            tempFile = new File(CommonHelper.getFileJPGPath(getContext(), "user.jpg"));
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, Constants.PHOTO_REQUEST_CAREMA);
        } else {
            ToastUtils.showLongToast(getContext(), "内存卡不可用");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                new DialogPermissions(PerfectInforActivity.this).show();
            }

        }

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                new DialogPermissions(PerfectInforActivity.this).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onAlbumClickListener() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);
        } else {
            choosePhoto();
        }
        mDialogUserHeader.dismiss();
    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, Constants.PHOTO_REQUEST_GALLERY);
    }

    @Override
    public void onClickConfirmBtnListener(String age) {
        mTvAget.setText(age);
    }

    @Override
    public void onIconChange(String userIcon) {
        GlideUtils.displayCircleImage(getContext(), userIcon, mIvUserHeader, 0, 0, GlideUtils.getUserNormalOptions(), true);
    }

    @Override
    public void onNickNameChange(String nickName) {
        mTvNickname.setText(nickName);
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        UserManager.getInst().removeUserInfoListener(this);
        mLlUserHeader = null;
        mLlNickname = null;
        mLlAge = null;
        mLlBinderWeixin = null;
        mTvNickname = null;
        mTvAget = null;
        mIvUserHeader = null;
        tempFile = null;
        cropFile = null;
        mDialogUserHeader = null;
        mDialogUserAge = null;
    }
}
