package com.zhangku.qukandian.activitys;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.MySharedAdapter;
import com.zhangku.qukandian.base.BaseLoadingActivity;
import com.zhangku.qukandian.bean.SharedBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetSharedDataProtocol;
import com.zhangku.qukandian.utils.RecyclerViewDivider;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.FootView;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/11/15.
 */

public class MySharedActivity extends BaseLoadingActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private FootView mFootView;
    private LinearLayoutManager mLinearLayoutManager;
    private MySharedAdapter mAdapter;

    private GetSharedDataProtocol mGetSharedDataProtocol;
    private ArrayList<SharedBean> mDatas = new ArrayList<>();
    private int mPage = 1;

    @Override
    public int getLoadingViewParentId() {
        return R.id.activity_my_shared_loading_layout;
    }

    @Override
    protected void initActionBarData() {
        setTitle("分享最新奖励");
    }

    @Override
    protected void initViews() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_my_shared_swipe);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_my_shared_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this,1,
                LinearLayoutManager.HORIZONTAL,12, ContextCompat.getColor(this,R.color.grey_e5)));
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mFootView = new FootView(this, mRecyclerView);
        mAdapter = new MySharedAdapter(this,mDatas);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setFooterView(mFootView.getView());
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    protected void loadData() {
        firstLoad();
    }

    private void firstLoad() {
        if (null == mGetSharedDataProtocol) {
            mGetSharedDataProtocol = new GetSharedDataProtocol(this, mPage,
                    Constants.SIZE, new BaseModel.OnResultListener<ArrayList<SharedBean>>() {
                @Override
                public void onResultListener(ArrayList<SharedBean> response) {
                    if (null != mSwipeRefreshLayout) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    if (response.size() > 0) {
                        mPage++;
                        mDatas.clear();
                        mDatas.addAll(response);
                        mAdapter.notifyDataSetChanged();
                        if(response.size() >= Constants.SIZE){
                            mRecyclerView.addOnScrollListener(mOnScrollListener);
                        }
                        hideLoadingLayout();
                    } else {
                        showEmptyCollect("暂无数据");
                    }
                    mGetSharedDataProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    if (null != mSwipeRefreshLayout) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    hideLoadingLayout();
                    mGetSharedDataProtocol = null;
                }
            });
            mGetSharedDataProtocol.postRequest();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_my_shared_layout;
    }

    @Override
    public String setPagerName() {
        return null;
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        firstLoad();
    }
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0 && mLinearLayoutManager.findLastCompletelyVisibleItemPosition() + 2 >= mAdapter.getItemCount()) {
                if (mFootView != null) {
                    mFootView.show();
                }
                loadMoreData();
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }
    };

    private void loadMoreData() {
        if (null == mGetSharedDataProtocol) {
            mGetSharedDataProtocol = new GetSharedDataProtocol(this, mPage,
                    Constants.SIZE, new BaseModel.OnResultListener<ArrayList<SharedBean>>() {
                @Override
                public void onResultListener(ArrayList<SharedBean> response) {
                    if(null != mFootView){
                        mFootView.hide();
                    }
                    if (response.size() > 0) {
                        mPage++;
                        int start = mDatas.size();
                        mDatas.addAll(response);
                        mAdapter.notifyItemRangeInserted(start,response.size());
                    } else {
                        ToastUtils.showShortToast(MySharedActivity.this,"没有更多数据");
                        mRecyclerView.removeOnScrollListener(mOnScrollListener);
                    }
                    hideLoadingLayout();
                    mGetSharedDataProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    ToastUtils.showShortToast(MySharedActivity.this,"没有更多数据");
                    if(null != mFootView){
                        mFootView.hide();
                    }
                    hideLoadingLayout();
                    mGetSharedDataProtocol = null;
                }
            });
            mGetSharedDataProtocol.postRequest();
        }
    }
}
