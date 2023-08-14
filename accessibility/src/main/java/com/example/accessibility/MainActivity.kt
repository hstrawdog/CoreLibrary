package com.example.accessibility

import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import android.text.TextUtils
import com.example.accessibility.databinding.ActivityMainBinding
import com.example.accessibility.kuaishou.KuaiShouJob
import com.easy.core.ui.base.BaseViewBindingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.collections.HashSet

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {
    override fun initView() {
        binding.button62.setOnClickListener {
            startService()
        }
    }

    var isFirst = true
    override fun onResume() {
        super.onResume()
        if (isFirst) {
            isFirst = false
            //startService()
        }

    }

    var kuaiShouJob = KuaiShouJob()
    private fun startService() {
        ServiceManager.openService(this) {
            // 服务开启
            if (it) {
                kuaiShouJob.start()
            }
        }
    }
}