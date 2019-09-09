package com.zhangku.qukandian.activitys.me;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.GoldDetailsAdapter;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseLoadingActivity;
import com.zhangku.qukandian.bean.GoldBean;
import com.zhangku.qukandian.bean.IncomeDetialsBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetGoldRecordProtocol;
import com.zhangku.qukandian.protocol.GetIncomeDetailsTopDataProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AdCommonBannerHelp;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.HuaweiNavigationBar;
import com.zhangku.qukandian.utils.RecyclerViewDivider;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.GrayBgActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzuoning on 2017/4/11.
 * 收入明细(我的钱包)
 */

public class IncomeDetailsActivity extends BaseLoadingActivity implements View.OnClickListener {
    private GoldDetailsAdapter mAdapter;
    private ArrayList<GoldBean> mGoldBeens = new ArrayList<>();
    private int mPage = 1;
    private GetGoldRecordProtocol mGetGoldRecordProtocol;
    protected RecyclerView mRecyclerView;
    private GrayBgActionBar mGrayBgActionBar;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_income_details_layout;
    }

    @Override
    public String setPagerName() {
        return "收入明细";
    }

    @Override
    protected void loadData() {
        hideLoadingLayout();
        if (null == mGetGoldRecordProtocol) {
            mGetGoldRecordProtocol = new GetGoldRecordProtocol(this, mPage, Constants.SIZE * 2, new BaseModel.OnResultListener<List<GoldBean>>() {
                @Override
                public void onResultListener(List<GoldBean> response) {
//                    if (response.size() > 0) {
                        mPage++;
                        if (response.size() >= Constants.SIZE * 2) {
                            mRecyclerView.addOnScrollListener(mOnScrollListener);
                        } else {
                            mRecyclerView.removeOnScrollListener(mOnScrollListener);
                        }
                        mGoldBeens.clear();
                        mGoldBeens.addAll(response);
                        mAdapter.notifyDataSetChanged();
//                    } else {
//                        showEmptySearch("您还没有金币啊~快去做任务赚金币吧");
//                    }
                    hideLoadingLayout();
                    mGetGoldRecordProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    showLoadFail();
                    mGetGoldRecordProtocol = null;
                }
            });
            mGetGoldRecordProtocol.postRequest();
        }
    }

    @Override
    public void initViews() {
//        if (!UserManager.getInst().hadLogin()) {
//            ActivityUtils.startToBeforeLogingActivity(this);
//            finish();
//            return;
//        }
        mGrayBgActionBar = findViewById(R.id.gray_actionbar_layout);

        mRecyclerView = findViewById(R.id.incomedetails_recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(IncomeDetailsActivity.this, 2,
                LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(IncomeDetailsActivity.this, R.color.grey_f2)));
//        mFootView = new FootView(IncomeDetailsActivity.this, mRecyclerView);
        mAdapter = new GoldDetailsAdapter(this, mGoldBeens);
        mAdapter.setType(mAdapter.TYPE_GOLD);
//        mAdapter.setFooterView(mFootView.getView());
        mRecyclerView.setAdapter(mAdapter);
        //头部
        View headView = LayoutInflater.from(this).inflate(R.layout.head_income_details_ac, mRecyclerView, false);
        TextView myTodayGoldNumTV = (TextView) headView.findViewById(R.id.myTodayGoldNumTV);
        TextView myHisGoldNumTV = (TextView) headView.findViewById(R.id.myHisGoldNumTV);
        TextView withdrawBtn = (TextView) headView.findViewById(R.id.withdrawBtn);
        TextView myGoldNumTV = (TextView) headView.findViewById(R.id.myGoldNumTV);
        FrameLayout adView = (FrameLayout) headView.findViewById(R.id.adView);
        headView.findViewById(R.id.newPeopleGuide).setOnClickListener(this);

        myHisGoldNumTV.setText(""+UserManager.getInst().getUserBeam().getGoldAccount().getSum());
        myTodayGoldNumTV.setText("" + UserManager.getInst().getUserBeam().getGoldAccount().getTodayAmout());
        myGoldNumTV.setText("" + UserManager.getInst().getUserBeam().getGoldAccount().getAmount());

        withdrawBtn.setOnClickListener(this);
        AdCommonBannerHelp.getInstance().getAdResult(this, adView);
        mAdapter.setHeaderView(headView);
        //底部
        View inflate = LayoutInflater.from(this).inflate(R.layout.foot_view_income_detail, mRecyclerView,false);
        mAdapter.setFooterView(inflate);
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        if("vivo".equals(QuKanDianApplication.mUmen)){//vivo审核不通过
            adView.setVisibility(View.GONE);
        }
//        View view = findViewById(R.id.incomedetails_palceholder);
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.height = HuaweiNavigationBar.getNavigationBarHeight(this);
//        view.setLayoutParams(layoutParams);

        findViewById(R.id.incomeLL).setOnClickListener(this);
        findViewById(R.id.rankingLL).setOnClickListener(this);

        mGrayBgActionBar.setTvTitle("我的钱包");
    }


    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    protected RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0 && recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange()) {
                loadMoreData();
            }

        }
    };

    protected void loadMoreData() {
        if (mGetGoldRecordProtocol == null) {
            mGetGoldRecordProtocol = new GetGoldRecordProtocol(this, mPage, Constants.SIZE * 2, new BaseModel.OnResultListener<List<GoldBean>>() {
                @Override
                public void onResultListener(List<GoldBean> response) {
//                    if (null != mFootView) {
//                        mFootView.hide();
//                    }
                    if (response.size() > 0) {
                        mPage++;
                        mGoldBeens.addAll(response);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showLongToast(IncomeDetailsActivity.this, "没有更多数据");
                        mRecyclerView.removeOnScrollListener(mOnScrollListener);
                    }
                    mGetGoldRecordProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
//                    if (null != mFootView) {
//                        mFootView.hide();
//                    }
                    mGetGoldRecordProtocol = null;
                }
            });
            mGetGoldRecordProtocol.postRequest();
        }
    }

    @Override
    public int getLoadingViewParentId() {
        return R.id.incomedetails_loadinglayout;
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mGrayBgActionBar = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.withdrawBtn://提现
                ActivityUtils.startToWithdrawalsActivity(this);
                break;
            case R.id.incomeLL://晒收入
                MobclickAgent.onEvent(this, "294-shaishouru");
                ActivityUtils.startToShareIncomeActivity(IncomeDetailsActivity.this);
                break;
            case R.id.rankingLL://排行榜
                ActivityUtils.startToIncomeRankActivity(this);
                break;
            case R.id.newPeopleGuide://新人指导
                ActivityUtils.startToQukandianCourseActivity(this);
                break;
        }
    }
}
