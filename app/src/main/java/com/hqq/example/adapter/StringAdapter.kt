package com.hqq.example.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.R
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.adapter
 * @FileName :   StringAdapter
 * @Date : 2019/4/29 0029  下午 2:23
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class StringAdapter : BaseQuickAdapter<String?, BaseViewHolder>(R.layout.item_main) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tv_title, item)
    }


}