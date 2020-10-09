package com.hqq.example.ui.adaptation

import android.app.Activity
import android.content.Intent
import com.hqq.core.glide.ImageLoadUtils
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.example.databinding.ActivityDefImgBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.app.ui.activity
 * @FileName :   DefImgActivity
 * @Date : 2019/7/18 0018  下午 3:52
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class DefImgActivity : BaseViewBindingActivity<ActivityDefImgBinding>() {
    companion object {
        fun open(context: Activity) {
            context.startActivityForResult(Intent(context, DefImgActivity::class.java), -1)
        }
    }

    override fun initView() {

        ImageLoadUtils.with("http://103.247.176.188/cover/wingsg//10038693//CoNA patch GT3.gif", binding.ivGif)

    }
}