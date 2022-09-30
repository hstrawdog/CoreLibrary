package com.hqq.core.kt

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.hqq.core.ui.base.BaseFragment

/**
 * @Author : huangqiqiang
 * @Package : com.rongji.core.kt
 * @Date : 下午 1:21
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
fun BaseFragment.open(cls: Class<*>, bundle: Bundle = Bundle(), result: ActivityResultCallback<ActivityResult> = ActivityResultCallback<ActivityResult> { }) {
//    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), result).launch(Intent(requireContext(), cls).apply {
//        putExtras(bundle)
//    })

    registerForActivity.launch(Intent(requireContext(), cls).apply {
        putExtras(bundle)
    })
    activityResult.observe(this) {
        result.onActivityResult(it)
        activityResult.removeObservers(this)
    }
}
