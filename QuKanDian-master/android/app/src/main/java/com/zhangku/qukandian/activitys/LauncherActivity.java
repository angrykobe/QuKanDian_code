package com.zhangku.qukandian.activitys;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallAdapter;
import com.fm.openinstall.listener.AppWakeUpAdapter;
import com.fm.openinstall.model.AppData;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.OpenInstallBean;
import com.zhangku.qukandian.bean.QkdPushBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnAdSplashListener;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewRuleProtocol;
import com.zhangku.qukandian.protocol.GetNewUserInfoProtocol;
import com.zhangku.qukandian.protocol.GetReadTipsPro;
import com.zhangku.qukandian.protocol.GetTestMissionProtocol;
import com.zhangku.qukandian.receiver.BatteryReceiver;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AdCommonSplashHelp;
import com.zhangku.qukandian.utils.FileBuildForBugUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.MachineInfoUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ToastUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by yuzuoning on 2017/3/27.
 * 启动页
 */

public class LauncherActivity extends BaseAct implements EasyPermissions.PermissionCallbacks {
    private int mTimes = 5;
    private Timer mCount = null;
    private TextView mTextView;//跳过按钮
    private FrameLayout mFrameLayout;
    private RelativeLayout mSkipLayout;
    private BatteryReceiver mBatteryReceiver;

    public static final int RC_WRITE_PERM = 11;
    protected static final int RC_PHONE_PERM = 22;
    protected static final int RC_LOCATION = 33;
    private boolean clickAd = false;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            if (null != getIntent().getExtras()) {
                UserManager.getInst().mPushPostId = getIntent().getExtras().getInt(Constants.ID, -1);
                UserManager.getInst().mPushType = getIntent().getExtras().getInt(Constants.PUSH_TYPE);
                UserManager.getInst().mQkdPushBean = (QkdPushBean) intent.getSerializableExtra(Constants.INTENT_DATA);

                // 此处要调用，否则App在后台运行时，会无法截获
                OpenInstall.getWakeUp(intent, wakeUpAdapter);
            }
        } catch (Exception e) {
        }
    }

    //判断是否有读写权限
    protected boolean hasWritePermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    //判断是否有获取设备号
    protected boolean hasPhonePermission() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE);
    }

    protected boolean hasLocation() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasWritePermission()) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        RC_WRITE_PERM);
            } else if (!hasPhonePermission()) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        RC_PHONE_PERM);
            } else if (!hasLocation()) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        RC_LOCATION);
            } else {
                setOnlyMark();
                initUserInfo();
            }
        } else {
            setOnlyMark();
            initUserInfo();
        }
//        if (null != getIntent().getExtras()) {
//            LogUtils.LogE("------------yoooo");
//            UserManager.getInst().mPushPostId = getIntent().getExtras().getInt(Constants.ID, -1);
//            UserManager.getInst().mPushType = getIntent().getExtras().getInt(Constants.PUSH_TYPE);
//            LogUtils.LogE("------------UserManager.getInst().mPushPostId="+UserManager.getInst().mPushPostId);
//
//        }
//        qukandian://launch/
        //  qukandian://launch?name=miss&age=8"
//        Intent intent1 = getIntent();
//        Uri uri1=intent1.getData();
//        if(uri1!=null){
//            String name=uri1.getQueryParameter("name");
//            String age=uri1.getQueryParameter("age");
//            String scheme= uri1.getScheme();
//            String host=uri1.getHost();
//            String port=uri1.getPort()+"";
//            String path=uri1.getPath();
//            String query=uri1.getQuery();
//        }else{
//
//        }

        //获取唤醒参数
        OpenInstall.getWakeUp(getIntent(), wakeUpAdapter);
        getInstallData();
    }

    AppWakeUpAdapter wakeUpAdapter = new AppWakeUpAdapter() {
        @Override
        public void onWakeUp(AppData appData) {
            //获取渠道数据
            String channelCode = appData.getChannel();
            //获取绑定数据
            String bindData = appData.getData();
            Log.d("OpenInstall", "getWakeUp : wakeupData = " + appData.toString());
//            ToastUtils.showShortToast(LauncherActivity.this, "getWakeUp : wakeupData = " + appData.toString());
        }
    };

    //携带参数安装
    private void getInstallData() {
        OpenInstall.getInstall(new AppInstallAdapter() {
            @Override
            public void onInstall(AppData appData) {
                //获取渠道数据
                String channelCode = appData.getChannel();
                //获取自定义数据
                String bindData = appData.getData();
                Log.d("OpenInstall", "getInstall : installData = " + appData.toString());
                try {
                    if (TextUtils.isEmpty(bindData)) return;
                    OpenInstallBean openInstallBean = new Gson().fromJson(bindData, OpenInstallBean.class);
                    ToastUtils.showShortToast(LauncherActivity.this, "getInviterId = " + openInstallBean.getInviterId());
                    if ((UserSharedPreferences.getInstance().getLong(Constants.INVITATION_CODE, 0) == 0)) {
                        UserSharedPreferences.getInstance().putLong(Constants.INVITATION_CODE, openInstallBean.getInviterId());
                        UserSharedPreferences.getInstance().putString(Constants.INVITATION_SOURCE, openInstallBean.getSource());
                    }
                } catch (Exception e) {

                }

            }
        });
    }

    private void initUserInfo() {
        UserManager.getInst().setLaunch(true);
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String name = uri.getQueryParameter("key");
                UserManager.KEY = name;
            }
        }
        mBatteryReceiver = new BatteryReceiver();
        registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        mFrameLayout = findViewById(R.id.launch_layout);
        mSkipLayout = (RelativeLayout) findViewById(R.id.launch_layout_skip_layout);
        mTextView = (TextView) findViewById(R.id.launch_layout_time);
        mCount = new Timer();

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTo();
            }
        });

        try {
            if (null != getIntent().getExtras()) {
                UserManager.getInst().mPushPostId = getIntent().getExtras().getInt(Constants.ID, -1);
                UserManager.getInst().mPushType = getIntent().getExtras().getInt(Constants.PUSH_TYPE);

                UserManager.getInst().mQkdPushBean = (QkdPushBean) intent.getSerializableExtra(Constants.INTENT_DATA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (UserSharedPreferences.getInstance().getInt(Constants.ISCLEAR) != QuKanDianApplication.getCode()) {
            UserSharedPreferences.getInstance().putString(Constants.PATH, "");
            UserSharedPreferences.getInstance().putString(Constants.TIMER, "");
            UserSharedPreferences.getInstance().putBoolean(Constants.NOREMIND, false);
            UserSharedPreferences.getInstance().putBoolean(Constants.NOREMIND1, false);
            UserSharedPreferences.getInstance().putInt(Constants.ISCLEAR, QuKanDianApplication.getCode());
            GlideUtils.clearImageAllCache(this, false);
        }
        AdCommonSplashHelp.getInstance().getAdResult(LauncherActivity.this, mFrameLayout, mSkipLayout, new OnAdSplashListener() {
            @Override
            public void adSuccess() {
                if (null != mTextView)
                    mTextView.setVisibility(View.VISIBLE);
                startTimmer();
            }

            @Override
            public void adFail() {
                if (null != mTextView) mTextView.setVisibility(View.VISIBLE);
                if (!clickAd && isFront) {
                    startTo();
                }
            }

            @Override
            public void adClick() {

            }
        });
        //????
        if (UserManager.getInst().hadLogin()) {//已登录
            if (UserSharedPreferences.getInstance().getLong(Constants.TOKEN_RECORD_IN) != 0
                    && System.currentTimeMillis() - UserSharedPreferences.getInstance().getLong(Constants.TOKEN_RECORD_IN)
                    > UserSharedPreferences.getInstance().getLong(Constants.TOKEN_EXPIRES_IN)) {
                UserManager.getInst().logout(this);
            } else {
                if (null != mTextView) {
                    // mTextView.setVisibility(View.VISIBLE);
                }
                new GetNewUserInfoProtocol(LauncherActivity.this, new BaseModel.OnResultListener<UserBean>() {
                    @Override
                    public void onResultListener(UserBean response) {
//                        if (null != response) {
//                            MobclickAgent.onProfileSignIn(response.getId() + "");
//                        }
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        // new GetNewAdListProtocol(LauncherActivity.this).postRequest();
                    }
                }).postRequest();
            }
            JPushInterface.cleanTags(this, 1);
        } else {
            HashSet<String> list = new HashSet<>();
            list.add("unregistered");
            JPushInterface.setTags(this, 1, list);
            if (null != mTextView) {
                // mTextView.setVisibility(View.VISIBLE);
            }
            // new GetNewAdListProtocol(LauncherActivity.this).postRequest();
        }

        new GetNewRuleProtocol(this, null).postRequest();
        //获取测试任务
        new GetTestMissionProtocol(this, new BaseModel.OnResultListener() {
            @Override
            public void onResultListener(Object response) {

            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();

        //new GetReadTipsPro(this, null).postRequest();
    }

    private void startTimmer() {
        if (mCount == null) return;
        mCount.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mTextView == null) return;
                        mTimes--;
                        mTextView.setText(mTimes + " 跳过");
                        if (mTimes == 0) {
                            cancelCount();
                            if (!clickAd && isFront) {
                                startTo();
                            }
                        }
                    }
                });
            }
        }, 1000, 1000);
    }


    private void setOnlyMark() {
        if (TextUtils.isEmpty(UserSharedPreferences.getInstance().getString(Constants.ONLY_MARK, ""))) {
            MachineInfoUtil.getInstance().getIMEI(this);
        }
    }

    private boolean hadStartTo = false;

    private void startTo() {
        if (!hadStartTo) {
            hadStartTo = true;
            //判断是否第一只登录，或者已经登录，或者登录超过14填
            if (UserSharedPreferences.getInstance().getBoolean(Constants.FIRST_LOGIN, true)) {
                ActivityUtils.startToGuideActivity(LauncherActivity.this);
                finish();
            } else if (UserManager.getInst().hadLogin()
                    && (System.currentTimeMillis() - UserSharedPreferences.getInstance().getLong(Constants.TOKEN_RECORD_IN)) / 1000 / 60 / 60 / 24 > 14) {
                UserManager.getInst().logout(LauncherActivity.this);
                finish();
            } else {
                ActivityUtils.startToMainActivity(LauncherActivity.this, Constants.TAB_INFORMATION, 0);
                finish();
            }
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_launcher_layout;
    }

    @Override
    protected String setTitle() {
        return "";
    }

    @Override
    protected void myOnClick(View v) {

    }

    private boolean isFront = false;

    @Override
    protected void onResume() {
//        //判断是否该跳转到主页面  部分手机推送跳转有问题
        if (clickAd) {
            startTo();
        }
        super.onResume();
        isFront = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFront = false;
    }

    @Override
    protected void onStop() {
        clickAd = true;
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBatteryReceiver != null)
            unregisterReceiver(mBatteryReceiver);

        cancelCount();

        wakeUpAdapter = null;
    }

    private void cancelCount() {
        if (null != mCount) {
            mCount.cancel();
            mCount = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        requestPer(requestCode);
    }

    //权限被拒绝
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        requestPer(requestCode);
    }

    private void requestPer(int requestCode) {
        switch (requestCode) {
            case RC_WRITE_PERM:
                if (!hasPhonePermission()) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            RC_PHONE_PERM);
                } else if (!hasLocation()) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            RC_LOCATION);
                } else {
                    setOnlyMark();
                    initUserInfo();
                }
                break;
            case RC_PHONE_PERM:
                if (!hasLocation()) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            RC_LOCATION);
                } else {
                    setOnlyMark();
                    initUserInfo();
                }
                break;
            case RC_LOCATION:
                setOnlyMark();
                initUserInfo();
                break;
        }
    }
}
