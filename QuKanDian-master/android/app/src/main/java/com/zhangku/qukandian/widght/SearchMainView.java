package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.SearchHistoryAdapter;
import com.zhangku.qukandian.bean.SearchHistoryBean;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.utils.LayoutInflaterUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/3/30.
 */

public class SearchMainView extends LinearLayout {
    private RecyclerView mRecyclerView;
    private SearchHistoryAdapter mAdapter;
    private LinearLayout mLayoutEmptyView;
    private GridLayoutManager mGridLayoutManager;
    private List<SearchHistoryBean> mSearchHistoryBeen = new ArrayList<>();
    private View mClearBtn;

    public SearchMainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLayoutEmptyView = (LinearLayout) findViewById(R.id.search_main_history_empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.search_main_history_record);
        mClearBtn = LayoutInflaterUtils.inflateView(getContext(), R.layout.layout_search_clear_btn);

        mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mGridLayoutManager.getSpanCount();
            }
        });

        updateData();
        clearBtnShowState(mSearchHistoryBeen.size() > 0);
        mAdapter = new SearchHistoryAdapter(getContext(), mSearchHistoryBeen);
        mAdapter.setFooterView(mClearBtn);
        mRecyclerView.setAdapter(mAdapter);


        mClearBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DBTools.clearSearchHistory();
                notifyHistory();
            }
        });

    }

    private void clearBtnShowState(boolean show) {
        if (show) {
            mClearBtn.setVisibility(View.VISIBLE);
        } else {
            mClearBtn.setVisibility(View.GONE);
        }
    }

    public void setIOnclickHistoryItemListener(SearchHistoryAdapter.IOnclickHistoryItemListener iOnclickHistoryItemListener) {
        mAdapter.setIOnclickHistoryItemListener(iOnclickHistoryItemListener);
    }

    public void notifyHistory() {
        updateData();
        mAdapter.notifyDataSetChanged();
        clearBtnShowState(mSearchHistoryBeen.size() > 0);
    }

    private void updateData() {
        if(DBTools.getSearchHistory().size() > 0){
            mLayoutEmptyView.setVisibility(View.GONE);
        }else {
            mLayoutEmptyView.setVisibility(View.VISIBLE);
        }
        mSearchHistoryBeen.clear();
        mSearchHistoryBeen.addAll(DBTools.getSearchHistory());
    }
}
