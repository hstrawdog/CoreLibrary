package com.easy.core.kt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.easy.core.ui.base.BaseFragment
import com.easy.core.ui.compose.BaseComposeFragment
import com.easy.core.ui.open.INTERNAL_REQUEST_CODE
import com.easy.core.ui.open.openRequestCode

fun BaseFragment.setResultOk(data: Bundle = Bundle()) {
    val sourceIntent = activity?.intent ?: Intent().apply {
        arguments.openRequestCode()?.let {
            putExtra(INTERNAL_REQUEST_CODE, it)
        }
    }
    activity?.setResult(AppCompatActivity.RESULT_OK, openDelegate.createResultIntent(sourceIntent, data))
}

fun BaseComposeFragment.setResultOk(data: Bundle = Bundle()) {
    val sourceIntent = activity?.intent ?: Intent().apply {
        arguments.openRequestCode()?.let {
            putExtra(INTERNAL_REQUEST_CODE, it)
        }
    }
    activity?.setResult(AppCompatActivity.RESULT_OK, openDelegate.createResultIntent(sourceIntent, data))
}
