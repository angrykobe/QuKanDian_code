package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.QukandianBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.download.InstallUtils;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.service.DownloadService;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.io.File;

/**
 * Created by yuzuoning on 2017/4/25.
 */

public class DialogUpdate extends BaseDialog implements View.OnClickListener {
    private ImageView mIvCancel;
    private TextView mTvVersionNema;
    private TextView mTvContent;
    private TextView mTvUpdateRedBtn;
    private TextView mTvExiteBtn;
    private TextView mTvUpdateBtn;
    private LinearLayout mBtnLayout;

    private LinearLayout mUpdateLayoutBg;
    private LinearLayout mWifiRemindLayout;
    private TextView mTvWifiCancel;
    private TextView mTvWifiContinue;

    private LinearLayout mLlUpdateType3;//更新提示接口返回的第三种情况
    private CheckBox mChbUpdateIgnore;//下次不再提醒
    private TextView mTvUpdateType3Ignore;//本次忽略
    private TextView mTvUpdateType3Update;//立即更新

    private boolean isDissmis = false;//
    private String mUrl = "";
    private OnClickFinishListener mOnClickFinishListener;
    private int mVersion = 0;


    public DialogUpdate(Context context, OnClickFinishListener onClickFinishListener) {
        super(context, R.style.zhangku_dialog);
        mOnClickFinishListener = onClickFinishListener;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_update;
    }

    @Override
    protected void initView() {
        mUpdateLayoutBg = (LinearLayout) findViewById(R.id.update_bg);
        mIvCancel = (ImageView) findViewById(R.id.dialog_update_cancel);
        mTvUpdateBtn = (TextView) findViewById(R.id.dialog_update_update_btn);
        mTvExiteBtn = (TextView) findViewById(R.id.dialog_update_exite);
        mTvUpdateRedBtn = (TextView) findViewById(R.id.dialog_update_red_update_btn);
        mTvVersionNema = (TextView) findViewById(R.id.dialog_update_version_name);
        mTvContent = (TextView) findViewById(R.id.dialog_update_content);
        mBtnLayout = (LinearLayout) findViewById(R.id.dialog_update_update_btn_layout);
        mWifiRemindLayout = (LinearLayout) findViewById(R.id.wifi_status);
        mTvWifiCancel = (TextView) findViewById(R.id.dialog_update_exite_wifi);
        mTvWifiContinue = (TextView) findViewById(R.id.dialog_update_update_wifi_continue);

        //更新弹窗类型3
        mLlUpdateType3 = (LinearLayout) findViewById(R.id.ll_update_type3);
        mChbUpdateIgnore = (CheckBox) findViewById(R.id.chb_update_ignore);
        mTvUpdateType3Ignore = (TextView) findViewById(R.id.tv_update_type3_ignore);
        mTvUpdateType3Update = (TextView) findViewById(R.id.tv_update_type3_update);

        mIvCancel.setOnClickListener(this);
        mTvUpdateRedBtn.setOnClickListener(this);
        mTvExiteBtn.setOnClickListener(this);
        mTvUpdateBtn.setOnClickListener(this);
        mTvWifiCancel.setOnClickListener(this);
        mTvWifiContinue.setOnClickListener(this);

        mTvUpdateType3Ignore.setOnClickListener(this);
        mTvUpdateType3Update.setOnClickListener(this);
        setCanceledOnTouchOutside(false);


        mChbUpdateIgnore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    UserSharedPreferences.getInstance().putBoolean(Constants.IS_UPDATE_IGNORE, true);
                } else {
                    UserSharedPreferences.getInstance().putBoolean(Constants.IS_UPDATE_IGNORE, false);
                }
            }
        });
    }

    public void setContent(String v, String content, String url, int version) {
        mTvVersionNema.setText("("+v+")");
        mTvContent.setText(content);
        mUrl = url;
        mVersion = version;
    }

    public void setUpdate(boolean update) {
        isDissmis = update;
        if (update) {
            mBtnLayout.setVisibility(View.VISIBLE);
            mTvUpdateRedBtn.setVisibility(View.GONE);
            mIvCancel.setVisibility(View.GONE);
            mLlUpdateType3.setVisibility(View.GONE);
            setCanceledOnTouchOutside(false);
        } else {
            mBtnLayout.setVisibility(View.GONE);
            mLlUpdateType3.setVisibility(View.GONE);
            mTvUpdateRedBtn.setVisibility(View.VISIBLE);
            mIvCancel.setVisibility(View.VISIBLE);
            setCanceledOnTouchOutside(true);
        }
    }

    public void setUpdate(int level, boolean update) {
        isDissmis = update;
        if (update) {
            //强制
            mBtnLayout.setVisibility(View.VISIBLE);
            mTvUpdateRedBtn.setVisibility(View.GONE);
            mIvCancel.setVisibility(View.GONE);
            mLlUpdateType3.setVisibility(View.GONE);
            setCanceledOnTouchOutside(false);
        } else {
            if (level != 3) {//2.9.6.4之前旧的更新提示
                mBtnLayout.setVisibility(View.GONE);
                mLlUpdateType3.setVisibility(View.GONE);
                mTvUpdateRedBtn.setVisibility(View.VISIBLE);
                mIvCancel.setVisibility(View.VISIBLE);
                setCanceledOnTouchOutside(true);
            } else {//更新类型3
                mBtnLayout.setVisibility(View.GONE);
                mTvUpdateRedBtn.setVisibility(View.GONE);
                mIvCancel.setVisibility(View.GONE);
                mLlUpdateType3.setVisibility(View.VISIBLE);
                setCanceledOnTouchOutside(true);
            }
        }

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
            case R.id.dialog_update_cancel:
            case R.id.dialog_update_exite_wifi:
                UserSharedPreferences.getInstance().putInt(Constants.IGNORE_VERSION, QuKanDianApplication.getCode());
//                DBTools.addIgnoreVersion(mVersion);
                dismiss();
                break;
            case R.id.dialog_update_exite:
                if (null != mOnClickFinishListener) {
                    mOnClickFinishListener.onClickFinishListener();
                }
                dismiss();
                break;
            case R.id.dialog_update_update_wifi_continue:
                download();
                new DialogUpdateProgress(getContext()).show();
                mWifiRemindLayout.setVisibility(View.GONE);
                if (!isDissmis) {
                    dismiss();
                }
                break;
            case R.id.dialog_update_update_btn://更新
            case R.id.dialog_update_red_update_btn:
            case R.id.tv_update_type3_update:
                if (!TextUtils.isEmpty(UserSharedPreferences.getInstance().getString(Constants.PATH, ""))) {
                    File file = new File(UserSharedPreferences.getInstance().getString(Constants.PATH, ""));
                    if (file.exists()) {
                        InstallUtils.installAPK(getContext(), UserSharedPreferences.getInstance().getString(Constants.PATH, ""));
                    }
                    if (!isDissmis) {
                        dismiss();
                    }
                } else {
                    if (CommonHelper.isWifi(getContext())) {
                        download();
                        new DialogUpdateProgress(getContext()).show();
                        if (!isDissmis) {
                            dismiss();
                        }
                    } else {
                        mWifiRemindLayout.setVisibility(View.VISIBLE);
                        mUpdateLayoutBg.setVisibility(View.GONE);
                    }
                }
                //无视  下次不再提醒
                UserSharedPreferences.getInstance().putBoolean(Constants.IS_UPDATE_IGNORE, false);
                break;
            case R.id.tv_update_type3_ignore://本次忽略
                UserSharedPreferences.getInstance().putInt(Constants.IGNORE_VERSION, QuKanDianApplication.getCode());
                if (!isDissmis) {
                    dismiss();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isDissmis) {
                dismiss();
            }
            return isDissmis;
        }
        return super.onKeyDown(keyCode, event);
    }

    public interface OnClickFinishListener {
        void onClickFinishListener();
    }

    public void download() {
        if (mUrl.startsWith("http://") || mUrl.startsWith("https://")) {
            download(mUrl);
        } else {
            download("http://" + mUrl);
        }
    }

    private void download(String url) {
        Intent intent = new Intent(getContext(), DownloadService.class);
        intent.putExtra("name", "qukandianapk");
        intent.putExtra("url", url);
        getContext().startService(intent);
    }
}
