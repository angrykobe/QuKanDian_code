package com.zhangku.qukandian.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import android.util.TypedValue;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zhangku.qukandian.activitys.FaceToFaceInviteActivity;
import com.zhangku.qukandian.activitys.additional.ChbyxjFaceToFaceInviteActivity;
import com.zhangku.qukandian.config.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuzuoning on 2017/4/20.
 */

public class QRCodeUtil {
    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Context context, Bitmap src, Bitmap logo, int bgRes) {
        Bitmap mBgBitmap = BitmapFactory.decodeResource(context.getResources(), bgRes);

        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return mergeBitmap(context, mBgBitmap, bitmap);
    }

    /**
     * 生成二维码Bitmap
     *
     * @param content   内容
     * @param widthPix  图片宽度
     * @param heightPix 图片高度
     * @param logoBm    二维码中心的Logo图标（可以为null）
     * @return 生成二维码及保存文件是否成功
     */
    public static File createQRImage(Context context, String content, int widthPix, int heightPix, Bitmap logoBm, int bgRes,String fileName) {
        try {
            if (content == null || "".equals(content)) {
                return null;
            }
            Log.e("shared","content:"+content);
            //配置参数
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //容错级别
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            //设置空白边距的宽度
            hints.put(EncodeHintType.MARGIN, 0); //default is 4
            // 图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, widthPix, heightPix, hints);
            int[] pixels = new int[widthPix * heightPix];
            // 下面这里按照二维码的算法，逐个生成二维码的图片，
            // 两个for循环是图片横列扫描的结果
            for (int y = 0; y < heightPix; y++) {
                for (int x = 0; x < widthPix; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * widthPix + x] = 0xff000000;
                    } else {
                        pixels[y * widthPix + x] = 0xffffffff;
                    }
                }
            }

            // 生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix);

            if (logoBm != null) {
                bitmap = addLogo(context, bitmap, logoBm, bgRes);
            }

            String path = null;
            if (CommonHelper.checkSDCard(context)) {
                path = CommonHelper.savePath(context);
            } else {
                path = context.getFilesDir().getAbsolutePath();
                path += "zhangku/";
            }
            File file = new File(path, fileName);

            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
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
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Bitmap mergeBitmap(Context context, Bitmap bitmapBg, Bitmap bitmapfrom) {
        Bitmap tempBitmap = bitmapBg.copy(Bitmap.Config.ARGB_8888, true);
        int bgWtdth = bitmapBg.getWidth();
        int bgHeight = bitmapBg.getHeight();
        int srcWidth = bitmapfrom.getWidth();
        int srcHeight = bitmapfrom.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(bgWtdth, bgHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(tempBitmap, 0, 0, null);
            if(context instanceof FaceToFaceInviteActivity || context instanceof ChbyxjFaceToFaceInviteActivity){
                canvas.drawBitmap(bitmapfrom, (bgWtdth - srcWidth) / 2,
                        bgHeight - srcHeight - DisplayUtils.dip2px(context, 82), null);
            }else {
                canvas.drawBitmap(bitmapfrom, (bgWtdth - srcWidth) / 2,
                        bgHeight - srcHeight - DisplayUtils.dip2px(context, 49), null);
            }

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        bitmapBg.recycle();
        return bitmap;
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
        if (Config.SCREEN_WIDTH > 1080) {
            matrix.postScale(2f, 2f);
        } else if (Config.SCREEN_WIDTH == 1080) {
            matrix.postScale(1.5f, 1.5f);
        } else {
            matrix.postScale(1f, 1f);
        }
        Bitmap newBM = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return newBM;
    }

}
