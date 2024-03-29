package com.easy.core.ui.base

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.easy.core.ui.base.IBanding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.binding
 * @FileName :   BaseBindingActivity
 * @Date : 2020/7/30 0030  上午 10:41
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
abstract class BaseDataBindingActivity<T : ViewDataBinding> : BaseActivity(), IBanding {

    lateinit var binding: T

    /**
     * 禁止 子类继承使用 保证走的都是getLayoutView方法
     * @return
     */
    override fun getLayoutViewId(): Int {
        return  0
    }
    /**
     * 绑定Binding
     */
    override fun getLayoutView(parent: ViewGroup): View {
        binding = DataBindingUtil.inflate<T>(layoutInflater, getLayoutId(), parent, false)
        binding.lifecycleOwner = this
        return binding.root
    }



}