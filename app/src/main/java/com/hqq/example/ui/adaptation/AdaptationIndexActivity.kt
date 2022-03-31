package com.hqq.example.ui.adaptation

import com.hqq.core.ui.list.BaseListActivity
import com.hqq.example.adapter.MainAdapter
import com.hqq.example.bean.MainBean
import com.hqq.example.ui.screen.DimenActivity
import com.hqq.example.ui.screen.TextViewBuilderSizeActivity
import com.hqq.example.ui.skin.SkinAActivity
import com.hqq.example.ui.view.BlackAndWhiteActivity
import com.hqq.example.ui.view.SvgActivity

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui
 * @FileName :   AdaptationIndexActivity
 * @Date : 2020/1/2 0002  上午 9:35
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class AdaptationIndexActivity(override val adapter: MainAdapter= MainAdapter()) : BaseListActivity() {
    override fun initData() {
        adapter.addData(MainBean("文字适配测试", TextViewBuilderSizeActivity::class.java))
        adapter.addData(MainBean("1像素大小测试", DimenActivity::class.java))
        adapter.addData(MainBean("换肤测试", SkinAActivity::class.java))
        adapter.addData(MainBean("SVG测试", SvgActivity::class.java))
        adapter.addData(MainBean("黑白化测试", BlackAndWhiteActivity::class.java))

    }

}