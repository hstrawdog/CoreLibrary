package com.easy.core.kt

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import com.easy.core.ui.base.BaseActivity

/**
 *  打开指定的界面
 * @receiver BaseActivity
 * @param cls Class<*>
 * @param result ActivityResultCallback<ActivityResult>
 */
fun BaseActivity.open(cls:Class<*>, bundle:Bundle = Bundle(), result:ActivityResultCallback<ActivityResult> = ActivityResultCallback<ActivityResult> { }) {
    registerForActivity.launch(Intent(this, cls).apply {
        putExtras(bundle)
    })
    activityResult =result

}

///**
// *  拍照并预览
// * @receiver BaseActivity
// * @param result ActivityResultCallback<Bitmap>
// */
//fun BaseActivity.takePicturePreview(result: ActivityResultCallback<Bitmap?>) {
//    SysPermissionsUtils.requestCamera(  object : PermissionsResult {
//        override fun onPermissionsResult(status: Boolean) {
//            registerForActivityResult(ActivityResultContracts.TakePicturePreview(), result).launch(null)
//        }
//    })
//
//}

