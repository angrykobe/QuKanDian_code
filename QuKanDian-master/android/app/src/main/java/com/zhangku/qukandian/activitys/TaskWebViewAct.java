package com.zhangku.qukandian.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.BaseNew.BaseTitleAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.AdMissionOtherBean;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.EventBusBean.ShowNewPeopleTaskDialogBean;
import com.zhangku.qukandian.bean.LaoBean;
import com.zhangku.qukandian.bean.NewPeopleTaskBean;
import com.zhangku.qukandian.bean.SubmitTaskBean;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.dialog.DialogNewPeopleTask;
import com.zhangku.qukandian.dialog.ShareForWebView;
import com.zhangku.qukandian.interfaces.OnSharedListener;
import com.zhangku.qukandian.javascript.DialogShareInterface;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.DynmicmissionProtocol;
import com.zhangku.qukandian.protocol.GetNewHttmissionRuleProtocol;
import com.zhangku.qukandian.protocol.PostNewAwakenInfor;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.GsonUtil;
import com.zhangku.qukandian.utils.MachineInfoUtil;
import com.zhangku.qukandian.utils.PermissionHelper;
import com.zhangku.qukandian.utils.SetWebSettings;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.ReadGoldProcessView;
import com.zhangku.qukandian.widght.ReadGoldView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 创建者          xuzhida
 * 创建日期        2018/10/9
 * 任务h5页面
 */
public class TaskWebViewAct extends BaseAct implements DialogShareInterface.OnShareListener {
    private TextView titleTV;
    private ProgressBar webprogressBar;
    private WebView webview;
    // private ReadGoldView mReadGoldView;
    private ReadGoldView readGoldView;
    private boolean isClickSecend = true;
    private TaskBean.MoreMissionBean nowMissionBean;
    private TaskBean mBean;
    private List<TaskBean.MoreMissionBean.MissionClickRulesBean> adRuleList;

    private List<Integer> timeList = new ArrayList<>();
    private boolean hadClickMoveInfor = false;
    private boolean hadClickInfor = false;
    private boolean isShowGuideInfor = true;
    private long lastScollTime = 0;//手指滑动的时间
    private int clickNum = 0;

    @Override
    protected void loadData() {
    }

    @Override
    protected void initViews() {
        if (!UserManager.getInst().hadLogin()) {
            ActivityUtils.startToBeforeLogingActivity(this);
            finish();
            return;
        }

        webview = (WebView) findViewById(R.id.webview);
        readGoldView = findViewById(R.id.ReadGoldView);
        readGoldView.setProcessClick(null);
        webprogressBar = (ProgressBar) findViewById(R.id.webprogressBar);
        titleTV = (TextView) findViewById(R.id.titleTV);
        SetWebSettings.setWebviewForActivity(this, webview, webprogressBar);

        webview.addJavascriptInterface(new DialogShareInterface(this), "Share");
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                clickNum++;
                isClickSecend = true;
                if (url.startsWith("http") && url.contains(".apk")) {
                    try {
                        Intent viewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(viewIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (!url.startsWith("http")) {//打开deeplink
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
            }
        });

        findViewById(R.id.titleCloseTV).setOnClickListener(this);
        findViewById(R.id.titleBackIV).setOnClickListener(this);
        View StatusBar = findViewById(R.id.StatusBar);
        setStatusView(this, StatusBar);

        mBean = (TaskBean) getIntent().getSerializableExtra("TaskBean");
        //按需求多任务循环交替
        List<TaskBean.MoreMissionBean> allTask = mBean.getMoreMission();//多任务对象
        int size = allTask.size();//多任务个数
        int nowFinishCount = mBean.getFinishedTime();//任务已完成次数
        if (size == 0) {//无多任务，退出
            ToastUtils.showLongToast(this, "任务权限不足");
            finish();
            return;
        }

        if (mBean.getFinishedTime() == mBean.getAwardsTime()) {// 总次数 == 已完成次数
            nowMissionBean = allTask.get(0);//展示第一条多任务
            readGoldView.setVisibility(View.GONE);
        } else {
            int finishedTime = nowFinishCount % size;//  多任务开始位置（上一次任务的位置）
            for (int i = finishedTime; i < size + finishedTime; i++) {//从上一个任务位置开始循环
                int index = i % size;
                TaskBean.MoreMissionBean moreMissionBean = allTask.get(index);//获取 某个多任务对象
                if (moreMissionBean.getAwardsTime() == moreMissionBean.getFinishedTime()) {//当前多任务已没剩余次数
                    continue;
                } else {//开始本任务
                    nowMissionBean = moreMissionBean;
                    break;
                }
            }
            if (nowMissionBean == null) {
                ToastUtils.showLongToast(this, "当前无任务");
                finish();
                return;
            }
            adRuleList = nowMissionBean.getMissionClickRules();
            int rand = new Random().nextInt(100);
            int allTime = 0;
            TaskBean.MoreMissionBean.MissionClickRulesBean nowAdClRulesBean;
            for (TaskBean.MoreMissionBean.MissionClickRulesBean bean1 : adRuleList) {
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
            readGoldView.setTouchListener(touchViewListenForSougou);
        }
        webview.loadUrl("" + nowMissionBean.getGotoLink());
        isClickSecend = !nowMissionBean.getIsSecondClickEnable();

//        //请求时间
//        mGetHttmissionRuleProtocol = new GetNewHttmissionRuleProtocol(this, 2, new BaseModel.OnResultListener<LaoBean>() {
//            @Override
//            public void onResultListener(LaoBean response) {
//                mGetHttmissionRuleProtocol = null;
//            }
//
//            @Override
//            public void onFailureListener(int code, String error) {
//                mGetHttmissionRuleProtocol = null;
//            }
//        });
//        mGetHttmissionRuleProtocol.postRequest();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_webview_with_process;
    }

    @Override
    protected String setTitle() {
        return "";
    }

    @Override
    protected void myOnClick(View v) {
        switch (v.getId()) {
            case R.id.titleBackIV:
                if (webview.canGoBack()) {
                    webview.goBack();
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
            if (webview.canGoBack()) {
                webview.goBack();
                return true;
            } else {
                finish();
                return true;
            }
        }
        return false;
    }

    @Override
    public void finish() {
        super.finish();
//        EventBus.getDefault().post(mBean);
    }

    @Override
    public void onShareListener(int type, String title, String desc, String tel) {
    }

    @Override
    public void onShareListener(int type, String shareUrl, String bitmapUrl, String title, String content) {
    }

    @Override
    public void onShareDialog(final String title, final String desc, final String imgUrl, final String url) {
        ShareForWebView shareDialog = new ShareForWebView(this);
        shareDialog.setOnSharedListener(new OnSharedListener() {
            @Override
            public void onSharedListener(DetailsBean mDetailsBean, SHARE_MEDIA share_media) {
                UMImage sImg = new UMImage(TaskWebViewAct.this, imgUrl);

                sImg.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                sImg.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
                sImg.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

                UMWeb web = new UMWeb(url);
                web.setTitle(title);//标题

                UMImage umImage = new UMImage(TaskWebViewAct.this, UserManager.mShareBean.getWechatIcon());
                umImage.setThumb(sImg);

                web.setThumb(umImage);  //缩略图
                web.setDescription(desc);//描述

                if (TextUtils.isEmpty(title)) {
                    new ShareAction(TaskWebViewAct.this)
                            .setPlatform(share_media)
                            .setCallback(mUMShareListener)
                            .withMedia(umImage)
                            .share();
                } else {
                    new ShareAction(TaskWebViewAct.this)
                            .setPlatform(share_media)
                            .setCallback(mUMShareListener)
                            .withMedia(web)
                            .share();
                }
            }
        });
        shareDialog.show();
    }

    @Override
    public void onToMainListener(int type) {
    }

    @Override
    public void onToAnywhereListener(String deeplink) {
    }

    @Override
    public void onBackListener() {
        finish();
    }

    private UMShareListener mUMShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(TaskWebViewAct.this, "正在打开应用");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtils.showLongToast(TaskWebViewAct.this, "分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (throwable.toString().contains("没有安装应用")) {
                ToastUtils.showLongToast(TaskWebViewAct.this, "没有安装应用");
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        lastScollTime = System.currentTimeMillis();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            if (readGoldView != null)
                readGoldView.setDestory();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (readGoldView != null)
            readGoldView.setDestory();
    }

    private boolean hasShow = false;

    private ReadGoldProcessView.OnTouchViewListen touchViewListenForSougou = new ReadGoldProcessView.OnTouchViewListen() {
        @Override
        public boolean onTouchView(int position) {
            // if (isClickSecend) mReadGoldView.hideRemind();
            // switch (position) {
            //     case 2:
            //         if (!isClickSecend) {//二次点击条件是否已经满足
            //             if (!hasShow) {
            //                 hasShow = true;
            //                 mReadGoldView.showRemind("点击感兴趣的内容进行阅读，才可获得金币~", "知道了");
            //             }
            //             return false;
            //         }
            //         break;
            //     case 4:
            //         getGoldUrl();
            //         return true;
            // }
            // return true;
            switch (position) {
                case -1:
                    break;
                case 1:
                    getGoldUrl();
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

    private void getGoldUrl() {
        //动态任务完成接口
        AdMissionOtherBean bean = new AdMissionOtherBean(mBean.getName(), mBean.getTypeId(), MachineInfoUtil.getInstance().getIMEI(this));
        bean.setAdsName(nowMissionBean.getAdsName());
        bean.setIsMore(true);
        new DynmicmissionProtocol(this, bean,
                new BaseModel.OnResultListener<SubmitTaskBean>() {
                    @Override
                    public void onResultListener(SubmitTaskBean response) {
                        nowMissionBean.setFinishedTime(nowMissionBean.getFinishedTime() + 1);
                        mBean.setFinishedTime(mBean.getFinishedTime() + 1);
                        EventBus.getDefault().post(new TaskBean());
                        if (response.getMissionName().contains("first_luck")) {
                            if (response.getDescription().contains("finish")) {
//                                CustomToast.showToast(TaskWebViewAct.this, response.getGoldAmount() + "", response.getDescription());
                                CustomToast.showToast(TaskWebViewAct.this, response.getGoldAmount() + "", "新手任务-幸运任务");
                                MobclickAgent.onEvent(TaskWebViewAct.this, "294-finishxingyunrenwu");
                                EventBus.getDefault().post(new ShowNewPeopleTaskDialogBean());
                                ActivityUtils.startToMainActivity(TaskWebViewAct.this, 2, 0);
                                finish();
                            } else {
                                CustomToast.showToast(TaskWebViewAct.this, response.getGoldAmount() + "", response.getDescription());
                                EventBus.getDefault().post(new NewPeopleTaskBean());
                                finish();
                            }
                        } else {
                            CustomToast.showToast(TaskWebViewAct.this, response.getGoldAmount() + "", response.getDescription());
                        }
                    }

                    @Override
                    public void onFailureListener(int code, String error) {
                        ToastUtils.showLongToast(TaskWebViewAct.this, error);
                    }
                }).postRequest();
    }

    @Override
    public void onGetPhone() {
        if (!PermissionHelper.check(PermissionHelper.READ_CONTACTS)) {
            PermissionHelper.request(this, PermissionHelper.READ_CONTACTS_CODE, PermissionHelper.READ_CONTACTS);
            return;
        }
        getPhone();
    }

    private void getPhone() {
        List list = CommonHelper.getPhoneList(this);
        String json = GsonUtil.toJson(list);
        String javascriptStr = "javascript:getPhoneBook(" + json + ")";
        webview.loadUrl(javascriptStr);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 用户同意了操作权限
            switch (requestCode) {
                case PermissionHelper.READ_CONTACTS_CODE:
                    getPhone();
                    break;
            }
        } else {
            // 用户拒绝了操作权限
            switch (requestCode) {
                case PermissionHelper.READ_CONTACTS_CODE:
                    PermissionHelper.showAlert(this, "通讯录");
                    break;
            }

        }
    }
}
