package com.hqq.example.ui.file

import android.os.Build
import com.hqq.core.glide.ImageLoadUtils
import com.hqq.core.permission.PermissionsUtils
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.image.BitmapUtils
import com.hqq.core.utils.file.FileUtils
import com.hqq.core.utils.file.SaveBitmapUtils
import com.hqq.example.databinding.ActivitySaveBitmapBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.file
 * @Date : 下午 3:47
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SaveBitmapActivity : BaseViewBindingActivity<ActivitySaveBitmapBinding>() {

    var path: String = ""

    override fun initView() {
        binding.textView28.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    path =
                        SaveBitmapUtils.saveBitmap2AppCache(BitmapUtils.createBitmapFromView2(binding.tvTitle), "", FileUtils.getDefFileName(".png"))
                    binding.textView40.setText("$path")
                    ImageLoadUtils.with(path, binding.imageView15)

                }
            }
        }
        //保存在相册
        binding.textView29.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView29.text
                    SaveBitmapUtils.saveBitmap2Pictures(BitmapUtils.createBitmapFromView2(binding.tvTitle), fileName = FileUtils.getDefFileName(".png"))
                        ?.let {
                            path = it.path.toString()
                            binding.textView40.setText("$path")
                            ImageLoadUtils.with(path, binding.imageView15)
                        }
                }
            }
        }
        // 保存在相册 指定目录中
        binding.textView32.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView32.text
                    SaveBitmapUtils.saveBitmap2Pictures(BitmapUtils.createBitmapFromView2(binding.tvTitle), "a", fileName = FileUtils.getDefFileName(".png"))
                        ?.let {
                            path = it.path.toString()
                            binding.textView40.setText("$path")
                            ImageLoadUtils.with(path, binding.imageView15)
                        }

                }
            }
        }

        binding.textView34.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView34.text
                    // 保存到默认的文件夹
                    path =
                        SaveBitmapUtils.saveBitmap2ExternalPrivate(BitmapUtils.createBitmapFromView2(binding.tvTitle), "a", fileName = FileUtils.getDefFileName(".png"))
                    binding.textView40.setText("$path")
                    ImageLoadUtils.with(path, binding.imageView15)
                }
            }
        }



        binding.textView33.setOnClickListener {
            FileUtils.getFile2Uri(path)?.let { it1 ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    FileUtils.deleteFile(this, it1)
                }
            }
        }


    }
}