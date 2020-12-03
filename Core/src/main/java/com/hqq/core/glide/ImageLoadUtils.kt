package com.hqq.core.glide

import android.widget.ImageView
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
    fun getRequestOption(): RequestOptions {
        return RequestOptions()
                .format(DecodeFormat.PREFER_RGB_565) //缓存SOURC和RESULT
                .diskCacheStrategy(DiskCacheStrategy.ALL) //不做内存缓存
                .skipMemoryCache(false)
                .dontAnimate()
                .placeholder(CoreConfig.get().defImg)//缓存SOURC和RESULT
    }

    /**
     * 圆角的 图片
     *
     * @param px
     * @return
     */
    fun getRequestOptionRound(px: Int = ResourcesUtils.getDimen(R.dimen.x10).toInt()): RequestOptions {
        return getRequestOption()
                .transform(GlideRoundTransform(px))
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

        GlideApp.with(imageView)
                .load(url)
                .apply(getRequestOption().override(width, height))
                .into(imageView)
    }

    /**
     * 圆角
     *
     * @param url
     * @param imageView
     */
    fun withFillet(url: String?, imageView: ImageView) {
        GlideApp.with(imageView)
                .load(url)
                .apply(getRequestOptionRound())
                .into(imageView)
    }

    /**
     * @param url
     * @param imageView
     * @param radius    圆角  单位 px
     */
    fun withFillet2PX(url: String?, imageView: ImageView?, radius: Int) {
        GlideApp.with(imageView!!)
                .load(url)
                .apply(
                        getRequestOption().transforms(CenterCrop(), RoundedCorners(radius))
                )
                .into(imageView)
    }

    /**
     * 加载圆形图
     *
     * @param url
     * @param imageView
     */
    fun transformHead(url: String?, imageView: ImageView?) {
        GlideApp.with(imageView!!).load(url).apply(
                RequestOptions.circleCropTransform()
                        .placeholder(R.mipmap.ic_def_head)
        ).into(imageView)
    }

    /**
     * 圆角头像
     *
     * @param url
     * @param imageView
     */
    fun transformCircularHead(url: String?, imageView: ImageView?) {
        GlideApp.with(imageView!!)
                .load(url)
                .thumbnail()
                .apply(RequestOptions.circleCropTransform() //不做内存缓存
                        .skipMemoryCache(true)
                        .dontAnimate()
                        .placeholder(R.mipmap.ic_def_head_circular)
                )
                .into(imageView)
    }
}