package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.SharedBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.GlideUtils;

import java.util.List;

/**
 * Created by yuzuoning on 2017/11/15.
 */

public class MySharedAdapter extends BaseRecyclerViewAdapter<SharedBean> {
    public MySharedAdapter(Context context, List<SharedBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ShareViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_my_shared_layout, parent, false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final SharedBean bean) {
        ShareViewHolder view = (ShareViewHolder) holder;
        if(null != bean.getPostText()){
            view.mTvTitle.setText(bean.getPostText().getTitle());
            GlideUtils.displayImage(mContext,bean.getPostText().getFirstDefaultImage().getSrc(),view.mIvImage);
        }
        view.mTvTime.setText("最新奖励:"+ CommonHelper.utcToString(bean.getCreationTime(),"yyyy-MM-dd hh:mm"));
        view.mTvGold.setText("已奖励"+bean.getTotalAmount()+"金币");
        view.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bean.getPostText().getTextType() == Constants.TYPE_NEW){
                    ActivityUtils.startToInformationDetailsActivity(mContext,bean.getPostId(),AnnoCon.ART_DETAIL_FROM_ORDINARY);

//                    if (Constants.XIGUANG_NAME.equals(bean.getChannelName())) {
//                        ActivityUtils.startToInformationDetailsActivity(mContext, bean.getId(), AdConstant.ART_DETAIL_FROM_XIGUANG);
//                    } else if (Constants.HIGH_PRICE_NAME.equals(bean.getChannelName()) ) {
//                        ActivityUtils.startToInformationDetailsActivity(mContext, bean.getId(), AdConstant.ART_DETAIL_FROM_HIGH_PRICE);
//                    }else{
//                        ActivityUtils.startToInformationDetailsActivity(mContext, bean.getId(), AdConstant.ART_DETAIL_FROM_ORDINARY);
//                    }
                }else {
                    ActivityUtils.startToVideoDetailsActivity(mContext,bean.getPostId(),-1);
                }
            }
        });
    }

    class ShareViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLayout;
        TextView mTvTitle;
        ImageView mIvImage;
        TextView mTvGold;
        TextView mTvTime;

        public ShareViewHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView;
            mTvTitle = itemView.findViewById(R.id.item_my_shared_title);
            mIvImage = itemView.findViewById(R.id.item_my_shared_img);
            mTvGold = itemView.findViewById(R.id.item_my_shared_gold);
            mTvTime = itemView.findViewById(R.id.item_my_shared_time);
        }
    }
}
