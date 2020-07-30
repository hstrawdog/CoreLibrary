package com.hqq.core.ui.dialog;

import android.content.DialogInterface;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.hqq.core.R;
import com.hqq.core.ui.BaseViewBuilderHolder;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseSelectDialog
 * @Date : 2019/10/31 0031  上午 11:12
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public class BaseSelectDialog<T extends BaseViewBuilderHolder> extends BaseDialog implements DialogInterface {
    protected T mViewHolder;
    private AlertParams mAlertParams;

    @Override
    public int getViewId() {
        return R.layout.dialog_base_select_dialog;
    }

    @Override
    public int getAnimation() {
        return R.style.dialogAnimation_fade_in2fade_out;
    }

    @Override
    public int getWeight() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    public void initView() {

        if (mAlertParams != null) {
            initAlertParams();
        }


        mViewHolder = getViewHolder();
        mViewHolder.builder(this, mRootView.findViewById(R.id.ll_content));
        mViewHolder.addToParent();
        mViewHolder.initView();
    }

    /**
     * builder 对象处理
     */
    private void initAlertParams() {
        // 左边按钮 取消
        TextView tvCancel = mRootView.findViewById(R.id.tv_cancel);
        tvCancel.setText(mAlertParams.mNegativeButtonText);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAlertParams.mNegativeButtonListener != null) {
                    mAlertParams.mNegativeButtonListener.onClick(BaseSelectDialog.this, BUTTON_NEGATIVE);
                }
            }
        });
        // 右边按钮 确认
        TextView tvDetermine = mRootView.findViewById(R.id.tv_determine);
        tvDetermine.setText(mAlertParams.mPositiveButtonText);
        tvDetermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAlertParams.mPositiveButtonListener != null) {
                    mAlertParams.mPositiveButtonListener.onClick(BaseSelectDialog.this, BUTTON_POSITIVE);
                }
            }
        });
        // 提示
        TextView tvTitle = mRootView.findViewById(R.id.tv_title);
        tvTitle.setText(mAlertParams.mTitle);

    }

    private T getViewHolder() {
        return (T) mAlertParams.mBaseViewBuilderHolder;
    }


    public void setAlertParams(AlertParams alertParams) {
        mAlertParams = alertParams;
    }

    public AlertParams getAlertParams() {
        return mAlertParams;
    }

    @Override
    public void cancel() {
        dismiss();
    }


    public static class Builder {
        AlertParams mAlertParams;

        /**
         * 参考 AlertDialog Builder 模式创建SelectDialog
         */
        public Builder() {
            mAlertParams = new AlertParams();
        }

        /**
         * 构建
         *
         * @return
         */
        public BaseSelectDialog create() {
            BaseSelectDialog baseSelectDialog = new BaseSelectDialog();
            baseSelectDialog.setAlertParams(mAlertParams);
            return baseSelectDialog;
        }

        /**
         * 设置中间布局  采用ViewHolder  方式构建
         *
         * @param baseViewBuilderHolder
         * @return
         */
        public Builder setBaseViewBuilderHolder(BaseViewBuilderHolder baseViewBuilderHolder) {
            mAlertParams.mBaseViewBuilderHolder = baseViewBuilderHolder;
            return this;
        }

        /**
         * 设置取消按钮
         *
         * @param onCancelListener
         * @return
         */
        public BaseSelectDialog.Builder setOnCancelListener(DialogInterface.OnClickListener onCancelListener) {
            mAlertParams.mNegativeButtonListener = onCancelListener;
            return this;
        }

        /**
         * 设置取消按钮
         *
         * @param onCancelText
         * @param onCancelListener
         * @return
         */
        public BaseSelectDialog.Builder setOnCancelListener(String onCancelText, DialogInterface.OnClickListener onCancelListener) {
            mAlertParams.mNegativeButtonListener = onCancelListener;
            mAlertParams.mNegativeButtonText = onCancelText;

            return this;
        }

        /**
         * 确定按钮
         *
         * @param listener
         * @return
         */
        public BaseSelectDialog.Builder setPositiveButton(final OnClickListener listener) {
            mAlertParams.mPositiveButtonListener = listener;
            return this;
        }

        /**
         * 确定按钮
         *
         * @param text
         * @param listener
         * @return
         */
        public BaseSelectDialog.Builder setPositiveButton(CharSequence text, final OnClickListener listener) {
            mAlertParams.mPositiveButtonText = text;
            mAlertParams.mPositiveButtonListener = listener;
            return this;
        }

        /**
         * 设置标题
         *
         * @param text
         * @return
         */
        public BaseSelectDialog.Builder setTitle(CharSequence text) {
            mAlertParams.mTitle = text;
            return this;
        }


    }


}
