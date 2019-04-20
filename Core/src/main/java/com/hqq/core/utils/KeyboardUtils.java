package com.hqq.core.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 键盘 开启/关闭
 * @Author : huangqiqiang
 * @Package : com.core.library.utils
 * @FileName :   KeyboardUtils
 * @Date : 2018/9/26 0026  下午 6:20
 * @Descrive : TODO
 * @Email :
 * https://github.com/Blankj/AndroidUtilCode/blob/master/utilcode/src/main/java/com/blankj/utilcode/util/KeyboardUtils.java
 */
public class KeyboardUtils {

    /**
     * Show the soft input.
     *
     * @param activity The activity.
     */
    public static void showSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }



    /**
     * Show the soft input.
     *
     * @param window The Window.
     */
    public static void showSoftInput(final Window window) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }
        /**
         * Show the soft input.
         *
         * @param view The view.
         */
    public static void showSoftInput(final View view,Context context) {
        InputMethodManager imm =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        //noinspection ConstantConditions
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }
    /**
     *  隐藏软键盘
     * @param activity
     */
    public  static  void hideSoftInput(Activity activity){
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * Hide the soft input.
     *
     * @param view The view.
     */
    public static void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //noinspection ConstantConditions
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
