package com.zhangku.qukandian.biz.adcore;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;

import com.zhangku.qukandian.activitys.ad.AdWebViewAct;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.observer.AdDownObserver;
import com.zhangku.qukandian.protocol.PostNewAwakenInfor;
import com.zhangku.qukandian.utils.ActivityUtils;
import com.zhangku.qukandian.utils.CommonHelper;
import com.zhangku.qukandian.utils.FileBuildForBugUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class ZkNativeAdBase {

    private DownloadManager downloadManager;
    private PostNewAwakenInfor postNewAwakenInfor;
    public static final String STAER_DOWN = "startDown";
    public static final String COMPLETE_DOWN = "CompleteDown";
    public static final String STAER_INSTANLL = "startInstanll";
    public static final String COMPLETE_INSTANLL = "CompleteInstanll";
    public static final String COMPLETE_OPEN = "CompleteOpen";

    public void openUrl(Context context, String url, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        if (bean.getDeliveryMode() == 1) {
            ActivityUtils.startToAdWebViewAct(context, url, bean);
        } else {
            ActivityUtils.startToUrlActivity(context, url, Constants.URL_FROM_ADS, true, bean);
        }
    }

    public boolean openDeepLink(String deeplink_url, Context context, AdLocationBeans.AdLocationsBean.ClientAdvertisesBean bean) {
        if (context == null || TextUtils.isEmpty(deeplink_url)) {
            return false;
        }
        try {
            if (bean.isAwaken()) {
                return CommonHelper.openDeeplink(context, deeplink_url);
            } else {
                if (postNewAwakenInfor == null) {
                    postNewAwakenInfor = new PostNewAwakenInfor(context, bean, null);
                    postNewAwakenInfor.postRequest();
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public Long openDownApk(String url, String apkName, Context context) {
        if (TextUtils.isEmpty(apkName)) {
            apkName = UUID.randomUUID() + ".apk";
        }
        if (!apkName.endsWith(".apk")) {
            apkName += ".apk";
        }
        // 指定下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库
        request.allowScanningByMediaScanner();
        // 设置通知的显示类型，下载进行时和完成后显示通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置通知栏的标题，如果不设置，默认使用文件名
//        request.setTitles("This is title");
        // 设置通知栏的描述
//        request.setDescription("This is description");
        // 允许在计费流量下下载
        request.setAllowedOverMetered(false);
        // 允许该记录在下载管理界面可见
        request.setVisibleInDownloadsUi(false);
        // 允许漫游时下载
        request.setAllowedOverRoaming(true);
        // 允许下载的网路类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // 设置下载文件保存的路径和文件名
//        String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, apkName);
//        另外可选一下方法，自定义下载路径
//        request.setDestinationUri()
//        request.setDestinationInExternalFilesDir()
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        // 添加一个下载任务
        final long downloadId = downloadManager.enqueue(request);
        return downloadId;
    }
}
