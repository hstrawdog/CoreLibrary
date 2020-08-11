package com.hqq.example.ui.customize

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.view.View
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.log.LogUtils.e
import com.hqq.example.R
import com.hqq.example.ui.customize.widget.GuideView

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.customize
 * @FileName :   CurtainActivity
 * @Date : 2020/5/12 0012  下午 3:22
 * @Email :  qiqiang213@gmail.com
 * @Descrive :
 */
class CurtainActivity : BaseActivity() {
    override fun getLayoutViewId(): Int {
        return R.layout.activity_curtain
    }

    override fun initView() {
        val guideView = findViewById<GuideView>(R.id.gv_view)
        findViewById<View>(R.id.textView11).setOnClickListener {
            val rect = Rect()
            findViewById<View>(R.id.imageView8).getDrawingRect(rect)
            val viewLocation = IntArray(2)
            findViewById<View>(R.id.imageView8).getLocationOnScreen(viewLocation)
            e("")
            guideView.setTargetView(findViewById(R.id.imageView8))
            guideView.visibility = View.VISIBLE
        }
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, CurtainActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}