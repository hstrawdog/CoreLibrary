package com.easy.example.ui.tab.layout

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.easy.core.ui.base.BaseFragment

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core_app.ui.activity.tab_layout
 * @FileName :   TabFragment
 * @Date : 2019/6/10 0010  上午 10:07
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class TabFragment : BaseFragment() {


    override fun getLayoutView(parent: ViewGroup): View? {
        val textView = TextView(activity)
        textView.text = "viewPage Fragment"
        return textView
    }

    override fun getLayoutViewId(): Int {
        return 0
    }

    override fun initView() {}
}