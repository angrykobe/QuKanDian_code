package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.DialogRecommendDailyAdapter;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.RecommendDailyBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetRecommendedDailyProtocol;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2018/1/3.
 */

public class DialogRecommendedDaily extends BaseDialog implements DialogRecommendDailyAdapter.OnCliclItemListener {
    private RecyclerView mRecyclerView;
    private DialogRecommendDailyAdapter mAdapter;
    private ImageView mIvCancel;
    private List<RecommendDailyBean> mDatas = new ArrayList<>();

    public DialogRecommendedDaily(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_recommended_daily;
    }

    @Override
    protected void initView() {
        mIvCancel = findViewById(R.id.dialog_recommend_daily_dismiss);
        mRecyclerView = findViewById(R.id.dialog_recommend_daily_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new DialogRecommendDailyAdapter(getContext(), mDatas, this);
        mRecyclerView.setAdapter(mAdapter);
        mIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobclickAgent.onEvent(mContext, "293-rwtjguanbi");
                dismiss();
            }
        });
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    public void loadData() {
        new GetRecommendedDailyProtocol(getContext(), new BaseModel.OnResultListener<ArrayList<RecommendDailyBean>>() {
            @Override
            public void onResultListener(ArrayList<RecommendDailyBean> response) {
                String today = CommonHelper.formatTimeYMD(System.currentTimeMillis(), true);
                UserSharedPreferences.getInstance().putString(Constants.SHOW_RANK, today);
                mDatas.clear();
                mDatas.addAll(response);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();
    }

    @Override
    public void onCliclItemListener(int position) {
        dismiss();
    }

    @Override
    public void show() {
        super.show();
        MobclickAgent.onEvent(mContext, "293-renwutuijian");
    }
}
