package com.easy.core.utils.file

import android.content.Context
import android.os.Environment
import com.easy.core.CoreConfig

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.utils.file
 * @Date : 00:58
 * @Email : qiqiang213@gmail.com
 * @Describe :
 *   * Android 10 区分存储 / 沙箱存储
 * 1. 内部存储package下cache  database  等
 * 2. 外部公共存储 Download  登录
 * 3. 外部私有存储 Android/data/package
 */
object FilePathTools {

    //region  内部私有存储 /data/user/0/
    /**
     * @param context
     * @return /data/user/0/com.easy.core/cache
     */
    @JvmStatic
    fun getCacheDir(context: Context = CoreConfig.applicationContext): String {
        return context.cacheDir.path
    }

    /**
     *
     * @param context Context
     * @return String /data/user/0/ com.easy.core/cache
     */
    @JvmStatic
    fun getCodeCacheDir(context: Context): String {
        return context.codeCacheDir.path
    }

    /**
     *
     * @return String /data/user/0/ com.easy.core/code_cache
     */
    @JvmStatic
    fun getCodeCacheDir(): String {
        return getCodeCacheDir(CoreConfig.applicationContext)
    }

    /**
     * @param context
     * @return /data/user/0/ com.easy.core/files
     */
    @JvmStatic
    fun getFilesDir(context: Context): String {
        return context.filesDir.path
    }

    /**
     * 同上
     *
     * @param context
     * @param fileName
     * @return /data/data/package/files/fileName
     */
    @JvmStatic
    fun getFileStreamPath(context: Context, fileName: String = ""): String {
        return context.getFileStreamPath(fileName).path
    }

    /**
     *
     * 内部存储下的私有目录
     * @param fileName
     * @return String /data/user/0/com.easy.core/databases/$fileName
     */
    @JvmStatic
    fun getDataBaseDir(fileName: String): String {
        return CoreConfig.applicationContext.getDatabasePath(fileName)?.path + ""
    }

    /**
     *  获取包下的 指定目录文件夹
     * @param fileName String
     * @return File? /data/user/0/com.easy.core/app_
     */
    fun getPackageDir(fileName: String): String {
        return CoreConfig.applicationContext.getDir(fileName, Context.MODE_APPEND).path
    }

    //endregion

    //region 外部存储
    /**
     * /storage/emulated/0/Android/data/package/cache
     *
     * @param context
     * @return
     */
    @JvmStatic
    fun getExternalCacheDir(context: Context): String {
        return context.externalCacheDir!!.path
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
     * @return /storage/emulated/0/Android/data/package/files/fileName
     * app 卸载后 目录也会删除掉
     */
    @kotlin.jvm.JvmStatic
    fun getExternalFilesDir(context: Context = CoreConfig.applicationContext, fileName: String = ""): String {
        return context.getExternalFilesDir(fileName)!!.path
    }

    //endregion

    //region 公共存储
    //Android 10 以下可以直接访问  以上需要通过  MediaStore
    /**
     * /storage/emulated/0/Pictures
     */
    @kotlin.jvm.JvmStatic
    fun getExternalPicturesPath(): String {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path
    }

    /**
     *  下载地址地址
     * @return String
     */
    @JvmStatic
    fun getExternalDownloadsPath(): String {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
    }

    /**
     *  获取根目录地址
     * @return String /storage/emulated/0/
     */
    @JvmStatic
    fun getStorageDirectory(): String {
        return Environment.getExternalStorageDirectory().absolutePath
    }

    //endregion

}
