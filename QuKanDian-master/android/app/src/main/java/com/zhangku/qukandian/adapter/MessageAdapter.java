package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.LocalMessageBean;
import com.zhangku.qukandian.bean.MessageBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.ClickTipObserver;
import com.zhangku.qukandian.protocol.PutMessageTipProtocol;
import com.zhangku.qukandian.protocol.PutNewMessageTipProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.URLImageGetter;

import java.util.List;

/**
 * Created by yuzuoning on 2017/4/18.
 * 消息适配器
 */

public class MessageAdapter extends BaseRecyclerViewAdapter<MessageBean> {
    private OnClickRefreshListener mOnClickRefreshListener;
    private boolean mIsRefresh = false;
    private PutNewMessageTipProtocol mPutMessageTipProtocol;

    public MessageAdapter(Context context, List<MessageBean> beans, OnClickRefreshListener onClickRefreshListener) {
        super(context, beans);
        mOnClickRefreshListener = onClickRefreshListener;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new MessageHolder(LayoutInflater.from(mContext).inflate(R.layout.item_message_view, parent, false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, final int dataIndex, final MessageBean bean) {
        MessageHolder view = (MessageHolder) holder;
//        if (mIsRefresh) {
//            LocalMessageBean localMessageBean = new LocalMessageBean();
//            localMessageBean.setNewId(bean.getId());
//            localMessageBean.setContent(bean.getContent());
//            localMessageBean.setIsReading(true);
////            localMessageBean.setTpltName(bean.getTpltName());
//            localMessageBean.setTitle(bean.getTitle());
//            localMessageBean.setCreationTime(bean.getCreationTime());
//            localMessageBean.setMessageType(bean.getType());
//            localMessageBean.setUserId(UserManager.getInst().getUserBeam().getId());
//            localMessageBean.setLinkTo(bean.getLinkTo());
//            DBTools.saveMessage(localMessageBean);
//            view.mIvPostion.setVisibility(View.GONE);
//        } else {
//            if (DBTools.getMessageReadStstus(bean)) {
//                view.mIvPostion.setVisibility(View.GONE);
//            } else {
//                view.mIvPostion.setVisibility(View.VISIBLE);
//            }
//        }
        view.mIvPostion.setVisibility(bean.isReading()?View.GONE:View.VISIBLE);
        view.mTvMessage1.setText(Html.fromHtml(bean.getContent(), new URLImageGetter(mContext, view.mTvMessage1, 0), null));
        view.mTvTime.setText(CommonHelper.utcToString(bean.getCreationTime(), "yyyy-MM-dd HH:mm:ss"));
        view.mTvTitle.setText(bean.getTitle());
        if ("意见反馈回复".equals(bean.getTitle())) {
            view.mBottomLayout.setVisibility(View.GONE);
        } else {
            view.mBottomLayout.setVisibility(View.VISIBLE);
        }
        view.mSkipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == mPutMessageTipProtocol) {
                    mPutMessageTipProtocol = new PutNewMessageTipProtocol(mContext, bean.getType(), new BaseModel.OnResultListener<Object>() {
                        @Override
                        public void onResultListener(Object response) {
                            if (null != mOnClickRefreshListener) {
                                mOnClickRefreshListener.onCliclRefreshListener(dataIndex);
                            }

                            if(TextUtils.isEmpty(bean.getActionUrl())){
                                if (!TextUtils.isEmpty(bean.getLinkTo())) {
                                    ActivityUtils.startToWebviewAct(mContext, bean.getLinkTo());
                                } else {
                                    ActivityUtils.startToIncomeDetailsActivity(mContext);
                                }
                            }else{
                                ActivityUtils.startToAnyWhereJsonActivity(mContext, bean.getActionUrl());
                            }
                            mPutMessageTipProtocol = null;
                        }

                        @Override
                        public void onFailureListener(int code, String error) {
                            mPutMessageTipProtocol = null;
                        }
                    });
                    mPutMessageTipProtocol.postRequest();
                }

            }
        });
    }

    public void setRefresh(boolean b) {
        mIsRefresh = b;
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        ImageView mIvPostion;
        TextView mTvTitle;
        TextView mTvTime;
        TextView mTvMessage1;
        LinearLayout mSkipLayout;
        LinearLayout mBottomLayout;

        public MessageHolder(View itemView) {
            super(itemView);
            mBottomLayout = (LinearLayout) itemView.findViewById(R.id.item_message_view_message1_bottom_layout);
            mIvPostion = (ImageView) itemView.findViewById(R.id.item_message_view_postion);
            mTvTitle = (TextView) itemView.findViewById(R.id.item_message_view_title);
            mTvTime = (TextView) itemView.findViewById(R.id.item_message_view_time);
            mTvMessage1 = (TextView) itemView.findViewById(R.id.item_message_view_message1);
            mSkipLayout = (LinearLayout) itemView.findViewById(R.id.item_message_view_skip);

        }
    }

    public interface OnClickRefreshListener {
        void onCliclRefreshListener(int pisiton);
    }
}
