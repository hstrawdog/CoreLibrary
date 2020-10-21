package com.hqq.core.ui.dialog

import android.content.DialogInterface
import com.hqq.core.R
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
    var negativeButtonListener: DialogInterface.OnClickListener? = null

    /**
     * 中间布局
     */
    var baseViewBuilderHolder: BaseViewBuilderHolder? = null

    /**
     * 取消按钮上的文字
     */
    var negativeButtonText = "取消"

    /**
     * 确定按钮
     */
    var positiveButtonText: CharSequence = "确定"

    /**
     * 确定按钮监听
     */
    var positiveButtonListener: DialogInterface.OnClickListener? = null

    /**
     * 顶部标题
     */
    var title: CharSequence = "提示"

    /**
     *  标题字体大小
     */
    var titleFontSize: Float = 0f

    /**
     *  中间内容或者提示
     */
    var content: CharSequence = ""

    /**
     * 分割线
     */
    var showDividingLine: Boolean = true

    /**
     *  分割线颜色
     */
    var dividingLineColor: Int = R.color.color_line_gray


}