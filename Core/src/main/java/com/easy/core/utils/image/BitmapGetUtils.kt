package com.easy.core.utils.image

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.easy.core.CoreConfig
import com.easy.core.utils.data.DataUtils
import com.easy.core.utils.file.FileUtils
import com.easy.core.utils.log.LogUtils
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.utils.image
 * @Date : 02:14
 * @Email : qiqiang213@gmail.com
 * @Describe : 获取 bitmap
 */
object BitmapGetUtils {
    /**
     * 获取bitmap
     *
     * @param file 文件
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(file: File?): Bitmap? {
        if (file == null) {
            return null
        }
        var `is`: InputStream? = null
        return try {
            `is` = BufferedInputStream(FileInputStream(file))
            BitmapFactory.decodeStream(`is`)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        } finally {
            FileUtils.closeIO(`is`)
        }
    }

    /**
     * 获取bitmap
     *
     * @param file      文件
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(file: File?, maxWidth: Int, maxHeight: Int): Bitmap? {
        if (file == null) {
            return null
        }
        var `is`: InputStream? = null
        return try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            `is` = BufferedInputStream(FileInputStream(file))
            BitmapFactory.decodeStream(`is`, null, options)
            options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight)
            options.inJustDecodeBounds = false
            BitmapFactory.decodeStream(`is`, null, options)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        } finally {
            FileUtils.closeIO(`is`)
        }
    }

    /**
     * 获取bitmap
     *
     * @param filePath 文件路径
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(filePath: String?): Bitmap? {
        return if (DataUtils.isNullString(filePath)) {
            null
        } else BitmapFactory.decodeFile(filePath)
    }

    /**
     * 获取bitmap
     *
     * @param filePath  文件路径
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(filePath: String?, maxWidth: Int, maxHeight: Int): Bitmap? {
        if (DataUtils.isNullString(filePath)) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(filePath, options)
    }

    /**
     * 获取bitmap
     *
     * @param is        输入流
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(`is`: InputStream?, maxWidth: Int, maxHeight: Int): Bitmap? {
        if (`is` == null) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(`is`, null, options)
        options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeStream(`is`, null, options)
    }

    /**
     * 获取bitmap
     *
     * @param data      数据
     * @param offset    偏移量
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(data: ByteArray, offset: Int, maxWidth: Int, maxHeight: Int): Bitmap? {
        if (data.size == 0) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(data, offset, data.size, options)
        options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeByteArray(data, offset, data.size, options)
    }

    /**
     * 获取bitmap
     *
     * @param resId 资源id
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(resId: Int): Bitmap? {
        val `is` = CoreConfig.applicationContext.resources.openRawResource(resId)
        return BitmapFactory.decodeStream(`is`)
    }

    /**
     * 获取bitmap
     *
     * @param resId     资源id
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(resId: Int, maxWidth: Int, maxHeight: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        val `is` = CoreConfig.applicationContext.resources.openRawResource(resId)
        BitmapFactory.decodeStream(`is`, null, options)
        options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeStream(`is`, null, options)
    }

    /**
     * 获取bitmap
     *
     * @param res 资源对象
     * @param id  资源id
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(res: Resources?, id: Int): Bitmap? {
        return if (res == null) {
            null
        } else BitmapFactory.decodeResource(res, id)
    }

    /**
     * 获取bitmap
     *
     * @param res       资源对象
     * @param id        资源id
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(res: Resources?, id: Int, maxWidth: Int, maxHeight: Int): Bitmap? {
        if (res == null) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, id, options)
        options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, id, options)
    }

    /**
     * 获取bitmap
     *
     * @param fd 文件描述
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(fd: FileDescriptor?): Bitmap? {
        return if (fd == null) {
            null
        } else BitmapFactory.decodeFileDescriptor(fd)
    }

    /**
     * 获取bitmap
     *
     * @param fd        文件描述
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(fd: FileDescriptor?, maxWidth: Int, maxHeight: Int): Bitmap? {
        if (fd == null) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFileDescriptor(fd, null, options)
        options.inSampleSize = BitmapUtils.calculateInSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFileDescriptor(fd, null, options)
    }
    /**
     * 获取bitmap
     *
     * @param is 输入流
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(`is`: InputStream?): Bitmap? {
        return if (`is` == null) {
            null
        } else BitmapFactory.decodeStream(`is`)
    }

    /**
     * 获取bitmap
     *
     * @param data   数据
     * @param offset 偏移量
     * @return bitmap
     */
    @JvmStatic
    fun getBitmap(data: ByteArray, offset: Int): Bitmap? {
        return if (data.isEmpty()) {
            null
        } else BitmapFactory.decodeByteArray(data, offset, data.size)
    }

    /**
     * 从网络上加载Bitmap
     *
     * @param imgUrl
     * @return
     */
    @JvmStatic
    fun getBitmapFromNet(imgUrl: String?): Bitmap? {
        var inputStream: InputStream? = null
        var outputStream: ByteArrayOutputStream? = null
        var url: URL? = null
        try {
            url = URL(imgUrl)
            val httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.readTimeout = 2000
            httpURLConnection.connect()
            if (httpURLConnection.responseCode == 200) {
                //网络连接成功
                inputStream = httpURLConnection.inputStream
                outputStream = ByteArrayOutputStream()
                val buffer = ByteArray(1024 * 8)
                var len = -1
                while (inputStream.read(buffer)
                        .also { len = it } != -1
                ) {
                    outputStream.write(buffer, 0, len)
                }
                val bu = outputStream.toByteArray()
                return BitmapFactory.decodeByteArray(bu, 0, bu.size)
            } else {
                LogUtils.d("网络连接失败----" + httpURLConnection.responseCode)
            }
        } catch (e: Exception) {

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: IOException) {

                    e.printStackTrace()
                }
            }
        }
        return null
    }

}
