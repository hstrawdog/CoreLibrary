package com.easy.example.ui.adaptation

import com.easy.core.ui.list.BaseListActivity
import com.easy.example.adapter.MainAdapter
import com.easy.example.bean.MainBean
import com.easy.example.ui.screen.DimenActivity
import com.easy.example.ui.screen.TextViewBuilderSizeActivity
import com.easy.example.ui.skin.SkinAActivity
import com.easy.example.ui.soft.hide.keyboard.SoftHidKeyboardIndexActivity
import com.easy.example.ui.view.BlackAndWhiteActivity
import com.easy.example.ui.view.SvgActivity

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui
 * @FileName :   AdaptationIndexActivity
 * @Date : 2020/1/2 0002  上午 9:35
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class AdaptationIndexActivity(override val adapter: MainAdapter = MainAdapter()) :
    BaseListActivity() {
    override fun initData() {
        adapter.addData(com.easy.example.bean.MainBean("键盘适配r", SoftHidKeyboardIndexActivity::class.java))
        adapter.addData(com.easy.example.bean.MainBean("文字适配测试", com.easy.example.ui.screen.TextViewBuilderSizeActivity::class.java))
        adapter.addData(com.easy.example.bean.MainBean("1像素大小测试", com.easy.example.ui.screen.DimenActivity::class.java))
        adapter.addData(com.easy.example.bean.MainBean("换肤测试", SkinAActivity::class.java))
        adapter.addData(com.easy.example.bean.MainBean("SVG测试", com.easy.example.ui.view.SvgActivity::class.java))
        adapter.addData(com.easy.example.bean.MainBean("黑白化测试", com.easy.example.ui.view.BlackAndWhiteActivity::class.java))

    }

}