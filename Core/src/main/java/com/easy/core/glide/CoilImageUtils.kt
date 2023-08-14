package com.easy.core.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import coil.Coil
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import coil.util.CoilUtils
import com.easy.core.CoreConfig
import okhttp3.OkHttpClient

class CoilImageUtils {


    suspend fun init(imageView: ImageView) {






    }

    /**
     * 从图片地址获取Bitmap
     * @receiver Context
     * @param url String
     * @return Bitmap?
     */
    suspend fun Context.getImageBitmapByUrl(url:String):Bitmap? {
        val request = ImageRequest.Builder(this)
            //
            .data(url)
            //
            .allowHardware(false).build()
        val result = (imageLoader.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    /**
     *  普通的加载图片    imageView 的 拓展
     */
    fun into(imageView:ImageView, url:String, placeholder:Int, errorDrawable:Drawable) {

        imageView.load(url) {
            //渐进进出
            crossfade(true)
            //加载中占位图
            placeholder(placeholder)
            //圆形图
            // 模糊变换 BlurTransformation
            // 圆形 CircleCropTransformation
            // 灰度变换 GrayscaleTransformation
            // 圆角变换  RoundedCornersTransformation
            transformations(CircleCropTransformation())
            //加载错误占位图
            error(errorDrawable)
        }
    }


}