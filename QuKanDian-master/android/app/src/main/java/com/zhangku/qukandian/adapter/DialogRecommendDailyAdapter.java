package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.RecommendDailyBean;
import com.zhangku.qukandian.utils.ActivityUtils;

import java.util.List;

/**
 * Created by yuzuoning on 2017/12/29.
 * 任务完成弹框广告item的样子？
 */

public class DialogRecommendDailyAdapter extends BaseRecyclerViewAdapter<RecommendDailyBean> {
    private OnCliclItemListener mOnCliclItemListener;
    //type（1：首页任务弹窗，2：签到弹窗，3：宝箱弹窗）
    private int type;

    public DialogRecommendDailyAdapter(Context context, List beans, OnCliclItemListener onCliclItemListener) {
        super(context, beans);
        mOnCliclItemListener = onCliclItemListener;
    }

    public void setType(int type){
        this.type = type;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dialog_task_remind_view, parent, false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, final int dataIndex, final RecommendDailyBean bean) {
        TaskViewHolder view = (TaskViewHolder) holder;
        view.mTvTitile.setText(bean.getDisplayName());
        view.mTvDes.setText(bean.getDescription());
        String s = bean.getCoinAmountScope() == 0 ? "" : (bean.getCoinAmountScope() + "元");
//        String s1 = bean.getGoldAmountScope() == 0 ? "" : (bean.getGoldAmountScope() + "金币");
        String s1 = TextUtils.isEmpty(bean.getGoldAmountScope()) ? "" : (bean.getGoldAmountScope() + "金币");
        view.mTvGlod.setText(s + s1);

        view.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type == 2){
                    MobclickAgent.onEvent(mContext, "293-baoxiangtanchuang");
                }else if(type == 3){
                    MobclickAgent.onEvent(mContext, "293-qiandaotuijian");
                }else{
                    MobclickAgent.onEvent(mContext, "05_01_intobaoxiangwindow");
                }
                ActivityUtils.startToMainActivity(mContext, 2,0);
                if (null != mOnCliclItemListener) {
                    mOnCliclItemListener.onCliclItemListener(dataIndex);
                }
            }
        });
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTitile;
        TextView mTvGlod;
        TextView mTvDes;
        LinearLayout mLayout;

        public TaskViewHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView;
            mTvTitile = itemView.findViewById(R.id.item_dialog_task_remind_title);
            mTvGlod = itemView.findViewById(R.id.item_dialog_task_remind_glod);
            mTvDes = itemView.findViewById(R.id.item_dialog_task_remind_des);
        }
    }

    public interface OnCliclItemListener {
        void onCliclItemListener(int position);
    }
}
