package com.easy.example.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.adapter
 * @FileName :   FullPagerSnapAdapter
 * @Date : 2019/6/18 0018  上午 9:53
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class FullPagerSnapAdapter : BaseQuickAdapter<String, QuickViewHolder>() {
    private var mScreenWidth = 0
    var itemCountPerRowOrColumn = 3
    var mIntegers: MutableList<Int>

    init {
        mIntegers = ArrayList()
        mIntegers.add(R.color.colorPrimaryDark)
        mIntegers.add(R.color.colorPrimary)
        mIntegers.add(R.color.colorAccent)
    }

    override fun onBindViewHolder(helper: QuickViewHolder, position: Int, item: String?) {
        mScreenWidth = context.resources.displayMetrics.widthPixels
        val layoutParams = helper.itemView.layoutParams as GridLayoutManager.LayoutParams
        layoutParams.width = mScreenWidth / itemCountPerRowOrColumn
        layoutParams.height = layoutParams.width * 4 / 3
        helper.itemView.layoutParams = layoutParams
        helper.itemView.setBackgroundResource(mIntegers[(Math.random() * 100 % 3).toInt()])
        helper.setText(R.id.tv_item, item)
    }


    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
        return QuickViewHolder(R.layout.item_full_pager_snap, parent)
    }
}