package com.zhangku.qukandian.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.adapter.DialogRecommendDailyAdapter;
import com.zhangku.qukandian.adapter.SignDialogAdapter;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.RecommendDailyBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewRecommendedDailyProtocol;
import com.zhangku.qukandian.utils.QRShare;
import com.zhangku.qukandian.utils.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * 拆宝箱成功
 * Created by yuzuoning on 2017/7/24.
 */

public class DialogSignBaoXiang extends BaseDialog implements View.OnClickListener, QRShare.OnShareSuccessListener, DialogRecommendDailyAdapter.OnCliclItemListener {
    private LinearLayout mLayoutTask_completed_tip;
    private TextView mTvGold;
    private ImageView mTvCancelBtn;
    private RecyclerView mRecyclerView;
    private SignDialogAdapter mAdapter;

    public DialogSignBaoXiang(Context context) {
        super(context, R.style.zhangku_dialog);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_sign_layout;
    }

    @Override
    protected void initView() {
        mLayoutTask_completed_tip = findViewById(R.id.task_completed_tip);
        mTvGold = findViewById(R.id.dialog_sign_gold);
        mTvCancelBtn = findViewById(R.id.dialog_sign_cancel);
        mRecyclerView = findViewById(R.id.dialog_chaibaoxiang_task);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getContext(), 1,
                LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(getContext(), R.color.black_a9)));

        mTvCancelBtn.setOnClickListener(this);
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }

    public void setGold(String gold) {
        mTvGold.setText(gold + "金币");
    }

    public void getDataForTask(int type) {
        new GetNewRecommendedDailyProtocol(getContext(), type, new BaseModel.OnResultListener<List<RecommendDailyBean>>() {
            @Override
            public void onResultListener(List<RecommendDailyBean> response) {
                if (response.size() > 0) {
                    mLayoutTask_completed_tip.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    DialogRecommendDailyAdapter mAdapter = new DialogRecommendDailyAdapter(getContext(), response, DialogSignBaoXiang.this);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                    mLayoutTask_completed_tip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();
    }

    public void getDataForAd() {
        final List<Object> list = new ArrayList<>();
        mAdapter = new SignDialogAdapter(getContext(), list);
        mRecyclerView.setAdapter(mAdapter);

        AdUtil.fetchAd(getContext(), AnnoCon.AD_TYPE_CHAI, new AdUtil.AdCallBack() {
            @Override
            public void getAdContent(Object object, int adIndex) {
                mLayoutTask_completed_tip.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                list.add(object);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_sign_cancel:
                MobclickAgent.onEvent(getContext(), "05_02_clickclossbtn");
                dismiss();
                break;
        }
    }

    @Override
    public void onShareSuccessListener() {
        dismiss();
    }

    @Override
    public void onCliclItemListener(int position) {
        dismiss();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mAdapter != null && hasFocus)
            mAdapter.notifyDataSetChanged();
    }

    @Override
    protected boolean isCanceledOnTouchOutside() {
        return false;
    }
}
