package com.easy.core.widget.ptr

import `in`.srain.cube.views.ptr.PtrFrameLayout
import android.content.Context
import android.util.AttributeSet
 /**
  * @Author : huangqiqiang
  * @Package : com.easy.core.widget.ptr
  * @FileName :   PtrClassicFrameLayout.kt
  * @Date  : 2020/9/1 0001  下午 5:25
  * @Email :  qiqiang213@gmail.com
  * @Descrive :
  */
open class PtrClassicFrameLayout : PtrFrameLayout {
    var header: PtrClassicDefaultHeader? = null
        private set

    constructor(context: Context?) : super(context) {
        initViews()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initViews()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initViews()
    }

    private fun initViews() {
        header = PtrClassicDefaultHeader(getContext())
        setHeaderView(header)
        addPtrUIHandler(header)
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    fun setLastUpdateTimeKey(key: String?) {
        if (header != null) {
            header!!.setLastUpdateTimeKey(key)
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    fun setLastUpdateTimeRelateObject(`object`: Any) {
        if (header != null) {
            header!!.setLastUpdateTimeRelateObject(`object`)
        }
    }
}