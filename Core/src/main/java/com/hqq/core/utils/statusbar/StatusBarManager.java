package com.hqq.core.utils.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
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
 * @Email :
 * https://www.jianshu.com/p/7d8df51347ff
 * https://blog.csdn.net/qq_36230279/article/details/80664863
 */
public class StatusBarManager {


    /**
     * 在Android4.1及以上版本中，你可以把应用的内容显示在状态栏后面，所以当status bar隐藏和显示的时候，内容区域不会改变大小。想要到达这个效果，可以使用SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN。你同样也需要使用 SYSTEM_UI_FLAG_LAYOUT_STABLE 来帮助你的app来维持一个稳定的布局。
     *
     * 当你使用了这种方式， 你的职责就是确保app UI的主要部分 (比如，在地图应用中内置的控制) 不会因为被system bars 覆盖而结束。 这会导致你的app不能用。在大多数情况下你可以控制它通过在XML 布局文件中添加 theandroid:fitsSystemWindows 属性，设置为true。这个会调节 父 ViewGroup的padding值来给system windows腾出空间。对大多数应用这已经足够了。
     *
     * 但是还有另外一些情况 你可能需要修改默认的padding来得到你渴望的布局。直接相对于system bars来操作你的content布局(这个空间称为window's "content insets"), 覆写fitSystemWindows(Rect insets)
     * . 这个方法fitSystemWindows() 会被view hierarchy 调用当 一个window的content insets 变化的时候，来允许window相应的调整它的内容区域。通过覆写这个方法你可以操作这个insets来达到你想要的效果。
     *
     * 作者：ShenJC
     * 链接：https://www.jianshu.com/p/e27e7f09d1f7
     * 來源：简书
     * 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
     */


    /**
     * 修改状态栏为全透明
     *
     * @param activity
     */
    @TargetApi(19)
    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0及以上
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * http://open-wiki.flyme.cn/index.php?title=%E7%8A%B6%E6%80%81%E6%A0%8F%E5%8F%98%E8%89%B2
     * https://blog.csdn.net/qq_36230279/article/details/80534665
     *
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static void statusBarLightMode(Activity activity) {
        setStatusBarModel(activity, true);
    }

    /**
     * 同上
     *
     * @param activity
     */
    public static void statusBarDarkMode(Activity activity) {
        setStatusBarModel(activity, false);
    }


    /*****************   *************************/
    /**
     * 字体只有两种 颜色 深色(黑) 浅色(白)
     *
     * @param mode
     */
    public static void statusBarLightMode(Activity activity, boolean mode) {
        if (mode) {
            StatusBarManager.statusBarLightMode(activity);
        } else {
            StatusBarManager.statusBarDarkMode(activity);
        }
    }


    /**
     * 设置
     *
     * @param activity
     * @param lightStatusBar
     */
    private static void setStatusBarModel(Activity activity, boolean lightStatusBar) {
        transparencyBar(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (MIUIUtils.isMIUI()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setStatusBarTextColor(activity.getWindow(), lightStatusBar);
                } else {
                    MIUISetStatusBarLightMode(activity, lightStatusBar);
                }
            } else if (FlymeUtils.isFlyme()) {
                //参数 false 白色 true 黑色
                StatusbarColorUtils.setStatusBarDarkIcon(activity, lightStatusBar);
            } else if (FlymeUtils.isMeizuFlymeOS()) {
                processFlyMe(lightStatusBar, activity);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setStatusBarTextColor(activity.getWindow(), lightStatusBar);
            } else {

            }
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
     * 状态栏暗色模式，清除MIUI、flyme或6.0以上版本状态栏黑色文字、图标
     */
    public static void statusBarDarkMode(Activity activity, int type) {
        if (type == 1) {
            MIUISetStatusBarLightMode(activity, false);
        } else if (type == 2) {
            FlymeSetStatusBarLightMode(activity.getWindow(), false);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

    }


    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    /**
     * 需要MIUIV6以上
     *
     * @param activity
     * @param dark     是否把状态栏文字及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    //状态栏透明且黑色字体
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);
                } else {
                    //清除黑色字体
                    extraFlagField.invoke(window, 0, darkModeFlag);
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 改变魅族的状态栏字体为黑色，要求FlyMe4以上
     */
    private static void processFlyMe(boolean isLightStatusBar, Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
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



