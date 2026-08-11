package com.easy.core.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.easy.core.utils.log.LogUtils
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import java.lang.reflect.WildcardType

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
        val tClass = resolveViewBindingClass(javaClass)
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

}

internal fun resolveViewBindingClass(startClass: Class<*>): Class<out ViewBinding> {
    var currentClass = startClass
    val resolvedTypes = mutableMapOf<TypeVariable<*>, Type>()
    while (currentClass != Any::class.java) {
        val genericSuperclass = currentClass.genericSuperclass
        if (genericSuperclass is ParameterizedType) {
            val rawClass = genericSuperclass.rawType as? Class<*> ?: break
            rawClass.typeParameters.zip(genericSuperclass.actualTypeArguments).forEach { (typeVariable, type) ->
                resolvedTypes[typeVariable] = resolveType(type, resolvedTypes)
            }
            if (rawClass == BaseViewBindingActivity::class.java) {
                val bindingClass = genericSuperclass.actualTypeArguments.firstOrNull()
                    ?.let { resolveType(it, resolvedTypes) }
                    .let(::getRawClass)
                if (bindingClass != null && ViewBinding::class.java.isAssignableFrom(bindingClass)) {
                    @Suppress("UNCHECKED_CAST")
                    return bindingClass as Class<out ViewBinding>
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
    throw IllegalStateException("Unable to resolve ViewBinding type for ${startClass.name}")
}

private fun resolveType(type: Type, resolvedTypes: Map<TypeVariable<*>, Type>): Type {
    var resolvedType = type
    val visitedTypes = mutableSetOf<Type>()
    while (resolvedType is TypeVariable<*> && visitedTypes.add(resolvedType)) {
        resolvedType = resolvedTypes[resolvedType] ?: break
    }
    return resolvedType
}

private fun getRawClass(type: Type?): Class<*>? = when (type) {
    is Class<*> -> type
    is ParameterizedType -> type.rawType as? Class<*>
    is WildcardType -> getRawClass(type.upperBounds.firstOrNull())
    else -> null
}
