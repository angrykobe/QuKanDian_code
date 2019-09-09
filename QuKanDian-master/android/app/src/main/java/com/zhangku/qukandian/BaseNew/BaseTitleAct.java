package com.zhangku.qukandian.BaseNew;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangku.qukandian.R;

import java.io.Serializable;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/20
 */
public abstract class BaseTitleAct extends BaseAct {

    private View mContentView;
    private ImageView mErrorImg;
    private TextView mErrorTV;
    protected TextView mSubTitleRightTV;
    protected TextView titleTV;

    @Override
    protected void setConView() {
        setContentView(R.layout.ac_base_title);

        findViewById(R.id.baseBackIV).setOnClickListener(this);
        titleTV = findViewById(R.id.baseTitleTV);
        titleTV.setText(""+setTitle());

        LinearLayout lLayout = findViewById(R.id.baseLLayout);
        mErrorTV = findViewById(R.id.errorTV);
        mSubTitleRightTV = findViewById(R.id.baseSubTitleRightTV);
        mErrorImg = findViewById(R.id.errorImg);
        mContentView = LayoutInflater.from(this).inflate(getLayoutRes(), lLayout, false);
        lLayout.addView(mContentView);
        View StatusBar = findViewById(R.id.StatusBar);
        setStatusView(this,StatusBar);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.baseBackIV){
            finish();
        }
        super.onClick(v);
    }

    //暂无数据展示
    protected void showNoData(){
        mErrorTV.setVisibility(View.VISIBLE);
        mErrorImg.setVisibility(View.VISIBLE);
        mErrorImg.setImageResource(R.mipmap.empty_search);
        mErrorTV.setText("暂无数据~");
        mContentView.setVisibility(View.GONE);
    }
    //请求出错展示
    protected void showErrorData(){
        mErrorTV.setVisibility(View.VISIBLE);
        mErrorImg.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);

        mErrorImg.setImageResource(R.mipmap.error_view);// 请求出错
        mErrorTV.setText("服务器出错啦~");
    }
    //网络有问题
    protected void showErrorConn(){
        mErrorTV.setVisibility(View.VISIBLE);
        mErrorImg.setVisibility(View.VISIBLE);
        mContentView.setVisibility(View.GONE);

        mErrorImg.setImageResource(R.mipmap.empty_netword);// 请求出错
        mErrorTV.setText("网络出错啦~");
    }

}
