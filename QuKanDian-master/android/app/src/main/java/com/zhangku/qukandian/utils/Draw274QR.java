package com.zhangku.qukandian.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.umeng.socialize.media.UMImage;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.manager.UserManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yuzuoning on 2018/1/12.
 */

public class Draw274QR {
    public static File drawQR(Context context, Bitmap bgRes, String fileName, String content) {
        Bitmap bitmap = bgRes.copy(Bitmap.Config.ARGB_8888, true);

        String path = null;
        if (CommonHelper.checkSDCard(context)) {
            path = CommonHelper.savePath(context);
        } else {
            path = context.getFilesDir().getAbsolutePath();
            path += "zhangku/";
        }
        File file = new File(path, fileName);
        int bgWtdth = bitmap.getWidth();
        int bgHeight = bitmap.getHeight();
        Bitmap bitmap1 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        bitmap.recycle();

        Canvas canvas = new Canvas(bitmap1);

        int length = (int) (bitmap.getWidth() / 2.9);
        TextPaint paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(context, R.color.red_f7b));
        paint.setTextSize(bgWtdth / 18);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        Bitmap qr = DrawQR.createQRImage(context, content, length, length, new UMImage(context, R.mipmap.app_icon).asBitmap());
        canvas.drawBitmap(qr, bgWtdth / 2 - length / 2, (float) (bgHeight / 1.54), null);

        SpannableStringBuilder spannableString = new SpannableStringBuilder("" + UserManager.getInst().getUserBeam().getId());
//        spannableString.setSpan(new ForegroundColorSpan(CommonHelper.parseColor("#000000")), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        StaticLayout layout = new StaticLayout(spannableString, paint, Config.SCREEN_WIDTH, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);
        canvas.translate(bgWtdth / 2 - layout.getWidth() / 2, (float) (bgHeight / 1.75));
        layout.draw(canvas);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, out);
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
