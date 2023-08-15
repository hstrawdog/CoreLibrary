package com.easy.example.ui.system.info

import android.widget.TextView
import com.easy.core.ui.base.BaseActivity
import com.easy.core.utils.AppTool
import com.easy.core.utils.DeviceUtils
import com.easy.core.utils.ScreenUtils.getAllScreenHeight
import com.easy.core.utils.ScreenUtils.getNavigationBarHeight
import com.easy.core.utils.file.CacheUtil.getAppCacheSize
import com.easy.core.utils.file.CacheUtil.getCacheSize
import com.easy.core.utils.file.CacheUtil.getTotalCacheSize
import com.easy.core.utils.ScreenUtils.getScreenDensityDpi
import com.easy.core.utils.ScreenUtils.getScreenRealHeight
import com.easy.core.utils.ScreenUtils.getScreenWidth
import com.easy.core.utils.ScreenUtils.getScreenXDPI
import com.easy.core.utils.ScreenUtils.getScreenYDPI
import com.easy.core.utils.ScreenUtils.getStatusBarHeight
import com.easy.core.utils.ScreenUtils.isAllScreenDevice
import com.easy.core.utils.text.TextSpannableBuilder

import com.easy.example.R

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.activity
 * @FileName :   BaseInfoActivity
 * @Date : 2019-07-02  21:51
 * @Descrive : TODO
 * @Email :
</描述当前版本功能> */
class BaseInfoActivity : BaseActivity() {
    lateinit var mTvInfo: TextView

    override fun getLayoutViewId(): Int {
        return  R.layout.activity_base_info
    }

    override fun initView() {
        mTvInfo = findViewById(R.id.tv_info)
        mTvInfo.setText(TextSpannableBuilder().addTextPart("包名    ")
            .addTextPart(AppTool.getPackageName(this))
            .addTextPart("\n版本    ")
            .addTextPart(AppTool.getVerName(activity))
            .addTextPart("\n版本号    ")
            .addTextPart(AppTool.getVersionCode(this).toString() + "")
            .addTextPart("\n系统分配大小    ")
            .addTextPart(getAppCacheSize(this).toString() + "M")
            .addTextPart("\nGlide缓存大小    ")
            .addTextPart(getCacheSize(this) + "")
            .addTextPart("\n缓存总大小    ")
            .addTextPart(getTotalCacheSize(this) + "")
            .addTextPart("\n手机类型    ")
            .addTextPart(DeviceUtils.getPhoneType(this)
                .toString())
            .addTextPart("\n设备厂商    ")
            .addTextPart(DeviceUtils.buildMANUFACTURER)

            .addTextPart("\n状态栏高度    ")
            .addTextPart(getStatusBarHeight(this).toString() + "px")
            .addTextPart("\n导航栏高度    ")
            .addTextPart(getNavigationBarHeight(this).toString() + "px")
            .addTextPart("\n是否全面屏    ")
            .addTextPart(isAllScreenDevice(this).toString())

            .addTextPart("\n屏幕宽度    ")
            .addTextPart(getScreenWidth(this).toString() + "px")
            .addTextPart("\n屏幕高度    ")
            .addTextPart(getAllScreenHeight().toString() + "px")
            .addTextPart("\n屏幕真实高度    ")
            .addTextPart(getScreenRealHeight(this).toString())

            .addTextPart("\n密度x    ")
            .addTextPart(getScreenXDPI(this).toString() + "dpi")
            .addTextPart("\n密度y    ")
            .addTextPart(getScreenYDPI(this).toString() + "dpi")
            .addTextPart("\n屏幕密度    ")
            .addTextPart(getScreenDensityDpi(this).toString() + "dpi")

            .build())
    }
}