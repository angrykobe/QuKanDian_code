package com.zhangku.qukandian.biz.adpackage.lezhenshiwan;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.RemoteViews;
import android.widget.Toast;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class X5JavaScriptInterface {

    private Activity mActivity;

    public static WebView mWebView;

    private Handler mHandler = new Handler();

    public X5JavaScriptInterface(Activity activity, WebView webview) {
        this.mActivity = activity;
        this.mWebView = webview;
    }

    /**
     * 判断应用示是否安装
     *
     * @param packageName 应用包名
     * @return true 已经安装，false 未安装
     */
    @JavascriptInterface
    public void CheckAppIsInstall(final String packageName) {

        if (null == mActivity || null == mWebView || TextUtils.isEmpty(WowanIndex.mStringKey)) {
            return;
        }

        final String[] versionCodeArray = new String[1];

        boolean flag = false;
        PackageManager pm = mActivity.getPackageManager();
        List<PackageInfo> pakageinfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo pi : pakageinfos) {
            if (packageName.equalsIgnoreCase(pi.packageName)) {
                versionCodeArray[0] = String.valueOf(pi.versionCode);
                flag = true;
            }
        }
        if (flag) {// 表示已经安装了
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    // CheckAppCallback(BoundleId,Version,Sign);
                    String BoundleId = packageName;
                    String Sign = PlayMeUtils.encrypt(packageName + versionCodeArray[0] + WowanIndex.mStringKey);
                    mWebView.loadUrl("javascript:CheckAppCallback('" + BoundleId + "','" + versionCodeArray[0] + "','"
                            + Sign + "')");
                }
            });
        } else {
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:CheckAppNoInstall()");
                }
            });
        }
    }

    /**
     * @param packageName 指定打开应用的包名
     */
    @JavascriptInterface
    public void startAnotherApp(String packageName) {
        try {
            if (mActivity == null || TextUtils.isEmpty(packageName)) {
                return;
            }

            if (!isAPKinstall(mActivity, packageName)) {
                return;
            }

            // 打开指定包名的应用
            startAnotherApp(mActivity, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断应用示是否安装
     *
     * @param packageName 应用包名
     * @return true 已经安装，false 未安装
     */
    public static boolean isAPKinstall(Context context, String packageName) {
        boolean flag = false;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> pakageinfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo pi : pakageinfos) {
            if (packageName.equalsIgnoreCase(pi.packageName)) {
                flag = true;
                break;
            }
        }

        if (!flag) {
            PackageInfo packageInfo;
            try {
                packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            } catch (NameNotFoundException e) {
                packageInfo = null;
                e.printStackTrace();
            }
            if (packageInfo == null) {
                flag = false;
            } else {
                flag = true;
            }
        }

        return flag;
    }

    /**
     * 根据包名打开APP
     *
     * @param packageName
     */
    public static void startAnotherApp(Context context, String packageName) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);
            List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件保存地址
     *
     * @return
     */
//	public static String getDownPath() {
//		File sdDir = null;
//		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
//		if (sdCardExist) {
//			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
//		} else {
//			return "";
//		}
//		return sdDir.toString() + "/wowansdk";
//	}

    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    public void install(Context context, File file) {

        try {

            boolean haveInstallPermission;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 先判断是否有安装未知来源应用的权限
                haveInstallPermission = mActivity.getPackageManager().canRequestPackageInstalls();
                if (!haveInstallPermission) {
                    // 弹框提示用户手动打开
                    Uri packageURI = Uri.parse("package:" + mActivity.getPackageName());
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                    intent.putExtra("path", file.getPath());
                    mActivity.startActivityForResult(intent, 101);
                    return;
                }
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 24) { // 判读版本是否在7.0以上
                // 参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致 参数3
                Uri apkUri = FileProvider.getUriForFile(context, mActivity.getPackageName() + ".fileProvider", file);

                // 添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    // 权限sd卡
    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private ServiceConnection conn;

    /**
     * 给js的多任务下载的方法 isInstall 是否自动安装 0 -- 不自动安装 1--自动安装
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @JavascriptInterface
    public void downloadApkFile(final int whichTask, final int isInstall, String url) {
        try {
            // Check if we have write permission
            int permission = ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(mActivity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                return;
            }

            if (mActivity == null || mWebView == null) {
                return;
            }

            // 如果下载地址是一个重定向地址 就得先取最终的地址
            if (!url.contains(".apk")) {
                url = redirectPath(url);
            }


            File sdDir = null;
            String sdPath = "";
            boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
            if (sdCardExist) {
                sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            }
            if (sdDir != null) {
                sdPath = sdDir.toString() + "/wowan";
            }

            if (TextUtils.isEmpty(sdPath)) {
                Toast.makeText(mActivity, "请插入SD卡", Toast.LENGTH_LONG).show();
                return;
            }

            sdDir = new File(sdPath);
            if (!sdDir.exists()) {
                sdDir.mkdir();
            }

            //cid_adid.apk
            String mApkName = whichTask + ".apk";
            if (mActivity instanceof DetailActivity) {
                DetailActivity activity = (DetailActivity) mActivity;
                String cid = activity.getmStringCid();
                mApkName = cid + "_" + mApkName;
            }

            DownloadManager downloadManager = (DownloadManager) mActivity.getSystemService(Context.DOWNLOAD_SERVICE);

            SharedPreferences sp = mActivity.getSharedPreferences("wowan", Activity.MODE_PRIVATE);
            //查询id的下载状况
            long aLong = sp.getLong(whichTask + "", 0);
            if (aLong != 0) {
                int[] bytesAndStatus = PlayMeUtils.getBytesAndStatus(aLong, downloadManager);
                if (bytesAndStatus[2] == DownloadManager.STATUS_SUCCESSFUL) {
                    //下载成功，获取uri
                    Uri downIdUri = downloadManager.getUriForDownloadedFile(aLong);
                    if (null != downIdUri && !TextUtils.isEmpty(downIdUri.toString())) {
                        //判断安装文件是否存在,若果存在，安装，否则，走下载流程
                        File apkFile = new File(sdPath, mApkName);
                        if (apkFile != null && apkFile.exists()) {
                            //开始安装
                            //开始安装
                            PlayMeUtils.install(mActivity, apkFile);
                            //通知js下载成功
                            if (null != mWebView) {
                                final String path = sp.getString(aLong + "path", "");
                                final String adid = sp.getString(aLong + "adid", "");

                                mWebView.post(new Runnable() {

                                    @Override
                                    public void run() {
                                        X5JavaScriptInterface.mWebView.loadUrl("javascript:downloadApkFileFinishListener("
                                                + adid + ",'" + path + "')");

                                    }
                                });
                            }


                            return;
                        }

                    }

                }
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setAllowedOverRoaming(false);//漫游网络是否可以下载
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            //在通知栏中显示，默认就是显示的
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setVisibleInDownloadsUi(true); //显示下载进度

            request.setDestinationInExternalPublicDir("/wowan/", mApkName);

            request.allowScanningByMediaScanner();  //准许被系统扫描到
            long downloadId = downloadManager.enqueue(request);


            sp.edit().putLong(whichTask + "", downloadId).commit();//保存此次下载ID
            sp.edit().putString(downloadId + "path", sdPath + "/" + mApkName).commit();//保存此次下载的本地路径
            sp.edit().putString(downloadId + "adid", whichTask + "").commit();//保存此次下载的adid

            //通知页面开始查询进度
            if (mActivity instanceof DetailActivity) {
                DetailActivity activity = (DetailActivity) mActivity;
                activity.startCheckProgressStates();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取重定向后最终地址 2016/08/04 add wangjun
    public String redirectPath(String str) {
        URL url = null;
        String realURL = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(str);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("User-Agent", "PacificHttpClient");
            conn.setInstanceFollowRedirects(true);
            conn.getResponseCode();// trigger server redirect
            realURL = conn.getURL().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return realURL;
    }


//
//                    if (X5JavaScriptInterface.mWebView != null) {
//                        X5JavaScriptInterface.mWebView.post(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                X5JavaScriptInterface.mWebView.loadUrl("javascript:downloadApkFileErrorListener("
//                                        + task.getTag() + ",'" + e.toString() + "')");
//
//                            }
//                        });
//                    }


    /**
     * 给js的安装apk的方法 //id：apk标识， path: 本地apk路径 InstallApkListener(int id,int
     * status,String msg){} //id：apk标识，status:安装状态（0唤起安装失败，1唤起安装成功）， msg: 消息
     */

    @JavascriptInterface
    public void InstallApk(final int whichTask, String path) {
        if (TextUtils.isEmpty(path)) {
            if (X5JavaScriptInterface.mWebView != null) {
                X5JavaScriptInterface.mWebView.post(new Runnable() {

                    @Override
                    public void run() {
                        X5JavaScriptInterface.mWebView.loadUrl(
                                "javascript:InstallApkListener(" + whichTask + "," + 0 + ",'" + "安装路径为空" + "')");

                    }
                });
            }
            return;
        }
        File localFile = new File(path);
        if (!localFile.exists()) {
            if (X5JavaScriptInterface.mWebView != null) {
                X5JavaScriptInterface.mWebView.post(new Runnable() {

                    @Override
                    public void run() {
                        X5JavaScriptInterface.mWebView.loadUrl(
                                "javascript:InstallApkListener(" + whichTask + "," + 0 + ",'" + "安装路径不存在" + "')");

                    }
                });
            }
            return;
        }

        install(mActivity, localFile);
        if (X5JavaScriptInterface.mWebView != null) {
            X5JavaScriptInterface.mWebView.post(new Runnable() {

                @Override
                public void run() {
                    X5JavaScriptInterface.mWebView
                            .loadUrl("javascript:InstallApkListener(" + whichTask + "," + 1 + ",'" + "唤起安装成功" + "')");

                }
            });
        }

    }

    /**
     * 根据指定的包名卸载apk
     */
    @JavascriptInterface
    public void uninstallApk(String packName) {
        if (TextUtils.isEmpty(packName) || null == mActivity) {
            return;
        }
        Uri packageURI = Uri.parse("package:" + packName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        mActivity.startActivity(uninstallIntent);
    }

    /**
     * 刷新当前页面
     */
    @JavascriptInterface
    public void RefreshWeb() {
        if (null == mHandler || null == mWebView)
            return;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String subUrl = mWebView.getUrl();
                if (!TextUtils.isEmpty(subUrl)) {
                    mWebView.loadUrl(subUrl);
                }
            }
        });
    }

    /**
     * 外部浏览器打开指定链接url
     */
    @JavascriptInterface
    public void Browser(String url) {
        if (null == mActivity)
            return;
        // 跳转外部浏览器
        try {
            Uri content_url = Uri.parse(url);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(content_url);
            if (mActivity.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                mActivity.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制内容到剪切板里
     *
     * @param officialaccount
     */
    @JavascriptInterface
    public void copyContent(String officialaccount, String tips) {
        try {
            if (officialaccount == null) {
                return;
            }

            if (mActivity == null) {
                return;
            }

            ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(officialaccount);

            if (TextUtils.isEmpty(tips)) {
                // MyToast.Show(activity, "已拷贝到剪切板");
            } else {
                Toast.makeText(mActivity, tips, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取剪切板内容
     */
    @JavascriptInterface
    public void GetCopyContent() {
        final String content = getCopyBoradContent();
        if (X5JavaScriptInterface.mWebView != null) {
            X5JavaScriptInterface.mWebView.post(new Runnable() {

                @Override
                public void run() {
                    X5JavaScriptInterface.mWebView.loadUrl("javascript:APPReturnClipboard('" + content + "')");

                }
            });
        }
    }

    /**
     * 获取剪切板内容
     */
    public String getCopyBoradContent() {
        try {

            if (mActivity == null) {
                return "";
            }

            ClipboardManager clipboardManager = (ClipboardManager) mActivity
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                    || clipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML)) {
                ClipData cd = clipboardManager.getPrimaryClip();
                Item item = cd.getItemAt(0);
                String content = "";
                try {
                    content = item.getText().toString();
                } catch (Exception e) {
                    content = "";
                }

                // 内容为空 返回null
                if (TextUtils.isEmpty(content)) {
                    return "";
                }
                return content;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * web页面调用打开广告详细页面
     * @param url 打开地址
     */
    @JavascriptInterface
    public void openAdDetail(String url) {
        if (TextUtils.isEmpty(url) || mActivity == null) {
            return;
        }
        Intent intent = new Intent(mActivity, DetailActivity.class);
        intent.putExtra("url", url + "&issdk=1&sdkver=" + WowanIndex.mStringVer);
        mActivity.startActivity(intent);
    }

    /**
     * web页面调用打开广告详细页面
     * @param url 打开地址
     * @param cid 渠道号
     */
    @JavascriptInterface
    public void openAdDetailWithCid(String url,String cid) {
        if (TextUtils.isEmpty(url) || mActivity == null||TextUtils.isEmpty(cid)) {
            return;
        }
        Intent intent = new Intent(mActivity, DetailActivity.class);
        intent.putExtra("url", url + "&issdk=1&sdkver=" + WowanIndex.mStringVer);
        intent.putExtra("cid", cid);
        mActivity.startActivity(intent);
    }

}
