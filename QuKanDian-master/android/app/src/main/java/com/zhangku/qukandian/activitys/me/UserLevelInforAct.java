package com.zhangku.qukandian.activitys.me;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhangku.qukandian.BaseNew.BaseTitleAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.UserLevelInforAdapter;
import com.zhangku.qukandian.bean.UserLevelInforBean;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetNewExpInforPro;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2019/4/17
 * 你不注释一下？
 */
public class UserLevelInforAct extends BaseTitleAct {

    private UserLevelInforAdapter userLevelInforAdapter;

    @Override
    protected void loadData() {
        GetNewExpInforPro pro = new GetNewExpInforPro(this, new BaseModel.OnResultListener<List<UserLevelInforBean>>() {
            @Override
            public void onResultListener(List<UserLevelInforBean> response) {
                userLevelInforAdapter.setList(response);
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        });
        pro.postRequest();
    }

    @Override
    protected void initViews() {
        SmartRefreshLayout refreshLayout = findViewById(R.id.SmartRefreshLayout);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(false);
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
////                getUserInfo();
//            }
//        });
        RecyclerView recyclerView = findViewById(R.id.RecyclerView);
        userLevelInforAdapter = new UserLevelInforAdapter(this, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(userLevelInforAdapter);
        //底部
        View inflate = LayoutInflater.from(this).inflate(R.layout.foot_view_level, recyclerView,false);
        userLevelInforAdapter.setFooterView(inflate);

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.view_recycleview;
    }

    @Override
    protected String setTitle() {
        return "经验明细";
    }

    @Override
    protected void myOnClick(View v) {

    }
}
