package com.easy.core.recycle.indicator

/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.banner.indicator
 * @FileName :   Indicator
 * @Date : 2019/1/5 0005
 * @Email :  qiqiang213@gmail.com
 * @Describe :  指示器接口  Indicator
 */
interface Indicator {
    /**
     * 当前选中的item position
     *
     * @param item position
     */
    fun setCurrentItem(item: Int)

    /**
     * 更新view
     */
    fun notifyDataSetChanged()

    /**
     * 总数
     *
     * @param column size
     */
    fun setPageColumn(column: Int)
}