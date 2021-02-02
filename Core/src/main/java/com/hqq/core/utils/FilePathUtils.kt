package com.hqq.core.utils

import android.content.Context
import android.os.Environment

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @FileName :   FilePathUtils
 * @Date : 2019/11/29 0029  上午 9:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object FilePathUtils {
    /**
     * 内部存储
     */
    /**
     * @return /data
     */
    @kotlin.jvm.JvmStatic
    val path4Data: CharSequence
        get() = Environment.getDataDirectory().path

    /**
     * @param context
     * @return /data/data/com.learn.test/cache
     */
    @kotlin.jvm.JvmStatic
    fun getCacheDir(context: Context): String {
        return context.cacheDir.path
    }

    /**
     * @param context
     * @return /data/data/com.learn.test/files
     */
    @kotlin.jvm.JvmStatic
    fun getFilesDir(context: Context): String {
        return context.filesDir.path
    }

    /**
     * @param context
     * @return /data/data/com.learn.test/files
     */
    fun getFileStreamPath(context: Context): String {
        return context.getFileStreamPath("").path
    }

    /**
     * 同上
     *
     * @param context
     * @param fileName
     * @return /data/data/com.learn.test/files/fileName
     */
    @kotlin.jvm.JvmStatic
    fun getFileStreamPath(context: Context, fileName: String?): String {
        return context.getFileStreamPath("").path
    }
    /**
     * 外部存储
     */
    /**
     * @return /storage/emulated/0
     */
    @kotlin.jvm.JvmStatic
    val externalStorageDirectory: String
        get() = Environment.getExternalStorageDirectory().path

    /**
     * /storage/emulated/0/Android/data/com.learn.test/cache
     *
     * @param context
     * @return
     */
    @kotlin.jvm.JvmStatic
    fun getExternalCacheDir(context: Context): String {
        return context.externalCacheDir!!.path
    }

    /**
     * @param context
     * @return /storage/emulated/0/Android/data/com.learn.test/files
     */
    fun getExternalFilesDir(context: Context): String {
        return context.getExternalFilesDir("")!!.path
    }

    /**
     * @param context
     * @param fileName DIRECTORY_MUSIC
     * DIRECTORY_ALARMS
     * DIRECTORY_NOTIFICATIONS
     * Environment.DIRECTORY_PICTURES
     * DIRECTORY_MOVIES
     * DIRECTORY_DOWNLOADS
     * DIRECTORY_DCIM
     * DIRECTORY_DOCUMENTS
     * @return /storage/emulated/0/Android/data/com.learn.test/files/fileName
     */
    @kotlin.jvm.JvmStatic
    fun getExternalFilesDir(context: Context, fileName: String?): String {
        return context.getExternalFilesDir(fileName)!!.path
    }
    /**
     * 公共存储 6.0及之后的系统需要动态申请权限
     */
    /**
     * @return /storage/emulated/0
     */
    @kotlin.jvm.JvmStatic
    val sdCardExternalStorageDirectory: String
        get() = Environment.getExternalStorageDirectory().path

    /**
     * @return /storage/emulated/0
     */
    @kotlin.jvm.JvmStatic
    val sdCardExternalStoragePublicDirectory: String
        get() = Environment.getExternalStorageDirectory().path

    /**
     * @param fileName 文件夹名
     * @return /storage/emulated/0/fileName
     */
    fun getSdCardExternalStoragePublicDirectory(fileName: String?): String {
        return Environment.getExternalStorageDirectory().path
    }
    /**
     * 文件名
     */
    /**
     * 随机名称
     *
     * @return
     */
    val defFileName: String
        get() = System.currentTimeMillis().toString() + ""

    /**
     * 带后缀的文件名称
     *
     * @param suffix
     * @return
     */
    fun getDefFileName(suffix: String): String {
        return defFileName + suffix
    }
}