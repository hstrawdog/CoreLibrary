package com.easy.core.album.Adapter

import android.content.Context
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.easy.core.R
import com.easy.core.album.annotation.LocalMediaType
import com.easy.core.album.common.SelectOptions
import com.easy.core.album.dialog.OptAnimationLoader
import com.easy.core.album.entity.LocalMedia
import com.easy.core.album.utils.AlbumUtils
import com.easy.core.album.utils.LoadUtils

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.album.Adapter
 * @FileName :   AlbumDetailAdapter
 * @Date : 2017/5/7 0001  上午 11:08
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class AlbumDetailAdapter(private val maxSelectNum: Int) : BaseQuickAdapter<LocalMedia, QuickViewHolder>() {
    private var selectImages: MutableList<LocalMedia> = ArrayList()
    private var imageSelectChangedListener: OnPhotoSelectChangedListener? =
        null

    fun bindImagesData(images: List<LocalMedia>) {
        submitList(images.toMutableList())
        selectImages = SelectOptions.instance.selectLocalMedia
    }


    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {

        return QuickViewHolder(R.layout.picture_image_grid_item, parent)
    }

    override fun onBindViewHolder(baseViewHolder: QuickViewHolder, position: Int, localMedia: LocalMedia?) {

        localMedia?.let {
            localMedia?.position = baseViewHolder.position
            selectImage(baseViewHolder, isSelected(localMedia), false)
            LoadUtils.loadLocalMediaPath(localMedia.localMediaType, localMedia.uri,
                baseViewHolder.getView(R.id.iv_picture))
            if (localMedia.localMediaType == LocalMediaType.VALUE_TYPE_VIDEO) {
                baseViewHolder.getView<View>(R.id.rl_duration).visibility = View.VISIBLE
                baseViewHolder.getView<TextView>(R.id.tv_duration).text = "时长: " + formatDuring(localMedia.duration)
            } else {
                baseViewHolder.getView<View>(R.id.rl_duration).visibility = View.GONE
            }
            bindClick(baseViewHolder.position, baseViewHolder, localMedia)
        }
    }

    /**
     * 绑定 点击事件
     *
     * @param position
     * @param contentHolder
     * @param localMedia
     */
    private fun bindClick(position: Int, contentHolder: QuickViewHolder, localMedia: LocalMedia) {
        contentHolder.getView<ImageView>(R.id.check)
            .setOnClickListener {
                if (!AlbumUtils.isFastDoubleClick(200)) {
                    changeCheckboxState(contentHolder, localMedia)
                }
            }
        contentHolder.itemView.setOnClickListener {
            if (imageSelectChangedListener != null) {
                imageSelectChangedListener!!.onPictureClick(localMedia, position)
            } else {
                if (!AlbumUtils.isFastDoubleClick) {
                    changeCheckboxState(contentHolder, localMedia)
                }
            }
        }
    }

    fun isSelected(image: LocalMedia): Boolean {
        for (media in selectImages) {
            if (media.path == image.path) {
                return true
            }
        }
        return false
    }

    /**
     * 设置图片点击效果
     *
     * @param holder
     * @param isChecked
     * @param isAnim
     */
    fun selectImage(holder: QuickViewHolder, isChecked: Boolean, isAnim: Boolean) {

        holder.getView<ImageView>(R.id.check).isSelected = isChecked
        if (isChecked) {
            if (isAnim) {
                val animation = OptAnimationLoader.loadAnimation(context, R.anim.modal_in)
                holder.getView<ImageView>(R.id.check)
                    .startAnimation(animation)
            }
            holder.getView<ImageView>(R.id.iv_picture)
                .setColorFilter(ContextCompat.getColor(context, R.color.image_overlay2), PorterDuff.Mode.SRC_ATOP)
        } else {
            holder.getView<ImageView>(R.id.iv_picture)
                .setColorFilter(ContextCompat.getColor(context, R.color.image_overlay), PorterDuff.Mode.SRC_ATOP)
        }
    }

    /**
     * 改变图片选中状态
     *
     * @param contentHolder
     * @param image
     */
    private fun changeCheckboxState(contentHolder: QuickViewHolder, image: LocalMedia) {
        val isChecked = contentHolder.getView<ImageView>(R.id.check).isSelected
        if (selectImages.size >= maxSelectNum && !isChecked) {
            Toast.makeText(context, context.getString(R.string.picture_message_max_num, maxSelectNum.toString() + ""),
                Toast.LENGTH_LONG)
                .show()
            return
        }
        if (isChecked) {
            for (media in selectImages) {
                if (media.path == image.path) {
                    selectImages.remove(media)
                    subSelectPosition()
                    break
                }
            }
        } else {
            selectImages.add(image)
        }
        //通知点击项发生了改变
        //notifyItemChanged(contentHolder.getAdapterPosition());
        selectImage(contentHolder, !isChecked, true)
        if (imageSelectChangedListener != null) {
            imageSelectChangedListener!!.onChange(selectImages)
        }
    }

    /**
     * 更新选择的顺序
     */
    private fun subSelectPosition() {
        // if (is_checked_num) {
        var index = 0
        val len = selectImages.size
        while (index < len) {
            val media = selectImages[index]
            index++
        }
    }

    interface OnPhotoSelectChangedListener {
        fun onTakePhoto()
        fun onChange(selectImages: List<LocalMedia>?)
        fun onPictureClick(media: LocalMedia?, position: Int)
    }

    fun setOnPhotoSelectChangedListener(imageSelectChangedListener: OnPhotoSelectChangedListener?) {
        this.imageSelectChangedListener = imageSelectChangedListener
    }

    companion object {
        fun formatDuring(mss: Long): String {
            val hours = mss % (1000 * 60 * 60 * 24) / (1000 * 60 * 60)
            val minutes = mss % (1000 * 60 * 60) / (1000 * 60)
            val seconds = mss % (1000 * 60) / 1000
            return "$hours:$minutes $seconds"
        }
    }


}