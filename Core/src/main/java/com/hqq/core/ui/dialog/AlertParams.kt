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
 * 防源码Dialog的构建方式
 * 需要不断补充属性
 */
class AlertParams {
    /**
     * 顶部标题
     */
    var title: CharSequence = "提示"

    /**
     *  标题字体大小
     */
    var titleFontSize: Float = 50f

    /**
     *  中间内容或者提示
     */
    var content: CharSequence = ""


    /**
     * 中间布局
     */
    var baseViewBuilderHolder: BaseViewBuilderHolder? = null

    /**
     * 取消按钮上的文字
     */
    var negativeButtonText = "取消"

    /**
     * 取消按钮监听
     */
    var negativeButtonListener: DialogInterface.OnClickListener? = null

    /**
     * 确定按钮
     */
    var positiveButtonText: CharSequence = "确定"

    /**
     * 确定按钮监听
     */
    var positiveButtonListener: DialogInterface.OnClickListener? = null

    /**
     * 中立按钮
     */
    var neutralButtonText: CharSequence = ""

    /**
     *  中立按钮监听
     */
    var neutralButtonListener: DialogInterface.OnClickListener? = null


    /**
     * 分割线
     */
    var showDividingLine: Boolean = true

    /**
     *  分割线颜色
     */
    var dividingLineColor: Int = R.color.color_line_gray

    /**
     *  点击空白位置 关闭dialog
     */
    var isDismissBackground = true

    /**
     *  屏蔽返回键
     */
    var shieldReturn = false
}