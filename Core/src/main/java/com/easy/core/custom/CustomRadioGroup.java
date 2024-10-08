package com.easy.core.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.custom
 * @Date : 17:04
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class CustomRadioGroup extends LinearLayout {
    private CustomRadioButton selectedRadioButton;
    private OnRadioButtonSelectedListener listener;

    public interface OnRadioButtonSelectedListener {
        void onRadioButtonSelected(int index);
    }

    public CustomRadioGroup(Context context) {
        super(context);
        init();
    }

    public CustomRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 设置点击事件
        setOnClickListener(v -> {
//            View selectedView = getCurrentFocus();
//            if (selectedView instanceof CustomRadioButton) {
//                selectRadioButton((CustomRadioButton) selectedView);
//            }
        });
    }

    public void setOnRadioButtonSelectedListener(OnRadioButtonSelectedListener listener) {
        this.listener = listener;
    }

    public void selectRadioButton(CustomRadioButton radioButton) {
        if (selectedRadioButton != null) {
            selectedRadioButton.setSelected(false);
        }
        selectedRadioButton = radioButton;
        selectedRadioButton.setSelected(true);

        // 获取索引并回调
        int index = indexOfChild(radioButton);
        if (listener != null) {
            listener.onRadioButtonSelected(index);
        }
    }

    public CustomRadioButton getSelectedRadioButton() {
        return selectedRadioButton;
    }
}

