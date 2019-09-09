package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.TabLayoutFragmentAdapter;
import com.zhangku.qukandian.base.BaseFragment;
import com.zhangku.qukandian.utils.CommonHelper;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/4/3.
 */

public class TabLayoutCommonLayout extends LinearLayout {
    private TabLayout mTabLayout;
    private CustomViewPager mCustomViewPager;
    private TabLayoutFragmentAdapter mAdapter;
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    private ArrayList<String> mTabNames = new ArrayList<>();

    public TabLayoutCommonLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        ExitActivityObserver.getInst().addExitActivityObserverAction(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTabLayout = (TabLayout) findViewById(R.id.layout_article_collect_common_tablayout);
        mCustomViewPager = (CustomViewPager) findViewById(R.id.layout_article_collect_common_viewpage);

        mAdapter = new TabLayoutFragmentAdapter(((AppCompatActivity) getContext()).getSupportFragmentManager()
                , mFragments, mTabNames);
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

    public void addFragment(BaseFragment fragment, String tabname) {
        mFragments.add(fragment);
        mTabNames.add(tabname);
        mAdapter.notifyDataSetChanged();
    }

    public void setCurrentTab(int tab) {
        mCustomViewPager.setCurrentItem(tab);
    }

    public void hideTabLayout() {
        mTabLayout.setVisibility(View.GONE);
    }

//    @Override
//    public void onActivityDestory() {
//        mTabNames.clear();
//        mTabNames = null;
//        mFragments.clear();
//        mFragments = null;
//        mTabLayout = null;
//        mAdapter = null;
//        mCustomViewPager = null;
//    }
}
