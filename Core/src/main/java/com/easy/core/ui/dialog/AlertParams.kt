package com.easy.core.ui.dialog

import android.content.DialogInterface
import com.easy.core.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.dialog
 * @FileName :   AlertParams
 * @Date : 2020/7/30 0030  下午 1:58
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * 仿源码Dialog Builder 构建模式
 * 需要不断补充属性
 *
 * 1. 三个按钮的文本都是空的  隐藏底部按钮  ->  如果改成 动态addView 效果会不会更理想
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
    var dialogViewBuilder: DialogViewBuilder? = null

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