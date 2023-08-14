package com.easy.album.utils

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.easy.album.R
import com.easy.album.annotation.LocalMediaType

/**
 * @Author : huangqiqiang
 * @Package : com.easy.album.utils
 * @FileName :   LoadUtils
 * @Date : 2020/1/16 0016  上午 10:56
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
object LoadUtils {
    /**
     * 加载 图片
     *
     * @param path
     * @param picture
     */
    fun loadImage(path: Uri?, picture: ImageView) {
        val options = RequestOptions().placeholder(R.drawable.image_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .centerCrop()
        Glide.with(picture.context)
            .load(path)
            .apply(options) // .override(150, 150)
            .into(picture)
    }

    /**
     * 加载视频
     *
     * @param path
     * @param picture
     */
    fun loadVideo(path: Uri?, picture: ImageView) {
        val options = RequestOptions().placeholder(R.drawable.image_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .centerCrop()
        options.signature(ObjectKey(System.currentTimeMillis()))
        Glide.with(picture.context)
            .load(path)
            .thumbnail(0.5f)
            .into(picture)
    }

    /**
     * 加载  本地 文件
     *
     * @param localMediaType 文件类型
     * @param path           地址
     * @param picture        控件
     */
    fun loadLocalMediaPath(localMediaType: Int, path: Uri?, picture: ImageView) {
        if (localMediaType == com.easy.album.annotation.LocalMediaType.VALUE_TYPE_IMAGE) {
            loadImage(path, picture)
        } else if (localMediaType == com.easy.album.annotation.LocalMediaType.VALUE_TYPE_VIDEO) {
            loadVideo(path, picture)
        }
    }
}