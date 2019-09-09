package com.zhangku.qukandian.activitys;

import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.ChannelBean;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.EventBusBean.ShowNewPeopleTaskDialogBean;
import com.zhangku.qukandian.bean.EventBusBean.TaskNewPeopleEvent;
import com.zhangku.qukandian.bean.LogUpBean;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.bean.QkdPushBean;
import com.zhangku.qukandian.bean.QukandianBean;
import com.zhangku.qukandian.bean.ReadProgressBean;
import com.zhangku.qukandian.bean.RuleBean;
import com.zhangku.qukandian.bean.ShareBean;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.bean.UpdateBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogExitTip;
import com.zhangku.qukandian.dialog.DialogMeOperate;
import com.zhangku.qukandian.dialog.DialogNewPeopleRedGotGold;
import com.zhangku.qukandian.dialog.DialogNewPeopleRedPacket3;
import com.zhangku.qukandian.dialog.DialogNewPeopleRefuse;
import com.zhangku.qukandian.dialog.DialogNewPeopleTask;
import com.zhangku.qukandian.dialog.DialogOperate;
import com.zhangku.qukandian.dialog.DialogRecommendedDaily;
import com.zhangku.qukandian.dialog.DialogUpdate;
import com.zhangku.qukandian.fragment.TaskFragment;
import com.zhangku.qukandian.fragment.Video.VideoFragment;
import com.zhangku.qukandian.fragment.information.InformationFragment;
import com.zhangku.qukandian.fragment.me.MeFragment;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.PushOberver;
import com.zhangku.qukandian.protocol.AdsLogUpProtocol;
import com.zhangku.qukandian.protocol.GetInformationChannelTabProtocol;
import com.zhangku.qukandian.protocol.GetNewTaskForNewPeoplePro;
import com.zhangku.qukandian.protocol.GetShareImgProtocol;
import com.zhangku.qukandian.protocol.PutNewTaskForNewRedProtocol;
import com.zhangku.qukandian.protocol.QukandianNewProtocol;
import com.zhangku.qukandian.protocol.UpdateProtocol;
import com.zhangku.qukandian.receiver.jPush.MyReceiver;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AppUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.MachineInfoUtil;
import com.zhangku.qukandian.utils.MySensorManager;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.CacheManage;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.SpConstants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.SpUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.SmartView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/21
 * 你不注释一下？
 */
public class MainActivity extends BaseAct implements UserManager.IOnLoginStatusLisnter, PushOberver.OnPushOperateListener {

    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentTransaction transaction;

    private InformationFragment mInformationFragment;
    private Fragment mNewMeFragment;
    private TaskFragment mNewTaskFragment;
    private VideoFragment mVideoFragment;

    public SmartView mSmartView;
    private ImageView mBottomActIV;
    private ImageView mBottomHomeIV;
    private View mLastTouchView;
    private View mBottomTaskSpotView;
    private View mBottomMeSpotView;
    private long mRecoreTime;
    private GetNewTaskForNewPeoplePro mGetNewTaskForNewPeoplePro;
    private DialogOperate mDialogOperate;
    private DialogRecommendedDaily mDialogRecommendedDaily;
    private DialogNewPeopleRedPacket3 mDialogNewPeopleRedPacket;
    private DialogNewPeopleTask mDialogNewPeopleTask;

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        pushAbout();
        if (null != intent.getExtras()) {
            ChannelBean bean = (ChannelBean) intent.getSerializableExtra(Constants.CHANNEL_BEAN);
            if (bean == null || TextUtils.isEmpty(bean.getDisplayName())) {
                int tab = intent.getExtras().getInt(Constants.MAIN_TAB, 0);
                int channel = intent.getExtras().getInt(Constants.CHANNEL_TAB, 0);
                switch (tab) {
                    case 0:
                        iniFragment(tab, findViewById(R.id.mBottomHomeView));
                        mInformationFragment.setTab(channel);
                        mInformationFragment.setCurrentTab(channel);
                        break;
                    case 1:
                        iniFragment(tab, findViewById(R.id.mBottomVideoView));
                        break;
                    case 2:
                        iniFragment(tab, findViewById(R.id.mBottomTaskView));
                        break;
                    case 3:
                        iniFragment(tab, findViewById(R.id.mBottomMeView));
                        break;
                }
            } else {
                mInformationFragment.setCurChannel(bean);
            }
//            boolean show = intent.getExtras().getBoolean("show", false);
//            if(show){
//                showNewPeopleTaskDialogBus(new ShowNewPeopleTaskDialogBean());
//            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (Math.abs(mRecoreTime - System.currentTimeMillis()) < 1400) {
                finishMe();
            } else {
                if (!isShowDialogExit()) {
                    ToastUtils.showShortToast(this, "再按一次退出");
                }
            }
            mRecoreTime = System.currentTimeMillis();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void finishMe() {
        MobclickAgent.onProfileSignOff();
        CommonHelper.copy(this, "", "");
        finish();
    }

    //显示阅读打卡没完成任务时的退出弹窗
    private boolean isShowDialogExit() {
        boolean isShow = false;
        if (UserBean.getShowReadProgress()) {//权限判断
            if (UserManager.getInst().getQukandianBean().getReadprogress() == null)
                return false;
            ArrayList<ReadProgressBean.ProgressRuleBean> list = UserManager.getInst().getQukandianBean().getReadprogress().getProgressRule();
            //当前阅读数量
            int nowRead = UserManager.adsProgressCnt;
            if (list != null && list.size() > 0) {
                for (ReadProgressBean.ProgressRuleBean bean : list) {
                    if (0 <= nowRead && nowRead < bean.getAdsCnt()) {//比较当前阅读数 是否 还没完成
                        int sum = bean.getAdsCnt() - nowRead;//剩余阅读篇数
                        showDialogExitTip(sum, bean.getGold());
                        return true;
                    }
                }
            }
        }
        return isShow;
    }

    private void showDialogExitTip(int sum, int gold) {
        DialogExitTip mDialogExit = new DialogExitTip(this, sum, gold, new DialogExitTip.OnFinishListener() {
            @Override
            public void onFinishListener() {
                MobclickAgent.onEvent(MainActivity.this, "296-jixuzhuanqian1");
                MobclickAgent.onEvent(MainActivity.this, "296-jixuzhuanqian2");
                finishMe();
            }
        });
        MobclickAgent.onEvent(MainActivity.this, "296-tuichuquerenxianshi");
        mDialogExit.show();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mInformationFragment = new InformationFragment();
        mVideoFragment = new VideoFragment();
        mNewTaskFragment = new TaskFragment();
        mNewMeFragment = new MeFragment();
        fragmentList.add(mInformationFragment);
        fragmentList.add(mVideoFragment);
        fragmentList.add(mNewTaskFragment);
        fragmentList.add(mNewMeFragment);

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contentPanel, fragmentList.get(0))
                .add(R.id.contentPanel, fragmentList.get(1))
                .add(R.id.contentPanel, fragmentList.get(2))
                .add(R.id.contentPanel, fragmentList.get(3))
                .commit();

        mSmartView = findViewById(R.id.mSmartView);
        mBottomActIV = findViewById(R.id.mBottomActIV);
        mBottomHomeIV = findViewById(R.id.mBottomHomeIV);
        mBottomTaskSpotView = findViewById(R.id.mBottomTaskSpotView);
        mBottomMeSpotView = findViewById(R.id.mBottomMeSpotView);

        View homeView = findViewById(R.id.mBottomHomeView);
        homeView.setOnClickListener(this);
        findViewById(R.id.mBottomVideoView).setOnClickListener(this);
        findViewById(R.id.mBottomActIV).setOnClickListener(this);
        findViewById(R.id.mBottomTaskView).setOnClickListener(this);
        findViewById(R.id.mBottomMeView).setOnClickListener(this);

        if (TextUtils.isEmpty(UserSharedPreferences.getInstance().getString(Constants.ONLY_MARK, ""))) {
            MachineInfoUtil.getInstance().getIMEI(this);
        }


        new GetAppInfoTask().execute();

        UserManager.getInst().addLoginListener(this);
        PushOberver.getInstance().addPushOperateListener(this);

//        showDialog(UserManager.getInst().hadLogin());//替换到onResume 调用
        initActView();
        pushAbout();
        new QukandianNewProtocol(this, new BaseModel.OnResultListener<QukandianBean>() {
            @Override
            public void onResultListener(QukandianBean response) {
                getChannelList(response.getChannelVersion());

                mInformationFragment.getIconShow();

                adsLogUp();
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();
        updateProtocol(0);//取消根据数值小于多少来更新灰度
        new GetShareImgProtocol(MainActivity.this, new BaseModel.OnResultListener<ShareBean>() {
            @Override
            public void onResultListener(ShareBean response) {
                UserManager.mShareBean = response;
                UserManager.getInst().getmRuleBean().getShoutuPosterConfig();
                for (int i = 0; i < response.getShareFrientPosterItems().size(); i++) {
                    GlideUtils.loadImage(MainActivity.this, response.getShareFrientPosterItems().get(i).getImageLink(), new GlideUtils.OnLoadImageListener() {
                        @Override
                        public void onSucess(Bitmap bitmap, String url) {
                            UserManager.mBitmaps.add(bitmap);
                        }

                        @Override
                        public void onFail(Drawable errorDrawable) {

                        }
                    });
                }
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();
//        iniFragment(0, homeView);
        EventBus.getDefault().register(this);

        int tab = 0;
        int channel = 0;
        if (null != getIntent().getExtras()) {
            tab = getIntent().getExtras().getInt(Constants.MAIN_TAB);
            channel = getIntent().getExtras().getInt(Constants.CHANNEL_TAB);
        }
        switch (tab) {
            case 0:
                iniFragment(tab, findViewById(R.id.mBottomHomeView));
                mInformationFragment.setTab(channel);
                mInformationFragment.setCurrentTab(channel);
                break;
            case 1:
                iniFragment(tab, findViewById(R.id.mBottomVideoView));
                break;
            case 2:
                iniFragment(tab, findViewById(R.id.mBottomTaskView));
                break;
            case 3:
                iniFragment(tab, findViewById(R.id.mBottomMeView));
                break;
        }

        if (UserManager.getInst().getmRuleBean() != null && UserManager.getInst().getmRuleBean().getMyPageToast() != null) {
            List<String> imgs = UserManager.getInst().getmRuleBean().getMyPageToast().getImgs();
            if (imgs != null) {
                for (int i = 0; i < imgs.size(); i++) {
                    GlideUtils.preload(this, imgs.get(i));
                }
            }
        }


    }

    //获取文章频道列表
    private void getChannelList(int channelVersion) {
        int appVersion = QuKanDianApplication.getCode();
        int tempVersion = UserSharedPreferences.getInstance().getInt(Constants.App_VERSION);
        if (appVersion != tempVersion) {
            UserSharedPreferences.getInstance().putInt(Constants.CHANNEL_VERSION, -1);//让其重新加载频道信息
            UserSharedPreferences.getInstance().putInt(Constants.App_VERSION, appVersion);//缓存版本号
        }
        if (UserSharedPreferences.getInstance().getInt(Constants.CHANNEL_VERSION) != 0) {
            if (channelVersion != UserSharedPreferences.getInstance().getInt(Constants.CHANNEL_VERSION)) {
                UserSharedPreferences.getInstance().putInt(Constants.CHANNEL_VERSION, channelVersion);
                final int versionCode = AppUtils.getVersionCode(MainActivity.this);
                new GetInformationChannelTabProtocol(MainActivity.this, Constants.TYPE_NEW, versionCode, new BaseModel.OnResultListener<ArrayList<ChannelBean>>() {
                    @Override
                    public void onResultListener(ArrayList<ChannelBean> response) {
                        if (null != response) {
                            DBTools.changeChannel(response);
                            new GetInformationChannelTabProtocol(MainActivity.this, Constants.TYPE_VIDEO, versionCode,
                                    new BaseModel.OnResultListener<ArrayList<ChannelBean>>() {
                                        @Override
                                        public void onResultListener(ArrayList<ChannelBean> response) {
                                            if (null != response) {
                                                DBTools.changeChannel(response);
                                            }
                                        }

                                        @Override
                                        public void onFailureListener(int code, String error) {
                                        }

                                    }).postRequest();
                        }

                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                    }

                }).postRequest();
            }
        } else {
            UserSharedPreferences.getInstance().putInt(Constants.CHANNEL_VERSION, channelVersion);
        }
    }

    private void updateProtocol(final int mGrayscaleNum) {
        new UpdateProtocol(MainActivity.this, new BaseModel.OnResultListener<ArrayList<UpdateBean>>() {
            @Override
            public void onResultListener(ArrayList<UpdateBean> response) {
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
                            if (UserManager.getInst().getUserBeam().getId() - 10000 <= mGrayscaleNum) {
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
                        if (maxVersionCode > appReleaseVersion) {
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
        DialogUpdate dialogUpdate = new DialogUpdate(MainActivity.this, new DialogUpdate.OnClickFinishListener() {
            @Override
            public void onClickFinishListener() {
                finish();
            }
        });
        dialogUpdate.show();
        dialogUpdate.setContent(updateBean.getTitle(), updateBean.getDescription(), updateBean.getUpdateUrl(), updateBean.getBuildAndroidNo());
        dialogUpdate.setUpdate(level, updateBean.isIsAndroidForce());
    }

    private void iniFragment(int fragmentIndex, View touchView) {
        if (mLastTouchView != null) mLastTouchView.setSelected(false);
        touchView.setSelected(true);
        mLastTouchView = touchView;

        transaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == fragmentIndex) {
                transaction.show(fragmentList.get(i));
                fragmentList.get(i).setUserVisibleHint(true);
            } else {
                transaction.hide(fragmentList.get(i));
                fragmentList.get(i).setUserVisibleHint(false);
            }
        }
        transaction.commit();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main3;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.mBottomHomeView:
                if (v.isSelected()) {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBottomHomeIV, "rotation", 0f, 360f);
                    objectAnimator.setDuration(700);
                    objectAnimator.setRepeatCount(1);
                    objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
                    objectAnimator.start();
                    mInformationFragment.refresh();
                } else {
                    iniFragment(0, v);
                }
                break;
            case R.id.mBottomVideoView:
                if (v.isSelected()) {
                    mVideoFragment.refresh();
                } else {
                    iniFragment(1, v);
                }
                break;
            case R.id.mBottomActIV:
                MobclickAgent.onEvent(this, "3783626018_clickActivityButton");
                if (UserManager.getInst().hadLogin()) {
                    String urls = UserManager.getInst().getmRuleBean().getBottomIconConfig().getGotoLink();
                    CommonHelper.openUrl(urls, this);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(this);
                }
                break;
            case R.id.mBottomTaskView:
                if (UserManager.getInst().hadLogin()) {
                    if (v.isSelected()) {
                        mVideoFragment.refresh();
                    } else {
                        iniFragment(2, v);
                        //隐藏小红点
                        if (mBottomTaskSpotView.getVisibility() == View.VISIBLE) {
                            int taskFragSpotEverydayCount = AdsRecordUtils.getInstance().getTaskFragSpotEverydayCount();
                            AdsRecordUtils.getInstance().saveTaskFragSpotEverydayCount(taskFragSpotEverydayCount + 1);
                            UserSharedPreferences.getInstance().saveTaskFragSpotEverydayCount();
                            mBottomTaskSpotView.setVisibility(View.GONE);
                        }
                    }
                } else {
                    ActivityUtils.startToBeforeLogingActivity(this);
                }
                break;
            case R.id.mBottomMeView:
                if (!v.isSelected()) {
                    iniFragment(3, v);
                    //隐藏小红点
                    if (mBottomMeSpotView.getVisibility() == View.VISIBLE) {
                        int count = AdsRecordUtils.getInstance().getMeFragSpotEverydayCount();
                        AdsRecordUtils.getInstance().saveMeFragSpotEverydayCount(count + 1);
                        UserSharedPreferences.getInstance().saveMeFragSpotDayCount();
                        mBottomMeSpotView.setVisibility(View.GONE);
                    }


                }
                break;
        }
    }


    /**
     * 新手福利弹框
     */
    public void showDialog(boolean isLogin) {
        mSmartView.setGone();
        if (isLogin) {
            if (!UserBean.getGrayJurisdiction(AnnoCon.UserPower.REGISTER_COIN)) {//无新收红包权限
                //展示活动弹框
                showActDialog();
            } else if (UserManager.getInst().getUserBeam().getMission().getFinished().contains(Constants.REGISTER_COIN)) {//有权限，且领过新收红包
                //判断用户有没有已经完成所有新手任务
                showNewPeopleTaskDialog(true);//新手福利弹框
//                showNewPeopleTaskDialog(false);//新手福利弹框  改版：弹新页面  默认不弹出
            } else {//有权限，没领过新手红包
                //没领取红包弹红包弹框
                showNewPeopleRedPag();
            }
        } else {//无登陆流程
            if (UserManager.getInst().getmRuleBean().getNewbieConfig().getStatusForUnlogin() == 1) {
                showNewPeopleRedPag();
            } else {
                showActDialog();
            }
        }
    }

    /**
     * 新手福利弹框
     *
     * @param isShowDialog 是否展示新手福利弹框 true xian展示新手福利弹框 false 展示小icon（右下角）
     */
    private void showNewPeopleTaskDialog(final boolean isShowDialog) {
        if (mGetNewTaskForNewPeoplePro == null) {
            mGetNewTaskForNewPeoplePro = new GetNewTaskForNewPeoplePro(this, new BaseModel.OnResultListener<List<NewPeopleTaskBean>>() {
                @Override
                public void onResultListener(final List<NewPeopleTaskBean> response) {
                    boolean isDone = true;
                    for (TaskBean bean : response) {
                        if (bean.isIsFinished()) {
                            bean.setBindingButton("已领取");
                        } else {
                            isDone = false;
                        }
                    }
                    if (response.size() == 0) {
                        showActDialog();
//                    } else if (!isDone || !UserManager.getInst().getUserBeam().isHeben()) {
                    } else if (!isDone) {
                        //展示新手福利弹框
                        //新手任务完成，展示运营活动
                        mDialogNewPeopleTask = new DialogNewPeopleTask(MainActivity.this, response, new DialogNewPeopleTask.DismissListener() {
                            @Override
                            public void dismissListener(final DialogNewPeopleTask dialog, boolean doneTask) {
                                if (!doneTask) {
                                    mSmartView.setData(response);
                                    mSmartView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            MobclickAgent.onEvent(MainActivity.this, "294-renwufuchuang");
//                                            mSmartView.setGone();
//                                            dialog.show();
                                            ActivityUtils.startToNewPeopleTaskActivity(MainActivity.this);
                                        }
                                    });
                                } else {//新手任务完成，展示运营活动
                                    mSmartView.setGone();
                                    showActDialog();
                                }
                            }
                        });
//                        if (isShowDialog) {
//                            mSmartView.setGone();
//                            mDialogNewPeopleTask.show();
//                        } else {//展示小icon
                        showSmartView(response);
//                        }
                    } else {
                        //展示活动弹框
                        showActDialog();
                    }
                    mGetNewTaskForNewPeoplePro = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetNewTaskForNewPeoplePro = null;
                }
            });
            mGetNewTaskForNewPeoplePro.postRequest();
        }
    }

    //展示小icon
    private void showSmartView(final List<NewPeopleTaskBean> response) {
        int doneNum = 0;
        for (NewPeopleTaskBean bean : response) {
            if (bean.isIsFinished()) {
                doneNum++;
            }
        }
        //完成的数量判断，是否一元提现过的判断
//        boolean doneTask = doneNum == response.size() && UserManager.getInst().getUserBeam().isHeben();
        boolean doneTask = doneNum == response.size();
        if (!doneTask) {
            mSmartView.setData(response);
            mSmartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MobclickAgent.onEvent(MainActivity.this, "294-renwufuchuang");
//                                    mSmartView.setGone();
//                                    mDialogNewPeopleTask.show();
                    ActivityUtils.startToNewPeopleTaskActivity(MainActivity.this);

                }
            });
        }
    }

    //运营弹框
    private void showActDialog() {
        //活动弹框
        boolean operateShow = false;
        if (UserBean.getGrayJurisdiction(AnnoCon.UserPower.OPERATION_TOAST)) {
            //本地缓存弹的次数
            int countDay = UserSharedPreferences.getInstance().getInt(Constants.SHOW_OPERATION_DAY_COUT);//累计日数
            int count = AdsRecordUtils.getInstance().getInt(Constants.SHOW_OPERATION);//每天累计数
            //后台要求累计数
            int lastDay = UserManager.getInst().getmRuleBean().getOperationConfig().getLastDay();
            int lastTime = UserManager.getInst().getmRuleBean().getOperationConfig().getLastTime();
            if (countDay < lastDay && count < lastTime) {
                if (mDialogOperate == null) {
                    mDialogOperate = new DialogOperate(MainActivity.this, new DialogOperate.OnClickCloseListener() {
                        @Override
                        public void onClickCloseListener() {
                            if (UserManager.getInst().getmRuleBean().getOperationConfig().isShowThumbNail()) {
                                mSmartView.setData(UserManager.getInst().getmRuleBean().getOperationConfig());
                            }
                        }
                    });
                }
                mDialogOperate.show();
//                operateShow = true;
                operateShow = false;//运营活动与等级弹窗一起出现
            }
        }
        //运营无活动
        if (!operateShow) {
            // 每日任务弹框
            String today = CommonHelper.formatTimeYMD(System.currentTimeMillis(), true);
            if (!today.equals(UserSharedPreferences.getInstance().getString(Constants.SHOW_RANK, ""))) {
                if (mDialogRecommendedDaily == null) {
                    mDialogRecommendedDaily = new DialogRecommendedDaily(MainActivity.this);
                    mDialogRecommendedDaily.loadData();
                }
                mDialogRecommendedDaily.show();
            }
            //活动小图标//右下小icon
            boolean isShowIncudtIcon;
            //本地次数
            int oneDayCount = AdsRecordUtils.getInstance().getHomeSmartEverydayCount();
            int dayCount = UserSharedPreferences.getInstance().getHomeSmartDayCount();
            //后台要求次数
            RuleBean.InductIconConfigBean inductIconConfig = UserManager.getInst().getmRuleBean().getInductIconConfig();
            int lastDay = inductIconConfig.getLastDay();
            int lastOneDayCount = inductIconConfig.getLastTime();
            if (UserManager.getInst().hadLogin()) {
                isShowIncudtIcon = UserBean.getGrayJurisdiction(AnnoCon.UserPower.OPERETION_RIGHTICON);
            } else {
                isShowIncudtIcon = inductIconConfig.getStatusForUnlogin() == 1;
            }
            isShowIncudtIcon = isShowIncudtIcon && dayCount < lastDay && oneDayCount < lastOneDayCount;
            //展示小图标
            if (isShowIncudtIcon) {
                mSmartView.setData(inductIconConfig);
            } else {
                if (UserManager.getInst().hadLogin() && UserManager.getInst().getUserBeam().isLevelGrand()) {
                    mSmartView.showLevelView();
                }
            }
        }
    }

    //新手注册红包弹框
    private void showNewPeopleRedPag() {
        if (mDialogNewPeopleRedPacket == null) {
            mDialogNewPeopleRedPacket = new DialogNewPeopleRedPacket3(this, new DialogNewPeopleRedPacket3.OnListenaer() {
                @Override
                public void onSubmitListener(final DialogNewPeopleRedPacket3 dialog) {
                    HashMap<String, String> map = new HashMap();
                    if (UserManager.getInst().hadLogin()) {
                        //用户已经领取过
                        if (UserManager.getInst().getUserBeam().getMission().getFinished().contains(Constants.REGISTER_COIN)) {
                            ToastUtils.showShortToast(MainActivity.this, "您已领取过新手红包~");
                        } else {
                            mSmartView.setGone();
                            getNewPeopleRedGold();
                            map.put("type", "已登录");
                        }
                    } else {
                        map.put("type", "未登录");
                        // 显示小icon
                        mSmartView.setData(null);
                        mSmartView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.show();
                                mSmartView.setGone();
                            }
                        });
                        ActivityUtils.startToBeforeLogingActivity(MainActivity.this);
                    }
                    MobclickAgent.onEvent(MainActivity.this, "06_01_02clickredpage", map);
                }

                @Override
                public void onCancelListener(final DialogNewPeopleRedPacket3 dialog) {
                    // 显示小icon
                    mSmartView.setData(null);
                    mSmartView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dialog == null || dialog.isShowing()) {
                                return;
                            }
                            dialog.show();
                            mSmartView.setGone();
                        }
                    });
                    if (!UserSharedPreferences.getInstance().getBoolean(Constants.FIRST_GOTO_TASK, false)) {
                        new DialogNewPeopleRefuse(MainActivity.this).show();
                    }
                }
            });
        }
        mDialogNewPeopleRedPacket.show();
    }

    //新手红包领取
    private void getNewPeopleRedGold() {
        new PutNewTaskForNewRedProtocol(this, new BaseModel.OnResultListener<DoneTaskResBean>() {
            @Override
            public void onResultListener(DoneTaskResBean response) {
                //是否第一次打开任务中心，两个弹框不一样
                DialogNewPeopleRedGotGold mDialogNewShowGold = new DialogNewPeopleRedGotGold(MainActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 用户领取新手红包后，弹新手福利弹框
                        showNewPeopleTaskDialog(true);
//                        showNewPeopleTaskDialog(false);
                    }
                });
                mDialogNewShowGold.show();
                mDialogNewShowGold.setCoin("" + response.getGoldAmount());
            }

            @Override
            public void onFailureListener(int code, String error) {
            }
        }).postRequest();
    }

    @Override
    public void onLoginStatusListener(boolean state) {
        if (mSmartView != null) mSmartView.setGone();
        if (mDialogOperate != null) mDialogOperate.dismiss();
        if (mDialogRecommendedDaily != null) mDialogRecommendedDaily.dismiss();
        if (mDialogNewPeopleRedPacket != null) mDialogNewPeopleRedPacket.dismiss();
        showDialog(state);
        initActView();
        if (state) {
            MobclickAgent.onProfileSignIn(UserManager.getInst().getUserBeam().getId() + "");
        }
    }

    private void initActView() {
        RuleBean.BottomIconConfigBean bottomIconBean = UserManager.getInst().getmRuleBean().getBottomIconConfig();
        if (UserManager.getInst().hadLogin()) {
            //底部中间展示
            if (UserBean.getGrayJurisdiction(AnnoCon.UserPower.BOTTOM_MISSION)
                    && !TextUtils.isEmpty(bottomIconBean.getIcon())
            ) {
                mBottomActIV.setVisibility(View.VISIBLE);
                GlideUtils.displayImage(this, bottomIconBean.getIcon(), mBottomActIV);
                MobclickAgent.onEvent(this, "6338555605_showActivityButton");
            } else {
                mBottomActIV.setVisibility(View.GONE);
            }
        } else {
            if (bottomIconBean.getStatusForUnlogin() != 1) {
                mBottomActIV.setVisibility(View.GONE);
            } else {
                mBottomActIV.setVisibility(View.VISIBLE);
                GlideUtils.displayImage(this, bottomIconBean.getIcon(), mBottomActIV);
                MobclickAgent.onEvent(this, "6338555605_showActivityButton");
            }
        }

        //红点展示
        List<RuleBean.RedIconConfigsBean> redIconConfigs = UserManager.getInst().getmRuleBean().getRedIconConfigs();
        if (redIconConfigs.size() >= 3 && redIconConfigs.get(2).getType() == 2) {
            int lastCountOneDay = AdsRecordUtils.getInstance().getTaskFragSpotEverydayCount();
            int lastDay = UserSharedPreferences.getInstance().getTaskFragSpotDayCount();

            if (redIconConfigs.get(2).isIsShow()
                    && lastDay <= redIconConfigs.get(2).getLastDay()
                    && lastCountOneDay < redIconConfigs.get(2).getLastTime()) {
                mBottomTaskSpotView.setVisibility(View.VISIBLE);
            } else {
                mBottomTaskSpotView.setVisibility(View.GONE);
            }
        }

        if (redIconConfigs.size() >= 2 && redIconConfigs.get(1).getType() == 1) {
            int lastCountOneDay = AdsRecordUtils.getInstance().getMeFragSpotEverydayCount();
            int lastDay = UserSharedPreferences.getInstance().getMeFragSpotDayCount();
            if (redIconConfigs.get(1).isIsShow()
                    && lastDay <= redIconConfigs.get(1).getLastDay()
                    && lastCountOneDay < redIconConfigs.get(1).getLastTime()) {
                mBottomMeSpotView.setVisibility(View.VISIBLE);
            } else {
                mBottomMeSpotView.setVisibility(View.GONE);
            }
        }
    }

    private void pushAbout() {
        if (UserManager.getInst().mPushPostId >= 0) {
            switch (UserManager.getInst().mPushType) {
                case MyReceiver.TYPE_ARTICL:
                    ActivityUtils.startToInformationDetailsActivity(this, UserManager.getInst().mPushPostId, AnnoCon.ART_DETAIL_FROM_PUSH);
                    break;
                case MyReceiver.TYPE_VIDEO:
                    ActivityUtils.startToVideoDetailsActivity(this, UserManager.getInst().mPushPostId, 0);
                    break;
                case MyReceiver.TYPE_PLATFORM_MESSAGE:
                    if (UserManager.getInst().hadLogin()) {
                        ActivityUtils.startToMessageConterActivity(this, 0);
                    }
                    break;
                case MyReceiver.TYPE_USER_MESSAGE:
                    if (UserManager.getInst().hadLogin()) {
                        ActivityUtils.startToMessageConterActivity(this, 0);
                    }
                    break;
                case MyReceiver.TYPE_WITHDRAWALS:
                    if (UserManager.getInst().hadLogin()) {
                        ActivityUtils.startToWithdrawalsRecordActivity(this);
                    }
                    break;
                case MyReceiver.TYPE_RATE:
                case MyReceiver.TYPE_TRIBUTE_FIRST:
                    if (UserManager.getInst().hadLogin()) {
                        ActivityUtils.startToIncomeDetailsActivity(this);
                    }
                    break;
                case MyReceiver.TYPE_NEW_TUDI_ACTIVITY:
                    ActivityUtils.startToMainActivity(this, Constants.TAB_ME, 0);
                    break;
                case MyReceiver.TYPE_NEW_ACTIVITY:
                    if (UserManager.getInst().hadLogin()) {
                        if (!TextUtils.isEmpty(UserManager.mActivityPath)) {
                            ActivityUtils.startToAssignActivity(this, UserManager.mActivityPath, UserManager.mTab);
                        }
                    } else {
                        ActivityUtils.startToBeforeLogingActivity(this);
                    }
                    break;
            }
            UserManager.getInst().mPushPostId = -1;
        }
    }

    @Override
    public void onPushOperateListener(QkdPushBean qkdPushBean) {
        mSmartView.setData(qkdPushBean);
    }

    private class GetAppInfoTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<String> packnames = CommonHelper.getAllApp(MainActivity.this);
            if (!TextUtils.isEmpty(UserManager.isGetInstallPacs)
                    && !UserSharedPreferences.getInstance().getBoolean(Constants.UP_PACEAGE_OK, false)
                    && UserManager.isGetInstallPacs.equals("1")) {
                int randNumber = new Random().nextInt(10 - 1 + 1) + 1;
                if (randNumber == 1) {
                    CommonHelper.uploadNiceApp(MainActivity.this, packnames);
                }
            }
            return null;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //这段代码从finish()之后移除，结果程序生命周期，执行正常。
        Process.killProcess(Process.myPid());
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReflashNewPeopleTask(NewPeopleTaskBean msg) {
        if (mGetNewTaskForNewPeoplePro == null) {
            mGetNewTaskForNewPeoplePro = new GetNewTaskForNewPeoplePro(this, new BaseModel.OnResultListener<List<NewPeopleTaskBean>>() {
                @Override
                public void onResultListener(final List<NewPeopleTaskBean> response) {
                    boolean isDone = true;
                    for (TaskBean bean : response) {
                        if (bean.isIsFinished()) {
                        } else {
                            isDone = false;
                        }
                    }
//                    if (!isDone || !UserManager.getInst().getUserBeam().isHeben()) {
                    if (!isDone) {
                        mSmartView.setData(response);
                        mGetNewTaskForNewPeoplePro = null;
                    } else {
                        if (mDialogNewPeopleTask != null) mDialogNewPeopleTask.dismiss();
                        mSmartView.setGone();
                    }
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetNewTaskForNewPeoplePro = null;
                }
            });
            mGetNewTaskForNewPeoplePro.postRequest();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showNewPeopleTaskDialogBus(ShowNewPeopleTaskDialogBean msg) {
        if (mSmartView.getVisibility() != View.GONE) {
            mSmartView.performClick();
        }
    }

    //任务列表-新手福利做完-要刷新主页的新手福利标识
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showNewPeopleTaskDialogBus2(TaskNewPeopleEvent msg) {
        if (msg.getRefresh()) {
            showDialog(UserManager.getInst().hadLogin());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDialog(UserManager.getInst().hadLogin());
        if (mSmartView.getVisibility() != View.GONE) {
            mSmartView.startAnim();
        }
    }

    //广告日志上传
    private void adsLogUp() {
        if (UserManager.adsLog != 1) {//后台控制广告日志上传
            return;
        }
        List<LogUpBean> logUpBeans = CacheManage.get(Constants.ADSLOG_UP, LogUpBean.class);
        if (logUpBeans == null || logUpBeans.size() == 0) {
            return;
        }

        new AdsLogUpProtocol(this, new BaseModel.OnResultListener<Integer>() {
            @Override
            public void onResultListener(Integer response) {
                CacheManage.remove(Constants.ADSLOG_UP);
            }

            @Override
            public void onFailureListener(int code, String error) {
                CacheManage.remove(Constants.ADSLOG_UP);
            }
        }).postRequest();
    }
}
