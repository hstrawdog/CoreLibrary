package com.easy.example.ui

import android.app.Activity
import android.content.Intent
import com.easy.core.kt.open
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.ui.base.open
import com.easy.core.utils.ToastUtils
import com.easy.example.databinding.ActivityResultBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui
 * @Date : 10:39
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ResultActivity : BaseViewBindingActivity<ActivityResultBinding>() {

    override fun initView() {

        binding.textView39.text = System.identityHashCode(this).toString()
        binding.button75.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("data", "button75")
            })
            finish()
        }
        binding.button76.setOnClickListener {
            open(ResultActivity::class.java) {
                it?.data?.getStringExtra("data")?.let {
                    ToastUtils.showToast("button76:  " + it)
                }
            }
        }
    }
}