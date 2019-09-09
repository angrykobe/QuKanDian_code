package com.zhangku.qukandian.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/5
 * text  bug记录，为了某些间歇新bug使用
 */
public class FileBuildForBugUtils {
    private static final String filePath = "/sdcard/zhangku/";
    private static final String fileName = "log.txt";

    public static void Log(){
        Log.e("wayway","ClassName = "+Thread.currentThread().getStackTrace()[3].getFileName() + " line== " +
                Thread.currentThread().getStackTrace()[3].getLineNumber());
    }
    public static void Log(String content){
        Log.e("wayway","ClassName = "+Thread.currentThread().getStackTrace()[3].getFileName() + " line== " +
                Thread.currentThread().getStackTrace()[3].getLineNumber() + "  content == "+ content);
    }

    //FileBuildForBugUtils.writeTxtToFile("startCount  803  " + Thread.currentThread().getStackTrace()[2].getLineNumber());
    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent) {
        strcontent = "ClassName = "+Thread.currentThread().getStackTrace()[3].getFileName() + " " +
                        Thread.currentThread().getStackTrace()[3].getLineNumber() + " Content == " + strcontent;
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }
}
