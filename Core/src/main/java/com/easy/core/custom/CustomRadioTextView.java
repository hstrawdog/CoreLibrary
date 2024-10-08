package com.easy.core.custom;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easy.core.R;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.custom
 * @Date : 16:53
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class CustomRadioTextView extends LinearLayout {
    private ImageView imageView;
    private TextView textView;
    private boolean isSelected = false;

    public CustomRadioTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_radio_button, this, true);
        imageView = findViewById(R.id.image);
        textView = findViewById(R.id.text);
    }

    public void setImageResource(int resId) {
        imageView.setImageResource(resId);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        updateView();
    }

    public boolean isSelected() {
        return isSelected;
    }

    private void updateView() {
        if (isSelected) {
            setBackgroundColor(Color.LTGRAY); // 选中时变色
            textView.setTextColor(Color.WHITE);
        } else {
            setBackgroundColor(Color.TRANSPARENT); // 默认颜色
            textView.setTextColor(Color.BLACK);
        }
    }
}
