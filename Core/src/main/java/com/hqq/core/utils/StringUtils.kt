package com.hqq.core.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.hqq.core.CoreConfig

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.utils
 * @Date : 下午 3:54
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
object StringUtils {
    /**
     * 月日时分秒，0-9前补0
     */
    @JvmStatic
    fun fillZero(number: Int): String {
        return if (number < 10) "0$number" else "" + number
    }

    /**
     * 复制内容到剪贴板
     *
     * @param content
     * @param context
     */
    @JvmOverloads
    fun copyContentToClipboard(content: String?, context: Context = CoreConfig.applicationContext) {
        //获取剪贴板管理器：
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        val mClipData = ClipData.newPlainText("Label", content)
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData)
    }
}