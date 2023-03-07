package com.hqq.core.ui.dialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding
import com.hqq.core.R
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.dialog
 * @Date : 下午 2:05
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class BaseBindingDialog<T : ViewBinding> : BaseDialog() {
    lateinit var binding : T
    override val layoutId : Int
        get() = 0

    override fun initView() {}
    override fun initContentView() {
        val linearLayout = rootView !!.findViewById<LinearLayout>(R.id.ll_rootView)
        var view = getBindingView(linearLayout)
        linearLayout.gravity = gravity
        linearLayout.addView(view)
        view?.setOnClickListener { }
        if (isDismissBackground) {
            linearLayout.setOnClickListener { dismiss() }
        }
    }

    /**
     * 反射构建View
     * @param parent ViewGroup
     * @return View?
     */
    private fun getBindingView(parent : ViewGroup) : View? {
        val tClass = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
        try {
            val method = tClass.getMethod("inflate" , LayoutInflater::class.java , ViewGroup::class.java , Boolean::class.javaPrimitiveType)
            binding = method.invoke(null , layoutInflater , parent , false) as T
        } catch (e : NoSuchMethodException) {
            e.printStackTrace()
        } catch (e : IllegalAccessException) {
            e.printStackTrace()
        } catch (e : InvocationTargetException) {
            e.printStackTrace()
        }
        return binding.root
    }

}