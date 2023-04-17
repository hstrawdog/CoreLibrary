package com.hqq.example.ui.share

import android.content.Intent
import android.net.Uri
import android.os.StrictMode
import android.text.TextUtils
import android.widget.Toast
import androidx.core.content.FileProvider
import com.hqq.core.permission.PermissionsUtils
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.core.utils.file.BitmapUtils
import com.hqq.core.utils.file.FileUtils
import com.hqq.core.utils.file.SaveBitmapUtils
import com.hqq.core.utils.log.LogUtils
import com.hqq.example.databinding.ActivityShareIndexBinding
import java.io.File


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.share
 * @Date : 00:36
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ShareIndexActivity : BaseViewBindingActivity<ActivityShareIndexBinding>() {
    override fun initView() {
        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()

        binding.button80.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    var path = SaveBitmapUtils.saveBitmap2AppCache(
                        BitmapUtils.createBitmapFromView2(binding.button80),
                        "",
                        FileUtils.getDefFileName(".png")
                    )
                    LogUtils.e("$path")
                    shareImg(File(path))
                }
            }
        }
        binding.button81.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    var path = SaveBitmapUtils.saveBitmap2ExternalPrivate(
                        BitmapUtils.createBitmapFromView2(binding.button81),
                        "",
                        FileUtils.getDefFileName(".png")
                    )
                    LogUtils.e("$path")
                    shareImg(File(path))
                }
            }
        }
        binding.button82.setOnClickListener {
            PermissionsUtils.requestStorage {
                if (it) {
                    var path = SaveBitmapUtils.saveBitmap2Pictures(
                        BitmapUtils.createBitmapFromView2(binding.button82),
                        "",
                        FileUtils.getDefFileName(".png")
                    )
                    LogUtils.e("$path")
                    shareImg(File(path))
                }
            }
        }

    }

    /**
     * 分享图片
     */
    fun shareImg(file: File) {
        if (file.exists()) {
//            val uri: Uri = Uri.fromFile(file)
            val uri: Uri? = FileUtils.getFile2Uri(file)
            val intent = Intent()
            intent.setAction(Intent.ACTION_SEND)
            intent.setType("image/*")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            val chooserIntent: Intent = Intent.createChooser(intent, "分享到:")
            startActivity(chooserIntent)
        } else {
            Toast.makeText(activity, "文件不存在", 1000).show()
        }
    }

    fun stringCheck(str: String?): Boolean {
        return null != str && !TextUtils.isEmpty(str)
    }
}