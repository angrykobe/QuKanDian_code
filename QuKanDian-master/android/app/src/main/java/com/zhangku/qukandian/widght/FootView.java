package com.zhangku.qukandian.widght;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangku.qukandian.R;


/**
 * 用于listview加载更多,底部布局
 * @author yuzuoning
 */
public class FootView{

	private Context mContext;
	private View    mView;
	private View    mLoadFailView;
	
	public FootView(Context context, ViewGroup parent) {
		mContext = context;
		mView    = LayoutInflater.from(mContext).inflate(R.layout.layout_footerview,parent,false);
		mLoadFailView = mView.findViewById(R.id.layout_footerview_load_fail);
		hide();
	}

	public FootView(Context context) {
		mContext = context;
		mView    = LayoutInflater.from(mContext).inflate(R.layout.layout_footerview,null,false);
		mLoadFailView = mView.findViewById(R.id.layout_footerview_load_fail);
		hide();
	}

	public void hide(){
		if(mView != null){
			mView.findViewById(R.id.layout_footerview_content).setVisibility(View.GONE);
			mLoadFailView.setVisibility(View.GONE);
		}
	}

	public void onBottom(String str){
		if(mView != null){
			mView.findViewById(R.id.layout_footerview_content).setVisibility(View.VISIBLE);
			mView.findViewById(R.id.foot_img).setVisibility(View.GONE);
			((TextView)mView.findViewById(R.id.foot_text)).setText(str);
			mLoadFailView.setVisibility(View.GONE);
		}
	}

	public void invisible(){
		if(mView != null){
			mView.findViewById(R.id.layout_footerview_content).setVisibility(View.INVISIBLE);
			mLoadFailView.setVisibility(View.INVISIBLE);
		}
	}
	
	public void show(){
		if(mView != null){
			mLoadFailView.setVisibility(View.GONE);
			mView.findViewById(R.id.layout_footerview_content).setVisibility(View.VISIBLE);
		}
	}

	public void showLoadFail(View.OnClickListener onClickListener){
		if(mLoadFailView != null){
			mView.findViewById(R.id.layout_footerview_content).setVisibility(View.GONE);
			mLoadFailView.setVisibility(View.VISIBLE);
			mLoadFailView.setOnClickListener(onClickListener);
		}
	}
	
	public View getView(){
		return mView;
	}

}
