package com.easy.example.ui.system.info

import android.content.res.Configuration
import android.hardware.SensorManager
import android.os.Build
import android.view.OrientationEventListener
import android.widget.TextView
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.AppTool
import com.easy.core.utils.DeviceUtils
import com.easy.core.utils.ScreenUtils.getAllScreenHeight
import com.easy.core.utils.ScreenUtils.getNavigationBarHeight
import com.easy.core.utils.ScreenUtils.getScreenDensityDpi
import com.easy.core.utils.ScreenUtils.getScreenRealHeight
import com.easy.core.utils.ScreenUtils.getScreenWidth
import com.easy.core.utils.ScreenUtils.getScreenXDPI
import com.easy.core.utils.ScreenUtils.getScreenYDPI
import com.easy.core.utils.ScreenUtils.getStatusBarHeight
import com.easy.core.utils.ScreenUtils.getStatusBarHeight4Resources
import com.easy.core.utils.ScreenUtils.isAllScreenDevice
import com.easy.core.utils.file.CacheUtil.getAppCacheSize
import com.easy.core.utils.file.CacheUtil.getCacheSize
import com.easy.core.utils.file.CacheUtil.getTotalCacheSize
import com.easy.core.utils.log.LogUtils
import com.easy.core.utils.text.TextSpannableBuilder
import com.easy.example.R
import com.easy.example.databinding.ActivityBaseInfoBinding


/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.activity
 * @FileName :   BaseInfoActivity
 * @Date : 2019-07-02  21:51
 * @Describe : TODO
 * @Email :
</描述当前版本功能> */
class BaseInfoActivity : BaseViewBindingActivity<ActivityBaseInfoBinding>() {
    lateinit var mTvInfo: TextView

    var mOrientationEventListener: OrientationEventListener? = null

    //   项目设定死了 竖屏  不会再触发
    override fun onConfigurationChanged(newConfig: Configuration) {
        LogUtils.e("newConfig.orientation=" + newConfig.orientation);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            LogUtils.e("触发竖屏");
            binding.textView9.text = "触发竖屏"

        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LogUtils.e("触发横屏");
            binding.textView9.text = "触发横屏"

        }
        super.onConfigurationChanged(newConfig)

    }

    override fun initView() {

        mOrientationEventListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                LogUtils.e("onOrientationChanged: $orientation")
                var direction = ""
                if (orientation > 350 || orientation < 20) { //0度  90 正竖屏
                    direction = " 正竖屏"
                } else if (orientation in 71..109) { //90度 右横屏
                    direction = " 右横屏"

                } else if (orientation in 161..199) { //180度 倒竖屏
                    direction = " 倒竖屏"

                } else if (orientation in 251..289) { //270度 左横屏
                    direction = " 左横屏"

                }
                binding.textView8.setText("当前屏幕角度 :  ${orientation}   方向: ${direction}")

            }
        }
        var mOrientationEventListener2 = object : OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            //
            override fun onOrientationChanged(orientation: Int) {
                LogUtils.e("onOrientationChanged2: $orientation")
            }
        }
        if (mOrientationEventListener?.canDetectOrientation() == true) {
            LogUtils.e("可以检测方向")
            mOrientationEventListener2?.enable()
            mOrientationEventListener?.enable() //开启
        } else {
            LogUtils.e("无法检测方向")
            mOrientationEventListener?.disable()
            mOrientationEventListener2?.disable()

        }




        mTvInfo = findViewById(R.id.tv_info)
        mTvInfo.setText(TextSpannableBuilder().addTextPart("包名    ")
            .addTextPart(AppTool.getPackageName(this))
            .addTextPart("\n系统版本    ")
            .addTextPart(" ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})")
            .addTextPart("\n设备厂商    ")
            .addTextPart(DeviceUtils.buildMANUFACTURER)
            .addTextPart("\n型号: ${Build.MODEL} ")
            .addTextPart("\nAPK版本    ")
            .addTextPart(AppTool.getVerName(activity))
            .addTextPart("\nApk版本号    ")
            .addTextPart(AppTool.getVersionCode(this)
                .toString() + "")
            .addTextPart("\n系统分配大小    ")
            .addTextPart(getAppCacheSize(this).toString() + "M")
            .addTextPart("\nGlide缓存大小    ")
            .addTextPart(getCacheSize(this) + "")
            .addTextPart("\n缓存总大小    ")
            .addTextPart(getTotalCacheSize(this) + "")
            .addTextPart("\n手机类型    ")
            .addTextPart(DeviceUtils.getPhoneType(this)
                .toString())

            .addTextPart("\n顶部状态栏高度    ")
            .addTextPart(getStatusBarHeight4Resources(this).toString() + "px")
            .addTextPart("\n底部导航栏高度    ")
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

    override fun onDestroy() {
        super.onDestroy()
        mOrientationEventListener?.disable();//注销
    }
}