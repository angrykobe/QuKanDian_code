package com.zhangku.qukandian.activitys;

import android.content.Intent;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.SearchHistoryAdapter;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.SearchHistoryBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.fragment.search.SearchArticleFragment;
import com.zhangku.qukandian.fragment.search.SearchTegetherFragment;
import com.zhangku.qukandian.fragment.search.SearchVideoFragment;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.SearchHeaderView;
import com.zhangku.qukandian.widght.SearchMainView;
import com.zhangku.qukandian.widght.TabLayoutCommonLayout;

import java.util.Map;

/**
 * Created by yuzuoning on 2017/3/29.
 * 搜索页
 */

public class SearchActivity extends BaseTitleActivity implements SearchHeaderView.IClickListener, SearchHistoryAdapter.IOnclickHistoryItemListener {
    private SearchHeaderView mSearchHeaderView;
    private SearchMainView mSearchMainView;
    private TabLayoutCommonLayout mSearchResultView;

    private SearchTegetherFragment mSearchTegetherFragment;
    private SearchVideoFragment mSearchVideoFragment;
    private SearchArticleFragment mSearchArticleFragment;

    private String mKeywordForLable = "";
    public static final int FROM_VIDEO = 0;
    public static final int FROM_OTHER = 1;
    private int mFromType;

    @Override
    protected void onResume() {
        super.onResume();
        Map<String,String> map = new ArrayMap<>();
        map.put("to","搜索页");
        MobclickAgent.onEvent(this, "AllPv",map);
    }

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        if(null != getIntent()){
            mKeywordForLable = "#"+getIntent().getExtras().getString(Constants.KEY_WORD)+"#";
            mFromType = getIntent().getExtras().getInt(Constants.FROM_TYPE,0);
        }

        mSearchHeaderView = (SearchHeaderView) findViewById(R.id.activity_search_layout_header);
        mSearchMainView = (SearchMainView) findViewById(R.id.activity_search_layout_main);
        mSearchResultView = (TabLayoutCommonLayout) findViewById(R.id.activity_search_layout_result);

        mSearchTegetherFragment = new SearchTegetherFragment();
        mSearchArticleFragment = new SearchArticleFragment();
        mSearchVideoFragment = new SearchVideoFragment();
        mSearchResultView.setVisibility(View.GONE);

        mSearchResultView.addFragment(mSearchTegetherFragment, "综合");
        mSearchResultView.addFragment(mSearchArticleFragment, "资讯");
        mSearchResultView.addFragment(mSearchVideoFragment, "视频");

        mSearchHeaderView.setIClickListener(this);
        mSearchMainView.setIOnclickHistoryItemListener(this);

        if(!"##".equals(mKeywordForLable)){
            mSearchHeaderView.setKeyword(mKeywordForLable);
            onCLickSearchListener(mKeywordForLable);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search_layout;
    }

    @Override
    public String setPagerName() {
        return "搜索";
    }

    @Override
    public void onClickClearListener() {
        mSearchResultView.setVisibility(View.GONE);
        mSearchMainView.setVisibility(View.VISIBLE);
        mSearchTegetherFragment.initPager();
        mSearchArticleFragment.initPager();
        mSearchVideoFragment.initPager();
    }

    @Override
    public void onClickCancleListener() {
        finish();
    }

    @Override
    public void onCLickSearchListener(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            ToastUtils.showLongToast(this, "请输点什么再搜索吧~");
        } else {
            if(mFromType == FROM_VIDEO){
                mSearchResultView.setCurrentTab(2);
            }else {
                mSearchResultView.setCurrentTab(0);
            }

            mSearchResultView.setVisibility(View.VISIBLE);
            mSearchMainView.setVisibility(View.GONE);
            mSearchTegetherFragment.searchKeyword(keyword);
            mSearchArticleFragment.searchKeyword(keyword);
            mSearchVideoFragment.searchKeyword(keyword);
            SearchHistoryBean bean = new SearchHistoryBean();
            bean.setKeyword(keyword);
            bean.setDate(System.currentTimeMillis());
            DBTools.addSearchHistory(bean);
            mSearchMainView.notifyHistory();
        }
    }

    @Override
    public void onclickHistoryItemListener(String keyword) {
        mSearchHeaderView.setKeyword(keyword);
        onCLickSearchListener(keyword);
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mSearchHeaderView = null;
        mSearchMainView = null;
        mSearchResultView = null;
        mSearchTegetherFragment = null;
        mSearchVideoFragment = null;
        mSearchArticleFragment = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(QuKanDianApplication.getmContext()).onActivityResult(requestCode, resultCode, data);

    }
}
