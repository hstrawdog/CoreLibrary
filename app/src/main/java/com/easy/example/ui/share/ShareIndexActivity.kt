package com.easy.example.ui.share

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.StrictMode
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import com.easy.core.permission.SysPermissionsUtils
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.image.BitmapUtils
import com.easy.core.utils.file.FileUtils
import com.easy.core.utils.file.SaveBitmapUtils
import com.easy.core.utils.log.LogUtils
import com.easy.example.databinding.ActivityShareIndexBinding
import java.io.File


/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.share
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
            SysPermissionsUtils.requestStorage ({
                if (it) {
                    var path = SaveBitmapUtils.saveBitmap2AppCache(
                        BitmapUtils.createBitmapFromView2(binding.button80),
                        "",
                        FileUtils.getDefFileName(".png")
                    )
                    LogUtils.e("$path")
                    shareImg(File(path))
                }
            })
        }
        binding.button81.setOnClickListener {
            SysPermissionsUtils.requestStorage ({
                if (it) {
                    var path = SaveBitmapUtils.saveBitmap2ExternalPrivate(
                        BitmapUtils.createBitmapFromView2(binding.button81),
                        "",
                        FileUtils.getDefFileName(".png")
                    )
                    LogUtils.e("$path")
                    shareImg(File(path))
                }
            })
        }
        binding.button82.setOnClickListener {
            SysPermissionsUtils.requestStorage( {
                if (it) {
                    var path = SaveBitmapUtils.saveBitmap2Pictures(
                        BitmapUtils.createBitmapFromView2(binding.button82),
                        "",
                        FileUtils.getDefFileName(".png")
                    )
                    LogUtils.e("$path")
                    shareImg(File(filePathByUri(activity,path)))
                }
            })
        }

    }

    fun filePathByUri(context: Context?, uri: Uri?): String? {
        var imagePath: String? = null
        if (context != null && uri != null) {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(uri, proj, null, null, null)
            if (cursor!!.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                imagePath = cursor.getString(column_index)
            }
            cursor.close()
        }
        return imagePath
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