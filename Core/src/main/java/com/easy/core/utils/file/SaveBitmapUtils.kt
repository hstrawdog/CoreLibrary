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
 */
object SaveBitmapUtils {


    /**
     * ✅ 一、内部存储（私有）
     * 应用卸载后，文件会被删除，用户不可直接访问
     *
     * context.filesDir
     * 路径示例：/data/user/0/com.example.app/files/
     * 适用于：私有数据保存，例如缓存的小图片等。
     *
     * context.cacheDir
     * 路径示例：/data/user/0/com.example.app/cache/
     * 适用于：临时图片缓存，系统可能自动清理。
     *
     * context.getDir("images", MODE_PRIVATE)
     * 路径示例：/data/user/0/com.example.app/app_images/
     * 你可以创建自己的命名子目录。
     * @param bitmap Bitmap
     * @param path String  FileUtils.getCacheDir()
     * 如果需要通知相册  请直接保存至相册目录下
     */
    @Deprecated(" 请改用 {@link #saveBitmap2AppCache(Bitmap?, String, String, CompressFormat)}")
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
     *
     * @param bitmap Bitmap?
     * @param relativePath String
     * @param fileName String
     * @param compressFormat CompressFormat
     * @return String
     */
    @JvmStatic
    fun saveBitmap2AppCache(bitmap:Bitmap?, relativePath:String = "", fileName:String = "", compressFormat:CompressFormat = CompressFormat.PNG):String {
        var fullName = fileName
        // fileName 如果不是图片后缀结尾补充 图片后缀
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

    /**
     *✅ 二、外部存储（私有）
     * 应用卸载后被删除，用户不可见，适用于大型图片或缓存等
     *
     * context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
     * 路径示例：/storage/emulated/0/Android/data/com.example.app/files/Pictures/
     * 适用于：保存不需要用户直接查看的图片，如上传前的临时文件。
     *
     * context.externalCacheDir
     * 路径示例：/storage/emulated/0/Android/data/com.example.app/cache/
     * 适用于：图片缓存，空间不足时可能被清理。
     * @param relativePath String
     * @param fileName String
     *  保存至公有目录上  Q 以上有指定目录 需要 通过  MediaStore 进行保存
     *  Q 一下key直接使用文件读写进行保存
     */
    @JvmStatic
    @Deprecated(message = "请改用 saveBitmap2Pictures()")
    fun saveBitmap2Pictures(bitmap:Bitmap?, relativePath:String = "", fileName:String = getDefFileName(".png")):Uri? {
        if (bitmap == null) {
            return null
        }
        return FileUtils.saveBitmap2Pictures(relativePath, fileName, bitmap)
    }

    /**
     *  同上
     * @param bitmap Bitmap?
     * @param relativePath String
     * @param fileName String
     * @param compressFormat CompressFormat
     * @return Uri?
     */

    fun saveBitmap2Pictures(bitmap:Bitmap?, relativePath:String = "", fileName:String = "", compressFormat:CompressFormat = CompressFormat.PNG):Uri? {
        if (bitmap == null) {
            return null
        }
        var fullName = fileName
        // fileName 如果不是图片后缀结尾补充 图片后缀
        if (fileName.isNullOrEmpty()) {
            fullName = getDefFileName(defFileName, compressFormat)
        } else if (!FileUtils.hasImageSuffix(fileName)) {
            fullName = getDefFileName(fileName, compressFormat)
        }
        return FileUtils.saveBitmap2Pictures(relativePath, fullName, bitmap, compressFormat)
    }

    /**
     *✅ 三、外部存储（公共，可见）
     * Android 10（API 29）之后需要使用 MediaStore 和 ContentResolver 保存，应用卸载后不会删除
     *
     * 传统路径（Android 10 以下）：
     *
     * kotlin
     * 复制
     * 编辑
     * Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
     * 路径示例：/storage/emulated/0/Pictures/
     * 注意：Android 10+ 后此方法被弃用，需使用 MediaStore。
     *
     * 使用 MediaStore 保存到系统图库
     * Android 10+ 推荐方式，图片将出现在系统“相册/图库”中。
     * @param bitmap Bitmap
     * @param path String
     */
    @JvmStatic
    fun saveBitmap2ExternalPrivate(bitmap:Bitmap?, relativePath:String = "", fileName:String = getDefFileName(".png")):String {
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



    fun saveBitmap2ExternalPrivate(bitmap:Bitmap?, relativePath:String = "", fileName:String = "", compressFormat:CompressFormat = CompressFormat.PNG):String {
        if (bitmap == null) {
            return ""
        }

        var fullName = fileName
        // fileName 如果不是图片后缀结尾补充 图片后缀
        if (fileName.isNullOrEmpty()) {
            fullName = getDefFileName(defFileName, compressFormat)
        } else if (!FileUtils.hasImageSuffix(fileName)) {
            fullName = getDefFileName(fileName, compressFormat)
        }



        if (isQ()) {

            FileUtils.saveBitmap(bitmap, fullName)
        } else {
            FileUtils.saveBitmap(bitmap, fullName)
            // 发送至相册
            MediaStore.Images.Media.insertImage(CoreConfig.applicationContext.contentResolver, fullName, File(fullName).name, null)
            CoreConfig.applicationContext.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(fullName).parentFile)))
        }
        return fullName

    }

}