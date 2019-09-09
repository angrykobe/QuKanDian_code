package com.zhangku.qukandian.fragment.me;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.LauncherActivity;
import com.zhangku.qukandian.activitys.MainActivity;
import com.zhangku.qukandian.adapter.MeFragAdapter;
import com.zhangku.qukandian.adapter.NewMeHeadAdapter;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseFragment;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.EventBusBean.ShowNewPeopleTaskDialogBean;
import com.zhangku.qukandian.bean.MessageTipBean;
import com.zhangku.qukandian.bean.MyBannerBean;
import com.zhangku.qukandian.bean.RuleBean;
import com.zhangku.qukandian.bean.UpLevelResBean;
import com.zhangku.qukandian.bean.UserAddGoldBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.bean.UserMenuConfig;
import com.zhangku.qukandian.bean.WeChatBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogMeOperate;
import com.zhangku.qukandian.dialog.DialogUpLevel;
import com.zhangku.qukandian.dialog.DialogWechatRemind;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.ClickTipObserver;
import com.zhangku.qukandian.observer.MenuChangeOberver;
import com.zhangku.qukandian.protocol.GetMyMenuListProtocol;
import com.zhangku.qukandian.protocol.GetNewMessageTipsProtocol;
import com.zhangku.qukandian.protocol.GetNewMyBannerProtocol;
import com.zhangku.qukandian.protocol.GetNewUserInfoProtocol;
import com.zhangku.qukandian.protocol.PutUpLevelProtocol;
import com.zhangku.qukandian.protocol.PutUserInfoProtocol;
import com.zhangku.qukandian.protocol.UploadWeChatInfoProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.GPSUtils;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.MySensorManager;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.SpConstants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.SpUtils;
import com.zhangku.qukandian.utils.ShoutuShareUtil;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.widght.AdViewPager;
import com.zhangku.qukandian.widght.TextSwitcherView;
import com.zhangku.qukandian.widght.UserLevelView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * 创建者          xuzhida
 * 创建日期        2018/8/17
 */
public class MeFragment extends BaseFragment implements View.OnClickListener, UserManager.IOnLoginStatusLisnter, ClickTipObserver.OnClickTipListener, UserManager.IOnUserInfoChange, UserManager.IOnGoldChangeListener, MenuChangeOberver.OnMenuChangeListener {
    private GetNewMyBannerProtocol mMyPageBannerProtocol;
    private AdViewPager viewpager;
    private TextView copyTV;//
    private TextView codeTV;//邀请码
    private TextView nameTV;//
    private ImageView userPhotoIV;//
    private ImageView userHeadwearIV;//用户专属头饰
    private TextView todayIncomeTV;//今日收益
    private TextView balanceTV;//余额
    private TextView apprenticeTV;//徒弟
    private UserLevelView mUserLevelView;


    private MeFragAdapter mNewMeAdapter;
    private NewMeHeadAdapter headAdapter;//顶部
    private SmartRefreshLayout refreshLayout;
    private UserMenuConfig mMessageBeanTop;//顶部横屏菜单栏的
    private UserMenuConfig mMessageBean;//下面竖屏列表栏

    private GetNewMessageTipsProtocol mGetMessageTipsProtocol;
    private RecyclerView headRecyView;
    private GetMyMenuListProtocol mGetMyMenuListProtocol;
    private UploadWeChatInfoProtocol mUploadWeChatInfoProtocol;
    private DialogWechatRemind mDialogWechatRemind;
    private PutUpLevelProtocol putUpLevelProtocol;

    private MySensorManager mySensorManager;

    @Override
    protected void noNetword() {

    }

    @Override
    public void loadData(Context context) {
        getBannerData(context);
        getUserInfo();
        showMyDialog();
    }

    private void getBannerData(Context context) {
        if (null == mMyPageBannerProtocol) {
            mMyPageBannerProtocol = new GetNewMyBannerProtocol(context, new BaseModel.OnResultListener<MyBannerBean>() {
                @Override
                public void onResultListener(MyBannerBean response) {
                    boolean isHave = false;
                    if (response.getStatusForLogin() == 1) {
                        isHave = true;
                    } else if (response.getStatusForLogin() == 2) {
                        if (UserManager.getInst().getUserBeam().getMissionGrarntedUsers() != null) {
                            for (int i = 0; i < UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size(); i++) {
                                if (Constants.MYPAGE_BANNER.equals(UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).getMission().getName())) {
                                    isHave = UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(i).isIsActive();
                                }
                            }
                        }
                    }
                    if (isHave) {
                        viewpager.initData(response.getBannerConfigs(), AnnoCon.FROM_NewMeFragment3);
                    } else {
                        viewpager.setVisibility(View.GONE);
                    }
                    mMyPageBannerProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mMyPageBannerProtocol = null;
                }
            });
            mMyPageBannerProtocol.postRequest();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.view_recycleview;
    }

    @Override
    protected void initViews(View convertView) {
        if ("1".equals(UserManager.getInst().getQukandianBean().getGyro())) {
            mySensorManager = new MySensorManager(getActivity());
            mySensorManager.onResume();
        }
        mDialogWechatRemind = new DialogWechatRemind(mParent);

        refreshLayout = convertView.findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getUserInfo();
            }
        });
        RecyclerView recyclerView = convertView.findViewById(R.id.RecyclerView);
        mNewMeAdapter = new MeFragAdapter(getContext(), null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mNewMeAdapter);


        View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_new_me, recyclerView, false);
        viewpager = headView.findViewById(R.id.viewpager);
        copyTV = headView.findViewById(R.id.copyTV);
        codeTV = headView.findViewById(R.id.codeTV);
        nameTV = headView.findViewById(R.id.nameTV);
        todayIncomeTV = headView.findViewById(R.id.todayIncomeTV);
        balanceTV = headView.findViewById(R.id.balanceTV);
        apprenticeTV = headView.findViewById(R.id.ApprenticeTV);
        userPhotoIV = headView.findViewById(R.id.userPhotoIV);
        userHeadwearIV = headView.findViewById(R.id.userHeadwearIV);
        headRecyView = headView.findViewById(R.id.headRecyclerView);
        mUserLevelView = headView.findViewById(R.id.userLevelView);

        headView.findViewById(R.id.wechatShareTV).setOnClickListener(this);
        headView.findViewById(R.id.wechatCircleTV).setOnClickListener(this);
        headView.findViewById(R.id.qqShareTV).setOnClickListener(this);
        headView.findViewById(R.id.codeShareTV).setOnClickListener(this);

        mUserLevelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startToUserLevelAct(getContext());
            }
        });

        headView.findViewById(R.id.todayIncomeView).setOnClickListener(this);
        headView.findViewById(R.id.balanceView).setOnClickListener(this);
        headView.findViewById(R.id.apprenticeView).setOnClickListener(this);

        headView.findViewById(R.id.withdrawView).setOnClickListener(this);
        headView.findViewById(R.id.walletView).setOnClickListener(this);
        headView.findViewById(R.id.friendView).setOnClickListener(this);
        headView.findViewById(R.id.shoppingView).setOnClickListener(this);
        headView.findViewById(R.id.copyTV).setOnClickListener(this);
        headView.findViewById(R.id.nameTV).setOnClickListener(this);
        headView.findViewById(R.id.userPhotoIV).setOnClickListener(this);
        headView.findViewById(R.id.settingTV).setOnClickListener(this);

        //设置头部滚动提现
        TextSwitcherView mTextSwitcherView = headView.findViewById(R.id.task_activity_marquee);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int number1 = new Random().nextInt(9000) + 1000;
            int people = new Random().nextInt(9) + 2;
            String temp1 = "趣友 **** " + number1
                    + "成功邀请" + people + "名徒弟获得" + (2.5 * people) + "元";
            arrayList.add(temp1);

            int number2 = new Random().nextInt(9000) + 1000;
            int reward = new Random().nextInt(11) + 5;
            String temp2 = "趣友 **** " + number2
                    + "今日获得好友进贡" + reward + "元";
            arrayList.add(temp2);
        }
        mTextSwitcherView.getResource(arrayList);

        mNewMeAdapter.setHeaderView(headView);

        UserManager.getInst().addLoginListener(this);
        MenuChangeOberver.getInstance().addOnChangeStateListener(this);//手机绑定改变监听
        ClickTipObserver.getInstance().addListener(this);//消息改变
        UserManager.getInst().addUserInfoListener(this);
        UserManager.getInst().addGoldListener(this);
        initListView();

        mNewMeAdapter.setOnItemClick(new BaseRecyclerViewAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                if ("绑定微信".equals(mNewMeAdapter.getList().get(position).getName())) {
                    if (UserManager.getInst().hadLogin()) {
                        bindWeChat();
                    } else {
                        ActivityUtils.startToBeforeLogingActivity(getContext());
                    }
                } else {
                    if (mNewMeAdapter.getList().get(position).getName().contains("玩法攻略")) {
                        MobclickAgent.onEvent(getContext(), "293-gonglue");
                    } else if (mNewMeAdapter.getList().get(position).getGotoLink().contains("收入排行榜")) {
                        MobclickAgent.onEvent(getContext(), "293-paihang");

                    } else if (mNewMeAdapter.getList().get(position).getGotoLink().contains("淘宝特卖")) {
                        MobclickAgent.onEvent(getContext(), "293-taobao");

                    } else if (mNewMeAdapter.getList().get(position).getGotoLink().contains("今日趣看视界")) {
                        MobclickAgent.onEvent(getContext(), "293--jinrikandian");

                    } else if (mNewMeAdapter.getList().get(position).getGotoLink().contains("提现兑换")) {
                        MobclickAgent.onEvent(getContext(), "293-tixianduihuan");
                    }
                    ActivityUtils.startToAnyWhereJsonActivity(getContext(), mNewMeAdapter.getList().get(position).getGotoLink());
                }
            }
        });
        EventBus.getDefault().register(this);//注册
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UserManager.getInst().removeLoginListener(this);
        MenuChangeOberver.getInstance().removeOnChangeStateListener(this);//手机绑定改变监听
        ClickTipObserver.getInstance().removeListener(this);//消息改变
        EventBus.getDefault().unregister(this);//解除注册
    }

    private void initListView() {
        if (mGetMyMenuListProtocol == null) {
            mGetMyMenuListProtocol = new GetMyMenuListProtocol(getContext(), new BaseModel.OnResultListener<List<UserMenuConfig>>() {
                @Override
                public void onResultListener(List<UserMenuConfig> response) {
                    List<UserMenuConfig> list = new ArrayList<>();
                    List<UserMenuConfig> headList = new ArrayList<>();
                    //分类菜单
                    for (UserMenuConfig userMenuConfig : response) {
                        if (userMenuConfig.getMenuType() != -1) {
                            list.add(userMenuConfig);
                        } else {
                            headList.add(userMenuConfig);
                        }
                    }
                    //初始化内容列表
                    Iterator<UserMenuConfig> iter = list.iterator();
                    while (iter.hasNext()) {
                        UserMenuConfig bean = iter.next();
                        if ("分享记录".equals(bean.getName())) {
                            if (UserManager.getInst().hadLogin()
                                    && null != UserManager.getInst().getUserBeam().getMissionGrarntedUsers()
                                    && UserManager.getInst().getUserBeam().getMissionGrarntedUsers().size() > 0
                                    && UserManager.getInst().getUserBeam().getMissionGrarntedUsers().get(0).isIsActive()) {
                            } else {
                                iter.remove();
                            }
                        }
                        if ("兑换商城".equals(bean.getName()) && "huaweiMarket".equals(QuKanDianApplication.mUmen)) {
                            iter.remove();
                        }
                        if ("输入邀请码".equals(bean.getName())) {
                            if (UserManager.getInst().getUserBeam().getMentorUser().getMentorId() == 0
                                    && UserManager.getInst().getUserBeam().getMentorUser().getSumMentee() == 0) {
                            } else {
                                iter.remove();
                            }
                        }
                        if ("绑定微信".equals(bean.getName())) {
                            if (null != UserManager.getInst().getUserBeam().getWechatUser()) {
                                iter.remove();
                            }
                        }
                        if ("消息中心".equals(bean.getName())) {
                            mMessageBean = bean;
                        }
                    }
                    mNewMeAdapter.getList().clear();
                    mNewMeAdapter.addList(list);
                    //初始化头部列表
                    if (headList.size() > 0) {
                        GridLayoutManager layout = new GridLayoutManager(getContext(), headList.size());
                        headRecyView.setLayoutManager(layout);

                        headAdapter = new NewMeHeadAdapter(getContext(), headList);
                        headRecyView.setAdapter(headAdapter);
                        headAdapter.setOnItemClick(new BaseRecyclerViewAdapter.OnItemClick() {
                            @Override
                            public void onItemClick(View view, int position) {
                                if (headAdapter.getList().get(position).getGotoLink().contains("提现兑换")) {
                                    MobclickAgent.onEvent(getContext(), "294-tixianduihuan");
                                }
                                ActivityUtils.startToAnyWhereJsonActivity(getContext(), headAdapter.getList().get(position).getGotoLink());
                            }
                        });
                        for(UserMenuConfig bean :headList){
                            if ("我的消息".equals(bean.getName())) {
                                mMessageBeanTop = bean;
                                getMessageReadNum();
                            }
                        }
                    } else {
                        headRecyView.setVisibility(View.GONE);
                    }
                    mGetMyMenuListProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetMyMenuListProtocol = null;
                }
            });
            mGetMyMenuListProtocol.postRequest();
        }
    }

    @Override
    public String setPagerName() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wechatShareTV:
                MobclickAgent.onEvent(getContext(), "294-wodewechatfenxiang");
                if (UserManager.getInst().hadLogin()) {
                    new ShoutuShareUtil(getActivity()).sharedTransit(SHARE_MEDIA.WEIXIN);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
                break;
            case R.id.wechatCircleTV:
                MobclickAgent.onEvent(getContext(), "294-wodepengyouquanfenxiang");
                if (UserManager.getInst().hadLogin()) {
                    new ShoutuShareUtil(getActivity()).sharedTransit(SHARE_MEDIA.WEIXIN_CIRCLE);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
                break;
            case R.id.qqShareTV:
                MobclickAgent.onEvent(getContext(), "294-wodeqqfenxiang");
                if (UserManager.getInst().hadLogin()) {
                    new ShoutuShareUtil(getActivity()).sharedTransit(SHARE_MEDIA.QQ);
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
                break;
            case R.id.codeShareTV:
                MobclickAgent.onEvent(getContext(), "294-wodesaomafenxiang");
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToFace2FaceInviteActivity(getContext());
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
                break;
            case R.id.nameTV:
            case R.id.userPhotoIV:
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToPerfectActivity(getContext());
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
                break;
            case R.id.copyTV:
                CommonHelper.copy(getContext(), "" + UserManager.getInst().getUserBeam().getId(), "");

                ToastUtils.showLongToast(getContext(), "已复制：" + UserManager.getInst().getUserBeam().getId());
                break;
            case R.id.walletView://钱包
            case R.id.balanceView://我的余额
            case R.id.todayIncomeView://今日收益
                MobclickAgent.onEvent(getActivity(), "04_01_clicktodayincome");
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToIncomeDetailsActivity(getContext());
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
                break;
            case R.id.friendView://邀请好友
            case R.id.apprenticeView://我的徒弟
                MobclickAgent.onEvent(getContext(), "04_03_clickmytudi");
                if (UserManager.getInst().hadLogin()) {
                    // 收徒页改成H5
                    ActivityUtils.startToShoutuActivity(getContext());
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
                break;
            case R.id.withdrawView://提现
                MobclickAgent.onEvent(getActivity(), "04_07_cllicktixian");
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToWithdrawalsActivity(getContext());
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
                break;
            case R.id.shoppingView://兑换商城
                UserSharedPreferences.getInstance().putBoolean(Constants.DUI_BA, false);
                if (UserManager.getInst().hadLogin()) {
                    ActivityUtils.startToMallActivity(getContext(), "");
                } else {
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
                break;
            case R.id.settingTV:
                ActivityUtils.startToFeedbackActivity(getContext());
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getMessageReadNum();
            onLoginStatusListener(UserManager.getInst().hadLogin());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onLoginStatusListener(UserManager.getInst().hadLogin());
    }

    //登陆状态改变监听
    @Override
    public void onLoginStatusListener(boolean state) {
        if (state) {
            todayIncomeTV.setText("" + UserManager.getInst().getUserBeam().getGoldAccount().getTodayAmout());
            balanceTV.setText("" + UserManager.getInst().getUserBeam().getGoldAccount().getAmount());
            apprenticeTV.setText("" + UserManager.getInst().getUserBeam().getMentorUser().getSumMentee());

            copyTV.setVisibility(View.VISIBLE);
            codeTV.setText("我的邀请码：" + UserManager.getInst().getUserBeam().getId());
            nameTV.setText("" + UserManager.getInst().getUserBeam().getNickName());
            GlideUtils.displayCircleImage(getContext(), UserManager.getInst().getUserBeam().getAvatarUrl()
                    , userPhotoIV, 2, ContextCompat.getColor(mParent, R.color.white), GlideUtils.getUserNormalOptions(), true);
            userHeadwearIV.setVisibility(UserManager.getInst().getUserBeam().isHeadwearFlag() ? View.VISIBLE : View.GONE);//头饰隐藏显示
            getMessageReadNum();
            mUserLevelView.setLevel(UserManager.getInst().getUserBeam().getLevel(), "" + UserManager.getInst().getUserBeam().getLevelDisplayName());
            if (!UserManager.getInst().getUserBeam().isLevelGrand()) {//用户无权限，隐藏
                mUserLevelView.setVisibility(View.GONE);
                userHeadwearIV.setVisibility(View.GONE);
            } else {
                mUserLevelView.setVisibility(View.VISIBLE);
            }
            //登陆实时刷新banner页面（金猪活动）
            getBannerData(getContext());
            if (getContext() instanceof MainActivity) {
                MainActivity ac = (MainActivity) getContext();
                ac.mSmartView.changeLevelImg();
            }
        } else {
            todayIncomeTV.setText("0");
            balanceTV.setText("0");
            apprenticeTV.setText("0");

            codeTV.setText("");
            copyTV.setVisibility(View.GONE);
            nameTV.setText("立即登录");
            userPhotoIV.setImageResource(R.mipmap.user_photo);
            userHeadwearIV.setVisibility(View.GONE);
            mUserLevelView.setVisibility(View.GONE);
        }
        initListView();
    }

    private void getUserInfo() {
        if (UserManager.getInst().hadLogin()) {
            new GetNewUserInfoProtocol(getContext(), new BaseModel.OnResultListener<UserBean>() {
                @Override
                public void onResultListener(UserBean response) {
                    //等级提升
                    if (response.getLevel() < response.gettLevel() && UserManager.getInst().getUserBeam().isLevelGrand())
                        showUpLevelDialog();
                    refreshLayout.finishRefresh(true);
                    onLoginStatusListener(true);
                }

                @Override
                public void onFailureListener(int code, String error) {
                    ToastUtils.showLongToast(getContext(), "" + error);
                    refreshLayout.finishRefresh(true);
                }
            }).postRequest();
        } else {
            refreshLayout.finishRefresh(true);
            onLoginStatusListener(false);
        }
    }

    private void showUpLevelDialog() {
        if (putUpLevelProtocol == null) {
            putUpLevelProtocol = new PutUpLevelProtocol(getContext(), new BaseModel.OnResultListener<UpLevelResBean>() {
                @Override
                public void onResultListener(UpLevelResBean response) {
                    putUpLevelProtocol = null;
                    if (getContext() != null && getContext() instanceof Activity) {
                        Activity ac = (Activity) getContext();
                        if (Build.VERSION.SDK_INT >= 17) {
                            if (ac.isDestroyed()) {
                                return;
                            }
                        } else {
                            if (ac.isFinishing()) {
                                return;
                            }
                        }
                    }
                    DialogUpLevel dialogUpLevel = new DialogUpLevel(getContext(), response);
                    dialogUpLevel.show();
                }

                @Override
                public void onFailureListener(int code, String error) {
                    putUpLevelProtocol = null;
                }
            });
            putUpLevelProtocol.postRequest();
        }
    }
    /**
     * 我的弹窗
     */
    private void showMyDialog() {
        if (!UserManager.getInst().hadLogin()) {
            return;
        }
        if (!QuKanDianApplication.isFirst) {
            // return;
        }

        if (UserManager.getInst().getmRuleBean() == null ||
                UserManager.getInst().getmRuleBean().getMyPageToast() == null) {
            return;
        }

        QuKanDianApplication.isFirst = false;
        RuleBean.MyDialogBean myDialogBean = UserManager.getInst().getmRuleBean().getMyPageToast();

        // 连续天数控制
        int dayNum = UserSharedPreferences.getInstance().getInt(SpConstants.ME_DIALOG_CONTINUE_DAY, 1);
        if (myDialogBean.getLastDay() < dayNum) {
            return;
        }

        // 每天次数控制
        int everyDayNum = SpUtils.getInstance().getInt(SpConstants.ME_DIALOG_ONEDAY_COUNT, 1);
        if (myDialogBean.getLastTime() < everyDayNum) {
            return;
        }

        // 时间间隔控制
        long lastShowTime = AdsRecordUtils.getInstance().getLong(SpConstants.ME_DIALOG_LAST_TIME, 0);
        if (System.currentTimeMillis() - lastShowTime < myDialogBean.getMinute() * 60 * 1000) {
            return;
        }

        if (everyDayNum == 1) {
            // 每天第一次
            UserSharedPreferences.getInstance().putInt(SpConstants.ME_DIALOG_CONTINUE_DAY, dayNum + 1);
        }
        SpUtils.getInstance().putInt(SpConstants.ME_DIALOG_ONEDAY_COUNT, everyDayNum + 1);
        AdsRecordUtils.getInstance().putLong(SpConstants.ME_DIALOG_LAST_TIME, System.currentTimeMillis());

        new DialogMeOperate(getActivity()).show();
    }

    //消息未读
    private void getMessageReadNum() {
        if (UserManager.getInst().hadLogin() && null == mGetMessageTipsProtocol) {
            mGetMessageTipsProtocol = new GetNewMessageTipsProtocol(getContext(), new BaseModel.OnResultListener<List<MessageTipBean>>() {
                @Override
                public void onResultListener(List<MessageTipBean> response) {
                    //用户消息未读数 + 平台消息未读数
                    int count = response.get(0).getCount() + response.get(1).getCount();
                    if(mMessageBean!=null){
                        mMessageBean.setShow(count > 0);
                        mNewMeAdapter.notifyDataSetChanged();
                    }
                    if(mMessageBeanTop!=null){
                        mMessageBeanTop.setShow(count > 0);
                        headAdapter.notifyDataSetChanged();
                    }
                    mGetMessageTipsProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetMessageTipsProtocol = null;
                }
            });
            mGetMessageTipsProtocol.postRequest();
        }
    }

    //菜单刷新
    @Override
    public void onMenuChangeListener() {
        initListView();
    }

    private void bindWeChat() {
        UMShareAPI.get(QuKanDianApplication.getmContext()).deleteOauth(mParent, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                mDialogPrograss.show();
                UMShareAPI.get(QuKanDianApplication.getmContext()).getPlatformInfo(mParent,
                        SHARE_MEDIA.WEIXIN, new UMAuthListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                ToastUtils.showLongToast(mParent, "正在打开跳转页面");
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA share_media, int i, final Map<String, String> map) {
                                if (null == mUploadWeChatInfoProtocol) {
                                    mUploadWeChatInfoProtocol = new UploadWeChatInfoProtocol(mParent, new BaseModel.OnResultListener<Boolean>() {
                                        @Override
                                        public void onResultListener(Boolean response) {
                                            mDialogPrograss.dismiss();
                                            if (response) {
                                                UserBean user = UserManager.getInst().getUserBeam();
                                                String defaulName = "趣友" + user.getId();
                                                if (defaulName.equals(user.getNickName())) {//用户没修改默认昵称，用微信昵称
                                                    user.setNickName(map.get("name"));
                                                    new PutUserInfoProtocol(getContext(), new BaseModel.OnResultListener() {
                                                        @Override
                                                        public void onResultListener(Object response) {
                                                            getUserInfo();
                                                        }

                                                        @Override
                                                        public void onFailureListener(int code, String error) {
                                                        }
                                                    }).putUserInfo(user);
                                                } else {
                                                    getUserInfo();
                                                }
                                            } else {
                                                mDialogWechatRemind.show();
                                                mDialogWechatRemind.setTvTitle("绑定失败，该微信号已绑定过其他账号。");
                                                onMenuChangeListener();
                                            }
                                            mUploadWeChatInfoProtocol = null;
                                        }

                                        @Override
                                        public void onFailureListener(int code, String error) {
                                            mDialogPrograss.dismiss();
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
                                mDialogPrograss.dismiss();
                                if (throwable.toString().contains("没有安装应用")) {
                                    ToastUtils.showLongToast(mParent, "没有安装应用");
                                }
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media, int i) {
                                mDialogPrograss.dismiss();
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

    //消息改变
    @Override
    public void onClickListener(int type, int number) {
        getMessageReadNum();
    }

    @Override
    public void onIconChange(String userIcon) {
        GlideUtils.displayCircleImage(getContext(), userIcon, userPhotoIV, 2, ContextCompat.getColor(mParent, R.color.white), GlideUtils.getUserNormalOptions(), true);
    }

    @Override
    public void onNickNameChange(String nickName) {
        nameTV.setText("" + nickName);
    }

    //金币改变监听
    @Override
    public void onGoldChangeListener(int addMoney) {
        if (mySensorManager != null) {
            mySensorManager.oneTimesAction();
        }
        if (addMoney == 0) {//某些接口无金币数量，直接刷新
            getUserInfo();
        } else {
            UserBean userBean = UserManager.getInst().getUserBeam();
            if (addMoney > 0) {//预防提现addMoney为负数
                UserManager.getInst().getUserBeam().getGoldAccount().setTodayAmout(userBean.getGoldAccount().getTodayAmout() + addMoney);//今日收益
                UserManager.getInst().getUserBeam().getGoldAccount().setSum(userBean.getGoldAccount().getSum() + addMoney);//历史总收入
            }
            int amount = userBean.getGoldAccount().getAmount() + addMoney;
            UserManager.getInst().getUserBeam().getGoldAccount().setAmount(amount);//余额

            todayIncomeTV.setText("" + UserManager.getInst().getUserBeam().getGoldAccount().getTodayAmout());
            balanceTV.setText("" + UserManager.getInst().getUserBeam().getGoldAccount().getAmount());
            boolean isFirstShow = UserSharedPreferences.getInstance().getBoolean("isFirstShow", false);
            if (!isFirstShow && amount >= 10000 && getContext() instanceof MainActivity) {
                UserSharedPreferences.getInstance().putBoolean("isFirstShow", true);
                EventBus.getDefault().post(new ShowNewPeopleTaskDialogBean());
            }
        }
    }

    //刷新我的页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReflashUserBean(UserAddGoldBean msg) {
        UserManager.getInst().goldChangeNofity(msg.getAddGold());
    }

    /**
     * postUserBean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLevelChange(UserBean userBean) {
        if (userBean.isLevelGrand() && mUserLevelView != null)
            mUserLevelView.setLevel(userBean.getLevel(), userBean.getLevelDisplayName());
    }
}
