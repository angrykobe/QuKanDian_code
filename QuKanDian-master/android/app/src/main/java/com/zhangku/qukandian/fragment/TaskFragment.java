package com.zhangku.qukandian.fragment;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sdk.searchsdk.DKSearch;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.TaskAdatper;
import com.zhangku.qukandian.adapter.TaskMenuAdapter;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseFragment;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.AdMissionBean;
import com.zhangku.qukandian.bean.AdMissionOtherBean;
import com.zhangku.qukandian.bean.LaoBean;
import com.zhangku.qukandian.bean.SubmitTaskBean;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.bean.TaskChestBean;
import com.zhangku.qukandian.bean.UserMenuConfig;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.DynmicmissionProtocol;
import com.zhangku.qukandian.protocol.GetNewHttmissionRuleProtocol;
import com.zhangku.qukandian.protocol.GetNewTaskChestProtocol;
import com.zhangku.qukandian.protocol.GetNewTaskListInfoProtocol;
import com.zhangku.qukandian.protocol.GetTaskMenuListProtocol;
import com.zhangku.qukandian.protocol.PutAdMissionProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.MachineInfoUtil;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.widght.TaskChestView;
import com.zhangku.qukandian.widght.TextSwitcherView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Created by yuzuoning on 2018/3/28.
 * 任务中心
 */

public class TaskFragment extends BaseFragment implements TaskAdatper.OnClickItemListener, View.OnClickListener, UserManager.IOnGoldChangeListener {
    private RecyclerView mRecyclerView;
    private View errorView;
    private TaskAdatper mAdapter;
    private long mLaoHuaFeiTime = 0;
    private long mLuckBagTime = 0;
    private long mOtherTime = 0;
    private int mPosition = -1;
    private int mDuration = 0;
    private int mTypeId;
    private String mMissionName;
    private TextView mGotoWithTV;
    private ArrayList<TaskBean> mDailyDatas = new ArrayList<>();
    private GetNewTaskListInfoProtocol mGetTaskListInfoProtocol;
    private GetNewHttmissionRuleProtocol mGetHttmissionRuleProtocol;
    private PutAdMissionProtocol mPutAdMissionProtocol;
    private DynmicmissionProtocol mDynmicmissionProtocol;
    private boolean isLoaded = false;
    private TaskChestView mTaskChestView;//任务宝箱
    private GetNewTaskChestProtocol mGetNewTaskChestProtocol;
    private LinearLayoutManager layoutManager;

    private GetTaskMenuListProtocol mGetTaskMenuListProtocol;
    private RecyclerView headRecyView;//幸运转盘、任务宝箱、搜索赚-动态列表

    @Override
    protected void noNetword() {
    }

    @Override
    public void loadData(Context context) {

    }

    public void loadData2(Context context) {
        if (errorView == null) return;
        if (!CommonHelper.isNetworkAvalible(context)) {
            errorView.setVisibility(View.VISIBLE);
        } else {
            errorView.setVisibility(View.GONE);
        }
        initListView();
        if (mAdapter != null && null == mGetTaskListInfoProtocol && UserManager.getInst().hadLogin()) {
            mGetTaskListInfoProtocol = new GetNewTaskListInfoProtocol(getActivity(), new BaseModel.OnResultListener<List<TaskBean>>() {
                @Override
                public void onResultListener(List<TaskBean> response) {
                    errorView.setVisibility(View.GONE);
                    if (null == mDailyDatas) {
                        return;
                    }
                    mDailyDatas.clear();
                    int newPeopleTaskNum = 0;//新手任务完成的数量
                    //添加新手任务
                    ArrayList<TaskBean> newPeopleTaskList = new ArrayList<>();//1新手
                    ArrayList<TaskBean> luckTaskList = new ArrayList<>();//2幸运
                    ArrayList<TaskBean> shoutuTaskList = new ArrayList<>();//3收徒
                    ArrayList<TaskBean> readList = new ArrayList<>();//4阅读
                    for (TaskBean bean : response) {
                        if (bean.isIsVisible()) {
                            if ("game_xianwan".equals(bean.getName()) && "vivo".equals(QuKanDianApplication.mUmen)) {
                                continue;
                            }
                            switch (bean.getClassifyId()) {
                                case 1://1新手
                                    if (bean.isIsFinished()) {
                                        bean.setBindingButton("已领取");
                                        newPeopleTaskNum++;
                                    }
                                    //多任务的时候外面的任务总数要自己算
                                    if (bean.getKindType() == 4) {
                                        int allAwards = 0;
                                        for (TaskBean.MoreMissionBean moreMissionBean : bean.getMoreMission()) {
                                            allAwards += moreMissionBean.getAwardsTime();
                                        }
                                        bean.setAwardsTime(allAwards);
                                    }
                                    newPeopleTaskList.add(bean);
                                    break;
                                case 2://2幸运
                                    if (bean.isIsFinished()) {
                                        bean.setBindingButton("已领取");
                                    }
                                    //多任务的时候外面的任务总数要自己算
                                    if (bean.getKindType() == 4) {
                                        int allAwards = 0;
                                        for (TaskBean.MoreMissionBean moreMissionBean : bean.getMoreMission()) {
                                            allAwards += moreMissionBean.getAwardsTime();
                                        }
                                        bean.setAwardsTime(allAwards);
                                    }

                                    luckTaskList.add(bean);
                                    break;
                                case 3://3收徒
                                    if (bean.isIsFinished()) {
                                        bean.setBindingButton("已领取");
                                    }
                                    shoutuTaskList.add(bean);
                                    break;
                                case 4://4阅读
                                    if (bean.isIsFinished()) {
                                        bean.setBindingButton("已领取");
                                    }
                                    readList.add(bean);
                                    break;
                            }
                        }


                    }
                    //新手已完成任务 != 新人任务 显示新手任务
                    if (newPeopleTaskNum != newPeopleTaskList.size()) {
                        mDailyDatas.addAll(newPeopleTaskList);
                    }
                    mDailyDatas.addAll(luckTaskList);
                    mDailyDatas.addAll(shoutuTaskList);
                    mDailyDatas.addAll(readList);

                    mAdapter.notifyDataSetChanged();
//                    mRecyclerView.scrollBy(0, 0);
                    hideLoadingLayout();
                    isLoaded = true;
                    mGetTaskListInfoProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    errorView.setVisibility(View.VISIBLE);
                    mGetTaskListInfoProtocol = null;
                }
            });
            mGetTaskListInfoProtocol.postRequest();
        }
        hideLoadingLayout();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_new_taks_layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除注册
        UserManager.getInst().removGoldListener(this);
        DKSearch.onDestory();
    }

    @Override
    protected void initViews(View convertView) {
        EventBus.getDefault().register(this);//注册
        UserManager.getInst().addGoldListener(this);
        try {
            //云告-搜索赚
            DKSearch.init(getActivity(), AdConfig.yungao_key);//
        } catch (Exception e) {
        }
        mRecyclerView = convertView.findViewById(R.id.activity_new_task_layout_recyclerview);
        errorView = convertView.findViewById(R.id.errorView);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new TaskAdatper(getActivity(), mDailyDatas);
        //头部
        View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_task_fragment, mRecyclerView, false);
        //设置头部滚动提现
        TextSwitcherView mTextSwitcherView = headView.findViewById(R.id.task_activity_marquee);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int number = new Random().nextInt(10000);
            int money = new Random().nextInt(Config.Moneys.length);
            int munit = new Random().nextInt(31);
            String temp = "趣友 **** " + (number >= 1000 ? number : (number + 1000))
                    + "，" + (munit > 0 ? munit : 1)
                    + "分钟前提现了" + CommonHelper.form2(Config.Moneys[money]) + "元到微信";
            arrayList.add(temp);
        }
        mTextSwitcherView.getResource(arrayList);

        mTaskChestView = headView.findViewById(R.id.TaskChestView);
        headRecyView = headView.findViewById(R.id.headRecyclerView);

        mAdapter.setHeaderView(headView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickItemListener(this);
        mGotoWithTV = convertView.findViewById(R.id.right);
//        headView.findViewById(R.id.new_task_shoutu).setOnClickListener(this);
//        headView.findViewById(R.id.newPeopleGoldBtn).setOnClickListener(this);
        convertView.findViewById(R.id.left).setOnClickListener(this);
        convertView.findViewById(R.id.right).setOnClickListener(this);

        mGotoWithTV.setText("" + UserManager.getInst().getUserBeam().getGoldAccount().getAmount());
    }

    private void initListView() {
        if (mGetTaskMenuListProtocol == null) {
            mGetTaskMenuListProtocol = new GetTaskMenuListProtocol(getContext(), GetTaskMenuListProtocol.PAGETYPE_TASK, new BaseModel.OnResultListener<List<UserMenuConfig>>() {
                @Override
                public void onResultListener(List<UserMenuConfig> response) {
                    List<UserMenuConfig> headList = new ArrayList<>();
                    //分类菜单
                    for (UserMenuConfig userMenuConfig : response) {
                        if (userMenuConfig.getMenuType() != -1) {
                        } else {
                            headList.add(userMenuConfig);
                        }
                    }
                    //初始化头部列表
                    if (headList.size() > 0) {
                        GridLayoutManager layout = new GridLayoutManager(getContext(), headList.size());
                        headRecyView.setLayoutManager(layout);
                        final TaskMenuAdapter headAdapter = new TaskMenuAdapter(getContext(), headList);
                        headRecyView.setAdapter(headAdapter);
                        headAdapter.setOnItemClick(new BaseRecyclerViewAdapter.OnItemClick() {
                            @Override
                            public void onItemClick(View view, int position) {
                                if (headAdapter.getList().get(position).getName().contains(AdConfig.yungao_string)) {//云告聚合搜索
                                    MobclickAgent.onEvent(getContext(), "296-sousuozhuan1");
                                    MobclickAgent.onEvent(getContext(), "296-sousuozhuan2");
                                    return;
                                }else if(headAdapter.getList().get(position).getName().contains("阅读打卡")){
                                    MobclickAgent.onEvent(getContext(), "296-yuedudaka1");
                                    MobclickAgent.onEvent(getContext(), "296-yuedudaka2");
                                }else if(headAdapter.getList().get(position).getName().contains("任务宝箱")){
                                    MobclickAgent.onEvent(getContext(), "296-renwubaoxiang1");
                                    MobclickAgent.onEvent(getContext(), "296-renwubaoxiang2");
                                }
                                ActivityUtils.startToAnyWhereJsonActivity(getContext(), headAdapter.getList().get(position).getGotoLink());
                            }
                        });
                    } else {
                        headRecyView.setVisibility(View.GONE);
                    }
                    mGetTaskMenuListProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetTaskMenuListProtocol = null;
                }
            });
            mGetTaskMenuListProtocol.postRequest();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left://大神秘籍
                MobclickAgent.onEvent(getContext(), "293-ruhezhuanqian");
                String url = UserManager.getInst().getQukandianBean().getPubwebUrl() + "/Induction/Getmoney.html";
                ActivityUtils.startToWebviewAct(getActivity(), url, "大神秘籍");
                break;
            case R.id.right://
                MobclickAgent.onEvent(getContext(), "294-renwuyeyue");
                ActivityUtils.startToWithdrawalsActivity(getContext());
                break;
        }
    }

    @Override
    public String setPagerName() {
        return "任务中心";
    }

    //BINDING_WECHAT
    @Override
    public void onClickItemListener(String name, int position) {
        mPosition = position;
        if (Constants.LAO_HUA_FEI.equals(name)) {
            mLaoHuaFeiTime = System.currentTimeMillis();
            if (mGetHttmissionRuleProtocol == null) {
                mGetHttmissionRuleProtocol = new GetNewHttmissionRuleProtocol(getActivity(), Constants.LAO_HUA_FEI.equals(name) ? 1 : 2, new BaseModel.OnResultListener<LaoBean>() {
                    @Override
                    public void onResultListener(LaoBean response) {
                        if (null != response) {
                            mDuration = response.getDuration();
                        }
                        mGetHttmissionRuleProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        mGetHttmissionRuleProtocol = null;
                    }
                });
                mGetHttmissionRuleProtocol.postRequest();
            }
        } else if (Constants.LUCK_BAG.equals(name)) {
            mLuckBagTime = System.currentTimeMillis();
            if (mGetHttmissionRuleProtocol == null) {
                mGetHttmissionRuleProtocol = new GetNewHttmissionRuleProtocol(getActivity(), Constants.LAO_HUA_FEI.equals(name) ? 1 : 2, new BaseModel.OnResultListener<LaoBean>() {
                    @Override
                    public void onResultListener(LaoBean response) {
                        if (null != response) {
                            mDuration = response.getDuration();
                        }
                        mGetHttmissionRuleProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        mGetHttmissionRuleProtocol = null;
                    }
                });
                mGetHttmissionRuleProtocol.postRequest();
            }
        }
    }

    //获取幸运任务阅读时长
    @Override
    public void onClickGetGoldTime(int typeId, String name, int position) {
//        if (mGetHttmissionRuleProtocol == null) {
//            mGetHttmissionRuleProtocol = new GetNewHttmissionRuleProtocol(getActivity(), Constants.LAO_HUA_FEI.equals(name) ? 1 : 2, new BaseModel.OnResultListener<LaoBean>() {
//                @Override
//                public void onResultListener(LaoBean response) {
//                    if (null != response) {
//                        mDuration = response.getDuration();
//                    }
//                    mGetHttmissionRuleProtocol = null;
//                }
//
//                @Override
//                public void onFailureListener(int code, String error) {
//                    mGetHttmissionRuleProtocol = null;
//                }
//            });
//            mGetHttmissionRuleProtocol.postRequest();
//        }
        mPosition = position;
        mOtherTime = System.currentTimeMillis();
        mMissionName = name;
        mTypeId = typeId;
    }

    @Override
    public void onClickGetGoldNow(int typeId, String name, final int position) {
        mPosition = position;
        mOtherTime = 1;
        mMissionName = name;
        mTypeId = typeId;
        mDuration = 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        Map<String, String> map = new ArrayMap<>();
        map.put("to", "任务系统");
        MobclickAgent.onEvent(getActivity(), "AllPv", map);
        if (mLaoHuaFeiTime != 0) {
            if ((System.currentTimeMillis() - mLaoHuaFeiTime) / 1000 > mDuration) {
                mLaoHuaFeiTime = 0;
                mDailyDatas.get(mPosition).setBindingButton("领取奖励");
                mAdapter.notifyDataSetChanged();
            } else {
                mLaoHuaFeiTime = 0;
            }
        }

        if (mOtherTime != 0) {
            int time = mDuration == 0 ? mDailyDatas.get(mPosition).getDuration() : mDuration;
            if ((System.currentTimeMillis() - mOtherTime) / 1000 > time) {
                mOtherTime = 0;
                if (null == mDynmicmissionProtocol && !mDailyDatas.get(mPosition).isIsFinished()) {
                    //动态任务完成接口
                    mDynmicmissionProtocol = new DynmicmissionProtocol(getActivity(),
                            new AdMissionOtherBean(mMissionName, mTypeId, MachineInfoUtil.getInstance().getIMEI(getActivity())),
                            new BaseModel.OnResultListener<SubmitTaskBean>() {
                                @Override
                                public void onResultListener(SubmitTaskBean response) {
                                    CustomToast.showToast(getContext(), response.getGoldAmount() + "", response.getDescription());
                                    if (mDailyDatas.get(mPosition).isRepeat()) {

                                    } else {
                                        mDailyDatas.get(mPosition).setButtonEnable(false);
                                        mDailyDatas.get(mPosition).setBindingButton("已领取");
                                        mDailyDatas.get(mPosition).setIsFinished(true);
                                    }

                                    refresh(true);
                                    mAdapter.notifyDataSetChanged();
                                    mDynmicmissionProtocol = null;
                                }

                                @Override
                                public void onFailureListener(int code, String error) {
                                    mDynmicmissionProtocol = null;
                                }
                            });
                    mDynmicmissionProtocol.postRequest();
                }
            } else {
                mOtherTime = 0;
            }
        }

        if (mLuckBagTime != 0) {
            if ((System.currentTimeMillis() - mLuckBagTime) / 1000 > mDuration) {
                mLuckBagTime = 0;
                if (null == mPutAdMissionProtocol) {
                    mPutAdMissionProtocol = new PutAdMissionProtocol(getActivity(),
                            new AdMissionBean(Constants.HUI_TOU_TIAO_LUCK_BAG, getContext()),
                            new BaseModel.OnResultListener<Boolean>() {
                                @Override
                                public void onResultListener(Boolean response) {
                                    if (response) {
                                        mDailyDatas.get(mPosition).setBindingButton("已领取");
                                        mDailyDatas.get(mPosition).setIsFinished(true);
                                        refresh(true);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                    mPutAdMissionProtocol = null;
                                }

                                @Override
                                public void onFailureListener(int code, String error) {
                                    mPutAdMissionProtocol = null;
                                }
                            });
                    mPutAdMissionProtocol.postRequest();
                }
            } else {
                mLuckBagTime = 0;
            }
        }
        if (UserManager.ANSWERED && isLoaded && mPosition >= 0 && mDailyDatas.size() > 0) {
            UserManager.ANSWERED = false;
            mDailyDatas.get(mPosition).setBindingButton("已领取");
            mDailyDatas.get(mPosition).setIsFinished(true);
            mAdapter.notifyDataSetChanged();
        } else {
            UserManager.ANSWERED = false;
        }
        refresh(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        refresh(true);
    }

    private void refresh(boolean refreshBox) {
        if (isHidden()) return;
        LogUtils.LogW("refresh:data");
        if (refreshBox) {
            // LogUtils.LogW("refresh:box");
//            reflashTaskChestBox();
        }
        loadData2(getContext());
        //新手首次打开
        if (UserManager.getInst().getUserBeam().getUserAdsType() == 1
                && UserSharedPreferences.getInstance().getBoolean(Constants.FIRST_GOTO_TASK, true)) {
            UserSharedPreferences.getInstance().putBoolean(Constants.FIRST_GOTO_TASK, false);
        }
        if (mGotoWithTV != null)
            mGotoWithTV.setText("" + UserManager.getInst().getUserBeam().getGoldAccount().getAmount());
    }

    //刷新item
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReflashItem(TaskBean msg) {
        loadData2(getContext());
    }

    //刷新宝箱数据  宝箱数据要更新频繁些，防止任务完成进度没刷新(幸运任务完成 ，热搜获取奖励，文章阅读奖励，广告红包奖励时刷新)
    private void reflashTaskChestBox() {
        //宝箱进度刷新
        if (!UserManager.getInst().hadLogin()) return;
        if (UserManager.getInst().getQukandianBean().getIsShowTaskChestBox() == 1
                && mGetNewTaskChestProtocol == null
                && mTaskChestView != null
                && !mTaskChestView.isAllOpen()) {
            mGetNewTaskChestProtocol = new GetNewTaskChestProtocol(getContext(), new BaseModel.OnResultListener<List<TaskChestBean>>() {
                @Override
                public void onResultListener(List<TaskChestBean> response) {
                    mTaskChestView.setVisibility(View.VISIBLE);
                    mTaskChestView.setData(response);
                    mGetNewTaskChestProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetNewTaskChestProtocol = null;
                }
            });
            mGetNewTaskChestProtocol.postRequest();
        }
    }

    @Override
    public void onGoldChangeListener(int addMoney) {
        if (addMoney != 0 && mGotoWithTV != null) {//某些接口无金币数量，直接刷新
            int i = Integer.valueOf(mGotoWithTV.getText().toString()) + addMoney;
            mGotoWithTV.setText("" + i);
        }
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

}
