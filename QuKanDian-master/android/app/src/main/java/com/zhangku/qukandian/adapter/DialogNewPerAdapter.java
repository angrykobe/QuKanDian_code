package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.RecommendDailyBean;
import com.zhangku.qukandian.utils.ActivityUtils;

import java.util.List;

/**
 * 新人引导弹框任务
 */
public class DialogNewPerAdapter extends BaseRecyclerViewAdapter<RecommendDailyBean> {
    private OnCliclItemListener mOnCliclItemListener;

    public DialogNewPerAdapter(Context context, List beans, OnCliclItemListener onCliclItemListener) {
        super(context, beans);
        mOnCliclItemListener = onCliclItemListener;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_adapter_new_per, parent, false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, final int dataIndex, final RecommendDailyBean bean) {
        TaskViewHolder view = (TaskViewHolder) holder;
        view.mTvDes.setText(bean.getDisplayName());
        String s = bean.getCoinAmountScope() == 0 ? "" : (bean.getCoinAmountScope() + "元");
        String s1 = TextUtils.isEmpty(bean.getGoldAmountScope()) ? "" : (bean.getGoldAmountScope() + "金币");
        view.mTvGlod.setText(s + s1);
        view.mTvInfor.setText(""+bean.getDescription());

        if(bean.isFinished()){//任务已完成
            view.itemView.setOnClickListener(null);
            view.mGoTask.setSelected(true);
        }else{
            view.mGoTask.setSelected(false);
            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityUtils.startToMainActivity(mContext, 2,0);
                    if (null != mOnCliclItemListener) {
                        mOnCliclItemListener.onCliclItemListener(dataIndex);
                    }
                }
            });
        }
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mTvGlod;
        TextView mTvDes;
        TextView mTvInfor;
        View mGoTask;
        LinearLayout mLayout;

        public TaskViewHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView;
            mTvGlod = itemView.findViewById(R.id.item_dialog_task_remind_glod);
            mTvDes = itemView.findViewById(R.id.item_dialog_task_remind_des);
            mGoTask = itemView.findViewById(R.id.gotoTaskBtn);
            mTvInfor = itemView.findViewById(R.id.item_dialog_task_remind_infor);
        }
    }

    public interface OnCliclItemListener {
        void onCliclItemListener(int position);
    }
}
