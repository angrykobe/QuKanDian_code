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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.zhangku.qukandian.BaseNew.BaseAct;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.TaskWebViewAct;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.AdMissionOtherBean;
import com.zhangku.qukandian.bean.CityBean;
import com.zhangku.qukandian.bean.SubmitTaskBean;
import com.zhangku.qukandian.bean.TaskBean;
import com.zhangku.qukandian.biz.adbeen.wangmai.WangMaiResBean;
import com.zhangku.qukandian.biz.adcore.AdBrowserActivity;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.biz.adcore.wangmai.WangMaiNativeAdLoader;
import com.zhangku.qukandian.dialog.DialogConfirm;
import com.zhangku.qukandian.interfaces.ApplicationGetIp;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.observer.AdDownObserver;
import com.zhangku.qukandian.protocol.DynmicmissionProtocol;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CustomToast;
import com.zhangku.qukandian.utils.GlideUtils;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 创建者          xuzhida
 * 创建日期        2018/12/12
 * 你不注释一下？
 */
public class VideoAdAct extends BaseAct implements AdDownObserver.OnAdDownListener {
    private Point mDownP = new Point(-999, -999);
    private Point mUpP = new Point(-999, -999);
    private WangMaiResBean.WxadBean mBean;
    private DownloadManager downloadManager;
    private boolean isDowning = false;
    private long clickTime;
    private VideoView mVideoView;
    private MyInstalledReceiver installedReceiver;
    private View mProgressbar;
    private ImageView iconIV;
    private TextView titleTV;
    private TextView subTitleTV;
    private ReadGoldView readGoldView;

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
                OkHttp3Utils.getInstance().doPostJson(AdConfig.wangmai_address, new WangMaiNativeAdLoader().getReqBean(new AdLocationBeans.AdLocationsBean.ClientAdvertisesBean(0, 0, 0), cityBean), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            String string = response.body().string();
                            WangMaiResBean wangMaiResBean = new Gson().fromJson(string, WangMaiResBean.class);
                            mBean = wangMaiResBean.getWxad();
                            if (mBean == null) {
                                ToastUtils.showLongToast(VideoAdAct.this, "获取视频失败！");
                                finish();
                                return;
                            }
                            upRequest(mBean.getWin_notice_url());

                            final Uri uri = Uri.parse(mBean.getVideo().getV_url());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final WangMaiResBean.WxadBean.VideoBean.ExtBean ext = mBean.getVideo().getExt();
                                    if (ext != null) {
                                        findViewById(R.id.bottomView).setVisibility(View.VISIBLE);
                                        GlideUtils.displayRoundImage(VideoAdAct.this, ext.getEndiconurl(), iconIV, 5);
                                        titleTV.setText("" + ext.getEndtitle());
                                        subTitleTV.setText("" + ext.getEnddesc());
                                        findViewById(R.id.detailTV).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                ActivityUtils.startToVideoAdEndCardAct(VideoAdAct.this, ext,mBean.getImage_src());
                                            }
                                        });
                                    }

                                    mVideoView.setVideoURI(uri);
                                    mVideoView.start();

                                    //广告素材为视频时，对视频播放阶段的上报
                                    WangMaiResBean.WxadBean.VideoBean.TranckingBean v_tracking = mBean.getVideo().getV_tracking();
                                    if (v_tracking != null) {
                                        List<WangMaiResBean.WxadBean.VideoBean.TranckingBean.ProgressTrackingEventBean> v_progress_tracking_event = v_tracking.getV_progress_tracking_event();
                                        if (v_progress_tracking_event != null && v_progress_tracking_event.size() != 0) {
                                            for (final WangMaiResBean.WxadBean.VideoBean.TranckingBean.ProgressTrackingEventBean bean : v_progress_tracking_event) {
                                                mVideoView.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        upRequest(bean.getUrl());
                                                    }
                                                }, bean.getMillisec());
                                            }
                                        }
                                    }

                                    final TaskBean bean = (TaskBean) getIntent().getSerializableExtra("bean");
                                    readGoldView.setVisibility(View.VISIBLE);
                                    readGoldView.setMaxProcess(bean.getDuration() == 0 ? mBean.getVideo().getDuration() : bean.getDuration());
                                    readGoldView.startTiming(1);
                                    readGoldView.setTouchListener(new ReadGoldProcessView.OnTouchViewListen() {
                                        @Override
                                        public boolean onTouchView(int position) {
                                            //动态任务完成接口
                                            new DynmicmissionProtocol(VideoAdAct.this,
                                                    new AdMissionOtherBean(bean.getName(), bean.getTypeId(), MachineInfoUtil.getInstance().getIMEI(VideoAdAct.this)),
                                                    new BaseModel.OnResultListener<SubmitTaskBean>() {
                                                        @Override
                                                        public void onResultListener(SubmitTaskBean response) {
                                                            CustomToast.showToast(VideoAdAct.this, response.getGoldAmount() + "", response.getDescription());
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
                        } catch (Exception e) {
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
        mProgressbar = findViewById(R.id.progressbar);
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                ToastUtils.showLongToast(VideoAdAct.this, "播放失败！");
                finish();
                return false;
            }
        });
        mVideoView.setOnPreparedListener(mOnPreparedListener);

        installedReceiver = new MyInstalledReceiver();
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
    }

    // 当MediaPlayer准备好后触发该回调
    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mProgressbar.setVisibility(View.GONE);
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
        if (mBean == null) return;
        if (isDowning) {
            ToastUtils.showLongToast(this, "任务进行中...");
            return;
        }
        upRequest(mBean.getClick_url());
        //交互类型：1=打开网页； 2=点击下载；3=Android；4=App store；5=下载；
        switch (mBean.getInteraction_type()) {
            case 1:
                if (openDeepLink(mBean.getDeep_link(), context)) {//打开了deeplink
                    upRequest(mBean.getAction_track_urls());
                } else {
                    startActivity(new Intent(this, AdBrowserActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("url", replaceMacro(mBean.getLanding_page_url()))
                    );
                }
                break;
            case 2:
                if (openDeepLink(mBean.getDeep_link(), context)) {//打开了deeplink
                    upRequest(mBean.getAction_track_urls());
                } else {
                    Long downID = openDownApk(mBean.getLanding_page_url(), mBean.getAd_title(), this);
                    AdDownObserver.getInstance().addOnAdDownListener(downID, this);
                    onAdDownStartListener();
                }
                break;
            case 3:
                if (openDeepLink(mBean.getDeep_link(), context)) {//打开了deeplink
                    upRequest(mBean.getAction_track_urls());
                } else {
                    startActivity(new Intent(this, AdBrowserActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            .putExtra("url", replaceMacro(mBean.getLanding_page_url()))
                    );
                }
                break;
            case 5:
            default:
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
        if (null != mVideoView) {
            mVideoView.pause();
            mVideoView.stopPlayback();
            mVideoView = null;
        }
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

    private void upRequest(List<String> list) {
        if (list == null) return;
        //上报点击
        for (final String item : list) {
            final String url = replaceMacro(item);
            OkHttp3Utils.getInstance().doGetWithUA(this, url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, Response response) {
                }
            });
        }
    }

    //点击监测宏替换
    private String replaceMacro(String str) {
        if (TextUtils.isEmpty(str)) return str;
        return str.replace("__down_x__", mDownP.x + "")//用户手指按下时的横坐标,如无法获取时，请填“-999”，替换 __down_x__
                .replace("__down_y__", mDownP.y + "")//用户手指按下时的纵坐标,如无法获取时，请填“-999”，替换 __down_y__
                .replace("__up_x__", mUpP.x + "")//用户手指离开手机屏幕时的横坐标,如无法获取时，请填“-999”，替换 __up_x__
                .replace("__up_y__", mUpP.y + "")//用户手指离开手机屏幕时的纵坐标,如无法获取时，请填“-999”，替换 __up_y__
                .replace("__re_down_x__", mDownP.x + "")//用户手指按下时的相对横坐标,如无法获取时，请填“-999”，替换 __re_down_x__
                .replace("__re_down_y__", mDownP.y + "")//用户手指按下时的相对纵坐标,如无法获取时，请填“-999”，替换 __re_down_y__
                .replace("__re_up_x__", mUpP.x + "")//用户手指离开手机屏幕时的相对横坐标,如无法获取时，请填“-999”，替换 __re_up_x__
                .replace("__re_up_y__", mUpP.y + "")//用户手指离开手机屏幕时的相对纵坐标,如无法获取时，请填“-999”，替换 __re_up_y__
//                .replace("__CLICKID__", localPositionUp[1] + "")//interaction_type=3的情况下，请将该宏使用请求落地页返回的Json结果集用clickid字段的值
                .replace("__utc_ts__", clickTime + "")//当前业务开始时刻的UTC时间戳（例如点击按下，开始展现）
                .replace("__utc_end_ts__", System.currentTimeMillis() + "")//当前业务结束时刻的UTC时间戳（例如点击抬起）
                ;
    }

    @Override
    public void onAdDownStartListener() {
        isDowning = true;
        upRequest(mBean.getDownload_track_urls());
    }

    @Override
    public void onAdDownCompleteListener() {
        isDowning = false;
        upRequest(mBean.getDownloaded_track_urls());

    }

    @Override
    public void onAdDownInstallStartListener() {
    }

    @Override
    public void onAdDownInstallCompleteListener() {
    }

    public class MyInstalledReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {        // install
                if (mBean != null)
                    upRequest(mBean.getInstalled_track_urls());
            }
        }
    }


    //刷新item
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReflashItem(WangMaiResBean.WxadBean.VideoBean.ExtBean msg) {
        click();
    }

}
