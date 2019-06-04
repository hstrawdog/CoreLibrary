package com.hqq.core.utils.statusbar;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.blibrary
 * @FileName :   StatusBarManager
 * @Date : 2018/5/24 0024  下午 3:09
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 * https://www.jianshu.com/p/7d8df51347ff
 * https://blog.csdn.net/qq_36230279/article/details/80664863
 */
public class StatusBarManager {
    /**
     * MIUI状态栏字体黑色与白色标识位
     */
    static final String IMMERSION_MIUI_STATUS_BAR_DARK = "EXTRA_FLAG_STATUS_BAR_DARK_MODE";

    /**
     * 在Android4.1及以上版本中，你可以把应用的内容显示在状态栏后面，所以当status bar隐藏和显示的时候，内容区域不会改变大小。想要到达这个效果，可以使用SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN。你同样也需要使用 SYSTEM_UI_FLAG_LAYOUT_STABLE 来帮助你的app来维持一个稳定的布局。
     * <p>
     * 当你使用了这种方式， 你的职责就是确保app UI的主要部分 (比如，在地图应用中内置的控制) 不会因为被system bars 覆盖而结束。 这会导致你的app不能用。在大多数情况下你可以控制它通过在XML 布局文件中添加 theandroid:fitsSystemWindows 属性，设置为true。这个会调节 父 ViewGroup的padding值来给system windows腾出空间。对大多数应用这已经足够了。
     * <p>
     * 但是还有另外一些情况 你可能需要修改默认的padding来得到你渴望的布局。直接相对于system bars来操作你的content布局(这个空间称为window's "content insets"), 覆写fitSystemWindows(Rect insets)
     * . 这个方法fitSystemWindows() 会被view hierarchy 调用当 一个window的content insets 变化的时候，来允许window相应的调整它的内容区域。通过覆写这个方法你可以操作这个insets来达到你想要的效果。
     * <p>
     * 作者：ShenJC
     * 链接：https://www.jianshu.com/p/e27e7f09d1f7
     * 來源：简书
     * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
     */
    @TargetApi(19)
    public static void transparencyBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0及以上
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View decorView = window.getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4到5.0
            WindowManager.LayoutParams localLayoutParams = window.getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


    /**
     * @param window
     * @param lightStatusBar
     */
    public static void setStatusBarModel(Window window, boolean lightStatusBar) {
        transparencyBar(window);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarTextColor(window, lightStatusBar);
        }
        if (OSUtils.isMIUI6Later()) {
            MIUISetStatusBarLightMode(window, lightStatusBar);
        } else if (OSUtils.isFlymeOS4Later()) {
            //参数 false 白色 true 黑色
            FlymeOSStatusBarFontUtils.setStatusBarDarkIcon(window, lightStatusBar);
        } else if (!OSUtils.isFlymeOS4Later()) {
            processFlyMe(lightStatusBar, window);
        }
    }


    /**
     * 设置StatusBar字体颜色
     * <p>
     * 参数true表示StatusBar风格为Light，字体颜色为黑色
     * 参数false表示StatusBar风格不是Light，字体颜色为白色
     * <p>
     * <item name="android:windowLightStatusBar">true</item>
     * 在theme或style中使用这个属性改变StatusBar的字体颜色，这种形式相对不灵活
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void setStatusBarTextColor(Window window, boolean lightStatusBar) {
        if (window == null) {
            return;
        }
        View decor = window.getDecorView();
        int ui = decor.getSystemUiVisibility();
        if (lightStatusBar) {
            ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        decor.setSystemUiVisibility(ui);
    }


    /**
     * 需要MIUIV6以上
     *
     * @param window
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static void MIUISetStatusBarLightMode(Window window, boolean dark) {
        if (window != null) {
            Class<? extends Window> clazz = window.getClass();
            try {
                int darkModeFlag;
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                String key = IMMERSION_MIUI_STATUS_BAR_DARK;
                Field field = layoutParams.getField(key);
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    //状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
            } catch (Exception ignored) {

            }
        }
    }

    /**
     * 改变魅族的状态栏字体为黑色，要求FlyMe4以上
     */
    private static void processFlyMe(boolean isLightStatusBar, Window activity) {
        WindowManager.LayoutParams lp = activity.getAttributes();
        try {
            Class<?> instance = Class.forName
                    ("android.view.WindowManager$LayoutParams");
            int value = instance.getDeclaredField
                    ("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp);
            Field field = instance.getDeclaredField("meizuFlags");
            field.setAccessible(true);
            int origin = field.getInt(lp);
            if (isLightStatusBar) {
                field.set(lp, origin | value);
            } else {
                field.set(lp, (~value) & origin);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

    }


}



