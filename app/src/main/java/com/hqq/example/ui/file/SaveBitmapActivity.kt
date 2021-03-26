package com.hqq.example.ui.file

import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.BitmapUtils
import com.hqq.core.utils.FileUtils
import com.hqq.example.databinding.ActivitySaveBitmapBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.file
 * @Date : 下午 3:47
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SaveBitmapActivity : BaseViewBindingActivity<ActivitySaveBitmapBinding>() {
    override fun initView() {
        binding.textView28.setOnClickListener {
            // 保存到默认的文件夹
            FileUtils.SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle)).save()
        }
        binding.textView28.setOnClickListener {
            // 保存到默认的文件夹
            FileUtils.SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle)).save()
        }

    }
}