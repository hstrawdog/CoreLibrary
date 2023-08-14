/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.easy.album.entity

import android.net.Uri

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: cn.huangqiqiang.halbum.entity.LocalMediaFolder.java
 * @author: 黄其强
 * @date: 2017-05-04 20:16
</描述当前版本功能> */
class LocalMediaFolder {
    var name: String? = null
    var path: String? = null
    var firstImagePath: String? = null
    var firstImageUri: Uri? = null
    var imageNum = 0
    var isChecked = false
    var checkedNum = 0
    var type = 0
    var images: ArrayList<com.easy.album.entity.LocalMedia> = ArrayList()
}