package com.zhangku.qukandian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.EveryDaySignBean;
import com.zhangku.qukandian.bean.TaskInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/5/18.
 */

public class SignAdapter extends RecyclerView.Adapter<SignAdapter.MyViewHolder> {

    private List<EveryDaySignBean.MissionRulesBean> mList;
    private int signDay;//已签到天数

    public SignAdapter(List<EveryDaySignBean.MissionRulesBean> list) {
        mList = list;
    }

    public SignAdapter() {
        mList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_sign_day, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText("+"+mList.get(position).getMinGoldAmount());
        holder.tv.setSelected( signDay>=(position+1) );
    }

    @Override
    public int getItemCount() {
        return mList.size()>7?7:mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textview);
        }
    }

    public void setSignDay(int day){
        signDay = day;
        notifyDataSetChanged();
    }

    /**
     * @param day  已签到天数
     * @param mList
     */
    public void setDate(int day,List<EveryDaySignBean.MissionRulesBean> mList) {
        signDay = day;
        this.mList = mList;
        notifyDataSetChanged();
    }
}
