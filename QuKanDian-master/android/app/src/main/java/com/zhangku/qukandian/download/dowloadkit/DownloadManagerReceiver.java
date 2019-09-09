package com.zhangku.qukandian.download.dowloadkit;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by hocgin on 2017/9/25.
 */

public abstract class DownloadManagerReceiver extends BroadcastReceiver {
	private Context context;
	private Intent intent;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		this.intent = intent;
		String action = intent.getAction();
		if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(action)) {
			// 点击下载进度通知时, 对应的下载ID以数组的方式传递
			long[] downloadIds = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
			notificationClicked(downloadIds);
		} else if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
            /*
             * 获取下载完成对应的下载ID, 这里下载完成指的不是下载成功, 下载失败也算是下载完成,
             * 所以接收到下载完成广播后, 还需要根据 id 手动查询对应下载请求的成功与失败.
             */
			long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L);
			// 根据获取到的ID，使用上面第3步的方法查询是否下载成功
			downloadComplete(id);
		}
//		//接收卸载广播
//		if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
//			String packageName = intent.getDataString();
//			System.out.println("卸载了:"  + packageName + "包名的程序");
//		}
	}
	
	public Context getContext() {
		return context;
	}
	
	public Intent getIntent() {
		return intent;
	}
	
	public abstract void notificationClicked(long[] downloadIds);
	
	public abstract void downloadComplete(long id);

}
