package com.easy.core.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.base
 * @Date  : 下午 4:35
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */

abstract class BaseViewBindingFragment<T : ViewBinding> : BaseFragment() {
    override val layoutViewId: Int
        get() = 0

    lateinit var binding: T
    override fun getLayoutView(parent: ViewGroup): View? {
        val tClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        try {
            val method = tClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.javaPrimitiveType)
            binding = method.invoke(null, layoutInflater, parent, false) as T
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return binding?.root
    }

}