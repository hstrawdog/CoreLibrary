package com.hqq.example.ui.jetpack.livedata

import android.view.View
import android.view.ViewGroup
import com.hqq.core.ui.base.BaseActivity
import com.hqq.example.databinding.ActivityLiveDataSpanViewBinding

class LiveDataSpanViewActivity(override val layoutViewId: Int = 0) : BaseActivity() {


    lateinit var binding: ActivityLiveDataSpanViewBinding;

    override fun getLayoutView(parent: ViewGroup): View? {
        binding = ActivityLiveDataSpanViewBinding.inflate(layoutInflater, parent, false)
        return binding.root
    }

    override fun initView() {

        binding.button52.setOnClickListener {
            LiveUser.getInstance(activity).observe(this, {

            })

        }
    }

}