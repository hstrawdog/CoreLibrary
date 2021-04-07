package com.hqq.core.ui.base

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.hqq.core.permission.PermissionsResult
import com.hqq.core.permission.PermissionsUtils

/**
 *  打开指定的界面
 * @receiver BaseActivity
 * @param cls Class<*>
 * @param result ActivityResultCallback<ActivityResult>
 */
fun BaseActivity.open(cls: Class<*>, bundle: Bundle= Bundle(), result: ActivityResultCallback<ActivityResult> = ActivityResultCallback<ActivityResult> { }) {
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), result).launch(Intent(this, cls).apply {
        putExtras(bundle)
    })
}

/**
 *  拍照并预览
 * @receiver BaseActivity
 * @param result ActivityResultCallback<Bitmap>
 */
fun BaseActivity.takePicturePreview(result: ActivityResultCallback<Bitmap>) {
    PermissionsUtils.requestCamera(object : PermissionsResult {
        override fun onPermissionsResult(status: Boolean) {
            registerForActivityResult(ActivityResultContracts.TakePicturePreview(), result).launch(null)
        }
    })

}
