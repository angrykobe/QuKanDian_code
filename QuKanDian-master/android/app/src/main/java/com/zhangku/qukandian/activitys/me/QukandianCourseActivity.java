package com.zhangku.qukandian.activitys.me;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.HelpAdapter;
import com.zhangku.qukandian.adapter.QuestionHeadAdapter;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.HelpBean;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetEcoHelpProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.widght.GrayBgActionBar;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/4/19.
 * 趣看视界教程
 */

public class QukandianCourseActivity extends BaseTitleActivity {
    private GrayBgActionBar mGrayBgActionBar;
    private RecyclerView mRecyclerView;
    private HelpAdapter mAdapter;
    private ArrayList<HelpBean> mDatas = new ArrayList<>();
    private GetEcoHelpProtocol mGetEcoHelpProtocol;
    private RecyclerView topRecy;
    private LinearLayoutManager layout;
    private QuestionHeadAdapter headAdapter;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String,String> map = new ArrayMap<>();
        map.put("to","趣看视界教程");
        MobclickAgent.onEvent(this, "AllPv",map);
    }

    @Override
    protected void loadData() {
        super.loadData();
        if (null == mGetEcoHelpProtocol) {
            mGetEcoHelpProtocol = new GetEcoHelpProtocol(this, new BaseModel.OnResultListener<ArrayList<HelpBean>>() {
                @Override
                public void onResultListener(ArrayList<HelpBean> response) {
                    mDatas.clear();
                    mDatas.addAll(response);
                    mAdapter.notifyDataSetChanged();
                    headAdapter.notifyDataSetChanged();
                    mGetEcoHelpProtocol = null;
                }

                @Override
                public void onFailureListener(int code,String error) {
                    mGetEcoHelpProtocol = null;
                }
            });
            mGetEcoHelpProtocol.postRequest();
        }
    }

    @Override
    protected void initViews() {
        mGrayBgActionBar = (GrayBgActionBar) findViewById(R.id.gray_actionbar_layout);
        mGrayBgActionBar.setTvTitle("趣看视界教程");
        mRecyclerView = (RecyclerView) findViewById(R.id.activity_qukandian_course_recyclerview);
        layout = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layout);
        mAdapter = new HelpAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);

        View headView = LayoutInflater.from(this).inflate(R.layout.head_question_ac,mRecyclerView,false);
        topRecy = headView.findViewById(R.id.topRecyView);
        topRecy.setLayoutManager(new GridLayoutManager(this,3));
        headAdapter = new QuestionHeadAdapter(this, mDatas);
        topRecy.setAdapter(headAdapter);
        headAdapter.setItemClick(new QuestionHeadAdapter.ItemClick() {
            @Override
            public void onItemClick(View v, int postion) {
                layout.scrollToPositionWithOffset(postion+1,0);
            }
        });


        mAdapter.setHeaderView(headView);

        findViewById(R.id.feedbackTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startToFeedbackActivity(QukandianCourseActivity.this);
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_qukandian_course_layout;
    }

    @Override
    public String setPagerName() {
        return "趣看视界教程";
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mGetEcoHelpProtocol = null;
    }
}
