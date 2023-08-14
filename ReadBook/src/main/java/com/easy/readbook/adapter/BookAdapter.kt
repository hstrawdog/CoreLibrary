package com.easy.readbook.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.core.glide.ImageLoadUtils
import com.qq.readbook.R
import com.easy.readbook.room.entity.Book

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.adapter
 * @Date  : 上午 11:25
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class BookAdapter : BaseQuickAdapter<com.easy.readbook.room.entity.Book, BaseViewHolder>(R.layout.item_book), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: com.easy.readbook.room.entity.Book) {
        holder.setText(R.id.tv_book_name, item.name)
        holder.setText(R.id.tv_book_desc, item.desc)
        holder.setText(R.id.tv_book_author, item.author)
        holder.setText(R.id.tv_book_type, item.type)
        holder.setText(R.id.tv_sources, "来源: " + item.sourceName)
        item.imgUrl?.let {
            ImageLoadUtils.withFillet(item.imgUrl, holder.getView(R.id.iv_book_img))
        }
    }
}