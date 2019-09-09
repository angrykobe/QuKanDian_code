package com.zhangku.qukandian.activitys.me;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zhangku.qukandian.BaseNew.BaseTitleAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.BaseTabLayoutFragmentAdapter;
import com.zhangku.qukandian.fragment.me.GetGoldForDowningFragment;
import com.zhangku.qukandian.fragment.me.GetGoldForHadDoneFragment;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 赚赏金
 */
public class GetGoldForDownActivity extends BaseTitleAct {

    protected TabLayout mTabLayout;
    protected BaseTabLayoutFragmentAdapter mAdapter;
    protected ViewPager mViewPager;
    private List<Fragment> mFragments;
    private List<String> mTabNames;

    @Override
    protected void loadData() {
    }

    @Override
    protected void initViews() {
        if (!UserManager.getInst().hadLogin()) {
            ActivityUtils.startToBeforeLogingActivity(this);
            finish();
            return;
        }
        mTabLayout = findViewById(R.id.TabLayout);
        mViewPager = findViewById(R.id.ViewPager);
        mFragments = new ArrayList<>();
        mTabNames = new ArrayList<>();
        mTabNames.add("任务列表");
        mTabNames.add("我参与的");
        mFragments.add(new GetGoldForDowningFragment());
        mFragments.add(new GetGoldForHadDoneFragment());
        mAdapter = new BaseTabLayoutFragmentAdapter(getSupportFragmentManager(), mFragments, mTabNames);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.act_get_gold_for_down;
    }

    @Override
    protected String setTitle() {
        return "赚赏金";
    }

    @Override
    protected void myOnClick(View v) {

    }

    public void refresh(){
        GetGoldForHadDoneFragment fragment = (GetGoldForHadDoneFragment) mFragments.get(1);
        fragment.loadData(1);
    }
}
