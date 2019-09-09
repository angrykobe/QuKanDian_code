package com.zhangku.qukandian.utils;

import android.app.DownloadManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.webkit.URLUtil;

import com.zhangku.qukandian.application.QuKanDianApplication;

/**
 * 创建者          xuzhida
 * 创建日期        2018/8/27
 * 你不注释一下？
 */
public class DownHelper {


    private static final String TAG = "DownHelper";
    public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");

    private Context mApplicationContext;
    private DownloadManager mDownloadManager;
    private DownProcess processListener;

//    private static class Holder {
//        private final static DownHelper sInstance = new DownHelper();
//    }

    private DownHelper() {
        mApplicationContext = QuKanDianApplication.getAppContext();
        mDownloadManager = (DownloadManager) mApplicationContext.getSystemService(Context.DOWNLOAD_SERVICE);
        registerReceiver();
    }

    public static DownHelper getInstance() {
        return new DownHelper();
    }

    public void onDestroy() {
        unRegisterReceiver();
    }

    public long startDownloading(String url, String fileName) {
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setTitle(fileName);
//        request.setMimeType(mimeType);
        //这是安卓.apk文件的类型。有些机型必须设置此方法，才能在下载完成后，点击通知栏的Notification时，才能正确的打开安装界面。不然会弹出一个Toast（can not open file）
        request.setMimeType("application/vnd.android.package-archive");
        // 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库
        request.allowScanningByMediaScanner();
        // 设置通知的显示类型，下载进行时和完成后显示通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // 设置通知栏的标题，如果不设置，默认使用文件名
//        request.setTitles("This is title");
        // 设置通知栏的描述
//        request.setDescription("This is description");
        // 允许在计费流量下下载
        request.setAllowedOverMetered(true);
        // 允许该记录在下载管理界面可见
        request.setVisibleInDownloadsUi(true);
        // 允许漫游时下载
        request.setAllowedOverRoaming(true);
        // 允许下载的网路类型
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        // 设置下载文件保存的路径和文件名
//        String fileName = URLUtil.guessFileName(url, contentDisposition, mimeType);
//        request.setDestinationInExternalFilesDir(mApplicationContext, Environment.DIRECTORY_DOWNLOADS, fileName);
//        /**
//         * 方法1:
//         * 目录: Android -> data -> com.app -> files -> Download -> dxtj.apk
//         * 这个文件是你的应用所专用的,软件卸载后，下载的文件将随着卸载全部被删除
//         */
//        request.setDestinationInExternalFilesDir( mApplicationContext , Environment.DIRECTORY_DOWNLOADS ,  "dxtj.apk" );
//        /**
//         * 方法2:
//         * 下载的文件存放地址  SD卡 download文件夹，dxtj.apk
//         * 软件卸载后，下载的文件会保留
//         */
//        //在SD卡上创建一个文件夹
//        request.setDestinationInExternalPublicDir(  "/epmyg/"  , "dxtj.apk" ) ;
//        /**
//         * 方法3:
//         * 如果下载的文件希望被其他的应用共享
//         * 特别是那些你下载下来希望被Media Scanner扫描到的文件（比如音乐文件）
//         */
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        /**
         * 方法4
         * 文件将存放在外部存储的确实download文件内，如果无此文件夹，创建之，如果有，下面将返回false。
         * 系统有个下载文件夹，比如小米手机系统下载文件夹  SD卡--> Download文件夹
         */
        //创建目录
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir() ;
        //设置文件存放路径
        request.setDestinationInExternalPublicDir(  Environment.DIRECTORY_DOWNLOADS  , fileName ) ;


        long downloadId = mDownloadManager.enqueue(request);
        return downloadId;
    }
    //下载进度回调
    public void setDownListen(DownProcess processListener){
        this.processListener = processListener;
    }

    private int queryDownloadStatusById(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = null;
        int downloadStatus = -1;
        try {
            c = mDownloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                /**
                 * downloadStatus的状态值有一下几种
                 *
                 * Value of {@link #COLUMN_STATUS} when the download is waiting to start.
                 public final static int STATUS_PENDING = 1 << 0;
                 * Value of {@link #COLUMN_STATUS} when the download is currently running.
                 public final static int STATUS_RUNNING = 1 << 1;
                 * Value of {@link #COLUMN_STATUS} when the download is waiting to retry or resume.
                 public final static int STATUS_PAUSED = 1 << 2;
                 * Value of {@link #COLUMN_STATUS} when the download has successfully completed.
                 public final static int STATUS_SUCCESSFUL = 1 << 3;
                 * Value of {@link #COLUMN_STATUS} when the download has failed (and will not be retried).
                 public final static int STATUS_FAILED = 1 << 4;
                 */
                downloadStatus = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));

                long totalBytes = c.getLong(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                long currentBytes = c.getLong(c.getColumnIndex(
                        DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                final int progress =(totalBytes == -1 && currentBytes > 0) ? 100 : (int) (currentBytes * 100 / totalBytes);
                Log.e(TAG, "download "+progress+"%, downloadStatus = "+downloadStatus);
                if(processListener !=null)
                    processListener.downProcess(progress, downloadStatus,downloadId);
            }


//            int reasonIdx = c.getColumnIndex(DownloadManager.COLUMN_REASON);
//            // Iterate over the result Cursor.
//            while (c.moveToNext()) {
////                // Extract the data we require from the Cursor.
////                String title = c.getString(titleIdx);
////                int fileSize = c.getInt(fileSizeIdx);
////                int bytesDL = c.getInt(bytesDLIdx);
//
//                // Translate the pause reason to friendly text.
//                int reason = c.getInt(reasonIdx);
//                String reasonString = "Unknown";
//                switch (reason) {
//                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI :
//                        reasonString = "Waiting for WiFi"; break;
//                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK :
//                        reasonString = "Waiting for connectivity"; break;
//                    case DownloadManager.PAUSED_WAITING_TO_RETRY :
//                        reasonString = "Waiting to retry"; break;
//                    default : break;
//                }
//            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return downloadStatus;
    }

    public void pauseOrContinueDownloading(long downloadId) {
        int downloadStatus = queryDownloadStatusById(downloadId);
        if (downloadStatus == DownloadManager.STATUS_RUNNING
                || downloadStatus == DownloadManager.STATUS_PAUSED) {
            try {
                Uri uri = ContentUris.withAppendedId(CONTENT_URI, downloadId);
                ContentValues values = new ContentValues();
                boolean isPause = downloadStatus == DownloadManager.STATUS_RUNNING;
                /**
                 * 这里要改变的status的状态值有一下这些,是在Downloads.Impl.下面的
                 * 我们这里用到的是 STATUS_PAUSED_BY_APP = 193(使暂停),STATUS_RUNNING = 192(使继续下载)
                 *
                 * This download hasn't stated yet
                 public static final int STATUS_PENDING = 190;
                 * This download has started
                 public static final int STATUS_RUNNING = 192;
                 * This download has been paused by the owning app.
                 public static final int STATUS_PAUSED_BY_APP = 193;
                 * This download encountered some network error and is waiting before retrying the request.
                 public static final int STATUS_WAITING_TO_RETRY = 194;
                 * This download is waiting for network connectivity to proceed.
                 public static final int STATUS_WAITING_FOR_NETWORK = 195;
                 * This download exceeded a size limit for mobile networks and is waiting for a Wi-Fi
                 * connection to proceed.
                 public static final int STATUS_QUEUED_FOR_WIFI = 196;
                 * This download couldn't be completed due to insufficient storage
                 * space.  Typically, this is because the SD card is full.
                 public static final int STATUS_INSUFFICIENT_SPACE_ERROR = 198;
                 * This download couldn't be completed because no external storage
                 * device was found.  Typically, this is because the SD card is not
                 * mounted.
                 public static final int STATUS_DEVICE_NOT_FOUND_ERROR = 199;
                 * This download has successfully completed.
                 * Warning: there might be other status values that indicate success
                 * in the future.
                 * Use isSucccess() to capture the entire category.
                 public static final int STATUS_SUCCESS = 200;
                 * This request couldn't be parsed. This is also used when processing
                 * requests with unknown/unsupported URI schemes.
                 public static final int STATUS_BAD_REQUEST = 400;
                 * This status means that the download is authenticate. It need user
                 * name and password A dialog will show to user to input user name and
                 * password.
                 * @internal
                public static final int STATUS_NEED_HTTP_AUTH = 401;
                 * This download can't be performed because the content type cannot be
                 * handled.
                public static final int STATUS_NOT_ACCEPTABLE = 406;
                 * This download cannot be performed because the length cannot be
                 * determined accurately. This is the code for the HTTP error "Length
                 * Required", which is typically used when making requests that require
                 * a content length but don't have one, and it is also used in the
                 * client when a response is received whose length cannot be determined
                 * accurately (therefore making it impossible to know when a download
                 * completes).
                public static final int STATUS_LENGTH_REQUIRED = 411;
                 * This download was interrupted and cannot be resumed.
                 * This is the code for the HTTP error "Precondition Failed", and it is
                 * also used in situations where the client doesn't have an ETag at all.
                public static final int STATUS_PRECONDITION_FAILED = 412;
                 * The lowest-valued error status that is not an actual HTTP status code.
                public static final int MIN_ARTIFICIAL_ERROR_STATUS = 488;
                 * The requested destination file already exists.
                public static final int STATUS_FILE_ALREADY_EXISTS_ERROR = 488;
                 * Some possibly transient error occurred, but we can't resume the download.
                public static final int STATUS_CANNOT_RESUME = 489;
                 * This download was canceled
                public static final int STATUS_CANCELED = 490;
                 * This download has completed with an error.
                 * Warning: there will be other status values that indicate errors in
                 * the future. Use isStatusError() to capture the entire category.
                public static final int STATUS_UNKNOWN_ERROR = 491;
                 * This download couldn't be completed because of a storage issue.
                 * Typically, that's because the filesystem is missing or full.
                 * Use the more specific {@link #STATUS_INSUFFICIENT_SPACE_ERROR}
                 * and {@link #STATUS_DEVICE_NOT_FOUND_ERROR} when appropriate.
                public static final int STATUS_FILE_ERROR = 492;
                 * This download couldn't be completed because of an HTTP
                 * redirect response that the download manager couldn't
                 * handle.
                public static final int STATUS_UNHANDLED_REDIRECT = 493;
                 * This download couldn't be completed because of an
                 * unspecified unhandled HTTP code.
                public static final int STATUS_UNHANDLED_HTTP_CODE = 494;
                 * This download couldn't be completed because of an
                 * error receiving or processing data at the HTTP level.
                public static final int STATUS_HTTP_DATA_ERROR = 495;
                 * This download couldn't be completed because of an
                 * HttpException while setting up the request.
                public static final int STATUS_HTTP_EXCEPTION = 496;
                 * This download couldn't be completed because there were
                 * too many redirects.
                public static final int STATUS_TOO_MANY_REDIRECTS = 497;
                 * This download has failed because requesting application has been
                 *
                 * @hide
                 * @deprecated since behavior now uses
                 *             {@link #STATUS_WAITING_FOR_NETWORK}
                 @Deprecated
                 public static final int STATUS_BLOCKED = 498;
                 */
                int status = isPause ? 193: 192;//(这里就参照上面的注释看吧)
                values.put(DownloadManager.COLUMN_STATUS, status);
                mApplicationContext.getContentResolver().update(uri, values, null, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancelDownload(long downloadId) {
        mDownloadManager.remove(downloadId);
    }

    private void registerReceiver() {
        mApplicationContext.getContentResolver().registerContentObserver(CONTENT_URI, true, mDownloadObserver);
    }

    private void unRegisterReceiver() {
        mApplicationContext.getContentResolver().unregisterContentObserver(mDownloadObserver);
    }

    private ContentObserver mDownloadObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            Log.w(TAG, "mDownloadObserver onChange uri = " + uri);
            long downloadId = -1;
            try {
                downloadId = ContentUris.parseId(uri);
            }catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "e:" + e.toString());
            }
            if(-1 == downloadId)    return;
            queryDownloadStatusById(downloadId);
        }
    };

    public void remove(long id){
        mDownloadManager.remove(id);
    }

    public interface DownProcess{
        /**
         * @param process  下载进度
         * @param state     下载状态 2下载中  8 下载完成
         * @param downId     id
         */
        void downProcess(int process,int state,long downId);
    }
}
