package com.hqq.core.ui.dialog

import android.content.DialogInterface
import android.view.WindowManager
import android.widget.TextView
import com.hqq.core.R
import com.hqq.core.ui.BaseViewBuilderHolder

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseSelectDialog
 * @Date : 2019/10/31 0031  上午 11:12
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class BaseSelectDialog<T : BaseViewBuilderHolder?> : BaseDialog(), DialogInterface {
    var alertParams: AlertParams? = null
    private val viewHolder: T?
        get() = alertParams!!.baseViewBuilderHolder as T

    override val viewId: Int
        get() = R.layout.dialog_base_select_dialog

    override val animation: Int
        get() = R.style.dialogAnimation_fade_in2fade_out

    override val weight: Int
        get() = WindowManager.LayoutParams.MATCH_PARENT

    override fun initView() {
        if (alertParams != null) {
            initAlertParams()
        }
        viewHolder!!.builder(this, rootView!!.findViewById(R.id.ll_content))
        viewHolder!!.addToParent()
        viewHolder!!.initView()
    }

    /**
     * builder 对象处理
     */
    private fun initAlertParams() {
        // 左边按钮 取消
        val tvCancel = rootView!!.findViewById<TextView>(R.id.tv_cancel)
        tvCancel.text = alertParams!!.negativeButtonText
        tvCancel.setOnClickListener {
            if (alertParams!!.negativeButtonListener != null) {
                alertParams!!.negativeButtonListener!!.onClick(this@BaseSelectDialog, DialogInterface.BUTTON_NEGATIVE)
            }
        }
        // 右边按钮 确认
        val tvDetermine = rootView!!.findViewById<TextView>(R.id.tv_determine)
        tvDetermine.text = alertParams!!.positiveButtonText
        tvDetermine.setOnClickListener {
            if (alertParams!!.positiveButtonListener != null) {
                alertParams!!.positiveButtonListener!!.onClick(this@BaseSelectDialog, DialogInterface.BUTTON_POSITIVE)
            }
        }
        // 提示
        val tvTitle = rootView!!.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = alertParams!!.title
    }

    override fun cancel() {
        dismiss()
    }

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
        fun create(): BaseSelectDialog<*> {
            val baseSelectDialog: BaseSelectDialog<*> = BaseSelectDialog<BaseViewBuilderHolder>()
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
         * @param onCancelListener
         * @return
         */
        fun setOnCancelListener(onCancelListener: DialogInterface.OnClickListener?): Builder {
            alertParams.negativeButtonListener = onCancelListener
            return this
        }

        /**
         * 设置取消按钮
         *
         * @param onCancelText
         * @param onCancelListener
         * @return
         */
        fun setOnCancelListener(onCancelText: String?, onCancelListener: DialogInterface.OnClickListener?): Builder {
            alertParams.negativeButtonListener = onCancelListener
            alertParams.negativeButtonText = onCancelText!!
            return this
        }

        /**
         * 确定按钮
         *
         * @param listener
         * @return
         */
        fun setPositiveButton(listener: DialogInterface.OnClickListener?): Builder {
            alertParams.positiveButtonListener = listener
            return this
        }

        /**
         * 确定按钮
         *
         * @param text
         * @param listener
         * @return
         */
        fun setPositiveButton(text: CharSequence?, listener: DialogInterface.OnClickListener?): Builder {
            alertParams.positiveButtonText = text!!
            alertParams.positiveButtonListener = listener
            return this
        }

        /**
         * 设置标题
         *
         * @param text
         * @return
         */
        fun setTitle(text: CharSequence?): Builder {
            alertParams.title = text!!
            return this
        }

    }
}