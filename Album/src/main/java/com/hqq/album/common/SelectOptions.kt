package com.hqq.album.common

import com.hqq.album.entity.LocalMedia

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.common
 * @FileName :   SelectOptions
 * @Date : 2020/7/1 0001  上午 9:39
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class SelectOptions {
    /**
     * 选择后的数据
     */
    var selectLocalMedia = ArrayList<LocalMedia>()

    /**
     * 文件夹缓存内容
     */
    var mFolderLocalMedia: ArrayList<LocalMedia> = ArrayList()

    /**
     *  回调
     */
    var call: AlbumPhotoCallBack? = null

    fun reset() {
        call = null
        mFolderLocalMedia.clear()
        selectLocalMedia.clear()
    }

    companion object {
        val instance = SelectOptions()
    }
}