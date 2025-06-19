package com.easy.core.recycle

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.recycle
 * @Date  : 09:45
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
interface RecycleViewBannerChangeListener<Any> {
    /**
     * @param t
     * @return
     */
    fun getUrl(t: Any): String

    /**
     * @param t
     * @return
     */
    fun getTitle(t: Any): String
}
