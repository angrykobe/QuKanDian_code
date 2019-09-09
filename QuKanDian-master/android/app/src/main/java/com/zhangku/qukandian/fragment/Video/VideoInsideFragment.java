package com.zhangku.qukandian.fragment.Video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.adapter.VideoAdapter;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.base.BaseRefreshRecyclerViewFragment;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.RecommendAdsBean;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnSharedListener;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetAdsListForTypeProtocol;
import com.zhangku.qukandian.protocol.GetRecommendAdsProtocol;
import com.zhangku.qukandian.protocol.GetRecommendProtocol;
import com.zhangku.qukandian.utils.AdsLogUpUtils;
import com.zhangku.qukandian.utils.AnimUtil;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yuzuoning on 2017/3/30.
 */

public class VideoInsideFragment extends BaseRefreshRecyclerViewFragment {
    private int mChannelId = 0;
    private String mTabName = "";
    private VideoAdapter mAdapter;
    private ArrayList<Object> mDatas = new ArrayList<>();
    //    private OnSharedListener mOnSharedListener;
    private GetRecommendAdsProtocol mGetRecommendProtocol;
    private int mSize = 0;

    public void setChannelId(int id) {
        mChannelId = id;
    }

    @Override
    protected void noNetword() {
    }

    @Override
    public void loadData(Context context) {
        String key = mChannelId + "" + Constants.TYPE_VIDEO;
        long duration = System.currentTimeMillis() - UserSharedPreferences.getInstance().getLong(key, System.currentTimeMillis());
        List<InformationBean> informationBeen = DBTools.queryListByChannelId(mChannelId, Constants.TYPE_VIDEO);
        if (informationBeen.size() > 0 && duration < 3600000) {
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
            hideLoadingLayout();
        } else {
            if (null == mGetRecommendProtocol) {
                mGetRecommendProtocol = new GetRecommendAdsProtocol(getContext(), mChannelId, Constants.TYPE_VIDEO, Constants.SIZE, false, new BaseModel.OnResultListener<RecommendAdsBean>() {
                    @Override
                    public void onResultListener(RecommendAdsBean recommendAdsBean) {
                        List<InformationBean> response = recommendAdsBean.getPostTexts();
                        if (response.size() > 0) {
                            mDatas.clear();
                            mDatas.addAll(response);
                            mAdapter.notifyDataSetChanged();
                            DBTools.insertList(response, mChannelId, Constants.TYPE_VIDEO);
                            startFetchAdThread(0, recommendAdsBean.getAds());
                            hideLoadingLayout();
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
                        mGetRecommendProtocol = null;
                        showLoadFail();
                        ToastUtils.showLongToast(getContext(), "加载失败,点击重试");
                    }
                });
                mGetRecommendProtocol.postRequest();
            }
        }
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
    protected BaseRecyclerViewAdapter<Object> getAdapter() {
        mAdapter = new VideoAdapter(getContext(), mDatas, new VideoAdapter.OnVideoSharedListener() {
            //            @Override
//            public void onSharedListener(DetailsBean mDetailsBean, SHARE_MEDIA share_media) {
//                if (null != mOnSharedListener) {
//                    mOnSharedListener.onSharedListener(mDetailsBean, share_media);
//                }
//            }
            @Override
            public void onClickReadRecordListener() {
                loadMore(true);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
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
        return "视频";
    }

    public void loadMore(final boolean up) {
        if (null == mGetRecommendProtocol) {
            if (up) {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
                mGetRecommendProtocol = new GetRecommendAdsProtocol(getContext(), mChannelId, Constants.TYPE_VIDEO, Constants.SIZE, false, new BaseModel.OnResultListener<RecommendAdsBean>() {
                    @Override
                    public void onResultListener(final RecommendAdsBean recommendAdsBean) {
                        List<InformationBean> response = recommendAdsBean.getPostTexts();
                        mSize = response.size();
                        mDatas.addAll(0, response);
                        if (mAdapter != null && mHandler != null) {
                            // 当前读到
                            mAdapter.showReadRecord(mSize);
                            mAdapter.notifyDataSetChanged();
                            startFetchAdThread(0, recommendAdsBean.getAds());

                            animHeadView(response.size());

                            mRecyclerView.scrollToPosition(0);
                        }
                        if (null != mSwipeRefreshLayout) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        DBTools.insertList(response, mChannelId, Constants.TYPE_VIDEO);
                        mGetRecommendProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        if (null != mSwipeRefreshLayout) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        ToastUtils.showLongToast(getContext(), "加载失败");
                        mGetRecommendProtocol = null;
                    }
                });
                mGetRecommendProtocol.postRequest();
            } else {
                mGetRecommendProtocol = new GetRecommendAdsProtocol(getContext(), mChannelId, Constants.TYPE_VIDEO, Constants.SIZE, false, new BaseModel.OnResultListener<RecommendAdsBean>() {
                    @Override
                    public void onResultListener(RecommendAdsBean recommendAdsBean) {
                        List<InformationBean> response = recommendAdsBean.getPostTexts();
                        if (response.size() > 0) {
                            int start = mDatas.size();
                            mDatas.addAll(response);
                            mAdapter.notifyItemRangeInserted(start, response.size());
                            startFetchAdThread(start, recommendAdsBean.getAds());
                        } else {
                            ToastUtils.showShortToast(getContext(), "没有更多数据");
                            if (null != mSwipeRefreshLayout) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                            mRecyclerView.removeOnScrollListener(mOnScrollListener);
                        }
                        if (null != mFootView) {
                            mFootView.hide();
                        }
                        DBTools.insertList(response, mChannelId, Constants.TYPE_VIDEO);
                        mGetRecommendProtocol = null;
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        if (null != mFootView) {
                            mFootView.hide();
                        }
                        mGetRecommendProtocol = null;
                        ToastUtils.showLongToast(getContext(), "加载失败");
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
        return mTabName + "视频内页";
    }


//    public void setOnSharedListener(OnSharedListener onSharedListener) {
//        mOnSharedListener = onSharedListener;
//    }

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

        AdUtil.fetchAd(getActivity(), ads, AnnoCon.AD_TYPE_HOME, mChannelId, new AdUtil.AdCallBack() {
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
                    AdsLogUpUtils.saveAdsList(AdsLogUpUtils.ADLOGUP_TYPE_VIDEO_LIST,object);
                }
            }
        });
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mHandler = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserSharedPreferences.getInstance().getBoolean(Constants.REFRESH_FLAG, false)) {
            UserSharedPreferences.getInstance().putBoolean(Constants.REFRESH_FLAG, false);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void initViews(View convertView) {
        super.initViews(convertView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //为您推荐头部提醒动画
    private void animHeadView(int num) {
        mTvRemindText.setText("趣看视界为您推荐" + num + "条新资讯");
        AnimUtil.animHeadView(getContext(), mLayoutHeadRemind);
    }
}
