package com.zhangku.qukandian.activitys.me;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangku.qukandian.BaseNew.BaseTitleAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.DownAppDetailAdapter;
import com.zhangku.qukandian.bean.DownAppBean;
import com.zhangku.qukandian.dialog.DialogGetGoldReadyDown;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.DownHelper;
import com.zhangku.qukandian.utils.GlideUtils;

/**
 * 创建者          xuzhida
 * 创建日期        2018/9/11
 * 你不注释一下？
 */
public class DownAppDetailAct extends BaseTitleAct implements DialogGetGoldReadyDown.OnGetGoldListener {

    private RecyclerView mRecyclerView;
    private TextView mStartTaskTV;
    private DialogGetGoldReadyDown dialogGetGoldReadyDown;
    private DownAppBean mBean;

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews() {
        mBean = (DownAppBean) getIntent().getSerializableExtra("bean");
        if(mBean.getStage() == DownAppBean.APP_DOWN_START){
            mBean.setStage(DownAppBean.APP_NULL);
        }

        mRecyclerView = findViewById(R.id.recyclerview);
        mStartTaskTV = findViewById(R.id.startTaskTV);
        mStartTaskTV.setOnClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DownAppDetailAdapter mAdapter = new DownAppDetailAdapter(this, mBean.getBountyTaskSteps());
        mRecyclerView.setAdapter(mAdapter);

        View headView = LayoutInflater.from(this).inflate(R.layout.head_down_app_detail, mRecyclerView, false);
        mAdapter.setHeaderView(headView);
        TextView appDecTV = (TextView) headView.findViewById(R.id.appDecTV);
        TextView appInforTV = (TextView) headView.findViewById(R.id.appInforTV);
        TextView appNameTV = (TextView) headView.findViewById(R.id.appNameTV);
        ImageView appImg = (ImageView) headView.findViewById(R.id.appImg);

        appNameTV.setText("" + mBean.getAppName());
        appInforTV.setText("" + mBean.getDescription());
        appDecTV.setText("" + mBean.getTaskExplain());
        GlideUtils.displayRoundImage(this, mBean.getLogoImgSrc(), appImg, 10);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_down_app_detail;
    }

    @Override
    protected String setTitle() {
        return "赚赏金";
    }

    @Override
    protected void myOnClick(View v) {
        if (dialogGetGoldReadyDown == null) {
            dialogGetGoldReadyDown = new DialogGetGoldReadyDown(this, mBean);
            dialogGetGoldReadyDown.setListener(this);
        }
        dialogGetGoldReadyDown.show();
        dialogGetGoldReadyDown.updateView(mBean);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBean.getStage() == DownAppBean.APP_DOWN_END && CommonHelper.isAppInstalled(mBean.getAppPackage())) {
            mBean.setStage(DownAppBean.APP_INSTALL);
            DBTools.updateDownAppBean(mBean);
            if (dialogGetGoldReadyDown != null)
                dialogGetGoldReadyDown.updateView(mBean);
        } else if (mBean.getStage() == DownAppBean.APP_PLAY && mBean.getStartTime() > 10) {//试玩时间异常不执行计时操作
            long nowPlayTime = System.currentTimeMillis() - mBean.getStartTime();
            long allPlayTime = mBean.getPlayTime() + nowPlayTime;
            mBean.setPlayTime(allPlayTime);
            DBTools.updateDownAppBean(mBean);
            if (dialogGetGoldReadyDown != null)
                dialogGetGoldReadyDown.updateView(mBean);
        }
    }

    //任务状态改变监听
    @Override
    public void onGetGoldListener(DownAppBean bean) {
        switch (bean.getStage()) {
            case DownAppBean.APP_DOWN_START:
            case DownAppBean.APP_DOWN_END:
            case DownAppBean.APP_NULL:
                initButton();
                break;
            case DownAppBean.APP_DONE:
                initButton();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownHelper.getInstance().remove(mBean.getDownId());
    }

    private void initButton() {

//        //-1 无操作 0 开始下载 1、下载完成 2、安装完成（未打开app） 3、试玩中(打开了app) 4 任务完成
//        switch (mBean.getStage()) {
//            case 0:
//            case 1:
//            case 2:
//            case 3:
//            case 4:
//                mStartTaskTV.setText("进行中...");
//                break;
//            default:
//                mStartTaskTV.setText("开始任务");
//                break;
//        }
    }
}
