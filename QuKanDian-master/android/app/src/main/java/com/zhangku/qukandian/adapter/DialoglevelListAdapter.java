package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.UserMenuConfig;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.GlideUtils;

import java.util.List;

public class DialoglevelListAdapter extends BaseRecyclerViewAdapter<UserMenuConfig> {

    public DialoglevelListAdapter(Context context, List beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new DialoglevelListAdapter.TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dialog_level, parent, false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, final int dataIndex, final UserMenuConfig bean) {
        final DialoglevelListAdapter.TaskViewHolder view = (DialoglevelListAdapter.TaskViewHolder) holder;
        view.mTitleTV.setText(""+bean.getName());
        GlideUtils.displayCircleImage(mContext, bean.getImgSrc()
                , view.img, 0, 0, GlideUtils.getUserNormalOptions(), true);
        view.mTaskStateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bean.getName().contains("任务宝箱")){
                    MobclickAgent.onEvent(mContext, "296-levebaoxiang1");
                    MobclickAgent.onEvent(mContext, "296-levebaoxiang2");
                }else if(bean.getName().contains("幸运转盘")){
                    MobclickAgent.onEvent(mContext, "296-levelzhuanpan1");
                    MobclickAgent.onEvent(mContext, "296-levelzhuanpan2");
                }
                ActivityUtils.startToAnyWhereJsonActivity(mContext, bean.getGotoLink());
            }
        });
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTV;
        TextView mTaskStateTV;
        ImageView img;

        public TaskViewHolder(View itemView) {
            super(itemView);
            mTitleTV = itemView.findViewById(R.id.titleTV);
            mTaskStateTV = itemView.findViewById(R.id.mTaskStateTV);
            img = itemView.findViewById(R.id.img);
        }
    }
}
