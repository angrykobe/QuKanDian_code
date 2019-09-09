package com.zhangku.qukandian.fragment.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.zhangku.qukandian.adapter.InformationAdapter;
import com.zhangku.qukandian.base.BaseLoadMoreFragment;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.OnCilicReadRecordListener;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetSearchRestulProtocol;
import com.zhangku.qukandian.utils.ToastUtils;

import java.util.ArrayList;

/**
 * Created by yuzuoning on 2017/4/3.
 */

public class SearchArticleFragment extends BaseLoadMoreFragment implements OnCilicReadRecordListener {
    private int mPage = 1;
    private InformationAdapter mAdapter;
    private ArrayList<Object> mDatas = new ArrayList<>();
    private String mKeyword = "";
    private boolean load = false;
    private GetSearchRestulProtocol mGetSearchRestulProtocol;

    @Override
    protected void noNetword() {
        showEmptyNetword();
    }

    @Override
    public void loadData(Context context) {
        showLoading();
        if(null == mGetSearchRestulProtocol){
            mGetSearchRestulProtocol = new GetSearchRestulProtocol(getContext(), Constants.TYPE_NEW, mKeyword, Constants.SIZE, mPage, new BaseModel.OnResultListener<ArrayList<InformationBean>>() {
                @Override
                public void onResultListener(ArrayList<InformationBean> response) {
                    load = true;
                    if (response.size() > 0) {
                        mPage++;
                        if (response.size() >= 1) {
                            mRecyclerView.addOnScrollListener(mOnScrollListener);
                        } else {
                            if (null != mGetSearchRestulProtocol.mSearchBean) {
                                if (mGetSearchRestulProtocol.mSearchBean.getPageSize() == Constants.SIZE) {
                                    mRecyclerView.addOnScrollListener(mOnScrollListener);
                                } else {
                                    mRecyclerView.removeOnScrollListener(mOnScrollListener);
                                }
                            } else {
                                mRecyclerView.removeOnScrollListener(mOnScrollListener);
                            }
                        }
                        mDatas.clear();
                        mDatas.addAll(response);
                        mAdapter.notifyDataSetChanged();
                        hideLoadingLayout();
                    } else {
                        showEmptySearch();
                    }
                    if (null != mFootView) {
                        mFootView.hide();
                    }
                    mGetSearchRestulProtocol = null;
                }

                @Override
                public void onFailureListener(int code,String error) {
                    if (null != mFootView) {
                        mFootView.hide();
                    }
                    mGetSearchRestulProtocol = null;
                    showLoadFail();
                }
            });
            mGetSearchRestulProtocol.postRequest();
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

    public void searchKeyword(String keyword) {
        mKeyword = keyword;
        mPage = 1;
        if (load) {
            loadData(getContext());
        }
    }

    @Override
    protected BaseRecyclerViewAdapter getAdapter() {
        mAdapter = new InformationAdapter(getContext(), mDatas,false, this,false);
        return mAdapter;
    }

    @Override
    protected void loadMoreData(RecyclerView recyclerView, int dx, int dy) {
        loadMore();
    }

    private void loadMore() {
        if (null == mGetSearchRestulProtocol) {
            mGetSearchRestulProtocol = new GetSearchRestulProtocol(getContext(), Constants.TYPE_NEW, mKeyword, Constants.SIZE, mPage, new BaseModel.OnResultListener<ArrayList<InformationBean>>() {
                @Override
                public void onResultListener(ArrayList<InformationBean> response) {
                    load = true;
                    mFootView.hide();
                    if (response.size() > 0) {
                        mPage++;
                        int start = mDatas.size();
                        mDatas.addAll(response);
                        mAdapter.notifyItemRangeInserted(start, response.size());
                    } else {
                        ToastUtils.showLongToast(getContext(), "没有更多数据");
                        mRecyclerView.removeOnScrollListener(mOnScrollListener);
                    }
                    mGetSearchRestulProtocol = null;
                }

                @Override
                public void onFailureListener(int code,String error) {
                    mGetSearchRestulProtocol = null;
                }
            });
            mGetSearchRestulProtocol.postRequest();
        }
    }

    @Override
    public String setPagerName() {
        return "搜索-资讯";
    }

    public void initPager() {
        mPage = 1;
    }

    @Override
    public void onCilicReadRecordListener() {

    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mDatas.clear();
        mDatas = null;
        mFootView = null;
        mAdapter = null;
        mLinearLayoutManager = null;
        mRecyclerView.setAdapter(null);
        mRecyclerView = null;
    }
}