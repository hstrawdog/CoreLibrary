package com.hqq.example.ui.file

import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.BitmapUtils
import com.hqq.core.utils.file.FileUtils
import com.hqq.core.utils.file.SaveBitmapBuild
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
            SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle))
                    .setFileName("aaaa1111.png")
                    .save2AppCache()
        }

        binding.textView29.setOnClickListener {
            // 保存到默认的文件夹
            SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle)).save2Public()
        }

    }
}