package com.zhangku.qukandian.base;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.FragmentAdapter;
import com.zhangku.qukandian.widght.CustomViewPager;

import java.util.ArrayList;
import java.util.List;
@Deprecated
public abstract class BaseListFragment extends BaseFragment implements OnPageChangeListener {

	protected CustomViewPager mViewPager;
	protected List<Fragment>	mFragments;
	protected List<String>     mTitles;
	protected FragmentAdapter mAdapter;
	protected int				mIndex = -1;
	protected View              mCurrentView = null; 
	private   Intent            mIntent;

	@SuppressWarnings("deprecation")
	@Override
	protected void initViews(View convertView) {
		mViewPager = (CustomViewPager) convertView.findViewById(R.id.fragment_viewpager);
		mFragments = new ArrayList<Fragment>();
		mTitles    = new ArrayList<String>();
		addFragments();
		mAdapter = new FragmentAdapter(getManager(), mFragments,mTitles);
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
	}
	
	public abstract FragmentManager getManager();

	public void addFragments(){
		
	}
	
	@Override
	public final void onPageSelected(int position) {
		selectTab(position);
	}
	
	@Override
	public final void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}
	
	@Override
	public final void onPageScrollStateChanged(int arg0) {
		
	}
	
	//选中某个tab
	public void selectTab(int index){
		if(index == mIndex) return;
    	if(mIndex == -1){
    		setSelected(true, index);
    		mIndex = index;
    	}else{
    		setSelected(false, mIndex);
    		setSelected(true, index);
    		mIndex = index;
    	}
    }
    
	//设置为选中
    public abstract void setSelected(boolean selsted, int index);
    	
    public void setIntent(Intent intent){
		mIntent = intent;
	}
    
    @Override
    public void onResume() {
    	super.onResume();
    	if(null != mIntent){
			 int tab = getIntentTab(mIntent);
			 setSelected(true, tab);
			 mIntent = null;
		}
    }
    
    public abstract int getIntentTab(Intent intent);
	
	@SuppressWarnings("deprecation")
	@Override
	protected void releaseRes() {
		if(mViewPager != null){
			mViewPager.removeAllViews();
			mViewPager.setOnPageChangeListener(null);
			mViewPager = null;
		}
		mAdapter  = null;
		if(mFragments != null){
			mFragments.clear();
			mFragments = null;
		}
		super.releaseRes();
	}
}
