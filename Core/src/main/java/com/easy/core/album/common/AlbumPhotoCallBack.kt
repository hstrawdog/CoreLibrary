package com.easy.core.album.common

import com.easy.core.album.entity.LocalMedia

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.album.common
 * @Date : 17:03
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
interface AlbumPhotoCallBack {
    fun onSelectLocalMedia(arrayList: ArrayList<LocalMedia>?)


}