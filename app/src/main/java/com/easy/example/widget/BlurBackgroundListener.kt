package com.easy.example.widget

/**
 * @Author : huangqiqiang
 * @Package : com.rongji.patelf.ui.photo.listener
 * @Date : 16:14
 * @Email : qiqiang213@gmail.com
 * @Describe : 背景虚化值修改了
 */
fun interface BlurBackgroundListener {
    /**
     *
     * @param isUpdate Boolean  true  需要执行逻辑  false   只需要更新界面
     *
     */
    fun onBlurBackgroundValueListener(isUpdate:Boolean )

}