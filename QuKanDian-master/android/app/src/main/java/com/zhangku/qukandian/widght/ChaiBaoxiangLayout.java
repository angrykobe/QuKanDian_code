package com.zhangku.qukandian.widght;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.BoxRuleBean;
import com.zhangku.qukandian.bean.ChestBoxBean;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.EventBusBean.ChaiBaoxiangEvent;
import com.zhangku.qukandian.bean.SougouHotwordBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogChaiBaoXiang;
import com.zhangku.qukandian.dialog.DialogSignBaoXiang;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.GetInfoSougouProtocol;
import com.zhangku.qukandian.protocol.GetNewChestBoxProtocol;
import com.zhangku.qukandian.protocol.GetNewChestBoxRule;
import com.zhangku.qukandian.protocol.PutNewTaskProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.AndroidScheduler;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.CacheManage;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;

import static android.content.Context.ALARM_SERVICE;
import static android.os.SystemClock.uptimeMillis;

/**
 * Created by yuzuoning on 2017/8/25.
 */

public class ChaiBaoxiangLayout extends RelativeLayout implements UserManager.IOnLoginStatusLisnter {
    private LinearLayout mLayoutChaiBtn;
    private LinearLayout mLayoutChaiGaryBtn;
    private TextView mTvTimer;
    private TextView getGoldTV;
    private View mSearch;//搜索
    private PutNewTaskProtocol mSubmitTaskProtocol;
    private boolean isClickChaiBtn = false;
    private Context mContext;
    private GetNewChestBoxRule getNewChestBoxRule;
    private Subscription subscribe;
    private long timeCount;//倒计时剩余总秒
    private DialogChaiBaoXiang chaiBaoXiangDialog;
    private BoxRuleBean mBoxRuleBean;

    private GetInfoSougouProtocol mGetInfoSougouProtocol;
    private RelativeLayout mRlSearch;
    private ImageView mIvSearchCountIcon;//热搜的icon
    private TextView mTvSearchNumber;//热搜的红包数量
    private TextView mTvSearchTip;//热搜的热词
    private TextView mTvTitle;//标题

    public ChaiBaoxiangLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        UserManager.getInst().addLoginListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(mContext).inflate(R.layout.layout_chai_baoxiang_layout, this);
        mLayoutChaiBtn = findViewById(R.id.layout_chai_baoxiang_chai_btn);
        mLayoutChaiGaryBtn = findViewById(R.id.layout_chai_baoxiang_chai_gray_btn);
        mTvTimer = findViewById(R.id.layout_chai_baoxiang_timer);
        mSearch = findViewById(R.id.main_search_layout);
        getGoldTV = findViewById(R.id.getGoldTV);

        EventBus.getDefault().register(this);

        mRlSearch = findViewById(R.id.rl_search);
        mIvSearchCountIcon = findViewById(R.id.view_item_infor_tab_iv);
        mTvSearchNumber = findViewById(R.id.view_item_infor_tab_number_tv);
        mTvSearchTip = findViewById(R.id.tv_search_tip);
        mTvTitle = findViewById(R.id.tv_title);

        getChestBoxNew();
        //宝箱领取点击
        mLayoutChaiBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutChaiBtn.setClickable(false);
                isClickChaiBtn = true;
                MobclickAgent.onEvent(getContext(), "01_01_clickbaoxiangbtn");
                if (UserManager.getInst().hadLogin()) {
                    if (null == mSubmitTaskProtocol && !"领取中".equals(getGoldTV.getText().toString())) {
                        mSubmitTaskProtocol = new PutNewTaskProtocol(getContext(), Constants.CHEST_BOX_MISSION, new BaseModel.OnResultListener<DoneTaskResBean>() {
                            @Override
                            public void onResultListener(DoneTaskResBean response) {
                                mLayoutChaiBtn.setClickable(true);
                                getGoldTV.setText("领取中");
                                //弹框
                                if (mBoxRuleBean != null && mBoxRuleBean.getToastType() == 2) {
                                    //弹框内容为任务
                                    DialogSignBaoXiang mDialogSignBaoXiang = new DialogSignBaoXiang(getContext());
                                    mDialogSignBaoXiang.show();
                                    mDialogSignBaoXiang.setGold("" + response.getGoldAmount());
                                    mDialogSignBaoXiang.getDataForTask(3);
                                } else {
                                    //内容为广告
                                    DialogSignBaoXiang mDialogSignBaoXiang = new DialogSignBaoXiang(getContext());
                                    mDialogSignBaoXiang.show();
                                    mDialogSignBaoXiang.setGold("" + response.getGoldAmount());
                                    mDialogSignBaoXiang.getDataForAd();
                                }
                                //获取宝箱倒计时规则
                                getChestBoxNew();
                                mSubmitTaskProtocol = null;
                            }

                            @Override
                            public void onFailureListener(int code, String error) {
                                mLayoutChaiBtn.setClickable(true);
                                ToastUtils.showShortToast(getContext(), error);
                                getChestBoxNew();
                                mSubmitTaskProtocol = null;
                            }
                        });
                        mSubmitTaskProtocol.postRequest();
                    }
                } else {
                    mLayoutChaiBtn.setClickable(true);
                    ActivityUtils.startToBeforeLogingActivity(getContext());
                }
            }
        });
        //倒计时点击
        mLayoutChaiGaryBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayoutChaiGaryBtn.setClickable(false);
                MobclickAgent.onEvent(getContext(), "01_01_clickbaoxiangbtn");
                if (mBoxRuleBean != null && mBoxRuleBean.getToastType() == 2) {
                    //弹框内容为任务
                    chaiBaoXiangDialog = new DialogChaiBaoXiang(getContext());
                    chaiBaoXiangDialog.show();
                    chaiBaoXiangDialog.getDataForTask();
                    chaiBaoXiangDialog.setTime("" + getTimeFormat(timeCount));
                } else {
                    //内容为广告
                    chaiBaoXiangDialog = new DialogChaiBaoXiang(getContext());
                    chaiBaoXiangDialog.show();
                    chaiBaoXiangDialog.getDataForAd();
                    chaiBaoXiangDialog.setTime("" + getTimeFormat(timeCount));
                }
                mLayoutChaiGaryBtn.setClickable(true);
            }
        });
        //搜索框点击
        mRlSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                MobclickAgent.onEvent(getContext(), "296-resouintrance1");
                MobclickAgent.onEvent(getContext(), "296-resouintrance2");
                String hotWord = mTvSearchTip.getText().toString();
                //未领取的红包数量
                String redNum = "0";
                if (mTvSearchNumber.getVisibility() == View.VISIBLE) {
                    redNum = mTvSearchNumber.getText().toString();
                }
                int rscnt = -1;
                if (UserManager.getInst().hadLogin()) {//已登录
                    rscnt = UserManager.getInst().getQukandianBean().getRscnt();
                }
                ActivityUtils.startToSougouActivity(getContext(), hotWord, redNum, rscnt);
            }
        });

        getSougouHotWord();

    }

    //倒计时转换，2000-01-01-00:00:00为时间戳，把秒数转为HH:mm:ss时间格式。24小时内有效，超出有bug
    private String getTimeFormat(long num) {
        long l = 946656000000l + num * 1000;
        Date date = new Date(l);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String format1 = format.format(date);
        return format1;
    }

    public void getChestBoxNew() {
        if (UserManager.getInst().hadLogin()) {
            //获取宝箱间隔总时间  一些配置信息
            new GetNewChestBoxProtocol(getContext(), new BaseModel.OnResultListener<ChestBoxBean>() {

                @Override
                public void onResultListener(ChestBoxBean response) {
                    mBoxRuleBean = response.getRule();
                    if (response.getEnable()) {
                        mLayoutChaiBtn.setVisibility(View.VISIBLE);
                        mLayoutChaiGaryBtn.setVisibility(View.VISIBLE);
                        long mTime = response.getSecondsLeft();//后台规定红包间隔时间，单位小时
                        if (mTime == 0) {
                            mLayoutChaiBtn.setVisibility(View.VISIBLE);
                            mLayoutChaiGaryBtn.setVisibility(View.GONE);
                            getGoldTV.setText("可领取");
                        } else {
                            startCountTime(mTime);
                            getGoldTV.setText("可领取");
                        }
                    } else {
                        getGoldTV.setText("可领取");
                        mLayoutChaiBtn.setVisibility(View.GONE);
                        mLayoutChaiGaryBtn.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailureListener(int code, String error) {
                    if (getGoldTV != null)
                        getGoldTV.setText("可领取");
                }
            }).postRequest();
        }
    }

    public void getChestBox() {
        if (UserManager.getInst().hadLogin()
                && getNewChestBoxRule == null) {
            //获取宝箱间隔总时间  一些配置信息
            getNewChestBoxRule = new GetNewChestBoxRule(mContext, new BaseModel.OnResultListener<BoxRuleBean>() {

                @Override
                public void onResultListener(BoxRuleBean response) {
                    mBoxRuleBean = response;
                    if (response.isIsEnable()) {
                        mLayoutChaiBtn.setVisibility(View.VISIBLE);
                        mLayoutChaiGaryBtn.setVisibility(View.VISIBLE);
                        int mTime = response.getDuration();//后台规定红包间隔时间，单位小时
                        ChaibaoxiangState(mTime);
                    } else {
                        getGoldTV.setText("可领取");
                        mLayoutChaiBtn.setVisibility(View.GONE);
                        mLayoutChaiGaryBtn.setVisibility(View.GONE);
                    }
                    getNewChestBoxRule = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    if (getGoldTV != null)
                        getGoldTV.setText("可领取");
                    getNewChestBoxRule = null;
                }
            });
            getNewChestBoxRule.postRequest();
        }
    }

    private void ChaibaoxiangState(final int time) {
        new GetNewChestBoxProtocol(getContext(), new BaseModel.OnResultListener<ChestBoxBean>() {
            @Override
            public void onResultListener(final ChestBoxBean response) {
                if (response.getCreationTimeStamp() <= 0 || response.getCurrentTimeStamp() <= 0) {
                    getGoldTV.setText("可领取");
                    return;
                }
                double creationTimeStamp = response.getCreationTimeStamp();
                double currentTimeStamp = response.getCurrentTimeStamp();
                final long timer = (long) (60 * 60 * time - (currentTimeStamp - creationTimeStamp));
                if (timer <= 0) {
                    mLayoutChaiBtn.setVisibility(View.VISIBLE);
                    mLayoutChaiGaryBtn.setVisibility(View.GONE);
                    getGoldTV.setText("可领取");
                } else {
                    startCountTime(timer);
                    getGoldTV.setText("可领取");
                }
            }

            @Override
            public void onFailureListener(int code, String error) {
                getGoldTV.setText("可领取");
            }
        }).postRequest();
    }

    @Override
    public void onLoginStatusListener(boolean state) {
        // 登录之后把点击状态重置
        isClickChaiBtn = false;
        setState(state);
    }

    public void setState(boolean state) {
        if (!state) {//拆宝箱显示
            //退出登陆要执行的步骤
            mLayoutChaiBtn.setVisibility(View.VISIBLE);//显示可领取
            mLayoutChaiGaryBtn.setVisibility(View.GONE);//隐藏不可领取（计时状态）
            //还原一些状态值
            isClickChaiBtn = false;
            //取消订阅
            if (subscribe != null) {
                subscribe.unsubscribe();
                subscribe = null;
            }
        } else {//退出登陆
            if (!isClickChaiBtn) {
                getChestBoxNew();
            }
        }
    }


    public View getmSearch() {
        return mSearch;
    }

    /**
     * 倒计时
     *
     * @param countTime 倒计时时间 单位 秒
     */
    private void startCountTime(final long countTime) {
        //第一个参数：代表两个消息发送之间的间隔时间(轮训时间)
        //第二参数：时间单位：(毫秒，秒，分钟) TimeUtil时间工具类
        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
        subscribe = rx.Observable.interval(1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(countTime + 1, TimeUnit.SECONDS) //设置循环11次
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        timeCount = countTime - aLong;
                        return getTimeFormat(timeCount);
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mLayoutChaiBtn.setVisibility(View.GONE);
                        mLayoutChaiGaryBtn.setVisibility(View.VISIBLE);
                    }
                })
                .subscribeOn(AndroidScheduler.mainThread())
                .observeOn(AndroidScheduler.mainThread())//操作UI主要在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        mLayoutChaiBtn.setVisibility(View.VISIBLE);
                        mLayoutChaiGaryBtn.setVisibility(View.GONE);
                        subscribe = null;
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String format1) { //接受到一条就是会操作一次UI
                        if (chaiBaoXiangDialog != null) chaiBaoXiangDialog.setTime("" + format1);
                        mTvTimer.setText("" + format1);
                    }
                });
    }

    //获取搜狗热词
    private void getSougouHotWord() {
        mGetInfoSougouProtocol = new GetInfoSougouProtocol(getContext(),
                new BaseModel.OnResultListener<ArrayList<SougouHotwordBean>>() {
                    @Override
                    public void onResultListener(ArrayList<SougouHotwordBean> response) {
                        if (null != response) {
                            CacheManage.remove(Constants.SOUGOU_HOTWORD);
                            CacheManage.put(Constants.SOUGOU_HOTWORD, response);
                            setSougouHotWord();
                        }
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                    }

                });
        mGetInfoSougouProtocol.postRequest();
    }

    private List<SougouHotwordBean> hotwordBeans;
    private final int UPDATE_TEXTSWITCHER = 1;

    private void setSougouHotWord() {
        hotwordBeans = CacheManage.get(Constants.SOUGOU_HOTWORD, SougouHotwordBean.class);
        if (hotwordBeans.size() > 0) {
            setAlarm();
        }
    }

    private void setAlarm() {
        Timer timer = new Timer();
        timer.schedule(timerTask, 1, 5000);
    }


    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            Message msg = new Message();
            msg.what = UPDATE_TEXTSWITCHER;
            handler.sendMessage(msg);
        }
    };
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXTSWITCHER:
                    upDataTextView();
                    break;
                default:
                    break;
            }
        }
    };

    private void upDataTextView() {
        Random random = new Random();
        int rand = random.nextInt(hotwordBeans.size());
        if (mTvSearchTip == null) return;
        mTvSearchTip.setText(hotwordBeans.get(rand).getKwd());
    }

    @Subscribe
    public void onEventMainThread(ChaiBaoxiangEvent event) {
        //新手福利页面 设置的自动跳转
        if (event.isOnclick()) {
            mRlSearch.performClick();
        }
    }

    public void setTitleShow(boolean isShow) {
        if (mTvTitle == null) return;
        if (mRlSearch == null) return;
        if (isShow) {
            mTvTitle.setVisibility(VISIBLE);
            mRlSearch.setVisibility(GONE);
        } else {
            mTvTitle.setVisibility(GONE);
            mRlSearch.setVisibility(VISIBLE);
        }

    }
}
