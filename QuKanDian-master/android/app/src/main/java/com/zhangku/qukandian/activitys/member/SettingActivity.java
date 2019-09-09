package com.zhangku.qukandian.activitys.member;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.MainActivity;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.UpdateBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogUpdate;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.UpdateProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/3/31.
 * 设置界面
 */

public class SettingActivity extends BaseTitleActivity implements View.OnClickListener {
    private LinearLayout mLlUpdatePassword;
    private LinearLayout mLlPerfectInfor;
    private LinearLayout mLlUpdateCheck;
    private LinearLayout mLlClearCache;
    private LinearLayout mLlFeedback;
    private LinearLayout mLlQukandianScore;
    private LinearLayout mLlQukandianAbout;
    private TextView mBtnCancleLogin;
    private TextView mTvCacheNumber;
    private ImageView mIvPositionRed;
    private DialogUpdate mDialogUpdate;

    @Override
    protected void initActionBarData() {
        setTitle("设置");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new ArrayMap<>();
        map.put("to", "设置");
        MobclickAgent.onEvent(this, "AllPv", map);
    }

    @Override
    protected void initViews() {
        mLlUpdateCheck = (LinearLayout) findViewById(R.id.activity_setting_layout_update);
        mLlUpdatePassword = (LinearLayout) findViewById(R.id.activity_setting_layout_update_password);
        mLlPerfectInfor = (LinearLayout) findViewById(R.id.activity_setting_layout_perfect_infor);
        mTvCacheNumber = (TextView) findViewById(R.id.activity_setting_layout_clear_cache_number);
        mLlClearCache = (LinearLayout) findViewById(R.id.activity_setting_layout_clear_cache);
        mLlFeedback = (LinearLayout) findViewById(R.id.activity_setting_layout_feedback);
        mLlQukandianScore = (LinearLayout) findViewById(R.id.activity_setting_layout_score);
        mLlQukandianAbout = (LinearLayout) findViewById(R.id.activity_setting_layout_about);
        mBtnCancleLogin = (TextView) findViewById(R.id.activity_setting_layout_cancel_login);
        mIvPositionRed = (ImageView) findViewById(R.id.activity_setting_layout_update_position_red);

        Switch goldSoundSwitch = findViewById(R.id.goldSoundSwitch);

        cancleBtnStatus();

        if (UserSharedPreferences.getInstance().getBoolean(Constants.UPDATE, false)) {
            mIvPositionRed.setVisibility(View.VISIBLE);
        } else {
            mIvPositionRed.setVisibility(View.GONE);
        }
        boolean aBoolean = UserSharedPreferences.getInstance().getBoolean(Constants.ART_GOLD_SOUND, true);
        goldSoundSwitch.setChecked(aBoolean);
        goldSoundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserSharedPreferences.getInstance().putBoolean(Constants.ART_GOLD_SOUND, isChecked);
            }
        });

        mTvCacheNumber.setText(GlideUtils.getCacheSize(this));
        mLlUpdatePassword.setOnClickListener(this);
        mLlPerfectInfor.setOnClickListener(this);
        mLlClearCache.setOnClickListener(this);
        mLlFeedback.setOnClickListener(this);
        mLlQukandianScore.setOnClickListener(this);
        mLlQukandianAbout.setOnClickListener(this);
        mBtnCancleLogin.setOnClickListener(this);
        mLlUpdateCheck.setOnClickListener(this);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_setting_layout;
    }

    @Override
    public String setPagerName() {
        return "设置";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_setting_layout_update_password:
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToForgetPasswordActivity(SettingActivity.this);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(SettingActivity.this);
                }
                break;
            case R.id.activity_setting_layout_perfect_infor:
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToPerfectActivity(SettingActivity.this);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(SettingActivity.this);
                }
                break;
            case R.id.activity_setting_layout_clear_cache:
                GlideUtils.clearImageAllCache(SettingActivity.this, true);
                mTvCacheNumber.setText(GlideUtils.getCacheSize(SettingActivity.this));
                break;
            case R.id.activity_setting_layout_feedback:
                ActivityUtils.startToFeedbackActivity(this);
                break;
            case R.id.activity_setting_layout_score:
                if (hasAnyMarketInstalled(this)) {
                    Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    ToastUtils.showShortToast(this, "您还没有安装应用市场");
                }
                break;
            case R.id.activity_setting_layout_about:
                ActivityUtils.startToAboutActivity(this);
                break;
            case R.id.activity_setting_layout_update:
                updateProtocol();
                break;
            case R.id.activity_setting_layout_cancel_login:
                mBtnCancleLogin.setVisibility(View.GONE);
                UserManager.getInst().logout(this);
                finish();
                break;
        }
    }

    private void cancleBtnStatus() {
        if (UserManager.getInst().hadLogin()) {
            mBtnCancleLogin.setVisibility(View.VISIBLE);
        } else {
            mBtnCancleLogin.setVisibility(View.GONE);
        }
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mLlUpdatePassword = null;
        mLlPerfectInfor = null;

        mLlClearCache = null;
        mLlFeedback = null;
        mLlQukandianScore = null;
        mLlQukandianAbout = null;
        mBtnCancleLogin = null;
        mTvCacheNumber = null;
    }

    public static boolean hasAnyMarketInstalled(Context context) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("market://details?id=android.browser"));
        List list = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return 0 != list.size();

    }

    private void updateProtocol() {
        if (mHandler == null) return;
        mDialogPrograss.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new UpdateProtocol(SettingActivity.this, new BaseModel.OnResultListener<ArrayList<UpdateBean>>() {
                    @Override
                    public void onResultListener(ArrayList<UpdateBean> response) {
                        mDialogPrograss.dismiss();
                        int maxVersionCode = 1;
                        int tem = 0;
                        for (int i = 0; i < response.size(); i++) {
                            maxVersionCode = response.get(i).getBuildAndroidNo() > maxVersionCode ?
                                    response.get(i).getBuildAndroidNo() : maxVersionCode;
                            if (response.get(i).getBuildAndroidNo() > maxVersionCode) {
                                tem = i;
                                maxVersionCode = response.get(i).getBuildAndroidNo();
                            }
                        }

                        if (QuKanDianApplication.getCode() < maxVersionCode) {
                            UserSharedPreferences.getInstance().putBoolean(Constants.UPDATE, true);
                            int level = response.get(tem).getLevel();
                            if (level == 0) {// 都跟新
                                showUpdateDialog(response, tem, level);
                            } else if (level == 1) {//  灰度userid小于 10000+grayscaleNum 更新
                                if (UserManager.getInst().hadLogin()) {
                                    if (UserManager.getInst().getUserBeam().getId() - 10000 <= 0) {
                                        showUpdateDialog(response, tem, level);
                                    }
                                }
                            } else if (level == 2) {//灰度更新
                                if (UserManager.getInst().getUserBeam().isGrayUser()) {
                                    showUpdateDialog(response, tem, level);
                                }
                            } else if (level == 3) {//灰度更新
                                //缓存的线上版本号
                                int appReleaseVersion = UserSharedPreferences.getInstance().getInt(Constants.APP_RELEASE_VERSION, 0);
                                if (maxVersionCode >= appReleaseVersion) {//检测更新特殊处理（即使首页的已经设置 下次不再提醒，检测时依旧会弹窗）
                                    //下次不再提醒  设置为  false
                                    UserSharedPreferences.getInstance().putBoolean(Constants.IS_UPDATE_IGNORE, false);
                                }
                                //缓存线上版本号
                                UserSharedPreferences.getInstance().putInt(Constants.APP_RELEASE_VERSION, maxVersionCode);
                                showUpdateDialog(response, tem, level);
                            }
                        } else {
                            UserSharedPreferences.getInstance().putBoolean(Constants.UPDATE, false);
                        }
                    }

                    @Override
                    public void onFailureListener(int code, String error) {

                    }
                }).checkUpdate();
            }
        }, 3000);
    }


    private void showUpdateDialog(ArrayList<UpdateBean> response, int tem, int level) {
        UpdateBean updateBean = response.get(tem);
        int historyIgnoreVer = UserSharedPreferences.getInstance().getInt(Constants.IGNORE_VERSION, 0);
        //类型3：是否  下次不再提醒 ---  默认不是
        boolean isUpdateIgnore = UserSharedPreferences.getInstance().getBoolean(Constants.IS_UPDATE_IGNORE, false);
        if (QuKanDianApplication.getCode() > historyIgnoreVer//无缓存弹升级弹框
                || updateBean.isIsAndroidForce()//强制升级
                || (level == 3 && QuKanDianApplication.getCode() == historyIgnoreVer && !isUpdateIgnore)) {//下次不再提醒：否  ，本次忽略的 下次打开会弹窗
            showUpdate(updateBean, level);
        }
    }

    private void showUpdate(UpdateBean updateBean, int level) {
        DialogUpdate dialogUpdate = new DialogUpdate(SettingActivity.this, new DialogUpdate.OnClickFinishListener() {
            @Override
            public void onClickFinishListener() {
                finish();
            }
        });
        dialogUpdate.show();
        dialogUpdate.setContent(updateBean.getTitle(), updateBean.getDescription(), updateBean.getUpdateUrl(), updateBean.getBuildAndroidNo());
        dialogUpdate.setUpdate(level, updateBean.isIsAndroidForce());
    }
}
