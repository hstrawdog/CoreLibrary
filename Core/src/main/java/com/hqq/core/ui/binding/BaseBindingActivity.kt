package com.hqq.core.ui.binding

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.ICreateRootView.IBanding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.binding
 * @FileName :   BaseBindingActivity
 * @Date : 2020/7/30 0030  上午 10:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseBindingActivity<T : ViewDataBinding>
    : BaseActivity(), IBanding {

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