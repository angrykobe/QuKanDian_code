package com.zhangku.qukandian.base;

import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.dialog.DialogLoadRemind;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.RecyclerViewDivider;
import com.zhangku.qukandian.widght.FootView;
import com.zhangku.qukandian.widght.SuperSwipeRefreshLayout;

import java.util.Map;

/**
 * Created by yuzuoning on 2017/3/26.
 * 带刷新的recyclerview的fragment
 */

public abstract class BaseRefreshRecyclerViewFragment extends BaseFragment {
    protected SuperSwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLinearLayoutManager;
    protected FootView mFootView;
    protected BaseRecyclerViewAdapter mBaseAdapter;
    protected DialogLoadRemind mDialogLoadRemind;
    protected LinearLayout mLayoutHeadRemind;
    protected TextView mTvRemindText;
    protected LinearLayout mLinearLayout;
    protected TextView mTvHeaderText;
    protected ImageView mIvHeaderImg;

    @Override
    protected int getLayoutRes() {
        return R.layout.base_refresh_recyclerview_fragment;
    }

    @Override
    protected void initViews(View convertView) {
        addLoadingView(convertView, R.id.base_refresh_loading_layout);
        mLinearLayout = convertView.findViewById(R.id.base_refhresh_linearlayout);
        mDialogLoadRemind = (DialogLoadRemind) convertView.findViewById(R.id.dialog_load_remind_layout);
        mSwipeRefreshLayout = (SuperSwipeRefreshLayout) convertView.findViewById(R.id.base_swip_refresh_layout);
        mRecyclerView = (RecyclerView) convertView.findViewById(R.id.base_refresh_recyclerview);
        mLayoutHeadRemind = (LinearLayout) convertView.findViewById(R.id.item_information_header);
        mTvRemindText = (TextView) mLayoutHeadRemind.findViewById(R.id.item_read_record_view_text);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), 1, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getContext(), R.color.grey_f2)));
        mFootView = new FootView(getContext(), mRecyclerView);
        mBaseAdapter = getAdapter();
        mBaseAdapter.setFooterView(mFootView.getView());
        mRecyclerView.setAdapter(mBaseAdapter);

        mSwipeRefreshLayout.setHeaderView(createHeaderView());
        mSwipeRefreshLayout.setHeaderViewBackgroundColor(getResources().getColor(R.color.grey_f2));
        mSwipeRefreshLayout.setTargetScrollWithLayout(true);
//        mSwipeRefreshLayout.setFooterView(mFootView.getView());
        mSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                mTvHeaderText.setText("正在刷新");
                refresh();
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                if(enable){
                    mTvHeaderText.setText("松开刷新");
                }else {
                    mTvHeaderText.setText("下拉刷新");
                }
            }
        });
        mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(mSwipeRefreshLayout.getContext())
                .inflate(R.layout.refresh_header_view, null);
        mIvHeaderImg = headerView.findViewById(R.id.refresh_header_img);
        mTvHeaderText = headerView.findViewById(R.id.refresh_header_text);
        GlideUtils.diaplayLocalGif(getContext(),R.drawable.loading_icon,mIvHeaderImg);
        return headerView;
    }

    protected RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
//            LogUtils.LogE("dy="+dy+"::recyclerView.computeVerticalScrollOffset()="+recyclerView.computeVerticalScrollOffset()+"::recyclerView.computeVerticalScrollExtent()="+recyclerView.computeVerticalScrollExtent()+"::recyclerView.computeVerticalScrollRange()="+recyclerView.computeVerticalScrollRange());
            if (dy > 0 && recyclerView.computeVerticalScrollOffset() + recyclerView.computeVerticalScrollExtent() + 20 >= recyclerView.computeVerticalScrollRange()) {
                if (mFootView != null) {
                    mFootView.show();
                }
                Map<String, String> map = new ArrayMap<>();
                map.put("count", getLoadType());
                MobclickAgent.onEvent(getContext(), "RefreshList", map);
                loadMoreData(recyclerView, dx, dy);
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    };

    protected abstract BaseRecyclerViewAdapter getAdapter();

    protected abstract void refresh();

    protected abstract void loadMoreData(RecyclerView recyclerView, int dx, int dy);
    protected abstract String getLoadType();
    @Override
    public String setPagerName() {
        return null;
    }

    protected void showRemind(boolean show) {
        if (show) {
            mDialogLoadRemind.setVisibility(View.VISIBLE);
        } else {
            mDialogLoadRemind.setVisibility(View.GONE);
        }
    }
}
