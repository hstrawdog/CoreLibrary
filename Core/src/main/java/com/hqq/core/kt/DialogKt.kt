package com.hqq.core.kt

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import com.hqq.core.ui.dialog.BaseDialog

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.kt
 * @Date  : 19:03
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
fun BaseDialog.open(
    cls:Class<*>,
    bundle:Bundle = Bundle(),
    result:ActivityResultCallback<ActivityResult?> = ActivityResultCallback<ActivityResult?> { },
) {
    registerForActivity.launch(Intent(activity, cls).apply {
        putExtras(bundle)
    })
    activityResult.observe(this) {
        result.onActivityResult(it)
        activityResult.removeObservers(this)
    }
}
