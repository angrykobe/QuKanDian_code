package com.zhangku.qukandian.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static android.R.attr.path;
import static com.bumptech.glide.Glide.with;

/**
 * Created by yuzuoning on 2017/7/3.
 */

public class SaveFile {


    private Context context;

    public SaveFile() {
    }

    public SaveFile(Context context) {
        super();
        this.context = context;
    }

    //Glide保存图片
    public void savePicture(final String fileName, String url) {
        if (fileName.contains(".gif")) {
            Glide.with(context).asGif().load(url).into(new SimpleTarget<GifDrawable>() {
                @Override
                public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                    try {
                        //Glide 切换到4.8.0版本 动图转化保存  还未处理
                        savaFileToSD(fileName,Bitmap2Bytes(resource.getFirstFrame()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    try {
                        savaFileToSD(fileName, Bitmap2Bytes(resource));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    }
    private byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    class Down extends AsyncTask<Object, Object, File> {
        private Context mContext;

        public Down(Context context) {
            mContext = context;
        }

        @Override
        protected File doInBackground(Object... params) {
            File file = null;
            try {
                file = with(mContext).load(params[0]).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return file;
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            // 最后通知图库更新
            try {
                MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                        file.getAbsolutePath(), null, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
            Toast.makeText(mContext, "图片已为您保存在相册", Toast.LENGTH_SHORT).show();
        }
    }

    //往SD卡写入文件的方法
    public void savaFileToSD(String filename, byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/pictures";
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            filename = filePath + "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);//将bytes写入到输出流中
            output.close();//关闭输出流
            File file = new File(filename);
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).setData(Uri.fromFile(file)));
            Toast.makeText(context, "图片已为您保存在相册", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
    }

    public static void saveImageToGallery(Context context, Bitmap bmp, boolean isGif) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "";
        if (isGif) {
            fileName = System.currentTimeMillis() + ".gif";
        } else {
            fileName = System.currentTimeMillis() + ".jpg";
        }

        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        Toast.makeText(context, "图片已为您保存在相册", Toast.LENGTH_SHORT).show();
    }
}
