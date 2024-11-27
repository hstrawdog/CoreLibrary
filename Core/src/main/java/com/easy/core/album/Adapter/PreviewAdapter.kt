package com.easy.core.album.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.VideoView
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.easy.core.R
import com.easy.core.album.annotation.LocalMediaType
import com.easy.core.album.entity.LocalMedia
import com.easy.core.album.utils.AlbumFileUtils
import com.easy.core.glide.ImageLoadUtils
import com.easy.core.utils.file.FileUtils

/**
 * @Author : huangqiqiang
 * @Package : com.easy.album.Adapter
 * @FileName :   PreviewAdapter
 * @Date : 2020/1/16 0016  上午 11:42
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class PreviewAdapter : BaseQuickAdapter<LocalMedia, QuickViewHolder>() {

    var call: ((position: LocalMedia) -> Unit?)? = null



    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
        return QuickViewHolder(R.layout.fragment_album_preview, parent)
    }
    override fun onBindViewHolder(viewHolder: QuickViewHolder, position: Int, localMedia: LocalMedia?) {
        localMedia?.let {
            when (localMedia.localMediaType) {
                LocalMediaType.VALUE_TYPE_IMAGE -> {
                    val url = FileUtils.getPathFromUri(context, localMedia.uri)
                    val degree = AlbumFileUtils.readPictureDegree(url)
                    //旋转图片
                    if (degree > 0) {
                        val bitmap = AlbumFileUtils.rotateBitmap(degree, BitmapFactory.decodeFile(url))
                        viewHolder.getView<SubsamplingScaleImageView>(R.id.image_item)
                            .setImage(ImageSource.bitmap(bitmap))
                    } else {
                        viewHolder.getView<SubsamplingScaleImageView>(R.id.image_item)
                            .setImage(ImageSource.uri(localMedia.uri))
                    }
//                viewHolder.getView<SubsamplingScaleImageView>(R.id.image_item)
//                    .setOnTouchListener { v, event -> //                        Toast.makeText(context,"111",1).show();
//                        true
//                    }
                }

                LocalMediaType.VALUE_TYPE_VIDEO -> {
                    viewHolder.getView<VideoView>(R.id.vv_view)
                        .setMediaController(MediaController(context))
                    viewHolder.getView<VideoView>(R.id.vv_view)
                        .setVideoURI(localMedia.uri)
                    viewHolder.getView<VideoView>(R.id.vv_view)
                        .start()
                    viewHolder.getView<VideoView>(R.id.vv_view)
                        .requestFocus()
                    viewHolder.getView<VideoView>(R.id.vv_view).visibility = View.VISIBLE
                    viewHolder.getView<ProgressBar>(R.id.pb_bar).visibility = View.GONE
                }

                LocalMediaType.VALUE_URL_IMAGE -> {
                    ImageLoadUtils.getBitmapByFail(context, localMedia.path,
                        object : ImageLoadUtils.GlideLoadBitmapCallback {
                            override fun getBitmapCallback(bitmap: Bitmap) {
                                viewHolder.getView<SubsamplingScaleImageView>(R.id.image_item)
                                    .setImage(ImageSource.bitmap(bitmap))
                            }

                            override fun onLoadFailed() {
                            }

                        })

                    viewHolder.getView<SubsamplingScaleImageView>(R.id.image_item)
                        .setOnLongClickListener {
                            call?.invoke(localMedia)
                            false
                        }
                }

                else -> {}
            }
        }
    }


}