package com.hqq.album.common

import android.content.Intent
import com.hqq.album.activity.AlbumDirectoryActivity
import com.hqq.album.activity.AlbumFolderActivity
import com.hqq.album.annotation.LocalMediaType

/**
 * @version V1.0 <描述当前版本功能>
 * @author: huangqiqiang
 * 在此写用途
 * @FileName: cn.huangqiqiang.halbum.common.FunctionOptions.java
 * @emain: 593979591@qq.com
 * @date: 2017-05-07 22:19
</描述当前版本功能> */
class FunctionOptions {
    /**
     * 缓存的单利对象
     */
    var mAlbum: Album? = null

    /**
     * 多选最大可选数量
     */
    var maxSelectNum = 9
        private set

    /**
     * 是否显示拍照图片
     */
    var isDisplayCamera = false
        private set

    /**
     * 启动相机 拍照
     */
    var isStartUpCamera = false
        private set

    /**
     * 默认显示的图片
     */
    var albumType = LocalMediaType.VALUE_TYPE_IMAGE
        private set

    /**
     * 默认显示的文件夹
     */
    var chooseFolder = "最近照片"
        private set

    /**
     * 是否支持gif
     */
    var isSupportGif = false
        private set

    /**
     * 拍照后是否发送至相册
     */
    var isSendAlbum = true
        private set

    private fun rest() {
        isStartUpCamera = false
        isStartUpCamera = false
        maxSelectNum = 9
        albumType = LocalMediaType.VALUE_TYPE_IMAGE
    }

    fun setStartUpCamera(startUpCamera: Boolean): FunctionOptions {
        isStartUpCamera = startUpCamera
        return this
    }

    fun setMaxSelectNum(maxSelectNum: Int): FunctionOptions {
        this.maxSelectNum = maxSelectNum
        return this
    }

    fun setDisplayCamera(displayCamera: Boolean): FunctionOptions {
        isDisplayCamera = displayCamera
        return this
    }

    fun setAlbum(album: Album?): FunctionOptions {
        mAlbum = album
        return this
    }

    fun setAlbumType(albumType: Int): FunctionOptions {
        rest()
        this.albumType = albumType
        return this
    }

    fun setSupportGif(supportGif: Boolean): FunctionOptions {
        isSupportGif = supportGif
        return this
    }

    fun setIsSendAlbum(isSendAlbum: Boolean): FunctionOptions {
        this.isSendAlbum = isSendAlbum
        return this
    }

    fun setChooseFolder(chooseFolder: String): FunctionOptions {
        this.chooseFolder = chooseFolder
        return this
    }

    /**
     * 数据将回调给调用的 activity 或者Fragment
     *
     * @param requestCode
     */
    fun forResult(requestCode: Int) {
        val activity = mAlbum!!.activity
        SelectOptions.instance.reset()
        val intent = Intent(activity, AlbumDirectoryActivity::class.java)
        val fragment = mAlbum!!.fragment
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode)
        } else {
            activity!!.startActivityForResult(intent, requestCode)
        }
    }

    /**
     * 打开指定文件夹
     *
     * @param requestCode
     */
    fun forFolderResult(requestCode: Int) {
        val activity = mAlbum!!.activity
        SelectOptions.instance.reset()
        val intent = Intent(activity, AlbumFolderActivity::class.java)
        val fragment = mAlbum!!.fragment
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode)
        } else {
            activity!!.startActivityForResult(intent, requestCode)
        }
    }


    companion object {
        val instance = FunctionOptions()

    }
}