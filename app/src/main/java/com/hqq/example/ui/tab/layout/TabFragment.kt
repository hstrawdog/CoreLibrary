package com.hqq.example.ui.tab.layout

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hqq.core.ui.base.BaseFragment

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core_app.ui.activity.tab_layout
 * @FileName :   TabFragment
 * @Date : 2019/6/10 0010  上午 10:07
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class TabFragment : BaseFragment() {


    override val layoutViewId: Int
        get() = 0
    override fun getLayoutView(group: ViewGroup): View? {
        val textView = TextView(activity)
        textView.text = "viewPage Fragment"
        return textView
    }

    override fun initView() {}
}