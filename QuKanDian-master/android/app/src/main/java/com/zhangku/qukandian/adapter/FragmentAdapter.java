package com.zhangku.qukandian.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
	
	private List<Fragment> mFragments;
	private List<String>   mTitleList;

	public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titleList) {
		super(fm);
		mFragments = fragments;
		mTitleList  = titleList;
	}

	@Override
	public Fragment getItem(int index) {
		return mFragments.get(index);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if(null != mTitleList && mTitleList.size() > position)
		return mTitleList.get(position);
		else return super.getPageTitle(position);
	}


}