/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.easy.core.album.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.easy.core.R
import com.easy.core.album.entity.LocalMediaFolder
import com.easy.core.album.utils.LoadUtils

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: cn.huangqiqiang.halbum.Adapter.AlbumDirectoryAdapter.java
 * @author: 黄其强
 * @date: 2017-05-05 16:10
</描述当前版本功能> */
class AlbumDirectoryAdapter : BaseQuickAdapter<LocalMediaFolder, QuickViewHolder>() {

    fun bindFolderData(folders: ArrayList<LocalMediaFolder>) {
        submitList(folders)
    }




    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
        return QuickViewHolder(R.layout.picture_album_folder_item, parent)
    }

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, folder: LocalMediaFolder?) {

        folder?.let {

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
    }


    private var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(folderName: String?, images: List<com.easy.core.album.entity.LocalMedia?>?)
    }


}