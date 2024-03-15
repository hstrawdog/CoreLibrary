package com.easy.core.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.easy.core.CoreConfig
import com.easy.core.R
import com.easy.core.glide.GlideApp
import com.easy.core.utils.ResourcesUtils

/**
 * @Author : huangqiqiang
 * @Package : com.easy.blibrary.glide
 * @FileName :   ImageLoadUtils
 * @Date : 2018/2/9  9:23
 * @Email :  qiqiang213@gmail.com
 * @Describe :
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
    @JvmStatic
    fun getRequestOption(resourceId: Int = CoreConfig.get().defImg): RequestOptions {
        return RequestOptions().format(DecodeFormat.PREFER_RGB_565) //缓存SOURC和RESULT
            .diskCacheStrategy(DiskCacheStrategy.ALL) //不做内存缓存
            .skipMemoryCache(false)
            .dontAnimate()
            .placeholder(resourceId)//缓存SOURC和RESULT
    }

    /**
     * 圆角的 图片
     *
     * @param px
     * @return  RequestOptions
     */
    @JvmStatic
    fun getRequestOptionRound(px: Int = ResourcesUtils.getDimen(R.dimen.x10)
        .toInt(), resourceId: Int = CoreConfig.get().defImg): RequestOptions {
        return getRequestOption(resourceId).transform(GlideRoundTransform(px))
    }

    /**
     * 圆角
     *
     * @param url
     * @param imageView
     */
    @JvmStatic
    fun withFillet(url: String?, imageView: ImageView) {
        GlideApp.with(imageView)
            .load(url)
            .apply(getRequestOptionRound())
            .into(imageView)
    }

    /**
     * 加载图片  指定宽度
     *
     * @param url
     * @param imageView
     * @param width     宽
     * @param height    高
     */
    @JvmStatic

    fun with(url: Any, imageView: ImageView, priority: Priority = Priority.NORMAL, width: Int = -1, height: Int = -1) {
        GlideApp.with(imageView)
            .load(url)
            .priority(priority)
            .apply(getRequestOption().override(width, height))
            .into(imageView)
    }

    /**
     * @param url
     * @param imageView
     * @param radius    圆角  单位 px
     * @param  resourceId   R.mipmap.ic_def_head
     */
    @JvmStatic

    fun withFillet2PX(url: String?, imageView: ImageView?, radius: Int, resourceId: Int = CoreConfig.get().defImg) {
        GlideApp.with(imageView!!)
            .load(url)
            .apply(getRequestOptionRound(radius, resourceId))
            .into(imageView)
    }

    /**
     * 圆形
     * @param url 地址
     * @param imageView 控件
     * @param resourceId 默认图 头像
     */
    @JvmStatic
    fun transformCircularHead(url: String?, imageView: ImageView?,
                              @DrawableRes resourceId: Int = R.mipmap.ic_def_head_circular) {
        GlideApp.with(imageView!!)
            .load(url)
            .thumbnail()
            .apply(RequestOptions.circleCropTransform() //不做内存缓存
                .skipMemoryCache(true)
                .dontAnimate()
                .placeholder(resourceId))
            .into(imageView)

    }

    /**
     * Glide获取网络图片，带容错机制error时开始一个新的请求
     * @param context Context?
     * @param uri String?
     * @param callback GlideLoadBitmapCallback
     */
    @JvmStatic
    fun getBitmapByFail(context: Context?, uri: String?, callback: GlideLoadBitmapCallback) {
        Glide.with(context!!)
            .asBitmap()
            .apply(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
            .load(uri)
            .error(Glide.with(context!!)
                .asBitmap()
                .load(uri))
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback.getBitmapCallback(resource)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    callback.onLoadFailed()
                }
            })
    }

    /**
     * 获取图片bitmap数据
     * @param context Context?
     * @param uri String?
     * @param width Int
     * @param height Int
     * @param callback GlideLoadBitmapCallback
     */
    @JvmStatic
    fun getBitmap(context: Context?, uri: String?, width: Int = -1, height: Int = -1,
                  callback: GlideLoadBitmapCallback) {
        Glide.with(context!!)
            .asBitmap()
            .apply(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(width, height))
            .load(uri)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback.getBitmapCallback(resource)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    callback.onLoadFailed()
                }
            })
    }


    /**
     * Glide获取网络图片，带容错机制error时开始一个新的请求
     * @param context Context?
     * @param uri String?
     * @param callback GlideLoadBitmapCallback
     */
    @JvmStatic
    fun getBitmapByFailover(context: Context?, uri: String?, callback: GlideLoadBitmapCallback) {
        Glide.with(context!!)
            .asBitmap()
            .apply(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
            .load(uri)
            .error(Glide.with(context!!)
                .asBitmap()
                .load(uri))
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback.getBitmapCallback(resource)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    callback.onLoadFailed()
                }
            })
    }
    /**
     * 加载圆形图
     *
     * @param url
     * @param imageView
     */
    @JvmStatic
    fun transformHead(url: String?, imageView: ImageView?) {
        imageView?.let {
            GlideApp.with(it
            )
                .load(url)
                .apply(RequestOptions.circleCropTransform()
                    .placeholder(R.mipmap.ic_user_img_avatar))
                .into(imageView)
        }
    }


    interface GlideLoadBitmapCallback {
        fun getBitmapCallback(bitmap: Bitmap)
        fun onLoadFailed()
    }
}