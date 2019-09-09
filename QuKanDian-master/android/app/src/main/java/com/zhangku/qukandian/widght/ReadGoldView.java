package com.zhangku.qukandian.widght;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AnimUtil;
import com.zhangku.qukandian.utils.LayoutParamsUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.TimeUtils;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/17
 * 你不注释一下？
 */
public class ReadGoldView extends LinearLayout implements UserManager.IOnLoginStatusLisnter {
    private Context mContext;
    private ReadGoldProcessView readGoldProcessView;
    private TextView readRemindBtn;
    private TextView readRemindTV;
    private View readRemindView;
    private float mDownY;
    private boolean isLongClick;
    private int mHeight;
    private int mParentHeight;

    public ReadGoldView(Context context) {
        this(context, null);
        mContext = context;
    }

    public ReadGoldView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReadGoldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(mContext).inflate(R.layout.view_read_get_gold, this);

        readRemindView = findViewById(R.id.readRemindView);
        readRemindTV = findViewById(R.id.readRemindTV);
        readRemindBtn = findViewById(R.id.readRemindBtn);
        readGoldProcessView = findViewById(R.id.readGoldProcessView);
        readRemindView.setVisibility(View.INVISIBLE);
        UserManager.getInst().addLoginListener(this);

        if (!UserManager.getInst().hadLogin()) {//无登录
            String nowDate = TimeUtils.formatPhotoDate(System.currentTimeMillis());//获取日期
            String cacheDate = UserSharedPreferences.getInstance().getString(Constants.READ_DETAIL_FOR_LOGIN, "");//获取缓存日期
            if (!nowDate.equals(cacheDate)) {//今天无提示过
                //弹出提示
                showRemind(R.string.read_remindstr_for_login, R.string.read_remindbtn_for_login, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                    readRemindView.setVisibility(View.INVISIBLE);
                        ActivityUtils.startToBeforeLogingActivity(mContext);
                        MobclickAgent.onEvent(mContext, "01_05_02_clicksigninbtn");
                    }
                });
                //覆盖缓存换上今日日期
                UserSharedPreferences.getInstance().putString(Constants.READ_DETAIL_FOR_LOGIN, nowDate);
            }
        }

        //设置阅读时间获取金币
        readGoldProcessView.setmMaxProcess(Integer.valueOf(TextUtils.isEmpty(UserManager.readingDuration) ? "20" : UserManager.readingDuration));
        readGoldProcessView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startToReadProfitActivity(v.getContext());
                MobclickAgent.onEvent(mContext, "01_05_01_clickjishiqi");
            }
        });

        readGoldProcessView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isLongClick = true;
                return true;
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getHeight();
        FrameLayout parent = (FrameLayout) getParent();
        mParentHeight = parent.getHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isLongClick) {
                    float v = ev.getY() - mDownY;//位移差
                    int v1 = (int) (getTop() + v);
                    if(mHeight+getTop() <= mParentHeight || v<0)
                        LayoutParamsUtils.changeAdProcess(this, getLeft(),v1,0,0);
                }
                break;
            case MotionEvent.ACTION_UP:
                isLongClick = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setProcessClick(OnClickListener onClick) {
        readGoldProcessView.setOnClickListener(onClick);
    }

    //设置最大值
    public void setMaxProcess(int maxProcess) {
//        readGoldProcessView.setmMaxProcess(Integer.valueOf(TextUtils.isEmpty(UserManager.readingDuration) ? "20" : UserManager.readingDuration));
        readGoldProcessView.setmMaxProcess(maxProcess);
    }

    //隐藏提示框框
    public void hideRemind() {
        readRemindView.setVisibility(View.INVISIBLE);
    }

    //隐藏提示框框(带动画)
    public void hideRemindWithAnim() {
        AnimUtil.animAlpha(readRemindView);
    }

    //显示提示框
    public void showRemind(String remindStr, String btnStr, View.OnClickListener listener) {
        readRemindView.setVisibility(View.VISIBLE);
        readRemindTV.setText(remindStr);
        if (TextUtils.isEmpty(btnStr)) {
            readRemindBtn.setVisibility(View.GONE);
        } else {
            readRemindBtn.setVisibility(View.VISIBLE);
            readRemindBtn.setText(btnStr);
            readRemindBtn.setOnClickListener(listener);
        }
    }

    public void showRemind(Spanned remindStr, String btnStr, View.OnClickListener listener) {
        readRemindView.setVisibility(View.VISIBLE);
        readRemindTV.setText(remindStr);
        if (TextUtils.isEmpty(btnStr)) {
            readRemindBtn.setVisibility(View.GONE);
        } else {
            readRemindBtn.setVisibility(View.VISIBLE);
            readRemindBtn.setText(btnStr);
            readRemindBtn.setOnClickListener(listener);
        }
    }

    public void showRemind(int remindStrID, int btnStrID, View.OnClickListener listener) {
        showRemind(mContext.getString(remindStrID), mContext.getString(btnStrID), listener);
    }

    public void showRemind(int remindStrID, int btnStrID) {
        showRemind(mContext.getString(remindStrID), mContext.getString(btnStrID));
    }

    public void showRemind(String remindStr, String btnStr) {
        showRemind(remindStr, btnStr, new OnClickListener() {
            @Override
            public void onClick(View v) {
                readRemindView.setVisibility(View.INVISIBLE);
            }
        });
    }

    //
    public void setTouchListener(ReadGoldProcessView.OnTouchViewListen listener) {
        readGoldProcessView.setListener(listener);
    }

    //开始计时
    public void startTiming(int pos) {
        readGoldProcessView.run(pos);
    }

    //广告红包计时逻辑
    public void startTimingForAdRed() {

        readGoldProcessView.runForAdRed();
    }
    //293 转圈计时逻辑
    public void startTimingFor293() {
        readGoldProcessView.runForProcess();
    }

    public int getReadTime() {
        return readGoldProcessView.getReadTime();
    }

    //停止转圈圈
    public void setStopRun(boolean isStopRun) {
        readGoldProcessView.setStopRun(isStopRun);
    }

    public boolean isShowing() {
        return readRemindView.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onLoginStatusListener(boolean state) {
        if (state)
            hideRemind();
    }

    public ReadGoldProcessView getReadGoldProcessView() {
        return readGoldProcessView;
    }

    public void setDestory() {
        this.setVisibility(View.GONE);
        readGoldProcessView.setDestory();
        UserManager.getInst().removeLoginListener(this);
    }
}
