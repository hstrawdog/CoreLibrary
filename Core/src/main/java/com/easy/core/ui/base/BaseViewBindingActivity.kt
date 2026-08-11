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
        val tClass = findViewBindingClass()
        try {
            val method = tClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.javaPrimitiveType)
            binding = method.invoke(null, layoutInflater, parent, false) as T
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            LogUtils.dInfo{e}
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            LogUtils.dInfo{e}
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            LogUtils.dInfo{e}
        }
        return binding?.root
    }

    @Suppress("UNCHECKED_CAST")
    private fun findViewBindingClass(): Class<T> {
        var currentClass: Class<*> = javaClass
        while (currentClass != Any::class.java) {
            val genericSuperclass = currentClass.genericSuperclass
            if (genericSuperclass is ParameterizedType) {
                val rawClass = genericSuperclass.rawType as? Class<*> ?: break
                if (rawClass == BaseViewBindingActivity::class.java) {
                    val bindingClass = genericSuperclass.actualTypeArguments.firstOrNull() as? Class<*>
                    if (bindingClass != null && ViewBinding::class.java.isAssignableFrom(bindingClass)) {
                        return bindingClass as Class<T>
                    }
                    break
                }
                currentClass = rawClass
            } else if (genericSuperclass is Class<*>) {
                currentClass = genericSuperclass
            } else {
                break
            }
        }
        throw IllegalStateException("Unable to resolve ViewBinding type for ${javaClass.name}")
    }
}
