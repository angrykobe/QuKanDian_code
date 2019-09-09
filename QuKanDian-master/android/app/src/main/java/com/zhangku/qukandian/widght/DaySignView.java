package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adapter.SignAdapter;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.EventBusBean.DaySignViewEvent;
import com.zhangku.qukandian.bean.EveryDaySignBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.CheckinSignMissionProtocol;
import com.zhangku.qukandian.protocol.GetNewTaskInfoForSignEveryProtocol;
import com.zhangku.qukandian.protocol.PutNewSubmitTaskForSignEveryProtocol;
import com.zhangku.qukandian.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/12
 * 签到控件
 */
public class DaySignView extends FrameLayout implements UserManager.IOnLoginStatusLisnter {

    private final Context mContext;
    private TextView titleTV;
    private TextView descTV;
    private TextView signTV;
    private TextView continuityTV;
    private RecyclerView recyclerview;
    private SignAdapter mAdapter;
    private int mDay;

    public DaySignView(@NonNull Context context) {
        this(context, null);
    }

    public DaySignView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DaySignView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        UserManager.getInst().addLoginListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.view_day_sign, this);
        EventBus.getDefault().register(this);//注册
        titleTV = findViewById(R.id.titleTV);
        descTV = findViewById(R.id.descTV);
        signTV = findViewById(R.id.signTV);
        continuityTV = findViewById(R.id.continuityTV);
        recyclerview = findViewById(R.id.recyclerview);
        mAdapter = new SignAdapter();

        recyclerview.setLayoutManager(new GridLayoutManager(mContext, 7));
        recyclerview.setAdapter(mAdapter);

        getSignDaysNumber();
        signTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signTV.isSelected()) return;

                MobclickAgent.onEvent(getContext(), "03_02_cllicksignbtn");
//                setSign(true);
                signTV.setSelected(true);
                new PutNewSubmitTaskForSignEveryProtocol(getContext(), new BaseModel.OnResultListener<DoneTaskResBean>() {
                    @Override
                    public void onResultListener(DoneTaskResBean response) {
                        setSign(true);
                        mDay += 1;
                        continuityTV.setText(Html.fromHtml(mContext.getString(R.string.sign_for_every, mDay)));
                        mAdapter.setSignDay(mDay);
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        setSign(false);
                        ToastUtils.showLongToast(getContext(), error);
                        if ("今天你已经签到".equals(error)) {
                            getSignDaysNumber();
                        }
                    }
                }).postRequest();
            }
        });
        descTV.setText("" + UserManager.getInst().getQukandianBean().getCheckindesc());
    }


    public void getSignDaysNumber() {
        if (!UserManager.getInst().hadLogin()) return;
        new CheckinSignMissionProtocol(getContext(), 7, new BaseModel.OnResultListener<Integer>() {
            @Override
            public void onResultListener(Integer day) {
                mDay = day;

                new GetNewTaskInfoForSignEveryProtocol(mContext, Constants.SIGN_CONTNUED, new BaseModel.OnResultListener<EveryDaySignBean>() {
                    @Override
                    public void onResultListener(EveryDaySignBean response) {
                        setSign(response.isIsFinished());//是否已经签到
                        List<EveryDaySignBean.MissionRulesBean> missionRules = response.getMissionRules();
//                        if (response != null && missionRules.size() > 1) {
//                            int num = missionRules.get(1).getMinGoldAmount() - missionRules.get(0).getMinGoldAmount();
//                            descTV.setText("每日签到领金币，连续签到+" + num);
//                        }
                        continuityTV.setText(Html.fromHtml(mContext.getString(R.string.sign_for_every, mDay)));
                        mAdapter.setDate(mDay > 6 ? 6 : mDay, missionRules);
                    }

                    @Override
                    public void onFailureListener(int code, String error) {

                    }
                }).postRequest();

            }

            @Override
            public void onFailureListener(int code, String error) {
            }
        }).postRequest();
    }

    private void setSign(boolean isSign) {
        if (isSign) {
            signTV.setSelected(true);
            signTV.setText("已签到");
        } else {
            signTV.setSelected(false);
            signTV.setText("签到");
        }
    }


    @Override
    public void onLoginStatusListener(boolean state) {
        if (state) getSignDaysNumber();
    }


//    @Override
//    public void destroyDrawingCache() {
//        super.destroyDrawingCache();
//        EventBus.getDefault().unregister(this);//解除注册
//    }

    @Subscribe
    public void onEventMainThread(DaySignViewEvent msg) {
        if (msg == null) return;
        if (msg.isSignSuccess()) {//新手福利页面签到成功
            getSignDaysNumber();
        }
    }
}
