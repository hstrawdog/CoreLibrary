package com.example.accessibility

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.accessibility.databinding.ActivityMainBinding
import com.hqq.core.ui.base.BaseViewBindingActivity

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
            startService()
        }

    }

    var kuaiShouJob = KuaiShouJob()
    private fun startService() {
        ServiceManager.openService(this) {
            // 服务开启
            kuaiShouJob.start()
        }
    }
}