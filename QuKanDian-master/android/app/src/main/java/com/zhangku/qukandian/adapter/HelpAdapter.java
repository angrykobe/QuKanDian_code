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
import com.zhangku.qukandian.bean.HelpBean;
import com.zhangku.qukandian.widght.ItemHelpChildView;

import java.util.List;

/**
 * Created by yuzuoning on 2017/4/19.
 */

public class HelpAdapter extends BaseRecyclerViewAdapter<HelpBean> {
    public HelpAdapter(Context context, List<HelpBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new HelpHolder(LayoutInflater.from(mContext).inflate(R.layout.item_help_view,parent,false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, HelpBean bean) {
        HelpHolder view = (HelpHolder) holder;
        if("金币问题".equals(bean.getDisplayName())){
            view.mIvIcon.setBackgroundResource(R.mipmap.course_gold);
        }else if("收徒问题".equals(bean.getDisplayName())) {
            view.mIvIcon.setBackgroundResource(R.mipmap.course_student);
        }else if("提现问题".equals(bean.getDisplayName())) {
            view.mIvIcon.setBackgroundResource(R.mipmap.course_withdrawals);
        }else{
            view.mIvIcon.setBackgroundResource(R.mipmap.course_novice);
        }
        view.mTvTitle.setText(bean.getDisplayName());
        view.mItemHelpChildView.setData(bean.getDocHelpers());
    }

    class HelpHolder extends RecyclerView.ViewHolder{
        ImageView mIvIcon;
        TextView mTvTitle;
        ItemHelpChildView mItemHelpChildView;
        public HelpHolder(View itemView) {
            super(itemView);
            mIvIcon = (ImageView) itemView.findViewById(R.id.item_help_view_icon);
            mTvTitle = (TextView) itemView.findViewById(R.id.item_help_view_title);
            mItemHelpChildView = (ItemHelpChildView) itemView.findViewById(R.id.item_help_child_view_layout);
        }
    }
}
