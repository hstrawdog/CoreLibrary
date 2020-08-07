package com.hqq.core.ui.dialog

import android.content.DialogInterface
import com.hqq.core.ui.BaseViewBuilderHolder

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.dialog
 * @FileName :   AlertParams
 * @Date : 2020/7/30 0030  下午 1:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class AlertParams {
    /**
     * 取消按钮监听
     */
    var mNegativeButtonListener: DialogInterface.OnClickListener? = null

    /**
     * 中间布局
     */
    var mBaseViewBuilderHolder: BaseViewBuilderHolder? = null

    /**
     * 取消按钮上的文字
     */
    var mNegativeButtonText = "取消"

    /**
     * 确定按钮
     */
    var mPositiveButtonText: CharSequence = "确定"

    /**
     * 确定按钮监听
     */
    var mPositiveButtonListener: DialogInterface.OnClickListener? = null

    /**
     * 顶部标题
     */
    var mTitle: CharSequence = "提示"
}