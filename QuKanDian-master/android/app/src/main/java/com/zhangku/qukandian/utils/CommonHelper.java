package com.zhangku.qukandian.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EncodeUtils;
import com.google.gson.Gson;
import com.zhangku.qukandian.activitys.MainActivity;
import com.zhangku.qukandian.application.QuKanDianApplication;
import com.zhangku.qukandian.bean.AdLocationBeans;
import com.zhangku.qukandian.bean.AppInfo;
import com.zhangku.qukandian.config.AnnoCon;
import com.zhangku.qukandian.config.Constants;
import com.zhangku.qukandian.protocol.UpPackageProtocol;
import com.zhangku.qukandian.utils.SharedPreferencesUtil.UserSharedPreferences;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ikidou.reflect.TypeToken;

import static android.content.Context.TELEPHONY_SERVICE;

public class CommonHelper {

    public static char[] sDigits = "0123456789abcdef".toCharArray();

    /**
     * first app user
     */
    public static final int AID_APP = 10000;
    /**
     * offset for uid ranges for each user
     */
    public static final int AID_USER = 100000;

    public static boolean checkSDCard(Context context) {
        return Storage.externalStorageAvailable();
    }

    public static String savePath(Context context) {
//        if(checkSDCard(context)){
        File file = new File(Environment.getExternalStorageDirectory(), "zhangku");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
//        }else {
//            ToastUtils.showShortToast(context,"存储卡不可用");
//            return null;
//        }
    }

    public static String getSavePath(Context context) {
        String path = "";
        if (CommonHelper.checkSDCard(context)) {
            path = CommonHelper.savePath(context);
        } else {
            File pathFile = new File(context.getFilesDir(), "zhangku");
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            path = pathFile.getAbsolutePath();
        }
        return path;
    }

    public static String getFileJPGPath(Context context, String fileName) {

        String filePath = null;
        if (CommonHelper.checkSDCard(context)) {
            filePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            filePath = context.getFilesDir().getAbsolutePath();
        }
        if (!filePath.endsWith("/")) {
            filePath += "/";
        }
        filePath += "zhangku/";
        File file = new File(filePath);
        if (!file.exists())
            file.mkdirs();
        filePath = filePath + fileName;
        return filePath;
    }

    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    public static String formatPlayTime(long time) {
        return String.format("%02d:%02d:%02d", time / 3600, time / 60 % 60,
                time % 60);
    }

    public static String stringForTime(int timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public static String getMD5(byte[] source) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();
            char[] str = new char[32];
            for (int idx = 0; idx < 16; ++idx) {
                byte tmpByte = tmp[idx];
                str[idx << 1] = sDigits[tmpByte >>> 4 & 0xf];
                str[(idx << 1) + 1] = sDigits[tmpByte & 0xf];
            }
            result = new String(str);
        } catch (Exception e) {
        }
        return result;
    }

    public static String md5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String getMD5(String source) {
        return getMD5(source.getBytes());
    }


    public static String formatSize(long size) {
        if (size >= 0 && size < 1024) {
            return String.format("%dB", size);
        } else if (size >= 1024 && size < 1024 * 1024) {
            return String.format("%dKB", size / 1024);
        } else {
            return String.format("%.1fM", size / 1024 / 1024.f);
        }
    }

    public static String formatHighLevelGameSize(long size) {
        return String.valueOf(size / 1024 / 1024) + "M";
    }


    public static void installApk(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(path)), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        int code;
        try {
            PackageInfo pInfo = pm.getPackageInfo(context.getPackageName(), 0);
            code = pInfo.versionCode;
        } catch (NameNotFoundException e) {
            code = 0;
        }
        return code;
    }

    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        String versionName = "";
        try {
            PackageInfo pInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = "V" + pInfo.versionName;
        } catch (NameNotFoundException e) {
            versionName = "V1.0";
        }
        return versionName;
    }

    public static String getVersionName(Context context, String pkg) {
        String name = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(pkg, 0);
            name = info.versionName;
        } catch (Exception e) {
        }
        return name;
    }

    //获取手机安装的应用信息（排除系统自带）
    public static ArrayList<String> getAllApp(Context context) {
        ArrayList<String> results = new ArrayList<String>();
        try {
            if (context == null) return results;
            List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
            for (PackageInfo i : packages) {
                if ((i.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    AppInfo appInfo = new AppInfo();
//                Drawable drawable = context.getPackageManager().getApplicationIcon(i.applicationInfo);
//                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    Bitmap bitmap = drawableToBitamp(context.getPackageManager().getApplicationIcon(i.applicationInfo), Bitmap.Config.ARGB_4444);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    appInfo.setIcon(baos.toByteArray());
                    appInfo.setName(i.applicationInfo.loadLabel(context.getPackageManager()).toString());
                    appInfo.setPackageName(i.applicationInfo.packageName);
                    appInfo.save();
                    results.add(i.applicationInfo.packageName);
                    // 销毁图片，防止内存泄露
                    bitmap.recycle();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
//    public static String getAllApp(Context context) {
//        String result = "";
//        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
//        for (PackageInfo i : packages) {
//            if ((i.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
//                AppInfo appInfo = new AppInfo();
//                Drawable drawable = context.getPackageManager().getApplicationIcon(i.applicationInfo);
//                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//                appInfo.setIcon(baos.toByteArray());
//                appInfo.setName(i.applicationInfo.loadLabel(context.getPackageManager()).toString());
//                appInfo.setPackageName(i.applicationInfo.packageName);
//                appInfo.save();
//                result += i.applicationInfo.loadLabel(context.getPackageManager()).toString() + ",";
//            }
//        }
//        return result.substring(0, result.length() - 1);
//    }

    public static void uploadNiceApp(Context context, ArrayList<String> packnames) {
        if (packnames == null || packnames.size() <= 0) return;
        String latlng = "";
        String upPacknames = "";
        String imei = MachineInfoUtil.getInstance().getIMEI(context);

        ArrayList<String> okPacknames = new ArrayList<String>();
        ArrayList<String> nicePacknames = new ArrayList<String>(Arrays.asList("com.jifen.qukan", "com.cashtoutiao", "com.coohua.xinwenzhuan",
                "com.ss.android.article.lite", "com.ss.android.article.news",
                "com.songheng.eastnews", "cn.youth.news", "com.tencent.reading", "com.tencent.news", "com.ifext.news"));
        for (String packname : packnames) {
            if (nicePacknames.contains(packname)) {
                okPacknames.add(packname);
            }
        }
        if (okPacknames.size() > 0) {
            upPacknames = StringUtils.join(",", okPacknames);
        }

        String provider = "";
        //获取定位服务
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //获取当前可用的位置控制器
        List<String> list = locationManager.getProviders(true);

        if (list.contains(LocationManager.GPS_PROVIDER)) {
            //是否为GPS位置控制器
            provider = LocationManager.GPS_PROVIDER;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为网络位置控制器
            provider = LocationManager.NETWORK_PROVIDER;
        }

        //版本判断
        boolean isFineLocation = true;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                isFineLocation = false;
            }
        }
        if (isFineLocation) {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                //获取当前位置，这里只用到了经纬度
                latlng = location.getLatitude() + "," + location.getLongitude();
            }
        }

        String content = "{\"IMEI\":\"" + imei + "\",\"Packages\":\"" + upPacknames + "\",\"Location\":\"" + latlng + "\",\"UserId\":\"" + com.zhangku.qukandian.manager.UserManager.getInst().getUserBeam().getId() + "\"}";
        try {
            new UpPackageProtocol(context, content).postRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap drawableToBitamp(Drawable drawable, Bitmap.Config config) {
        Bitmap bitmap = null;
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        if (config != null) {
            bitmap = Bitmap.createBitmap(w, h, config);
        } else {
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        }
        drawable.setCallback(null);
        return bitmap;
    }

    //获取屏幕宽度
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    //获取屏幕高度
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();
    }

    //bitmap转byte[]
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 获取本APP渠道
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        String channel = "gw01";
        try {
            ApplicationInfo appInfo;
            appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData.containsKey("UMENG_CHANNEL"))
                channel = appInfo.metaData.getString("UMENG_CHANNEL");
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }

    /**
     * 获取时间差 x天x小时x分x秒
     *
     * @param date1
     * @param date2
     * @return
     * @throws ParseException
     */
    public static String DateDiff(Date date1, Date date2) {
        String str_Diff = "";
        long between = (date2.getTime() - date1.getTime()) / 1000;//除以1000是为了转换成秒
        long day = between / 3600 / 24;
        long hour = between / 3600 % 24;
        long minute = between / 60 % 60;
        long second = between % 60;
        if (day > 0) {
            str_Diff = day + "天";
        } else if (hour > 0) {
            str_Diff = hour + "小时";
        } else if (minute > 0) {
            str_Diff = minute + "分";
        } else {
            str_Diff = second + "秒";
        }
        return str_Diff;
    }

    /**
     * 获取系统当前日期
     *
     * @return
     */
    public static Date getSystemCurrentTime() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 处理float小数位数,四舍五入，保留2位
     *
     * @param f
     * @return
     */
    public static double doubleDeal(double f) {
        BigDecimal b = new BigDecimal(f);
        return b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static String getPackageVersion(Context context, String filePath) {
        String version = "V1.0.0";
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = filePath;
            appInfo.publicSourceDir = filePath;
            version = info.versionName;       //得到版本信息
        }
        return version;
    }

    public static String getPkgByPath(Context context, String filePath) {
        String pkg = "";
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = filePath;
            appInfo.publicSourceDir = filePath;
            pkg = info.packageName;       //得到版本信息
        }
        return pkg;
    }

    public static int getVersionCodeByPackage(Context context, String pkg) {
        int code = -1;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(pkg, 0);
            code = info.versionCode;
        } catch (Exception e) {
        }
        return code;
    }

    public static String[] getSdCardList(Context context) {
        String[] sdcards = null;
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
            getVolumePathsMethod.setAccessible(true);
            Object[] params = {};
            Object invoke = getVolumePathsMethod.invoke(storageManager, params);
            sdcards = new String[((String[]) invoke).length];
            for (int i = 0; i < ((String[]) invoke).length; i++) {
                //System.out.println(((String[])invoke)[i]);
                sdcards[i] = ((String[]) invoke)[i];
            }
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return sdcards;
    }


    public static String formatTime(long time, boolean exactly) {
        if (exactly) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);// yy-MM-dd
            return dateFormat.format(new Date(time * 1000));
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
            return dateFormat.format(new Date(time * 1000));
        }
    }

    public static String formatTimeYMD(long time, boolean exactly) {
        if (exactly) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);// yy-MM-dd
            return dateFormat.format(new Date(time));
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
            return dateFormat.format(new Date(time));
        }
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String timet(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        int i = Integer.parseInt(time);
        return sdr.format(new Date(i * 1000L));
    }

    public static String formatTimeMoment(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return dateFormat.format(new Date(time * 1000));
    }

    public static String formatTimeMoment(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(new Date(time * 1000));
    }

    public static String formatTimeSecond(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.CHINA);
        return dateFormat.format(new Date(time * 1000));
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String formatTimeline(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return dateFormat.format(new Date(time));
    }

    public static Date stringToDate(String time, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
//        Date date = simpleDateFormat.parse(time);
        ParsePosition pos = new ParsePosition(8);
        Date date = simpleDateFormat.parse(time, pos);
        return date;
    }

    public static String utcToString(String u, String type) {
        String uNice = u;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssss");
        Date date = null;
        try {
            date = formatter.parse(u);
            SimpleDateFormat format = new SimpleDateFormat(type);
            uNice = format.format(new Date(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return uNice;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static long stringToLong(String time, String format) throws ParseException {
        Date date = stringToDate(time, format);
        return dateToLong(date);
    }

    public static int string2Int(String num) {
        int count = 0;
        try {
            if (!TextUtils.isEmpty(num)) {
                count = Integer.parseInt(num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void startVibrator(Context context, int time) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(time);
    }

    //获取可用内存的大小(单位M)
    public static long getAvailableMemoryMB(Context context) {
        // 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem / (1024 * 1024);
    }

    //获取可用内存的大小(单位Kb)
    public static long getAvailableMemoryKB(Context context) {
        // 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem / 1024;
    }

    //获取全部内存的大小(单位M)
    public static long getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue();// 获得系统总内存，单位是KB
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return initial_memory / (1024);
    }

    //获取内存使用率
    public static float getMemoryUsage(Context context) {
        long total = getTotalMemory(context);
        if (total == 0) {
            return 50;
        } else {
            long usage = total - getAvailableMemoryMB(context);
            return usage * 1.0f / total;
        }
    }

    public static void setTextMultiColor(TextView textView, String targetString, String parentString, int targetColor) {
        int index = getIndexForWord(targetString, parentString, 0);
        if (index >= 0 && !TextUtils.isEmpty(targetString)) {
            SpannableStringBuilder spannableString = new SpannableStringBuilder(parentString);
            spannableString.setSpan(new ForegroundColorSpan(targetColor), index, index + targetString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);
        } else {
            textView.setText(parentString);
        }
    }

    public static void setTextMultiColor(TextView textView, float size, String targetString, String parentString, String targetColor) {
        if (null != textView) {
            int index = getIndexForWord(targetString, parentString, 0);
            if (index >= 0 && !TextUtils.isEmpty(targetString)) {
                SpannableStringBuilder spannableString = new SpannableStringBuilder(parentString);
                spannableString.setSpan(new ForegroundColorSpan(CommonHelper.parseColor(targetColor)), index, index + targetString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new AbsoluteSizeSpan((int) size), index, index + targetString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView.setText(spannableString);
            } else {
                textView.setText(parentString);
            }
        }
    }

    public static void setTextMultiColor(TextView textView, float size, String targetString, String parentString) {
        if (null != textView) {
            int index = getIndexForWord(targetString, parentString, 0);
            if (index >= 0 && !TextUtils.isEmpty(targetString)) {
                SpannableStringBuilder spannableString = new SpannableStringBuilder(parentString);
                spannableString.setSpan(new AbsoluteSizeSpan((int) size), index, index + targetString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                textView.setText(spannableString);
            } else {
                textView.setText(parentString);
            }
        }
    }

    public static void setTextMultiColor(TextView textView, String targetString, String targetString1, String parentString, int targetColor) {
        int index = getIndexForWord(targetString, parentString, 0);
        int index1 = getIndexForWord(targetString1, parentString, index + targetString.length());
        if (index >= 0 && !TextUtils.isEmpty(targetString)) {
            SpannableStringBuilder spannableString = new SpannableStringBuilder(parentString);
            spannableString.setSpan(new ForegroundColorSpan(targetColor), index, index + targetString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(targetColor), index1, index1 + targetString1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);
        } else {
            textView.setText(parentString);
        }
    }

    //解析字符串颜色
    public static int parseColor(String color) {
        String colorString = color.trim();
        if (TextUtils.isEmpty(colorString)) return 0;
        if (!colorString.startsWith("#")) return 0;
        int colorValue = 0;
        try {
            colorValue = Color.parseColor(colorString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return colorValue;
    }

    public static int getIndexForWord(String word, String words, int from) {
        if (TextUtils.isEmpty(word)) return -1;
        if (words.contains(word.trim()) || words.contains(word)) {
            return words.indexOf(word, from);
        }
        return -1;
    }

    /**
     * 判断当前界面是否是桌面
     */
    public static boolean isHome(Context contenxt) {

        ActivityManager mActivityManager = (ActivityManager) contenxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes(contenxt).contains(rti.get(0).topActivity.getPackageName());
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    public static List<String> getHomes(Context mContext) {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = mContext.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    /**
     * 获取app的名称 这个方法获取最近运行任何中最上面的一个应用的包名, 进行了api版本的判断,然后利用不同的方法获取包名,具有兼容性
     *
     * @return 返回包名, 如果出现异常或者获取失败返回""
     */
    public static String getTopAppInfoPackageName(Context mContext) {
        if (Build.VERSION.SDK_INT < 21) { // 如果版本低于22
            return getTopApp(mContext);
        } else {
            //因为遇到cm5.1.1系统使用getForegroundApp（）此方法获得的值为空而使用<21的方法却能获得顶部包名 因此做判断
            String topPackage = getTopApp(mContext);

            return TextUtils.isEmpty(getForegroundApp()) ? topPackage : getForegroundApp();

        }
    }

    /**
     * api21以下获取当前运行应用包名的方法
     *
     * @param context 上下文参数
     */
    public static String getTopApp(Context context) {
        // 获取到activity的管理的类
        ActivityManager m = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取最近的一个运行的任务的信息
        List<ActivityManager.RunningTaskInfo> tasks = m.getRunningTasks(1);

        if (tasks != null && tasks.size() > 0) { // 如果集合不是空的
            // 返回任务栈中最上面的一个
            ActivityManager.RunningTaskInfo info = m.getRunningTasks(1).get(0);
            return info.baseActivity.getPackageName();
        } else {
            return "";
        }
    }


    /**
     * api21以上
     * 不需要手动授权判断当前应用包名的方法(此处于判断当前是否是桌面)
     */
    public static String getForegroundApp() {
        File[] files = new File("/proc").listFiles();
        int lowestOomScore = Integer.MAX_VALUE;
        String foregroundProcess = null;
        for (File file : files) {
            if (!file.isDirectory()) {
                continue;
            }
            int pid;
            try {
                pid = Integer.parseInt(file.getName());
            } catch (NumberFormatException e) {
                continue;
            }
            try {
                String cgroup = read(String.format("/proc/%d/cgroup", pid));
                String[] lines = cgroup.split("\n");
                String cpuSubsystem;
                String cpuaccctSubsystem;

                if (lines.length == 2) {//有的手机里cgroup包含2行或者3行，我们取cpu和cpuacct两行数据
                    cpuSubsystem = lines[0];
                    cpuaccctSubsystem = lines[1];
                } else if (lines.length == 3) {
                    cpuSubsystem = lines[0];
                    cpuaccctSubsystem = lines[2];
                } else {
                    continue;
                }
                if (!cpuaccctSubsystem.endsWith(Integer.toString(pid))) {
                    // not an application process
                    continue;
                }
                if (cpuSubsystem.endsWith("bg_non_interactive")) {
                    // background policy
                    continue;
                }
                String cmdline = read(String.format("/proc/%d/cmdline", pid));
                if (cmdline.contains("com.android.systemui")) {
                    continue;
                }
                int uid = Integer.parseInt(
                        cpuaccctSubsystem.split(":")[2].split("/")[1].replace("uid_", ""));
                if (uid >= 1000 && uid <= 1038) {
                    // system process
                    continue;
                }
                int appId = uid - AID_APP;
                int userId = 0;
                // loop until we get the correct user id.
                // 100000 is the offset for each user.
                while (appId > AID_USER) {
                    appId -= AID_USER;
                    userId++;
                }
                if (appId < 0) {
                    continue;
                }
                // u{user_id}_a{app_id} is used on API 17+ for multiple user account support.
                // String uidName = String.format("u%d_a%d", userId, appId);
                File oomScoreAdj = new File(String.format("/proc/%d/oom_score_adj", pid));
                if (oomScoreAdj.canRead()) {
                    int oomAdj = 0;
                    String num = read(oomScoreAdj.getAbsolutePath());
                    if (!TextUtils.isEmpty(num)) {
                        if (num.startsWith("0") && num.length() > 1) {
                            num = num.substring(1);
                        }
                        oomAdj = Integer.parseInt(num);
                    }
                    if (oomAdj != 0) {
                        continue;
                    }
                }
                int oomscore = Integer.parseInt(read(String.format("/proc/%d/oom_score", pid)));
                if (oomscore < lowestOomScore) {
                    lowestOomScore = oomscore;
                    foregroundProcess = cmdline;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return foregroundProcess;
    }

    private static String read(String path) throws IOException {
        StringBuilder output = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        output.append(reader.readLine());
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            output.append('\n').append(line);
        }
        reader.close();
        return output.toString().trim();//不调用trim()，包名后面会带有乱码
    }

    //打开网络设置
    public static void openNetworkSeeting(Context context) {
        // 跳转到系统设置界面
        Intent intentToNetwork = null;
        if (Build.VERSION.SDK_INT > 16) {
            intentToNetwork = new Intent(Settings.ACTION_SETTINGS);
        } else {
            intentToNetwork = new Intent();
            ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intentToNetwork.setComponent(componentName);
            intentToNetwork.setAction("android.intent.action.VIEW");
        }
        context.startActivity(intentToNetwork);
    }

    /*
     * 剪切图片
     */
    public static File crop(Uri uri, File cropFile, Context context, int code) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        cropFile = new File(CommonHelper.getFileJPGPath(context, System.currentTimeMillis() + ".jpg")); // 以时间秒为文件名
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        intent.putExtra("output", Uri.fromFile(cropFile));  // 传入目标文件
        ((Activity) context).startActivityForResult(intent, Constants.PHOTO_REQUEST_CUT);
        return cropFile;
    }

    /**
     * 获取控件宽
     */
    public static int getWidth(final View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);

        return view.getMeasuredWidth();
    }

    public static int getConnectType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return networkInfo.getType();
        }
        return -1;
    }

    public static boolean checkCellphone(String cellphone) {
//        String regex = "(?:\\(?[0\\+]?\\d{1,3}\\)?)[\\s-]?(?:0|\\d{1,4})[\\s-]?(?:(?:13\\d{9})|(?:\\d{7,8}))";
//        String regex = "^((\+\d{1,3}(-| )?\(?\d\)?(-| )?\d{1,5})|(\(?\d{2,6}\)?))(-| )?(\d{3,4})(-| )?(\d{4})(( x| ext)\d{1,5}){0,1}$";
        String regex = "^\\d+$";
        return cellphone.matches(regex);
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else {
            return day2 - day1;
        }
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    public static void copy(Context context, String copyContent, String str) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", copyContent);
        cm.setPrimaryClip(mClipData);
        if (!str.isEmpty()) {
            ToastUtils.showLongToast(context, str);
        }
    }

    /**
     * 跳转到应用权限设置页面 http://www.tuicool.com/articles/jUby6rA
     *
     * @param context 传入app 或者 activity context，通过context获取应用packegename，之后通过packegename跳转制定应用
     * @return 是否是miui
     */
    public static void gotoMIUIPermissionSettings(Context context) {
        applyMiuiPermission(context);
    }

    public static void gotoOPPOPermissionSettings(Context context) {
        try {
            Intent localIntent = new Intent("oppo.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(localIntent);
        } catch (ActivityNotFoundException e) {
            ToastUtils.showLongToast(context, "跳转失败");
        }
    }

    /**
     * 小米 ROM 权限申请
     */
    public static void applyMiuiPermission(Context context) {

        getMiuiSettingIntent(context);
    }


    /**
     * 跳转到MIUI8权限设置界面
     */
    private static void getMiuiSettingIntent(Context context) {
        try {
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent
                    .setClassName("com.miui.securitycenter",
                            "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(localIntent);
        } catch (Exception e) {
            getOtherMiuiSetting(context);
        }
    }

    /**
     * 跳转到其他MIUI权限设置界面
     */
    private static void getOtherMiuiSetting(Context context) {
        try {
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent
                    .setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(localIntent);
        } catch (Exception e) {
            goNormalIntentSetting(context);
        }
    }


    private static void goNormalIntentSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showLongToast(context, "跳转失败");
        }
    }

    /**
     * 检查手机是否是miui
     *
     * @return
     * @ref http://dev.xiaomi.com/doc/p=254/index.html
     */
    public static boolean isMIUI() {
        String device = Build.MANUFACTURER;
        return device.equals("Xiaomi");
    }

    public static boolean isOPPO() {
        String device = Build.MANUFACTURER;
        if (device.equals("OPPO")) {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
        } else {
            return false;
        }
    }

    public static String form2(double num) {
        String temp = String.valueOf(num);
        if (temp.length() - temp.indexOf(".") == 2) {
            BigDecimal bd = new BigDecimal(num);
            bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
            return bd.toString();
        } else {
            return temp.substring(0, temp.indexOf(".") + 3);
        }

    }

    public static boolean isSim(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }
        String simSer = tm.getSimSerialNumber();
        return !TextUtils.isEmpty(simSer);

    }

    public synchronized static List<Map<String, String>> getPhoneList(Context context) {
        List<Map<String, String>> list = new ArrayList<>();
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, null, null, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            // 取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name = cursor.getString(nameFieldColumnIndex);
            String number = cursor.getString(numberFieldColumnIndex);
            Map<String, String> map = new HashMap<>();
            map.put("number", number);
            map.put("name", name);
            list.add(map);
        }
        return list;
    }

    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    //生成随机数字和字母,
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static int getCheckTimeStamp(long timeOffset) {
        int timeNice = 0;
        long clientTime = System.currentTimeMillis();
        timeNice = (int) ((clientTime + timeOffset) / 1000);
        return timeNice;
    }

    public static boolean openDeeplink(Context context, String deeplink) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(deeplink));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 判断网络情况
     *
     * @param context 上下文
     * @return false 表示没有网络 true 表示有网络
     */
    public static boolean isNetworkAvalible(Context context) {
        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();

            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否安装
     * check the app is installed
     */
    public static boolean isAppInstalled(String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = QuKanDianApplication.getContext().getPackageManager().getPackageInfo(packagename, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            //System.out.println("没有安装");
            return false;
        } else {
            //System.out.println("已经安装");
            return true;
        }
    }

    //启动app
    public static boolean startApp(String appId) {
        PackageManager packageManager = QuKanDianApplication.getContext().getPackageManager();
        Intent intent = new Intent();
        intent = packageManager.getLaunchIntentForPackage(appId);
        if (intent != null) {
            QuKanDianApplication.getContext().startActivity(intent);
            return true;
        } else {
//            Toast.makeText(ActFsdAndJtj.this, "未安装", Toast.LENGTH_LONG).show();
            return false;
        }
    }

//    /**
//     * make true current connect service is wifi
//     * @param mContext
//     * @return
//     */
//    private static boolean isWifi(Context mContext) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
//        if (activeNetInfo != null
//                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//            return true;
//        }
//        return false;
//    }


    public static String MyCheck(String rand, int time, String... lists) {
        String key = "323311dfe86a4c9eba0d2a2820ecab47";

        List<String> arrList = new ArrayList(Arrays.asList(lists));

        arrList.add("nonceStr" + rand);
        arrList.add("timestamp" + time);
        Collections.sort(arrList);
        String str = "";
        for (String s : arrList) {
            str += s;
        }
        md5(key + str);
        return md5(key + str);
    }

    public static void openUrl(String urls, Context context) {
        if (!TextUtils.isEmpty(urls)) {
            if (urls.contains("http")) {
                if (urls.contains("flag=chbyxj")) {
                    ActivityUtils.startToChbyxjUrlActivity(context, urls);
                } else {
                    ActivityUtils.startToWebviewAct(context, urls);
                }
            } else if (urls.startsWith("xcxα")) {
                // 分享小程序
                if (context instanceof Activity) {
                    OperateUtil.shareMiniAppMain(urls, (MainActivity) context, null);
                }
            } else {
                if (urls.contains("|")) {
                    String[] url = urls.split("\\|");
                    if (url.length > 1) {
                        ActivityUtils.startToAssignActivity(context, url[0], Integer.valueOf(url[1]));
                    } else {
                        ActivityUtils.startToAssignActivity(context, urls, -1);
                    }
                } else {
                    ActivityUtils.startToAssignActivity(context, urls, -1);
                }
            }
        }
    }

    /**
     * 【加密规则】：url参数base64加密之后，剪切前十位，移接到末尾url参数base64之后，剪切前十位，移接到末尾
     * @param unBase64String
     * @return
     */
    public static String encodeBase64(String unBase64String) {
        String encode;

        String base64String = EncodeUtils.base64Encode2String(unBase64String.getBytes());
        String top10String = base64String.substring(0,10);
        String lastString = base64String.substring(10);

        encode = lastString + top10String;

        return encode;
    }

    public static String getLocation(Context mContext){
        double[] latlng = DeviceUtil.getLatlng(mContext);
        String location = "0,0";
        if (latlng != null && latlng.length == 2) {
            location = latlng[0] + "," + latlng[1];
        }
        return location;
    }
}
