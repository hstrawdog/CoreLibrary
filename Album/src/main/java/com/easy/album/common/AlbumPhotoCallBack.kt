package com.easy.album.common

import com.easy.album.entity.LocalMedia

/**
 * @Author : huangqiqiang
 * @Package : com.easy.album.common
 * @Date : 17:03
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
interface AlbumPhotoCallBack {
    fun onSelectLocalMedia(arrayList: ArrayList<com.easy.album.entity.LocalMedia>?)


}