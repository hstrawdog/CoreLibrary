package com.hqq.core.utils;

import android.content.Context;
import android.os.Environment;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @FileName :   FilePathUtils
 * @Date : 2019/11/29 0029  上午 9:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class FilePathUtils {
    /**
     * 内部存储
     */
    /**
     * @return /data
     */
    public static CharSequence getPath4Data() {
        return Environment.getDataDirectory().getPath();
    }

    /**
     * @param context
     * @return /data/data/com.learn.test/cache
     */
    public static String getCacheDir(Context context) {
        return context.getCacheDir().getPath();
    }

    /**
     * @param context
     * @return /data/data/com.learn.test/files
     */
    public static String getFilesDir(Context context) {
        return context.getFilesDir().getPath();
    }

    /**
     * @param context
     * @return /data/data/com.learn.test/files
     */
    public static String getFileStreamPath(Context context) {
        return context.getFileStreamPath("").getPath();
    }

    /**
     * 同上
     *
     * @param context
     * @param fileName
     * @return /data/data/com.learn.test/files/fileName
     */
    public static String getFileStreamPath(Context context, String fileName) {
        return context.getFileStreamPath("").getPath();
    }

    /**
     * 外部存储
     */
    /**
     * @return /storage/emulated/0
     */
    public static String getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * /storage/emulated/0/Android/data/com.learn.test/cache
     *
     * @param context
     * @return
     */
    public static String getExternalCacheDir(Context context) {
        return context.getExternalCacheDir().getPath();
    }

    /**
     * @param context
     * @return /storage/emulated/0/Android/data/com.learn.test/files
     */
    public static String getExternalFilesDir(Context context) {
        return context.getExternalFilesDir("").getPath();
    }

    /**
     * @param context
     * @param fileName DIRECTORY_MUSIC
     *                 DIRECTORY_ALARMS
     *                 DIRECTORY_NOTIFICATIONS
     *                 Environment.DIRECTORY_PICTURES
     *                 DIRECTORY_MOVIES
     *                 DIRECTORY_DOWNLOADS
     *                 DIRECTORY_DCIM
     *                 DIRECTORY_DOCUMENTS
     * @return /storage/emulated/0/Android/data/com.learn.test/files/fileName
     */
    public static String getExternalFilesDir(Context context, String fileName) {
        return context.getExternalFilesDir(fileName).getPath();
    }

    /**
     * 公共存储 6.0及之后的系统需要动态申请权限
     */
    /**
     * @return /storage/emulated/0
     */
    public static String getSdCardExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * @return /storage/emulated/0
     */
    public static String getSdCardExternalStoragePublicDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * @param fileName 文件夹名
     * @return /storage/emulated/0/fileName
     */
    public static String getSdCardExternalStoragePublicDirectory(String fileName) {
        return Environment.getExternalStorageDirectory().getPath();
    }
    /**
     * 文件名
     */
    /**
     * 随机名称
     *
     * @return
     */
    public static String getDefFileName() {
        return System.currentTimeMillis() + "";
    }
    /**
     * 带后缀的文件名称
     *
     * @param suffix
     * @return
     */
    public static String getDefFileName(String suffix) {
        return System.currentTimeMillis() + suffix;
    }

}
