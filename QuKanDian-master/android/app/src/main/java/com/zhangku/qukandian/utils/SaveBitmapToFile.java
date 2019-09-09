package com.zhangku.qukandian.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yuzuoning on 2017/6/15.
 */

public class SaveBitmapToFile {
    public static File save(Context context, Bitmap bitmap) {
        String path = CommonHelper.getSavePath(context);

        File file = new File(path, "poster" + ".jpg");
        if (file.exists()) {
            file.delete();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
                if (null != bitmap) {
                    if (bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File save(Context context, int resId, String name) {
        String path = CommonHelper.getSavePath(context);
        File file = new File(path, name + ".jpg");
        if (file.exists()) {
            file.delete();
        }

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
                if (null != bitmap) {
                    if (bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    public static File save(Context context, Bitmap resId, String name) {
        String path = CommonHelper.getSavePath(context);

        File file = new File(path, name + ".jpg");
        if (file.exists()) {
            file.delete();
        }

        Bitmap bitmap = resId.copy(Bitmap.Config.ARGB_8888,true);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                    out.close();
                }
                if (null != bitmap) {
                    if (bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
