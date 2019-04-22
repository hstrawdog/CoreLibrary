package com.hqq.core;

import android.app.Application;

import com.hqq.core.toolbar.IDefToolBarImpl;


/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   CoreBuildConfig
 * @Date : 2018/11/23 0023  下午 2:16
 * @Descrive :  配置文件
 * @Email :
 */
public class CoreBuildConfig {
    private static volatile CoreBuildConfig singleton;

    public static CoreBuildConfig getInstance() {
        if (singleton == null) {
            synchronized (CoreBuildConfig.class) {
                if (singleton == null) {
                    singleton = new CoreBuildConfig();
                }
            }
        }
        return singleton;
    }

    private CoreBuildConfig() {
    }


    /**
     * 默认 标题栏 内容
     */
    private Class<?> mDefIToolbar = IDefToolBarImpl.class;
    /**
     * 布局默认背景颜色
     */
    private int bgColor = R.color.bg_color;
    /**
     * Application   主要获取 context
     * 理论奔溃后 会再次执行 Application 中的 onCreate()
     * mApplication  应单是 非空的
     */
    private Application mApplication;
    /**
     * 是否开启 log日志  BuildConfig.Debug
     */
    private boolean debug = false;

    private int defImg = R.drawable.ic_def_img;
    /**
     * 状态栏 模式
     */
    private boolean statusMode = true;

    /**
     * {@link #init(Application, boolean)}
     *
     * @param application Application
     */
    @Deprecated
    public void init(Application application) {
        mApplication = application;
    }

    /**
     * @param application Application
     * @param isDebug     是否 开启log日志
     */
    public void init(Application application, boolean isDebug) {
        mApplication = application;
        debug = isDebug;
    }

    public Class<?> getDefIToolbar() {
        return mDefIToolbar;
    }

    public void setDefIToolbar(Class<?> defIToolbar) {
        mDefIToolbar = defIToolbar;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public Class<?> getDefItoobar() {
        return mDefIToolbar;
    }

    public void setDefItoobar(Class<?> defItoobar) {
        mDefIToolbar = defItoobar;
    }

    public Application getApplication() {
        return mApplication;
    }

    public boolean isDebug() {
        return debug;
    }

    public int getDefImg() {
        return defImg;
    }

    public void setDefImg(int defImg) {
        this.defImg = defImg;
    }

    public boolean isStatusMode() {
        return statusMode;
    }

    public CoreBuildConfig setStatusMode(boolean statusMode) {
        this.statusMode = statusMode;
        return this;
    }
}
