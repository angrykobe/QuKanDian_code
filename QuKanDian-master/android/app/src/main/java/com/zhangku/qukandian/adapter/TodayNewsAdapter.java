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
import com.zhangku.qukandian.bean.TodayNewBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.GlideUtils;

import java.util.List;

/**
 * Created by yuzuoning on 2017/8/28.
 */

public class TodayNewsAdapter extends BaseRecyclerViewAdapter<TodayNewBean> {
    public TodayNewsAdapter(Context context, List<TodayNewBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new TodayNewsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_today_news_layout, parent, false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final TodayNewBean bean) {
        TodayNewsHolder view = (TodayNewsHolder) holder;
        view.mTvTitle.setText(bean.getPostText().getTitle());
        view.mTvTime.setText(CommonHelper.utcToString(bean.getPushTime(), "HH:mm"));
        GlideUtils.displayImage(mContext, bean.getPostText().getFirstDefaultImage().getSrc(), view.mIvImageView);
        view.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getPostText().getTextType() == 0) {
                    ActivityUtils.startToInformationDetailsActivity(mContext, bean.getPostId(),AnnoCon.ART_DETAIL_FROM_ORDINARY);
//                    if (Constants.XIGUANG_NAME.equals(bean.getChannelName())) {
//                        ActivityUtils.startToInformationDetailsActivity(mContext, bean.getId(), AdConstant.ART_DETAIL_FROM_XIGUANG);
//                    } else if (Constants.HIGH_PRICE_NAME.equals(bean.getChannelName()) ) {
//                        ActivityUtils.startToInformationDetailsActivity(mContext, bean.getId(), AdConstant.ART_DETAIL_FROM_HIGH_PRICE);
//                    }else{
//                        ActivityUtils.startToInformationDetailsActivity(mContext, bean.getId(), AdConstant.ART_DETAIL_FROM_ORDINARY);
//                    }
                } else {
                    ActivityUtils.startToVideoDetailsActivity(mContext, bean.getPostId(),1);
                }
            }
        });
    }

    class TodayNewsHolder extends RecyclerView.ViewHolder {
        TextView mTvTime;
        TextView mTvTitle;
        ImageView mIvImageView;
        LinearLayout mLayout;

        public TodayNewsHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView;
            mTvTime = (TextView) itemView.findViewById(R.id.item_today_news_time);
            mTvTitle = (TextView) itemView.findViewById(R.id.item_today_news_title);
            mIvImageView = (ImageView) itemView.findViewById(R.id.item_today_news_img);
        }
    }
}
