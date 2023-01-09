package com.hqq.example.ui.compose

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hqq.core.ui.base.BaseActivity

class ComposeIndexActivity : BaseActivity() {
    override val layoutViewId:Int
        get() = 0

    override fun getLayoutView(parent:ViewGroup):View? {
        return  TextView(activity)
    }

    override fun initView() {}
}