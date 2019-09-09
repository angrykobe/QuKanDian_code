package com.zhangku.qukandian.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhangku.qukandian.fragment.Video.VideoInsideFragment;

import java.util.List;

/**
 * Created by yuzuoning on 2017/3/30.
 */

public class VideoTabAdapter extends FragmentPagerAdapter {

    private List<VideoInsideFragment> fragments;
    private List<String> tabTitle;
    public int mCurrentPosition = -1;


    public VideoTabAdapter(FragmentManager fm, List<VideoInsideFragment> fragments, List<String> tabTitle) {
        super(fm);
        this.fragments = fragments;
        this.tabTitle = tabTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    //设置tablayout标题

    @Override
    public CharSequence getPageTitle(int position) {
        mCurrentPosition = position;
        return tabTitle.get(position);

    }

}
