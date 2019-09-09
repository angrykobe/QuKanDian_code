package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.widght.VideoAboutLayout;

/**
 * Created by yuzuoning on 2017/7/18.
 */

public class VideoOneItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int mPostId = 0;
    private Context mContext;
    private DetailsBean mDetailsBean;
    private VideoAboutLayout mVideoAboutLayouts;
    public VideoOneItemAdapter(Context context,int postId) {
        mContext = context;
        mPostId = postId;
    }

    public void setHeaderData(DetailsBean bean) {
        mDetailsBean = bean;
    }

    public DetailsBean getHeaderData(){
        return mDetailsBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneItemHolder(LayoutInflater.from(mContext).inflate(R.layout.video_about_layout,null,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OneItemHolder view = (OneItemHolder) holder;
        mVideoAboutLayouts = view.mVideoAboutLayout;
        view.mVideoAboutLayout.setDatas(mPostId,mDetailsBean);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class OneItemHolder extends RecyclerView.ViewHolder{
        VideoAboutLayout mVideoAboutLayout;
        public OneItemHolder(View itemView) {
            super(itemView);
            mVideoAboutLayout = (VideoAboutLayout) itemView;
        }
    }
    public void setRefresh(){
        if (mVideoAboutLayouts != null) mVideoAboutLayouts.adapterNotify();
    }
}
