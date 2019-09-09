package com.zhangku.qukandian.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TabLayout adatper
 * Created by yuzuoning on 16/6/23.
 */

public class InformationTabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabTitle = new ArrayList<>();
    public int mCurrentPosition = -1;
    int id = 1;
    Map<String, Integer> IdsMap = new HashMap<>();

    public InformationTabAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabTitle1) {
        super(fm);
        this.fragments = fragments;
        this.tabTitle = tabTitle1;
        for (String info : tabTitle) {
            if (!IdsMap.containsKey(info)) {
                IdsMap.put(info, id++);
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        mCurrentPosition = position;
        return tabTitle.get(position);
    }
}
