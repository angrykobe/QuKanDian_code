package com.zhangku.qukandian.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/27
 * 新手红包用户点击去逛逛后的弹框
 */
public class DialogNewPeopleRefuse extends BaseDialog {

    public DialogNewPeopleRefuse(Context context) {
        super(context);
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_new_people_refuse;
    }

    @Override
    protected void initView() {
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        findViewById(R.id.doneOtherTaskBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserManager.getInst().hadLogin()){
                    ActivityUtils.startToMainActivity(getContext(), 2, 0);
                }else{
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
                MobclickAgent.onEvent(mContext, "293-yindaogo");
                dismiss();
            }
        });
        findViewById(R.id.cancleTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(mContext, "293-yindaojujue");
                dismiss();
            }
        });
    }

    @Override
    protected void release() {

    }

    @Override
    protected void setPosition() {

    }
}
