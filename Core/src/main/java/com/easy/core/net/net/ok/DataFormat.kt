package com.easy.core.net.net.ok

import androidx.annotation.IntDef
import androidx.annotation.StringDef
import com.easy.core.net.net.ok.DataFormat.Companion.POST
import com.easy.core.net.net.ok.DataFormat.Companion.XML
import java.lang.annotation.RetentionPolicy

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.net.ok
 * @Date : 11:23
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
@StringDef(POST, XML)
annotation class DataFormat {
    companion object {
        const val POST: String = "post"
        const val XML: String = "xml"
    }
}