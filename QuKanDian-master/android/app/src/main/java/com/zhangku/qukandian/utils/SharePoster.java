package com.zhangku.qukandian.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.umeng.socialize.media.UMImage;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.config.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yuzuoning on 2017/6/15.
 */

public class SharePoster {
    public static File drawPoster(Context context, String money, String content, Bitmap bg, OnDrawFinishedListener onDrawFinishedListener) {
        if (bg == null) return null;
        TextPaint paint = new TextPaint();
        paint.setTextSize(DisplayUtils.dip2px(context, 18));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(context, R.color.black_33));
        TextPaint paint1 = new TextPaint();
        paint1.setTextSize(DisplayUtils.dip2px(context, 9));
        paint1.setStyle(Paint.Style.FILL);
        paint1.setAntiAlias(true);
        paint1.setColor(ContextCompat.getColor(context, R.color.black_33));

        int bgWtdth = bg.getWidth();
        int bgHeight = bg.getHeight();

        String path = null;
        if (CommonHelper.checkSDCard(context)) {
            path = CommonHelper.savePath(context);
        } else {
            path = context.getFilesDir().getAbsolutePath();
            path += "zhangku/";
        }

        File file = new File(path, "income.jpg");
        if (file.exists()) {
            file.delete();
        } else {
            file.mkdir();
        }
        Bitmap bitmap = bg.copy(Bitmap.Config.ARGB_8888, true);

        Canvas canvas = new Canvas(bitmap);

        StaticLayout layout = new StaticLayout(money, paint, Config.SCREEN_WIDTH - 18, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);
        canvas.translate(bgWtdth / 2 - layout.getWidth() / 2, bgHeight / 4.7f);
        layout.draw(canvas);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
//        if(Build.MANUFACTURER.equals("HUAWEI")){
            UMImage img = new UMImage(context, R.mipmap.app_icon);
            canvas.drawBitmap(DrawQR.createQRImage(context, content
                    , bgWtdth/3,bgWtdth/3, img.asBitmap())
                    , Config.SCREEN_WIDTH / 2 - bgWtdth / 6, bgHeight / 2.05f, null);

            StaticLayout layout2 = new StaticLayout("扫码加入趣看视界", paint1, Config.SCREEN_WIDTH - 18, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.translate(0, bgHeight / 1.38f);
            layout2.draw(canvas);
//        }else {
//            UMImage img = new UMImage(context, R.mipmap.app_icon_new);
//            canvas.translate(bgWtdth/2,bgHeight / 2.15f);
//            canvas.drawBitmap(DrawQR.createQRImage(context, content
//                    , 240, 240, img.asBitmap())
//                    , 20, 20, null);
//
//            StaticLayout layout2 = new StaticLayout("扫码加入趣看视界", paint1, Config.SCREEN_WIDTH - 18, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);
//            canvas.translate(-layout2.getWidth() / 2+150, 270);
//            layout2.draw(canvas);
//        }

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
                    if (null != onDrawFinishedListener) {
                        onDrawFinishedListener.onDrawFinishedListener(file);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    public interface OnDrawFinishedListener {
        void onDrawFinishedListener(File file);
    }
}

