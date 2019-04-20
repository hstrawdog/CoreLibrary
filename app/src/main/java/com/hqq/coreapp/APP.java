package com.hqq.coreapp;

import android.app.Application;

import com.hqq.core.CoreBuildConfig;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary
 * @FileName :   APP
 * @Date : 2018/11/23 0023  下午 2:21
 * @Descrive :
 * @Email :
 */
public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //配置 默认加载的 toolBar
        CoreBuildConfig.getInstance().init(this,true);
        //.setDefItoobar(IDefToolBarImpl.class);
    }
}
