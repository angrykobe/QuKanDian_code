package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.DownAppBean;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 你不注释一下？
 */
public class DownAppDetailAdapter extends BaseRecyclerViewAdapter<DownAppBean.BountyTaskStepBean> {

    public DownAppDetailAdapter(Context context, List<DownAppBean.BountyTaskStepBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_down_app, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final DownAppBean.BountyTaskStepBean bean) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.stedNameTV.setText(""+bean.getStepExplain());
        LinearLayoutManager layout = new LinearLayoutManager(mContext);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        myViewHolder.RecyclerView.setLayoutManager(layout);
        DownAppDetailImageAdapter adapter = new DownAppDetailImageAdapter(mContext,bean.getBountyTaskStepImgs());
        myViewHolder.RecyclerView.setAdapter(adapter);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView stedNameTV;
        public RecyclerView RecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            stedNameTV = itemView.findViewById(R.id.stedNameTV);
            RecyclerView = itemView.findViewById(R.id.RecyclerView);
        }
    }
}
