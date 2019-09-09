package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.DownAppBean;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.GlideUtils;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 你不注释一下？
 */
public class DownAppDetailImageAdapter extends BaseRecyclerViewAdapter<DownAppBean.BountyTaskStepBean.BountyTaskStepImgsBean> {

    public DownAppDetailImageAdapter(Context context, List<DownAppBean.BountyTaskStepBean.BountyTaskStepImgsBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_imageview, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final DownAppBean.BountyTaskStepBean.BountyTaskStepImgsBean bean) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
//        myViewHolder.imageview.setText(""+bean.getAppName());
        GlideUtils.displayImage(mContext,bean.getStepImgSrc(),myViewHolder.imageview);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageview;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview);


            ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) imageview.getLayoutParams();
            int width = DisplayUtils.getScreenWidth(QuKanDianApplication.getmContext()) * 650 / 720;
            linearParams.width = width;
            linearParams.height = 370*width/650;
            imageview.setLayoutParams(linearParams);
        }
    }
}
