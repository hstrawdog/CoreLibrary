package com.hqq.core.utils.file

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.hqq.core.CoreConfig
import com.hqq.core.permission.PermissionsResult
import com.hqq.core.permission.PermissionsUtils
import com.hqq.core.utils.ToastUtils
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
    fun setIsSave2Album(isSave: Boolean) = apply { this.isSave2Album = isSave }

    var saveModel = 1
    fun setSaveModel(model: Int) = apply { this.saveModel = model }

    /**
     * 文件路径
     */
    var filePath: String = ""
        get() {
            return FileUtils.getExternalPicturesPath() + "/" + fileName
        }

    /**
     *  保存图片 区分三种存储
     */
    fun save() {
        when (saveModel) {
            1 -> {
                save2Public()
            }
            2 -> {
                save2AppCache()
            }
            3 -> {
                save2Private()
            }
        }
    }

    /**
     *  默认保存在picture 目录下
     */
    fun save2Public() {
        if (bitmap != null && context != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                FileUtils.saveBitmap2Picture(context!!, bitmap!!, fileName = fileName)
                ToastUtils.showToast("保存成功")
            } else {
                //Android 10  一下 需要文件读写权限
                PermissionsUtils.requestStorage(object : PermissionsResult {
                    override fun onPermissionsResult(status: Boolean) {
                        FileUtils.saveBitmap(bitmap, filePath)
                        if (isSave2Album) {
                            // 发送广播 通知相册
                            MediaStore.Images.Media.insertImage(context!!.contentResolver, filePath, fileName, null)
                            context!!.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(filePath))))
                        }
                        ToastUtils.showToast("保存成功")
                    }
                })
            }
        }

    }

    /**
     *   保存图片到App cache
     */
    fun save2AppCache() {
        var path = FileUtils.getCacheDir(context!!) + "/" + fileName
        FileUtils.saveBitmap(bitmap, path)
        if (isSave2Album) {
            // 发送广播 通知相册
            MediaStore.Images.Media.insertImage(context!!.contentResolver, path, fileName, null)
            ToastUtils.showToast("保存成功")
        }
        context!!.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, FileUtils.getFile2Uri(path)))
    }

    /**
     *  保存在外部私有存储下
     *
     */
    fun save2Private() {
        val path = FileUtils.getExternalFilesDir(context!!) + "/" + fileName
        FileUtils.saveBitmap(bitmap, path)

        if (isSave2Album) {
            // 发送广播 通知相册
            MediaStore.Images.Media.insertImage(context!!.contentResolver, path, fileName, null)
            context!!.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(filePath))))
        }
        ToastUtils.showToast("保存成功")
    }
}
