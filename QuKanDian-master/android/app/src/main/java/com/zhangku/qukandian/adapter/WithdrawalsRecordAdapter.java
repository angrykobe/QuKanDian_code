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
import com.zhangku.qukandian.bean.WithdrawalsRecordBean;
import com.zhangku.qukandian.utils.CommonHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yuzuoning on 2017/4/12.
 */

public class WithdrawalsRecordAdapter extends BaseRecyclerViewAdapter<WithdrawalsRecordBean> {
    public WithdrawalsRecordAdapter(Context context, List<WithdrawalsRecordBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new WithdrawalsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_withdrawals_record_view, parent, false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, WithdrawalsRecordBean bean) {
        WithdrawalsHolder view = (WithdrawalsHolder) holder;
        view.mTvTitle.setText(bean.getCoinGift().getTitle());
        view.mTvTime.setText(CommonHelper.utcToString(bean.getCreationTime(), "yyyy-MM-dd HH:mm:ss"));
        if(0 == bean.getApplyStatus()){//申请中
            view.mTvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.black_33));
        }else if(100 == bean.getApplyStatus()){//审核中
            view.mTvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.black_33));
        }else if(500 == bean.getApplyStatus()){//提现失败
            view.mTvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.red));
        }else {//提现成功
            view.mTvStatus.setTextColor(ContextCompat.getColor(mContext,R.color.title_color));
        }
        view.mTvStatus.setText(bean.getStatusMemo());
    }

    class WithdrawalsHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        TextView mTvTime;
        TextView mTvStatus;

        public WithdrawalsHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.item_withdrawals_title);
            mTvTime = (TextView) itemView.findViewById(R.id.item_withdrawals_time);
            mTvStatus = (TextView) itemView.findViewById(R.id.item_withdrawals_status);
        }
    }
}
