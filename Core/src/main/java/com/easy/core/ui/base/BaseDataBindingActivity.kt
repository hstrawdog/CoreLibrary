package com.easy.core.ui.base

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.easy.core.ui.base.IRootView.IBanding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.binding
 * @FileName :   BaseBindingActivity
 * @Date : 2020/7/30 0030  上午 10:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseDataBindingActivity<T : ViewDataBinding> : BaseActivity(), IBanding {

    lateinit var binding: T

    /**
     * 禁止 子类继承使用 保证走的都是getLayoutView方法
     * @return
     */
    final override val layoutViewId: Int = 0

    /**
     * 绑定Binding
     */
    override fun getLayoutView(parent: ViewGroup): View {
        binding = DataBindingUtil.inflate<T>(layoutInflater, layoutId, parent, false)
        binding.lifecycleOwner = this
        return binding.root
    }



}