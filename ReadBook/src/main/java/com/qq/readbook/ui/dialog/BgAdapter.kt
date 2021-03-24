package com.qq.readbook.ui.dialog

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.qq.readbook.R
import com.qq.readbook.weight.page.ReadSettingManager

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.ui.dialog
 * @Date : 下午 3:08
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class BgAdapter : BaseQuickAdapter<Int?, BaseViewHolder>(R.layout.item_bg) {
    override fun convert(baseViewHolder: BaseViewHolder, i: Int?) {
        baseViewHolder.setBackgroundResource(R.id.view_bg_view, i!!)
        if (ReadSettingManager.getInstance().pageStyle.bgColor == i) {
            baseViewHolder.setGone(R.id.iv_checked, false)
        } else {
            baseViewHolder.setGone(R.id.iv_checked, true)
        }
    }

    init {
        addData(R.color.read_bg_one)
        addData(R.color.read_bg_two)
        addData(R.color.read_bg_four)
        addData(R.color.read_bg_five)
    }
}