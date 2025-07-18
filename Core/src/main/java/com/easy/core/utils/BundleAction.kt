package com.easy.core.utils

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.shape.utils
 * @FileName :   BundleAction.kt
 * @Date  : 2021/6/1 0001  上午 10:46
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
interface BundleAction {
    val bundle: Bundle?

    fun getBundleInt(name: String?): Int {
        return getBundleInt(name, 0)
    }

    fun getBundleInt(name: String?, defaultValue: Int): Int {
        val bundle = bundle ?: return defaultValue
        return bundle.getInt(name, defaultValue)
    }

    fun getBundleLong(name: String?): Long {
        return getBundleLong(name, 0)
    }

    fun getBundleLong(name: String?, defaultValue: Int): Long {
        val bundle = bundle ?: return defaultValue.toLong()
        return bundle.getLong(name, defaultValue.toLong())
    }

    fun getBundleFloat(name: String?): Float {
        return getBundleFloat(name, 0)
    }

    fun getBundleFloat(name: String?, defaultValue: Int): Float {
        val bundle = bundle ?: return defaultValue.toFloat()
        return bundle.getFloat(name, defaultValue.toFloat())
    }

    fun getBundleDouble(name: String?): Double {
        return getBundleDouble(name, 0)
    }

    fun getBundleDouble(name: String?, defaultValue: Int): Double {
        val bundle = bundle ?: return defaultValue.toDouble()
        return bundle.getDouble(name, defaultValue.toDouble())
    }

    fun getBundleBoolean(name: String?): Boolean {
        return getBundleBoolean(name, false)
    }

    fun getBundleBoolean(name: String?, defaultValue: Boolean): Boolean {
        val bundle = bundle ?: return defaultValue
        return bundle.getBoolean(name, defaultValue)
    }

    fun getBundleString(name: String?): String? {
        val bundle = bundle ?: return null
        return bundle.getString(name)
    }

    fun <P : Parcelable?> getBundleParcelable(name: String?): P? {
        val bundle = bundle ?: return null
        return bundle.getParcelable(name)
    }

    fun <S : Serializable?> getBundleSerializable(name: String?): S? {
        val bundle = bundle ?: return null
        return bundle.getSerializable(name) as S?
    }

    fun getBundleStringArrayList(name: String?): ArrayList<String?>? {
        val bundle = bundle ?: return null
        return bundle.getStringArrayList(name)
    }

    fun getBundleIntegerArrayList(name: String?): ArrayList<Int?>? {
        val bundle = bundle ?: return null
        return bundle.getIntegerArrayList(name)
    }
}