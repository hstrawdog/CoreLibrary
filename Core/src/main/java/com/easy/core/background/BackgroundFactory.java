package com.easy.core.background;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.easy.core.R;
import com.easy.core.background.drawable.DrawableFactory;
import com.easy.core.background.drawable.TextViewGradientColor;

public class BackgroundFactory  {

    /**
     * 根据属性设置图片背景
     *
     * @param context 上下文
     * @param attrs   bl属性
     * @param view    view
     * @return view
     */
    @Nullable
    public static View setViewBackground( Context context, AttributeSet attrs, View view) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.background);
        TypedArray pressTa = context.obtainStyledAttributes(attrs, R.styleable.background_press);
        TypedArray selectorTa = context.obtainStyledAttributes(attrs, R.styleable.background_selector);
        TypedArray textTa = context.obtainStyledAttributes(attrs, R.styleable.text_selector);
        TypedArray buttonTa = context.obtainStyledAttributes(attrs, R.styleable.background_button_drawable);
        TypedArray otherTa = context.obtainStyledAttributes(attrs, R.styleable.bl_other);
        TypedArray multiSelTa = context.obtainStyledAttributes(attrs, R.styleable.background_multi_selector);
        TypedArray multiTextTa = context.obtainStyledAttributes(attrs, R.styleable.background_multi_selector_text);
        TypedArray textViewTa = context.obtainStyledAttributes(attrs, R.styleable.bl_text);


        try {
            if (typedArray.getIndexCount() == 0 && selectorTa.getIndexCount() == 0 && pressTa.getIndexCount() == 0 && textTa.getIndexCount() == 0 && buttonTa.getIndexCount() == 0  && multiSelTa.getIndexCount() == 0 && multiTextTa.getIndexCount() == 0 && textViewTa.getIndexCount() == 0 && otherTa.getIndexCount() == 0) {
                return view;
            }
            //R.styleable.background_selector 和 R.styleable.background_multi_selector的属性不能同时使用
            if (selectorTa.getIndexCount() > 0 && multiSelTa.getIndexCount() > 0) {
                throw new IllegalArgumentException("Background_selector and background_multi_selector cannot be used simultaneously");
            }
            if (textTa.getIndexCount() > 0 && multiTextTa.getIndexCount() > 0) {
                throw new IllegalArgumentException("text_selector and background_multi_selector_text cannot be used simultaneously");
            }

            GradientDrawable drawable = null;
            StateListDrawable stateListDrawable = null;
            if (buttonTa.getIndexCount() > 0 && view instanceof CompoundButton) {
                //view.setClickable(true);
                ((CompoundButton) view).setButtonDrawable(DrawableFactory.getButtonDrawable(typedArray, buttonTa));
            } else if (selectorTa.getIndexCount() > 0) {
                stateListDrawable = DrawableFactory.getSelectorDrawable(typedArray, selectorTa);
                //view.setClickable(true);
                setDrawable(stateListDrawable, view, otherTa, typedArray);
            } else if (pressTa.getIndexCount() > 0) {
                drawable = DrawableFactory.getDrawable(typedArray);
                stateListDrawable = DrawableFactory.getPressDrawable(drawable, typedArray, pressTa);
                //view.setClickable(true);
                setDrawable(stateListDrawable, view, otherTa, typedArray);
            } else if (multiSelTa.getIndexCount() > 0) {
                stateListDrawable = DrawableFactory.getMultiSelectorDrawable(context, multiSelTa, typedArray);
                setBackground(stateListDrawable, view, typedArray);
            } else if (typedArray.getIndexCount() > 0) {

                    if (hasGradientState(typedArray)) {
                        stateListDrawable = DrawableFactory.getStateGradientDrawable(typedArray);
                        setDrawable(stateListDrawable, view, otherTa, typedArray);
                    } else {
                        drawable = DrawableFactory.getDrawable(typedArray);
                        setDrawable(drawable, view, otherTa, typedArray);
                    }
            }

            if (view instanceof TextView && textTa.getIndexCount() > 0) {
                ((TextView) view).setTextColor(DrawableFactory.getTextSelectorColor(textTa));
            } else if (view instanceof TextView && multiTextTa.getIndexCount() > 0) {
                ((TextView) view).setTextColor(DrawableFactory.getMultiTextColorSelectorColorCreator(context, multiTextTa));
            } else if (view instanceof TextView && textViewTa.getIndexCount() > 0) {
                new TextViewGradientColor().invoke(context, attrs, (TextView) view);
            }


            if (typedArray.getBoolean(R.styleable.background_bl_ripple_enable, false) && typedArray.hasValue(R.styleable.background_bl_ripple_color)) {
                int color = typedArray.getColor(R.styleable.background_bl_ripple_color, 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Drawable contentDrawable = (stateListDrawable == null ? drawable : stateListDrawable);
                    RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(color), contentDrawable, contentDrawable);
                    //view.setClickable(true);
                    setBackground(rippleDrawable, view, typedArray);
                } else if (stateListDrawable == null) {
                    StateListDrawable tmpDrawable = new StateListDrawable();
                    GradientDrawable unPressDrawable = DrawableFactory.getDrawable(typedArray);
                    unPressDrawable.setColor(color);
                    tmpDrawable.addState(new int[]{-android.R.attr.state_pressed}, drawable);
                    tmpDrawable.addState(new int[]{android.R.attr.state_pressed}, unPressDrawable);
                    //view.setClickable(true);
                    setDrawable(tmpDrawable, view, otherTa, typedArray);
                }
            }


            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return view;
        } finally {
            typedArray.recycle();
            pressTa.recycle();
            selectorTa.recycle();
            textTa.recycle();
            buttonTa.recycle();
            otherTa.recycle();
            multiSelTa.recycle();
            multiTextTa.recycle();
            textViewTa.recycle();

        }
    }

    private static void setDrawable(Drawable drawable, View view, TypedArray otherTa, TypedArray typedArray) {

        if (view instanceof TextView) {
            if (otherTa.hasValue(R.styleable.bl_other_bl_position)) {
                if (otherTa.getInt(R.styleable.bl_other_bl_position, 0) == 1) {
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    ((TextView) view).setCompoundDrawables(drawable, null, null, null);
                } else if (otherTa.getInt(R.styleable.bl_other_bl_position, 0) == 2) {
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    ((TextView) view).setCompoundDrawables(null, drawable, null, null);
                } else if (otherTa.getInt(R.styleable.bl_other_bl_position, 0) == 4) {
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    ((TextView) view).setCompoundDrawables(null, null, drawable, null);
                } else if (otherTa.getInt(R.styleable.bl_other_bl_position, 0) == 8) {
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    ((TextView) view).setCompoundDrawables(null, null, null, drawable);
                }
            } else {
                setBackground(drawable, view, typedArray);
            }
        } else {
            setBackground(drawable, view, typedArray);
        }

    }

    private static void setBackground(Drawable drawable, View view, TypedArray typedArray) {
        if (typedArray.hasValue(R.styleable.background_bl_stroke_width) && typedArray.hasValue(R.styleable.background_bl_stroke_position)) {
            //bl_stroke_position flag默认值
            int left = 1 << 1;
            int top = 1 << 2;
            int right = 1 << 3;
            int bottom = 1 << 4;
            float width = typedArray.getDimension(R.styleable.background_bl_stroke_width, 0f);
            int position = typedArray.getInt(R.styleable.background_bl_stroke_position, 0);
            float leftValue = hasStatus(position, left) ? 0 : -width;
            float topValue = hasStatus(position, top) ? 0 : -width;
            float rightValue = hasStatus(position, right) ? 0 : -width;
            float bottomValue = hasStatus(position, bottom) ? 0 : -width;
            drawable = new LayerDrawable(new Drawable[]{drawable});
            ((LayerDrawable) drawable).setLayerInset(0, (int) leftValue, (int) topValue, (int) rightValue, (int) bottomValue);
        }

        if (typedArray.hasValue(R.styleable.background_bl_shape_alpha)) {
            float alpha = typedArray.getFloat(R.styleable.background_bl_shape_alpha, 0f);
            if (alpha >= 1) {
                alpha = 255;
            } else if (alpha <= 0) {
                alpha = 0;
            } else {
                alpha = alpha * 255;
            }
            drawable.setAlpha((int) alpha);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    private static boolean hasStatus(int flag, int status) {
        return (flag & status) == status;
    }



    private static boolean hasGradientState(TypedArray typedArray) {
        return typedArray.hasValue(R.styleable.background_bl_checkable_gradient_startColor) || typedArray.hasValue(R.styleable.background_bl_checked_gradient_startColor) || typedArray.hasValue(R.styleable.background_bl_enabled_gradient_startColor) || typedArray.hasValue(R.styleable.background_bl_selected_gradient_startColor) || typedArray.hasValue(R.styleable.background_bl_pressed_gradient_startColor) || typedArray.hasValue(R.styleable.background_bl_focused_gradient_startColor);
    }
}


