package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.HelpBean;

import java.util.List;


/**
 * 创建者          xuzhida
 * 创建日期        2018/8/21
 * 你不注释一下？
 */
public class QuestionHeadAdapter extends BaseRecyclerViewAdapter<HelpBean> {
    public ItemClick itemClick;
    public QuestionHeadAdapter(Context context, List<HelpBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new QuestionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_textview,parent,false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int viewPosition, int dataIndex, HelpBean bean) {
        QuestionHolder questionHolder = (QuestionHolder) holder;
        questionHolder.mContentTV.setText(""+bean.getDisplayName());
        questionHolder.mContentTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick!=null)
                    itemClick.onItemClick(v,viewPosition);
            }
        });
    }



    class QuestionHolder extends RecyclerView.ViewHolder{
        TextView mContentTV;
        public QuestionHolder(View itemView) {
            super(itemView);
            mContentTV = (TextView) itemView.findViewById(R.id.contentTV);
        }
    }


    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

    public interface ItemClick{
        void onItemClick(View v,int postion);
    }
}
