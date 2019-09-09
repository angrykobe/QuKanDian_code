package com.zhangku.qukandian.base;

import android.support.design.widget.TabLayout;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.TabLayoutFragmentAdapter;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.widght.CustomViewPager;
import com.zhangku.qukandian.widght.GrayBgActionBar;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/4/11.
 */
@Deprecated
public abstract class BaseTabActivity extends BaseTitleActivity {
    private GrayBgActionBar mGrayBgActionBar;
    protected TabLayout mTabLayout;
    protected CustomViewPager mCustomViewPager;
    protected TabLayoutFragmentAdapter mAdapter;
    protected ArrayList<BaseFragment> mFragments = new ArrayList<>();
    protected ArrayList<String> mTabNames = new ArrayList<>();
    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        addFragments();
        addTabList();
        mGrayBgActionBar = (GrayBgActionBar) findViewById(R.id.gray_actionbar_layout);
        mGrayBgActionBar.setTvTitle(title());
        mTabLayout = (TabLayout) findViewById(R.id.activity_shoutu_layout_tablayout);
        mCustomViewPager = (CustomViewPager) findViewById(R.id.fragment_viewpager);
        mAdapter = new TabLayoutFragmentAdapter(getSupportFragmentManager(),mFragments,mTabNames);
        mCustomViewPager.setAdapter(mAdapter);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setupWithViewPager(mCustomViewPager);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                CommonHelper.setIndicator(mTabLayout, 20, 20);
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_shoutu_layout;
    }

    protected void setCurrentTab(int tab){
        mCustomViewPager.setCurrentItem(tab);
    }

    @Override
    public String setPagerName() {
        return null;
    }

    protected abstract String title();
    protected abstract void addFragments();
    protected abstract void addTabList();
}
