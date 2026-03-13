package com.easy.core.adapter

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder

/**
 * @Author : huangqiqiang
 * @Package : com.core..adapter
 * @Date : 14:46
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class BaseRecycleAdapter<T : Any>(var layoutId: Int) : BaseQuickAdapter<T, QuickViewHolder>() {
    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): QuickViewHolder {
        return QuickViewHolder(layoutId, parent);

    }

}
