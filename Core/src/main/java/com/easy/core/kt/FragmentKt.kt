package com.easy.core.kt

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.appcompat.app.AppCompatActivity
import com.easy.core.ui.compose.BaseComposeFragment
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.base.BaseFragment

/**
 * @Author : huangqiqiang
 * @Package : com.rongji.core.kt
 * @Date : 下午 1:21
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
fun BaseFragment.open(cls:Class<*>, bundle:Bundle = Bundle(), callback:(ActivityResult) -> Unit = {}) {
    val requestCode = requestCodeGenerator.incrementAndGet()
    activityResultMap[requestCode] = callback

    registerForActivity.launch(Intent(requireContext(), cls).apply {
        putExtras(bundle)
        putExtra("__request_code__", requestCode)
    })
}

fun BaseFragment.setResultOk(data:Bundle = Bundle()) {
    val code = arguments?.getInt("__request_code__", -1) ?: -1
    val intent = Intent().apply {
        putExtras(data)
        putExtra("__request_code__", code)
    }
    activity?.setResult(AppCompatActivity.RESULT_OK, intent)
}

/**
 * 在 Compose Fragment 中打开新的 Activity，并通过 callback 接收返回结果。
 */
fun BaseComposeFragment.open(cls:Class<*>, bundle:Bundle = Bundle(), callback:(ActivityResult) -> Unit = {}) {
    val requestCode = requestCodeGenerator.incrementAndGet()
    activityResultMap[requestCode] = callback

    registerForActivity.launch(Intent(requireContext(), cls).apply {
        putExtras(bundle)
        putExtra("__request_code__", requestCode)
    })
}

/**
 * 将 Compose Fragment 的处理结果回传给宿主 Activity。
 */
fun BaseComposeFragment.setResultOk(data:Bundle = Bundle()) {
    val code = arguments?.getInt("__request_code__", -1) ?: -1
    val intent = Intent().apply {
        putExtras(data)
        putExtra("__request_code__", code)
    }
    activity?.setResult(AppCompatActivity.RESULT_OK, intent)
}
