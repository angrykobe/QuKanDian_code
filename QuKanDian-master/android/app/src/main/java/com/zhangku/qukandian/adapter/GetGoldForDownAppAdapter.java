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
import com.zhangku.qukandian.bean.DownAppBean;
import com.zhangku.qukandian.utils.GlideUtils;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 你不注释一下？
 */
public class GetGoldForDownAppAdapter extends BaseRecyclerViewAdapter<DownAppBean> {

    public GetGoldForDownAppAdapter(Context context, List<DownAppBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_get_gold_for_downapp, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final DownAppBean bean) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.appNameTV.setText(""+bean.getAppName());
        myViewHolder.appDecTV.setText(""+bean.getDescription());
        myViewHolder.goldNumTV.setText("+"+bean.getGold());
        GlideUtils.displayRoundImage(mContext, bean.getLogoImgSrc(), myViewHolder.appImg,6);

        //-1 无操作 0 开始下载 1、下载完成 2、安装完成（未打开app） 3、试玩中(打开了app) 4 任务完成
        switch (bean.getStage()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
                myViewHolder.appStaringTV.setVisibility(View.VISIBLE);
                break;
                default:
                    myViewHolder.appStaringTV.setVisibility(View.GONE);
                    break;
        }
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
