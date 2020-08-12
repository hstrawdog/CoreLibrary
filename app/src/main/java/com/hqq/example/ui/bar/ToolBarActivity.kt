package com.hqq.example.ui.bar

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.utils.ToastUtils.showToast
import com.hqq.core.utils.statusbar.StatusBarManager.setStatusBarModel
import com.hqq.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.iblibrary.ui.activity
 * @FileName :   ToolBarActivity
 * @Date : 2018/11/22 0022
 * @Descrive : TODO
 * @Email :  qiqiang213@gmail.com
 */
class ToolBarActivity : BaseActivity() {
    override fun getLayoutViewId(): Int {
        return R.layout.activity_tool_bar
    }

    override fun initView() {

//        mRootViewBuild.getDefToolBar().setRightTextView("分享", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showToast(mActivity, "点击分享");
//
//            }
//        });
        mRootViewBuild?.getDefToolBar<Any>()!!.addRightTextView("分享", View.OnClickListener { showToast(mActivity, "点击分享") })
        mRootViewBuild?.getDefToolBar<Any>()!!.addRightTextView("分享", R.color.color_333, View.OnClickListener { showToast(mActivity, "点击分享") })
        //        mRootViewBuild.getDefToolBar().addRightTextView("分享2", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ToastUtils.showToast(mActivity, "点击分享2");
//
//            }
//        });
        mRootViewBuild?.getDefToolBar<Any>()!!.addRightImageView(R.mipmap.ic_more, View.OnClickListener { showToast(mActivity, "点击more") })
        findViewById<View>(R.id.button).setOnClickListener(this)
        findViewById<View>(R.id.button2).setOnClickListener(this)
        findViewById<View>(R.id.button3).setOnClickListener(this)
        findViewById<View>(R.id.button4).setOnClickListener(this)
        findViewById<View>(R.id.button5).setOnClickListener(this)
        findViewById<View>(R.id.button6).setOnClickListener(this)
        findViewById<View>(R.id.button7).setOnClickListener(this)
        findViewById<View>(R.id.button8).setOnClickListener(this)
        findViewById<View>(R.id.button12).setOnClickListener(this)
        findViewById<View>(R.id.button13).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_bar_right -> {
            }
            R.id.button5 -> mRootViewBuild?.getDefToolBar<Any>()!!.setDefStatusColor(ContextCompat.getColor(mActivity!!, R.color.color_77400a))
            R.id.button4 -> mRootViewBuild?.getDefToolBar<Any>()!!.toolBarBg.setImageResource(R.color.white)
            R.id.button7 -> mRootViewBuild?.getDefToolBar<Any>()!!.toolBarBg.setImageResource(R.color.color_000)
            R.id.button3 -> mRootViewBuild?.getDefToolBar<Any>()!!.toolBarBg.setImageResource(R.color.color_77400a)
            R.id.button -> setStatusBarModel(mActivity!!.window, true)
            R.id.button2 -> setStatusBarModel(mActivity!!.window, false)
            R.id.button6 -> mRootViewBuild?.getDefToolBar<Any>()!!.setToolbarTitle("new标题")
            R.id.button8 -> mRootViewBuild?.getDefToolBar<Any>()!!.setToolBarColor(R.color.color_main)
            R.id.button12 -> SettingToolBarActivity.open(this)
            R.id.button13 -> SearchBarActivity.open(this)
            else -> {
            }
        }
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, ToolBarActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}