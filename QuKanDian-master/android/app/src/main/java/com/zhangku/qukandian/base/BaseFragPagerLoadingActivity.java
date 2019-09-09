

package com.zhangku.qukandian.base;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.FragmentAdapter;
import com.zhangku.qukandian.widght.CustomViewPager;

import java.util.ArrayList;
import java.util.List;
@Deprecated
public abstract class BaseFragPagerLoadingActivity extends BaseFragmentLoadingActivity {

	protected FragmentAdapter mPagerAdapter;
	protected List<Fragment>  mFragments = new ArrayList<Fragment>();
	protected List<String>    mTitles     = new ArrayList<String>();
	protected CustomViewPager mViewPager;
	protected int 		mIndex = -1;
	public void initViews() {
		super.initViews();
		mViewPager = (CustomViewPager) findViewById(R.id.fragment_viewpager);
		mPagerAdapter = new FragmentAdapter(mFragmentManager, mFragments,mTitles);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOffscreenPageLimit(mPagerAdapter.getCount());
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				selectTab(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	//选中某个tab
	protected void selectTab(int index){
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
	
	@SuppressWarnings("deprecation")
	@Override
	protected void releaseRes() {
		if(mViewPager != null){
			mViewPager.removeAllViews();
			mViewPager.setOnPageChangeListener(null);
			mViewPager = null;
		}
		mPagerAdapter  = null;
		if(mFragments != null){
			mFragments.clear();
			mFragments = null;
		}
		super.releaseRes();
	}

}
