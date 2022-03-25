package com.hqq.core.utils.file

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.hqq.core.CoreConfig
import com.hqq.core.utils.ToastUtils
import java.io.File

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils.file
 * @Date : 17:14
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object SaveBitmap {

    /**
     *
     * @param bitmap Bitmap
     * @param path String  FileUtils.getCacheDir()
     * 如果需要通知相册  请直接保存至相册目录下
     */
    fun saveBitmap2AppCache(bitmap: Bitmap, path: String) {
        FileUtils.saveBitmap(bitmap, path)
    }

    /**
     * @see  android.os.Environment.DIRECTORY_PICTURES
     * @see  android.os.Environment.DIRECTORY_DCIM
     * {@link Environment}
     * @param bitmap Bitmap
     * @param path String
     *  保存至公有目录上  Q 以上有指定目录 需要 通过  MediaStore 进行保存
     *  Q 一下key直接使用文件读写进行保存
     */
    fun saveBitmap2Pictures(bitmap: Bitmap, path: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            /**
             * 需要以 DCIM / Pictures开头
             *  {@link Environment} Environment.DIRECTORY_PICTURES + File.separator + fileName
             *  #Environment.DIRECTORY_DCIM + File.separator + fileName
             */
            FileUtils.saveBitmap2Public(CoreConfig.applicationContext, bitmap, path)
            ToastUtils.showToast("保存成功")
        } else {
            // 方案1 采用uri存储
            // 方案2       采用文件读写

            //Android 10  一下 需要文件读写权限
            FileUtils.saveBitmap(bitmap, path)
            // 发送至相册
            MediaStore.Images.Media.insertImage(CoreConfig.applicationContext.contentResolver, path, File(path).name, null)
            CoreConfig.applicationContext.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(path).parentFile)))

        }
    }

    /**
     *  保存图片至 外部存储 指定目录上
     *   Q 10 只能 在固定的  目录上
     * @param bitmap Bitmap
     * @param path String
     */
    fun saveBitmap2ExternalPrivate(bitmap: Bitmap, path: String) {
        if (FileUtils.isQ()) {
            // 直接保存到相册  没必要在保存到私有目录下 多生成一张
            return saveBitmap2Pictures(bitmap, path)
        } else {
            FileUtils.saveBitmap(bitmap, path)
            ToastUtils.showToast("保存成功")
            // 发送至相册
            MediaStore.Images.Media.insertImage(CoreConfig.applicationContext.contentResolver, path, File(path).name, null)
            CoreConfig.applicationContext.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(path).parentFile)))

        }
    }

}