package com.hqq.core.adapter

import android.view.View
import androidx.annotation.Keep
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @Author : huangqiqiang
 * @Package : com.core.sellercenter.adapter
 * @FileName :   BaseRcViewHolder
 * @Date : 2018/11/30 0030  下午 1:18
 * @Descrive : 用于RecycleView 的嵌套 Adapter也可以复用
 * @Email :  qiqiang213@gmail.com
 */
@Keep
class BaseRcViewHolder<T : BaseQuickAdapter<*, *>?>(view: View?) : BaseViewHolder(view) {
    var baseQuickAdapter: BaseQuickAdapter<*, *>? = null

}