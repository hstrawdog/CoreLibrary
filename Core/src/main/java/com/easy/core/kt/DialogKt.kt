package com.easy.core.kt

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.easy.core.ui.compose.BaseComposeDialog
import com.easy.core.ui.dialog.BaseDialog
import com.easy.core.ui.open.INTERNAL_REQUEST_CODE
import com.easy.core.ui.open.openRequestCode

fun BaseDialog.setResultOk(data: Bundle = Bundle()) {
    val sourceIntent = activity?.intent ?: Intent().apply {
        arguments.openRequestCode()?.let {
            putExtra(INTERNAL_REQUEST_CODE, it)
        }
    }
    activity?.setResult(Activity.RESULT_OK, openDelegate.createResultIntent(sourceIntent, data))
}

fun BaseComposeDialog.setResultOk(data: Bundle = Bundle()) {
    val sourceIntent = activity?.intent ?: Intent().apply {
        arguments.openRequestCode()?.let {
            putExtra(INTERNAL_REQUEST_CODE, it)
        }
    }
    activity?.setResult(Activity.RESULT_OK, openDelegate.createResultIntent(sourceIntent, data))
}
