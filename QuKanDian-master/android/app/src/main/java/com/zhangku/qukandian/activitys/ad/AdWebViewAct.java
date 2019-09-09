package com.zhangku.qukandian.activitys.ad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.BaseNew.BaseTitleAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.natives.AdZhiZuNative;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.AdResultBean;
import com.zhangku.qukandian.bean.AdsClickParam;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogNewPeopleDoneTask;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PostNewAwakenInfor;
import com.zhangku.qukandian.protocol.PutNewAdClickProtocol;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.SetWebSettings;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.ReadGoldProcessView;
import com.zhangku.qukandian.widght.ReadGoldView;
import com.zhangku.qukandian.widght.X5WebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.zhangku.qukandian.bean.AdLocationBeans.AdLocationsBean.ClientAdvertisesBean.AdClRulesBean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/10/9
 * 广告点击h5页面
 */
public class AdWebViewAct extends BaseTitleAct {
    private X5WebView mWebView;
    private ProgressBar webprogressBar;
    private ReadGoldView readGoldView;

    private String mUrl = "";
    private PutNewAdClickProtocol mSubmitAdClickProtocol;//广告点击获取金币
    private AdsClickParam mAdsClickParam;
    private long lastScollTime = 0;//手指滑动的时间
    private AdLocationBeans.AdLocationsBean.ClientAdvertisesBean mBean;
    private int clickNum = 0;
    private List<AdClRulesBean> adRuleList = new ArrayList<>();
    private List<Integer> timeList = new ArrayList<>();
    private boolean hadClickMoveInfor = false;
    private boolean hadClickInfor = false;
    private boolean isShowGuideInfor = true;
    private PostNewAwakenInfor postNewAwakenInfor;

    @Override

    protected void loadData() {
    }

    @Override
    protected void initViews() {
        mUrl = getIntent().getExtras().getString("url");
        mBean = (AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) getIntent().getExtras().get("ClientAdvertisesBean");

        adRuleList = mBean.getAdverClickRules();

        webprogressBar = findViewById(R.id.webprogressBar);
        mWebView = findViewById(R.id.url_webview);

        SetWebSettings.setWebviewForAd(this, mWebView, webprogressBar);
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
        }

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Context context = view.getContext();
                Uri nextUri = Uri.parse(url);
                clickNum++;
                if ("http".equals(url.substring(0, 4)) && !url.contains(".apk")) {
                    view.loadUrl(url);
                } else {
                    if (mBean.isAwaken()) {
                        CommonHelper.openDeeplink(AdWebViewAct.this, url);
                    } else {
                        if (postNewAwakenInfor == null) {
                            postNewAwakenInfor = new PostNewAwakenInfor(AdWebViewAct.this, mBean, null);
                            postNewAwakenInfor.postRequest();
                        }
                    }
                }
                if ("alipays".equals(nextUri.getScheme())) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }
                if ("http".equals(nextUri.getScheme()) || "https".equals(nextUri.getScheme())) {
                    String originalUrl = view.getOriginalUrl();
                    Map<String, String> extraHeaders = new HashMap<>();
                    //微信支付的时候手动添加Referer
                    if (!TextUtils.isEmpty(originalUrl) && url.startsWith("https://wx.tenpay.com/")) {
                        extraHeaders.put("Referer", originalUrl);
                        view.loadUrl(url, extraHeaders);
                    } else {
                        view.loadUrl(url);
                    }
                    return true;
                }
                //跳转微信客户端
                else if ("weixin".equals(nextUri.getScheme())) {
                    if (CommonHelper.isAppInstalled("com.tencent.mm")) {
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    } else {
                        ToastUtils.showShortToast(context, "您未安装微信");
                    }
                } else {
                    return false;
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

        int rand = new Random().nextInt(100);
        if (AdZhiZuNative.getRedState(mBean) != 0) {
            AdClRulesBean nowAdClRulesBean;
            int allTime = 0;
            for (AdClRulesBean bean1 : adRuleList) {
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

        int hisCount = UserSharedPreferences.getInstance().getInt(Constants.READ_ART_COUNT, 0);
        int firstInTimeRead = UserManager.getInst().getmRuleBean().getAdsClickInductionRule().getFirstInTimeRead();
        isShowGuideInfor = firstInTimeRead >= hisCount;
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

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
        if (isFinishing()) {
            readGoldView.setDestory();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        readGoldView.setDestory();
    }

    private ReadGoldProcessView.OnTouchViewListen touchViewListenForAdClick = new ReadGoldProcessView.OnTouchViewListen() {
        @Override
        public boolean onTouchView(int position) {
            switch (position) {
                case -1:
                    break;
                case 1:
                    requestAdGold();
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
                        new DialogNewPeopleDoneTask(AdWebViewAct.this, "" + response.getGoldAmount()).show();
                        UserManager.getInst().goldChangeNofity(response.getGoldAmount());
                    } else if (!"0".equals(response.getDescription())) {
                        if (response.getGoldAmount() == 0) {//红包获取失败
                            readGoldView.showRemind(UserManager.getInst().getmRuleBean().getAdsClickInductionRule().getScAdsGotGoldFailedTip(), "知道了");
                        } else if (response.getGoldAmount() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CustomToast.showToast(AdWebViewAct.this, response.getGoldAmount() + "", "阅读奖励");
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
}
