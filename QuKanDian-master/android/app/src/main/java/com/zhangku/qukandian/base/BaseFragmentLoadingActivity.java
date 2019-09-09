package com.zhangku.qukandian.base;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.utils.LayoutInflaterUtils;
import com.zhangku.qukandian.widght.LoadingLayout;


public abstract class BaseFragmentLoadingActivity extends BaseFragmentTitleActivity implements LoadingLayout.OnLoadingAction {

	protected LoadingLayout mLoadingLayout;

	@Override
	protected void addOtherView(ViewGroup parentLayout) {
		super.addOtherView(parentLayout);
		mLoadingLayout = (LoadingLayout) LayoutInflaterUtils.inflateView(this, R.layout.layout_loading);
		FrameLayout loadingViewParentLayout = (FrameLayout) parentLayout.findViewById(getLoadingViewParentId());
		loadingViewParentLayout.addView(mLoadingLayout);

		mLoadingLayout.setOnLoadingAction(this);
	}

	/**
	 * loadingLayout�?依附的视�?
	 * 
	 * @return
	 */
	public abstract int getLoadingViewParentId();

	protected void showLoading() {
		mLoadingLayout.showLoading();
	}

	protected void hideLoadingLayout() {
		mLoadingLayout.hideLoadingLayout();
	}

	protected void showNodata(String text, int drawTopId) {
		mLoadingLayout.showNodata(text, drawTopId);
	}

	protected void showLoadFail() {
		mLoadingLayout.showLoadFail();
	}

	protected void showNetwordFail(){
		mLoadingLayout.showEmptyNetword();
	}

	/**
	 * 加载失败
	 */
	@Override
	public void onLoadingFail() {

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
