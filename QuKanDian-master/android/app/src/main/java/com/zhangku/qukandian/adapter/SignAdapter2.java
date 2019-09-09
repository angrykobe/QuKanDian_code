package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.EveryDaySignBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 2019-6-10 11:01:20
 * ljs
 */
public class SignAdapter2 extends RecyclerView.Adapter<SignAdapter2.MyViewHolder> {

    private List<EveryDaySignBean.MissionRulesBean> mList;
    private int signDay;//已签到天数
    private Context mContext;

    public SignAdapter2(List<EveryDaySignBean.MissionRulesBean> list) {
        mList = list;
    }

    public SignAdapter2(Context context) {
        mList = new ArrayList<>();
        mContext = context;
    }

    @Override
    public SignAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SignAdapter2.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_sign_day2, null));
    }

    @Override
    public void onBindViewHolder(SignAdapter2.MyViewHolder holder, int position) {
        holder.tv.setText("" + mList.get(position).getMinGoldAmount());
        int nowDay = position + 1;
        holder.tv_sign_days.setText(nowDay + "天");
        if (signDay >= (position + 1)) {
            holder.rl_top.setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_sign_red));
            holder.iv_icon.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_sign_complete));
        } else {
            holder.rl_top.setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_sign_gary));
            holder.iv_icon.setBackground(mContext.getResources().getDrawable(R.mipmap.icon_coin_sign));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() > 7 ? 7 : mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        TextView tv_sign_days;
        ImageView iv_icon;
        RelativeLayout rl_top;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.textview);
            tv_sign_days = itemView.findViewById(R.id.tv_sign_days);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            rl_top = itemView.findViewById(R.id.rl_top);
        }
    }

    public void setSignDay(int day) {
        signDay = day;
        notifyDataSetChanged();
    }

    /**
     * @param day   已签到天数
     * @param mList
     */
    public void setDate(int day, List<EveryDaySignBean.MissionRulesBean> mList) {
        signDay = day;
        this.mList = mList;
        notifyDataSetChanged();
    }
}
