package com.hqq.album.Adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.VideoView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.davemorrissey.labs.subscaleview.ImageSource
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.hqq.album.R
import com.hqq.album.annotation.LocalMediaType
import com.hqq.album.entity.LocalMedia
import com.hqq.album.utils.AlbumFileUtils
import com.hqq.core.glide.ImageLoadUtils

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.album.Adapter
 * @FileName :   PreviewAdapter
 * @Date : 2020/1/16 0016  上午 11:42
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class PreviewAdapter : BaseQuickAdapter<LocalMedia, BaseViewHolder>(R.layout.fragment_album_preview) {

    var call: ((position: LocalMedia) -> Unit?)? = null


    override fun convert(viewHolder: BaseViewHolder, localMedia: LocalMedia) {
        when (localMedia.localMediaType) {
            LocalMediaType.VALUE_TYPE_IMAGE -> {
                val url = getPathFromUri(context, localMedia.uri)
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
                ImageLoadUtils.getBitmapByFail(context, localMedia.path, object :
                    ImageLoadUtils.GlideLoadBitmapCallback {
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