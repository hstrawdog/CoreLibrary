package com.hqq.example.ui.system.info

import android.widget.TextView
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.file.CacheUtil.getAppCacheSize
import com.hqq.core.utils.file.CacheUtil.getCacheSize
import com.hqq.core.utils.file.CacheUtil.getTotalCacheSize
import com.hqq.core.utils.ScreenUtils.getScreenDensityDpi
import com.hqq.core.utils.ScreenUtils.getScreenHeight
import com.hqq.core.utils.ScreenUtils.getScreenWidth
import com.hqq.core.utils.ScreenUtils.getScreenXDPI
import com.hqq.core.utils.ScreenUtils.getScreenYDPI
import com.hqq.core.utils.ScreenUtils.getStatusBarHeight4Resources
import com.hqq.core.utils.TextSpannableBuilder
import com.hqq.core.utils.VersionUtils.getPackageName
import com.hqq.core.utils.VersionUtils.getVerName
import com.hqq.core.utils.VersionUtils.getVersionCode
import com.hqq.example.R

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.activity
 * @FileName :   BaseInfoActivity
 * @Date : 2019-07-02  21:51
 * @Descrive : TODO
 * @Email :
</描述当前版本功能> */
class BaseInfoActivity : BaseActivity() {
    lateinit var mTvInfo: TextView
    override val layoutViewId: Int
        get() = R.layout.activity_base_info

    override fun initView() {
        mTvInfo = findViewById(R.id.tv_info)
        mTvInfo.setText(TextSpannableBuilder()
                .addTextPart("包名    ")
                .addTextPart(getPackageName(this))
                .addTextPart("\n版本    ")
                .addTextPart(getVerName(this))
                .addTextPart("\n版本号    ")
                .addTextPart(getVersionCode(this).toString() + "")
                .addTextPart("\n系统分配大小    ")
                .addTextPart(getAppCacheSize(this).toString() + "M")
                .addTextPart("\nGlide缓存大小    ")
                .addTextPart(getCacheSize(this) + "")
                .addTextPart("\n缓存总大小    ")
                .addTextPart(getTotalCacheSize(this) + "")
                .addTextPart("\n状态栏高度    ")
                .addTextPart(getStatusBarHeight4Resources(this).toString() + "px")
                .addTextPart("\n屏幕宽度    ")
                .addTextPart(getScreenWidth(this).toString() + "px")
                .addTextPart("\n屏幕高度    ")
                .addTextPart(getScreenHeight(this).toString() + "px")
                .addTextPart("\nx密度    ")
                .addTextPart(getScreenXDPI(this).toString() + "dpi")
                .addTextPart("\ny密度    ")
                .addTextPart(getScreenYDPI(this).toString() + "dpi")
                .addTextPart("\n屏幕密度    ")
                .addTextPart(getScreenDensityDpi(this).toString() + "dpi")
                .build())
    }
}