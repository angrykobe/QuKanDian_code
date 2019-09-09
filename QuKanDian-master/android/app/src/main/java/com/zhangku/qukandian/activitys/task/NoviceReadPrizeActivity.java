package com.zhangku.qukandian.activitys.task;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.NoviceReadPrizeAdapter;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.NoviceReadon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNoviceReadProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.RecyclerViewDivider;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/4/6.
 * 新手阅读奖励页面
 */

public class NoviceReadPrizeActivity extends BaseTitleActivity implements View.OnClickListener {
    private ImageView mIvBack;
    private RecyclerView mRecyclerView;
    private TextView mTvToReadBtn;
    private NoviceReadPrizeAdapter mAdapter;
    private ArrayList<NoviceReadon> mDatas = new ArrayList<>();
    private GetNoviceReadProtocol mGetNoviceReadProtocol;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void loadData() {
        super.loadData();
        if(null == mGetNoviceReadProtocol){
            mGetNoviceReadProtocol = new GetNoviceReadProtocol(this, new BaseModel.OnResultListener<ArrayList<NoviceReadon>>() {
                @Override
                public void onResultListener(ArrayList<NoviceReadon> response) {
                    if (null != response) {
                        if (response.size() > 0) {
                            mDatas.clear();
                            int tempInt = 0;
                            ArrayList<NoviceReadon> tempArray = new ArrayList<NoviceReadon>();
                            for (int i = 0; i < response.size(); i++) {
                                NoviceReadon bean = response.get(i);
                                if (bean.getUserId() > 0) {
                                    tempInt = i;
                                }
                                tempArray.add(bean);
                            }

                            for (int i = 0; i < tempInt; i++) {
                                tempArray.get(i).setOverdue(true);
                            }
                            mDatas.addAll(tempArray);
                        } else {
                            for (int i = 0; i < 25; i++) {
                                NoviceReadon n = new NoviceReadon();
                                n.setId("00000000-0000-0000-0000-000000000000");
                                n.setCreationTime("0001-01-01T00:00:00");
                                n.setIsReading(false);
                                n.setOverdue(false);
                                mDatas.add(n);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        mGetNoviceReadProtocol = null;
                    }
                }

                @Override
                public void onFailureListener(int code,String error) {
                    mGetNoviceReadProtocol = null;
                }
            });
            mGetNoviceReadProtocol.postRequest();
        }
    }

    @Override
    protected void initViews() {
        mIvBack = (ImageView) findViewById(R.id.activity_task_layout_back);
        mTvToReadBtn = (TextView) findViewById(R.id.activity_novice_read_prize_layout_to_read_btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_novice_read_prize_layout_recyclerview);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, 5, LinearLayoutManager.HORIZONTAL, R.mipmap.novice_fengexian));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        mAdapter = new NoviceReadPrizeAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mIvBack.setOnClickListener(this);
        mTvToReadBtn.setOnClickListener(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_novice_read_prize_layout;
    }

    @Override
    public String setPagerName() {
        return "新手阅读奖励";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_task_layout_back:
                finish();
                break;
            case R.id.activity_novice_read_prize_layout_to_read_btn:
                ActivityUtils.startToMainActivity(NoviceReadPrizeActivity.this, Constants.TAB_INFORMATION, 0);
                break;
        }
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mIvBack = null;
        mTvToReadBtn = null;
        mAdapter = null;
        mRecyclerView.setAdapter(null);
        mRecyclerView = null;
        mDatas.clear();
        mDatas = null;
    }
}
