package com.zhangku.qukandian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangku.qukandian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/6/15
 */
public class ArticleShieldAdapter extends RecyclerView.Adapter<ArticleShieldAdapter.MyViewHolder> {

    private List<String> mList;
    private List<String> mChooseList;

    public ArticleShieldAdapter(List<String> list){
        this.mList = list;
        mChooseList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_shield,null));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tv.setText(mList.get(position));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.tv.isSelected()){
                    holder.tv.setSelected(false);
                    mChooseList.remove(mList.get(position));
                }else{
                    holder.tv.setSelected(true);
                    mChooseList.add(mList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<String> getmChooseList() {
        return mChooseList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.contentTV);
        }
    }

    public void setmList(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public List<String> getmList() {
        return mList;
    }

}
