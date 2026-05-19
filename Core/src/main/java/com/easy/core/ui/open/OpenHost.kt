package com.easy.core.ui.open

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import java.util.ArrayDeque
import java.util.concurrent.atomic.AtomicInteger

const val INTERNAL_REQUEST_CODE = "__request_code__"

/**
 * 统一页面跳转宿主能力。
 *
 * Activity / Fragment / Dialog / Compose 宿主都通过同一套协议发起跳转并接收结果。
 */
interface OpenHost {
    val openContext: Context
    val openDelegate: OpenDelegate
}

class OpenDelegate(
    private val launcher: ActivityResultLauncher<Intent>
) {
    private val activityResultMap = mutableMapOf<Int, (ActivityResult) -> Unit>()
    private val pendingRequestCodes = ArrayDeque<Int>()
    private val requestCodeGenerator = AtomicInteger(2000)

    fun open(
        context: Context,
        cls: Class<*>,
        bundle: Bundle = Bundle(),
        callback: (ActivityResult) -> Unit = {}
    ) {
        val requestCode = requestCodeGenerator.incrementAndGet()
        activityResultMap[requestCode] = callback
        pendingRequestCodes.addLast(requestCode)
        launcher.launch(Intent(context, cls).apply {
            putExtras(bundle)
            putExtra(INTERNAL_REQUEST_CODE, requestCode)
        })
    }

    fun dispatch(result: ActivityResult) {
        val explicitRequestCode = result.data.openRequestCode()
        val requestCode = when {
            explicitRequestCode != null -> {
                pendingRequestCodes.remove(explicitRequestCode)
                explicitRequestCode
            }

            pendingRequestCodes.isNotEmpty() -> pendingRequestCodes.removeLast()
            else -> null
        }
        requestCode?.let { activityResultMap.remove(it)?.invoke(result) }
    }

    fun createResultIntent(sourceIntent: Intent?, data: Bundle = Bundle()): Intent {
        val requestCode = sourceIntent.openRequestCode()
        return Intent().apply {
            putExtras(data)
            requestCode?.let {
                putExtra(INTERNAL_REQUEST_CODE, it)
            }
        }
    }
}

fun Intent?.openRequestCode(): Int? {
    val requestCode = this?.getIntExtra(INTERNAL_REQUEST_CODE, -1) ?: -1
    return requestCode.takeIf { it != -1 }
}

fun Bundle?.openRequestCode(): Int? {
    val requestCode = this?.getInt(INTERNAL_REQUEST_CODE, -1) ?: -1
    return requestCode.takeIf { it != -1 }
}
