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
    protected var mViewHolder: T? = null
    var alertParams: AlertParams? = null
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
        mViewHolder = viewHolder
        mViewHolder!!.builder(this, mRootView!!.findViewById(R.id.ll_content))
        mViewHolder!!.addToParent()
        mViewHolder!!.initView()
    }

    /**
     * builder 对象处理
     */
    private fun initAlertParams() {
        // 左边按钮 取消
        val tvCancel = mRootView!!.findViewById<TextView>(R.id.tv_cancel)
        tvCancel.text = alertParams!!.mNegativeButtonText
        tvCancel.setOnClickListener {
            if (alertParams!!.mNegativeButtonListener != null) {
                alertParams!!.mNegativeButtonListener!!.onClick(this@BaseSelectDialog, DialogInterface.BUTTON_NEGATIVE)
            }
        }
        // 右边按钮 确认
        val tvDetermine = mRootView!!.findViewById<TextView>(R.id.tv_determine)
        tvDetermine.text = alertParams!!.mPositiveButtonText
        tvDetermine.setOnClickListener {
            if (alertParams!!.mPositiveButtonListener != null) {
                alertParams!!.mPositiveButtonListener!!.onClick(this@BaseSelectDialog, DialogInterface.BUTTON_POSITIVE)
            }
        }
        // 提示
        val tvTitle = mRootView!!.findViewById<TextView>(R.id.tv_title)
        tvTitle.text = alertParams!!.mTitle
    }

    private val viewHolder: T?
        private get() = alertParams!!.mBaseViewBuilderHolder as T

    override fun cancel() {
        dismiss()
    }

    class Builder {
        var mAlertParams: AlertParams

        /**
         * 构建
         *
         * @return
         */
        fun create(): BaseSelectDialog<*> {
            val baseSelectDialog: BaseSelectDialog<*> = BaseSelectDialog<Any?>()
            baseSelectDialog.alertParams = mAlertParams
            return baseSelectDialog
        }

        /**
         * 设置中间布局  采用ViewHolder  方式构建
         *
         * @param baseViewBuilderHolder
         * @return
         */
        fun setBaseViewBuilderHolder(baseViewBuilderHolder: BaseViewBuilderHolder?): Builder {
            mAlertParams.mBaseViewBuilderHolder = baseViewBuilderHolder
            return this
        }

        /**
         * 设置取消按钮
         *
         * @param onCancelListener
         * @return
         */
        fun setOnCancelListener(onCancelListener: DialogInterface.OnClickListener?): Builder {
            mAlertParams.mNegativeButtonListener = onCancelListener
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
            mAlertParams.mNegativeButtonListener = onCancelListener
            mAlertParams.mNegativeButtonText = onCancelText!!
            return this
        }

        /**
         * 确定按钮
         *
         * @param listener
         * @return
         */
        fun setPositiveButton(listener: DialogInterface.OnClickListener?): Builder {
            mAlertParams.mPositiveButtonListener = listener
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
            mAlertParams.mPositiveButtonText = text!!
            mAlertParams.mPositiveButtonListener = listener
            return this
        }

        /**
         * 设置标题
         *
         * @param text
         * @return
         */
        fun setTitle(text: CharSequence?): Builder {
            mAlertParams.mTitle = text!!
            return this
        }

        /**
         * 参考 AlertDialog Builder 模式创建SelectDialog
         */
        init {
            mAlertParams = AlertParams()
        }
    }
}