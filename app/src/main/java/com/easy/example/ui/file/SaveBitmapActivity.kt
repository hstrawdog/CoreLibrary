package com.easy.example.ui.file

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import com.easy.core.glide.ImageLoadUtils
import com.easy.core.permission.SysPermissionsUtils
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.image.BitmapUtils
import com.easy.core.utils.file.FileUtils
import com.easy.core.utils.file.SaveBitmapUtils
import com.easy.core.utils.image.BitmapCreateUtils
import com.easy.example.databinding.ActivitySaveBitmapBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.file
 * @Date : 下午 3:47
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SaveBitmapActivity : BaseViewBindingActivity<ActivitySaveBitmapBinding>() {

    var path:String = ""
    var uri:Uri? = null

    override fun initView() {
        binding.textView28.setOnClickListener {
            SysPermissionsUtils.requestStorage(supportFragmentManager, {
                if (it) {
                    path = SaveBitmapUtils.saveBitmap2AppCache(BitmapCreateUtils.createBitmapFromView2(binding.tvTitle), "", FileUtils.getDefFileName(".png"))
                    binding.textView40.setText("$path")
                    ImageLoadUtils.with(path, binding.imageView15)

                }
            })
        }
        //保存在相册
        binding.textView29.setOnClickListener {
            SysPermissionsUtils.requestStorage(supportFragmentManager, {
                if (it) {
                    binding.tvTitle.text = binding.textView29.text
                    SaveBitmapUtils.saveBitmap2Pictures(BitmapCreateUtils.createBitmapFromView2(binding.tvTitle), fileName = FileUtils.getDefFileName(".png"))?.let {
                            path = it.path.toString()
                            binding.textView40.setText("$path")
                            ImageLoadUtils.with(path, binding.imageView15)
                        }
                }
            })
        }
        // 保存在相册 指定目录中
        binding.textView32.setOnClickListener {
            SysPermissionsUtils.requestStorage(supportFragmentManager, {
                if (it) {
                    binding.tvTitle.text = binding.textView32.text
                    SaveBitmapUtils.saveBitmap2Pictures(BitmapCreateUtils.createBitmapFromView2(binding.tvTitle), "subdirectory", fileName = FileUtils.getDefFileName(".png"))?.let {
                            uri = it
                            binding.textView40.setText("${uri}")
                            ImageLoadUtils.with(uri!!, binding.imageView15)
                        }

                }
            })
        }

        binding.textView34.setOnClickListener {
            SysPermissionsUtils.requestStorage(supportFragmentManager, {
                if (it) {
                    binding.tvTitle.text = binding.textView34.text
                    // 保存到默认的文件夹
                    path = SaveBitmapUtils.saveBitmap2ExternalPrivate(BitmapCreateUtils.createBitmapFromView2(binding.tvTitle), "a", fileName = FileUtils.getDefFileName(".png"))
                    binding.textView40.setText("$path")
                    ImageLoadUtils.with(path, binding.imageView15)
                }
            })
        }



        binding.textView33.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                uri?.let { it1 -> FileUtils.deleteFile(this, it1) }
            }

        }


    }
}