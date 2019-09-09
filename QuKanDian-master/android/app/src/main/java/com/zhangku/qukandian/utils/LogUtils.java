package com.zhangku.qukandian.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.zhangku.qukandian.BuildConfig;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuzuoning on 2017/5/19.
 */

public class LogUtils {
    private static boolean isOpen = false;
//    private static boolean isOpen = true;
    private static boolean isSaveLog = false;
    private static String logFileRootPath = Environment.getExternalStorageDirectory()+"/zhangku/loggo/";
    private static String logContent = "";
    public static void LogV(String str) {
        if (isOpen) {
            Log.v("QKD", str);
        }
    }

    public static void LogD(String str) {
        if (isOpen) {
            Log.d("QKD", str);
        }
    }

    public static void LogI(String str) {
        if (isOpen) {
            Log.i("QKD", str);
        }
    }

    public static void LogW(String str) {
        if (isOpen) {
            Log.w("QKD", str);
        }
    }

    public static void LogE(String str) {
        if (isOpen) {
//            AddLogContent(str);
            Log.e("QKD", str);
        }
    }

    public static void AddLogContent(String str) {
        if (!isSaveLog) return;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(new Date());
        logContent += nowTime+"  "+str+"\n";
        if (logContent.length() > 1000) {
            SaveLog();
        }
    }

    public static void SaveLog() {
        if (!isSaveLog) return;
        if (TextUtils.isEmpty(logContent)) return;

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    synchronized(logContent)
                    {
                        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                        Date todayDate = new Date();
                        String todayDateNiceStr = df.format(todayDate);

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(todayDate);
                        calendar.add(Calendar.DAY_OF_MONTH, -2);
                        Date beforeDate = calendar.getTime();
                        String beforeDateNiceStr = df.format(beforeDate);

                        String logFilePath = logFileRootPath + "/" + todayDateNiceStr + ".log";
                        String logFilePath2 = logFileRootPath + "/" + beforeDateNiceStr + ".log";
                        File tmpFile = new File(logFilePath2);
                        if (tmpFile.exists()) {
                            tmpFile.delete();
                        }
                        FileUtil.write(logFilePath, logContent, true);
                        logContent = "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void ShitE(String msg) {
        if (isOpen) {
            int strLength = msg.length();
            int start = 0;
            int end = 2000;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e("QKD" + i, msg.substring(start, end));
                    start = end;
                    end = end + 2000;
                } else {
                    Log.e("QKD" + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void ShitE(String str,String msg) {
        if (isOpen) {
            int strLength = msg.length();
            int start = 0;
            int end = 2000;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e(i + str, msg.substring(start, end));
                    start = end;
                    end = end + 2000;
                } else {
                    Log.e(i + str, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }
}
