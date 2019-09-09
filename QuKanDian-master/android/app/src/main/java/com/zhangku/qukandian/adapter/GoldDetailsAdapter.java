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
 * Created by yuzuoning on 2017/4/12.
 */

public class GoldDetailsAdapter extends BaseRecyclerViewAdapter<GoldBean> {
    public static final int TYPE_GOLD = 0;
    public static final int TYPE_COIN = 1;

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public GoldDetailsAdapter(Context context, List<GoldBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new GoldHolder(LayoutInflater.from(mContext).inflate(R.layout.item_gold_view, parent, false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, GoldBean bean) {
        GoldHolder view = (GoldHolder) holder;
        view.mTvName.setText(bean.getDescription());
        int gold = (int) bean.getAmount();
//        if (gold > 0) {
            view.mTvGold.setText(gold + "金币");
            view.mTvMoney.setVisibility(View.GONE);
//        } else {
//            view.mTvMoney.setVisibility(View.VISIBLE);
//            view.mTvGold.setText(bean.getDescription());
//            view.mTvMoney.setText("扣除"+gold + "金币");
//        }
        view.mTvTime.setText(CommonHelper.utcToString(bean.getCreationTime(), "yyyy-MM-dd HH:mm:ss"));
    }

    class GoldHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        TextView mTvGold;
        TextView mTvTime;
        TextView mTvMoney;

        public GoldHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.item_gold_name);
            mTvGold = (TextView) itemView.findViewById(R.id.item_gold_gold);
            mTvTime = (TextView) itemView.findViewById(R.id.item_gold_time);
            mTvMoney = itemView.findViewById(R.id.item_gold_money);
        }
    }
}
