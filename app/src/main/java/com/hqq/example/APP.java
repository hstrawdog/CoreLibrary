package com.hqq.example;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import dagger.hilt.android.HiltAndroidApp;
import dagger.hilt.android.qualifiers.ApplicationContext;
import skin.support.SkinCompatManager;
import skin.support.app.SkinAppCompatViewInflater;
import skin.support.app.SkinCardViewInflater;
import skin.support.constraint.app.SkinConstraintViewInflater;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary
 * @FileName :   APP
 * @Date : 2018/11/23 0023  下午 2:21
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
@HiltAndroidApp
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //配置 默认加载的 toolBar    CoreInitProvider   会比 Application  更加优先
        //  CoreBuildConfig.getInstance().init(this, true);
        //.setDefItoobar(BaseDefToolBarImpl.class);
        SkinCompatManager.withoutActivity(this)
                // 基础控件换肤初始化
                .addInflater(new SkinAppCompatViewInflater())
                // material design 控件换肤初始化[可选]
                .addInflater(new SkinMaterialViewInflater())
                // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())
                // CardView v7 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())
                // 关闭状态栏换肤，默认打开[可选]
                .setSkinStatusBarColorEnable(false)
                // 关闭windowBackground换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)
                .loadSkin();
    }
    public boolean isApkDebugable() {
        //debug 返回true  release 返回false
        try {
            ApplicationInfo info = getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {

        }
        return false;
    }


}
