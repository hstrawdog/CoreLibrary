package com.hqq.example;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

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
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //配置 默认加载的 toolBar    CoreInitProvider   会比 Application  更加优先
        //  CoreBuildConfig.getInstance().init(this, true);
        //.setDefItoobar(BaseDefToolBarImpl.class);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);



        SkinCompatManager.withoutActivity(this)
                .addInflater(new SkinAppCompatViewInflater())           // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .addInflater(new SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
                .addInflater(new SkinCardViewInflater())                // CardView v7 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(false)                     // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(false)                   // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }
}
