package com.qq.readbook.ui.menu

import androidx.lifecycle.MutableLiveData
import com.hqq.core.ui.base.BaseViewModel
import com.qq.readbook.weight.page.ReadSettingManager

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.ui.menu
 * @Date : 下午 5:12
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ReadSettingViewModel : BaseViewModel() {

    var isBrightnessAuto = MutableLiveData(ReadSettingManager.getInstance().isBrightnessAuto)
    var textSize = ReadSettingManager.getInstance().textSize.toString()
    var isNightMode = ReadSettingManager.getInstance().isNightMode


}