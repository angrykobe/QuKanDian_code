package com.zhangku.qukandian.activitys.information;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.DetailsAdapter;
import com.zhangku.qukandian.adapter.ReadProgressTopAdapter;
import com.zhangku.qukandian.bean.ReadProgessBean;
import com.zhangku.qukandian.bean.ReadProgressBean;
import com.zhangku.qukandian.bean.UserBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.RecyclerViewDivider;

import java.util.ArrayList;

public class ReadProgressHeadLayout extends LinearLayout implements View.OnClickListener{
    private Context mContext;
    private RecyclerView mRecyclerView;
    private TextView mReadProgessTip;
    private ReadProgressTopAdapter mAdapter;
    private ArrayList<Object> mDatas = new ArrayList<>();

    public ReadProgressHeadLayout(Context context) {
        this(context, null);
    }

    public ReadProgressHeadLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReadProgressHeadLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.header_read_progress, this);
        mRecyclerView = findViewById(R.id.RecyclerView);
        mReadProgessTip = findViewById(R.id.tv_read_progress_tip);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ReadProgressTopAdapter(mContext, mDatas);//
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(mContext, 1, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(mContext, R.color.grey_f2)));

        getGoldView();
        mDatas.addAll(UserManager.getInst().getQukandianBean().getReadprogress().getProgressRule());
    }

    //显示阅读进度按钮
    private void getGoldView() {
        if (UserManager.getInst().hadLogin()) {//金币
            if (UserBean.getShowReadProgress()) {
                ArrayList<ReadProgressBean.ProgressRuleBean> list = UserManager.getInst().getQukandianBean().getReadprogress().getProgressRule();
                if (list != null && list.size() > 0) {
                    int gold = 0;//阅读进度能获取到奖励的总金币数
                    for (ReadProgressBean.ProgressRuleBean bean : list) {
                        gold = gold + bean.getGold();
                    }
                    mReadProgessTip.setText("" + gold);
                }
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }



    public void setData() {

    }

    public void setData(Object object) {
        if (object==null){
            ReadProgressTopAdapter.finishedBean.removeAll(ReadProgressTopAdapter.finishedBean);
            return;
        }
        ReadProgressTopAdapter.finishedBean = (ArrayList<Integer>) object;
        mAdapter.notifyDataSetChanged();
    }



}
