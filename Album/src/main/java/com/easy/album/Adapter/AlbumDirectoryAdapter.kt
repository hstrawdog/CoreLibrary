/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.easy.album.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.album.R
import com.easy.album.entity.LocalMedia
import com.easy.album.entity.LocalMediaFolder
import com.easy.album.utils.LoadUtils

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: cn.huangqiqiang.halbum.Adapter.AlbumDirectoryAdapter.java
 * @author: 黄其强
 * @date: 2017-05-05 16:10
</描述当前版本功能> */
class AlbumDirectoryAdapter : BaseQuickAdapter<LocalMediaFolder, BaseViewHolder>(R.layout.picture_album_folder_item) {

    fun bindFolderData(folders: ArrayList<LocalMediaFolder>) {
        setNewInstance(folders)
    }

    override fun convert(holder: BaseViewHolder, folder: LocalMediaFolder) {
        val name = folder.name
        val imageNum = folder.imageNum
        if (folder.isChecked) {
            holder.getView<TextView>(R.id.tv_img_num).visibility = View.VISIBLE
            holder.getView<TextView>(R.id.tv_img_num).text = folder.checkedNum.toString() + ""
        } else {
            holder.getView<TextView>(R.id.tv_img_num).visibility = View.INVISIBLE
        }
        LoadUtils.loadLocalMediaPath(folder.type, folder.firstImageUri, holder.getView(R.id.first_image))
        holder.getView<TextView>(R.id.image_num).text = "($imageNum)"
        holder.getView<TextView>(R.id.tv_folder_name).text = name
        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                notifyDataSetChanged()
            }
            onItemClickListener!!.onItemClick(folder.name, folder.images)
        }
    }


    private var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(folderName: String?, images: List<com.easy.album.entity.LocalMedia?>?)
    }
}