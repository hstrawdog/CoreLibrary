package com.hqq.core.recycler.indicator;


/**
 * @Author : huangqiqiang
 * @Package : com.qi.core.banner.indicator
 * @FileName :   Indicator
 * @Date : 2019/1/5 0005
 * @Email :  qiqiang213@gmail.com
 * @Descrive :  指示器接口  Indicator
 */
public interface Indicator {

    /**
     * 当前选中的item position
     *
     * @param item position
     */
    void setCurrentItem(int item);

    /**
     * 更新view
     */
    void notifyDataSetChanged();

    /**
     * 总数
     *
     * @param column size
     */
    void setPageColumn(int column);
}
