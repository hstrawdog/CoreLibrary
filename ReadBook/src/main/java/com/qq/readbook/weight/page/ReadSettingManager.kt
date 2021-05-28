package com.qq.readbook.weight.page


import com.hqq.core.CoreConfig
import com.hqq.core.utils.ScreenUtils
import com.hqq.core.utils.sp.SharedPreferencesUtil

class ReadSettingManager private constructor() {

    /**
     *  屏幕亮度
     */
    var brightness: Int
        get() = SharedPreferencesUtil.getObject(CoreConfig.applicationContext, SHARED_READ_BRIGHTNESS, 40) as Int
        set(progress) = SharedPreferencesUtil.putObject(CoreConfig.applicationContext,
            SHARED_READ_BRIGHTNESS, progress)

    /**
     *  字体大小
     */
    var textSize: Int
        get() = SharedPreferencesUtil.getObject(CoreConfig.applicationContext,
            SHARED_READ_TEXT_SIZE,
            ScreenUtils.dip2px(CoreConfig.get().application, 16f)) as Int
        set(textSize) = SharedPreferencesUtil.putObject(CoreConfig.applicationContext,
            SHARED_READ_TEXT_SIZE, textSize)


    var pageMode: PageMode
        get() {
            val mode = SharedPreferencesUtil.getObject(CoreConfig.applicationContext,
                SHARED_READ_PAGE_MODE,
                PageMode.SIMULATION.ordinal) as Int
            return PageMode.values()[mode]
        }
        set(mode) = SharedPreferencesUtil.putObject(CoreConfig.applicationContext,
            SHARED_READ_PAGE_MODE,
            mode.ordinal)

    var pageStyle: PageStyle
        get() {
            val style = SharedPreferencesUtil.getObject(CoreConfig.applicationContext,
                SHARED_READ_BG,
                PageStyle.BG_0.ordinal) as Int
            return PageStyle.values()[style]
        }
        set(pageStyle) = SharedPreferencesUtil.putObject(CoreConfig.applicationContext,
            SHARED_READ_BG, pageStyle.ordinal)

    var isNightMode: Boolean
        get() = SharedPreferencesUtil.getObject(CoreConfig.applicationContext,
            SHARED_READ_NIGHT_MODE,
            false) as Boolean
        set(isNight) = SharedPreferencesUtil.putObject(CoreConfig.applicationContext,
            SHARED_READ_NIGHT_MODE,
            isNight)

    /**
     * 全屏
     */
    var isFullScreen: Boolean
        get() = SharedPreferencesUtil.getObject(CoreConfig.applicationContext,
            SHARED_READ_FULL_SCREEN,
            false) as Boolean
        set(isFullScreen) = SharedPreferencesUtil.putObject(CoreConfig.applicationContext,
            SHARED_READ_FULL_SCREEN,
            isFullScreen)

    /**
     *  是否跟随系统
     */
    var isBrightnessAuto: Boolean
        get() = SharedPreferencesUtil.getObject(CoreConfig.applicationContext,
            SHARED_READ_IS_BRIGHTNESS_AUTO,
            false) as Boolean
        set(isAuto) = SharedPreferencesUtil.putObject(CoreConfig.applicationContext,
            SHARED_READ_IS_BRIGHTNESS_AUTO,
            isAuto)


    companion object {
        /*************实在想不出什么好记的命名方式。。 */

        val SHARED_READ_BG = "shared_read_bg"
        val SHARED_READ_BRIGHTNESS = "shared_read_brightness"
        val SHARED_READ_IS_BRIGHTNESS_AUTO = "shared_read_is_brightness_auto"
        val SHARED_READ_TEXT_SIZE = "shared_read_text_size"
        val SHARED_READ_PAGE_MODE = "shared_read_mode"
        val SHARED_READ_NIGHT_MODE = "shared_night_mode"
        val SHARED_READ_FULL_SCREEN = "shared_read_full_screen"


        @Volatile
        private var instance: ReadSettingManager? = null

        @Synchronized
        fun getInstance(): ReadSettingManager {
            if (instance == null) {
                instance = ReadSettingManager()
            }
            return instance as ReadSettingManager
        }
    }
}
