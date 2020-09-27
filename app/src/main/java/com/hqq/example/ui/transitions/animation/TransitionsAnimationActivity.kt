package com.hqq.example.ui.transitions.animation

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.hqq.core.ui.list.BaseListActivity
import com.hqq.example.R
import com.hqq.example.adapter.StringListAdapter

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.transitions.animation
 * @FileName :   TransitionsAnimationActivity
 * @Date : 2019/10/28 0028  下午 1:38
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class TransitionsAnimationActivity : BaseListActivity(), OnItemClickListener {
    override val baseAdapter: StringListAdapter = StringListAdapter()
    override fun initData() {
        baseAdapter.setOnItemClickListener(this)
        baseAdapter.addData("overridePendingTransition")
        baseAdapter.addData("makeCustomAnimation")
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when (position) {
            0 -> open(this)
            1 -> openMakeCustomAnimation(this)
            else -> {
            }
        }
    }

    override fun initAnimEnter() {}

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, TransitionsAnimationActivity::class.java)
            context.startActivityForResult(starter, -1)
            context.overridePendingTransition(R.anim.fade_in, R.anim.fade_in)
        }

        /**
         * md风格
         * @param activity
         */
        private fun openMakeCustomAnimation(activity: Activity) {
            val starter = Intent(activity, TransitionsAnimationActivity::class.java)
            val compat = ActivityOptionsCompat.makeCustomAnimation(activity,
                    R.anim.slide_left_in, R.anim.slide_left_in)
            ActivityCompat.startActivity(activity,
                    starter, compat.toBundle())
        }
    }
}