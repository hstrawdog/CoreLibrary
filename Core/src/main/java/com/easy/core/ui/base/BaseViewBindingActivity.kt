package com.easy.core.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.easy.core.utils.log.LogUtils
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.recycle
 * @Date : 下午 3:18
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class BaseViewBindingActivity<T : ViewBinding> : BaseActivity() {

    override fun getLayoutViewId(): Int {
        return 0
    }
    lateinit var binding: T

    override fun getLayoutView(parent: ViewGroup): View? {
        val tClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        try {
            val method = tClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.javaPrimitiveType)
            binding = method.invoke(null, layoutInflater, parent, false) as T
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            LogUtils.dInfo(e)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            LogUtils.dInfo(e)
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            LogUtils.dInfo(e)
        }
        return binding?.root
    }
}