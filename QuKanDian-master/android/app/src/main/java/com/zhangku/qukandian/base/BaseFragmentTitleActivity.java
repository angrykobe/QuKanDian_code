package com.zhangku.qukandian.base;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.zhangku.qukandian.widght.ActionBarLayout;

import static com.zhangku.qukandian.R.id.layout_title_back;

@Deprecated
public abstract class BaseFragmentTitleActivity extends BaseFragmentActivity {

	@Override
	protected void attachView() {
		super.attachView();
		initActionBarView();
		initActionBarData();
		addOtherView(mFrameLayout);
	}

	/**
	 * 配置头部数据
	 */
	protected void initActionBarData(){}
	
	private void initActionBarView(){
		mActionBarLayout.setOnBackListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackAction();
			}
		});
	}
	
	public void hideActionBarView(){
		if(mActionBarLayout != null){
			mActionBarLayout.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void setTitle(int titleId) {
		mActionBarLayout.setTitle(titleId);
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mActionBarLayout.setTitle(title);
	}

	public void hideBackBtn(){
		if(mActionBarLayout != null){
			mActionBarLayout.findViewById(layout_title_back).setVisibility(View.GONE);
		}
	}
	public void setTitleColors(int color){
		mActionBarLayout.setTitleColor(color);
	}

	protected void addMenuItem(int... resId){
		mActionBarLayout.addMenu(resId);
		mActionBarLayout.setOnActionBarMenuAction(new ActionBarLayout.OnActionBarMenuAction() {
			
			@Override
			public void onMenuAction(int menuId) {
				actionMenuOnClick(menuId);
			}
		});
	}
	
	protected View addMenuItem(int resId){
		View view = mActionBarLayout.addMenu(resId);
		mActionBarLayout.setOnActionBarMenuAction(new ActionBarLayout.OnActionBarMenuAction() {
			
			@Override
			public void onMenuAction(int menuId) {
				actionMenuOnClick(menuId);
			}
		});
		return view;
	}
	
	protected void removeMenuItem(int resId){
		mActionBarLayout.removeMenuItem(resId);
	}

	protected void actionMenuOnClick(int menuId){
		
	}
	
	protected void actionSearchClick(String keyWord){
		
	}
	
	protected void addOtherView(ViewGroup parentLayout) {
		
	}

	@Override
	protected void releaseRes() {
		if(mActionBarLayout != null){
			mActionBarLayout.removeAllViews();
			mActionBarLayout = null;
		}
		super.releaseRes();
	}
}
