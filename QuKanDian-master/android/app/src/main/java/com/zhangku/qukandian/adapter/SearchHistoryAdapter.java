package com.zhangku.qukandian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.SearchHistoryBean;

import java.util.List;

/**
 * Created by yuzuoning on 2017/4/3.
 */

public class SearchHistoryAdapter extends BaseRecyclerViewAdapter<SearchHistoryBean> {
    private IOnclickHistoryItemListener mIOnclickHistoryItemListener;
    public SearchHistoryAdapter(Context context, List<SearchHistoryBean> beans) {
        super(context, beans);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search_history_view,parent,false));
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, final SearchHistoryBean bean) {
        HistoryViewHolder view = (HistoryViewHolder) holder;
        view.mTvKeyword.setText(bean.getKeyword());
        view.mItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mIOnclickHistoryItemListener){
                    mIOnclickHistoryItemListener.onclickHistoryItemListener(bean.getKeyword());
                }
            }
        });
        if(viewPosition == mBeans.size()-1){
            view.mLine.setVisibility(View.GONE);
        }else {
            view.mLine.setVisibility(View.VISIBLE);
        }
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout mItemLayout;
        TextView mTvKeyword;
        View mLine;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            mItemLayout = (RelativeLayout) itemView.findViewById(R.id.item_search_history_view_item);
            mTvKeyword = (TextView) itemView.findViewById(R.id.item_search_history_view_keyword);
            mLine = itemView.findViewById(R.id.item_search_history_view_line);
        }
    }

    public void setIOnclickHistoryItemListener(IOnclickHistoryItemListener iOnclickHistoryItemListener){
        mIOnclickHistoryItemListener = iOnclickHistoryItemListener;
    }

    public interface IOnclickHistoryItemListener{
        void onclickHistoryItemListener(String keyword);
    }
}
