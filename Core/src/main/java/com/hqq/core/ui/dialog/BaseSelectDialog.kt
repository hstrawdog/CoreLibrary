package com.hqq.core.ui.dialog

import android.content.DialogInterface
import android.text.Layout
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
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
        viewHolder?.builder(this, rootView!!.findViewById(R.id.ll_content))
        viewHolder?.addToParent()
        viewHolder?.initView()
    }

    /**
     * builder 对象处理
     */
    private fun initAlertParams() {
        // 左边按钮 取消
        rootView?.findViewById<TextView>(R.id.tv_cancel)?.let {
            it.text = alertParams?.negativeButtonText
            it.setOnClickListener {
                if (alertParams?.negativeButtonListener != null) {
                    alertParams?.negativeButtonListener?.onClick(this@BaseSelectDialog, DialogInterface.BUTTON_NEGATIVE)
                } else {
                    // 没有实现事件回调
                    dismiss()
                }
            }
        }
        // 右边按钮 确认
        rootView?.findViewById<TextView>(R.id.tv_determine)?.let {
            it.text = alertParams?.positiveButtonText
            it.setOnClickListener {
                if (alertParams?.positiveButtonListener != null) {
                    alertParams?.positiveButtonListener?.onClick(this@BaseSelectDialog, DialogInterface.BUTTON_POSITIVE)
                }
            }
        }
        // 提示
        rootView?.findViewById<TextView>(R.id.tv_title)?.let {
            it.text = alertParams!!.title
        }
        // 内容
        alertParams?.content?.let {
            if (alertParams?.baseViewBuilderHolder == null && it.isNotEmpty()) {
                var tv = TextView(activity)
                tv.gravity = Gravity.CENTER
                tv.text = it
                tv.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
                rootView?.findViewById<LinearLayout>(R.id.ll_content)?.let {
                    it.addView(tv)
                }

            }
        }


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
         * @param text
         * @param onCancelListener
         * @return
         */
        fun setOnCancelListener(text: String?, onCancelListener: DialogInterface.OnClickListener?): Builder {
            alertParams.negativeButtonListener = onCancelListener
            text?.let {
                alertParams.positiveButtonText = it
            }
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
        fun setTitle(text: CharSequence?): Builder {
            text?.let {
                alertParams.title = it
            }
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

    }
}