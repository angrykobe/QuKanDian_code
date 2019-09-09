package com.zhangku.qukandian.activitys.information;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.ReadProfitAdapter;
import com.zhangku.qukandian.base.BaseLoadingActivity;
import com.zhangku.qukandian.bean.GoldBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewGoldRecordProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/18
 * 阅读收益
 */
public class ReadProfitActivity extends BaseLoadingActivity implements View.OnClickListener, UserManager.IOnLoginStatusLisnter {

    private TextView profitLoginTV;
    private RecyclerView mRecyclerView;
    private TextView profitGoLoginTV;
    private View profitShowMoneyView;
    private TextView profitMoneyTV;
    private int mPage = 1;
    private ReadProfitAdapter mAdapter;
    private GetNewGoldRecordProtocol mGetGoldRecordProtocol;

    @Override
    public int getLoadingViewParentId() {
        return R.id.url_loading;
    }

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        profitLoginTV = findViewById(R.id.profitLoginTV);
        mRecyclerView = findViewById(R.id.readProfitRecy);
        profitGoLoginTV = findViewById(R.id.profitGoLoginTV);
        profitShowMoneyView = findViewById(R.id.profitShowMoneyView);
        profitMoneyTV = findViewById(R.id.profitMoneyTV);

        findViewById(R.id.titleBackIV).setOnClickListener(this);
        findViewById(R.id.profitGoLoginTV).setOnClickListener(this);
        findViewById(R.id.profitGotoIncomeDetailsTV).setOnClickListener(this);

        mAdapter = new ReadProfitAdapter(this, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        initLoginView();
        hideLoadingLayout();
        UserManager.getInst().addLoginListener(this);
    }

    private void initLoginView(){
        mPage = 1;
        if(UserManager.getInst().hadLogin()){
            profitLoginTV.setVisibility(View.GONE);
            profitGoLoginTV.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            profitShowMoneyView.setVisibility(View.VISIBLE);
            loadMoreData();
        }else{//无登录界面操作
            profitLoginTV.setVisibility(View.VISIBLE);
            profitLoginTV.setText(R.string.read_profit_goto_login_str);
            profitGoLoginTV.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            profitShowMoneyView.setVisibility(View.GONE);
        }
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

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_read_profit;
    }

    @Override
    public String setPagerName() {
        return "阅读收益";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titleBackIV:
                finish();
                break;
            case R.id.profitGoLoginTV:
                ActivityUtils.startToBeforeLogingActivity(this);
                MobclickAgent.onEvent(this, "01_06_01_clicksignupbtn2");
                break;
            case R.id.profitGotoIncomeDetailsTV:
                ActivityUtils.startToIncomeDetailsActivity(this);
                break;
        }
    }

    protected void loadMoreData() {
        if (mGetGoldRecordProtocol == null) {
            mGetGoldRecordProtocol = new GetNewGoldRecordProtocol(this, mPage, Constants.SIZE * 2, new BaseModel.OnResultListener<List<GoldBean>>() {
                @Override
                public void onResultListener(List<GoldBean> response) {
                    if (response.size() > 0) {
                        mPage++;
                        double amount = Double.valueOf(profitMoneyTV.getText().toString());
                        for (GoldBean bean:response) {
                                amount += bean.getAmount();
                        }
                        profitMoneyTV.setText(""+amount);
                        mAdapter.addList(response);
                        if(mAdapter.getList().size()==0){
                            profitLoginTV.setVisibility(View.VISIBLE);
                            profitLoginTV.setText(R.string.read_profit_no_profit);
                            mRecyclerView.setVisibility(View.GONE);
                        }else{
                            profitLoginTV.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        profitLoginTV.setVisibility(View.VISIBLE);
                        profitLoginTV.setText(R.string.read_profit_no_profit);
//                        ToastUtils.showLongToast(ReadProfitActivity.this, "没有更多数据");
                        mRecyclerView.removeOnScrollListener(mOnScrollListener);
                    }
                    mGetGoldRecordProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mGetGoldRecordProtocol = null;
                }
            });
            mGetGoldRecordProtocol.postRequest();
        }
    }

    @Override
    public void onLoginStatusListener(boolean state) {
        initLoginView();
    }
}
