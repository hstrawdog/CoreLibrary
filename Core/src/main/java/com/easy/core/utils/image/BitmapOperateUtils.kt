package com.easy.core.utils.image

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.util.Log
import com.easy.core.utils.image.BitmapUtils.isEmptyBitmap
import java.io.ByteArrayOutputStream

/**
 * 图片处理工具类，包括缩放、裁剪、旋转和压缩等操作
 */
object BitmapOperateUtils {
    /**
     * 等比缩放 Bitmap，最大边长限制为 maxLength
     *
     * @param bitmap 原始 Bitmap
     * @param maxLength 最大边长（宽或高）
     * @return 缩放后的 Bitmap，如果原图已小于等于 maxLength，则返回原图
     */
    fun  scaleBitmapProportionalMax(bitmap: Bitmap, maxLength: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val scale = if (width >= height) {
            maxLength.toFloat() / width
        } else {
            maxLength.toFloat() / height
        }


        val newWidth = (width * scale).toInt()
        val newHeight = (height * scale).toInt()

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

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
        return zoomImage(src, scaleWidth, scaleHeight, recycle)
    }

    /**
     * 居中裁剪到指定宽高（类似 ImageView.ScaleType.CENTER_CROP）
     * @param src 原始 Bitmap
     * @param newWidth 目标宽度
     * @param newHeight 目标高度
     * @param recycle 是否回收原图
     * @return 裁剪后的 Bitmap
     */
    @JvmStatic
    fun centerCrop(src: Bitmap?, newWidth: Int, newHeight: Int, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src)) return null
        if (newWidth == src.width && newHeight == src.height) return src

        val srcWidth = src.width
        val srcHeight = src.height

        // 按比例缩放，取较大值保证能覆盖目标区域
        val scale = maxOf(
            newWidth.toFloat() / srcWidth,
            newHeight.toFloat() / srcHeight
                         )

        val scaledWidth = (srcWidth * scale).toInt()
        val scaledHeight = (srcHeight * scale).toInt()

        val scaledBitmap = Bitmap.createScaledBitmap(src, scaledWidth, scaledHeight, true)

        val x = (scaledWidth - newWidth) / 2
        val y = (scaledHeight - newHeight) / 2

        val result = Bitmap.createBitmap(scaledBitmap, x, y, newWidth, newHeight)

        if (recycle && !src.isRecycled) src.recycle()
        if (recycle && scaledBitmap != src && !scaledBitmap.isRecycled) scaledBitmap.recycle()

        return result
    }

    /**
     * 居中缩放到指定宽高（类似 ImageView.ScaleType.FIT_CENTER）
     * @param src 原始 Bitmap
     * @param newWidth 目标宽度
     * @param newHeight 目标高度
     * @param recycle 是否回收原图
     * @return 缩放后的 Bitmap（可能有留白）
     */
    @JvmStatic
    fun fitCenter(src: Bitmap?, newWidth: Int, newHeight: Int, recycle: Boolean = false): Bitmap? {
        if (src == null || isEmptyBitmap(src)) return null
        if (newWidth == src.width && newHeight == src.height) return src

        val srcWidth = src.width
        val srcHeight = src.height

        // 按比例缩放，取较小值保证能完整显示在目标区域
        val scale = minOf(
            newWidth.toFloat() / srcWidth,
            newHeight.toFloat() / srcHeight
                         )

        val scaledWidth = (srcWidth * scale).toInt()
        val scaledHeight = (srcHeight * scale).toInt()

        val scaledBitmap = Bitmap.createScaledBitmap(src, scaledWidth, scaledHeight, true)

        // 创建一个目标大小的空白图（透明背景）
        val result = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)

        // 将缩放后的图居中绘制
        val left = (newWidth - scaledWidth) / 2f
        val top = (newHeight - scaledHeight) / 2f
        canvas.drawBitmap(scaledBitmap, left, top, null)

        if (recycle && !src.isRecycled) src.recycle()
        if (recycle && scaledBitmap != src && !scaledBitmap.isRecycled) scaledBitmap.recycle()

        return result
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
        return zoomImage(src, scale, scale, recycle)
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
        return zoomImage(src, scale, scale, recycle)
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
     * 镜像 Bitmap（水平或垂直方向）
     * @param src 原始 Bitmap
     * @param recycle 是否回收原图
     * @param isHorizontal 是否水平镜像，false 表示垂直镜像
     * @return 镜像后的 Bitmap
     */
    @JvmStatic
    fun mirrorBitmap(src: Bitmap?, recycle: Boolean = false, isHorizontal: Boolean = true): Bitmap? {
        if (src == null || isEmptyBitmap(src)) return null

        val matrix = Matrix().apply {
            if (isHorizontal) {
                postScale(-1f, 1f)  // 水平翻转
            } else {
                postScale(1f, -1f)  // 垂直翻转
            }
        }

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
