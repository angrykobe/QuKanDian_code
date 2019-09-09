package com.zhangku.qukandian.activitys;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.NewPeopleTaskListAdapter;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewTaskForNewPeoplePro;

import java.util.ArrayList;
import java.util.List;

/**
 * 新手福利页面
 * 2019-6-6 10:27:01
 * ljs
 * （DialogNewPeopleTask转为activity）
 */
public class NewPeopleTaskActivity extends BaseAct {
    private RecyclerView mRecyclerView;
    private NewPeopleTaskListAdapter mAdapter;
    private List<NewPeopleTaskBean> mList = new ArrayList<>();
    private GetNewTaskForNewPeoplePro mGetNewTaskForNewPeoplePro;
    private LinearLayoutManager mLayout;
    protected TextView titleTV;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_new_people_task;
    }

    @Override
    protected String setTitle() {
        return "新手福利";
    }

    @Override
    protected void initViews() {
        titleTV = findViewById(R.id.baseTitleTV);
        titleTV.setText("新手福利");
        findViewById(R.id.baseBackIV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //头部
        View headView = LayoutInflater.from(NewPeopleTaskActivity.this).inflate(R.layout.head_new_people_task_top, mRecyclerView, false);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mLayout = new LinearLayoutManager(NewPeopleTaskActivity.this);
        mRecyclerView.setLayoutManager(mLayout);
        mAdapter = new NewPeopleTaskListAdapter(NewPeopleTaskActivity.this, mList);
        mRecyclerView.setAdapter(mAdapter);
        View footView = LayoutInflater.from(NewPeopleTaskActivity.this).inflate(R.layout.foot_new_people_task, mRecyclerView, false);
        mAdapter.setHeaderView(headView);
        mAdapter.setFooterView(footView);
    }

    @Override
    protected void loadData() {
        getData();
    }

    private void getData() {
        if (mGetNewTaskForNewPeoplePro == null) {
            mGetNewTaskForNewPeoplePro = new GetNewTaskForNewPeoplePro(NewPeopleTaskActivity.this, new BaseModel.OnResultListener<List<NewPeopleTaskBean>>() {
                @Override
                public void onResultListener(final List<NewPeopleTaskBean> response) {
                    if (mList != null && mList.size() != 0) {
                        List<NewPeopleTaskBean> donelist = new ArrayList<>();//要播放动画的任务
                        List<NewPeopleTaskBean> list = new ArrayList<>();//
                        for (NewPeopleTaskBean nowBean : response) {//最新新手任务
                            for (NewPeopleTaskBean lastBean : mList) {//缓存新手任务
                                if (lastBean.getDisplayName().equals(nowBean.getDisplayName())) {
                                    if (lastBean.isIsFinished() != nowBean.isIsFinished()) {//缓存跟服务端的数据不一样，默认用户完成该任务（需要播放动画）
                                        nowBean.setChange(true);
                                        donelist.add(nowBean);
                                    } else {
                                        nowBean.setChange(false);
                                        list.add(nowBean);
                                    }
                                }
                            }
                        }
                        boolean isDone = true;
                        for (NewPeopleTaskBean bean : response) {
                            if (!bean.isIsFinished()) {
                                isDone = false;
                            }
                        }
                        if(isDone && donelist.size()!=0){
                            MobclickAgent.onEvent(NewPeopleTaskActivity.this, "294-finishnewpeopletask");
                        }

                        mList.clear();
                        mList.addAll(donelist);
                        mList.addAll(list);
                        if (mLayout != null) mLayout.scrollToPosition(0);
                        mAdapter.setList(mList);
                    } else {
                        mAdapter.setList(response);
                    }
                    mGetNewTaskForNewPeoplePro = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetNewTaskForNewPeoplePro = null;
                }
            });
            mGetNewTaskForNewPeoplePro.postRequest();
        }
    }

    @Override
    protected void myOnClick(View v) {
        switch (v.getId()){

        }
    }
}
