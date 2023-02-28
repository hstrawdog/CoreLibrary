package com.hqq.example.ui

import android.app.Activity
import android.content.Intent
import com.hqq.core.databinding.ActivityRecycleViewBinding
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.ui.base.open
import com.hqq.example.R
import com.hqq.example.databinding.ActivityResultBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui
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
            open(ResultActivity::class.java)
        }
    }
}