package com.hqq.core;

import android.app.Application;

import com.hqq.core.annotation.ToolBarMode;
import com.hqq.core.toolbar.BaseDefToolBarImpl;
import com.hqq.core.toolbar.IToolBar;


/**
 * @Author : huangqiqiang
 * @Package : com.core.library
 * @FileName :   CoreBuildConfig
 * @Date : 2018/11/23 0023  下午 2:16
 * @Descrive :  配置文件
 * @Email :  qiqiang213@gmail.com
 */
public class CoreBuildConfig {

    /**
     * 默认 标题栏 内容
     */
    private Class<? extends IToolBar > mDefIToolbar = BaseDefToolBarImpl.class;

    /**
     * Application   主要获取 context
     * 理论奔溃后 会再次执行 Application 中的 onCreate()
     * mApplication  应单是 非空的
     */
    private Application mApplication;
    /**
     * 是否开启 log日志  BuildConfig.Debug
     */
    private boolean mDebug = false;

    /**
     * 默认图
     */
    private int mDefImg = R.drawable.ic_def_img;
    /**
     * 状态栏 模式
     */
    private int mStatusMode = ToolBarMode.LIGHT_MODE;
    /**
     * 单利维持对象
     */
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
        mDebug = isDebug;
    }

    public Class<? > getDefIToolbar() {
        return mDefIToolbar;
    }

    public void setDefIToolbar(Class<? extends  IToolBar> defIToolbar) {
        mDefIToolbar = defIToolbar;
    }

    public Class<? extends IToolBar> getDefItoobar() {
        return (Class<? extends IToolBar>) mDefIToolbar;
    }


    public Application getApplication() {
        return mApplication;
    }

    public boolean isDebug() {
        return mDebug;
    }

    public int getDefImg() {
        return mDefImg;
    }

    public void setDefImg(int defImg) {
        this.mDefImg = defImg;
    }

    public int isStatusMode() {
        return mStatusMode;
    }

    public CoreBuildConfig setStatusMode(@ToolBarMode int statusMode) {
        this.mStatusMode = statusMode;
        return this;
    }
}
