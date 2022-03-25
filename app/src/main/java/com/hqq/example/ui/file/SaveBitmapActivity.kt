package com.hqq.example.ui.file

import android.os.Build
import android.os.Environment
import com.hqq.core.glide.ImageLoadUtils
import com.hqq.core.permission.PermissionsUtils
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.file.BitmapUtils
import com.hqq.core.utils.file.FileUtils
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

    var path: String = ""

    override fun initView() {
        binding.textView28.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView28.text
                    // 保存到默认的文件夹
                    path = SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle))
                        .apply {
                            isSave2Album = false
                        }
                        .setFileName(System.currentTimeMillis().toString() + ".png").save2AppCache()
                    binding.textView40.setText("$path")
                    ImageLoadUtils.with(path, binding.imageView15)

                }
            }
        }
        binding.textView34.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView34.text
                    // 保存到默认的文件夹
                    path = SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle))
                        .setFileName(System.currentTimeMillis().toString() + ".png").save2AppCache()
                    binding.textView40.setText("$path")
                    ImageLoadUtils.with(path, binding.imageView15)
                }
            }
        }
        binding.textView30.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView30.text
                    // 保存到默认的文件夹
                    path = SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle))
                        .apply {
                            filePath =
                                Environment.getExternalStorageDirectory().absolutePath + File.separator + "HQQ/" + System.currentTimeMillis()
                                    .toString() + ".png";
                        }
                        .save2ExternalPrivate()
                    binding.textView40.setText("$path")
                    ImageLoadUtils.with(path, binding.imageView15)
                }
            }
        }
        binding.textView35.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView35.text
                    // 保存到默认的文件夹
                    path = SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle))
                        .apply {
                            isSave2Album = false
                            filePath =
                                Environment.getExternalStorageDirectory().absolutePath + File.separator + "HQQ/" + System.currentTimeMillis()
                                    .toString() + ".png";
                        }
                        .save2ExternalPrivate()
                    binding.textView40.setText("$path")
                    ImageLoadUtils.with(path, binding.imageView15)
                }
            }
        }
        binding.textView29.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView29.text
                    // 保存到默认的文件夹
                    path = SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle)).save2Pictures()
                }
            }
        }

        binding.textView31.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView31.text
                    // 保存到默认的文件夹
                    path = SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle))
                        .apply {
                            filePath = FileUtils.getStorageDirectory() + File.separator + "HQQ" + File.separator + System.currentTimeMillis() + ".png"
                        }
                        .save2Pictures()
                }
            }
        }
        binding.textView32.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    binding.tvTitle.text = binding.textView32.text
                    // 保存到默认的文件夹
                    path = SaveBitmapBuild(BitmapUtils.createBitmapFromView2(binding.tvTitle))
                        .apply {
                            filePath =
                                FileUtils.getExternalPicturesPath() + File.separator + "A" + File.separator + System.currentTimeMillis() + ".png"
                        }
                        .save2Pictures()
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