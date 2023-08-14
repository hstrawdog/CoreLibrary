package com.easy.core.ui.base

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.easy.core.permission.PermissionsResult
import com.easy.core.permission.PermissionsUtils
import com.easy.core.ui.base.BaseActivity

/**
 *  打开指定的界面
 * @receiver BaseActivity
 * @param cls Class<*>
 * @param result ActivityResultCallback<ActivityResult>
 */
fun BaseActivity.open(cls: Class<*>,
                      bundle: Bundle = Bundle(),
                      result: ActivityResultCallback<ActivityResult?> = ActivityResultCallback<ActivityResult?> { }) {
    registerForActivity.launch(Intent(this, cls).apply {
        putExtras(bundle)
    })
    activityResult.observe(this) {
        result.onActivityResult(it)
        activityResult.removeObservers(this)
    }
}

/**
 *  拍照并预览
 * @receiver BaseActivity
 * @param result ActivityResultCallback<Bitmap>
 */
fun BaseActivity.takePicturePreview(result: ActivityResultCallback<Bitmap?>) {
    PermissionsUtils.requestCamera(object : PermissionsResult {
        override fun onPermissionsResult(status: Boolean) {
            registerForActivityResult(ActivityResultContracts.TakePicturePreview(), result).launch(null)
        }
    })

}

