package com.hqq.core.utils

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import java.io.*
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   BitmapUtils
 * @Date : 2018/6/25 0025  上午 11:35
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
object BitmapUtils {

    fun generatBitmap(v: View): Bitmap {
        val bitmap = Bitmap.createBitmap(v.width, v.height,
                Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        v.draw(canvas)
        return bitmap
    }

    fun saveImageToGallery(context: Context, bitmaps: Bitmap) {
        // 首先保存图片
        val appDir = File(Environment.getExternalStorageDirectory().absolutePath + File.separator + "dearxy")
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        Log.e("test_sign", "图片全路径localFile = " + appDir.absolutePath + fileName)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            bitmaps.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            ToastUtils.showToast(context, "保存到相册失败！")
            e.printStackTrace()
        } catch (e: IOException) {
            ToastUtils.showToast(context, "保存到相册失败！")
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                    //回收
                    bitmaps.recycle()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.contentResolver,
                    file.absolutePath, fileName, null)
        } catch (e: FileNotFoundException) {
            ToastUtils.showToast(context, "保存到相册失败！")
            e.printStackTrace()
        }
        ToastUtils.showToast(context, "已保存到手机相册！")
        // 最后通知图库更新
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(File(appDir.path))))
    }

    /**
     * 等比缩放图片
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    fun zoomImg(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
        // 获得图片的宽高
        val width = bm.width
        val height = bm.height
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
    }

    /**
     * @param bm
     * @param newWidth
     * @return
     */
    fun zoomImg(bm: Bitmap, newWidth: Int): Bitmap? {
        // 获得图片的宽高
        val width = bm.width
        val height = bm.height
        // 计算缩放比例
        val scaleWidth = (newWidth.toFloat() - 5) / width
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleWidth)
        // 得到新的图片
        val newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
        bm.recycle()
        return bitmapCombine(newbm, 5, 5, Color.TRANSPARENT)
    }

    /**
     * 获得添加边框了的Bitmap
     *
     * @param bm     原始图片Bitmap
     * @param smallW 一条边框宽度
     * @param smallH 一条边框高度
     * @param color  边框颜色值
     * @return Bitmap 添加边框了的Bitmap
     */
    private fun bitmapCombine(bm: Bitmap?, smallW: Int, smallH: Int, color: Int): Bitmap? {
        //防止空指针异常
        if (bm == null) {
            return null
        }
        // 原图片的宽高
        val bigW = bm.width
        val bigH = bm.height
        // 重新定义大小
        val newW = bigW + smallW * 2
        val newH = bigH + smallH * 2

        // 绘原图
        val newBitmap = Bitmap.createBitmap(newW, newH, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        val p = Paint()
        p.color = color
        canvas.drawRect(Rect(0, 0, newW, newH), p)

        // 绘边框
        canvas.drawBitmap(bm, (newW - bigW - 2 * smallW) / 2 + smallW.toFloat(), (newH - bigH - 2 * smallH)
                / 2 + smallH.toFloat(), null)


//        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.save()
        canvas.restore()
        bm.recycle()
        return newBitmap
    }

    fun zoomDrawable(drawable: Drawable, w: Int, h: Int): Bitmap {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val oldbmp = drawableToBitmap(drawable)
        val matrix = Matrix()
        val scaleWidth = w.toFloat() / width
        val scaleHeight = h.toFloat() / height
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true)
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(width, height, config)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return bitmap
    }


    @Synchronized
    fun getBase64(path: String?): String {
        var bitmap: Bitmap? = null
        //字节数组输出流
        val baos: ByteArrayOutputStream? = null
        val fs: FileInputStream? = null
        var base64 = ""
        try {
            val byteArray = ScalePicture(path, 30).toByteArray()
            // , UTF8
            base64 = String(Base64.encode(byteArray, Base64.DEFAULT))
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                baos?.close()
                fs?.close()
                bitmap = null
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return base64
    }

    fun ScalePicture(fileName: String?, options: Int): ByteArrayOutputStream {
        var options = options
        var image: Bitmap? = null
        val baos = ByteArrayOutputStream()
        try {
            val fs = FileInputStream(fileName)
            val bs = BufferedInputStream(fs)
            image = BitmapFactory.decodeStream(bs)
            if (fs.available() / 1024 <= 512 && fs.available() != 0) {
                // 如果文件小于 521k  不压缩
                options = 100
            }
            image.compress(Bitmap.CompressFormat.JPEG, options, baos)
            //这里压缩options%，把压缩
            baos.writeTo(FileOutputStream(fileName))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return baos
    }

    fun getImageHead(key: String?): String? {
        val stringMap: MutableMap<String, String> = HashMap()
        stringMap["JPEG"] = "data:image/jpeg;base64,"
        stringMap["JPG"] = "data:image/jpg;base64,"
        stringMap["GIF"] = "data:image/gif;base64,"
        stringMap["PNG"] = "data:image/png;base64,"
        return stringMap[key]
    }
}