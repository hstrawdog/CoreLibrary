package com.easy.example.ui.jetpack.livedata

import android.view.View
import android.view.ViewGroup
import com.easy.core.ui.base.BaseActivity
import com.easy.example.databinding.ActivityLiveDataSpanViewBinding


class LiveDataSpanViewActivity() : BaseActivity() {

    lateinit var binding: ActivityLiveDataSpanViewBinding;
    override fun getLayoutView(parent: ViewGroup): View? {
        binding = ActivityLiveDataSpanViewBinding.inflate(layoutInflater, parent, false)
        return binding.root
    }

    override fun getLayoutViewId(): Int {
        return  0
    }

    override fun initView() {
        binding.button52.setOnClickListener {
            com.easy.example.ui.jetpack.livedata.LiveUser.getInstance(activity).observe(this, {

            })

        }
    }

}