package com.zhangku.qukandian.activitys.ad;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.JsonSyntaxException;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.BaseNew.BaseTitleAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.TaskWebViewAct;
import com.zhangku.qukandian.activitys.UrlWebViewActivity;
import com.zhangku.qukandian.adCommon.natives.AdZhiZuNative;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.AdMissionBean;
import com.zhangku.qukandian.bean.AdResultBean;
import com.zhangku.qukandian.bean.AdsClickParam;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.EventBusBean.LuckTurntableEvent;
import com.zhangku.qukandian.bean.EventBusBean.ShowNewPeopleTaskDialogBean;
import com.zhangku.qukandian.bean.EventBusBean.SougouEvent;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.bean.ResouRuleBean;
import com.zhangku.qukandian.bean.SougouCacheBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogNewPeopleDoneTask;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PutNewAdClickProtocol;
import com.zhangku.qukandian.protocol.PutNewSougouTaskProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.GsonUtil;
import com.zhangku.qukandian.utils.SetWebSettings;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.ReadGoldProcessView;
import com.zhangku.qukandian.widght.ReadGoldView;
import com.zhangku.qukandian.widght.X5WebView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 创建者          xuzhida
 * 创建日期        2018/10/9
 * 广告点击h5页面
 */
public class ResouAct extends BaseTitleAct {
    private X5WebView mWebView;
    private ProgressBar webprogressBar;
    private ReadGoldView readGoldView;

    private String mUrl = "";
    private PutNewSougouTaskProtocol mPutAdMissionProtocol;//普通任务接口，目前是惠头条和搜狗
    private long lastScollTime = 0;//手指滑动的时间
    private int clickNum = 0;
    private List<Integer> timeList = new ArrayList<>();
    private boolean hadClickMoveInfor = false;
    private boolean hadClickInfor = false;
    private boolean isShowGuideInfor = true;

    private String mUrlType = "";//1:幸运转盘进来的   其他：默认 热搜进来的
    private AdsClickParam mAdsClickParam;
    private AdLocationBeans.AdLocationsBean.ClientAdvertisesBean mBean;
    private PutNewAdClickProtocol mSubmitAdClickProtocol;//广告点击获取金币

    @Override
    protected void loadData() {
    }

    @Override
    protected void initViews() {
        mUrl = getIntent().getExtras().getString("url");
        mUrlType = getIntent().getExtras().getString("urlType");
        String beanJson = getIntent().getExtras().getString("beanJson");

        webprogressBar = findViewById(R.id.webprogressBar);
        mWebView = findViewById(R.id.url_webview);

        SetWebSettings.setWebviewForAd(this, mWebView, webprogressBar);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                clickNum++;
                if ("http".equals(url.substring(0, 4)) && !url.contains(".apk")) {
                    view.loadUrl(url);
                } else {
                    CommonHelper.openDeeplink(ResouAct.this, url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        mWebView.loadUrl(mUrl);

        readGoldView = findViewById(R.id.ReadGoldView);
        readGoldView.setProcessClick(null);//
        readGoldView.setVisibility(View.GONE);

        try {
            if (TextUtils.isEmpty(mUrlType)) {//不是幸运红包进来的 ----热搜进来的
                List<ResouRuleBean> resouRuleBeans = GsonUtil.fromJsonForList(beanJson, ResouRuleBean.class);

                int rand = new Random().nextInt(100);
                ResouRuleBean nowResouRuleBean;
                int allTime = 0;
                for (ResouRuleBean bean1 : resouRuleBeans) {
                    if (rand > bean1.getRate()) {
                        rand -= bean1.getRate();
                    } else {
                        nowResouRuleBean = bean1;
                        String[] split = nowResouRuleBean.getStayTimeStr().split("/");
                        for (int i = 0; i < split.length; i++) {
                            try {
                                String s = split[i];
                                String[] split1 = s.split(",");
                                int i2 = Integer.valueOf(split1[1]) - Integer.valueOf(split1[0]);
                                int i1 = new Random().nextInt(i2) + Integer.valueOf(split1[0]);
                                allTime += i1;
                                timeList.add(allTime);
                            } catch (Exception e) {
                                ToastUtils.showLongToast(this, "出错");
                                allTime += 10;
                                timeList.add(10);
                            }
                        }
                        break;
                    }
                }

                //            if (SougouCacheBean.isShowSougouAd() ) { //广告红包一天最大数
                if (UserManager.getInst().getQukandianBean().getRscnt() > 0 || resouRuleBeans.get(0).getType() == 1) {//get(0).getType() 幸运转盘进来的
                    readGoldView.setVisibility(View.VISIBLE);
                    readGoldView.setMaxProcess(allTime == 0 ? 20 : allTime);
                    readGoldView.startTimingFor293();
                    lastScollTime = System.currentTimeMillis();
                    readGoldView.setTouchListener(touchViewListenForAdClick);
                } else {
                    readGoldView.setVisibility(View.GONE);
                }
            }


            int hisCount = UserSharedPreferences.getInstance().getInt(Constants.READ_RESOU_COUNT, 0);
            int firstInTimeRead = UserManager.getInst().getmRuleBean().getAdsClickInductionRule().getFirstInTimeRead();
            isShowGuideInfor = firstInTimeRead >= hisCount;

            if (TextUtils.isEmpty(mUrlType)) {//不是幸运红包进来的 ----热搜进来的
                return;
            }
            if (mUrlType.equals("1")) {//幸运红包进来的
                String adjson = getIntent().getExtras().getString("adjson");
                if (TextUtils.isEmpty(adjson)) {
                    return;
                }
                mBean = GsonUtil.fromJson2(adjson, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.class);

                if (null != mBean) {
                    mAdsClickParam = new AdsClickParam();
                    mAdsClickParam.setAdsBagType(mBean.isIsSecondClickGoldEnable() ? 1 : 0);
                    mAdsClickParam.setAdLocId(mBean.getAdLocId());
                    mAdsClickParam.setAdvertiserId(mBean.getAdverId());
                    mAdsClickParam.setAdType(mBean.getAdType());
                    mAdsClickParam.setAdvertiserName(mBean.getAdverName());
                    mAdsClickParam.setAmount(mBean.getClickGold());
                    mAdsClickParam.setAdLink(mBean.getAdLink());
                    mAdsClickParam.setReferId(mBean.getReferId());
                    mAdsClickParam.setAdPageType(mBean.getAdPageType());

                    List<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.AdClRulesBean> adRuleList = mBean.getAdverClickRules();

                    int rand = new Random().nextInt(100);
                    if (AdZhiZuNative.getRedState(mBean) != 0) {
                        AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.AdClRulesBean nowAdClRulesBean;
                        int allTime = 0;
                        for (AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.AdClRulesBean bean1 : adRuleList) {
                            if (rand > bean1.getPresentRate()) {
                                rand -= bean1.getPresentRate();
                            } else {
                                nowAdClRulesBean = bean1;
                                String[] split = nowAdClRulesBean.getDuration().split("/");
                                for (int i = 0; i < split.length; i++) {
                                    try {
                                        String s = split[i];
                                        String[] split1 = s.split(",");
                                        int i2 = Integer.valueOf(split1[1]) - Integer.valueOf(split1[0]);
                                        int i1 = 0;
                                        if (i2 > 0) {
                                            i1 = new Random().nextInt(i2) + Integer.valueOf(split1[0]);
                                        } else {
                                            i1 = Integer.valueOf(split1[1]);
                                        }
                                        allTime += i1;
                                        timeList.add(allTime);
                                    } catch (Exception e) {
                                        ToastUtils.showLongToast(this, "出错");
                                        allTime += 10;
                                        timeList.add(10);
                                    }
                                }
                                break;
                            }
                        }

                        readGoldView.setVisibility(View.VISIBLE);
                        readGoldView.setMaxProcess(allTime == 0 ? 20 : allTime);
                        readGoldView.startTimingFor293();
                        lastScollTime = System.currentTimeMillis();
                        readGoldView.setTouchListener(touchViewListenForAdClick);
                    } else {
                        readGoldView.setVisibility(View.GONE);
                    }
                }
            }
        } catch (JsonSyntaxException e) {
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_webview_for_red;
    }

    @Override
    protected String setTitle() {
        return "";
    }

    @Override
    protected void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.titleBackIV:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.titleCloseTV:
                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                finish();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        lastScollTime = System.currentTimeMillis();
        return super.dispatchTouchEvent(ev);
    }

    private ReadGoldProcessView.OnTouchViewListen touchViewListenForAdClick = new ReadGoldProcessView.OnTouchViewListen() {
        @Override
        public boolean onTouchView(int position) {
            switch (position) {
                case -1:
                    break;
                case 1:
                    if (TextUtils.isEmpty(mUrlType)) {//热搜进来的
                        requestSougoGold();
                    } else if (mUrlType.equals("1")) {//幸运转盘进来的
                        requestAdGold();
                    }

                    return true;
            }
            int i = readGoldView.getReadGoldProcessView().getmCurProcess();
            if (i % 1000 == 0) {
                int i1 = i / 1000;
                int i2 = timeList.indexOf(i1) + 1;
                if (timeList.contains(i1)) {
                    if (clickNum >= i2) {
                        return true;
                    } else {
                        if (!hadClickInfor && isShowGuideInfor)
                            readGoldView.showRemind("请点击任意感兴趣的内容进行阅读，即可获得金币", "知道了", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    readGoldView.hideRemind();//
                                    hadClickInfor = true;
                                }
                            });//
                        return false;
                    }
                }
            }
            long nowTime = System.currentTimeMillis();
            if (nowTime - lastScollTime >= 8000) {
                if (!hadClickMoveInfor && isShowGuideInfor) {
                    readGoldView.showRemind("请上下滑动浏览文章才可获得金币哦~", "知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hadClickMoveInfor = true;
                        }
                    });//
                }
                return false;
            } else {
                readGoldView.hideRemind();//
                return true;
            }
        }

        @Override
        public void stayTimeTooLong() {
        }
    };

    //搜狗广告红包获取金币
    private void requestSougoGold() {
        if (null == mPutAdMissionProtocol && !mUrl.equals(AdsRecordUtils.getInstance().getString(Constants.SOUGOU_RECORD_URL, ""))) {
            //普通任务接口，目前是惠头条和搜狗
            mPutAdMissionProtocol = new PutNewSougouTaskProtocol(ResouAct.this,
                    new AdMissionBean(Constants.MISSION_TYPE_SOUGOU, ResouAct.this), new BaseModel.OnResultListener<DoneTaskResBean>() {
                @Override
                public void onResultListener(DoneTaskResBean response) {
                    int anInt = UserSharedPreferences.getInstance().getInt(Constants.READ_RESOU_COUNT, 0);
                    UserSharedPreferences.getInstance().putInt(Constants.READ_RESOU_COUNT, anInt + 1);
                    final int goldAmount = response.getGoldAmount();
                    if (goldAmount != 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CustomToast.showToastOther(ResouAct.this, goldAmount + "", "任务奖励");


                                EventBus.getDefault().post(new SougouEvent(1));//热搜红包数量变化
                            }
                        });
                    } else {
                        ToastUtils.showLongToast(ResouAct.this, "您的运气不好，该任务金币已被抢光");
                    }
                    UserManager.getInst().goldChangeNofity(goldAmount);
                    SougouCacheBean.addSougouAdCache();
                    AdsRecordUtils.getInstance().putString(Constants.SOUGOU_RECORD_URL, mUrl);
                    mPutAdMissionProtocol = null;

                    if ("first_resou".equals(response.getMsg())) {
                        EventBus.getDefault().post(new NewPeopleTaskBean());
                        finish();
                    } else if ("finish".equals(response.getMsg())) {
//                        new DialogNewPeopleTask(ResouAct.this,null,true).show();
                        EventBus.getDefault().post(new ShowNewPeopleTaskDialogBean());
                        ActivityUtils.startToMainActivity(ResouAct.this, 2, 0);
                        finish();
                    }
                }


                @Override
                public void onFailureListener(int code, String error) {
                    mPutAdMissionProtocol = null;
                    readGoldView.showRemind(error, "知道了");
                }
            });
            mPutAdMissionProtocol.postRequest();
        } else if (mUrl.equals(AdsRecordUtils.getInstance().getString(Constants.SOUGOU_RECORD_URL, ""))) {
            readGoldView.showRemind("已经领过该奖励~", "知道了");
        }
    }

    //广告红包获取金币
    private void requestAdGold() {
        if (null == mSubmitAdClickProtocol
                && null != UserManager.getInst().getUserBeam().getGoldAccount()
        ) {
            mSubmitAdClickProtocol = new PutNewAdClickProtocol(this, mAdsClickParam, new BaseModel.OnResultListener<AdResultBean>() {
                @Override
                public void onResultListener(final AdResultBean response) {
                    int anInt = UserSharedPreferences.getInstance().getInt(Constants.READ_ART_COUNT, 0);
                    UserSharedPreferences.getInstance().putInt(Constants.READ_ART_COUNT, anInt + 1);
                    // 获取金币结果
                    // DBTools.saveMyselfAdBean(mBean);
                    // PutNewAdClickProtocol.removeGoldAd(mBean);
                    AdUtil.mAdsCache.add(mBean.toString());
                    if ("first".equals(response.getDescription())) {
//                        new DialogNewPeopleTask(AdWebViewAct.this,null,true).show();
                        MobclickAgent.onEvent(QuKanDianApplication.getmContext(), "294-finishhongbao");
                        new DialogNewPeopleDoneTask(ResouAct.this, "" + response.getGoldAmount()).show();
                        UserManager.getInst().goldChangeNofity(response.getGoldAmount());
                        EventBus.getDefault().post(new LuckTurntableEvent(response.getGoldAmount()));//幸运宝箱 金币变化前端自己获取，默认传1
                    } else if (!"0".equals(response.getDescription())) {
                        if (response.getGoldAmount() == 0) {//红包获取失败
                            readGoldView.showRemind(UserManager.getInst().getmRuleBean().getAdsClickInductionRule().getScAdsGotGoldFailedTip(), "知道了");
                        } else if (response.getGoldAmount() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CustomToast.showToast(ResouAct.this, response.getGoldAmount() + "", "阅读奖励");
                                    EventBus.getDefault().post(new LuckTurntableEvent(response.getGoldAmount()));//幸运宝箱 金币变化前端自己获取，默认传1
                                }
                            });
                            UserManager.getInst().goldChangeNofity(response.getGoldAmount());
                        } else {
                            readGoldView.showRemind("您的运气不好，该任务金币已被抢光", "知道了");
                        }
                    } else {
                        readGoldView.showRemind("您的运气不好，该任务金币已被抢光", "知道了");
                    }
                    mSubmitAdClickProtocol = null;
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mSubmitAdClickProtocol = null;
                    readGoldView.showRemind(error, "知道了");
                }
            });
            mSubmitAdClickProtocol.postRequest();
        } else {
            readGoldView.showRemind("红包已经被领取了~", "知道了");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            readGoldView.setDestory();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        readGoldView.setDestory();
    }
}
