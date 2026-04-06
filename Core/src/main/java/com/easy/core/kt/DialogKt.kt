package com.easy.core.kt

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import com.easy.core.ui.compose.BaseComposeDialog
import com.easy.core.ui.dialog.BaseDialog

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.kt
 * @Date  : 19:03
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
fun BaseDialog.open(
    cls:Class<*>,
    bundle:Bundle = Bundle(),
    result:(ActivityResult) -> Unit = {},
) {
    activityResult.observe(this) {
        result(it)
        activityResult.removeObservers(this)
    }
    registerForActivity.launch(Intent(activity, cls).apply {
        putExtras(bundle)
    })
}

/**
 * 在 Compose Dialog 中打开新的 Activity，并通过 callback 接收返回结果。
 */
fun BaseComposeDialog.open(
    cls:Class<*>,
    bundle:Bundle = Bundle(),
    callback:(ActivityResult) -> Unit = {},
) {
    val requestCode = requestCodeGenerator.incrementAndGet()
    activityResultMap[requestCode] = callback
    registerForActivity.launch(Intent(requireContext(), cls).apply {
        putExtras(bundle)
        putExtra("__request_code__", requestCode)
    })
}
