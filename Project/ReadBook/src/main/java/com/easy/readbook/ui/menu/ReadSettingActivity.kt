package com.easy.readbook.ui.menu

import android.widget.SeekBar
import com.easy.core.ui.base.BaseVmActivity
import com.easy.readbook.BR
import com.easy.readbook.R
import com.easy.readbook.databinding.ActivityReadSettingBinding
import com.easy.readbook.weight.page.BrightnessUtils
import com.easy.readbook.weight.page.ReadSettingManager

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.ui.menu
 * @Date : 下午 3:31
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ReadSettingActivity : BaseVmActivity<ReadSettingViewModel, ActivityReadSettingBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_read_setting

    }

    override fun bindingViewModelId(): Int {

        return  BR.vm
    }

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