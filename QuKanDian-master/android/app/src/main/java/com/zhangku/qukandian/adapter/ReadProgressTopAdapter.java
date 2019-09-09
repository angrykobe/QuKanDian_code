package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.PutReadProgessBean;
import com.zhangku.qukandian.bean.ReadProgressBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutReadProgessProtocol;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class ReadProgressTopAdapter extends BaseRecyclerViewAdapter<Object> {

    public static ArrayList<Integer> finishedBean = new ArrayList<>();

    public ReadProgressTopAdapter(Context context, List<Object> beans) {
        super(context, beans);

    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int type) {
        RecyclerView.ViewHolder viewHolder = new ReadProgressTopAdapter.MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_read_progress_top, parent, false));
        return viewHolder;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final Object bean1) {
        final MyViewHolder mView = (MyViewHolder) holder;
        final ReadProgressBean.ProgressRuleBean bean = (ReadProgressBean.ProgressRuleBean) bean1;
        mView.mTitleTV.setText("" + bean.getAdsCnt() + "");
        mView.mTvAddGold.setText("+" + bean.getGold() + "金币");
        mView.mProgressBar.setMax(bean.getAdsCnt());
        //当前阅读数量
        int nowRead = UserManager.adsProgressCnt;
        //不满足阅读数量 ，领取按钮 致灰
        boolean isCanGet = false;

        if (nowRead <= 0) {
            mView.mProgressBar.setProgress(0);
            isCanGet = false;
        } else if (0 < nowRead && nowRead < bean.getAdsCnt()) {
            mView.mProgressBar.setProgress(nowRead);
            isCanGet = false;
        } else if (nowRead >= bean.getAdsCnt()) {
            mView.mProgressBar.setProgress(bean.getAdsCnt());
            isCanGet = true;
        }

        mView.mTaskStateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = mView.mTaskStateTV.getText().toString();
                if (text.equals("已领取")) {
                    ToastUtils.showShortToast(mContext, "今日已领取该奖励，明日再来吧~");
                    return;
                }

                new PutReadProgessProtocol(mContext, bean.getOrderId(), new BaseModel.OnResultListener<PutReadProgessBean>() {
                    @Override
                    public void onResultListener(PutReadProgessBean response) {
                        CustomToast.showToast(mContext, "" + response.getGoldAmount(), "");
                        setStateView(0, mView.mTaskStateTV, false);
                        UserManager.getInst().goldChangeNofity(response.getGoldAmount());

                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        ToastUtils.showShortToast(mContext, error);
                    }
                }).postRequest();
            }
        });

        if (finishedBean != null && finishedBean.size() > 0) {
            for (int i = 0; i < finishedBean.size(); i++) {
                int orderId = finishedBean.get(i);
                if (bean.getOrderId() == orderId) {
                    setStateView(0, mView.mTaskStateTV, isCanGet);
                    return;
                } else {
                    setStateView(1, mView.mTaskStateTV, isCanGet);
                }
            }
        }else {
            setStateView(1, mView.mTaskStateTV, isCanGet);
        }

    }

    private void setStateView(int type, TextView mView, boolean isCanGet) {
        if (type == 0) {
            mView.setText("已领取");
            mView.setTextColor(mContext.getResources().getColor(R.color.gray_9));
            mView.setBackground(mContext.getResources().getDrawable(R.drawable.share_recir_whitebg_grayframe));
        } else if (type == 1) {
            mView.setText("领取");
            if (isCanGet) {
                mView.setTextColor(mContext.getResources().getColor(R.color.tab_select_new));
                mView.setBackground(mContext.getResources().getDrawable(R.drawable.share_recir_tranbg_redframe));
            } else {
                mView.setTextColor(mContext.getResources().getColor(R.color.gray_9));
                mView.setBackground(mContext.getResources().getDrawable(R.drawable.share_recir_whitebg_grayframe));
            }

        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTV;
        TextView mTvAddGold;
        TextView mTaskStateTV;
        TextView mProgressTV;
        ProgressBar mProgressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTitleTV = itemView.findViewById(R.id.titleTV);
            mTvAddGold = itemView.findViewById(R.id.tv_add_gold);
            mTaskStateTV = itemView.findViewById(R.id.mTaskStateTV);
            mProgressBar = itemView.findViewById(R.id.mProgressBar);
            mProgressTV = itemView.findViewById(R.id.mProgressTV);
        }
    }

}
