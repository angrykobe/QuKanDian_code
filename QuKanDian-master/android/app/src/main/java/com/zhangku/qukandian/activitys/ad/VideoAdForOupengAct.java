package com.zhangku.qukandian.activitys.ad;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdMissionOtherBean;
import com.zhangku.qukandian.bean.CityBean;
import com.zhangku.qukandian.bean.SubmitTaskBean;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.biz.adbeen.oupeng.OupengResBean;
import com.zhangku.qukandian.biz.adbeen.wangmai.WangMaiResBean;
import com.zhangku.qukandian.biz.adcore.AdBrowserActivity;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.oupeng.OupengNativeAdLoader;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.dialog.DialogConfirm;
import com.zhangku.qukandian.interfaces.ApplicationGetIp;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.AdDownObserver;
import com.zhangku.qukandian.protocol.DynmicmissionProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.FileBuildForBugUtils;
import com.zhangku.qukandian.utils.GlideUtils;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.MachineInfoUtil;
import com.zhangku.qukandian.utils.OkHttp3Utils;
import com.zhangku.qukandian.utils.ToastUtils;
import com.zhangku.qukandian.widght.ReadGoldProcessView;
import com.zhangku.qukandian.widght.ReadGoldView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 创建者          xuzhida
 * 创建日期        2019/3/26
 * 欧朋视频
 */
public class VideoAdForOupengAct extends BaseAct implements AdDownObserver.OnAdDownListener {


    private Point mDownP = new Point(-999, -999);
    private Point mUpP = new Point(-999, -999);
    private OupengResBean mBean;
    private DownloadManager downloadManager;
    private boolean isDowning = false;
    private long clickTime;
    private VideoView mVideoView;
    private WebView mWebview;
    private VideoAdForOupengAct.MyInstalledReceiver installedReceiver;
    private View mProgressbar;
    private ImageView iconIV;
    private TextView titleTV;
    private TextView subTitleTV;
    private ReadGoldView readGoldView;
    private OupengResBean.AdsBean.MetaGroupBean videoBean;

    private int mTimes = 0;
    private Timer mCount = null;

    /**
     * AD_IMPRESSION 展现上报
     * AD_CLICK 点击上报
     * DOWN_LOAD_START 下载开始上报
     * DOWN_LOAD_END 下载完成上报
     * INSTALL_START 安装开始上报
     * INSTALL_END 安装完成上报
     * VIDEO_AD_START 视频开始播放上报
     * VIDEO_AD_END 视频正常播放结束上报
     * VIDEO_AD_PLAY 视频播放中上报
     * VIDEO_AD_CLOSE 视频关闭上报
     * VIDEO_LDP_PV 视频广告落地页展示上报
     * VIDEO_LDP_CLICK 视频广告落地页点击上报
     * VIDEO_LDP_CLOSE 视频广告落地页关闭上报
     * ACTIVE_END 应用激活上报信息
     */
    @Override
    protected void loadData() {
        if (!CommonHelper.isWifi(this)) {
            new DialogConfirm(this).setMessage("当前为非WIFI环境，是否继续播放视频？")
                    .setYesBtnText("继续播放")
                    .setNoBtnText("暂不播放")
                    .setListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (v.getId()) {
                                case R.id.dialog_confirm_no:
                                    finish();
                                    break;
                                case R.id.dialog_confirm_yes:
                                    request();
                                    break;
                            }
                        }
                    }).show();
        } else {
            request();
        }
    }

    private void request() {
        QuKanDianApplication.getCityBeanFirst(new ApplicationGetIp() {
            @Override
            public void getIp(CityBean cityBean) {
                String url = new OupengNativeAdLoader().getRequestUrl(cityBean.getCip());
                OkHttp3Utils.getInstance().doPostJsonForOupeng(AdConfig.oupeng_address, url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String string = response.body().string();
                            mBean = new Gson().fromJson(string, OupengResBean.class);
                            if (mBean == null || mBean.getError_code() != 0) {
                                ToastUtils.showLongToast(VideoAdForOupengAct.this, "获取视频失败！");
                                finish();
                                return;
                            }
                            videoBean = mBean.getAds().get(0).meta_group.get(0);
                            if (videoBean.video == null) {
                                ToastUtils.showLongToast(VideoAdForOupengAct.this, "获取视频失败！");
                                finish();
                                return;
                            }
                            final Uri uri = Uri.parse(videoBean.video);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mVideoView.setVideoURI(uri);
                                    mVideoView.start();

                                    findViewById(R.id.bottomView).setVisibility(View.VISIBLE);
                                    GlideUtils.displayRoundImage(VideoAdForOupengAct.this, videoBean.icon, iconIV, 5);
                                    titleTV.setText("" + videoBean.title);
                                    findViewById(R.id.detailTV).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            click();
                                        }
                                    });
                                    titleTV.setVisibility(View.GONE);
                                    iconIV.setVisibility(View.GONE);
                                    final TaskBean bean = (TaskBean) getIntent().getSerializableExtra("bean");
                                    readGoldView.setVisibility(View.VISIBLE);
                                    readGoldView.setMaxProcess(bean.getDuration() == 0 ? videoBean.video_duration : bean.getDuration());
                                    readGoldView.startTiming(1);
                                    readGoldView.setTouchListener(new ReadGoldProcessView.OnTouchViewListen() {
                                        @Override
                                        public boolean onTouchView(int position) {
                                            //动态任务完成接口
                                            new DynmicmissionProtocol(VideoAdForOupengAct.this,
                                                    new AdMissionOtherBean(bean.getName(), bean.getTypeId(), MachineInfoUtil.getInstance().getIMEI(VideoAdForOupengAct.this)),
                                                    new BaseModel.OnResultListener<SubmitTaskBean>() {
                                                        @Override
                                                        public void onResultListener(SubmitTaskBean response) {
                                                            CustomToast.showToast(VideoAdForOupengAct.this, response.getGoldAmount() + "", response.getDescription());
                                                            EventBus.getDefault().post(new TaskBean());
                                                        }

                                                        @Override
                                                        public void onFailureListener(int code, String error) {
                                                        }
                                                    }).postRequest();
                                            return true;
                                        }

                                        @Override
                                        public void stayTimeTooLong() {

                                        }
                                    });
                                }
                            });
                            clickTime = System.currentTimeMillis();
                            upRequest("AD_IMPRESSION");
                            upRequest("VIDEO_AD_START");
                        } catch (Exception e) {
                            ToastUtils.showLongToast(VideoAdForOupengAct.this, "获取视频失败！");
                            finish();
                            return;
                        }
                    }
                });
            }
        });
    }


    @Override
    protected void initViews() {
        iconIV = findViewById(R.id.iconIV);
        titleTV = findViewById(R.id.titleTV);
        subTitleTV = findViewById(R.id.subTitleTV);
        readGoldView = findViewById(R.id.ReadGoldView);
        readGoldView.setProcessClick(null);//

        mVideoView = findViewById(R.id.videoView);
        mWebview = findViewById(R.id.webview);
        mProgressbar = findViewById(R.id.progressbar);
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                ToastUtils.showLongToast(VideoAdForOupengAct.this, "播放失败！");
                finish();
                return false;
            }
        });
        mVideoView.setOnPreparedListener(mOnPreparedListener);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                upRequest("VIDEO_AD_END");
            }
        });
        installedReceiver = new VideoAdForOupengAct.MyInstalledReceiver();
        IntentFilter filter = new IntentFilter();

        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addDataScheme("package");
        this.registerReceiver(installedReceiver, filter);
        findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });
        findViewById(R.id.backIV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        EventBus.getDefault().register(this);

        mWebview.setWebViewClient(new WebViewClient());
        WebSettings settings = mWebview.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true); // 显示放大缩小 controler
        settings.setSupportZoom(true); // 可以缩放
        settings.setJavaScriptEnabled(true);
        mWebview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        click();
                        break;
                }
                return false;
            }
        });

    }

    // 当MediaPlayer准备好后触发该回调
    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mProgressbar.setVisibility(View.GONE);
            upRequest("VIDEO_AD_PLAY");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (mVideoView != null) mVideoView.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mVideoView != null) mVideoView.pause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownP.set((int) ev.getX(), (int) ev.getY());
                clickTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                mUpP.set((int) ev.getX(), (int) ev.getY());
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void click() {
        if (mBean == null || videoBean == null) return;
        if (isDowning) {
            ToastUtils.showLongToast(this, "任务进行中...");
            return;
        }
        upRequest("AD_CLICK");
        //交互类型：1=打开网页； 2=点击下载；3=Android；4=App store；5=下载；
        switch (videoBean.interaction_type) {
            case "WB":
                if (openDeepLink(replaceMacro(videoBean.clk_url), context)) {//打开了deeplink
                    upRequest("ACTIVE_END");
                } else {
                    upRequest("VIDEO_LDP_PV");
                    startActivityForResult(new Intent(this, AdBrowserActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("url", replaceMacro(videoBean.clk_url)), 1
                    );
                }
                break;
            case "DOWNLOAD":
                if (openDeepLink(videoBean.clk_url, context)) {//打开了deeplink
                    upRequest("ACTIVE_END");
                } else {
                    if(TextUtils.isEmpty(videoBean.app_package_name)){//空指针报错
                        return;
                    }
                    Long downID = openDownApk(replaceMacro(videoBean.clk_url), videoBean.app_package_name, this);
                    AdDownObserver.getInstance().addOnAdDownListener(downID, this);
                    onAdDownStartListener();
                }
                break;
        }

    }

    public boolean openDeepLink(String deeplink_url, Context context) {
        if (context == null || TextUtils.isEmpty(deeplink_url)) {
            return false;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink_url));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public Long openDownApk(String url, String apkName, Context context) {
        // 指定下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库
        request.allowScanningByMediaScanner();
        // 设置通知的显示类型，下载进行时和完成后显示通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 允许在计费流量下下载
        request.setAllowedOverMetered(false);
        // 允许该记录在下载管理界面可见
        request.setVisibleInDownloadsUi(false);
        // 允许漫游时下载
        request.setAllowedOverRoaming(true);
        // 允许下载的网路类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, apkName);
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        // 添加一个下载任务
        long downloadId = downloadManager.enqueue(request);
        return downloadId;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_video_ad;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        upRequest("VIDEO_AD_CLOSE");
        if (null != mVideoView) {
            mVideoView.pause();
            mVideoView.stopPlayback();
            mVideoView = null;
        }
        cancelCount();
        EventBus.getDefault().unregister(this);
        this.unregisterReceiver(installedReceiver);
    }

    @Override
    protected String setTitle() {
        return null;
    }

    @Override
    protected void myOnClick(View v) {
    }

    /**
     * AD_IMPRESSION 展现上报
     * AD_CLICK 点击上报
     * DOWN_LOAD_START 下载开始上报
     * DOWN_LOAD_END 下载完成上报
     * INSTALL_START 安装开始上报
     * INSTALL_END 安装完成上报
     * VIDEO_AD_START 视频开始播放上报
     * VIDEO_AD_END 视频正常播放结束上报
     * VIDEO_AD_PLAY 视频播放中上报
     * VIDEO_AD_CLOSE 视频关闭上报
     * VIDEO_LDP_PV 视频广告落地页展示上报
     * VIDEO_LDP_CLICK 视频广告落地页点击上报
     * VIDEO_LDP_CLOSE 视频广告落地页关闭上报
     * ACTIVE_END 应用激活上报信息
     */
    private void upRequest(String str) {
        if (mBean == null || mBean.getAds() == null || mBean.getAds().size() == 0) return;
        OupengResBean.AdsBean adsBean = mBean.getAds().get(0);
        List<OupengResBean.AdsBean.AdTrackingBean> ad_tracking = adsBean.ad_tracking;
        if (ad_tracking == null) return;
        //上报
        for (final OupengResBean.AdsBean.AdTrackingBean item : ad_tracking) {
            if (str.equals(item.tracking_event)) {
                if ("VIDEO_AD_PLAY".equals(str)) {
                    int point = item.point;
//                    mVideoView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            for (String url : item.tracking_url) {
//                                OkHttp3Utils.getInstance().doGetWithUA(VideoAdForOupengAct.this, replaceMacro(url), new Callback() {
//                                    @Override
//                                    public void onFailure(Call call, IOException e) {
//                                    }
//
//                                    @Override
//                                    public void onResponse(Call call, Response response) {
//                                    }
//                                });
//                            }
//                        }
//                    }, point);
                    mCount = new Timer();//可能多个上传
                    mTimes = point;
                    startTimmer(item.tracking_url);
                } else if ("VIDEO_AD_END".equals(str)) {
                    if (videoBean != null) {
                        if (!TextUtils.isEmpty(videoBean.video_ldpg_html)){
                            mWebview.setVisibility(View.VISIBLE);
                            mWebview.loadDataWithBaseURL("",videoBean.video_ldpg_html,"text/html;charset=utf-8","utf-8",null);
                        }
                    }
                    for (String url : item.tracking_url) {
                        OkHttp3Utils.getInstance().doGetWithUA(this, replaceMacro(url), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) {
                            }
                        });
                    }
                } else {
                    for (String url : item.tracking_url) {
                        OkHttp3Utils.getInstance().doGetWithUA(this, replaceMacro(url), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) {
                            }
                        });
                    }
                }
            }
        }
    }

    private void startTimmer(final List<String> strings) {
        if (mCount == null) return;
        try {
            mCount.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTimes--;
                            if (mTimes == 0) {
                                cancelCount();
                                //上传数据给第三方
                                for (final String url : strings) {
                                    OkHttp3Utils.getInstance().doGetWithUA(VideoAdForOupengAct.this, replaceMacro(url), new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            Log.w("QKD", "失败上传数据给第三方:"+url);
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) {
                                            Log.w("QKD", "成功上传数据给第三方:"+url);
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }, 1000, 1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void cancelCount() {
        if (null != mCount) {
            mCount.cancel();
            mCount = null;
        }
    }

    //点击监测宏替换
    private String replaceMacro(String str) {
        if (TextUtils.isEmpty(str)) return str;
        return str
                .replace("__REQ_WIDTH__", Config.SCREEN_WIDTH + "")//广告请求中的广告位宽，单位为像素
                .replace("__REQ_HEIGHT__", Config.SCREEN_HEIGHT + "")//广告请求中的广告位高，单位为像素_
                .replace("__WIDTH__", Config.SCREEN_WIDTH + "")//实际广告位的宽，单位为像素__
                .replace("__HEIGHT__", Config.SCREEN_HEIGHT + "")//实际广告位的高，单位为像素__
                .replace("__DOWN_X__", mDownP.x + "")//用户手指按下时的横坐标，广告位左上角为坐标原点_
                .replace("__DOWN_Y__", mDownP.y + "")//用户手指按下时的纵坐标，广告位左上角为坐标原点_
                .replace("__UP_X__", mUpP.x + "")//用户手指离开设备屏幕时的横坐标，广告位左上角为坐标原点_
                .replace("__UP_Y__", mUpP.y + "")//用户手指离开设备屏幕时的纵坐标，广告位左上角为坐标原点_
                ;
    }

    @Override
    public void onAdDownStartListener() {
        isDowning = true;
        upRequest("DOWN_LOAD_START");
    }

    @Override
    public void onAdDownCompleteListener() {
        isDowning = false;
        upRequest("DOWN_LOAD_END");
    }

    @Override
    public void onAdDownInstallStartListener() {
        upRequest("INSTALL_START");
    }

    @Override
    public void onAdDownInstallCompleteListener() {
    }

    public class MyInstalledReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {        // install
                if (mBean != null)
                    upRequest("INSTALL_END");
            }
        }
    }


    //刷新item
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReflashItem(WangMaiResBean.WxadBean.VideoBean.ExtBean msg) {
        click();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        upRequest("VIDEO_LDP_CLOSE");
    }
}
