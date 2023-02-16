package com.hqq.core.glide

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.hqq.core.CoreConfig
import com.hqq.core.R
import com.hqq.core.utils.ResourcesUtils

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.blibrary.glide
 * @FileName :   ImageLoadUtils
 * @Date : 2018/2/9  9:23
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 * 目的 只是为了更方便的替换Glide以及维护图片的加载
 * 参考 http://blog.csdn.net/hexingen/article/details/72578066
 * 参考 http://blog.csdn.net/wyb112233/article/details/52337392
 */
object ImageLoadUtils {
    /**
     * 默认配置
     *
     * @return
     */
    fun getRequestOption(resourceId: Int = CoreConfig.get().defImg): RequestOptions {
        return RequestOptions().format(DecodeFormat.PREFER_RGB_565) //缓存SOURC和RESULT
            .diskCacheStrategy(DiskCacheStrategy.ALL) //不做内存缓存
            .skipMemoryCache(false).dontAnimate().placeholder(resourceId)//缓存SOURC和RESULT
    }

    /**
     * 圆角的 图片
     *
     * @param px
     * @return  RequestOptions
     */
    fun getRequestOptionRound(px: Int = ResourcesUtils.getDimen(R.dimen.x10).toInt(), resourceId: Int = CoreConfig.get().defImg): RequestOptions {
        return getRequestOption(resourceId).transform(GlideRoundTransform(px))
    }

    /**
     * 圆角
     *
     * @param url
     * @param imageView
     */
    fun withFillet(url: String?, imageView: ImageView) {
        GlideApp.with(imageView).load(url).apply(getRequestOptionRound()).into(imageView)
    }

    /**
     * 加载图片  指定宽度
     *
     * @param url
     * @param imageView
     * @param width     宽
     * @param height    高
     */
    fun with(url: Any, imageView: ImageView, width: Int = -1, height: Int = -1) {
        GlideApp.with(imageView).load(url).apply(getRequestOption().override(width, height)).into(imageView)
    }


    /**
     * @param url
     * @param imageView
     * @param radius    圆角  单位 px
     * @param  resourceId   R.mipmap.ic_def_head
     */
    fun withFillet2PX(url: String?, imageView: ImageView?, radius: Int, resourceId: Int = CoreConfig.get().defImg) {
        GlideApp.with(imageView!!).load(url).apply(getRequestOptionRound(radius, resourceId)).into(imageView)
    }


    /**
     * 圆形
     * @param url 地址
     * @param imageView 控件
     * @param resourceId 默认图 头像
     */
    fun transformCircularHead(url: String?, imageView: ImageView?, @DrawableRes resourceId: Int = R.mipmap.ic_def_head_circular) {
        GlideApp.with(imageView!!).load(url).thumbnail().apply(RequestOptions.circleCropTransform() //不做内存缓存
            .skipMemoryCache(true).dontAnimate().placeholder(resourceId)).into(imageView)

    }



}