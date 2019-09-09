package com.zhangku.qukandian.fragment.me;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zhangku.qukandian.BaseNew.BaseRecycleFra;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.me.GetGoldForDownActivity;
import com.zhangku.qukandian.adapter.GetGoldForDownAppAdapter;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.DownAppBean;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.protocol.GetDownAppListProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.RecyclerViewDivider;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 赚赏金 未完成的任务
 */
public class GetGoldForDowningFragment extends BaseRecycleFra<DownAppBean>{

    private GetDownAppListProtocol mGetDownAppListProtocol;

    @Override
    public BaseRecyclerViewAdapter setAdapter(List mList) {
        return mAdapter = new GetGoldForDownAppAdapter(mContext, mList);
    }

    @Override
    public void loadData(int currentPage) {
        if(mGetDownAppListProtocol==null){
            mGetDownAppListProtocol = new GetDownAppListProtocol(mContext, this);
            mGetDownAppListProtocol.postRequest();
        }
    }

    @Override
    public void initUI(View view) {
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), 1,
                LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getContext(), R.color.line)));
        disableAll();

        mAdapter.setOnItemClick(new BaseRecyclerViewAdapter.OnItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                DownAppBean o = (DownAppBean) mAdapter.getList().get(position);
                ActivityUtils.startToDownAppDetailAct(getContext(),o);
            }
        });
    }

    @Override
    public void onFailureListener(int code, String error) {
        mGetDownAppListProtocol = null;
        super.onFailureListener(code, error);
    }

    @Override
    public void onResultSuccess(List<DownAppBean> response) {
        mGetDownAppListProtocol = null;
        DBTools.changeDownAppBean(response);
        Iterator<DownAppBean> iter = mAdapter.getList().iterator();
        while (iter.hasNext()){
            DownAppBean next = iter.next();
            //手机已经安装应用，且任务未开始 直接删除掉任务
            if(CommonHelper.isAppInstalled(next.getAppPackage()) && next.getStage()==-1){
                iter.remove();
            }
        }
        if(mAdapter.getList().size()==0){
            showNoData();
            return;
        }
        //排序
        Collections.sort(mAdapter.getList(), new Comparator<DownAppBean>() {
            @Override
            public int compare(DownAppBean o1, DownAppBean o2) {
                return o2.getStage() - o1.getStage();
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(currentPage);
    }
}
