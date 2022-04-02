package com.hqq.example.ui.fragment

import android.os.Bundle
import com.hqq.core.ui.base.BaseFragment
import com.hqq.core.ui.base.BaseViewBindingFragment
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.R
import com.hqq.example.databinding.FragmentLiveBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.fragment
 * @Date : 10:21
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class LiveFragment : BaseViewBindingFragment<FragmentLiveBinding>() {

    override fun initView() {

        binding.textView31.text = arguments?.get("position").toString()
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