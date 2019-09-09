package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.HadDownAppBean;
import com.zhangku.qukandian.utils.GlideUtils;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 你不注释一下？
 */
public class GetGoldForHadDownAdapter extends BaseRecyclerViewAdapter<HadDownAppBean> {

    public GetGoldForHadDownAdapter(Context context, List<HadDownAppBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_get_gold_for_downapp, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final HadDownAppBean bean) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.appNameTV.setText(""+bean.getAppName());
        myViewHolder.goldNumTV.setText("+"+bean.getGold());
//        myViewHolder.appDecTV.setText("+"+bean.getDescription());
        GlideUtils.displayRoundImage(mContext, bean.getLogoImgSrc(), myViewHolder.appImg,6);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView goldNumTV;
        public TextView appDecTV;
        public TextView appNameTV;
        public TextView appStaringTV;
        public ImageView appImg;

        public MyViewHolder(View itemView) {
            super(itemView);
            goldNumTV = (TextView) itemView.findViewById(R.id.goldNumTV);
            appDecTV = (TextView) itemView.findViewById(R.id.appDecTV);
            appNameTV = (TextView) itemView.findViewById(R.id.appNameTV);
            appStaringTV = (TextView) itemView.findViewById(R.id.appStaringTV);
            appImg = (ImageView) itemView.findViewById(R.id.appImg);
        }
    }
}
