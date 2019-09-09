package com.zhangku.qukandian.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zhangku.qukandian.bean.GoldBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2016/4/26.
 * 带添加头部和尾部功能的recyclerView基类
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private View mHeaderView;
    private View mFooterView;
    public List<T> mBeans = new ArrayList<T>();
    private int mHeaderCount;
    private int mFooterCount;
    public Context mContext;
    private OnItemClick onItemClick;

    public BaseRecyclerViewAdapter(Context context, List<T> beans) {
        mContext = context;
        if (beans != null) {
            mBeans = beans;
        }
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
    }

    protected static final int VIEW_TYPE_HEADER = 0;
    protected static final int VIEW_TYPE_FOOTER = 1;
    protected static final int VIEW_TYPE_NORMAL = 2;
    protected static final int TYPE_NONE = 5;
    protected static final int TYPE_ONE = 6;
    protected static final int TYPE_THREE = 7;
    protected static final int TYPE_READ_RECORD = 8;
    protected static final int TYPE_EMPTY = 14;
    // 原生广告
    protected static final int TYPE_AD_VIEW = 9;
    // 自主广告
    protected static final int TYPE_AD_VIEW_SELF = 10;
    protected static final int TYPE_AD_VIEW_NATIV = 11;
    protected static final int TYPE_AD_VIEW_MULTI = 12;
    protected static final int TYPE_AD_VIEW_LINK = 13;

    @Override
    public int getItemViewType(int position) {
        mHeaderCount = null == mHeaderView ? 0 : 1;
        mFooterCount = null == mFooterView ? 0 : 1;
        if (position < mHeaderCount) {
            return VIEW_TYPE_HEADER;
        } else if (position >= (mHeaderCount + mBeans.size())) {
            return VIEW_TYPE_FOOTER;
        } else {
            return getTypeView(position);
        }

    }

    protected int getTypeView(int postion) {
        return VIEW_TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_HEADER) {
            viewHolder = new HearderFooterHolder(mHeaderView);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            viewHolder = new HearderFooterHolder(mFooterView);
        } else {
            viewHolder = getViewHolder(parent, viewType);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == VIEW_TYPE_NORMAL
                || getItemViewType(position) == TYPE_NONE
                || getItemViewType(position) == TYPE_ONE
                || getItemViewType(position) == TYPE_THREE
                || getItemViewType(position) == TYPE_AD_VIEW_SELF
                || getItemViewType(position) == TYPE_AD_VIEW) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null)
                        onItemClick.onItemClick(v, position - mHeaderCount);
                }
            });
            bindView(holder, position, position - mHeaderCount, mBeans.get(position - mHeaderCount));
        }

    }

    @Override
    public int getItemCount() {
        return (null == mHeaderView ? 0 : 1) + mBeans.size() + (null == mFooterView ? 0 : 1);
    }

    public class HearderFooterHolder extends RecyclerView.ViewHolder {
        public HearderFooterHolder(View itemView) {
            super(itemView);
        }
    }

    protected abstract RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType);

    protected abstract void bindView(RecyclerView.ViewHolder holder, int viewPosition, int dataIndex, T bean);

    public int getHeaderCount() {
        return null == mHeaderView ? 0 : 1;
    }

    public int getFooterCount() {
        return null == mFooterView ? 0 : 1;
    }

    public List<T> getList() {
        return mBeans;
    }


    public void addList(List<T> list) {
        mBeans.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void onItemClick(View view, int position);
    }

    public void setList(List<T> list) {
        mBeans = list;
        notifyDataSetChanged();
    }

//    public void setList(List<T> list){
//        mBeans.addAll(list);
//        notifyDataSetChanged();
//    }
}
