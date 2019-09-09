package com.zhangku.qukandian.fragment.information;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.adapter.InformationAdapter;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.base.BaseRefreshRecyclerViewFragment;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.RecommendAdsBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnCilicReadRecordListener;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetAdsListForTypeProtocol;
import com.zhangku.qukandian.protocol.GetRecommendAdsProtocol;
import com.zhangku.qukandian.utils.AdsLogUpUtils;
import com.zhangku.qukandian.utils.AnimUtil;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.zhangku.qukandian.config.Constants.TYPE_NEW;

/**
 * Created by yuzuoning on 2017/3/24.
 * 资讯列表
 */
public class InformationInSideFragment extends BaseRefreshRecyclerViewFragment {
    private int mChannelId = -1;
    private String mTabname = "";
    private InformationAdapter mAdapter;
    private ArrayList<Object> mDatas = new ArrayList<>();
    private GetRecommendAdsProtocol mGetRecommendProtocol;

    public void setChannelId(int id, String channeName) {
        mChannelId = id;
        mTabname = channeName;
    }

    public int getChannelId() {
        return mChannelId;
    }

    @Override
    protected void noNetword() {

    }

    @Override
    public void loadData(Context context) {
        String key = mChannelId + "" + Constants.TYPE_NEW;
        long duration = System.currentTimeMillis() - UserSharedPreferences.getInstance().getLong(key, System.currentTimeMillis());
        List<InformationBean> informationBeen = DBTools.queryListByChannelId(mChannelId, Constants.TYPE_NEW);
        if (informationBeen.size() > 0 && duration < 60 * 60 * 1000) {
            mDatas.clear();
            mDatas.addAll(informationBeen);
            mAdapter.notifyDataSetChanged();

            new GetAdsListForTypeProtocol(context, AnnoCon.AD_TYPE_HOME, mChannelId, new BaseModel.OnResultListener<List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean>>() {
                @Override
                public void onResultListener(List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> response) {
                    startFetchAdThread(0, response);
                }

                @Override
                public void onFailureListener(int code, String error) {

                }
            }).postRequest();

            if (null != mRecyclerView) {
                mRecyclerView.scrollToPosition(0);
            }
            hideLoadingLayout();
        } else {
            if (mGetRecommendProtocol == null) {
                mGetRecommendProtocol = new GetRecommendAdsProtocol(getContext(), mChannelId, TYPE_NEW,
                        Constants.SIZE, true, new BaseModel.OnResultListener<RecommendAdsBean>() {
                    @Override
                    public void onResultListener(RecommendAdsBean recommendAdsBean) {
                        List<InformationBean> response = recommendAdsBean.getPostTexts();
                        if (response.size() > 0) {
                            mDatas.clear();
                            mDatas.addAll(response);
                            mAdapter.notifyDataSetChanged();
                            DBTools.insertList(response, mChannelId, Constants.TYPE_NEW);

                            startFetchAdThread(0, recommendAdsBean.getAds());

                            hideLoadingLayout();
                            if (null != mRecyclerView) {
                                mRecyclerView.scrollToPosition(0);
                            }
                        } else {
                            showEmptySearch("暂无数据");
                        }
                        mGetRecommendProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        if (null != mSwipeRefreshLayout) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        showLoadFail();
                        mGetRecommendProtocol = null;
                    }
                });
                mGetRecommendProtocol.postRequest();
            }
        }
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

        AdUtil.fetchEach(getActivity(), ads, AnnoCon.AD_TYPE_HOME, mChannelId, new AdUtil.AdCallBack() {
            @Override
            public void getAdContent(Object object, int adIndex) {
                int adIndexForList = start + adIndex - 1;//广告对于list中的位置，（上啦加载更多的时候会变动）
                LogUtils.LogW("aaaaaset:" + adIndexForList);
                if (adIndexForList >= 0 && mDatas.size() > adIndexForList) {
                    mDatas.set(adIndexForList, object);
                    if (mAdapter != null) {
                        mAdapter.notifyDataSetChanged();
                    }
                    //保存广告日志
                    AdsLogUpUtils.saveAdsList(AdsLogUpUtils.ADLOGUP_TYPE_INFO_LIST,object);
                }
            }
        });
    }

    @Override
    protected void onLoadingFailClick() {
        super.onLoadingFailClick();
        showLoading();
        loadData(getContext());
    }


    @Override
    protected void onLoadNotNetwordClick() {
        super.onLoadNotNetwordClick();
        showLoading();
        loadData(getContext());
    }

    @Override
    protected synchronized BaseRecyclerViewAdapter<Object> getAdapter() {
        if (mAdapter == null) {
            mAdapter = new InformationAdapter(getContext(), mDatas, true, new OnCilicReadRecordListener() {
                @Override
                public void onCilicReadRecordListener() {
                    loadMore(true);
                }
            }, true);
            mRecyclerView.setAdapter(mAdapter);
        }
        return mAdapter;
    }

    @Override
    protected void refresh() {
        loadMore(true);
    }

    @Override
    protected void loadMoreData(RecyclerView recyclerView, int dx, int dy) {
        loadMore(false);
    }

    @Override
    protected String getLoadType() {
        return "文章";
    }

    public synchronized void loadMore(final boolean up) {
//        if (null == mGetRecommendProtocol && isCanLoad) {
        if (null == mGetRecommendProtocol) {
            if (up) {
                //下拉
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
                mGetRecommendProtocol = new GetRecommendAdsProtocol(getContext(), mChannelId, TYPE_NEW,
                        Constants.SIZE, false, new BaseModel.OnResultListener<RecommendAdsBean>() {
                    @Override
                    public void onResultListener(final RecommendAdsBean recommendAdsBean) {
                        List<InformationBean> response = recommendAdsBean.getPostTexts();
                        if (mAdapter != null && response != null && mHandler != null) {
                            mDatas.addAll(0, response);
                            mAdapter.showReadRecord(response.size());
                            mAdapter.notifyDataSetChanged();

                            startFetchAdThread(0, recommendAdsBean.getAds());
                            DBTools.insertList(response, mChannelId, Constants.TYPE_NEW);
                            //显示为你推荐
                            animHeadView(response.size());
                        }
                        if (null != mRecyclerView) {
                            mRecyclerView.scrollToPosition(0);
                        }
                        if (null != mSwipeRefreshLayout) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        mGetRecommendProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        if (getContext() != null) {
                            if (null != mSwipeRefreshLayout) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                            ToastUtils.showLongToast(getContext(), "加载失败");
                            mGetRecommendProtocol = null;

                        }
                    }
                });
                mGetRecommendProtocol.postRequest();
            } else {
                //上拉
                mGetRecommendProtocol = new GetRecommendAdsProtocol(getContext(), mChannelId, TYPE_NEW,
                        Constants.SIZE, false, new BaseModel.OnResultListener<RecommendAdsBean>() {
                    @Override
                    public void onResultListener(RecommendAdsBean recommendAdsBean) {
                        List<InformationBean> response = recommendAdsBean.getPostTexts();
                        if (null != mFootView) {
                            mFootView.hide();
                        }
                        int start = 0;
                        if (response.size() > 0) {
                            start = mDatas.size();
                            mDatas.addAll(response);

                            startFetchAdThread(start, recommendAdsBean.getAds());

                            DBTools.insertList(response, mChannelId, Constants.TYPE_NEW);
                        } else {
                            ToastUtils.showShortToast(getContext(), "没有更多数据");
                            mRecyclerView.removeOnScrollListener(mOnScrollListener);
                        }
                        mAdapter.notifyDataSetChanged();
                        mGetRecommendProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        ToastUtils.showLongToast(getContext(), "加载失败");
                        mGetRecommendProtocol = null;

                    }
                });
                mGetRecommendProtocol.postRequest();
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public String setPagerName() {
        return mTabname + "资讯内页";
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        if (null != mGetRecommendProtocol) {
            mGetRecommendProtocol = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserSharedPreferences.getInstance().getBoolean(Constants.REFRESH_FLAG_1, false)) {
            UserSharedPreferences.getInstance().putBoolean(Constants.REFRESH_FLAG_1, false);
            mAdapter.notifyDataSetChanged();
        }
    }

    //为您推荐头部提醒动画
    private void animHeadView(int num) {
        mTvRemindText.setText("趣看视界为您推荐" + num + "条新资讯");
        AnimUtil.animHeadView(getContext(), mLayoutHeadRemind);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if ("社会摇".equals(mTabname)) {
                MobclickAgent.onEvent(getContext(), "293_Channel_shehuiyao_fragment");
            }
        }
    }
}
