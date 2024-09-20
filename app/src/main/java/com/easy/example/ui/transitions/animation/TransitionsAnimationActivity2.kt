package com.easy.example.ui.transitions.animation

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.chad.library.adapter4.BaseQuickAdapter
import com.easy.core.ui.list.BaseListActivity
import com.easy.example.R
import com.easy.example.adapter.StringListAdapter

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.transitions.animation
 * @FileName :   TransitionsAnimationActivity
 * @Date : 2019/10/28 0028  下午 1:38
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class TransitionsAnimationActivity2 : BaseListActivity(), BaseQuickAdapter.OnItemClickListener<String> {
    override val adapter: StringListAdapter = StringListAdapter()
    override fun initData() {
        adapter.setOnItemClickListener(this)
        adapter.add("overridePendingTransition")
        adapter.add("makeCustomAnimation")
    }



    override fun initAnimEnter() {}

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, TransitionsAnimationActivity2::class.java)
            context.startActivityForResult(starter, -1)
            context.overridePendingTransition(R.anim.fade_in, R.anim.fade_in)
        }

        /**
         * md风格
         * @param activity
         */
        private fun openMakeCustomAnimation(activity: Activity) {
            val starter = Intent(activity, TransitionsAnimationActivity2::class.java)
            val compat = ActivityOptionsCompat.makeCustomAnimation(activity,
                    R.anim.slide_left_in, R.anim.slide_left_in)
            ActivityCompat.startActivity(activity,
                    starter, compat.toBundle())
        }
    }

    override fun onClick(adapter: BaseQuickAdapter<String, *>, view: View, position: Int) {
        when (position) {
            0 -> open(this)
            1 -> openMakeCustomAnimation(this)
            else -> {
            }
        }
    }
}