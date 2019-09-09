package com.zhangku.qukandian.activitys.ad;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.biz.adbeen.wangmai.WangMaiResBean;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.GlideUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/18
 * 你不注释一下？
 */
public class VideoAdEndCardAct  extends BaseAct {

    @Override
    protected void loadData() {

    }

    @Override
    protected void initViews() {
        ImageView bgIV = findViewById(R.id.bgIV);
        ImageView iconIV = findViewById(R.id.iconIV);
        TextView titleTV = findViewById(R.id.titleTV);
        TextView ratingBarTV = findViewById(R.id.ratingBarTV);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        TextView subtitleTV = findViewById(R.id.subtitleTV);
        TextView downTV = findViewById(R.id.downTV);


        final WangMaiResBean.WxadBean.VideoBean.ExtBean bean = (WangMaiResBean.WxadBean.VideoBean.ExtBean) getIntent().getSerializableExtra("bean");
        String imgUrl1 = getIntent().getStringExtra("imgUrl");
        GlideUtils.displayRoundImage(this,bean.getEndiconurl(),iconIV,5);
        GlideUtils.displayImageForBg(this,imgUrl1,bgIV);
        titleTV.setText(""+bean.getEndtitle());
        subtitleTV.setText(""+bean.getEnddesc());
        ratingBar.setMax(10);
        ratingBar.setProgress(bean.getEndrating());
        ratingBarTV.setText(""+bean.getEndrating());

        downTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtils.startToVideoAdEndCardAct(VideoAdEndCardAct.this,"","");
                EventBus.getDefault().post(bean);
            }
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_video_end_card;
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void myOnClick(View v) {

    }
}
