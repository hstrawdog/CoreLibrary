package com.hqq.example.accessibility

import android.content.ComponentName
import android.content.Intent
import com.example.accessibility.ServiceManager
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.example.R
import com.hqq.example.databinding.ActivityTestAccessibilityBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.accessibility
 * @Date : 11:30
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class TestAccessibilityActivity : BaseViewBindingActivity<ActivityTestAccessibilityBinding>() {
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

    private fun startService() {
        if (ServiceManager.isServiceEnabled(this)) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.action = Intent.ACTION_MAIN
            intent.component = ComponentName("com.box.art", "com.ibox.nft.app.IBoxLauncherActivity")
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        } else {
            ServiceManager.openService(activity) {
                if (it) {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.action = Intent.ACTION_MAIN
                    intent.component = ComponentName("com.box.art", "com.ibox.nft.app.IBoxLauncherActivity")
                    intent.addCategory(Intent.CATEGORY_LAUNCHER)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            }
        }
    }
}