package com.zhangku.qukandian.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * 创建者          xuzhida
 * 创建日期        2019/2/13
 * sd类
 */
public class SDUtils {
    /**
     * SD卡总容量
     */
    public static long getSDAllSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //获取所有数据块数
        long allBlocks = sf.getBlockCount();
        //返回SD卡大小
        //return allBlocks * blockSize; //单位Byte
        //return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize)/1024/1024; //单位MB
    }

    /**
     * SD卡是否存在
     * @return
     */
    public static boolean ExistSDCard() {
        // MEDIA_MOUNTED sd卡在手机上正常使用状态
        // Environment.MEDIA_UNMOUNTED // 用户手工到手机设置中卸载sd卡之后的状态 
        // Environment.MEDIA_REMOVED // 用户手动卸载，然后将sd卡从手机取出之后的状态 
        // Environment.MEDIA_BAD_REMOVAL // 用户未到手机设置中手动卸载sd卡，直接拨出之后的状态 
        // Environment.MEDIA_SHARED // 手机直接连接到电脑作为u盘使用之后的状态 
        // Environment.MEDIA_CHECKINGS // 手机正在扫描sd卡过程中的状态 
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }
    /**
     * SD卡剩余空间
     */
    public static long getSDFreeSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
    }
}
