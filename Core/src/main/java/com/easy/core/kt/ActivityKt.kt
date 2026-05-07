package com.easy.core.kt

import android.app.Activity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.compose.BaseComposeActivity
import com.easy.core.ui.open.OpenHost

/**
 * 打开指定页面，并在原发起位置接收结果回调。
 */
fun OpenHost.open(cls: Class<*>, bundle: Bundle = Bundle(), callback: (ActivityResult) -> Unit = {}) {
    openDelegate.open(openContext, cls, bundle, callback)
}

fun BaseActivity.setResultOk(data: Bundle = Bundle()) {
    setResult(Activity.RESULT_OK, openDelegate.createResultIntent(intent, data))
}

fun BaseComposeActivity.setResultOk(data: Bundle = Bundle()) {
    setResult(Activity.RESULT_OK, openDelegate.createResultIntent(intent, data))
}
