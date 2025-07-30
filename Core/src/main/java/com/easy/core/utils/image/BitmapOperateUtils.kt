package com.easy.core.utils.image

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import com.easy.core.utils.image.BitmapUtils.isEmptyBitmap
import java.io.ByteArrayOutputStream

/**
 * 图片处理工具类，包括缩放、裁剪、旋转和压缩等操作
 */
object BitmapOperateUtils {

    /**
     * 按比例缩放图片（指定缩放倍数）
     * @param src 原始 Bitmap
     * @param scaleWidth 宽度缩放倍数（例如 0.5f 为缩小一半）
     * @param scaleHeight 高度缩放倍数
     * @param recycle 是否回收原图
     * @return 缩放后的 Bitmap
     */
    @JvmStatic
    fun zoomImage(src: Bitmap?, scaleWidth: Float, scaleHeight: Float, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src)) return null
        if (scaleWidth == 1f && scaleHeight == 1f) return src

        val matrix = Matrix().apply { postScale(scaleWidth, scaleHeight) }
        val result = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled && result != src) src.recycle()
        return result
    }

    /**
     * 将 Bitmap 缩放到目标宽高
     * @param src 原始 Bitmap
     * @param newWidth 目标宽度
     * @param newHeight 目标高度
     * @param recycle 是否回收原图
     * @return 缩放后的 Bitmap
     */
    @JvmStatic
    fun scaleImage(src: Bitmap?, newWidth: Float, newHeight: Float, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src)) return null
        if (newWidth.toInt() == src.width && newHeight.toInt() == src.height) return src

        val scaleWidth = newWidth / src.width.toFloat()
        val scaleHeight = newHeight / src.height.toFloat()
        return scaleImage(src, scaleWidth, scaleHeight, recycle)
    }

    /**
     * 缩放 Bitmap（以最大边为基准等比缩放）
     * @param src 原始 Bitmap
     * @param maxSize 最大边尺寸
     * @param recycle 是否回收原图
     * @return 缩放后的 Bitmap
     */
    @JvmStatic
    fun scaleByMax(src: Bitmap?, maxSize: Int, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src)) return null
        val max = src.width.coerceAtLeast(src.height)
        if (max <= maxSize) return src
        val scale = maxSize.toFloat() / max
        return scaleImage(src, scale, scale, recycle)
    }

    /**
     * 缩放 Bitmap（以最小边为基准等比缩放）
     * @param src 原始 Bitmap
     * @param minSize 最小边尺寸
     * @param recycle 是否回收原图
     * @return 缩放后的 Bitmap
     */
    @JvmStatic
    fun scaleByMin(src: Bitmap?, minSize: Int, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src)) return null
        val min = src.width.coerceAtMost(src.height)
        if (min <= minSize) return src
        val scale = minSize.toFloat() / min
        return scaleImage(src, scale, scale, recycle)
    }

    /**
     * 裁剪 Bitmap
     * @param src 原始 Bitmap
     * @param x 裁剪起始 x 坐标
     * @param y 裁剪起始 y 坐标
     * @param width 裁剪宽度
     * @param height 裁剪高度
     * @param recycle 是否回收原图
     * @return 裁剪后的 Bitmap
     */
    @JvmStatic
    fun cropBitmap(src: Bitmap?, x: Int, y: Int, width: Int, height: Int, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src)) return null
        if (x + width > src.width || y + height > src.height) return null
        val result = Bitmap.createBitmap(src, x, y, width, height)
        if (recycle && !src.isRecycled && result != src) src.recycle()
        return result
    }

    /**
     * 旋转 Bitmap
     * @param src 原始 Bitmap
     * @param degrees 旋转角度（顺时针）
     * @param recycle 是否回收原图
     * @return 旋转后的 Bitmap
     */
    @JvmStatic
    fun rotateBitmap(src: Bitmap?, degrees: Float, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src)) return null
        if (degrees == 0f) return src

        val matrix = Matrix().apply { postRotate(degrees) }
        val result = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled && result != src) src.recycle()
        return result
    }

    /**
     * 镜像 Bitmap（水平方向）
     * @param src 原始 Bitmap
     * @param recycle 是否回收原图
     * @return 镜像后的 Bitmap
     */
    @JvmStatic
    fun mirrorBitmap(src: Bitmap?, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src)) return null

        val matrix = Matrix().apply { postScale(-1f, 1f) }
        val result = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled && result != src) src.recycle()
        return result
    }

    /**
     * 倾斜 Bitmap
     * @param src 原始 Bitmap
     * @param kx x 轴倾斜因子
     * @param ky y 轴倾斜因子
     * @param recycle 是否回收原图
     * @return 倾斜后的 Bitmap
     */
    @JvmStatic
    fun skewBitmap(src: Bitmap?, kx: Float, ky: Float, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src)) return null

        val matrix = Matrix().apply { postSkew(kx, ky) }
        val result = Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
        if (recycle && !src.isRecycled && result != src) src.recycle()
        return result
    }

    /**
     * 按质量压缩图片（通过不断降低 JPEG 质量）
     * @param src 原始 Bitmap
     * @param maxByteSize 最大字节大小
     * @param recycle 是否回收原图
     * @return 压缩后的 Bitmap
     */
    @JvmStatic
    fun compressByQuality(src: Bitmap?, maxByteSize: Long, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src) || maxByteSize <= 0) return null

        val baos = ByteArrayOutputStream()
        var quality = 100
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos)
        while (baos.size() > maxByteSize && quality > 0) {
            quality -= 5
            baos.reset()
            src.compress(Bitmap.CompressFormat.JPEG, quality, baos)
        }

        val byteArray = baos.toByteArray()
        if (recycle && !src.isRecycled) src.recycle()
        return BitmapUtils.bytes2Bitmap(byteArray)
    }

    /**
     * 按采样率压缩图片（通过 BitmapFactory.Options 设置 inSampleSize）
     * @param src 原始 Bitmap
     * @param sampleSize 采样率，必须 >= 1
     * @param recycle 是否回收原图
     * @return 压缩后的 Bitmap
     */
    @JvmStatic
    fun compressBySample(src: Bitmap?, sampleSize: Float, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src) || sampleSize < 1) return null
        val width = src.width / sampleSize
        val height = src.height / sampleSize
        return scaleImage(src, width, height, recycle)
    }
}
