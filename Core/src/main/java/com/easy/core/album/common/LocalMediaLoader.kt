/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.easy.core.album.common

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.FragmentActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.easy.core.R
import com.easy.core.album.annotation.LocalMediaType
import com.easy.core.album.entity.LocalMediaFolder
import java.io.File
import java.util.Collections

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: cn.huangqiqiang.halbum.common.LocalMediaLoader.java
 * @author: 黄其强
 * @date: 2017-05-04 20:14
</描述当前版本功能> */
class LocalMediaLoader(private val activity: FragmentActivity, @LocalMediaType localMediaType: Int, isGif: Boolean) {
    var isGif: Boolean
    var index = 0

    @LocalMediaType
    private var mLocalMediaType = LocalMediaType.VALUE_TYPE_IMAGE

    init {
        mLocalMediaType = localMediaType
        this.isGif = isGif
    }

    fun loadAllImage(imageLoadListener: LocalMediaLoadListener) {

        LoaderManager.getInstance(activity).initLoader(mLocalMediaType, null, object :
            LoaderManager.LoaderCallbacks<Cursor?> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor?> {
                var cursorLoader: CursorLoader? = null
                var select: Array<String>? = null
                var condition = ""
                if (isGif) {

                    select = arrayOf("image/jpeg", "image/png", "image/gif")
                    condition =
                        (MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?" + " or " + MediaStore.Images.Media.MIME_TYPE + "=?")
                } else {
                    select = arrayOf("image/jpeg", "image/png")
                    condition =
                        (MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?")
                }
                if (id == LocalMediaType.VALUE_TYPE_IMAGE) {
                    cursorLoader =
                        CursorLoader(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, condition, select, IMAGE_PROJECTION[2] + " DESC")
                } else if (id == LocalMediaType.VALUE_TYPE_VIDEO) {
                    cursorLoader =
                        CursorLoader(activity, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_PROJECTION, null, null, VIDEO_PROJECTION[2] + " DESC")
                }
                return cursorLoader!!
            }

            override fun onLoadFinished(loader: Loader<Cursor?>, data: Cursor?) {
                try {
                    val imageFolders = ArrayList<LocalMediaFolder>()
                    val allImageFolder = LocalMediaFolder()
                    val allImages: ArrayList<com.easy.core.album.entity.LocalMedia> = ArrayList()
                    if (data != null) {
                        val count = data.count
                        if (count > 0) {
                            data.moveToFirst()
                            do {
                                val path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]))
                                // 如原图路径不存在或者路径存在但文件不存在,就结束当前循环
                                if (TextUtils.isEmpty(path) || !File(path).exists()) {
                                    continue
                                }
                                val dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]))
                                val id = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[3]))
                                //                                Uri uri = AlbumFileUtils.getFile2Uri(activity, path);
                                var uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id)
                                var duration = 0
                                if (mLocalMediaType == LocalMediaType.VALUE_TYPE_VIDEO) {
                                    duration = data.getInt(data.getColumnIndexOrThrow(VIDEO_PROJECTION[4]))
                                    uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "" + id)
                                } else {
                                    val size = data.getInt(data.getColumnIndexOrThrow(IMAGE_PROJECTION[4]))
                                    //是否大于10K
                                    if (size < 1024 * 10) {
                                        continue
                                    }
                                }
                                val image =
                                    com.easy.core.album.entity.LocalMedia(path, dateTime, duration.toLong(), mLocalMediaType)
                                image.uri = uri
                                val folder = getImageFolder(path, uri, imageFolders)
                                folder.images.add(image)
                                folder.type = mLocalMediaType
                                index++
                                folder.imageNum = folder.imageNum + 1
                                // 最近相册中  只添加最新的100条
                                if (index <= 100) {
                                    allImages.add(image)
                                    allImageFolder.type = mLocalMediaType
                                    allImageFolder.imageNum = allImageFolder.imageNum + 1
                                }
                            } while (data.moveToNext())
                            if (allImages.size > 0) {
                                sortFolder(imageFolders)
                                imageFolders.add(0, allImageFolder)
                                var title = ""
                                when (mLocalMediaType) {
                                    LocalMediaType.VALUE_TYPE_VIDEO -> title = activity.getString(R.string.lately_video)
                                    LocalMediaType.VALUE_TYPE_IMAGE -> title = activity.getString(R.string.lately_image)
                                    else -> {}
                                }
                                allImageFolder.firstImagePath = allImages[0].path
                                allImageFolder.firstImageUri = allImages[0].uri
                                allImageFolder.name = title
                                allImageFolder.type = mLocalMediaType
                                allImageFolder.images = allImages
                            }
                            imageLoadListener.loadComplete(imageFolders)
                        } else {
                            // 如果没有相册
                            imageLoadListener.loadComplete(imageFolders)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onLoaderReset(loader: Loader<Cursor?>) {}
        })
    }

    private fun sortFolder(imageFolders: List<LocalMediaFolder>) {
        // 文件夹按图片数量排序
        Collections.sort(imageFolders, java.util.Comparator { lhs, rhs ->
            val lsize = lhs.imageNum
            val rsize = rhs.imageNum
            if (lsize == rsize) 0 else if (lsize < rsize) 1 else -1
        })
    }

    private fun getImageFolder(path: String, uri: Uri, imageFolders: MutableList<LocalMediaFolder>): LocalMediaFolder {
        val imageFile = File(path)
        val folderFile = imageFile.parentFile
        var name = folderFile?.name
        if (!Album.functionOptions.chooseFolder.isEmpty()) {
            if (path.contains(Album.functionOptions.chooseFolder)) {
                name = Album.functionOptions.chooseFolder
            }
        }
        for (folder in imageFolders) {
            if (folder.name == name) {
                return folder
            }
        }
        val newFolder = LocalMediaFolder()
        newFolder.name = name
        if (folderFile != null) {
            newFolder.path = folderFile.absolutePath
        }
        newFolder.firstImagePath = path
        newFolder.firstImageUri = uri
        imageFolders.add(newFolder)
        return newFolder
    }

    interface LocalMediaLoadListener {
        fun loadComplete(folders: List<LocalMediaFolder>?)
    }

    companion object {
        private val IMAGE_PROJECTION =
            arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media._ID, MediaStore.Images.Media.SIZE)
        private val VIDEO_PROJECTION =
            arrayOf(MediaStore.Video.Media.DATA, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DATE_ADDED, MediaStore.Video.Media._ID, MediaStore.Video.Media.DURATION)

    }
}