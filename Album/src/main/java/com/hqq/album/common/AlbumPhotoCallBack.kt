package com.hqq.album.common

import com.hqq.album.entity.LocalMedia

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.common
 * @Date : 17:03
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
interface AlbumPhotoCallBack {
    fun onSelectLocalMedia(arrayList: ArrayList<LocalMedia>?)


}