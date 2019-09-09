package com.zhangku.qukandian.activitys.me;

import android.view.View;

import com.zhangku.qukandian.base.BaseTabActivity;
import com.zhangku.qukandian.fragment.me.AllRankFragment;
import com.zhangku.qukandian.fragment.me.WeekRankFragment;

/**
 * Created by yuzuoning on 2017/4/11.
 */

public class IncomeRankActivity extends BaseTabActivity {
    private WeekRankFragment mWeekRankFragment;
    private AllRankFragment mAllRankFragment;

    @Override
    public String setPagerName() {
        return "收入排行";
    }

    @Override
    protected String title() {
        return "收入排行";
    }

    @Override
    protected void addFragments() {
        mAllRankFragment =new AllRankFragment();
        mFragments.add(mAllRankFragment);
    }

    @Override
    protected void initViews() {
        super.initViews();
        mTabLayout.setVisibility(View.GONE);
    }

    @Override
    protected void addTabList() {
        mTabNames.add("总排行");
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mAllRankFragment = null;
    }
}
