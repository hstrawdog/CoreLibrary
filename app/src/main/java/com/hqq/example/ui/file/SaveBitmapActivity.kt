package com.hqq.example.ui.file

import android.os.Environment
import com.hqq.core.permission.PermissionsUtils
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.file.BitmapUtils
import com.hqq.core.utils.file.SaveBitmapBuild
import com.hqq.example.databinding.ActivitySaveBitmapBinding
import java.io.File

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
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView28.text
                    // 保存到默认的文件夹
                    SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle))
                        .setFileName("aaaa1111.png").save2AppCache()
                }
            }
        }
        binding.textView30.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView30.text
                    // 保存到默认的文件夹
                    SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle))
                        .apply {
                            filePath = Environment.getExternalStorageDirectory().absolutePath + File.separator + "HQQ/aa.png";
                        }
                        .save2ExternalPrivate()
                }
            }
        }

        binding.textView29.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView29.text
                    // 保存到默认的文件夹
                    SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle)).save2Public()
                }
            }
        }

        binding.textView31.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView31.text
                    // 保存到默认的文件夹
                    SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle)).save2ExternalPrivate()
                }
            }
        }

    }
}