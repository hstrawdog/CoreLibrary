package com.hqq.example.ui.skin

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Switch
import com.hqq.core.utils.log.LogUtils.e
import com.hqq.example.R

/**
 * @version V1.0 <描述当前版本功能>
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.skin
 * @FileName :   SkinAActivity
 * @Date : 2020/4/10  上午10:02
 * @Email : qiqiang213@gmail.com
 * @Descrive :
</描述当前版本功能> */
class SkinAActivity : BaseSkinActivity() {
    override fun initView() {
        super.initView()
        (findViewById<View>(R.id.switch_def) as Switch).setOnCheckedChangeListener { buttonView, isChecked -> e("------") }
        findViewById<View>(R.id.button34).setOnClickListener { SkinBActivity.open(mActivity) }
    }

    companion object {
        fun open(context: Context) {
            val starter = Intent(context, SkinAActivity::class.java)
            context.startActivity(starter)
        }
    }
}