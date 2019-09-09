package com.zhangku.qukandian.activitys;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.adCommon.natives.AdZhiZuNative;
import com.zhangku.qukandian.adCommon.util.AdUtil;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.base.BaseLoadingActivity;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.AdMissionBean;
import com.zhangku.qukandian.bean.AdResultBean;
import com.zhangku.qukandian.bean.AdsClickParam;
import com.zhangku.qukandian.bean.DoneTaskResBean;
import com.zhangku.qukandian.bean.EventBusBean.ShowNewPeopleTaskDialogBean;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.bean.SougouCacheBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.dialog.DialogNewPeopleDoneTask;
import com.zhangku.qukandian.dialog.DialogUrlWeb;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.PostNewAwakenInfor;
import com.zhangku.qukandian.protocol.PutNewAdClickProtocol;
import com.zhangku.qukandian.protocol.PutNewSougouTaskProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.SetWebSettings;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.ReadGoldProcessView;
import com.zhangku.qukandian.widght.ReadGoldView;
import com.zhangku.qukandian.widght.X5WebView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by yuzuoning on 2017/5/4.
 * 自主广告点击进来页
 */
public class UrlWebViewActivity extends BaseLoadingActivity implements View.OnClickListener {
    private ImageView mIvBack;
    private TextView mTvTitle;
    private X5WebView mWebView;
    private ProgressBar webprogressBar;
    private String mUrl = "";
    private boolean hadTouchWebView = false;//是否已经二次点击
    private DialogUrlWeb mDialogUrlWeb;//进入页面首次提示语
    private int mFrom = 0;
    private String mRecordUrl = "";//判断搜狗点击链接红包
    private PutNewAdClickProtocol mSubmitAdClickProtocol;//广告点击获取金币
    private PutNewSougouTaskProtocol mPutAdMissionProtocol;//普通任务接口，目前是惠头条和搜狗
    private AdsClickParam mAdsClickParam;
    private boolean isSecondClickGoldEnable = false;//是否需要2次点击
    private long lastScollTime = 0;//手指滑动的时间
    private boolean hadRemind;//是否已经提示过提示语
    private ReadGoldView readGoldView;
    private AdLocationBeans.AdLocationsBean.ClientAdvertisesBean mBean;
    private boolean isShow;
    private PostNewAwakenInfor postNewAwakenInfor;

    @Override
    protected void initActionBarData() {
        hideActionBarView();
    }

    @Override
    protected void initViews() {
        if (null != getIntent()) {
            mUrl = getIntent().getExtras().getString("url");
            mFrom = getIntent().getExtras().getInt("from");
            isShow = getIntent().getExtras().getBoolean("isShow");
            mBean = (AdLocationBeans.AdLocationsBean.ClientAdvertisesBean) getIntent().getExtras().get("object");
        }
        mDialogUrlWeb = new DialogUrlWeb(this);
        mIvBack = (ImageView) findViewById(R.id.url_gray_actionbar_back);
        mTvTitle = (TextView) findViewById(R.id.url_gray_actionbar_title);
        webprogressBar = findViewById(R.id.webprogressBar);
        mWebView = findViewById(R.id.url_webview);

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        lastScollTime = System.currentTimeMillis();
                        break;
                }
                return false;
            }
        });

        SetWebSettings.setWebviewForAd(this, mWebView, webprogressBar);
        if (null != mBean) {
            isSecondClickGoldEnable = mBean.isIsSecondClickGoldEnable();
            hadTouchWebView = !isSecondClickGoldEnable;//不需要2次点击流程
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

        hideLoadingLayout();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (mFrom != Constants.URL_FROM_NORMAL) {
                    if ("http".equals(url.substring(0, 4)) && !url.contains(".apk")) {
                        view.loadUrl(url);
                        hadTouchWebView = true;

                        if (mFrom == Constants.URL_FROM_SOUGOU && readGoldView.getVisibility() == View.GONE) {
                            //搜狗过来的广告在2次点击显示转圈圈
//                            if (SougouCacheBean.isShowSougouAd()) {
                            if (UserManager.getInst().getQukandianBean().getRscnt() > 0) {
                                //显示转圈圈
                                readGoldView.setVisibility(View.VISIBLE);
                                if (UserManager.getInst().getmRuleBean().getSougouGoldRule() != null && UserManager.getInst().getmRuleBean().getSougouGoldRule().getDuration() != 0) {
                                    readGoldView.setMaxProcess(UserManager.getInst().getmRuleBean().getSougouGoldRule().getDuration());
                                } else {
                                    readGoldView.setMaxProcess(15);
                                }
                                readGoldView.startTiming(1);
                            }
                            mRecordUrl = url;
                        }
                    } else {
                        if (mBean.isAwaken()) {
                            CommonHelper.openDeeplink(UrlWebViewActivity.this, url);
                        } else {
                            if (postNewAwakenInfor == null) {
                                postNewAwakenInfor = new PostNewAwakenInfor(UrlWebViewActivity.this, mBean, null);
                                postNewAwakenInfor.postRequest();
                            }
                        }
                    }
                } else {
                    CommonHelper.openDeeplink(UrlWebViewActivity.this, url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mDialogUrlWeb.dismiss();
                String mTitle = view.getTitle();
                if (TextUtils.isEmpty(mTitle)) {
                    mTvTitle.setText(mTitle);
                }
                lastScollTime = System.currentTimeMillis();
            }
        });
        mWebView.loadUrl(mUrl);

        mIvBack.setOnClickListener(this);

        readGoldView = findViewById(R.id.ReadGoldView);
        readGoldView.setProcessClick(null);//
        readGoldView.setVisibility(View.GONE);
        if (mFrom == Constants.URL_FROM_SOUGOU) {//搜狗过来的
//            if (SougouCacheBean.isShowSougouAd()) {
            if (UserManager.getInst().getQukandianBean().getRscnt() > 0) {
                mDialogUrlWeb.show();
            }
            readGoldView.setTouchListener(touchViewListenForSougou);
        } else if (mFrom == Constants.URL_FROM_ADS) {//自主过来的广告
            readGoldView.setVisibility(View.VISIBLE);
            readGoldView.setTouchListener(touchViewListenForAdClick);
            if (isShow && AdZhiZuNative.getRedState(mBean) != 0) { //广告红包一天最大数
//                if (mBean != null && mBean.getDuration() > 0) {
//                    readGoldView.setMaxProcess(mBean.getDuration());
//                } else {
//                    readGoldView.setMaxProcess(isSecondClickGoldEnable ? UserManager.getInst().getmRuleBean().getAdsClickGiftRule().getScGetGiftDuration() : UserManager.getInst().getmRuleBean().getAdsClickGiftRule().getGetGiftDuration());
//                }
                if(mBean != null){
                    readGoldView.setMaxProcess(isSecondClickGoldEnable ? UserManager.getInst().getmRuleBean().getAdsClickGiftRule().getScGetGiftDuration() : UserManager.getInst().getmRuleBean().getAdsClickGiftRule().getGetGiftDuration());
                }
                if (UserManager.getInst().getUserBeam().getUserAdsType() == 1) {  //1：新用户  2：用1天用户 3：老用户
                    if (!TextUtils.isEmpty(UserManager.getInst().getmRuleBean().getAdsClickInductionRule().getNewbieTip())) {//新人提示语
                        readGoldView.showRemind(UserManager.getInst().getmRuleBean().getAdsClickInductionRule().getNewbieTip(), "");

                        readGoldView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                readGoldView.hideRemind();
                                readGoldView.startTimingForAdRed();
                            }
                        }, 3000);
                    }
                } else if (UserManager.getInst().getUserBeam().getUserAdsType() == 2) {  //1：新用户  2：用1天用户 3：老用户
                    if (!TextUtils.isEmpty(UserManager.getInst().getmRuleBean().getAdsClickInductionRule().getNewbieTip())) {//新人提示语
                        readGoldView.showRemind(UserManager.getInst().getmRuleBean().getAdsClickInductionRule().getScAdsTip(), "");

                        readGoldView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                readGoldView.hideRemind();
                                readGoldView.startTimingForAdRed();
                            }
                        }, 3000);
                    }
                } else {
                    readGoldView.startTimingForAdRed();
                }
            } else {
                readGoldView.setVisibility(View.GONE);
            }
        }
    }

    private ReadGoldProcessView.OnTouchViewListen touchViewListenForSougou = new ReadGoldProcessView.OnTouchViewListen() {
        @Override
        public boolean onTouchView(int position) {
            if (position == 1) {
                //请求数据
                requestSougoGold();
            }
            return true;
        }

        @Override
        public void stayTimeTooLong() {
        }
    };

    private ReadGoldProcessView.OnTouchViewListen touchViewListenForAdClick = new ReadGoldProcessView.OnTouchViewListen() {
        @Override
        public boolean onTouchView(int position) {
            switch (position) {
                case -1:
                    break;
                case 3:
                    if (!hadRemind && UserManager.getInst().hadLogin()
                            && UserManager.getInst().getUserBeam().getUserAdsType() == 1) {  //1：新用户  2：用1天用户 3：老用户
                        hadRemind = !hadRemind;
                        readGoldView.showRemind("认真选择感兴趣的内容并点击阅读，才有机会获得金币", "知道了");
                    } else if (!hadRemind && UserManager.getInst().hadLogin()
                            && UserManager.getInst().getUserBeam().getUserAdsType() == 2) {  //1：新用户  2：用1天用户 3：老用户
                        readGoldView.showRemind("即将获得金币奖励，认真选择感兴趣的内容并点击阅读，才有机会获得金币", "知道了");
                        hadRemind = !hadRemind;
                    }
                    return hadTouchWebView;
                case 4:
                    if (UserManager.getInst().hadLogin()
                            && UserManager.getInst().getUserBeam().getUserAdsType() == 1) {  //1：新用户  2：用1天用户 3：老用户
                        readGoldView.showRemind("即将获得金币奖励，请继续保持阅读", "");
                        readGoldView.hideRemindWithAnim();
                    }
                    break;
                case 5:
                    //请求数据
                    if (mFrom == Constants.URL_FROM_SOUGOU) {
                        requestSougoGold();
                    } else {
                        requestAdGold();
                    }
                    return true;
            }
            long nowTime = System.currentTimeMillis();
            if (nowTime - lastScollTime >= 3000) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public void stayTimeTooLong() {
            ToastUtils.showShortToast(UrlWebViewActivity.this, "页面停留过久");
        }
    };

    @Override
    public int getLoadingViewParentId() {
        return R.id.url_webview;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_url_webview_new;
    }

    @Override
    public String setPagerName() {
        return "外链";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.url_gray_actionbar_back:
                finish();
                break;
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        if (null != mWebView) {
            mWebView.removeAllViews();
            mWebView.clearCache(true);
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebView != null && keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //广告红包获取金币
    private void requestAdGold() {
        if (null == mSubmitAdClickProtocol
                && null != UserManager.getInst().getUserBeam().getGoldAccount()
        ) {
            mSubmitAdClickProtocol = new PutNewAdClickProtocol(UrlWebViewActivity.this, mBean, new BaseModel.OnResultListener<AdResultBean>() {
                @Override
                public void onResultListener(final AdResultBean response) {
                    int anInt = UserSharedPreferences.getInstance().getInt(Constants.READ_ART_COUNT, 0);
                    UserSharedPreferences.getInstance().putInt(Constants.READ_ART_COUNT, anInt + 1);
                    // 获取金币结果
                    // DBTools.saveMyselfAdBean(mBean);
                    // PutNewAdClickProtocol.removeGoldAd(mBean);
                    AdUtil.mAdsCache.add(mBean.toString());
                    if ("first".equals(response.getDescription())) {
//                        new DialogNewPeopleTask(UrlWebViewActivity.this,null,true).show();
                        MobclickAgent.onEvent(QuKanDianApplication.getmContext(), "294-finishhongbao");
                        new DialogNewPeopleDoneTask(UrlWebViewActivity.this, "" + response.getGoldAmount()).show();
                        UserManager.getInst().goldChangeNofity(response.getGoldAmount());
                    } else if (!"0".equals(response.getDescription())) {
                        if (response.getGoldAmount() == 0 && isSecondClickGoldEnable) {//红包获取失败
                            readGoldView.showRemind(UserManager.getInst().getmRuleBean().getAdsClickInductionRule().getScAdsGotGoldFailedTip(), "知道了");
                        } else if (response.getGoldAmount() > 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CustomToast.showToast(UrlWebViewActivity.this, response.getGoldAmount() + "", "阅读奖励");
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

    //搜狗广告红包获取金币
    private void requestSougoGold() {
        if (null == mPutAdMissionProtocol && !mRecordUrl.equals(AdsRecordUtils.getInstance().getString(Constants.SOUGOU_RECORD_URL, ""))) {
            //普通任务接口，目前是惠头条和搜狗
            mPutAdMissionProtocol = new PutNewSougouTaskProtocol(UrlWebViewActivity.this,
                    new AdMissionBean(Constants.MISSION_TYPE_SOUGOU, UrlWebViewActivity.this), new BaseModel.OnResultListener<DoneTaskResBean>() {
                @Override
                public void onResultListener(DoneTaskResBean response) {
                    int anInt = UserSharedPreferences.getInstance().getInt(Constants.READ_RESOU_COUNT, 0);
                    UserSharedPreferences.getInstance().putInt(Constants.READ_RESOU_COUNT, anInt + 1);
                    final int goldAmount = response.getGoldAmount();
                    if (goldAmount != 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CustomToast.showToastOther(UrlWebViewActivity.this, goldAmount + "", "任务奖励");
                            }
                        });
                    } else {
                        ToastUtils.showLongToast(UrlWebViewActivity.this, "您的运气不好，该任务金币已被抢光");
                    }
                    UserManager.getInst().goldChangeNofity(goldAmount);
                    SougouCacheBean.addSougouAdCache();
                    AdsRecordUtils.getInstance().putString(Constants.SOUGOU_RECORD_URL, mRecordUrl);
                    mPutAdMissionProtocol = null;
                    if ("first_resou".equals(response.getMsg())) {
                        EventBus.getDefault().post(new NewPeopleTaskBean());
                        finish();
                    } else if ("finish".equals(response.getMsg())) {
//                        new DialogNewPeopleTask(UrlWebViewActivity.this,null,true).show();
                        EventBus.getDefault().post(new ShowNewPeopleTaskDialogBean());
                        ActivityUtils.startToMainActivity(UrlWebViewActivity.this, 2, 0);
                        finish();
                    }
                }

                @Override
                public void onFailureListener(int code, String error) {
                    mPutAdMissionProtocol = null;
                    ToastUtils.showLongToast(UrlWebViewActivity.this, error);
                }
            });
            mPutAdMissionProtocol.postRequest();
        } else if (mRecordUrl.equals(AdsRecordUtils.getInstance().getString(Constants.SOUGOU_RECORD_URL, ""))) {
            ToastUtils.showLongToast(this, "已经领过该奖励~");
        }
    }

//    //293  用户获得金币后，广告移除，替换新的广告内容； 解决广告主反馈用户重复率高的问题
//    private void removeGoldAd() {
//        if (!mBean.isRemove()) return;
//        String string = UserSharedPreferences.getInstance().getString(Constants.AD_INFO, "");
//        List<AdLocationBeans> list = GsonUtil.fromJsonForList(string,AdLocationBeans.class);
//        for (AdLocationBeans bean : list) {
//
//            List<AdLocationBeans.AdLocationsBean> lists = bean.getAdLocations();
//            if (null != lists && lists.size() > 0) {
//                //初始化列表中的广告位
//                for (int i = 0; i < lists.size() && i < 6; i++) {
//                    if (null == lists.get(i).getClientAdvertises() || lists.get(i).getClientAdvertises().size() == 0) {
//                        continue;
//                    }
//                    final AdLocationBeans.AdLocationsBean adLocationsBean = lists.get(i);
//                    if (adLocationsBean == null) continue;
//
//                    Iterator<AdLocationBeans.AdLocationsBean.ClientAdvertisesBean> iterator = adLocationsBean.getClientAdvertises().iterator();
//                    while (iterator.hasNext()) {
//                        AdLocationBeans.AdLocationsBean.ClientAdvertisesBean next = iterator.next();
//                        if (mBean.getMbLink().equals(next.getMbLink()) && mBean.getAdverId() == next.getAdverId()) {
//                            iterator.remove();
//                        }
//                    }
//                }
//            }
//        }
//        UserSharedPreferences.getInstance().putString(Constants.AD_INFO, new Gson().toJson(list));
//    }

}
