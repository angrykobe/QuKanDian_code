package com.zhangku.qukandian.activitys.video;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.util.ArrayMap;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.tedcoder.wkvideoplayer.view.MediaController;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;
import com.umeng.analytics.MobclickAgent;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.activitys.BaseDetailsActivity;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.network.BaseModel;
import com.zhangku.qukandian.protocol.DecodeVideoJRTTProtocol;
import com.zhangku.qukandian.protocol.DecodeVideoTencentProtocol;
import com.zhangku.qukandian.utils.ActivityManager;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.DisplayUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;
import com.zhangku.qukandian.utils.VideoResolveJRTT;
import com.zhangku.qukandian.widght.ReadGoldProcessView;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by yuzuoning on 2017/4/1.
 * 视频详情页
 */
public class VideoDetailsActivity extends BaseDetailsActivity {
    private RelativeLayout mRelativeLayout;
    private SuperVideoPlayer mSuperVideoPlayer;
    private boolean isStartTimer = true;
    private String mTempUrl = "";
    private String url;

    @Override
    protected void getHeaderData(final DetailsBean bean) {
        if (null != bean) {
            Map<String, String> map = new ArrayMap<>();
            map.put("To", "" + bean.getChannelId());
            MobclickAgent.onEvent(VideoDetailsActivity.this, "VideoChannel", map);
            if (bean.getPostTextVideos().size() > 0) {
                for (int i = 0; i < bean.getPostTextVideos().size(); i++) {
                    String temp = bean.getPostTextVideos().get(i).getSrc();
                    if (!TextUtils.isEmpty(temp)) {
                        mTempUrl = temp;
                    }
                }
                Map<String, String> map1 = new HashMap<>();
                if (bean.getSourceType() != null && "今日头条".equals(bean.getSourceType().getSourceName())) {
                    playJRTTVideo(bean);
                    map1.put("视频", "今日头条");
                } else if (bean.getSourceType() != null && "腾讯视频".equals(bean.getSourceType().getSourceName())) {
                    playTencentVideo(bean);
                    map1.put("视频", "腾讯视频");
                } else {
                    map1.put("视频", "服务器视频");
                    setPlayData(mTempUrl, bean);
                }
                MobclickAgent.onEvent(this, "VideoSoure", map1);
            }
        }

    }

    private void playJRTTVideo(final DetailsBean bean) {
        new DecodeVideoJRTTProtocol(this, new BaseModel.OnResultListener<String>() {
            @Override
            public void onResultListener(String response) {
                setPlayData(response, bean);
            }

            @Override
            public void onFailureListener(int code, String error) {
//                hideLoadingLayout();
                if (bean.getPostTextVideos() == null)
                    return;
                if (bean.getPostTextVideos().size() == 0)
                    return;
                setPlayData(bean.getPostTextVideos().get(0).getSrc(), bean);
            }
        }).getVideoUrl(new VideoResolveJRTT().getUrl(bean.getPostTextVideos().get(0).getVid()));
    }

    //
    private int mTimes = 0;

    private void playTencentVideo(final DetailsBean bean) {
        new DecodeVideoTencentProtocol(this, new BaseModel.OnResultListener<String>() {
            @Override
            public void onResultListener(String response) {
                if (!TextUtils.isEmpty(response)) {
                    setPlayData(response, bean);
                    return;
                }
                mTimes++;
                if (mTimes < 3) {
                    playTencentVideo(bean);
                    return;
                }
                if (bean.getPostTextVideos() == null)
                    return;
                if (bean.getPostTextVideos().size() == 0)
                    return;
                setPlayData(bean.getPostTextVideos().get(0).getSrc(), bean);
            }

            @Override
            public void onFailureListener(int code, String error) {
                mTimes++;
                if (mTimes < 3) {
                    playTencentVideo(bean);
                    return;
                }
                if (bean.getPostTextVideos() == null)
                    return;
                if (bean.getPostTextVideos().size() == 0)
                    return;

                setPlayData(bean.getPostTextVideos().get(0).getSrc(), bean);
            }
        }).getUrl(bean.getPostTextVideos().get(0).getVid());
    }

    private void setPlayData(String response, DetailsBean bean) {
        hideLoadingLayout();
        url = response;
        if (TextUtils.isEmpty(url)) {
            url = mTempUrl;
        }

        if (null != mAdapter) {
            mAdapter.setHeaderData(bean);
            mAdapter.notifyDataSetChanged();
        }
        if (null != mSuperVideoPlayer) {
            mSuperVideoPlayer.setVisibility(View.VISIBLE);
        }

        boolean ignoreRemind = UserSharedPreferences.getInstance().getBoolean(Constants.VIDEO_WIFI_REMIND_CHECK, false);
        if (CommonHelper.isWifi(this) || ignoreRemind) {
            if (null != mSuperVideoPlayer) {
                Uri uri = null;
                if (url != null) {
                    uri = Uri.parse(url);
                } else {
                    try {
                        String src = mAdapter.getHeaderData().getPostTextVideos().get(0).getSrc();
                        uri = Uri.parse(src);
                    } catch (Exception e) {

                    }
                }
                if (uri != null) {
                    mSuperVideoPlayer.setAutoHideController(true);
                    mSuperVideoPlayer.loadAndPlay(uri, 0);
                    wifiRemindView.setVisibility(View.GONE);
                }
            }
        } else {
            wifiRemindView.setVisibility(View.VISIBLE);
            wifiRemindTV.setText(Html.fromHtml(getString(R.string.wifi_video_str, "一些")));
        }
    }

    @Override
    protected void getAboutData() {
        hideLoadingLayout();
    }

    @Override
    protected void initViews() {
        super.initViews();
        mRelativeLayout = (RelativeLayout) findViewById(R.id.video_player_layout);
        mSuperVideoPlayer = (SuperVideoPlayer) findViewById(R.id.video_player);
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
        mSuperVideoPlayer.setAutoHideController(true);

        readGoldView.setTouchListener(new ReadGoldProcessView.OnTouchViewListen() {
            @Override
            public boolean onTouchView(int position) {

                if (UserManager.getInst().hadLogin()) {
                    long duration = System.currentTimeMillis() - mStartTime;
                    readOn(Constants.TYPE_VIDEO, duration);
                }
                return true;
            }

            @Override
            public void stayTimeTooLong() {
//                ToastUtils.showShortToast(VideoDetailsActivity.this, "页面停留过久");
            }
        });
        findViewById(R.id.contineWatchTV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wifiNotRemindCB.isChecked()) {
                    UserSharedPreferences.getInstance().putBoolean(Constants.VIDEO_WIFI_REMIND_CHECK, true);
                }
                if (null != mSuperVideoPlayer) {
                    Uri uri = null;
                    if (url != null) {
                        uri = Uri.parse(url);
                    } else {
                        try {
                            String src = mAdapter.getHeaderData().getPostTextVideos().get(0).getSrc();
                            uri = Uri.parse(src);
                        } catch (Exception e) {

                        }
                    }
                    if (uri != null) {
                        mSuperVideoPlayer.setVisibility(View.VISIBLE);
                        mSuperVideoPlayer.loadAndPlay(uri, 0);
                        wifiRemindView.setVisibility(View.GONE);
                    }

                }
            }
        });
    }

    /**
     * 播放器的回调函数
     */
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
        /**
         * 播放器关闭按钮回调
         */
        @Override
        public void onCloseVideo() {
//            mSuperVideoPlayer.setVisibility(View.VISIBLE);
//            mSuperVideoPlayer.setAutoHideController(false);
//            Uri uri = Uri.parse(mTempUrl);
//            mSuperVideoPlayer.loadAndPlay(uri, 0);
//            mSuperVideoPlayer.close();//关闭VideoView
//            mSuperVideoPlayer.setVisibility(View.GONE);
//            resetPageToPortrait();
        }

        /**
         * 播放器横竖屏切换回调
         */
        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
                mFrameLayout.setVisibility(View.VISIBLE);
                mActionBar.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams lp = mRelativeLayout.getLayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = DisplayUtils.dip2px(VideoDetailsActivity.this, 200);
                mRelativeLayout.setLayoutParams(lp);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
                mFrameLayout.setVisibility(View.GONE);
                mActionBar.setVisibility(View.GONE);
                ViewGroup.LayoutParams lp = mRelativeLayout.getLayoutParams();
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
                mRelativeLayout.setLayoutParams(lp);
            }
        }

        /**
         * 播放完成回调
         */
        @Override
        public void onPlayFinish() {

        }

        @Override
        public void onStartPlay() {
            if (isStartTimer) {
                isStartTimer = false;
//                if (mHandler == null) return;
//                mHandler.postDelayed(mRunnable, Integer.valueOf(TextUtils.isEmpty(UserManager.vedioDuration) ? "20" : UserManager.vedioDuration) * 1000);
                readGoldView.setMaxProcess(Integer.valueOf(TextUtils.isEmpty(UserManager.vedioDuration) ? "20" : UserManager.vedioDuration));
                readGoldView.startTiming(1);
            }
        }

        @Override
        public void onPlayState(boolean playing) {
            readGoldView.setStopRun(!playing);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onEvent(this, "VideoDetails");
        MobclickAgent.onEvent(this, "DetailsCount");
        if (UserSharedPreferences.getInstance().getBoolean(Constants.REFRESH_FLAG, false)) {
            UserSharedPreferences.getInstance().putBoolean(Constants.REFRESH_FLAG, false);
            mAdapter.setRefresh();
        }
        readGoldView.setStopRun(false);
    }

    @Override
    public String setPagerName() {
        return "视频详情页";
    }

    @Override
    protected void releaseRes() {
        super.releaseRes();
        if (null != mSuperVideoPlayer) {
            mSuperVideoPlayer.close();
            mSuperVideoPlayer = null;
        }
        if (null != mHandler) {
//          mHandler.removeCallbacks(mRunnable);
            mHandler = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                mVideoPlayCallback.onSwitchPageType();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSuperVideoPlayer.pausePlay(true);
        readGoldView.setStopRun(true);
        if (isFinishing()) {
            readGoldView.setDestory();
            mSuperVideoPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        readGoldView.setDestory();
        mSuperVideoPlayer = null;
        ActivityManager.removeActivity(this);
    }
}
