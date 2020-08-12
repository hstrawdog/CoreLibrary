package com.hqq.core.ui.binding

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.builder.ICreateRootView.IBanding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.binding
 * @FileName :   BaseBindingActivity
 * @Date : 2020/7/30 0030  上午 10:41
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseBindingActivity<T : ViewDataBinding?>
    : BaseActivity(), IBanding {
    var mBinding: T? = null
        get
        set

    /**
     * 禁止 子类继承使用 保证走的都是getLayoutView方法
     *
     * @return
     */
    override val mLayoutViewId: Int
        get() = 0

    override fun getLayoutView(parent: ViewGroup): View {
        mBinding = DataBindingUtil.inflate<T>(layoutInflater, layoutId, parent, false)
        mBinding!!.lifecycleOwner = this
        return mBinding!!.root
    }
}