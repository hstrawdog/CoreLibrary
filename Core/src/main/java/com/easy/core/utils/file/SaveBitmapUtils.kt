package com.easy.core.utils.file

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
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
 * 通用位图（Bitmap）保存工具类。
 *
 * ✅ 功能概览：
 * 1️⃣ 内部存储（App 私有目录，卸载后自动清除）
 * 2️⃣ 外部存储（App 外部私有目录，不出现在相册）
 * 3️⃣ 外部公共存储（用户可见，可出现在系统相册）
 *
 * 支持：
 * - 自动补全图片后缀名
 * - 指定压缩格式（PNG / JPEG / WEBP）
 * - Android Q 及以上版本的兼容适配
 * - 可选是否自动通知相册刷新
 */
object SaveBitmapUtils {


    // ---------------------------------------------------------
    // ✅ 一、内部存储（App 私有目录）
    // ---------------------------------------------------------
    /**
     * 将 Bitmap 保存到应用内部缓存目录（不推荐，已废弃）
     *
     * 目录示例：
     * - filesDir: /data/user/0/com.example.app/files/
     * - cacheDir: /data/user/0/com.example.app/cache/
     *
     * 应用卸载后文件会被删除，用户无法直接访问。
     * 若希望图片出现在相册，请使用外部存储相关方法。
     */
    @Deprecated("请改用 saveBitmap2AppCache(Bitmap?, String, String, CompressFormat)")
    @JvmStatic
    fun saveBitmap2AppCache(bitmap:Bitmap?, relativePath:String = "", fileName:String = getDefFileName(".png")):String {
        val path = if (relativePath.isNotNull()) {
            FilePathTools.getCacheDir() + File.separator + relativePath + File.separator + fileName
        } else {
            FilePathTools.getCacheDir() + File.separator + fileName
        }
        try {
            FileUtils.saveBitmap(bitmap, path)
        } catch (e:Exception) {
            e.printStackTrace()
            return ""
        }
        return path
    }

    /**
     * 保存图片至内部缓存目录
     *
     * @param bitmap 要保存的 Bitmap 对象
     * @param relativePath 相对路径，如 "images/temp"
     * @param fileName 文件名（若无则自动生成）
     * @param compressFormat 图片压缩格式（默认 PNG）
     * @return 返回完整保存路径
     */

    @JvmStatic
    fun saveBitmap2AppCache(bitmap:Bitmap?, relativePath:String = "", fileName:String = "", compressFormat:CompressFormat = CompressFormat.PNG):String {
        var fullName = fileName
        // 自动补后缀
        if (fileName.isNullOrEmpty()) {
            fullName = getDefFileName(defFileName, compressFormat)
        } else if (!FileUtils.hasImageSuffix(fileName)) {
            fullName = getDefFileName(fileName, compressFormat)
        }

        val path = if (relativePath.isNotNull()) {
            FilePathTools.getCacheDir() + File.separator + relativePath + File.separator + fullName
        } else {
            FilePathTools.getCacheDir() + File.separator + fullName
        }

        FileUtils.saveBitmap(bitmap, path)
        return path
    }


    // ---------------------------------------------------------
    // ✅ 二、外部存储（App 外部私有目录）
    // ---------------------------------------------------------
    /**
     * 将 Bitmap 保存至外部私有目录（已废弃版本）
     *
     * 示例路径：
     * /storage/emulated/0/Android/data/<package>/files/Pictures/
     *
     * 注意：
     * - 应用卸载后文件会被删除；
     * - 用户不可直接在相册中查看；
     * - Android Q 以下版本仍使用传统文件路径。
     */
    @Deprecated(message = "请改用 saveBitmap2Pictures()")
    fun saveBitmap2Pictures(bitmap:Bitmap?, relativePath:String = "", fileName:String = getDefFileName(".png")):Uri? {
        if (bitmap == null) return null
        return FileUtils.saveBitmap2Pictures(relativePath, fileName, bitmap)
    }

    /**
     * 将 Bitmap 保存至外部私有目录（推荐写法）
     *
     * @param bitmap 要保存的位图
     * @param relativePath 相对路径（如 "temp/img"）
     * @param fileName 文件名（可选，自动补后缀）
     * @param compressFormat 图片压缩格式
     * @return 保存成功返回 Uri，否则 null
     */
    fun saveBitmap2Pictures(bitmap:Bitmap?, relativePath:String = "", fileName:String = "", compressFormat:CompressFormat = CompressFormat.PNG):Uri? {
        if (bitmap == null) return null

        var fullName = fileName
        if (fileName.isNullOrEmpty()) {
            fullName = getDefFileName(defFileName, compressFormat)
        } else if (!FileUtils.hasImageSuffix(fileName)) {
            fullName = getDefFileName(fileName, compressFormat)
        }
        return FileUtils.saveBitmap2Pictures(relativePath, fullName, bitmap, compressFormat)
    }


    // ---------------------------------------------------------
    // ✅ 三、外部公共存储（用户可见，会出现在相册）
    // ---------------------------------------------------------
    /**
     * 保存 Bitmap 到外部公共目录。
     *
     * Android 10（Q）以上使用 MediaStore；
     * Android 9 及以下直接使用传统文件路径。
     *
     * @param bitmap 要保存的位图
     * @param relativePath 图片子目录（可选）
     * @param fileName 文件名
     * @return 返回最终路径字符串
     */

    @JvmStatic
    fun saveBitmap2ExternalPrivate(bitmap:Bitmap?, relativePath:String = "", fileName:String = getDefFileName(".png")):String {
        if (bitmap == null) return ""

        if (isQ()) {
            // Android Q 及以上：保存到应用外部私有目录
            val path = if (relativePath.isNotNull()) {
                FilePathTools.getExternalFilesDir() + File.separator + relativePath + File.separator + fileName
            } else {
                FilePathTools.getExternalFilesDir() + File.separator + fileName
            }
            FileUtils.saveBitmap(bitmap, path)
            return path
        } else {
            // Android 9 及以下：保存并手动通知系统相册刷新
            val path = if (relativePath.isNotNull()) {
                FilePathTools.getStorageDirectory() + File.separator + relativePath + File.separator + fileName
            } else {
                FilePathTools.getStorageDirectory() + File.separator + fileName
            }
            FileUtils.saveBitmap(bitmap, path)
            MediaStore.Images.Media.insertImage(CoreConfig.applicationContext.contentResolver, path, File(path).name, null)
            CoreConfig.applicationContext.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(path).parentFile)))
            return path
        }
    }

    /**
     * 保存 Bitmap 到外部公共目录（完整参数版本）
     *
     * @param bitmap 要保存的 Bitmap
     * @param relativePath 图片相对路径（例如 "Camera" 或 "MyApp"）
     * @param fileName 文件名（自动补后缀）
     * @param compressFormat 图片格式（默认 PNG）
     * @return 返回最终保存路径字符串；失败返回 ""
     *
     * 逻辑说明：
     * - Android Q 及以上版本：使用私有外部目录（不会出现在相册）
     * - Android Q 以下版本：保存后会通过广播通知系统刷新媒体库，使图片出现在系统相册中
     */
    fun saveBitmap2ExternalPrivate(bitmap:Bitmap?, relativePath:String = "", fileName:String = "", compressFormat:CompressFormat = CompressFormat.PNG):String {
        if (bitmap == null) return ""

        // 处理文件名（补后缀）
        var fullName = fileName
        if (fileName.isNullOrEmpty()) {
            fullName = getDefFileName(defFileName, compressFormat)
        } else if (!FileUtils.hasImageSuffix(fileName)) {
            fullName = getDefFileName(fileName, compressFormat)
        }

        // 拼接完整路径
        val allPath = if (relativePath.isNullOrEmpty()) {
            fullName
        } else {
            relativePath + File.separator + fullName
        }

        // Android Q 及以上：仅保存文件
        if (isQ()) {
            FileUtils.saveBitmap(bitmap, allPath)
        } else {
            // Android Q 以下：保存并更新相册
            FileUtils.saveBitmap(bitmap, allPath)
            MediaStore.Images.Media.insertImage(CoreConfig.applicationContext.contentResolver, allPath, File(allPath).name, null)
            CoreConfig.applicationContext.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(allPath).parentFile)))
        }
        return allPath
    }
}
