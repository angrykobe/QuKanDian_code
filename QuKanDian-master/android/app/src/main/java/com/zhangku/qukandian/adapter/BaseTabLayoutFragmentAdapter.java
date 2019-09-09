package com.zhangku.qukandian.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhangku.qukandian.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/3/30.
 */

public class BaseTabLayoutFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTabName = new ArrayList<>();

    public BaseTabLayoutFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabName) {
        super(fm);
        mFragments = fragments;
        mTabName = tabName;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabName.get(position);
    }
}
