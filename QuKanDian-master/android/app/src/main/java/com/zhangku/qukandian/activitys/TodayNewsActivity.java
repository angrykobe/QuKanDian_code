package com.zhangku.qukandian.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.TodayNewsAdapter;
import com.zhangku.qukandian.base.BaseLoadingActivity;
import com.zhangku.qukandian.bean.TodayNewBean;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetPushInfoProtocol;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/8/24.
 */

public class TodayNewsActivity extends BaseLoadingActivity {
    private ImageView mIvBackBtn;
    private RecyclerView mRecyclerView;
    private TodayNewsAdapter mAdapter;
    private ArrayList<TodayNewBean> mTodayNewBeen = new ArrayList<>();
    private GetPushInfoProtocol mGetPushInfoProtocol;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        mIvBackBtn = (ImageView) findViewById(R.id.today_news_back_btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_today_news_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TodayNewsAdapter(this, mTodayNewBeen);
        mRecyclerView.setAdapter(mAdapter);

        mIvBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (null == mGetPushInfoProtocol) {
            mGetPushInfoProtocol = new GetPushInfoProtocol(this, new BaseModel.OnResultListener<ArrayList<TodayNewBean>>() {
                @Override
                public void onResultListener(ArrayList<TodayNewBean> response) {
                    if(response.size() > 0){
                        mTodayNewBeen.clear();
                        mTodayNewBeen.addAll(response);
                        mAdapter.notifyDataSetChanged();
                        hideLoadingLayout();
                    }else {
                        showNodata("每日实时呈现有趣看视界");
                    }
                    mGetPushInfoProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetPushInfoProtocol = null;
                }
            });
            mGetPushInfoProtocol.postRequest();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_today_news_layout;
    }

    @Override
    public String setPagerName() {
        return null;
    }

    @Override
    public int getLoadingViewParentId() {
        return R.id.activity_today_news_layout_loading;
    }
}
