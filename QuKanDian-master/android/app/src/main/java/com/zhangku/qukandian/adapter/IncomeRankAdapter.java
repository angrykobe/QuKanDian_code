package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.RankBean;

import java.util.List;

/**
 * Created by yuzuoning on 2017/4/14.
 */

public class IncomeRankAdapter extends BaseRecyclerViewAdapter<RankBean.TopListBean> {
    public IncomeRankAdapter(Context context, List<RankBean.TopListBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new RankHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_student_view, parent, false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, RankBean.TopListBean bean) {
        RankHolder view = (RankHolder) holder;
        if (dataIndex == 0) {
            view.mTvRank.setBackgroundResource(R.mipmap.student_rank_one_icon);
            view.mTvRank.setText("");
            view.mTvPhone.setText(bean.getNickName() + "");
            view.mTvUserId.setText(bean.getMentorUserSumMentee() + "");
            view.mTvGold.setText(bean.getCoinAccountSum() + "");

            view.mTvPhone.setTextColor(ContextCompat.getColor(mContext, R.color.red_f2));
            view.mTvUserId.setTextColor(ContextCompat.getColor(mContext, R.color.red_f2));
            view.mTvGold.setTextColor(ContextCompat.getColor(mContext, R.color.red_f2));
        } else if (dataIndex == 1) {
            view.mTvRank.setBackgroundResource(R.mipmap.student_rank_two);
            view.mTvRank.setText("");
            view.mTvPhone.setText(bean.getNickName());
            view.mTvUserId.setText(bean.getMentorUserSumMentee() + "");
            view.mTvGold.setText(bean.getCoinAccountSum() + "");

            view.mTvPhone.setTextColor(ContextCompat.getColor(mContext, R.color.red_f2));
            view.mTvUserId.setTextColor(ContextCompat.getColor(mContext, R.color.red_f2));
            view.mTvGold.setTextColor(ContextCompat.getColor(mContext, R.color.red_f2));
        } else if (dataIndex == 2) {
            view.mTvRank.setBackgroundResource(R.mipmap.student_rank_three);
            view.mTvRank.setText("");
            view.mTvPhone.setText(bean.getNickName());
            view.mTvUserId.setText(bean.getMentorUserSumMentee() + "");
            view.mTvGold.setText(bean.getCoinAccountSum() + "");

            view.mTvPhone.setTextColor(ContextCompat.getColor(mContext, R.color.red_f2));
            view.mTvUserId.setTextColor(ContextCompat.getColor(mContext, R.color.red_f2));
            view.mTvGold.setTextColor(ContextCompat.getColor(mContext, R.color.red_f2));
        } else {
            view.mTvRank.setBackgroundResource(R.color.white);
            view.mTvRank.setText((dataIndex + 1) + "");
            view.mTvPhone.setText(bean.getNickName());
            view.mTvUserId.setText(bean.getMentorUserSumMentee() + "");
            view.mTvGold.setText(bean.getCoinAccountSum() + "");

            view.mTvRank.setTextColor(ContextCompat.getColor(mContext, R.color.black_33));
            view.mTvPhone.setTextColor(ContextCompat.getColor(mContext, R.color.black_33));
            view.mTvUserId.setTextColor(ContextCompat.getColor(mContext, R.color.black_33));
            view.mTvGold.setTextColor(ContextCompat.getColor(mContext, R.color.black_33));
        }

    }

    class RankHolder extends RecyclerView.ViewHolder {
        TextView mTvRank;
        TextView mTvPhone;
        TextView mTvUserId;
        TextView mTvGold;

        public RankHolder(View itemView) {
            super(itemView);
            mTvRank = (TextView) itemView.findViewById(R.id.item_my_student_view_rank);
            mTvPhone = (TextView) itemView.findViewById(R.id.item_my_student_view_phone);
            mTvUserId = (TextView) itemView.findViewById(R.id.item_my_student_view_userid);
            mTvGold = (TextView) itemView.findViewById(R.id.item_my_student_view_gold);
        }
    }
}
