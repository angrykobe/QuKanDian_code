package com.zhangku.qukandian.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.LayoutInflaterUtils;
import com.zhangku.qukandian.widght.LoadingLayout;

@Deprecated
public abstract class BaseLoadingActivity extends BaseTitleActivity implements LoadingLayout.OnLoadingAction {
	
	protected LoadingLayout mLoadingLayout;

	@Override
	protected void addOtherView(ViewGroup parentLayout) {
		super.addOtherView(parentLayout);
		mLoadingLayout = (LoadingLayout) LayoutInflaterUtils.inflateView(this, R.layout.layout_loading);
		ViewGroup loadingViewParentLayout = parentLayout.findViewById(getLoadingViewParentId());
		loadingViewParentLayout.addView(mLoadingLayout);
		mLoadingLayout.setOnLoadingAction(this);
	}

	public abstract int getLoadingViewParentId();


	protected void showLoading(){
		if(mLoadingLayout != null){
			mLoadingLayout.showLoading();
		}
	}
	
	protected void hideLoadingLayout(){
		if(mLoadingLayout != null){
			mLoadingLayout.hideLoadingLayout();
		}
	}
	
	protected void showNodata(String text, int drawTopId){
		if(mLoadingLayout != null){
			mLoadingLayout.showNodata(text, drawTopId);
		}
	}
	
	protected void showNodata(String text){
		showNodata(text, 0);
	}
	
	protected void showNodata(int marginTop, int bgRes, String text, int drawTopId) {
		if(mLoadingLayout != null){
			mLoadingLayout.showNodata(marginTop, bgRes, text, drawTopId);
		}
	}
	
	protected void hideNoData() {
		if(mLoadingLayout != null){
			mLoadingLayout.hideNoData();
		}
	}
	
	protected void showLoadFail(){
		if(mLoadingLayout != null){
			mLoadingLayout.showLoadFail();
		}
	}

	protected void showEmptyCollect(){
		if(mLoadingLayout != null){
			mLoadingLayout.findViewById(R.id.empty_collect_goto_home).setVisibility(View.VISIBLE);
			mLoadingLayout.findViewById(R.id.empty_collect_goto_home).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ActivityUtils.startToMainActivity(BaseLoadingActivity.this,0,0);
				}
			});
			mLoadingLayout.showEmptyCollect();
		}
	}
	protected void showEmptyCollect(String text){
		if(mLoadingLayout != null){
			mLoadingLayout.findViewById(R.id.empty_collect_goto_home).setVisibility(View.VISIBLE);
			mLoadingLayout.findViewById(R.id.empty_collect_goto_home).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ActivityUtils.startToMainActivity(BaseLoadingActivity.this,0,0);
				}
			});
			mLoadingLayout.showEmptyCollect(text);
		}
	}

	protected void showEmptySearch(String text){
		if(mLoadingLayout != null){
			mLoadingLayout.showEmptySearch(text);
		}
	}
	
//	protected void showNetworkError(){
//		if(mLoadingLayout != null){
//			mLoadingLayout.showNetworkError();
//		}
//	}
	
	/**
	 * 加载失败
	 */
	@Override
	public void onLoadingFail() {
		
	}

	@Override
	public void onNetworkFail() {
		showLoading();
	}

	protected void addMoreViewBelowNoData(View view){
		mLoadingLayout.addMoreViewBelowNoData(view);
	}

	@Override
	public void onNetChange(boolean isAvailable, boolean isWifi) {
		super.onNetChange(isAvailable, isWifi);
		if(!isAvailable && !isWifi){
			showEmptyNetword();
		}
	}

	private void showEmptyNetword() {
		if(null != mLoadingLayout){
			mLoadingLayout.showEmptyNetword();
		}
	}

	@Override
	protected void releaseRes() {
		if(mLoadingLayout != null){
			mLoadingLayout.removeAllViews();
			mLoadingLayout = null;
		}
		super.releaseRes();
	}
}
