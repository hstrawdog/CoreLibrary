package com.easy.core.utils.image

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.utils.image
 * @Date : 02:26
 * @Email : qiqiang213@gmail.com
 * @Describe : bitmap  操作
 *   缩放
 *   裁剪
 *   倾斜
 *   裁剪
 */
object BitmapOperateUtils {


    //region 缩放
    /**
     * 缩放图片
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @return 缩放后的图片
     */
    @JvmStatic
    fun scale(src: Bitmap, newWidth: Int, newHeight: Int, recycle: Boolean = false): Bitmap? {
        if (BitmapUtils.isEmptyBitmap(src)) {
            return null
        }
        val ret = Bitmap.createScaledBitmap(src, newWidth, newHeight, true)
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    /**
     * 缩放图片
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @return 缩放后的图片
     */
    @JvmStatic
    fun scale(src: Bitmap, scaleWidth: Float, scaleHeight: Float, recycle: Boolean = false): Bitmap? {
        if (BitmapUtils.isEmptyBitmap(src)) {
            return null
        }
        val matrix = Matrix()
        matrix.setScale(scaleWidth, scaleHeight)
        val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    /**
     * 等比缩放图片
     *
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return
     */
    @JvmStatic
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
    @JvmStatic
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
        return BitmapUtils.bitmapCombine(newbm, 5, 5, Color.TRANSPARENT)
    }

    /**
     * 以最小的比例 进行缩放图片
     * @param bm Bitmap
     * @param newWidth Int
     * @param newHeight Int
     * @return Bitmap
     */
    @JvmStatic
    fun zoomImg4minScale(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap { // 获得图片的宽高
        val width = bm.width
        val height = bm.height // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height // 取得想要缩放的matrix参数
        var sc = Math.min(scaleWidth, scaleHeight)
        val matrix = Matrix()
        matrix.postScale(sc, sc) // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
    }

    @JvmStatic
    fun zoomDrawable(drawable: Drawable, w: Int, h: Int): Bitmap {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val oldbmp = BitmapUtils.drawableToBitmap(drawable)
        val matrix = Matrix()
        val scaleWidth = w.toFloat() / width
        val scaleHeight = h.toFloat() / height
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true)
    }

    /**
     *  最小比例 缩放
     * @param bm Bitmap
     * @param newWidth Int
     * @param newHeight Int
     * @return Bitmap
     */
    @JvmStatic
    fun zoomImg4Min(bm: Bitmap, newWidth: Int, newHeight: Int): Bitmap { // 获得图片的宽高
        val width = bm.width
        val height = bm.height // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height // 取得想要缩放的matrix参数
        var sc = Math.min(scaleWidth, scaleHeight)
        val matrix = Matrix()
        matrix.postScale(sc, sc) // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)
    }

    /**
     * 超过过最大值 等比压缩
     * @param bm Bitmap
     * @param max Int
     * @return Bitmap
     */
    @JvmStatic

    fun zoomImg4Max(bm: Bitmap, max: Int): Bitmap {
        val width = bm.width
        val height = bm.height // 计算缩放比例
        val size = Math.max(width, height)
        if (size <= max) {
            return bm
        }
        var scal = max / size.toFloat()
        val matrix = Matrix()
        matrix.postScale(scal, scal) // 得到新的图片
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)

    }

    /**
     * 超过过最大值 等比压缩
     * @param bm Bitmap
     * @param max Int
     * @return Bitmap
     */
    fun zoomImg3(bm: Bitmap, max: Int): Bitmap {
        val width = bm.width
        val height = bm.height // 计算缩放比例
        val size = Math.max(width, height)
        if (size <= max) {
            return bm
        }
        var scal = max / size.toFloat()
        val matrix = Matrix()
        matrix.postScale(scal, scal) // 得到新的图片

        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true)

    }

    /**
     * 按最大边按一定大小缩放图片
     *
     * @param resources
     * @param resId
     * @param maxSize   压缩后最大长度
     * @return
     */
    @JvmStatic
    fun scaleImage(resources: Resources?, resId: Int, maxSize: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, resId, options)
        options.inSampleSize = BitmapUtils.calculateInSampleSize2(options, maxSize, maxSize)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(resources, resId, options)
    }

    @JvmStatic
    fun scalePicture(fileName: String?, options: Int): ByteArrayOutputStream {
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


    //endregion

    /**
     * 裁剪图片
     *
     * @param src    源图片
     * @param x      开始坐标x
     * @param y      开始坐标y
     * @param width  裁剪宽度
     * @param height 裁剪高度
     * @return 裁剪后的图片
     */
    @JvmOverloads
    @JvmStatic
    fun clip(src: Bitmap, x: Int, y: Int, width: Int, height: Int, recycle: Boolean = false): Bitmap? {
        if (BitmapUtils.isEmptyBitmap(src)) {
            return null
        }
        val ret = Bitmap.createBitmap(src, x, y, width, height)
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    /**
     * 倾斜图片
     *
     * @param src     源图片
     * @param kx      倾斜因子x
     * @param ky      倾斜因子y
     * @param recycle 是否回收
     * @return 倾斜后的图片
     */
    @JvmStatic
    fun skew(src: Bitmap, kx: Float, ky: Float, recycle: Boolean): Bitmap? {
        return skew(src, kx, ky, 0f, 0f, recycle)
    }

    /**
     * 倾斜图片
     *
     * @param src 源图片
     * @param kx  倾斜因子x
     * @param ky  倾斜因子y
     * @param px  平移因子x
     * @param py  平移因子y
     * @return 倾斜后的图片
     */
    @JvmStatic
    fun skew(src: Bitmap, kx: Float, ky: Float, px: Float, py: Float): Bitmap? {
        return skew(src, kx, ky, 0f, 0f, false)
    }

    /**
     * 倾斜图片
     *
     * @param src 源图片
     * @param kx  倾斜因子x
     * @param ky  倾斜因子y
     * @return 倾斜后的图片
     */
    @JvmStatic
    fun skew(src: Bitmap, kx: Float, ky: Float, px: Float = 0f, py: Float = 0f, recycle: Boolean = false): Bitmap? {
        if (BitmapUtils.isEmptyBitmap(src)) {
            return null
        }
        val matrix = Matrix()
        matrix.setSkew(kx, ky, px, py)
        val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    //region 旋转
    /**
     * 旋转图片
     *
     * @param src     源图片
     * @param degrees 旋转角度
     * @param px      旋转点横坐标
     * @param py      旋转点纵坐标
     * @return 旋转后的图片
     */
    @JvmStatic
    fun rotate(src: Bitmap, degrees: Int, px: Float, py: Float, recycle: Boolean = false): Bitmap? {
        if (BitmapUtils.isEmptyBitmap(src)) {
            return null
        }
        if (degrees == 0) {
            return src
        }
        val matrix = Matrix()
        matrix.setRotate(degrees.toFloat(), px, py)
        val ret = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return ret
    }

    /**
     *  旋转图片
     * @param bitmap Bitmap?
     * @param rotate Int
     * @return Bitmap?
     */
    @JvmStatic
    fun rotateBitmap(bitmap: Bitmap, rotate: Int): Bitmap {

        val w = bitmap.width
        val h = bitmap.height
        val mtx = Matrix()
        mtx.postRotate(rotate.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true)
    }

    /**
     * 镜像水平翻转
     * @param imageBitmap Bitmap
     * @return Bitmap
     */
    fun rotateLevel(imageBitmap: Bitmap): Bitmap {
        val width = imageBitmap.width
        val height = imageBitmap.height
        val m = Matrix()
        m.postScale(-1f, 1f) //镜像水平翻转
        return Bitmap.createBitmap(imageBitmap, 0, 0, width, height, m, true)

    }

    /**
     *
     * @param imageBitmap Bitmap
     * @return Bitmap
     */
    fun rotateVertical(imageBitmap: Bitmap): Bitmap {
        val width = imageBitmap.width
        val height = imageBitmap.height
        val m = Matrix()
        m.postScale(1f, -1f) //镜像水平翻转
        return Bitmap.createBitmap(imageBitmap, 0, 0, width, height, m, true)

    }
    //endregion

    /******************************~~~~~~~~~ 下方和压缩有关 ~~~~~~~~~ */

    /**
     * 按缩放压缩
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @return 缩放压缩后的图片
     */
    @JvmStatic
    fun compressByScale(src: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        return BitmapOperateUtils.scale(src, newWidth, newHeight, false)
    }

    /**
     * 按缩放压缩
     *
     * @param src       源图片
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @param recycle   是否回收
     * @return 缩放压缩后的图片
     */
    @JvmStatic
    fun compressByScale(src: Bitmap, newWidth: Int, newHeight: Int, recycle: Boolean): Bitmap? {
        return BitmapOperateUtils.scale(src, newWidth, newHeight, recycle)
    }

    /**
     * 按缩放压缩
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @return 缩放压缩后的图片
     */
    @JvmStatic
    fun compressByScale(src: Bitmap, scaleWidth: Float, scaleHeight: Float): Bitmap? {
        return BitmapOperateUtils.scale(src, scaleWidth, scaleHeight, false)
    }

    /**
     * 按缩放压缩
     *
     * @param src         源图片
     * @param scaleWidth  缩放宽度倍数
     * @param scaleHeight 缩放高度倍数
     * @param recycle     是否回收
     * @return 缩放压缩后的图片
     */
    @JvmStatic
    fun compressByScale(src: Bitmap, scaleWidth: Float, scaleHeight: Float, recycle: Boolean): Bitmap? {
        return BitmapOperateUtils.scale(src, scaleWidth, scaleHeight, recycle)
    }

    /**
     * 按质量压缩
     *
     * @param src     源图片
     * @param quality 质量
     * @return 质量压缩后的图片
     */
    @JvmOverloads
    @JvmStatic
    fun compressByQuality(src: Bitmap, quality: Int, recycle: Boolean = false): Bitmap? {
        if (BitmapUtils.isEmptyBitmap(src) || quality < 0 || quality > 100) {
            return null
        }
        val baos = ByteArrayOutputStream()
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos)
        val bytes = baos.toByteArray()
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * 按质量压缩
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @return 质量压缩压缩过的图片
     */
    @JvmOverloads
    @JvmStatic
    fun compressByQuality(src: Bitmap, maxByteSize: Long, recycle: Boolean = false): Bitmap? {
        if (BitmapUtils.isEmptyBitmap(src) || maxByteSize <= 0) {
            return null
        }
        val baos = ByteArrayOutputStream()
        var quality = 100
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos)
        while (baos.toByteArray().size > maxByteSize && quality >= 0) {
            baos.reset()
            src.compress(Bitmap.CompressFormat.JPEG, 5.let { quality -= it; quality }, baos)
        }
        if (quality < 0) {
            return null
        }
        val bytes = baos.toByteArray()
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    /**
     * 按采样大小压缩
     *
     * @param src        源图片
     * @param sampleSize 采样率大小
     * @return 按采样率压缩后的图片
     */
    @JvmOverloads
    @JvmStatic
    fun compressBySampleSize(src: Bitmap, sampleSize: Int, recycle: Boolean = false): Bitmap? {
        if (BitmapUtils.isEmptyBitmap(src)) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inSampleSize = sampleSize
        val baos = ByteArrayOutputStream()
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val bytes = baos.toByteArray()
        if (recycle && !src.isRecycled) {
            src.recycle()
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    }

}
