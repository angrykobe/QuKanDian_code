package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.EventBusBean.ChaiBaoxiangEvent;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogNewPeopleGuide;
import com.zhangku.qukandian.interfaces.DialogOnDismissListener;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutNewTaskProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AnimUtil;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class NewPeopleTaskListAdapter extends BaseRecyclerViewAdapter<NewPeopleTaskBean> {
    private PutNewTaskProtocol mSubmitTaskProtocol;

    public NewPeopleTaskListAdapter(Context context, List beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new NewPeopleTaskListAdapter.TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dialog_newpeople_task2, parent, false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, final int dataIndex, final NewPeopleTaskBean bean) {
        final NewPeopleTaskListAdapter.TaskViewHolder view = (NewPeopleTaskListAdapter.TaskViewHolder) holder;
        if (bean.isChange()) AnimUtil.animBig(view.itemView);
        view.mTitleTV.setText(""+bean.getDisplayName());
        view.mTvDescription.setText(""+bean.getDescription());
//        view.mGoldNumTV.setText(""+(TextUtils.isEmpty(bean.getCoinAmountScope()) ? "" : bean.getCoinAmountScope() + "元+")
//                + (TextUtils.isEmpty(bean.getGoldAmountScope()) ? "" : "+" + bean.getGoldAmountScope()));
        view.itemView.setAlpha(1f);
        view.mTaskStateTV.setEnabled(true);
        view.mTaskStateTV.setSelected(false);
        if (bean.isIsFinished()) {
            view.mTaskStateTV.setSelected(true);
            view.mTaskStateTV.setText("已完成");
//            view.mProgressBar.setMax(1);
//            view.mProgressBar.setProgress(1);
//            view.mProgressTV.setText("1/1");
            view.mTaskStateTV.setOnClickListener(null);

            if (bean.getKindType() == 4) {
//                view.mProgressTV.setText(bean.getAwardsTime() + "/" + bean.getAwardsTime());
            }
        } else if (bean.isIshide()) {
            view.itemView.setAlpha(0.5f);
            view.mTaskStateTV.setEnabled(false);
            view.mTaskStateTV.setText("待解锁");
//            view.mProgressBar.setMax(1);
//            view.mProgressBar.setProgress(0);
//            view.mProgressTV.setText("0/1");
            view.mTitleTV.setText("神秘任务");
//            view.mGoldNumTV.setText("+？？？？");

            view.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showTostShortCentent(mContext,"即将解锁，先去获得其它新手福利吧");
                }
            });
        } else {
            view.mTaskStateTV.setText("开始");
            //多任务
            if (bean.getKindType() == 4) {
//                view.mProgressTV.setText(bean.getFinishedTime() + "/" + bean.getAwardsTime());
//                view.mProgressBar.setMax(bean.getAwardsTime());
//                view.mProgressBar.setProgress(bean.getFinishedTime());
                if(Constants.FIRST_RESOU.equals(bean.getName()) && bean.getFinishedTime() == bean.getAwardsTime()){
                    view.mTaskStateTV.setText("领取");
                }
            } else {
//                view.mProgressBar.setMax(1);
//                view.mProgressBar.setProgress(0);
//                view.mProgressTV.setText("0/1");
            }
            view.mTaskStateTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constants.FIRST_READING.equals(bean.getName())) {//新手第一次阅读
                        MobclickAgent.onEvent(mContext, "294-alertyuedu");
                        MobclickAgent.onEvent(mContext, "296-newyueduzhuanjinbi1");
                        MobclickAgent.onEvent(mContext, "296-newyueduzhuanjinbi2");
                        new DialogNewPeopleGuide(mContext, R.mipmap.newpeople_task_guide_1, new DialogOnDismissListener() {
                            @Override
                            public void onDismissListener() {
                                if (bean.getGotoLink() == null) {
                                    ActivityUtils.startToMainActivity(mContext, 0, 0);
                                } else {
                                    String[] url = bean.getGotoLink().split("\\|");
                                    if (url.length > 2) {
                                        ActivityUtils.startToAssignActivity(mContext, url[0], Integer.valueOf(url[1]), Integer.valueOf(url[2]));
                                    } else if (url.length > 1) {
                                        ActivityUtils.startToAssignActivity(mContext, url[0], Integer.valueOf(url[1]));
                                    } else {
                                        ActivityUtils.startToAssignActivity(mContext, url[0], -1);
                                    }
                                }
                            }
                        }).show();
                    } else if (Constants.FIRST_ADS_GOLD.equals(bean.getName())) {//新手第一次广告红包获取
                        MobclickAgent.onEvent(mContext, "294-alertyueduhongbao");
                        MobclickAgent.onEvent(mContext, "296-newyueduhongbao1");
                        MobclickAgent.onEvent(mContext, "296-newyueduhongbao2");
                        new DialogNewPeopleGuide(mContext, R.mipmap.newpeople_task_guide_2, new DialogOnDismissListener() {
                            @Override
                            public void onDismissListener() {
                                if (bean.getGotoLink() == null) {
                                    ActivityUtils.startToMainActivity(mContext, 0, 0);
                                } else {
                                    String[] url = bean.getGotoLink().split("\\|");
                                    if (url.length > 2) {
                                        ActivityUtils.startToAssignActivity(mContext, url[0], Integer.valueOf(url[1]), Integer.valueOf(url[2]));
                                    } else if (url.length > 1) {
                                        ActivityUtils.startToAssignActivity(mContext, url[0], Integer.valueOf(url[1]));
                                    } else {
                                        ActivityUtils.startToAssignActivity(mContext, url[0], -1);
                                    }
                                }
                            }
                        }).show();
                    } else if (Constants.FIRST_RESOU.equals(bean.getName())) {//新手热搜多任务
                        MobclickAgent.onEvent(mContext, "294-alertshenmirenwu");
                        MobclickAgent.onEvent(mContext, "296-newresou1");
                        MobclickAgent.onEvent(mContext, "296-newresou2");
                        if (bean.getFinishedTime() == 0) {
                            new DialogNewPeopleGuide(mContext, R.mipmap.newpeople_task_guide_4, new DialogOnDismissListener() {
                                @Override
                                public void onDismissListener() {
                                    if (bean.getGotoLink() == null) {
                                        ActivityUtils.startToMainActivity(mContext, 0, 0);
                                    } else {
                                        String[] url = bean.getGotoLink().split("\\|");
                                        if (url.length > 2) {
                                            ActivityUtils.startToAssignActivity(mContext, url[0], Integer.valueOf(url[1]), Integer.valueOf(url[2]));
                                        } else if (url.length > 1) {
                                            ActivityUtils.startToAssignActivity(mContext, url[0], Integer.valueOf(url[1]));
                                        } else {
                                            ActivityUtils.startToAssignActivity(mContext, url[0], -1);
                                        }
                                        if(bean.getDisplayName().equals("热搜")){
                                            EventBus.getDefault().post(new ChaiBaoxiangEvent(true));
                                        }
                                    }
                                }
                            }).show();
                        } else if (bean.getAwardsTime() <= bean.getFinishedTime()) {
                            if (null == mSubmitTaskProtocol) {
                                mSubmitTaskProtocol = new PutNewTaskProtocol(mContext, Constants.FIRST_RESOU, new BaseModel.OnResultListener<DoneTaskResBean>() {
                                    @Override
                                    public void onResultListener(DoneTaskResBean response) {
                                        MobclickAgent.onEvent(mContext, "294-finishresou");
                                        bean.setIsFinished(true);

                                        boolean isDone = true;
                                        for (NewPeopleTaskBean bean : mBeans) {
                                            if (!bean.isIsFinished()) {
                                                isDone = false;
                                            }
                                        }
                                        if(isDone){
                                            MobclickAgent.onEvent(mContext, "294-finishnewpeopletask");
                                        }

                                        CustomToast.showToast(mContext, response.getGoldAmount() + "", response.getDescription());
                                        AnimUtil.animBig(view.itemView);
                                        NewPeopleTaskListAdapter.this.notifyDataSetChanged();
                                        mSubmitTaskProtocol = null;
                                    }

                                    @Override
                                    public void onFailureListener(int code, String error) {
                                        ToastUtils.showShortToast(mContext, error);
                                        mSubmitTaskProtocol = null;
                                    }
                                });
                                mSubmitTaskProtocol.postRequest();
                            }
                        } else {
                            ActivityUtils.startToMainActivity(mContext, 0, 10000000);
                        }
                    } else if (Constants.NOVICE_ANSWER.equals(bean.getName())) {
                        MobclickAgent.onEvent(mContext, "294-alertdati");
                        String url = UserManager.getInst().getQukandianBean().getPubwebUrl() + "/NoviceAnswer/";
                        ActivityUtils.startToWebviewAct(mContext,url,"新手答题");
                    } else if (bean.getKindType() == 4) {//幸运任务
                        MobclickAgent.onEvent(mContext, "294-alerxinyunrenwu");
                        MobclickAgent.onEvent(mContext, "296-newxingyunrenwu1");
                        MobclickAgent.onEvent(mContext, "296-newxingyunrewnu2");
                        new DialogNewPeopleGuide(mContext, R.mipmap.newpeople_task_guide_3, new DialogOnDismissListener() {
                            @Override
                            public void onDismissListener() {
                                ActivityUtils.startToMainActivity(mContext,2,0);
                            }
                        }).show();
                    }
                }
            });
        }
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTV;
        TextView mTaskStateTV;
        TextView mTvDescription;

        public TaskViewHolder(View itemView) {
            super(itemView);
            mTitleTV = itemView.findViewById(R.id.titleTV);
            mTaskStateTV = itemView.findViewById(R.id.mTaskStateTV);
            mTvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
