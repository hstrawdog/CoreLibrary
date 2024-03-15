package com.easy.example.ui.skin

import android.content.Context
import android.content.Intent
import android.view.View
import com.easy.example.R

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.skin
 * @FileName :   SkinBActivity
 * @Date : 2020/4/10  上午10:02
 * @Email : qiqiang213@gmail.com
 * @Describe :
</描述当前版本功能> */
class SkinBActivity : com.easy.example.ui.skin.BaseSkinActivity() {
    override fun initView() {
        super.initView()
        findViewById<View>(R.id.button34).setOnClickListener { com.easy.example.ui.skin.SkinCActivity.open(activity) }
    }

    companion object {
        fun open(context: Context) {
            val starter = Intent(context, SkinBActivity::class.java)
            context.startActivity(starter)
        }
    }
}