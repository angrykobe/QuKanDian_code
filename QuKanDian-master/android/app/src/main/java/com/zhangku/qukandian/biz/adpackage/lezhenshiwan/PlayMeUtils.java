package com.zhangku.qukandian.biz.adpackage.lezhenshiwan;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import java.io.File;
import java.security.MessageDigest;

public class PlayMeUtils {

    /**
     * 原生调用打开广告详细页面
     *
     * @param url     打开地址
     * @param context
     * @param cid  渠道号
     */
    public static void openAdDetail(Context context,String cid, String url) {
        if (TextUtils.isEmpty(url) || context == null) {
            return;
        }
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("url", url + "&issdk=1&sdkver=" + WowanIndex.mStringVer);
        intent.putExtra("cid", cid);
        context.startActivity(intent);
    }

    /**
     * 加密
     *
     * @param plaintext 明文
     * @return ciphertext 密文
     */
    public final static String encrypt(String plaintext) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = plaintext.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    public static void install(Activity activity, File file) {

        try {

            boolean haveInstallPermission;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // 先判断是否有安装未知来源应用的权限
                haveInstallPermission = activity.getPackageManager().canRequestPackageInstalls();
                if (!haveInstallPermission) {
                    // 弹框提示用户手动打开
                    Uri packageURI = Uri.parse("package:" + activity.getPackageName());
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                    activity.startActivityForResult(intent, 101);
                    return;
                }
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 24) { // 判读版本是否在7.0以上
                // 参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致 参数3
                Uri apkUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", file);

                // 添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    /**
     * 通过query查询下载状态，包括已下载数据大小，总大小，下载状态
     *
     * @param downloadId
     * @return
     */
    public static int[] getBytesAndStatus(long downloadId,DownloadManager downloadManager) {

        int[] bytesAndStatus = new int[]{
                -1, -1, 0
        };

        if(downloadManager == null){
            return bytesAndStatus;
        }

        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = downloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载文件大小
                bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //下载文件的总大小
                bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                //下载状态
                bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bytesAndStatus;
    }

}
