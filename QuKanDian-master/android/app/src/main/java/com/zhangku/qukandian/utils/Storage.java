package com.zhangku.qukandian.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.text.DecimalFormat;

/**
 * The helper class used to check the available space.
 * 
 */
public class Storage {

	/**
	 * Get the available space of the Android Download/Cache content directory.
	 * 
	 * @return available space
	 */
	public static long cachePartitionAvailableSpace() {
		StatFs stat = new StatFs(Environment.getDownloadCacheDirectory().getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * Get the available space of the Android data directory.
	 * 
	 * @return available space
	 */
	public static long dataPartitionAvailableSpace() {
		StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}
	
	public static long recordAvailableSpace(Context context){
		String filePath = null;
		if (CommonHelper.checkSDCard(context)) {
			int api = android.os.Build.VERSION.SDK_INT;
			if (api < 17) {
				filePath = Environment.getExternalStorageDirectory()
						.getAbsolutePath();
			}else{
				filePath = "/sdcard";
			}
			StatFs stat = new StatFs(filePath);
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		}
		return -1;
	}

	/**
	 * To check whether the Android external storage directory is available or not.
	 * 
	 * @return
	 */
	public static boolean externalStorageAvailable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}

	/**
	 * Get the available space of the Android internal storage directory.
	 * 
	 * @return available space
	 */
	public static long internalStorageAvailableSpace() {
		StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * Get the total space of the Android internal storage directory.
	 * 
	 * @return total space
	 */
	public static long internalStorageTotalSpace() {
		StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
		long blockSize = stat.getBlockSize();
		long blockCount = stat.getBlockCount();
		return blockCount * blockSize;
	}

	/**
	 * Get the available space of the Android external storage directory.
	 * 
	 * @return available space
	 */
	public static long externalStorageAvailableSpace() {
		if (!externalStorageAvailable()) {
			return -1;
		}
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	/**
	 * Get the total space of the Android external storage directory.
	 * 
	 * @return total space
	 */
	public static long externalStorageTotalSpace() {
		if (!externalStorageAvailable()) {
			return -1;
		}
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		long blockSize = stat.getBlockSize();
		long blockCount = stat.getBlockCount();
		return blockCount * blockSize;
	}

	/**
	 * 
	 * @param size
	 *            The size to format.
	 * @return
	 */
	public static String readableSize(long size) {
		if (size <= 0) {
			return "0";
		}
		final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.00").format(size / Math.pow(1024, digitGroups))
				+ units[digitGroups];
	}
}
