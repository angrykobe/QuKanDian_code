package com.zhangku.qukandian.fragment.me;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zhangku.qukandian.BaseNew.BaseRecycleFra;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.GetGoldForDownAppAdapter;
import com.zhangku.qukandian.adapter.GetGoldForHadDownAdapter;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.DownAppBean;
import com.zhangku.qukandian.bean.HadDownAppBean;
import com.zhangku.qukandian.protocol.GetDownAppListProtocol;
import com.zhangku.qukandian.protocol.GetHadDownAppListProtocol;
import com.zhangku.qukandian.utils.RecyclerViewDivider;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 你不注释一下？
 */
public class GetGoldForHadDoneFragment extends BaseRecycleFra<HadDownAppBean>{

    @Override
    public BaseRecyclerViewAdapter setAdapter(List mList) {
        return mAdapter = new GetGoldForHadDownAdapter(mContext, mList);
    }

    @Override
    public void loadData(int currentPage) {
        new GetHadDownAppListProtocol(mContext,currentPage, this).postRequest();
    }

    @Override
    public void initUI(View view) {
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), 1,
                LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getContext(), R.color.black_a9)));
//        loadData(currentPage);
    }

    @Override
    public void onResultSuccess(List<HadDownAppBean> response) {

    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        loadData(currentPage);
//    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        loadData(currentPage);
    }
}
