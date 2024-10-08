package com.easy.example.ui.launch.mode

import android.view.View
import com.easy.core.ui.base.BaseActivity
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.launch.mode
 * @FileName :   LaunchActivity
 * @Date : 2019/10/25 0025  下午 5:20
 * @Email : qiqiang213@gmail.com
 * @Describe : 启动模式
 */
open class LaunchActivity : BaseActivity() {
    override fun getLayoutViewId(): Int {
        return R.layout.activity_launch_mode_a
    }

    override fun initView() {
        findViewById<View>(R.id.button20).setOnClickListener { view: View -> this.onViewClicked(view) }
        findViewById<View>(R.id.button21).setOnClickListener { view: View -> this.onViewClicked(view) }
        findViewById<View>(R.id.button22).setOnClickListener { view: View -> this.onViewClicked(view) }
        findViewById<View>(R.id.button23).setOnClickListener { view: View -> this.onViewClicked(view) }
        findViewById<View>(R.id.button24).setOnClickListener { view: View -> this.onViewClicked(view) }
        findViewById<View>(R.id.button26).setOnClickListener { view: View -> this.onViewClicked(view) }
        findViewById<View>(R.id.button27).setOnClickListener { view: View -> this.onViewClicked(view) }
    }

    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.button20 -> LaunchModeAActivity.open(this)
            R.id.button21 -> LaunchModeBActivity.open(this)
            R.id.button22 -> LaunchModeCActivity.open(this)
            R.id.button23 -> LaunchModeDActivity.open(this)
            R.id.button24 -> SingleInstanceActivity.open(this)
            R.id.button26 -> SingleTaskActivity.open(this)
            R.id.button27 -> SingleTopActivity.open(this)
            else -> {}
        }
    }
}
