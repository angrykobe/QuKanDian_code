package com.zhangku.qukandian.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.TypedValue;

import com.umeng.socialize.media.UMImage;
import com.zhangku.qukandian.R;
import com.zhangku.qukandian.manager.UserManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yuzuoning on 2018/2/10.
 */

public class Draw281QR {
    public static File drawQR(final Context context, Bitmap bgRes, final Bitmap avatar, final String fileName, final String content) {

        final Bitmap bitmap = bgRes.copy(Bitmap.Config.ARGB_8888, true);
        final int bgWtdth = bitmap.getWidth();
        final int bgHeight = bitmap.getHeight();

        String path = CommonHelper.getSavePath(context);

        File file = new File(path, fileName);

        Bitmap bitmap1 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        bitmap.recycle();

        Canvas canvas = new Canvas(bitmap1);

        int length = (int) (bitmap.getWidth() / 2.9);
        TextPaint paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(context, R.color.white));
        paint.setTextSize(bgWtdth / 10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        TextPaint paint1 = new TextPaint();
        paint1.setAntiAlias(true);
        paint1.setColor(ContextCompat.getColor(context, R.color.white));
        paint1.setTextSize(bgWtdth / 21);
        paint1.setStyle(Paint.Style.FILL_AND_STROKE);
        paint1.setTypeface(Typeface.DEFAULT_BOLD);


        canvas.drawBitmap(getCircleAvatar(context, avatar), 150, 465, null);

        String name = UserManager.getInst().getUserBeam().getNickName();
        SpannableStringBuilder spannableString1 = new SpannableStringBuilder((name.length() > 6)?(name.substring(0,6)+"..."):name);
        StaticLayout layout1 = new StaticLayout(spannableString1, paint1, bgWtdth, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);
        canvas.translate(0 + 95, 478);
        layout1.draw(canvas);

        Bitmap qr = DrawQR.createQRImage(context, content, length, length, new UMImage(context, R.mipmap.app_icon).asBitmap());
        canvas.drawBitmap(qr, 0 + 144, 382, null);

        SpannableStringBuilder spannableString = new SpannableStringBuilder("" + UserManager.getInst().getUserBeam().getId());
        StaticLayout layout = new StaticLayout(spannableString, paint, bgWtdth, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);
        canvas.translate(0 - layout.getWidth()/8, 248);
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

    private static Bitmap getCircleAvatar(Context context, Bitmap avatar) {
        Bitmap bitmap = Bitmap.createBitmap(avatar.getWidth(), avatar.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //混合模式中的圆 DST
        paint.setColor(Color.BLACK);
        //半径取宽高中小的并/2
        canvas.drawCircle(avatar.getWidth() / 2, avatar.getHeight() / 2,
                avatar.getWidth() / 2 < avatar.getHeight() / 2 ? avatar.getWidth() / 2 : avatar.getHeight() / 2,
                paint);
        //添加混合模式给paint，矩阵，切圆，方形和圆形，选择圆形重叠的部分
        //PorterDuffXfermode：该方法及是
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //再把原始头像，画到bitmap上
        canvas.drawBitmap(avatar, 0, 0, paint);
        /**画个百边*/
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);//设置一个模式
        //进行单位变换

        float strokeWidth =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,
                        context.getResources().getDisplayMetrics());
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(avatar.getWidth() / 2, avatar.getHeight() / 2,
                avatar.getWidth() / 2 < avatar.getHeight() / 2 ? avatar.getWidth() / 2 : avatar.getHeight() / 2
                        - strokeWidth / 2, paint);
        Matrix matrix = new Matrix();
//        if (Config.SCREEN_WIDTH > 1080) {
//            matrix.postScale(2f, 2f);
//        } else if (Config.SCREEN_WIDTH == 1080) {
//            matrix.postScale(1.5f, 1.5f);
//        } else {
//            matrix.postScale(1f, 1f);
//        }
        Bitmap newBM = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return newBM;
    }
}
