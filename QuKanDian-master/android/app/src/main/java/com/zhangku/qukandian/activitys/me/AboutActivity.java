package com.zhangku.qukandian.activitys.me;

import android.support.v4.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseTitleActivity;
import com.zhangku.qukandian.bean.InitUserBean;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.AdsuserProtocol;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.FileBuildForBugUtils;
import com.zhangku.qukandian.widght.GrayBgActionBar;

import java.util.Map;

/**
 * Created by yuzuoning on 2017/4/18.
 */

public class AboutActivity extends BaseTitleActivity {
    private GrayBgActionBar mGrayBgActionBar;
    private TextView mTvName;
    private ImageView mImageView;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, String> map = new ArrayMap<>();
        map.put("to", "关于趣看视界");
        MobclickAgent.onEvent(this, "AllPv", map);
    }

    @Override
    protected void initViews() {
        mGrayBgActionBar = (GrayBgActionBar) findViewById(R.id.gray_actionbar_layout);

        mImageView = findViewById(R.id.about_icon);
        mGrayBgActionBar.setTvTitle("关于趣看视界");
        mTvName = (TextView) findViewById(R.id.about_name);
        mTvName.setText("趣看视界" + CommonHelper.getVersionName(this));
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileBuildForBugUtils.Log(QuKanDianApplication.mUmen + ""+QuKanDianApplication.getCode());

            }
        });
        new AdsuserProtocol(this, new InitUserBean(UserManager.getInst().getUserBeam().getId(),2,3), new BaseModel.OnResultListener<Boolean>() {
            @Override
            public void onResultListener(Boolean response) {
                if (response) {
                    UserManager.getInst().getUserBeam().setUserAdsType(2);
                }
            }

            @Override
            public void onFailureListener(int code, String error) {

            }
        }).postRequest();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_about_layout;
    }

    @Override
    public String setPagerName() {
        return "关于趣看视界";
    }
}
