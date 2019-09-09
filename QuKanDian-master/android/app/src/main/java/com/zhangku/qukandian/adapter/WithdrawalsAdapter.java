package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.GiftBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.dialog.DialogConfirm;
import com.zhangku.qukandian.dialog.DialogWithdrawals;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewUserInfoProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.LogUtils;

import java.util.List;

public class WithdrawalsAdapter extends BaseRecyclerViewAdapter<GiftBean> {
    private OnStartLoading mOnStartLoading;
    private WithdrawalsHolder lastViewHolder;
    private WithdrawalsInforHolder withdrawalsInforHolder;
    private WithdrawalsInforHolder withdrawalsInforHolder1;
    private int spiltPosition = 0;

    public void reset() {
        if (lastViewHolder != null) {
            lastViewHolder.mTvMoneyFree.setBackgroundResource(R.mipmap.item_withdrawals_normal);
        }
        lastViewHolder = null;
        notifyDataSetChanged();
    }

    @Override
    protected int getTypeView(int postion) {
        if (mBeans.get(postion - 1).getGold() == -2) {
            spiltPosition = postion - 1;
            return TYPE_ONE;
        }
        if (mBeans.get(postion - 1).getGold() == -3) {
            return TYPE_THREE;
        }
        return super.getTypeView(postion);
    }

    public WithdrawalsAdapter(Context context, List<GiftBean> beans, OnStartLoading onStartLoading) {
        super(context, beans);
        mOnStartLoading = onStartLoading;
        withdrawalsInforHolder = new WithdrawalsInforHolder(LayoutInflater.from(mContext).inflate(R.layout.item_withdrawals_infor, null, false));
        withdrawalsInforHolder1 = new WithdrawalsInforHolder(LayoutInflater.from(mContext).inflate(R.layout.item_withdrawals_infor, null, false));
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            return withdrawalsInforHolder;
        } else if (viewType == TYPE_THREE) {
            withdrawalsInforHolder1.mWithdrawalsInforLlNext.setVisibility(View.GONE);
            return withdrawalsInforHolder1;
        } else {
            return new WithdrawalsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_withdrawals_view, parent, false));
        }
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, final int viewPosition, final int dataIndex, final GiftBean bean) {
        if (holder instanceof WithdrawalsHolder) {
            final WithdrawalsHolder view = (WithdrawalsHolder) holder;
            view.itemView.setTag(bean);
            //余额不够 变灰
            if (UserManager.getInst().getUserBeam().getGoldAccount().getAmount() < bean.getGold()) {
                view.mTvMoneyFree.setTextColor(CommonHelper.parseColor("#666666"));
                view.mTvMoneyFree.setBackgroundResource(R.mipmap.item_withdrawals_gray);
            } else {
                if ("OneYuan".equals(bean.getName())) {//一元提现背景框
//                    view.mTvMoneyFree.setBackgroundResource(R.mipmap.item_withdrawals_xs);
                    //去掉新手福利标识
                    view.mTvMoneyFree.setBackgroundResource(R.mipmap.item_withdrawals_normal);
                } else if (bean.isLevel())
                    view.mTvMoneyFree.setBackgroundResource(R.mipmap.icon_djtq);
                else
                    view.mTvMoneyFree.setBackgroundResource(R.mipmap.item_withdrawals_normal);

            }
            view.mTvMoneyFree.setText(CommonHelper.form2(bean.getFee()) + "元");
            view.mTvMoneyTitle.setText(bean.getGold() + "金币");
            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (UserManager.getInst().getUserBeam().getGoldAccount().getAmount() >= bean.getGold()) {
                        LogUtils.LogW("curr:" + viewPosition + ":split:" + spiltPosition);
                        if (lastViewHolder != null) {
                            //还原上一个选中背景图
                            GiftBean tag = (GiftBean) lastViewHolder.itemView.getTag();
                            if ("OneYuan".equals(tag.getName())) {//一元提现背景框
                                MobclickAgent.onEvent(mContext, "293-xinshoutixian");
//                                lastViewHolder.mTvMoneyFree.setBackgroundResource(R.mipmap.item_withdrawals_xs);
                                //去掉新手福利标识
                                lastViewHolder.mTvMoneyFree.setBackgroundResource(R.mipmap.item_withdrawals_normal);
                            } else if (tag.isLevel()) {
                                lastViewHolder.mTvMoneyFree.setBackgroundResource(R.mipmap.icon_djtq);
                            } else {
                                if (bean.getFee() == 1) {
                                    MobclickAgent.onEvent(mContext, "293-1xiaoetixian");
                                } else if (bean.getFee() == 10) {
                                    MobclickAgent.onEvent(mContext, "293-10xiaoetixian");
                                }
                                lastViewHolder.mTvMoneyFree.setBackgroundResource(R.mipmap.item_withdrawals_normal);
                            }
                        }
                        lastViewHolder = view;
                        //设置选中背景图
                        view.mTvMoneyFree.setBackgroundResource(R.mipmap.item_withdrawals_selected);
                        if (!TextUtils.isEmpty(bean.getDesc())) {
                            if (viewPosition <= spiltPosition) {
                                withdrawalsInforHolder.mWithdrawalsInforTV.setVisibility(View.VISIBLE);
                                withdrawalsInforHolder1.mWithdrawalsInforTV.setVisibility(View.GONE);
                                withdrawalsInforHolder.mWithdrawalsInforTV.setText(Html.fromHtml(mContext.getString(R.string.withdrawals_infor, bean.getDesc())));
                            } else {
                                withdrawalsInforHolder.mWithdrawalsInforTV.setVisibility(View.GONE);
                                withdrawalsInforHolder1.mWithdrawalsInforTV.setVisibility(View.VISIBLE);
                                withdrawalsInforHolder1.mWithdrawalsInforTV.setText(Html.fromHtml(mContext.getString(R.string.withdrawals_infor, bean.getDesc())));
                            }
                            MobclickAgent.onEvent(mContext, "294--xiaoetixiainjine");
                        } else {
                            withdrawalsInforHolder.mWithdrawalsInforTV.setVisibility(View.GONE);
                            withdrawalsInforHolder1.mWithdrawalsInforTV.setVisibility(View.GONE);
                            MobclickAgent.onEvent(mContext, "294-wumenkanjine");
                        }
                        mOnStartLoading.onSelected(bean);
                    } else {//余额不够
                        MobclickAgent.onEvent(mContext, "294-yuebuzutanchuang");
                        new DialogConfirm(mContext)
                                .setTitles(R.string.go_to_task_str)
                                .setYesBtnText(R.string.goto_task_btn)
                                .setListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MobclickAgent.onEvent(mContext, "04_07_05_clicktaskbtn");
                                        ActivityUtils.startToMainActivity(mContext, 2, 0);
                                    }
                                })
                                .show();
                    }
                }
            });
        }
    }

    class WithdrawalsHolder extends RecyclerView.ViewHolder {
        TextView mTvMoneyTitle;
        TextView mTvMoneyFree;

        public WithdrawalsHolder(View itemView) {
            super(itemView);
            mTvMoneyTitle = itemView.findViewById(R.id.item_withdrawals_money_title);
            mTvMoneyFree = itemView.findViewById(R.id.item_withdrawals_money_free);
        }
    }


    class WithdrawalsInforHolder extends RecyclerView.ViewHolder {
        TextView mWithdrawalsInforTV;
        LinearLayout mWithdrawalsInforLlNext;

        public WithdrawalsInforHolder(View itemView) {
            super(itemView);
            mWithdrawalsInforTV = itemView.findViewById(R.id.mWithdrawalsInforTV);
            mWithdrawalsInforLlNext = itemView.findViewById(R.id.mWithdrawalsInforLlNext);
        }
    }

    public GiftBean getChooseBean() {
        if (lastViewHolder != null)
            return (GiftBean) lastViewHolder.itemView.getTag();
        else
            return null;
    }

    public interface OnStartLoading {
        void onSelected(GiftBean bean);
    }

    public void setInforTVGone(){
        withdrawalsInforHolder.mWithdrawalsInforTV.setVisibility(View.GONE);
        withdrawalsInforHolder1.mWithdrawalsInforTV.setVisibility(View.GONE);
    }
}
