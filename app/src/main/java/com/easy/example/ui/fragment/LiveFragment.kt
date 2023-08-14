package com.easy.example.ui.fragment

import android.os.Bundle
import com.easy.core.ui.base.BaseViewBindingFragment
import com.easy.core.utils.log.LogUtils
import com.easy.example.databinding.FragmentLiveBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.fragment
 * @Date : 10:21
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class LiveFragment : BaseViewBindingFragment<FragmentLiveBinding>() {
    override val isLazyLoad: Boolean
        get() = true
    override fun initView() {
        binding.textView31.text = arguments?.get("position").toString()
        LogUtils.e("----------------initView    ${arguments?.get("position").toString()}--------------------------------------" )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.e("----------------onCreate    ${arguments?.get("position").toString()}--------------------------------------" )
    }
    override fun onResume() {
        super.onResume()
        LogUtils.e("----------------onResume    ${arguments?.get("position").toString()}--------------------------------------" )
    }
    override fun onStop() {
        super.onStop()
        LogUtils.e("----------------onStop    ${arguments?.get("position").toString()}--------------------------------------" )
    }
    override fun onPause() {
        super.onPause()
        LogUtils.e("----------------onPause    ${arguments?.get("position").toString()}--------------------------------------" )
    }
    override fun onStart() {
        super.onStart()
        LogUtils.e("----------------onStart    ${arguments?.get("position").toString()}--------------------------------------" )
    }
    override fun onDestroy() {
        super.onDestroy()
        LogUtils.e("----------------onDestroy    ${arguments?.get("position").toString()}--------------------------------------" )
    }
}