package com.easy.example.ui.bar

import com.easy.core.CoreConfig
import com.easy.core.annotation.LayoutModel
import com.easy.core.toolbar.DefToolBar
import com.easy.core.ui.base.BaseActivity
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.bar
 * @Date : 14:24
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class DefToolBarActivity : BaseActivity() {
    override fun getLayoutViewId(): Int {
        return R.layout.activity_tool_bar
    }

    override fun initConfig() {
        super.initConfig()

        CoreConfig.get().defToolBarColor=R.color.red

        rootViewImpl.apply {
            layoutMode = LayoutModel.LAYOUT_MODE_FRAME_LAYOUT
            iToolBarBuilder.apply {
                statusBarColor = R.color.transparent
                showLine = false
            }
        }

    }


    override fun initView() {

        (iToolBar as DefToolBar)?.setToolBarBg(R.color.transparent)

    }
}
