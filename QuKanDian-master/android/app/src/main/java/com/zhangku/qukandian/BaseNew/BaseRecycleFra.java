package com.zhangku.qukandian.BaseNew;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseRecyclerViewAdapter;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.utils.CommonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/21
 * 你不注释一下？
 */
public abstract class BaseRecycleFra<T> extends BaseFra implements BaseModel.OnResultListener<List<T>> {
    protected int currentPage = 1;
    protected BaseRecyclerViewAdapter mAdapter;
    protected List<T> mList = new ArrayList<>();
    private SmartRefreshLayout refreshLayout;
    protected TextView errorTV;
    protected ImageView errorImg;
    private boolean isRefresh = true;
    protected RecyclerView mRecyclerView;

    @Override
    protected void loadData(Context context) {
        if(CommonHelper.isNetworkAvalible(context)){
            loadData(currentPage);
        }else{//无网络，且加载第一页，显示网络出错
            showErrorConn();
            refreshLayout.finishRefresh(true);//关闭刷新
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.view_recycleview;
    }

    @Override
    protected void initViews(View convertView) {
        mAdapter = setAdapter(mList);
        mRecyclerView = convertView.findViewById(R.id.RecyclerView);
        refreshLayout = convertView.findViewById(R.id.SmartRefreshLayout);
        errorTV = convertView.findViewById(R.id.errorTV);
        errorImg = convertView.findViewById(R.id.errorIV);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                currentPage = 1;
                loadData(mContext);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if(mRecyclerView.getVisibility() != View.GONE){//网络异常、无数据等异常情况禁止上啦加载更多
                    isRefresh = false;
                    loadData(currentPage+1);
                }else{
                    refreshLayout.finishLoadMore(false);//传入false表示刷新失败
                }
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        initUI(convertView);
    }

    @Override
    public void onResultListener(List<T> response) {
        refreshLayout.finishRefresh(true);//传入false表示刷新失败
        refreshLayout.finishLoadMore(true);//传入false表示加载失败
        errorTV.setVisibility(View.GONE);
        errorImg.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        if(isRefresh){
            //清空数据
            currentPage=1;
            mAdapter.getList().clear();
            if(response.size() == 0){
                //无数据
                showNoData();
            }
        }else{
            currentPage++;
        }
        mAdapter.addList(response);
        onResultSuccess(response);
    }

    @Override
    public void onFailureListener(int code, String error) {
        refreshLayout.finishLoadMore(false);//传入false表示加载失败
        refreshLayout.finishRefresh(false);//传入false表示刷新失败
        if(currentPage == 1){//请求第一条数据且有错误展示错误
            showErrorData();
        }
    }
    //设置不可刷新，加载更多
    protected void disableAll(){
        refreshLayout.setEnabled(false);
    }
    //设置不可刷新
    protected void disableRefresh(){
        refreshLayout.setEnableRefresh(false);
    }
    //设置不可 加载更多
    protected void disableLoadMore(){
        refreshLayout.setEnableLoadMore(false);
    }
    //暂无数据展示
    protected void showNoData(){
        errorTV.setVisibility(View.VISIBLE);
        errorImg.setVisibility(View.VISIBLE);
        errorImg.setImageResource(R.mipmap.empty_search);
        errorTV.setText("暂无数据~");
        mRecyclerView.setVisibility(View.GONE);
    }
    //请求出错展示
    protected void showErrorData(){
        errorTV.setVisibility(View.VISIBLE);
        errorImg.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        errorImg.setImageResource(R.mipmap.error_view);// 请求出错
        errorTV.setText("服务器出错啦~");
    }
    //网络有问题
    protected void showErrorConn(){
        errorTV.setVisibility(View.VISIBLE);
        errorImg.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);

        errorImg.setImageResource(R.mipmap.empty_netword);// 请求出错
        errorTV.setText("网络出错啦~");
    }




    public abstract BaseRecyclerViewAdapter setAdapter(List<T> mList);
    public abstract void loadData(int currentPage);
    public abstract void initUI(View view);
    public abstract void onResultSuccess(List<T> response);//数据加载成功
}
