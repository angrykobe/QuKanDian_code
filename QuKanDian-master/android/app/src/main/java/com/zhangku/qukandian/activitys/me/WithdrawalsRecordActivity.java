package com.zhangku.qukandian.activitys.me;

import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.WithdrawalsRecordAdapter;
import com.zhangku.qukandian.base.BaseLoadingActivity;
import com.zhangku.qukandian.bean.WithdrawalsRecordBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetWithdrawalsRecordProtocol;
import com.zhangku.qukandian.utils.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/4/12.
 * 提现记录
 */

public class WithdrawalsRecordActivity extends BaseLoadingActivity {
    private RecyclerView mRecyclerView;
    private WithdrawalsRecordAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ArrayList<WithdrawalsRecordBean> mDatas = new ArrayList<>();
    private int mPager = 1;
    private GetWithdrawalsRecordProtocol mGetWithdrawalsRecordProtocol;

    @Override
    protected void onResume() {
        super.onResume();
        Map<String,String> map = new ArrayMap<>();
        map.put("to","提现记录");
        MobclickAgent.onEvent(this, "AllPv",map);
    }

    @Override
    public int getLoadingViewParentId() {
        return R.id.activity_withdrawals_record_layout_loading;
    }

    @Override
    protected void initActionBarData() {
        setTitle("提现记录");
    }

    @Override
    protected void loadData() {
        super.loadData();
        new GetWithdrawalsRecordProtocol(this,mPager, new BaseModel.OnResultListener<ArrayList<WithdrawalsRecordBean>>() {
            @Override
            public void onResultListener(ArrayList<WithdrawalsRecordBean> response) {
                if (response.size() > 0 && mDatas != null) {
                    mDatas.clear();
                    mDatas.addAll(response);
                    mAdapter.notifyDataSetChanged();
                    hideLoadingLayout();
                    if (mDatas.size() >= Constants.SIZE) {
                        mRecyclerView.addOnScrollListener(mOnScrollListener);
                    }
                } else {
                    showEmptySearch("啊，土豪，您还没有提现过");
                }

            }

            @Override
            public void onFailureListener(int code,String error) {
                showLoadFail();
            }
        }).postRequest();

    }

    @Override
    public void onLoadingFail() {
        super.onLoadingFail();
        showLoading();
        loadData();
    }

    private void loadMoreData() {

//        if(mGetWithdrawalsRecordProtocol == null){
//
//        }
//        mGetWithdrawalsRecordProtocol = new GetWithdrawalsRecordProtocol(this, new BaseModel.OnResultListener<ArrayList<WithdrawalsRecordBean>>() {
//            @Override
//            public void onResultListener(ArrayList<WithdrawalsRecordBean> response) {
//                int start = mDatas.size();
//                if (mDatas.size() > 0) {
//                    mPager++;
//                    mDatas.addAll(response);
//                    mAdapter.notifyItemRangeChanged(start, response.size());
//                } else {
//                    ToastUtils.showLongToast(WithdrawalsRecordActivity.this, "没有更多数据");
//                    mRecyclerView.removeOnScrollListener(mOnScrollListener);
//                }
//                mGetWithdrawalsRecordProtocol = null;
//                hideLoadingLayout();
//            }
//
//            @Override
//            public void onFailureListener() {
//                mGetWithdrawalsRecordProtocol = null;
//            }
//        });
//        mGetWithdrawalsRecordProtocol.getWithdrawalsRecordDatas(mPager);
    }

    @Override
    protected void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_withdrawals_record_layout_recyclerview);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, 1, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(this, R.color.grey_f2)));
        mAdapter = new WithdrawalsRecordAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_withdrawals_record_layout;
    }

    @Override
    public String setPagerName() {
        return "提现记录";
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0 && mLinearLayoutManager.findLastVisibleItemPosition() + 2 > mAdapter.getItemCount()) {
                loadMoreData();
            }
        }
    };

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mDatas.clear();
        mDatas = null;
        mAdapter = null;
        mRecyclerView.setAdapter(null);
        mRecyclerView = null;
        mLinearLayoutManager = null;
    }
}
