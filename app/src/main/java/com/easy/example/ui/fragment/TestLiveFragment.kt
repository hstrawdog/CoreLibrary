package com.easy.example.ui.fragment

import android.view.View
import com.easy.core.ui.base.BaseFragment
import android.view.ViewGroup
import android.widget.TextView
import com.easy.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.fragment
 * @Date : 下午3:18
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class TestLiveFragment : BaseFragment() {
    override fun getLayoutViewId(): Int {
        return 0
    }
    val textView: TextView by lazy {
        TextView(activity)
    }
    val text = System.currentTimeMillis()
    override fun isLazyLoad(): Boolean {
        return true
    }

    override fun getLayoutView(parent: ViewGroup): View {
        return textView
    }

    override fun initView() {
        textView.setText("$text")
        LogUtils.d(" TestLiveFragment   onResume         $text ")
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