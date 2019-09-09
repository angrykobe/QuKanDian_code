package com.zhangku.qukandian.fragment.me;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.IncomeRankAdapter;
import com.zhangku.qukandian.base.BaseFragment;
import com.zhangku.qukandian.bean.RankBean;
import com.zhangku.qukandian.utils.LayoutInflaterUtils;
import com.zhangku.qukandian.widght.LayoutRankHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/4/12.
 */

public abstract class BaseRankFrgament extends BaseFragment {
    protected IncomeRankAdapter mAdapter;
    protected List<RankBean.TopListBean> mDatas = new ArrayList<>();
    protected LayoutRankHeader mLayoutRankHeader;
    private RecyclerView mRecyclerView;
    @Override
    public void loadData(Context context) {
        loadCurrentData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_week_rank_layout;
    }

    @Override
    protected void initViews(View convertView) {
        addLoadingView(convertView,R.id.frament_week_rank_layout_loading);
        mLayoutRankHeader = (LayoutRankHeader) LayoutInflaterUtils.inflateView(getContext(),R.layout.layout_rank_top_view);
        mRecyclerView = (RecyclerView) convertView.findViewById(R.id.frament_week_rank_layout_recylerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new IncomeRankAdapter(getContext(),mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setHeaderView(mLayoutRankHeader);
    }

    protected abstract void loadCurrentData();

    @Override
    public String setPagerName() {
        return "总排行榜";
    }
}
