package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.GoldBean;
import com.zhangku.qukandian.utils.CommonHelper;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/18
 */
public class ReadProfitAdapter extends BaseRecyclerViewAdapter<GoldBean> {

    public ReadProfitAdapter(Context context, List<GoldBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_read_profit,parent,false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder1, int viewPosition, int dataIndex, GoldBean goldBean) {
        MyViewHolder holder = (MyViewHolder) holder1;
        holder.moneyTV.setText(""+goldBean.getAmount());
        holder.timeTV.setText(""+goldBean.getCreationTime());
        holder.fromTV.setText(""+goldBean.getDescription());
        holder.timeTV.setText(CommonHelper.utcToString(goldBean.getCreationTime(), "yyyy-MM-dd HH:mm:ss"));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView moneyTV;
        private TextView timeTV;
        private TextView fromTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            moneyTV = itemView.findViewById(R.id.readProfitMoneyTV);
            timeTV = itemView.findViewById(R.id.readProfitTimeTV);
            fromTV = itemView.findViewById(R.id.readProfitClassTV);
        }
    }
}
