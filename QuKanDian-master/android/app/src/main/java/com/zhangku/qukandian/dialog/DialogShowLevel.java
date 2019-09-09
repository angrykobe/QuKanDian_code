package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.DialoglevelListAdapter;
import com.zhangku.qukandian.adapter.TaskMenuAdapter;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.bean.TaskChestBean;
import com.zhangku.qukandian.bean.UserLevelBean;
import com.zhangku.qukandian.bean.UserLevelInforBean;
import com.zhangku.qukandian.bean.UserMenuConfig;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewPowerInforPro;
import com.zhangku.qukandian.protocol.GetNewTaskChestProtocol;
import com.zhangku.qukandian.protocol.GetTaskMenuListProtocol;
import com.zhangku.qukandian.protocol.PutNewTaskchestBoxProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.UserLevelView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2019/4/15
 * 你不注释一下？
 */
public class DialogShowLevel extends BaseDialog implements View.OnClickListener {

    private ImageView levelIconIV;
    private TextView levelNameTV;
    private TextView goldNumTV;
    private TextView everydayTaskTitleTV;
    private TextView levelTitleTV;
    private View everydayTaskView;
    private TextView mOneTaskDesTV;
    private ImageView mOneTaskImg;
    private TextView mTwoTaskDesTV;
    private ImageView mTwoTaskImg;
    private TextView mThreeTaskDesTV;
    private ImageView mThreeTaskImg;
    private View levelDesView;
    private TextView levelDesTV;
    private TextView nextLevelDesTV;
    private List<TaskChestBean> mList;
    private TextView mLevelProcessTV;
    private UserLevelBean bean;
    private int oldGold = 0;

    private GetTaskMenuListProtocol mGetTaskMenuListProtocol;
    private RecyclerView headRecyView;//2.9.6.3改版：每日任务修改

    public DialogShowLevel(Context context, TextView mLevelProcessTV) {
        super(context);
        this.mLevelProcessTV = mLevelProcessTV;
        setData();
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_show_level;
    }

    @Override
    protected void initView() {
        setTranBg();
        everydayTaskTitleTV = findViewById(R.id.everydayTaskTitleTV);
        levelTitleTV = findViewById(R.id.levelTitleTV);
        everydayTaskView = findViewById(R.id.everydayTaskView);
        levelDesView = findViewById(R.id.levelDesView);

        headRecyView = findViewById(R.id.mRecyclerView);

        everydayTaskTitleTV.setSelected(true);
        levelTitleTV.setSelected(false);
//        everydayTaskView.setVisibility(View.VISIBLE);
        headRecyView.setVisibility(View.VISIBLE);
        levelDesView.setVisibility(View.GONE);



        levelIconIV = findViewById(R.id.levelIconIV);
        levelNameTV = findViewById(R.id.levelNameTV);
        goldNumTV = findViewById(R.id.goldNumTV);
        mOneTaskDesTV = findViewById(R.id.mOneTaskDesTV);
        mOneTaskImg = findViewById(R.id.mOneTaskImg);
        mTwoTaskDesTV = findViewById(R.id.mTwoTaskDesTV);
        mTwoTaskImg = findViewById(R.id.mTwoTaskImg);
        mThreeTaskDesTV = findViewById(R.id.mThreeTaskDesTV);
        mThreeTaskImg = findViewById(R.id.mThreeTaskImg);
        levelDesTV = findViewById(R.id.levelDesTV);
        nextLevelDesTV = findViewById(R.id.nextLevelDesTV);

        findViewById(R.id.submitTV).setOnClickListener(this);
        findViewById(R.id.closeTV).setOnClickListener(this);
        findViewById(R.id.moreLevelDesTV).setOnClickListener(this);
        mOneTaskImg.setOnClickListener(this);
        mTwoTaskImg.setOnClickListener(this);
        mThreeTaskImg.setOnClickListener(this);
        everydayTaskTitleTV.setOnClickListener(this);
        levelTitleTV.setOnClickListener(this);

        initListView();
    }

    public void initUI() {
        if (goldNumTV != null) {
            int nowGoldNum = UserManager.getInst().getUserBeam().getGoldAccount().getAmount();
            int disNum = nowGoldNum - oldGold;
            startAnimForGold(disNum);
//            oldGold = UserManager.getInst().getUserBeam().getGoldAccount().getAmount();
//            goldNumTV.setText(Html.fromHtml(mContext.getString(R.string.level_gold, "" + oldGold)));
        }
        if (levelIconIV != null)
            UserLevelView.setLevelBigImg(levelIconIV);
        if (levelNameTV != null)
            levelNameTV.setText("" + UserManager.getInst().getUserBeam().getLevelDisplayName());
    }

    @Override
    protected void release() {

    }

    private void initListView() {
        if (mGetTaskMenuListProtocol == null) {
            mGetTaskMenuListProtocol = new GetTaskMenuListProtocol(getContext(),GetTaskMenuListProtocol.PAGETYPE_LV, new BaseModel.OnResultListener<List<UserMenuConfig>>() {
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
                        LinearLayoutManager layout = new LinearLayoutManager(getContext());
                        headRecyView.setLayoutManager(layout);
                        final DialoglevelListAdapter headAdapter = new DialoglevelListAdapter(getContext(), headList);
                        headRecyView.setAdapter(headAdapter);
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
            case R.id.submitTV:
                ActivityUtils.startToMainActivity(mContext, 2, 0);
                dismiss();
                MobclickAgent.onEvent(getContext(), "295-gengduorenwu");
                break;
            case R.id.closeTV:
                dismiss();
                break;
            case R.id.mOneTaskImg:
                if (mList != null && mList.size() > 0) {
                    click(mList.get(0), mOneTaskImg);
                }
                break;
            case R.id.mTwoTaskImg:
                if (mList != null && mList.size() > 1) {
                    click(mList.get(1), mTwoTaskImg);
                }
                break;
            case R.id.mThreeTaskImg:
                if (mList != null && mList.size() > 2) {
                    click(mList.get(2), mThreeTaskImg);
                }
                break;
            case R.id.moreLevelDesTV:
                if (bean != null) {
                    String replace = bean.getLevelDescUrl().replace("http://pubweb", UserManager.getInst().getQukandianBean().getPubwebUrl());
                    ActivityUtils.startToWebviewAct(mContext, replace, "规则");
                }
                MobclickAgent.onEvent(getContext(), "295-gengduotequan");
                break;
            case R.id.everydayTaskTitleTV:
                everydayTaskTitleTV.setSelected(true);
                levelTitleTV.setSelected(false);
//                everydayTaskView.setVisibility(View.VISIBLE);
                headRecyView.setVisibility(View.VISIBLE);
                levelDesView.setVisibility(View.GONE);
                MobclickAgent.onEvent(getContext(), "295-meirirenwu");
                break;
            case R.id.levelTitleTV:
                everydayTaskTitleTV.setSelected(false);
                levelTitleTV.setSelected(true);
//                everydayTaskView.setVisibility(View.GONE);
                headRecyView.setVisibility(View.GONE);
                levelDesView.setVisibility(View.VISIBLE);
                MobclickAgent.onEvent(getContext(), "295-dengjitequan");
                break;
        }
    }

    @Override
    protected void setPosition() {

    }

    @Override
    public void show() {
        super.show();
        setData();
        oldGold = UserManager.getInst().getUserBeam().getGoldAccount().getAmount();
        goldNumTV.setText(Html.fromHtml(mContext.getString(R.string.level_gold, "" + oldGold)));
        UserLevelView.setLevelBigImg(levelIconIV);
        levelNameTV.setText("" + UserManager.getInst().getUserBeam().getLevelDisplayName());
    }

    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().unregister(this);
    }

    public void setData() {
        GetNewTaskChestProtocol mGetNewTaskChestProtocol = new GetNewTaskChestProtocol(getContext(), new BaseModel.OnResultListener<List<TaskChestBean>>() {

            @Override
            public void onResultListener(List<TaskChestBean> response) {
                mList = response;
                int pro = 0;
                if (response.get(0).getChestBoxType() == 3) {
                    if (mOneTaskImg!=null){
                        mOneTaskImg.setSelected(true);
                    }
                    pro += 1;
                }
                if (response.get(1).getChestBoxType() == 3) {
                    if (mTwoTaskImg!=null){
                        mTwoTaskImg.setSelected(true);
                    }
                    pro += 1;
                }
                if (response.get(2).getChestBoxType() == 3) {
                    if (mThreeTaskImg!=null){
                        mThreeTaskImg.setSelected(true);
                    }
                    pro += 1;
                }

                if (mLevelProcessTV != null)
                    mLevelProcessTV.setText("进度：" + pro + "/3");
                if (mOneTaskDesTV != null)
                    mOneTaskDesTV.setText(Html.fromHtml(getString(response.get(0))));
                if (mTwoTaskDesTV != null)
                    mTwoTaskDesTV.setText(Html.fromHtml(getString(response.get(1))));
                if (mThreeTaskDesTV != null)
                    mThreeTaskDesTV.setText(Html.fromHtml(getString(response.get(2))));
                initUI();
            }

            @Override
            public void onFailureListener(int code, String error) {
//                mGetNewTaskChestProtocol = null;
            }
        });
        mGetNewTaskChestProtocol.postRequest();

        GetNewPowerInforPro pro = new GetNewPowerInforPro(mContext, new BaseModel.OnResultListener<UserLevelBean>() {
            @Override
            public void onResultListener(UserLevelBean response) {
                bean = response;
                UserLevelBean.LevelInfoBean levelInfo = response.getLevelInfo();//用户目前等级
                UserLevelBean.NextLevelInfoBean nextLevelInfo = response.getNextLevelInfo();//用户下一个等级

                List<String> list = new ArrayList<>();
                if (levelInfo.isHeadwearFlag()) {
                    list.add("专属头饰");
                }
                if (levelInfo.isGoldFlag()) {
                    list.add("每日金币");
                }
                if (levelInfo.isQuickFlag()) {
                    list.add("极速到账");
                }
                if (levelInfo.isYdFlag()) {
                    list.add("阅读加成");
                }
                if (levelInfo.isBetaFlag()) {
                    list.add("内测资格");
                }
                if (levelInfo.isHbFlag()) {
                    list.add("红包加成");
                }
                if (levelInfo.isXyFlag()) {
                    list.add("任务加成");
                }
                if (levelInfo.isQdFlag()) {
                    list.add("签到加成");
                }
                if (levelInfo.isBxFlag()) {
                    list.add("宝箱加成");
                }
                if (levelInfo.isRsFlag()) {
                    list.add("热搜加成");
                }
                if (levelInfo.isEwtxFlag()) {
                    list.add("额外提现");
                }
                String s = "";
                for (int i = 0; i < list.size(); i++) {
                    if (i % 2 == 0) {
                        s += list.get(i) + "　　　　";
                    } else {
                        s += list.get(i) + "\n";
                    }
                }

//                List<String> listNext = new ArrayList<>();
//                if(levelInfo.isHeadwearFlag() != nextLevelInfo.isHeadwearFlag() ){
//                    listNext.add("专属头饰");
//                }
//                if(levelInfo.isGoldFlag()  != nextLevelInfo.isGoldFlag() ){
//                    listNext.add("每日金币");
//                }
//                if(levelInfo.isQuickFlag() != nextLevelInfo.isQuickFlag() ){
//                    listNext.add("极速到账");
//                }
//                if(levelInfo.isYdFlag() != nextLevelInfo.isYdFlag() ){
//                    listNext.add("阅读加成");
//                }
//                if(levelInfo.isBetaFlag() != nextLevelInfo.isBetaFlag() ){
//                    listNext.add("内测资格");
//                }
//                if(levelInfo.isHbFlag() != nextLevelInfo.isHbFlag() ){
//                    listNext.add("红包加成");
//                }
//                if(levelInfo.isXyFlag() != nextLevelInfo.isXyFlag() ){
//                    listNext.add("任务加成");
//                }
//                if(levelInfo.isQdFlag() != nextLevelInfo.isQdFlag() ){
//                    listNext.add("签到加成");
//                }
//                if(levelInfo.isBxFlag() != nextLevelInfo.isBxFlag() ){
//                    listNext.add("宝箱加成");
//                }
//                if(levelInfo.isRsFlag() != nextLevelInfo.isRsFlag() ){
//                    listNext.add("热搜加成");
//                }
//                if(levelInfo.isEwtxFlag() != nextLevelInfo.isEwtxFlag() ){
//                    listNext.add("额外提现");
//                }
//
//                String sNext = "";
//                for (int i = 0; i < listNext.size(); i++) {
//                    if(i%2==0){
//                        sNext += listNext.get(i) + "　　　　";
//                    }else{
//                        sNext += listNext.get(i) + "\n";
//                    }
//                }
//
                String sNext = "";
                for (int i = 0; i < response.getNewPrivilege().size(); i++) {
                    if (i % 2 == 0) {
                        sNext += response.getNewPrivilege().get(i) + "　　　　";
                    } else {
                        sNext += response.getNewPrivilege().get(i) + "\n";
                    }
                }

                nextLevelDesTV.setText(sNext);
                levelDesTV.setText(s);
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        });
        pro.postRequest();
    }

    public String getString(TaskChestBean bean) {
        String str = "";
        if (bean.getReadNum() != 0) {
            String string = mContext.getString(R.string.dialog_level, "阅读文章　　", bean.getReadFinishedNum(), bean.getReadNum());
            str += string;
        }
        if (bean.getAdsNum() != 0) {
            if (!TextUtils.isEmpty(str)) {
                str += "<br/>";
            }
            String string = mContext.getString(R.string.dialog_level, "阅读红包　　", bean.getAdsFinishedNum(), bean.getAdsNum());
            str += string;
        }
        if (bean.getResouNum() != 0) {
            if (!TextUtils.isEmpty(str)) {
                str += "<br/>";
            }
            String string = mContext.getString(R.string.dialog_level, "热搜　　　　", bean.getResouFinishedNum(), bean.getResouNum());
            str += string;
        }
        if (bean.getLuckyNum() != 0) {
            if (!TextUtils.isEmpty(str)) {
                str += "<br/>";
            }
            String string = mContext.getString(R.string.dialog_level, "幸运任务　　", bean.getLuckyFinishedNum(), bean.getLuckyNum());
            str += string;
        }
        return str;
    }

    private void click(final TaskChestBean bean, final View imgView) {
        switch (bean.getChestBoxType()) {
            case 0://未开启
                ToastUtils.showLongToast(getContext(), "请先打开前面的宝箱~");
                break;
            case 1://查看进度
                break;
            case 2://已完成
                //开启宝箱
//                MobclickAgent.onEvent(getContext(), "293-renwubaoxiang_kai");
                new PutNewTaskchestBoxProtocol(getContext(), bean, new BaseModel.OnResultListener<DoneTaskResBean>() {
                    @Override
                    public void onResultListener(DoneTaskResBean response) {
                        CustomToast.showToast(getContext(), "" + response.getGoldAmount(), "");
                        bean.setChestBoxType(3);
                        EventBus.getDefault().post(new TaskBean());
                        imgView.setSelected(true);
                        UserManager.getInst().goldChangeNofity(response.getGoldAmount());
                        oldGold = UserManager.getInst().getUserBeam().getGoldAccount().getAmount();
                        goldNumTV.setText(Html.fromHtml(mContext.getString(R.string.level_gold, "" + oldGold)));
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                    }
                }).postRequest();
                break;
            case 3://已领取
                break;
        }
    }

    private Thread thread;

    public void startAnimForGold(final int disNum) {
        final int disCha = disNum / 50;

        if (thread != null) return;
        if (disNum > 50) {
            if (thread == null) {
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= disNum; i += disCha) {
                            final int j = i;
                            try {
                                Thread.sleep(60);
                                goldNumTV.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        goldNumTV.setText(Html.fromHtml(mContext.getString(R.string.level_gold, oldGold + j + "")));
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        goldNumTV.post(new Runnable() {
                            @Override
                            public void run() {
                                oldGold = UserManager.getInst().getUserBeam().getGoldAccount().getAmount();
                                goldNumTV.setText(Html.fromHtml(mContext.getString(R.string.level_gold, oldGold + "")));
                            }
                        });
                        thread = null;
                    }
                });
                thread.start();
            }
        } else {
            oldGold = UserManager.getInst().getUserBeam().getGoldAccount().getAmount();
            goldNumTV.setText(Html.fromHtml(mContext.getString(R.string.level_gold, oldGold + "")));
        }
    }
}
