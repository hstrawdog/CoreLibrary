package com.easy.core.utils.file

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.easy.core.CoreConfig
import com.easy.core.utils.data.isNotNull
import com.easy.core.utils.file.FileUtils.defFileName
import com.easy.core.utils.file.FileUtils.getDefFileName
import com.easy.core.utils.file.FileUtils.isQ
import java.io.File

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.utils.file
 * @Date : 17:14
 * @Email : qiqiang213@gmail.com
 * @Describe :
 *  android:requestLegacyExternalStorage="true" 在targetSdkVersion 31  直接忽略  无法在随意存储在任意位置
 *  内部私有目录   FileUtils.getCacheDir() + File.separator + fileName
 *  外部私有存储  FileUtils.getExternalFilesDir(fileName = Environment.DIRECTORY_DOWNLOADS) + File.separator + fileName
 *  外部公共存储   Environment.DIRECTORY_PICTURES + File.separator + fileName
 *    梳理适配流程
 *     1. 需要保存并通知相册的图片
 *          ->   Q以上直接保存至相册
 *          ->   Q一下在区分保存
 *     2.
 *     总结
 *          Q以上直接保存至相册目录需要   @Environment.DIRECTORY_PICTURES //   Environment.DIRECTORY_DCIM
 */
object SaveBitmapUtils {

    /**
     *
     * @param bitmap Bitmap
     * @param path String  FileUtils.getCacheDir()
     * 如果需要通知相册  请直接保存至相册目录下
     */
    @JvmStatic
    fun saveBitmap2AppCache(bitmap: Bitmap?, relativePath: String = "",
                            fileName: String = getDefFileName(".png")): String {
        val path = if (relativePath.isNotNull()) {
            FilePathTools.getCacheDir() + File.separator + relativePath + File.separator + fileName
        } else {
            FilePathTools.getCacheDir() + File.separator + fileName
        }
        FileUtils.saveBitmap(bitmap, path)
        return path
    }

    /**
     * @see  android.os.Environment.DIRECTORY_PICTURES
     * @see  android.os.Environment.DIRECTORY_DCIM
     * {@link Environment}
     * @param relativePath String
     * @param fileName String
     *  保存至公有目录上  Q 以上有指定目录 需要 通过  MediaStore 进行保存
     *  Q 一下key直接使用文件读写进行保存
     */
    @JvmStatic
    fun saveBitmap2Pictures(bitmap: Bitmap?, relativePath: String = "",
                            fileName: String = getDefFileName(".png")): Uri? {
        if (bitmap == null) {
            return null
        }
        return FileUtils.saveBitmap2Pictures(relativePath, fileName, bitmap)
    }

    /**
     *  保存图片至 外部存储 指定目录上
     *   Q 10 只能 在固定的  目录上  如果需要通知相册-> 直接保存到相册
     *   Q 以下 如果有文件读写权限 随便存放
     * @param bitmap Bitmap
     * @param path String
     */
    @JvmStatic
    fun saveBitmap2ExternalPrivate(bitmap: Bitmap?, relativePath: String = "", fileName: String): String {
        if (bitmap == null) {
            return ""
        }
        if (isQ()) {
            val path = if (relativePath.isNotNull()) {
                FilePathTools.getExternalFilesDir() + File.separator + relativePath + File.separator + fileName
            } else {
                FilePathTools.getExternalFilesDir() + File.separator + fileName
            }
            FileUtils.saveBitmap(bitmap, path)
            return path
        } else {
            val path = if (relativePath.isNotNull()) {
                FilePathTools.getStorageDirectory() + File.separator + relativePath + File.separator + fileName
            } else {
                FilePathTools.getStorageDirectory() + File.separator + fileName
            }
            FileUtils.saveBitmap(bitmap, path)
            // 发送至相册
            MediaStore.Images.Media.insertImage(CoreConfig.applicationContext.contentResolver, path, File(path).name, null)
            CoreConfig.applicationContext.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(path).parentFile)))
            return path
        }
    }

}