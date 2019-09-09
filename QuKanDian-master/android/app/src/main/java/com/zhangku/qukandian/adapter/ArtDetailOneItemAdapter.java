package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.RecyclerViewDivider;
import java.util.ArrayList;

/**
 * 190214 详情页webview闪烁添加
 */
public class ArtDetailOneItemAdapter extends RecyclerView.Adapter<ArtDetailOneItemAdapter.OneItemHolder> {
    private DetailsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private Context context;
    private ArrayList<Object> mDatas;

    public ArtDetailOneItemAdapter(Context context,ArrayList<Object> mDatas){
        this.context = context;
        this.mDatas = mDatas;
        mAdapter = new DetailsAdapter(context, mDatas, Constants.TYPE_NEW);//下部列表（广告，推荐）
    }

    @Override
    public OneItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_only_recycleview,null,false));
    }

    @Override
    public void onBindViewHolder(OneItemHolder holder, int position) {
        mRecyclerView = holder.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(mAdapter);
        holder.recyclerView.addItemDecoration(new RecyclerViewDivider(context, 1, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(context, R.color.grey_f2)));
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class OneItemHolder extends RecyclerView.ViewHolder{

        RecyclerView recyclerView;

        public OneItemHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.RecyclerView);
        }
    }

    public DetailsAdapter getmAdapter() {
        return mAdapter;
    }

    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }

    public ArrayList<Object> getmDatas() {
        return mDatas;
    }

    //    public void setRefresh(){
//        if (mVideoAboutLayouts != null) mVideoAboutLayouts.adapterNotify();
//    }
}
