package com.hqq.core.utils

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.utils
 * @FileName :   BundleAction.kt
 * @Date  : 2021/6/1 0001  上午 10:46
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
interface BundleAction {
    val bundle: Bundle?
    fun getInt(name: String?): Int {
        return getInt(name, 0)
    }

    fun getInt(name: String?, defaultValue: Int): Int {
        val bundle = bundle ?: return defaultValue
        return bundle.getInt(name, defaultValue)
    }

    fun getLong(name: String?): Long {
        return getLong(name, 0)
    }

    fun getLong(name: String?, defaultValue: Int): Long {
        val bundle = bundle ?: return defaultValue.toLong()
        return bundle.getLong(name, defaultValue.toLong())
    }

    fun getFloat(name: String?): Float {
        return getFloat(name, 0)
    }

    fun getFloat(name: String?, defaultValue: Int): Float {
        val bundle = bundle ?: return defaultValue.toFloat()
        return bundle.getFloat(name, defaultValue.toFloat())
    }

    fun getDouble(name: String?): Double {
        return getDouble(name, 0)
    }

    fun getDouble(name: String?, defaultValue: Int): Double {
        val bundle = bundle ?: return defaultValue.toDouble()
        return bundle.getDouble(name, defaultValue.toDouble())
    }

    fun getBoolean(name: String?): Boolean {
        return getBoolean(name, false)
    }

    fun getBoolean(name: String?, defaultValue: Boolean): Boolean {
        val bundle = bundle ?: return defaultValue
        return bundle.getBoolean(name, defaultValue)
    }

    fun getString(name: String?): String? {
        val bundle = bundle ?: return null
        return bundle.getString(name)
    }

    fun <P : Parcelable?> getParcelable(name: String?): P? {
        val bundle = bundle ?: return null
        return bundle.getParcelable(name)
    }

    fun <S : Serializable?> getSerializable(name: String?): S? {
        val bundle = bundle ?: return null
        return bundle.getSerializable(name) as S?
    }

    fun getStringArrayList(name: String?): ArrayList<String?>? {
        val bundle = bundle ?: return null
        return bundle.getStringArrayList(name)
    }

    fun getIntegerArrayList(name: String?): ArrayList<Int?>? {
        val bundle = bundle ?: return null
        return bundle.getIntegerArrayList(name)
    }
}