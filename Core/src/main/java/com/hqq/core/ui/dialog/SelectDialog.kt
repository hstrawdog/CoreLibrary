package com.hqq.core.ui.dialog

import android.content.DialogInterface
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.hqq.core.R
import com.hqq.core.ui.BaseViewBuilderHolder
import com.hqq.core.utils.ResourcesUtils

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseSelectDialog
 * @Date : 2019/10/31 0031  上午 11:12
 * @Email : qiqiang213@gmail.com
 * @Descrive : 选择弹窗
 *  使用 AlertParams Builder 进行构建
 */
class SelectDialog<T : BaseViewBuilderHolder?> : BaseDialog(), DialogInterface, DialogInterface.OnKeyListener {
    /**
     *  属性对象
     */
    var alertParams: AlertParams? = null

    /**
     *  viewHolder
     */
    private val _viewHolder: T? = alertParams?.baseViewBuilderHolder as? T

    /**
     *  布局ID
     */
    override val layoutId: Int = R.layout.dialog_base_select_dialog

    /**
     *  动画
     */
    override val animation: Int = R.style.dialogAnimation_fade_in2fade_out

    /**
     *  宽度
     */
    override val weight: Int = WindowManager.LayoutParams.MATCH_PARENT
    override val isDismissBackground: Boolean
        get() {
            alertParams?.let {
                return it.isDismissBackground
            }
            return true
        }

    /**
     *  初始化
     */
    override fun initView() {
        alertParams?.let {
            initAlertParams()
        }
        _viewHolder?.apply {
            builder(this@SelectDialog, rootView!!.findViewById(R.id.ll_content))
            addToParent()
            initView()
        }
    }

    /**
     * builder 对象处理
     */
    private fun initAlertParams() {
        rootView?.let {
            // 左边按钮 取消
            it.findViewById<TextView>(R.id.tv_cancel)?.let {
                it.text = alertParams?.negativeButtonText
                it.setOnClickListener {
                    if (alertParams?.negativeButtonListener != null) {
                        alertParams?.negativeButtonListener?.onClick(this@SelectDialog, DialogInterface.BUTTON_NEGATIVE)
                    } else {
                        // 没有实现事件回调
                        dismiss()
                    }
                }
            }
            // 右边按钮 确认
            it.findViewById<TextView>(R.id.tv_determine)?.let {
                it.text = alertParams?.positiveButtonText
                it.setOnClickListener {
                    if (alertParams?.positiveButtonListener != null) {
                        alertParams?.positiveButtonListener?.onClick(this@SelectDialog, DialogInterface.BUTTON_POSITIVE)
                    }
                }
            }
            // 提示
            it.findViewById<TextView>(R.id.tv_title)?.apply {
                alertParams?.let {
                    this.text = it.title
                    // 标题大小
                    this.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.titleFontSize)
                }
            }

        }
        // 内容
        alertParams?.content?.let {
            if (alertParams?.baseViewBuilderHolder == null && it.isNotEmpty()) {
                var tv = TextView(activity)
                tv.gravity = Gravity.CENTER
                tv.text = it
                tv.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT)
                rootView?.findViewById<LinearLayout>(R.id.ll_content)?.apply {
                    addView(tv)
                    setPadding(0, 0, 0, ResourcesUtils.getDimen(R.dimen.x20).toInt())
                }
            }
        }
        if (alertParams?.shieldReturn == true) {
            dialog?.setOnKeyListener(this)
        }
    }

    /**
     *  重写返回  关闭对话框
     */
    override fun cancel() {
        dismiss()
    }

    /**
     *
     * @param dialog DialogInterface
     * @param keyCode Int
     * @param event KeyEvent
     * @return Boolean
     */
    override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
        return keyCode == KeyEvent.KEYCODE_BACK && alertParams?.shieldReturn == true
    }

    /**
     * Builder 对象 构建 SelectDialog
     */
    class Builder {

        /**
         *  属性池
         */
        private var alertParams: AlertParams = AlertParams()

        /**
         * 构建
         *
         * @return
         */
        fun create(): SelectDialog<*> {
            val baseSelectDialog: SelectDialog<*> = SelectDialog<BaseViewBuilderHolder>()
            baseSelectDialog.alertParams = alertParams
            return baseSelectDialog
        }

        /**
         * 设置中间布局  采用ViewHolder  方式构建
         *
         * @param baseViewBuilderHolder
         * @return
         */
        fun setBaseViewBuilderHolder(baseViewBuilderHolder: BaseViewBuilderHolder?): Builder {
            alertParams.baseViewBuilderHolder = baseViewBuilderHolder
            return this
        }

        /**
         * 设置取消按钮
         *
         * @param text
         * @param onCancelListener
         * @return
         */
        fun setOnCancelListener(text: String?, onCancelListener: DialogInterface.OnClickListener? = null): Builder {
            alertParams.negativeButtonListener = onCancelListener
            text?.let {
                alertParams.negativeButtonText = it
            }
            return this
        }

        /**
         * 确定按钮
         *
         * @param text
         * @param listener
         * @return
         */
        fun setPositiveButton(text: CharSequence?, listener: DialogInterface.OnClickListener? = null): Builder {
            text?.let {
                alertParams.positiveButtonText = it
            }
            alertParams.positiveButtonListener = listener
            return this
        }

        /**
         * 设置标题
         *
         * @param text
         * @return
         */
        fun setTitle(text: CharSequence?, fontSize: Float = ResourcesUtils.getDimen(R.dimen.x36)): Builder {
            text?.let {
                alertParams.title = it
            }
            alertParams.titleFontSize = fontSize
            return this
        }

        /**
         *  中间的提示
         */
        fun setContent(text: CharSequence?): Builder {
            text?.let {
                alertParams.content = it
            }
            return this
        }

        /**
         *  分割线
         */
        fun setDividingLine(showDividingLine: Boolean) {
            alertParams.showDividingLine = showDividingLine
        }

        /**
         * 空白关闭
         * @param dismiss Boolean
         * @return Builder
         */
        fun setDismissBackground(dismiss: Boolean): Builder {
            alertParams.isDismissBackground = dismiss
            return this

        }

        /**
         *  屏蔽返回键
         * @param shieldReturn Boolean
         * @return Builder
         */
        fun setShieldReturn(shieldReturn: Boolean): Builder {
            alertParams.shieldReturn = shieldReturn
            return this
        }
    }
}