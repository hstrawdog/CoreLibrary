package com.easy.album.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: cn.huangqiqiang.halbum.utils.AlbumFileUtils.java
 * @emain: 593979591@qq.com
 * @date: 2017-05-07 21:25
</描述当前版本功能> */
object AlbumFileUtils {
    const val POSTFIX = ".JPEG"
    const val POST_VIDEO = ".mp4"
    const val APP_NAME = "ImageSelector"
    fun createCameraFile(context: Context, type: Int): File? {
        return createMediaFile(context, context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.path, type)
    }

    private fun createMediaFile(context: Context, parentPath: String, type: Int): File? {
        val folderDir = File(parentPath)
        if (!folderDir.exists() && folderDir.mkdirs()) {
        }
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(Date())
        val fileName = APP_NAME + "_" + timeStamp + ""
        var tmpFile: File? = null
        when (type) {
            1 -> tmpFile = File(folderDir, fileName + POSTFIX)
            2 -> tmpFile = File(folderDir, fileName + POST_VIDEO)
            else -> {}
        }
        return tmpFile
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    fun readPictureDegree(path: String?): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path!!)
            val orientation =
                exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
                else -> {}
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return degree
    }

    /**
     * 获取图片uri
     *
     * @param context
     * @param path
     * @return
     */
    fun getImageContentUri(context: Context, path: String): Uri? {
        val cursor =
            context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Images.Media._ID), MediaStore.Images.Media.DATA + "=? ", arrayOf(path), null)
        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri = Uri.parse("content://media/external/images/media")
            Uri.withAppendedPath(baseUri, "" + id)
        } else {
            // 如果图片不在手机的共享图片数据库，就先把它插入。
            if (File(path).exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, path)
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                null
            }
        }
    }

    /**
     * @param context
     * @param url
     * @return
     */
    @JvmStatic
    fun getFile2Uri(context: Context, url: String?): Uri? {
        val tempFile = File(url)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //如果在Android7.0以上,使用FileProvider获取Uri
            try {
                return FileProvider.getUriForFile(context, context.packageName + ".provider", tempFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            return Uri.fromFile(tempFile)
        }
        return null
    }

    //旋转图片
    fun rotateBitmap(angle: Int, bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    /** 保存方法  */
    fun saveBitmap(path: String?, bm: Bitmap) {
        val f = File(path)
        if (f.exists()) {
            f.delete()
        }
        try {
            val out = FileOutputStream(f)
            bm.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.flush()
            out.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}