package com.zhangku.qukandian.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.base.BaseDialog;
import com.zhangku.qukandian.bean.EventBusBean.ChaiBaoxiangEvent;
import com.zhangku.qukandian.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/27
 * 你不注释一下？
 */
public class DialogNewPeopleTaskForRed extends BaseDialog {

    private String title;
    private int imgID;
    private String gotoLink;

    public DialogNewPeopleTaskForRed(Context context, String title, int imgID) {
        super(context);
        this.title = title;
        this.imgID = imgID;
    }

    @Override
    protected int centerViewId() {
        return R.layout.dialog_new_people_do_task;
    }

    @Override
    protected void initView() {
        TextView titleTV = findViewById(R.id.taskGoReadTitle);
        ImageView contentIV = findViewById(R.id.taskGoReadIV);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置Dialog背景透明效果
        titleTV.setText(title);
        contentIV.setImageResource(imgID);
        findViewById(R.id.taskGoReadTV).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ("如何阅读咨讯".equals(title)) {
                    MobclickAgent.onEvent(mContext, "293-yueduyindao");
                } else if ("如何领取阅读红包".equals(title)) {
                    MobclickAgent.onEvent(mContext, "293-hongbaoyindao");
                }
                if (gotoLink == null) {
                    ActivityUtils.startToMainActivity(v.getContext(), 0, 0);
                } else {
                    String[] url = gotoLink.split("\\|");
                    if (url.length > 2) {
                        ActivityUtils.startToAssignActivity(v.getContext(), url[0], Integer.valueOf(url[1]), Integer.valueOf(url[2]));
                    } else if (url.length > 1) {
                        ActivityUtils.startToAssignActivity(v.getContext(), url[0], Integer.valueOf(url[1]));
                    } else {
                        ActivityUtils.startToAssignActivity(v.getContext(), url[0], -1);
                    }
                    if(title.equals("如何完成热搜")){//热搜
                        EventBus.getDefault().post(new ChaiBaoxiangEvent(true));//跳转热搜页面
                    }
                }
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

    public void setGotoLink(String gotoLink) {
        this.gotoLink = gotoLink;
    }

}
