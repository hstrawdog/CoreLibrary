/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.easy.core.album.entity

import android.net.Uri
import com.easy.core.album.annotation.LocalMediaType

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: cn.huangqiqiang.halbum.entity.LocalMedia.java
 * @author: 黄其强
 * @date: 2017-05-04 20:17
</描述当前版本功能> */
open class LocalMedia {
    /**
     * 物理地址
     */
    var path: String? = null

    /**
     * 相册对应的 uri
     */
    var uri: Uri? = null


    /**
     * 应该是 视频的时长
     */
    var duration: Long = 0

    /**
     * 添加时间
     */
    var lastUpdateAt: Long = 0

    /**
     * 是否选中
     */
    var isChecked: Boolean = false

    /**
     * 预览下标
     */
    var position: Int = 0

    /**
     * 类型 图片 视频
     */
    @LocalMediaType
    var localMediaType: Int = LocalMediaType.VALUE_TYPE_IMAGE
    var dateTaken: Long? = null


    /**
     * 压缩后的地址
     */
    var compressPath: String? = null

    /**
     * 裁剪地址
     */
    var cutPath: String? = null

    /**
     * 是否裁剪
     */
    var isCut: Boolean = false


    constructor()

    constructor(path: String?, lastUpdateAt: Long, duration: Long, @LocalMediaType type: Int) {
        this.path = path
        this.duration = duration
        this.lastUpdateAt = lastUpdateAt
        this.localMediaType = type
    }

    fun setLocalMediaType(@LocalMediaType localMediaType: Int): LocalMedia {
        this.localMediaType = localMediaType
        return this
    }

    fun getIsChecked(): Boolean {
        return this.isChecked
    }

    fun setIsChecked(isChecked: Boolean) {
        this.isChecked = isChecked
    }
}
