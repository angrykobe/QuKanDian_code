package com.zhangku.qukandian.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class InstallUtils {


    //任务定时器
    private Timer mTimer;
    //定时任务
    private TimerTask mTask;
    //文件总大小
    private int fileLength = 1;
    //下载的文件大小
    private int fileCurrentLength;

    private Context context;
    private String httpUrl;
    private String savePath;
    private String saveName;
    private DownloadCallBack downloadCallBack;
    private static File saveFile;

    private boolean isComplete = false;

    private Handler mHandler = new Handler();
    public interface DownloadCallBack {
        void onStart();

        void onComplete(String path, int fileLength);

        void onLoading(long total, long current);

        void onFail(Exception e);
    }

    public interface InstallCallBack {

        void onSuccess();

        void onFail(Exception e);
    }

    public InstallUtils(Context context, String httpUrl, String saveName, DownloadCallBack downloadCallBack) {
        this.context = context;
        this.httpUrl = httpUrl;
        this.saveName = saveName;
        this.downloadCallBack = downloadCallBack;
        this.savePath = getCachePath(this.context);
    }


    public void downloadAPK() {
        if (TextUtils.isEmpty(httpUrl)) {
            return;
        }

        saveFile = new File(savePath);
        if (!saveFile.exists()) {
            boolean isMK = saveFile.mkdirs();
            if (!isMK) {
                //创建失败
                return;
            }
        }

        saveFile = new File(savePath + File.separator + saveName + ".apk");

        if (downloadCallBack != null) {
            //下载开始
            downloadCallBack.onStart();
        }

        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10 * 1000);
            connection.setReadTimeout(10 * 1000);
            connection.connect();
            inputStream = connection.getInputStream();
            outputStream = new FileOutputStream(saveFile);
            fileLength = connection.getContentLength();

            //计时器
            initTimer();
            byte[] buffer = new byte[1024];
            int current = 0;
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
                current += len;
                if (fileLength > 0) {
                    fileCurrentLength = current;
                }
            }
            isComplete = true;

            //下载完成
            if (downloadCallBack != null) {
                downloadCallBack.onComplete(saveFile.getPath(), fileLength);
                downloadCallBack = null;
            }
        } catch (final Exception e) {
            e.printStackTrace();
            if (downloadCallBack != null) {
                downloadCallBack.onFail(e);
                downloadCallBack = null;
            }
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
                if (connection != null)
                    connection.disconnect();
                //销毁Timer
                destroyTimer();
            } catch (IOException e) {
            }

        }

    }

    private void initTimer() {
        mTimer = new Timer();
        mTask = new TimerTask() {//在run方法中执行定时的任务
            @Override
            public void run() {
                if (downloadCallBack != null) {
                    if (!isComplete) {
                        downloadCallBack.onLoading(fileLength, fileCurrentLength);
                    }
                }
            }
        };
        //任务定时器一定要启动
        mTimer.schedule(mTask, 0, 200);
    }


    private void destroyTimer() {
        if (mTimer != null && mTask != null) {
            mTask.cancel();
            mTimer.cancel();
            mTask = null;
            mTimer = null;
        }
    }

    /**
     * 安装APK工具类
     *
     * @param context     上下文
     * @param filePath    文件路径
     */
    public static void installAPK(Context context, String filePath) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            File apkFile = new File(filePath);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".addam.apkdn.fileprovider", apkFile);
//                Uri contentUri = FileProvider.getUriForFile(context, "com.zhangku.qukandian.fileprovider", apkFile);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        } catch (Exception e) {
        }
        UserSharedPreferences.getInstance().putString(Constants.PATH,"");
    }


    /**
     * 获取app缓存路径    SDCard/Android/data/你的应用的包名/cache
     *
     * @param context
     * @return
     */
    public String getCachePath(Context context) {
        String cachePath;
        try {//Android 4.4会奔溃？？不知道为啥
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    || !Environment.isExternalStorageRemovable()) {
                //外部存储可用
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                //外部存储不可用
                cachePath = context.getCacheDir().getPath();
            }
        }catch (Exception e){
            cachePath = Environment.getExternalStorageDirectory().getPath();///storage/sdcard0
        }
        return cachePath;
    }

}
