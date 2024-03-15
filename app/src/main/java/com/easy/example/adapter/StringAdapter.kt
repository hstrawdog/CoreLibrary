package com.easy.example.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.easy.core.utils.log.LogUtils
import com.easy.example.R
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.adapter
 * @FileName :   StringAdapter
 * @Date : 2019/4/29 0029  下午 2:23
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class StringAdapter : BaseQuickAdapter<String?, BaseViewHolder>(R.layout.item_main) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tv_title, item)
    }


}