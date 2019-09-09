package com.zhangku.qukandian.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.download.InstallUtils;
import com.zhangku.qukandian.observer.DownloadObserver;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

/**
 * Created by yuzuoning on 2017/6/7.
 */

public class DownloadService extends IntentService {
    public DownloadService() {
        super("downService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String name = intent.getExtras().getString("name");
        String url = intent.getExtras().getString("url");
        new InstallUtils(DownloadService.this, url, name, new InstallUtils.DownloadCallBack() {
            @Override
            public void onStart() {
                Toast.makeText(DownloadService.this, "开始下载", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(String path, int fileLength) {
                DownloadObserver.getInstance().notifyProgress(fileLength, fileLength);
                UserSharedPreferences.getInstance().putString(Constants.PATH,path);
                //LogUtils.LogE("path==="+path);
                InstallUtils.installAPK(DownloadService.this, path);
            }

            @Override
            public void onLoading(long total, long current) {
                //LogUtils.LogE("current="+current+"::total="+total);
                DownloadObserver.getInstance().notifyProgress((int) current, (int) total);
            }

            @Override
            public void onFail(Exception e) {
                Toast.makeText(DownloadService.this, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }).downloadAPK();
    }

}
