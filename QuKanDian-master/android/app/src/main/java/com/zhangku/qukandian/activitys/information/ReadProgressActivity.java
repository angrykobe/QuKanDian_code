package com.zhangku.qukandian.activitys.information;

import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.adapter.DetailsAdapter;
import com.zhangku.qukandian.adapter.ReadProgressAdapter;
import com.zhangku.qukandian.adapter.SignDialogAdapter;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.ReadProgessBean;
import com.zhangku.qukandian.bean.RecommendAdsBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewArtDetailsAboutDataProtocol;
import com.zhangku.qukandian.protocol.GetReadProgessProtocol;
import com.zhangku.qukandian.utils.AdsLogUpUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 阅读进度页
 * 2019-6-3 14:38:07
 * ljs
 */
public class ReadProgressActivity extends BaseAct {
    private RecyclerView mRecyclerView;
    private ImageView mIvBackBtn;
    private View loadingView;

    private ReadProgressAdapter mAdapter;
    private ReadProgressHeadLayout headView;
    private List<Object> mDatas;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_read_progress;
    }

    @Override
    protected void initViews() {
        mIvBackBtn = findViewById(R.id.activity_read_progress_back);
        loadingView = findViewById(R.id.loadingView);


        //刷新
        SmartRefreshLayout refreshLayout = findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
        refreshLayout.setEnableRefresh(false);

        mRecyclerView = findViewById(R.id.RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mDatas = new ArrayList<>();
        mAdapter = new ReadProgressAdapter(this, mDatas,Constants.TYPE_NEW);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, 1, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.grey_f2)));

        //头部布局，标题，内容，点击张开等功能
        headView = (ReadProgressHeadLayout) LayoutInflater.from(this).inflate(R.layout.view_read_progress_top, mRecyclerView, false);
        mAdapter.setHeaderView(headView);

        mIvBackBtn.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        if (!CommonHelper.isNetworkAvalible(this)) {
            setContentView(R.layout.view_error);
            return;
        }
        loadingView.setVisibility(View.VISIBLE);
        View viewById = loadingView.findViewById(R.id.loadingIV);
        viewById.setBackgroundResource(R.drawable.load_anim);
//        getInfoData();

    }

    private void getInfoData(){
        //相关推荐
        new GetReadProgessProtocol(this, new BaseModel.OnResultListener<ReadProgessBean>() {
            @Override
            public void onResultListener(ReadProgessBean readProgessBean) {
                loadingView.setVisibility(View.GONE);
                if (readProgessBean.getFinished() != null && readProgessBean.getFinished().size() > 0) {
                    headView.setData(readProgessBean.getFinished());
                } else {
                    headView.setData(null);
                }
                mDatas.removeAll(mDatas);
                startFetchAdThread(0,readProgessBean.getAds());
            }

            @Override
            public void onFailureListener(int code, String error) {
            }
        }).postRequest();
    }

    /**
     * 插入广告
     */
    private void startFetchAdThread(final int start, List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> ads) {
        if (ads == null) return;

        Collections.sort(ads, new Comparator<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean>() {
            @Override
            public int compare(AdLocationBeans.AdLocationsBean.ClientAdvertisesBean o1, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean o2) {
                if (o1.getPageIndex() > o2.getPageIndex()) {
                    return 1;
                }
                if (o1.getPageIndex() < o2.getPageIndex()) {
                    return -1;
                }
                return 0;
            }
        });

        for (int i = 0; i < ads.size(); i++) {
            AdLocationBeans.AdLocationsBean.ClientAdvertisesBean ad = ads.get(i);
            mDatas.add(start + ad.getPageIndex() - 1, null);
            LogUtils.LogW("aaaaaadd:" + (start + ad.getPageIndex() - 1));
        }

        AdUtil.fetchEach(this, ads, AnnoCon.AD_TYPE_READ_PROGRESS, -1, new AdUtil.AdCallBack() {
            @Override
            public void getAdContent(Object object, int adIndex) {
                int adIndexForList = start + adIndex - 1;//广告对于list中的位置，（上啦加载更多的时候会变动）
                LogUtils.LogW("aaaaaset:" + adIndexForList);
                if (adIndexForList >= 0 && mDatas.size() > adIndexForList) {
                    mDatas.set(adIndexForList, object);
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }



    @Override
    protected String setTitle() {
        return "阅读进度";
    }

    @Override
    protected void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.activity_read_progress_back:
                finish();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
        getInfoData();
    }
}
