package com.zhangku.qukandian.base;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.utils.RecyclerViewDivider;
import com.zhangku.qukandian.widght.FootView;

/**
 * Created by yuzuoning on 2017/4/17.
 */

public abstract class BaseLoadMoreFragment extends BaseFragment {
    protected RecyclerView mRecyclerView;
    protected GridLayoutManager mLinearLayoutManager;
    protected FootView mFootView;
    protected BaseRecyclerViewAdapter mBaseAdapter;

    @Override
    public void loadData(Context context) {
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_base_load_more_layout;
    }

    @Override
    protected void initViews(View convertView) {
        addLoadingView(convertView, R.id.base_refresh_loading_layout);
        mRecyclerView = (RecyclerView) convertView.findViewById(R.id.base_refresh_recyclerview);

        mLinearLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), 2,
                LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getContext(), R.color.grey_f2)));
        mFootView = new FootView(getContext(), mRecyclerView);
        mBaseAdapter = getAdapter();
        mBaseAdapter.setFooterView(mFootView.getView());
        mRecyclerView.setAdapter(mBaseAdapter);

        mLinearLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mLinearLayoutManager.getSpanCount();
            }
        });
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    protected RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dy > 0 && recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) {
                if (mFootView != null) {
                    mFootView.show();
                }
                loadMoreData(recyclerView, dx, dy);
            }

        }
    };

    protected abstract BaseRecyclerViewAdapter getAdapter();

    protected abstract void loadMoreData(RecyclerView recyclerView, int dx, int dy);

    @Override
    public String setPagerName() {
        return null;
    }

    @Override
    protected void onLoadNotNetwordClick() {
        super.onLoadNotNetwordClick();
    }
}
