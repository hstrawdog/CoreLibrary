package com.easy.readbook.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.readbook.R
import com.easy.readbook.room.entity.Chapter

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.adapter
 * @Date : 上午 9:25
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ChaptersAdapter(var chapterPos: Int) : BaseQuickAdapter<com.easy.readbook.room.entity.Chapter, BaseViewHolder>(R.layout.item_chapter) {

    var showSearchPosition = -1

    override fun convert(holder: BaseViewHolder, item: com.easy.readbook.room.entity.Chapter) {
        holder.setText(R.id.tv_chapter, item.title)
        if (holder.layoutPosition == chapterPos) {
            holder.getView<TextView>(R.id.tv_chapter).isSelected = true
            holder.setTextColorRes(R.id.tv_chapter, R.color.color_red)
        } else {
            holder.getView<TextView>(R.id.tv_chapter).isSelected = false
            if (item.isCache) {
                holder.setTextColorRes(R.id.tv_chapter, R.color.color_333)
            } else {
                holder.setTextColorRes(R.id.tv_chapter, R.color.color_999)
            }
        }

        if (holder.adapterPosition == showSearchPosition) {
            holder.setBackgroundResource(R.id.ll_item, R.color.bg_color)
        } else {
            holder.setBackgroundResource(R.id.ll_item, R.color.white)
        }


    }

}