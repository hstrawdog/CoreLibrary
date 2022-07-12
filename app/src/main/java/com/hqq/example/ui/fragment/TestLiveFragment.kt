package com.hqq.example.ui.fragment

import android.view.View
import com.hqq.core.ui.base.BaseFragment
import android.view.ViewGroup
import android.widget.TextView
import com.hqq.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.fragment
 * @Date : 下午3:18
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class TestLiveFragment : BaseFragment() {
    override val layoutViewId: Int = 0
    val textView: TextView by lazy {
        TextView(activity)
    }
    val text = System.currentTimeMillis()

    override val isLazyLoad: Boolean
        get() = true

    override fun getLayoutView(parent: ViewGroup): View {
        return textView
    }

    override fun initView() {
        textView.setText("$text")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d(" TestLiveFragment   onResume         $text ")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d(" TestLiveFragment   onPause         $text ")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        LogUtils.d(" TestLiveFragment   setUserVisibleHint      $isVisibleToUser     $text ")
    }
}