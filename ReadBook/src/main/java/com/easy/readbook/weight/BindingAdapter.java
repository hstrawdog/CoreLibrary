package com.easy.readbook.weight;

import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;


/**
 * @Author : huangqiqiang
 * @Package : com.rongji.patelf.utils
 * @Date : 上午 10:08
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class BindingAdapter {

    @androidx.databinding.BindingAdapter("srcHead")
    public static void setSrcHead(ImageView imageView, String src) {

    }

    @androidx.databinding.BindingAdapter("checkValue")
    public static void setOnCheckedChangeListener(Switch view, boolean value) {
        view.setChecked(value);
    }

    @InverseBindingAdapter(attribute = "checkValue", event = "checkValueChange")
    public static boolean getOnCheckedChangeListener(Switch view) {
        return view.isChecked();
    }

    @androidx.databinding.BindingAdapter(value = {"checkValueChange"})
    public static void checkValueChange(Switch view, final InverseBindingListener checkValueChange) {

        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkValueChange.onChange();
            }
        });
    }


}
