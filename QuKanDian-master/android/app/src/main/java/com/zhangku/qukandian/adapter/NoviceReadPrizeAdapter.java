package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.NoviceReadon;
import com.zhangku.qukandian.utils.LayoutInflaterUtils;

import java.util.List;

/**
 * Created by yuzuoning on 2017/4/6.
 */

public class NoviceReadPrizeAdapter extends BaseRecyclerViewAdapter<NoviceReadon> {
    public NoviceReadPrizeAdapter(Context context, List<NoviceReadon> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NoviceHolder(LayoutInflaterUtils.inflateView(mContext, R.layout.item_novice_read_prize_adapter_view));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, NoviceReadon bean) {
        NoviceHolder view = (NoviceHolder) holder;
        view.mTvDate.setText(dataIndex + 1 + "");
        if ("00000000-0000-0000-0000-000000000000".equals(bean.getId())) {
            if (bean.isOverdue()) {
                view.mIvState.setSelected(bean.isIsReading());
                view.mTvDate.setSelected(bean.isIsReading());
                view.mIvState.setVisibility(View.VISIBLE);
            } else {
                view.mIvState.setVisibility(View.INVISIBLE);
                view.mTvDate.setTextColor(Color.BLACK);
            }
        } else {
            view.mIvState.setVisibility(View.VISIBLE);
            view.mIvState.setSelected(bean.isIsReading());
            view.mTvDate.setSelected(bean.isIsReading());
        }

    }

    class NoviceHolder extends RecyclerView.ViewHolder {
        TextView mTvDate;
        ImageView mIvState;

        public NoviceHolder(View itemView) {
            super(itemView);
            mTvDate = (TextView) itemView.findViewById(R.id.item_novice_read_prize_adapter_view_text);
            mIvState = (ImageView) itemView.findViewById(R.id.item_novice_read_prize_adapter_view_state);
        }
    }
}
