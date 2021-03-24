package com.qq.readbook.ui.menu

import android.widget.SeekBar
import com.hqq.core.ui.base.BaseVmActivity
import com.qq.readbook.BR
import com.qq.readbook.R
import com.qq.readbook.databinding.ActivityReadSettingBinding
import com.qq.readbook.weight.page.BrightnessUtils
import com.qq.readbook.weight.page.ReadSettingManager

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.ui.menu
 * @Date : 下午 3:31
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ReadSettingActivity : BaseVmActivity<ReadSettingViewModel, ActivityReadSettingBinding>() {

    override val layoutId: Int = R.layout.activity_read_setting
    override val bindingViewModelId: Int
        get() = BR.vm

    override fun initViews() {
        viewMode.isBrightnessAuto.observe(this, {
            ReadSettingManager.getInstance().isBrightnessAuto = it
            if (ReadSettingManager.getInstance().isBrightnessAuto) {
                BrightnessUtils.setDefaultBrightness(this)
            } else {
                BrightnessUtils.setBrightness(this, ReadSettingManager.getInstance().brightness)
            }
        })

        binding.sbBrightness.progress = ReadSettingManager.getInstance().brightness
        binding.sbBrightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val progress = seekBar.progress
                //设置当前 Activity 的亮度
                BrightnessUtils.setBrightness(activity, progress)
                //存储亮度的进度条
                ReadSettingManager.getInstance().brightness = progress
            }
        })
    }

}