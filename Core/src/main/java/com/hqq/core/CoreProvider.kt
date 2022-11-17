package com.hqq.core

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.hqq.core.utils.RegexUtils
import com.hqq.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core
 * @FileName :   CoreInitProvider
 * @Date : 2019/6/6 0006  上午 9:54
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * 利用 Provider监听 初始化
 */
class CoreProvider : ContentProvider() {
    /**
     * 默认初始化 CoreConfig
     */
    override fun onCreate(): Boolean {
        LogUtils.e4Debug(" CoreProvider  onCreate")
        if (!CoreConfig.get().isInitialized() || RegexUtils.checkIsNull(CoreConfig.get().application)) {
            CoreConfig.get().init(context as Application)
            LogUtils.e4Debug(" CoreProvider  setApplication")
        }

        return false
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }
}