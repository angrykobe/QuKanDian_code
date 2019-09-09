package com.zhangku.qukandian.receiver;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.zhangku.qukandian.download.dowloadkit.DownloadManagerReceiver;
import com.zhangku.qukandian.download.dowloadkit.query.AddFilter;
import com.zhangku.qukandian.download.dowloadkit.query.DownloadFileInfo;
import com.zhangku.qukandian.download.dowloadkit.query.DownloadQuery;
import com.zhangku.qukandian.observer.AdDownObserver;

import java.io.File;


/**
 * Created by hocgin on 2017/9/25.
 */

public class DownloadReceiver extends DownloadManagerReceiver {
    @Override
    public void notificationClicked(long[] downloadIds) {

    }

    @Override
    public void downloadComplete(long id) {
        AdDownObserver.OnAdDownListener listenerObserver = AdDownObserver.getInstance().getListenerObserver(id);
        if(listenerObserver!=null) {
            listenerObserver.onAdDownCompleteListener();
        }

        AddFilter filter = DownloadQuery.New((DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE))
                .filter(id);
        DownloadFileInfo query = filter.query();
        if(query ==null ) return;
        String uri = query.getLocalUri();
        if(uri == null) return;
        Uri uri1 = Uri.parse(uri);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri contentUri;
        // 判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String filePaht = getRealPathFromURI(getContext(), uri1);
            contentUri = FileProvider.getUriForFile(getContext(),
                    getContext().getPackageName() + ".addam.apkdn.fileprovider"
//                    "com.zhangku.qukandian.fileprovider"
//                    mContext.getPackageName() + ".addam.apkdn.fileprovider"
                    , new File(filePaht));
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contentUri = Uri.parse(uri);
        }
        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        getContext().startActivity(intent);

        if(listenerObserver!=null) {
            listenerObserver.onAdDownInstallStartListener();
        }
    }

    private String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI,
                new String[]{MediaStore.Images.ImageColumns.DATA},//
                null, null, null);
        if (cursor == null) result = contentURI.getPath();
        else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }
}
