package com.easy.core.ui.base

import com.easy.core.ui.base.BaseViewModel.OpenActivityComponent

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.builder
 * @FileName :   IOpenActivity
 * @Date : 2020/7/31 0031  下午 3:57
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
interface IOpenActivity {
    /**
     * 暴露出接口 打开Activity
     * Fragment  有可能需要把onActivityResult 回调给Activity
     *
     * @param openActivityComponent
     */
    fun openActivity(openActivityComponent: OpenActivityComponent)
}

interface IFinishActivity {
    /**
     *  返回上一页/ 关闭当前页面
     */
    fun finishActivity(goBackComponent: BaseViewModel.GoBackComponent)
}