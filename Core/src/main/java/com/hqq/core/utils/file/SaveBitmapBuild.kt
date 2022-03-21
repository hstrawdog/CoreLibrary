package com.hqq.core.utils.file

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.hqq.core.CoreConfig
import com.hqq.core.kt.getFileName
import com.hqq.core.permission.PermissionsResult
import com.hqq.core.permission.PermissionsUtils
import com.hqq.core.utils.ToastUtils
import com.hqq.core.utils.log.LogUtils
import java.io.File

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils.file
 * @Date : 下午 1:45
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
/**
 *  保存图片 辅助类
 *  Android 文件 存储 区分10以上 contentProvider存储 与Android10 一下File 操作
 * @property bitmap Bitmap
 * @property context Context?
 * @property fileName String
 * @constructor
 */
class SaveBitmapBuild(var bitmap: Bitmap?) {
    /**
     *  默认读取当前显示的界面
     */
    var context: Context? = CoreConfig.get().currActivity

    /**
     *  指定文件名称
     */
    var fileName: String = ""
        get() {
            return if (field.isEmpty()) {
                FileUtils.getDefFileName(".png")
            } else {
                field
            }
        }

    fun setFileName(fileName: String) = apply { this.fileName = fileName }

    /**
     * 是否保存到相册中
     */
    var isSave2Album = true

    /**
     * 文件路径
     * 默认使用cache  可以直接操作File  避免部分错误
     */
    var filePath: String = ""

    /**
     *
     * @param type Int 0  私有目录
     * @return String
     */
    private fun getFilePath(type: Int): String {
        var path = filePath
        when (type) {
            // 内部私有目录 可以直接用
            0 -> {
                // 空 ||  不不包含  cacehe
                if (path.isNullOrEmpty() || (!path.contains(FileUtils.getCacheDir()))) {
                    LogUtils.d("getFilePath        $path ")
                    path = FileUtils.getCacheDir() + File.separator + fileName
                }
            }
            // 外部私有存储  可以直接用File
            1 -> {
                // 空 ||  不不包含  cacehe
                if (path.isNullOrEmpty() || (!path.contains(FileUtils.getExternalFilesDir()))) {
                    LogUtils.d("getFilePath        $path ")
                    path = FileUtils.getExternalFilesDir(fileName = Environment.DIRECTORY_DOWNLOADS) + File.separator + fileName
                }
            }
            // 外部公共存储 需要用Uri
            2 -> {
                if (path.isNullOrEmpty()) {
                    //  9以上 需要用uri 进行存储
                    path = FileUtils.getExternalPicturesPath() + File.separator + fileName
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    } else {
                        //  直接访问 pictures 文件目录
                    }
                }
            }
        }

        return path
    }


    /**
     *   保存图片到App cache  也就是内部私有
     */
    fun save2AppCache(): String {
        var path = getFilePath(0)
        FileUtils.saveBitmap(bitmap, path)
        send2Album(path)
        ToastUtils.showToast("保存成功")
        return path
    }

    /**
     *  保存在外部私有存储下
     *  图片 保存后  在发送至相册 会出现两张图片
     *  原有的目录会有一张  相册 Pictures 也会出现一样  互不影响
     */
    fun save2ExternalPrivate(): String {
        var path = getFilePath(1)
        FileUtils.saveBitmap(bitmap, path)
        send2Album(path)
        ToastUtils.showToast("保存成功")
        return path
    }

    /**
     *  默认保存在picture 目录下
     *  android:requestLegacyExternalStorage="true"  才能访问 外部存储空间
     *  否则都是在Pictures目录下
     */
    fun save2Pictures() {
        if (bitmap != null && context != null) {
            val path = getFilePath(2)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                FileUtils.saveBitmap2Public(context!!, bitmap!!, path)
                ToastUtils.showToast("保存成功")
            } else {
                //Android 10  一下 需要文件读写权限
                PermissionsUtils.requestStorage(object : PermissionsResult {
                    override fun onPermissionsResult(status: Boolean) {
                        FileUtils.saveBitmap(bitmap, path)
                        send2Album(filePath)
                        ToastUtils.showToast("保存成功")
                    }
                })
            }
        }

    }

    /**
     * 将图片 通知至相册刷新
     * @param path String
     */
    private fun send2Album(path: String) {
        if (isSave2Album) {
            // 发送广播 通知相册
            MediaStore.Images.Media.insertImage(context!!.contentResolver, path, File(path).name, null)
            context!!.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(path))))
        }
    }
}
