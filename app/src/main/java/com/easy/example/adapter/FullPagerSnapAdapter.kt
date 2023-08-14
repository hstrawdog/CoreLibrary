package com.easy.example.adapter

import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.adapter
 * @FileName :   FullPagerSnapAdapter
 * @Date : 2019/6/18 0018  上午 9:53
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class FullPagerSnapAdapter : BaseQuickAdapter<String?, BaseViewHolder>(R.layout.item_full_pager_snap) {
    private var mScreenWidth = 0
    var itemCountPerRowOrColumn = 3
    var mIntegers: MutableList<Int>
    override fun convert(helper: BaseViewHolder, item: String?) {
        mScreenWidth = context.resources.displayMetrics.widthPixels
        val layoutParams = helper.itemView.layoutParams as GridLayoutManager.LayoutParams
        layoutParams.width = mScreenWidth / itemCountPerRowOrColumn
        layoutParams.height = layoutParams.width * 4 / 3
        helper.itemView.layoutParams = layoutParams
        helper.itemView.setBackgroundResource(mIntegers[(Math.random() * 100 % 3).toInt()])
        helper.setText(R.id.tv_item, item)
    }

    init {
        mIntegers = ArrayList()
        mIntegers.add(R.color.colorPrimaryDark)
        mIntegers.add(R.color.colorPrimary)
        mIntegers.add(R.color.colorAccent)
    }
}