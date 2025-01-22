package com.easy.example.ui.adaptation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Rect
import com.easy.core.CoreConfig
import com.easy.core.glide.ImageLoadUtils
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.ResourcesUtils
import com.easy.example.APP
import com.easy.example.R
import com.easy.example.databinding.ActivityDefImgBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.app.ui.activity
 * @FileName :   DefImgActivity
 * @Date : 2019/7/18 0018  下午 3:52
 * @Email :  qiqiang213@gmail.com
 * @Describe :
 */
class DefImgActivity : BaseViewBindingActivity<ActivityDefImgBinding>() {
    companion object {
        fun open(context: Activity) {
            context.startActivityForResult(Intent(context, DefImgActivity::class.java), -1)
        }
    }

    override fun initView() {

        CoreConfig.get().defErrorImg

        ImageLoadUtils.with("https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png", binding.ivGif)
        ImageLoadUtils.withFillet("https://img.jbzj.com/file_images/article/202010/2020101810184374.png", binding.imageView11)
        ImageLoadUtils.withFillet2PX("https://img.jbzj.com/file_images/article/202010/2020101810184374.png", binding.imageView12, 100)
        ImageLoadUtils.withFillet2PX("https://img.jbzj.com/file_images/article/202010/2020101810184374.png", binding.imageView13, com.easy.core.R.mipmap.ic_def_head)
        ImageLoadUtils.transformCircularHead("https://img.jbzj.com/file_images/article/202010/2020101810184374.png", binding.imageView14)



        ImageLoadUtils.with("http://39.155.212.90:19001/image/get/topicsxwk/cover/978-7-5073-4880-40001.jpg", binding.ivGif)



        binding.imageView20.setImageBitmap(onDef("01234567890123456789", "字字字字字字字字字字字字字字字字"))

    }


    fun onDef(title: String, periodicals: String): Bitmap? {
        var bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_def_pic)
        var newBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        var c = Canvas(newBitmap)
        c.drawBitmap(bitmap, Matrix(), android.graphics.Paint())
        var paint = android.graphics.Paint()
            .apply {
                color = ResourcesUtils.getColor(R.color.red)
                textSize =25f
                isAntiAlias = true
                strokeWidth = 25f
            }
        if (title.length > 9) {
            c.drawText(title.substring(0, 9), 10f, 130f, paint)
            c.drawText(title.substring(9, title.length), 10f, 160f, paint)
        } else {
            c.drawText(title, 10f, 130f, paint)
        }
        var mRect = Rect()
        paint.getTextBounds(periodicals, 0, periodicals.length, mRect)
        c.drawText(periodicals, bitmap.width - mRect.right - 10f, bitmap.height - mRect.bottom - 15f, paint)
        return newBitmap

    }
}