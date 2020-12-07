package com.hqq.core.glide

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.hqq.core.utils.log.LogUtils
import java.security.MessageDigest

/**
 * @Author : huangqiqiang
 * @Package : com.core.library.glide
 * @FileName :   GlideRoundTransform
 * @Date : 2018/11/26 0026  下午 3:14
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
class GlideRoundTransform(dp: Int) : BitmapTransformation() {
    private var radius = 0f

    init {
        radius = dp.toFloat()
    }
    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight)
        return roundCrop(pool, bitmap)!!
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {}

    private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) {
            return null
        }

        var result: Bitmap? = pool[source.width, source.height, Bitmap.Config.ARGB_8888]
        if (result == null) {
            result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.isAntiAlias = true
        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rectF, radius, radius, paint)
        return result
    }


}