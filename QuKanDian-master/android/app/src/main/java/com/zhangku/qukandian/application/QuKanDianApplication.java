package com.zhangku.qukandian.application;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.aitangba.swipeback.ActivityLifecycleHelper;
import com.ak.android.shell.AKAD;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.Utils;
import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdManager;
import com.bytedance.sdk.openadsdk.TTAdManagerFactory;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.cmcm.cmgame.CmGameSdk;
import com.cmcm.cmgame.gamedata.CmGameAppInfo;
import com.db.ta.sdk.TaSDK;
import com.duoyou.ad.openapi.DyAdApi;
import com.felink.adSdk.AdManager;
import com.fm.openinstall.OpenInstall;
import com.google.gson.Gson;
import com.hmob.hmsdk.HMSDK;
import com.iBookStar.views.YmConfig;
import com.sdk.searchsdk.DKSearch;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yilan.sdk.ui.YLUIInit;
import com.zhangku.qukandian.BuildConfig;
import com.zhangku.qukandian.bean.CityBean;
import com.zhangku.qukandian.bean.DetailsBean;
import com.zhangku.qukandian.bean.DeviceInfoBeen;
import com.zhangku.qukandian.bean.InformationBean;
import com.zhangku.qukandian.bean.LocalFavoriteBean;
import com.zhangku.qukandian.bean.PostTextVideosBean;
import com.zhangku.qukandian.biz.adcore.AdConfig;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.interfaces.ApplicationGetIp;
import com.zhangku.qukandian.manager.DBTools;
import com.zhangku.qukandian.manager.UserManager;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.CommonUtil;
import com.zhangku.qukandian.utils.DeviceUtil;
import com.zhangku.qukandian.utils.FileBuildForBugUtils;
import com.zhangku.qukandian.utils.LogUtils;
import com.zhangku.qukandian.utils.OkHttp3Utils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.AdsRecordUtils;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.SpConstants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.SpUtils;
import com.zhangku.qukandian.utils.TimeUtils;

import org.litepal.LitePalApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jiguang.adsdk.api.JSSPInterface;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.blankj.utilcode.util.ProcessUtils.isMainProcess;
import static org.litepal.crud.DataSupport.deleteAllAsync;

public class QuKanDianApplication extends LitePalApplication {

    private static Context mContext;
    private static int mCode = 0;
    public static String mUmen;
    public static String mUserAgent;
    public static DeviceInfoBeen mDeviceInfo;
    public static long mTimeOffset = 0;
    public static IWXAPI wxApi;
    private static CityBean cityBean;
    public static boolean isFirst = true;//我的弹框判断
    public static TTAdManager ttAdManager;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary.install(this);

        mContext = getApplicationContext();
        getPhoneScreenSize();
        mCode = CommonHelper.getVersionCode(mContext);
        mUmen = CommonHelper.getAppMetaData(this, "UMENG_CHANNEL");
        // 微信新版sdk初始化，通过WXAPIFactory工厂，获取IWXAPI的实例
        wxApi = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, false);

        mUserAgent = DeviceUtil.getUserAgent(this);
        mDeviceInfo = DeviceUtil.getDeviceInfo(this);

        String processName = getProcessName(this, android.os.Process.myPid());
        if ((processName != null && processName.endsWith("com.zhangku.qukandian"))) {
            initApp();
        }
        getCityBeanFirst(null);
        ttAdManager = TTAdManagerFactory.getInstance(this);
        doInit(ttAdManager, this);
        initCurrDate();

        //工具类初始化
        Utils.init(this);
//        initLog();
//        initCrash();

        //分享收徒邀请
        if (isMainProcess()) {
            OpenInstall.init(this);
        }
        //好兔视频
        initHaoTuVideo();

        //豹趣游戏
        initCmGameSdk();
        //多游游戏
        initDuoYou();
    }
     //多游游戏初始化
    private void initDuoYou() {
        DyAdApi.getDyAdApi().init("dy_59610651", "e283d5eaa2d16c21d70cb2d1e73b2096");
    }

    //step1:接入网盟广告sdk的初始化操作，详情见接入文档和穿山甲平台说明
    private static void doInit(TTAdManager ttAdManager, Context context) {
        ttAdManager.setAppId("5001141")
                .isUseTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                .setName("APP测试媒体").setTitleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                .setAllowShowNotifiFromSDK(true) //是否允许sdk展示通知栏提示
                .setAllowLandingPageShowWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                .openDebugMode() //测试阶段打开，可以通过日志排查问题，上线时去除该调用
//                .setGlobalAppDownloadListener(new AppDownloadStatusListener(context)) //下载任务的全局监听
                .setDirectDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G); //允许直接下载的网络状态集合
    }

    private void initApp() {
        HMSDK.init(this, AdConfig.yitan_appid);
        //点开 初始化广告SDK
        com.iBookStar.views.YmConfig.init(this, AdConfig.yuemeng_appid_for_code, AdConfig.yuemeng_read_appid);
        //阅盟小说
        YmConfig.initNovel(this, AdConfig.yuemeng_novel_appid);
        //极光广告
        JSSPInterface.init(this);
        //极光推送
//        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        //风灵广告初始化
        AdManager.init(this, AdConfig.felink_AdAppid, AdConfig.touTiao_AdAppid);

        TaSDK.init(this);

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.LogE(" onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

        registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());
        AKAD.initSdk(getApplicationContext(), false, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        // umeng社会化组件初始化
        MobclickAgent.openActivityDurationTrack(true);
        UMShareAPI.get(this);
        PlatformConfig.setWeixin(Constants.WX_APP_ID, Constants.WX_APP_SECRET);//趣看视界
        PlatformConfig.setQQZone("1106101220", "IM5V7I1IFT8p77zz");
        PlatformConfig.setSinaWeibo("2493857704", "82b9ea783f4005c9dedccbf905ebc30a", "https://sns.whalecloud.com/sina2/callback");
        // 初始化Bugly
        CommonUtil.initBugly(mContext);
        appToTop();


    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
//        Glide.with(this).onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        super.onLowMemory();
//        Glide.with(this).onLowMemory();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static int getCode() {
        return mCode;
    }

    private void getPhoneScreenSize() {
        Config.SCREEN_HEIGHT = CommonHelper.getScreenHeight(mContext);
        Config.SCREEN_WIDTH = CommonHelper.getScreenWidth(mContext);
    }

    public static Context getmContext() {
        return mContext;
    }

    public static void getCityBeanFirst(final ApplicationGetIp getIp) {
        if (cityBean == null) {
            OkHttp3Utils.getInstance().doGet("http://pv.sohu.com/cityjson?ie=utf-8", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    QuKanDianApplication.CityBeanHandle(getIp);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String string = response.body().string();
                        if (!TextUtils.isEmpty(string)) {
                            String json = string.substring(string.indexOf("{"), string.lastIndexOf("}") + 1);
                            cityBean = new Gson().fromJson(json, CityBean.class);
                        }
                        if (getIp != null)
                            getIp.getIp(cityBean);
                    } catch (Exception e) {
                        QuKanDianApplication.CityBeanHandle(getIp);
                    }

                }
            });
        } else {
            if (getIp != null)
                getIp.getIp(cityBean);
        }
    }

    private static void CityBeanHandle(final ApplicationGetIp getIp) {
        String ip = CommonUtil.GetLocalIP();
        cityBean = new CityBean();
        cityBean.setCip(ip);

        if (getIp != null)
            getIp.getIp(cityBean);
    }

    public static CityBean getCityBean() {
        return cityBean;
    }

    private void appToTop() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            public int count = 0;

            @Override
            public void onActivityStopped(Activity activity) {
                count--;
                if (count == 0) {//切换后台进行统计
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
//                FileBuildForBugUtils.Log(""+activity.getClass().getName());
                if (count == 0) {//进入前端进行统计
                    String nowDay = TimeUtils.formatPhotoDate(System.currentTimeMillis());
                    String cacheDay = AdsRecordUtils.getInstance().getString(Constants.IS_NEW_DAY, "");
                    if (!nowDay.equals(cacheDay)) {
                        AdsRecordUtils.getInstance().clear();
                        AdsRecordUtils.getInstance().putString(Constants.IS_NEW_DAY, nowDay);//保存本条链接，下次链接不展示红包
                        DBTools.clearMyselfAdBean();
                        deleteAllAsync(DetailsBean.class);
                        deleteAllAsync(InformationBean.class);
                    }
                }
                count++;
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }
        });
    }

    private void initCurrDate() {
        String currDate = TimeUtils.timeFormat(System.currentTimeMillis(), "yyyy-MM-dd");
        String lastDate = SpUtils.getInstance().getString(SpConstants.CURR_DATE, "");
        // 每天第一次启动
        if (!currDate.equals(lastDate)) {
            SpUtils.getInstance().putString(SpConstants.CURR_DATE, currDate);
            SpUtils.getInstance().remove(SpConstants.ME_DIALOG_ONEDAY_COUNT);
            SpUtils.getInstance().remove(SpConstants.SENSOR_TIMES);
        }
    }

    // init it in ur application
    public void initLog() {
        String dir = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = Environment.getExternalStorageDirectory().getPath() + System.getProperty("file.separator") + "qukandian";
        }
        final com.blankj.utilcode.util.LogUtils.Config config = com.blankj.utilcode.util.LogUtils.getConfig()
//                .setLogSwitch(BuildConfig.DEBUG)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(true)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(true)// 打印 log 时是否存到文件的开关，默认关
                .setDir(dir)// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("qukandian-log")// 当文件前缀为空时，默认为"util"，即写入文件为"util-yyyy-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setConsoleFilter(com.blankj.utilcode.util.LogUtils.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(com.blankj.utilcode.util.LogUtils.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1)// log 栈深度，默认为 1
                .setStackOffset(0)// 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setSaveDays(3)// 设置日志可保留天数，默认为 -1 表示无限时长
                // 新增 ArrayList 格式化器，默认已支持 Array, Throwable, Bundle, Intent 的格式化输出
                .addFormatter(new com.blankj.utilcode.util.LogUtils.IFormatter<ArrayList>() {
                    @Override
                    public String format(ArrayList list) {
                        return "LogUtils Formatter ArrayList { " + list.toString() + " }";
                    }
                });
//        LogUtils.d(config.toString());
    }

    @SuppressLint("MissingPermission")
    private void initCrash() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        CrashUtils.init(new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
//                com.blankj.utilcode.util.LogUtils.e(crashInfo);
                AppUtils.relaunchApp();
            }
        });
    }

    private void initHaoTuVideo() {
        try {
            int userId = UserManager.getInst().getUserBeam().getId();
            YLUIInit.getInstance()
                    .setApplication(this)
                    .setAccessKey("yld2njunbuh1")//设置accesskey
                    .setAccessToken("kduh5olbw80qvze2fao1ic0xyc4mk1pn")//设置token
                    .setUid(userId + "")//设置登录用户id
                    .build();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * 游戏SDK初始化
     */
    private void initCmGameSdk() {
        // 注意：如果有多个进程，请务必确保这个初始化逻辑只会在一个进程里运行
        // 注意：如果有多个进程，请务必确保这个初始化逻辑只会在一个进程里运行
        // 注意：如果有多个进程，请务必确保这个初始化逻辑只会在一个进程里运行

        final String adAppId = "5001141";   // 穿山甲appid
//        final String adAppId = "5001121";   // 穿山甲appid  demo

        TTAdSdk.init(this,
                new TTAdConfig.Builder()
                        .appId(adAppId)
                        .useTextureView(false) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                        .appName("APP测试媒体")
                        .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                        .allowShowNotify(true) //是否允许sdk展示通知栏提示
                        .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                        .debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                        .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI, TTAdConstant.NETWORK_STATE_3G) //允许直接下载的网络状态集合
                        .supportMultiProcess(false) //是否支持多进程，true支持
                        .build());


        CmGameAppInfo cmGameAppInfo = new CmGameAppInfo();
        cmGameAppInfo.setAppId("qukandian");                             // GameSdkID，向我方申请
        cmGameAppInfo.setAppHost("https://qkd-xyx-sdk-svc.beike.cn");   // 游戏host地址，向我方申请
//        cmGameAppInfo.setAppId("demo");                             // GameSdkID，向我方申请
//        cmGameAppInfo.setAppHost("https://xyx-sdk-svc.cmcm.com");   // 游戏host地址，向我方申请


        CmGameAppInfo.TTInfo ttInfo = new CmGameAppInfo.TTInfo();
        ttInfo.setRewardVideoId("901141435");
        ttInfo.setFullVideoId("901141694");
        ttInfo.setInterId("901141360");
        ttInfo.setNative_banner_id("901141197");
        cmGameAppInfo.setTtInfo(ttInfo);

        CmGameSdk.INSTANCE.initCmGameSdk(this, cmGameAppInfo, new com.zhangku.qukandian.utils.Img.CmGameImageLoader(), BuildConfig.DEBUG);
        Log.d("cmgamesdk", "current sdk version : " + CmGameSdk.INSTANCE.getVersion());
    }
}
