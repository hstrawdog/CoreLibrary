package com.hqq.core.listenner

/**
 * @Author : huangqiqiang
 * @Package : com.core..dialog
 * @FileName :   DialogClickListener
 * @Date : 2018/7/5 0005  下午 1:58
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
interface DialogClickListener<T> {
    /**
     * 传递
     * @param t  点击
     */
    fun onClick(t: T)
}