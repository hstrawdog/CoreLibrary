package com.hqq.core.ui.binding

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hqq.core.ui.base.BaseFragment
import com.hqq.core.ui.builder.ICreateRootView.IBanding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.binding
 * @FileName :   BaseBindingFragment
 * @Date : 2020/7/30 0030  上午 10:54
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseBindingFragment<T : ViewDataBinding?> : BaseFragment(), IBanding {
    protected var mBinding: T? = null
    override val mLayoutViewId: Int
        get() = 0

    override fun getLayoutView(parent: ViewGroup): View {
        mBinding = DataBindingUtil.inflate<T>(layoutInflater, layoutId, parent, false)
        mBinding!!.lifecycleOwner = this
        return mBinding!!.root
    }
}