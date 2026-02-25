package com.easy.core.utils.image

import android.R
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import androidx.exifinterface.media.ExifInterface
import com.easy.core.CoreConfig
import com.easy.core.utils.ToastUtils
import com.easy.core.utils.file.FileUtils
import java.io.*
import java.net.URL
import java.util.Locale


/**
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   BitmapUtils
 * @Date : 2018/6/25 0025  上午 11:35
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
object BitmapUtils {

    @JvmStatic
    fun saveImageToGallery(context:Context, bitmaps:Bitmap) {
        // 首先保存图片
        val appDir = File(Environment.getExternalStorageDirectory().absolutePath + File.separator + "dearxy")
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        Log.e("test_sign", "图片全路径localFile = " + appDir.absolutePath + fileName)
        var fos:FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            bitmaps.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e:FileNotFoundException) {
            ToastUtils.showToast(context, "保存到相册失败！")
            e.printStackTrace()
        } catch (e:IOException) {
            ToastUtils.showToast(context, "保存到相册失败！")
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                    //回收
                    bitmaps.recycle()
                } catch (e:IOException) {
                    e.printStackTrace()
                }
            }
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, fileName, null)
        } catch (e:FileNotFoundException) {
            ToastUtils.showToast(context, "保存到相册失败！")
            e.printStackTrace()
        }
        ToastUtils.showToast(context, "已保存到手机相册！")
        // 最后通知图库更新
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(File(appDir.path))))
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
    @JvmStatic
    fun bitmapCombine(bm:Bitmap?, smallW:Int, smallH:Int, color:Int):Bitmap? {
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
        canvas.drawBitmap(bm, (newW - bigW - 2 * smallW) / 2 + smallW.toFloat(), (newH - bigH - 2 * smallH) / 2 + smallH.toFloat(), null)

//        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.save()
        canvas.restore()
        bm.recycle()
        return newBitmap
    }

    @JvmStatic
    fun drawableToBitmap(drawable:Drawable):Bitmap {
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
    fun getBase64(path:String?,format:Bitmap.CompressFormat = Bitmap.CompressFormat.PNG):String {
        var bitmap:Bitmap? = null
        //字节数组输出流
        val baos:ByteArrayOutputStream? = null
        val fs:FileInputStream? = null
        var base64 = ""
        try {
            bitmap = BitmapFactory.decodeFile(path, BitmapFactory.Options())
            var newBitmap = BitmapOperateUtils.compressByQuality(bitmap, 800)
            val byteArray = bitmap2Bytes(newBitmap!!, format)
            // , UTF8
            base64 = String(Base64.encode(byteArray, Base64.DEFAULT))
        } catch (e:IOException) {
            e.printStackTrace()
        } finally {
            try {
                baos?.close()
                fs?.close()
                bitmap = null
            } catch (e:IOException) {
                e.printStackTrace()
            }
        }
        return base64
    }

    @JvmStatic
    fun getImageHead(key:String?):String? {
        val stringMap:MutableMap<String, String> = HashMap()
        stringMap["JPEG"] = "data:image/jpeg;base64,"
        stringMap["JPG"] = "data:image/jpg;base64,"
        stringMap["GIF"] = "data:image/gif;base64,"
        stringMap["PNG"] = "data:image/png;base64,"
        return stringMap[key]
    }

    /**
     *  查询图片   Options
     * @param path String
     * @return BitmapFactory.Options
     */
    @JvmStatic
    fun getImageOptions(path:String):BitmapFactory.Options {
        val options:BitmapFactory.Options = BitmapFactory.Options()
        //设置为true,表示解析Bitmap对象，该对象不占内存
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        return options
    }

    /**
     * 把bitmap画到一个白底的newBitmap上,将newBitmap返回
     * @param bitmap 要绘制的位图
     * @return Bitmap
     */
    @JvmStatic
    fun drawableBitmapOnWhiteBg(bitmap:Bitmap):Bitmap {
        val newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)
        canvas.drawColor(CoreConfig.applicationContext.resources.getColor(R.color.white))
        val paint = Paint()
        canvas.drawBitmap(bitmap, 0f, 0f, paint) //将原图使用给定的画笔画到画布上
        return newBitmap
    }

    /**
     *  将bitmap 转换成 字节数组
     * @param bitmap Bitmap
     * @return ByteArray
     */
    @JvmStatic
    fun bitmap2ByteArray(bitmap:Bitmap, format:Bitmap.CompressFormat = Bitmap.CompressFormat.WEBP, quality:Int = 80):ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(format, quality, baos)
        return baos.toByteArray()
    }

    /**
     * 得到bitmap的大小
     */
    @JvmStatic
    fun getBitmapSize(bitmap:Bitmap):Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.allocationByteCount
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) { //API 12
            bitmap.byteCount
        } else bitmap.rowBytes * bitmap.height
        // 在低版本中用一行的字节x高度
        //earlier version
    }

    /**
     *  webp 格式
     * @param bitmap Bitmap
     * @return ByteArray
     */
    @JvmStatic
    fun bitmap2ByteArray4WeB(bitmap:Bitmap):ByteArray {
        return bitmap2ByteArray(bitmap, Bitmap.CompressFormat.WEBP)
    }

    /**
     *  绘制背景
     * @param color Int
     * @param orginBitmap Bitmap
     * @return Bitmap
     */
    @JvmStatic
    fun drawBg4Bitmap(color:Int, orginBitmap:Bitmap):Bitmap {
        val paint = Paint()
        paint.color = color
        val bitmap = Bitmap.createBitmap(orginBitmap.width, orginBitmap.height, orginBitmap.config)
        val canvas = Canvas(bitmap)
        canvas.drawRect(0f, 0f, orginBitmap.width.toFloat(), orginBitmap.height.toFloat(), paint)
        canvas.drawBitmap(orginBitmap, 0f, 0f, paint)
        return bitmap
    }

    /**
     * 镜像图片
     * @param srcBitmap
     * @return
     */
    @JvmStatic
    fun convertBitmap(srcBitmap:Bitmap):Bitmap {
        val width = srcBitmap.width
        val height = srcBitmap.height
        val newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) // 创建一个新的和SRC长度宽度一样的位图
        val cv = Canvas(newb)
        val m = Matrix()
        m.postScale(-1f, 1f) //镜像水平翻转
        val new2 = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, m, true)

        // 右边图片
        cv.drawBitmap(new2, Rect(0, 0, width, height), Rect(0, 0, width, height), null)
        return newb
    }

    /**
     * @param srcBitmap 原图
     * @param offsetX   0-100
     * @param offsetY   0-100
     * @return
     */
    @JvmStatic
    fun convertBitmap(srcBitmap:Bitmap, offsetX:Int, offsetY:Int):Bitmap {
        val width = srcBitmap.width
        val height = srcBitmap.height
        val newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) // 创建一个新的和SRC长度宽度一样的位图
        val cv = Canvas(newb)
        val m = Matrix()
        m.postScale(-1f, 1f) //镜像水平翻转
        val new2 = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, m, true)
        val xSize = width / 4
        val ySize = height / 4
        val OffsetY = (ySize * ((offsetY - 50.0) / 50.0)).toInt()
        val OffsetX = (xSize * ((offsetX - 50.0) / 50.0)).toInt()
//        LogUtils.e { " OffsetX :     $OffsetX       xSize:   $xSize  " }
//        LogUtils.e { " OffsetY :     $OffsetY       offsetY:   $offsetY  " }
        // 左边图片
        cv.drawBitmap(srcBitmap, Rect(xSize + OffsetX, 0, xSize + width / 2 + OffsetX, height), Rect(0, 0, width / 2, height), null)
        // 右边图片
        cv.drawBitmap(new2, Rect(xSize - OffsetX, 0, xSize + width / 2 - OffsetX, height), Rect(width / 2, OffsetY, width, height + OffsetY), null)
        return newb
    }

    /**
     * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
     *
     *
     * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;
     *
     *
     * B.本地路径:url="file://mnt/sdcard/photo/image.png";
     *
     *
     * C.支持的图片格式 ,png, jpg,bmp,gif等等
     *
     * @param url
     * @return
     */
    @JvmStatic
    fun getLocalOrNetBitmap(url:String?):Bitmap? {
        var bitmap:Bitmap? = null
        var `in`:InputStream? = null
        var out:BufferedOutputStream? = null
        return try {
            `in` = BufferedInputStream(URL(url).openStream(), 1024)
            val dataStream = ByteArrayOutputStream()
            out = BufferedOutputStream(dataStream, 1024)
            copy(`in`, out)
            out.flush()
            var data = dataStream.toByteArray()
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
            data = null
            bitmap
        } catch (e:IOException) {
            e.printStackTrace()
            null
        }
    }

    @Throws(IOException::class)
    private fun copy(`in`:InputStream, out:OutputStream) {
        val b = ByteArray(1024)
        var read:Int
        while (`in`.read(b).also { read = it } != -1) {
            out.write(b, 0, read)
        }
    }

    @JvmStatic
    fun getColorByInt(colorInt:Int):Int {
        return colorInt or -16777216
    }

    /**
     * 修改颜色透明度
     *
     * @param color
     * @param alpha
     * @return
     */
    @JvmStatic
    fun changeColorAlpha(color:Int, alpha:Int):Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

    @JvmStatic
    fun getAlphaPercent(argb:Int):Float {
        return Color.alpha(argb) / 255f
    }

    @JvmStatic
    fun alphaValueAsInt(alpha:Float):Int {
        return Math.round(alpha * 255)
    }

    @JvmStatic
    fun adjustAlpha(alpha:Float, color:Int):Int {
        return alphaValueAsInt(alpha) shl 24 or (0x00ffffff and color)
    }

    @JvmStatic
    fun colorAtLightness(color:Int, lightness:Float):Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] = lightness
        return Color.HSVToColor(hsv)
    }

    @JvmStatic
    fun lightnessOfColor(color:Int):Float {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        return hsv[2]
    }

    @JvmStatic
    fun getHexString(color:Int, showAlpha:Boolean):String {
        val base = if (showAlpha) -0x1 else 0xFFFFFF
        val format = if (showAlpha) "#%08X" else "#%06X"
        return String.format(format, base and color).toUpperCase(Locale.ROOT)
    }

    /**
     * bitmap转byteArr
     *
     * @param bitmap bitmap对象
     * @param format 格式
     * @return 字节数组
     */
    @JvmStatic
    fun bitmap2Bytes(bitmap:Bitmap, format:Bitmap.CompressFormat?):ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(format, 100, baos)
        return baos.toByteArray()
    }

    /**
     * byteArr转bitmap
     *
     * @param bytes 字节数组
     * @return bitmap对象
     */
    @JvmStatic
    fun bytes2Bitmap(bytes:ByteArray):Bitmap? {
        return if (bytes.size != 0) {
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } else {
            null
        }
    }

    /**
     * drawable转bitmap
     *
     * @param drawable drawable对象
     * @return bitmap对象
     */
    @JvmStatic
    fun drawable2Bitmap(drawable:Drawable):Bitmap {
        // 取 drawable 的长宽
        val w = drawable.intrinsicWidth
        val h = drawable.intrinsicHeight

        // 取 drawable 的颜色格式
        val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        // 建立对应 bitmap
        val bitmap = Bitmap.createBitmap(w, h, config)
        // 建立对应 bitmap 的画布
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, w, h)
        // 把 drawable 内容画到画布中
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * bitmap转drawable
     *
     * @param res    resources对象
     * @param bitmap bitmap对象
     * @return drawable对象
     */
    @JvmStatic
    fun bitmap2Drawable(res:Resources?, bitmap:Bitmap?):Drawable {
        return BitmapDrawable(res, bitmap)
    }

    @JvmStatic
    fun bitmap2Drawable(bitmap:Bitmap?):Drawable {
        return BitmapDrawable(bitmap)
    }

    /**
     * drawable转byteArr
     *
     * @param drawable drawable对象
     * @param format   格式
     * @return 字节数组
     */
    @JvmStatic
    fun drawable2Bytes(drawable:Drawable, format:Bitmap.CompressFormat?):ByteArray {
        val bitmap = drawable2Bitmap(drawable)
        return bitmap2Bytes(bitmap, format)
    }

    /**
     * byteArr转drawable
     *
     * @param res   resources对象
     * @param bytes 字节数组
     * @return drawable对象
     */
    @JvmStatic
    fun bytes2Drawable(res:Resources?, bytes:ByteArray):Drawable {
        val bitmap = bytes2Bitmap(bytes)
        return bitmap2Drawable(res, bitmap)
    }

    @JvmStatic
    fun bytes2Drawable(bytes:ByteArray):Drawable {
        val bitmap = bytes2Bitmap(bytes)
        return bitmap2Drawable(bitmap)
    }

    /**
     * 计算采样大小
     *
     * @param options   选项
     * @param maxWidth  最大宽度
     * @param maxHeight 最大高度
     * @return 采样大小
     */
    fun calculateInSampleSize(options:BitmapFactory.Options, maxWidth:Int, maxHeight:Int):Int {
        if (maxWidth == 0 || maxHeight == 0) {
            return 1
        }
        var height = options.outHeight
        var width = options.outWidth
        var inSampleSize = 1
        while (1.let { height = height shr it; height } >= maxHeight && 1.let {
                width = width shr it; width
            } >= maxWidth) {
            inSampleSize = inSampleSize shl 1
        }
        return inSampleSize
    }

    /**
     * 计算inSampleSize
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun calculateInSampleSize2(options:BitmapFactory.Options, reqWidth:Int, reqHeight:Int):Int {
        var reqWidth = reqWidth
        var reqHeight = reqHeight
        val height = options.outHeight
        val width = options.outWidth
        val MIN_WIDTH = 100
        var inSampleSize = 1
        return if (width < MIN_WIDTH) {
            inSampleSize
        } else {
            var heightRatio:Int
            if (width > height && reqWidth < reqHeight || width < height && reqWidth > reqHeight) {
                heightRatio = reqWidth
                reqWidth = reqHeight
                reqHeight = heightRatio
            }
            if (height > reqHeight || width > reqWidth) {
                heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
                val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
                inSampleSize = Math.max(heightRatio, widthRatio)
            }
            inSampleSize
        }
    }

    /**
     * 获取图片旋转角度
     *
     * @param filePath 文件路径
     * @return 旋转角度
     */
    @JvmStatic
    fun getRotateDegree(filePath:String):Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(filePath)
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            degree = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 90
            }
        } catch (e:IOException) {
            e.printStackTrace()
        }
        return degree
    }

    /**
     * 转为圆形图片
     *
     * @param src 源图片
     * @return 圆形图片
     */
    @JvmOverloads
    @JvmStatic
    fun toRound(src:Bitmap, recycle:Boolean = false):Bitmap? {
        if (isEmptyBitmap(src)) {
            return null
        }
        val width = src.width
        val height = src.height
        val radius = Math.min(width, height) shr 1
        val ret = src.copy(src.config, true)
        val paint = Paint()
        val canvas = Canvas(ret)
        val rect = Rect(0, 0, width, height)
        paint.isAntiAlias = true
        paint.color = Color.TRANSPARENT
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawCircle((width shr 1.toFloat().toInt()).toFloat(), (height shr 1.toFloat().toInt()).toFloat(), radius.toFloat(), paint)
        canvas.drawBitmap(src, rect, rect, paint)
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    /**
     * 转为圆角图片
     *
     * @param src    源图片
     * @param radius 圆角的度数
     * @return 圆角图片
     */
    @JvmOverloads
    @JvmStatic
    fun toRoundCorner(src:Bitmap?, radius:Float, recycle:Boolean = false):Bitmap? {
        if (null == src) {
            return null
        }
        val width = src.width
        val height = src.height
        val ret = src.copy(src.config, true)
        val bitmapShader = BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val paint = Paint()
        val canvas = Canvas(ret)
        val rectf = RectF(0f, 0f, width.toFloat(), height.toFloat())
        paint.isAntiAlias = true
        paint.shader = bitmapShader
        canvas.drawRoundRect(rectf, radius, radius, paint)
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    /**
     * 添加颜色边框
     *
     * @param src         源图片
     * @param borderWidth 边框宽度
     * @param color       边框的颜色值
     * @return 带颜色边框图
     */
    @JvmStatic
    fun addFrame(src:Bitmap?, borderWidth:Int, color:Int):Bitmap {
        return addFrame(src, borderWidth, color)
    }

    /**
     * 添加颜色边框
     *
     * @param src         源图片
     * @param borderWidth 边框宽度
     * @param color       边框的颜色值
     * @param recycle     是否回收
     * @return 带颜色边框图
     */
    @JvmStatic
    fun addFrame(src:Bitmap, borderWidth:Int, color:Int, recycle:Boolean):Bitmap? {
        if (isEmptyBitmap(src)) {
            return null
        }
        val newWidth = src.width + borderWidth shr 1
        val newHeight = src.height + borderWidth shr 1
        val ret = Bitmap.createBitmap(newWidth, newHeight, src.config)
        val canvas = Canvas(ret)
        val rec = canvas.clipBounds
        val paint = Paint()
        paint.color = color
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth.toFloat()
        canvas.drawRect(rec, paint)
        canvas.drawBitmap(src, borderWidth.toFloat(), borderWidth.toFloat(), null)
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    /**
     * 添加倒影
     *
     * @param src              源图片的
     * @param reflectionHeight 倒影高度
     * @return 带倒影图片
     */
    @JvmOverloads
    @JvmStatic
    fun addReflection(src:Bitmap, reflectionHeight:Int, recycle:Boolean = false):Bitmap? {
        if (isEmptyBitmap(src)) {
            return null
        }
        val REFLECTION_GAP = 0
        val srcWidth = src.width
        val srcHeight = src.height
        if (0 == srcWidth || srcHeight == 0) {
            return null
        }
        val matrix = Matrix()
        matrix.preScale(1f, -1f)
        val reflectionBitmap = Bitmap.createBitmap(src, 0, srcHeight - reflectionHeight, srcWidth, reflectionHeight, matrix, false) ?: return null
        val ret = Bitmap.createBitmap(srcWidth, srcHeight + reflectionHeight, src.config)
        val canvas = Canvas(ret)
        canvas.drawBitmap(src, 0f, 0f, null)
        canvas.drawBitmap(reflectionBitmap, 0f, srcHeight + REFLECTION_GAP.toFloat(), null)
        val paint = Paint()
        paint.isAntiAlias = true
        val shader = LinearGradient(0f, srcHeight * 1f, 0f, ret.height + REFLECTION_GAP * 1f, 0x70FFFFFF, 0x00FFFFFF, Shader.TileMode.MIRROR)
        paint.shader = shader
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas.save()
        canvas.drawRect(0f, srcHeight.toFloat(), srcWidth.toFloat(), ret.height + REFLECTION_GAP.toFloat(), paint)
        canvas.restore()
        if (!reflectionBitmap.isRecycled) {
            reflectionBitmap.recycle()
        }
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    /**
     * 添加文字水印
     *
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param alpha    水印字体透明度
     * @param x        起始坐标x
     * @param y        起始坐标y
     * @return 带有文字水印的图片
     */
    @JvmOverloads
    @JvmStatic
    fun addTextWatermark(src:Bitmap, content:String?, textSize:Int, color:Int, alpha:Int, x:Float, y:Float, recycle:Boolean = false):Bitmap? {
        if (isEmptyBitmap(src) || content == null) {
            return null
        }
        val ret = src.copy(src.config, true)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val canvas = Canvas(ret)
        paint.alpha = alpha
        paint.color = color
        paint.textSize = textSize.toFloat()
        val bounds = Rect()
        paint.getTextBounds(content, 0, content.length, bounds)
        canvas.drawText(content, x, y, paint)
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    /**
     * 添加图片水印
     *
     * @param src       源图片
     * @param watermark 图片水印
     * @param x         起始坐标x
     * @param y         起始坐标y
     * @param alpha     透明度
     * @return 带有图片水印的图片
     */
    @JvmOverloads
    @JvmStatic
    fun addImageWatermark(src:Bitmap, watermark:Bitmap?, x:Int, y:Int, alpha:Int, recycle:Boolean = false):Bitmap? {
        if (isEmptyBitmap(src)) {
            return null
        }
        val ret = src.copy(src.config, true)
        if (!isEmptyBitmap(watermark)) {
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            val canvas = Canvas(ret)
            paint.alpha = alpha
            canvas.drawBitmap(watermark!!, x.toFloat(), y.toFloat(), paint)
        }
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    /**
     * 转为alpha位图
     *
     * @param src 源图片
     * @return alpha位图
     */
    @JvmStatic
    fun toAlpha(src:Bitmap?):Bitmap {
        return toAlpha(src)
    }

    /**
     * 转为alpha位图
     *
     * @param src     源图片
     * @param recycle 是否回收
     * @return alpha位图
     */
    @JvmStatic
    fun toAlpha(src:Bitmap, recycle:Boolean):Bitmap? {
        if (isEmptyBitmap(src)) {
            return null
        }
        val ret = src.extractAlpha()
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    /**
     * 可以对该图的非透明区域着色
     *
     *
     * 有多种使用场景，常见如 Button 的 pressed 状态，View 的阴影状态等
     *
     * @param iv
     * @param src
     * @param radius
     * @param color
     * @return
     */
    private fun getDropShadow(iv:ImageView, src:Bitmap, radius:Float, color:Int):Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
        val width = src.width
        val height = src.height
        val dest = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(dest)
        val alpha = src.extractAlpha()
        canvas.drawBitmap(alpha, 0f, 0f, paint)
        val filter = BlurMaskFilter(radius, BlurMaskFilter.Blur.OUTER)
        paint.maskFilter = filter
        canvas.drawBitmap(alpha, 0f, 0f, paint)
        iv.setImageBitmap(dest)
        return dest
    }

    /**
     * 转为灰度图片
     *
     * @param src 源图片
     * @return 灰度图
     */
    @JvmOverloads
    @JvmStatic
    fun toGray(src:Bitmap, recycle:Boolean = false):Bitmap? {
        if (isEmptyBitmap(src)) {
            return null
        }
        val grayBitmap = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.RGB_565)
        val canvas = Canvas(grayBitmap)
        val paint = Paint()
        val colorMatrix = ColorMatrix()
        colorMatrix.setSaturation(0f)
        val colorMatrixColorFilter = ColorMatrixColorFilter(colorMatrix)
        paint.colorFilter = colorMatrixColorFilter
        canvas.drawBitmap(src, 0f, 0f, paint)
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return grayBitmap
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @return `true`: 成功<br></br>`false`: 失败
     */
    @JvmStatic
    fun save(src:Bitmap, filePath:String?, format:Bitmap.CompressFormat?):Boolean {
        return save(src, FileUtils.getFileByPath(filePath), format, false)
    }

    /**
     * 保存图片
     *
     * @param src      源图片
     * @param filePath 要保存到的文件路径
     * @param format   格式
     * @param recycle  是否回收
     * @return `true`: 成功<br></br>`false`: 失败
     */
    @JvmStatic
    fun save(src:Bitmap, filePath:String?, format:Bitmap.CompressFormat?, recycle:Boolean):Boolean {
        return save(src, FileUtils.getFileByPath(filePath), format, recycle)
    }

    /**
     * 保存图片
     *
     * @param src    源图片
     * @param file   要保存到的文件
     * @param format 格式
     * @return `true`: 成功<br></br>`false`: 失败
     */
    @JvmOverloads
    @JvmStatic
    fun save(src:Bitmap, file:File?, format:Bitmap.CompressFormat?, recycle:Boolean = false):Boolean {
        if (isEmptyBitmap(src) || !FileUtils.createOrExistsFile(file)) {
            return false
        }
        println(src.width.toString() + ", " + src.height)
        var os:OutputStream? = null
        var ret = false
        try {
            os = BufferedOutputStream(FileOutputStream(file))
            ret = src.compress(format, 100, os)
            if (recycle && !src.isRecycled) {
                src.recycle()
            }
        } catch (e:IOException) {
            e.printStackTrace()
        } finally {
            FileUtils.closeIO(os)
        }
        return ret
    }

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param file 　文件
     * @return `true`: 是<br></br>`false`: 否
     */
    @JvmStatic
    fun isImage(file:File?):Boolean {
        return file != null && isImage(file.path)
    }

    /**
     * 根据文件名判断文件是否为图片
     *
     * @param filePath 　文件路径
     * @return `true`: 是<br></br>`false`: 否
     */
    @JvmStatic
    fun isImage(filePath:String):Boolean {
        val path = filePath.toUpperCase()
        return (path.endsWith(".PNG") || path.endsWith(".JPG") || path.endsWith(".JPEG") || path.endsWith(".BMP") || path.endsWith(".GIF"))
    }

    /**
     * 获取图片类型
     *
     * @param filePath 文件路径
     * @return 图片类型
     */
    @JvmStatic
    fun getImageType(filePath:String?):String? {
        return getImageType(FileUtils.getFileByPath(filePath))
    }

    /**
     * 获取图片类型
     *
     * @param file 文件
     * @return 图片类型
     */
    @JvmStatic
    fun getImageType(file:File?):String? {
        if (file == null) return null
        var `is`:InputStream? = null
        return try {
            `is` = FileInputStream(file)
            getImageType(`is`)
        } catch (e:IOException) {
            e.printStackTrace()
            null
        } finally {
            FileUtils.closeIO(`is`)
        }
    }

    /**
     * 流获取图片类型
     *
     * @param is 图片输入流
     * @return 图片类型
     */
    @JvmStatic
    fun getImageType(`is`:InputStream?):String? {
        return if (`is` == null) null else try {
            val bytes = ByteArray(8)
            if (`is`.read(bytes, 0, 8) != -1) getImageType(bytes) else null
        } catch (e:IOException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 获取图片类型
     *
     * @param bytes bitmap的前8字节
     * @return 图片类型
     */
    @JvmStatic
    fun getImageType(bytes:ByteArray):String? {
        if (isJPEG(bytes)) {
            return "JPEG"
        }
        if (isGIF(bytes)) {
            return "GIF"
        }
        if (isPNG(bytes)) {
            return "PNG"
        }
        return if (isBMP(bytes)) {
            "BMP"
        } else null
    }

    @JvmStatic
    fun isJPEG(b:ByteArray):Boolean {
        return b.size >= 2 && b[0] == 0xFF.toByte() && b[1] == 0xD8.toByte()
    }

    @JvmStatic
    fun isGIF(b:ByteArray):Boolean {
        return b.size >= 6 && b[0] == 'G'.toByte() && b[1] == 'I'.toByte() && b[2] == 'F'.toByte() && b[3] == '8'.toByte() && (b[4] == '7'.toByte() || b[4] == '9'.toByte()) && b[5] == 'a'.toByte()
    }

    @JvmStatic
    fun isPNG(b:ByteArray):Boolean {
        return (b.size >= 8 && b[0] == 137.toByte() && b[1] == 80.toByte() && b[2] == 78.toByte() && b[3] == 71.toByte() && b[4] == 13.toByte() && b[5] == 10.toByte() && b[6] == 26.toByte() && b[7] == 10.toByte())
    }

    @JvmStatic
    fun isBMP(b:ByteArray):Boolean {
        return b.size >= 2 && b[0].toInt() == 0x42 && b[1].toInt() == 0x4d
    }

    /**
     * 判断bitmap对象是否为空
     *
     * @param src 源图片
     * @return `true`: 是<br></br>`false`: 否
     */
    @JvmStatic
    fun isEmptyBitmap(src:Bitmap?):Boolean {
        return src == null || src.width == 0 || src.height == 0
    }

    /**
     * 缩略图工具类，
     * 可以根据本地视频文件源、
     * Bitmap 对象生成缩略图
     *
     * @param filePath
     * @param kind
     * @return
     */
    @JvmStatic
    fun getThumb(filePath:String, kind:Int):Bitmap? {
        return ThumbnailUtils.createVideoThumbnail(filePath, kind)
    }

    @JvmStatic
    fun getThumb(source:Bitmap?, width:Int, height:Int):Bitmap {
        return ThumbnailUtils.extractThumbnail(source, width, height)
    }

    /**
     * 绘制 9Path
     *
     * @param c
     * @param bmp
     * @param rect
     */
    @JvmStatic
    fun drawNinePath(c:Canvas?, bmp:Bitmap, rect:Rect?) {
        val patch = NinePatch(bmp, bmp.ninePatchChunk, null)
        patch.draw(c, rect)
    }

    /**
     * 创建的包含文字的图片，背景为透明
     *
     * @param source              图片
     * @param txtSize             文字大小
     * @param innerTxt            显示的文字
     * @param textColor           文字颜色Color.BLUE
     * @param textBackgroundColor 文字背景板颜色 Color.parseColor("#FFD700")
     * @return
     */
    @JvmStatic
    fun createTextImage(source:Bitmap, txtSize:Int, innerTxt:String, textColor:Int, textBackgroundColor:Int):Bitmap {
        val bitmapWidth = source.width
        val bitmapHeight = source.height
        val textWidth = txtSize * innerTxt.length
        val textHeight = txtSize
        val width:Int
        width = if (bitmapWidth > textWidth) {
            bitmapWidth + txtSize * innerTxt.length
        } else {
            txtSize * innerTxt.length
        }
        val height = bitmapHeight + txtSize

        //若使背景为透明，必须设置为Bitmap.Config.ARGB_4444
        val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
        val canvas = Canvas(bm)
        //把图片画上来
        val bitmapPaint = Paint()
        canvas.drawBitmap(source, (width - bitmapWidth) / 2.toFloat(), 0f, bitmapPaint)
        val paint = Paint()
        paint.color = textColor
        paint.textSize = txtSize.toFloat()
        paint.isAntiAlias = true

        //计算得出文字的绘制起始x、y坐标
        val posX = (width - txtSize * innerTxt.length) / 2
        val posY = height / 2
        val textX = posX + txtSize * innerTxt.length / 4
        val paint1 = Paint()
        paint1.color = textBackgroundColor
        paint1.strokeWidth = 3f
        paint1.style = Paint.Style.FILL_AND_STROKE
        val r1 = RectF()
        r1.left = posX.toFloat()
        r1.right = posX + txtSize * innerTxt.length.toFloat()
        r1.top = posY.toFloat()
        r1.bottom = posY + txtSize.toFloat()
        canvas.drawRoundRect(r1, 10f, 10f, paint1)
        canvas.drawText(innerTxt, textX.toFloat(), posY + txtSize - 2.toFloat(), paint)
        return bm
    }

    @JvmStatic
    fun getDensity(context:Context):Float {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val dm = DisplayMetrics()
        val display = wm.defaultDisplay
        display.getMetrics(dm)
        return dm.density
    }


}