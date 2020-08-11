package com.hqq.example.ui.skin

import android.content.Context
import android.content.Intent
import android.view.View
import com.hqq.example.R

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.skin
 * @FileName :   SkinBActivity
 * @Date : 2020/4/10  上午10:02
 * @Email : qiqiang213@gmail.com
 * @Descrive :
</描述当前版本功能> */
class SkinBActivity : BaseSkinActivity() {
    override fun initView() {
        super.initView()
        findViewById<View>(R.id.button34).setOnClickListener { SkinCActivity.open(mActivity) }
    }

    companion object {
        fun open(context: Context) {
            val starter = Intent(context, SkinBActivity::class.java)
            context.startActivity(starter)
        }
    }
}