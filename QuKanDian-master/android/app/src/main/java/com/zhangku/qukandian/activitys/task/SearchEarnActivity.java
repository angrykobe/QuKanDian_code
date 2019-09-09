package com.zhangku.qukandian.activitys.task;

import android.view.View;

import com.sdk.searchsdk.DKSearch;
import com.sdk.searchsdk.SearchView;
import com.sdk.searchsdk.interfaces.NativePagerCallBack;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.widght.GrayBgActionBar;


/**
 * 2019年5月22日11:03:48
 * ljs
 * 搜索赚-云告
 */
public class SearchEarnActivity extends BaseTitleActivity implements View.OnClickListener {
    private GrayBgActionBar mGrayBgActionBar;
    private SearchView mSearchView;
    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {

        mGrayBgActionBar = (GrayBgActionBar) findViewById(R.id.gray_actionbar_layout);
        mGrayBgActionBar.setTvTitle("搜索赚");

        DKSearch.init(SearchEarnActivity.this,"59f82e69d44df");//测试，正式待定
        DKSearch.setNativePagerCallBack(new NativePagerCallBack() {
            @Override
            public void openPager(String s) {
                //添加 跳转 逻辑处理
            }
        });

        mSearchView = (SearchView)this.findViewById(R.id.mSearchView);
        //设置用户id
        DKSearch.setUesrId(this,"1000");

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(mSearchView != null){
            //重置 搜索框状态
            mSearchView.onResume();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_task_search_earn;
    }

    @Override
    public String setPagerName() {
        return "搜索赚";
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        mGrayBgActionBar = null;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DKSearch.userLogout(this); // 在用户退出登录时 将SDK中的登录状态清理一下
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
