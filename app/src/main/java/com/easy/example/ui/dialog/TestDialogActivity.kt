package com.easy.example.ui.dialog

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.easy.core.ui.dialog.DialogViewBuilder
import com.easy.core.ui.base.BaseActivity
import com.easy.core.kt.open
import com.easy.core.ui.dialog.SelectDialog
import com.easy.core.utils.ToastUtils
import com.easy.example.R
import com.easy.example.dialog.*

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.ui.activity
 * @FileName :   TestDialogActivity
 * @Date : 2019/5/24 0024
 * @Email :  qiqiang213@gmail.com
 * @Describe : TODO
 */
class TestDialogActivity : BaseActivity() {

    override fun getLayoutViewId(): Int {
        return R.layout.activity_test_dialog
    }

    override fun initView() {
        findViewById<View>(R.id.button9).setOnClickListener(this)
        findViewById<View>(R.id.button14).setOnClickListener(this)
        findViewById<View>(R.id.button15).setOnClickListener(this)
        findViewById<View>(R.id.button16).setOnClickListener(this)
        findViewById<View>(R.id.button10).setOnClickListener(this)
        findViewById<View>(R.id.button17).setOnClickListener(this)
        findViewById<View>(R.id.button18).setOnClickListener(this)
        findViewById<View>(R.id.button28).setOnClickListener(this)
        findViewById<View>(R.id.button51).setOnClickListener(this)
        findViewById<View>(R.id.button52).setOnClickListener(this)
        findViewById<View>(R.id.button53).setOnClickListener(this)
        findViewById<View>(R.id.button54).setOnClickListener(this)
        findViewById<View>(R.id.button65).setOnClickListener(this)
        findViewById<View>(R.id.button66).setOnClickListener(this)
        findViewById<View>(R.id.button67).setOnClickListener(this)
        findViewById<View>(R.id.button68).setOnClickListener(this)
        findViewById<View>(R.id.button95).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button9 -> {
                open(PopupWindowIndexActivity::class.java)
            }

            R.id.button10 -> {
                val mFullBottomSheetFragments = FoldFullBottomSheet()
                mFullBottomSheetFragments.show(supportFragmentManager)
            }

            R.id.button18 -> com.easy.example.dialog.SelectDialog.showDialog(supportFragmentManager)
            R.id.button14 -> BottomDialog.showDialog(supportFragmentManager)
            R.id.button15 -> FullDialog.showDialog(supportFragmentManager)
            R.id.button16 -> RightDialog.showDialog(supportFragmentManager)
            R.id.button17 -> LeftDialog.showDialog(supportFragmentManager)
            R.id.button28 -> SelectDialog.Builder()
                .setBaseViewBuilderHolder(ViewBuilderA())
                .create()
                .show(supportFragmentManager)

            R.id.button51 -> SelectDialog.Builder()
                .setBaseViewBuilderHolder(object : DialogViewBuilder() {

                    override fun getLayoutViewId(): Int {
                        return R.layout.view_holder_a
                    }

                    override fun initView() {}
                    override fun onClick(view: View) {}
                })
                .create()
                .show(supportFragmentManager)

            R.id.button52 -> SelectDialog.Builder()
                .setTitle("标题")
                .setBaseViewBuilderHolder(object : DialogViewBuilder() {
                    override fun getLayoutViewId(): Int {
                        return -1
                    }

                    override fun getLayoutView(viewGroup: ViewGroup): View? {
                        val textView = TextView(this@TestDialogActivity)
                        textView.text = "我是代码中创建的View"
                        textView.gravity = Gravity.CENTER
                        textView.layoutParams =
                            LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
                        return textView
                    }

                    override fun initView() {}
                    override fun onClick(view: View) {}
                })
                .create()
                .show(supportFragmentManager)

            R.id.button53 -> SelectDialog.Builder()
                .setTitle("标题")
                .setContent("我是内容")
                .setPositiveButton("确定2") { dialog, which -> ToastUtils.showToast("确定2") }
                .setOnCancelListener("取消1") { dialog, which -> ToastUtils.showToast("取消1") }
                .create()
                .show(supportFragmentManager)

            R.id.button54 -> SelectDialog.Builder()
                .setTitle("标题")
                .setContent("我是内容")
                .setPositiveButton("确定2") { dialog, which -> ToastUtils.showToast("确定2") }
                .setNeutralButton("中立") { dialog, which -> ToastUtils.showToast("中立") }
                .setOnCancelListener("取消1") { dialog, which -> ToastUtils.showToast("取消1") }
                .create()
                .show(supportFragmentManager)

            R.id.button65 -> SelectDialog.Builder()
                .setTitle("标题")
                .setContent("我是内容")
                .setPositiveButton("") { dialog, which -> ToastUtils.showToast("确定2") }
                .setNeutralButton("") { dialog, which -> ToastUtils.showToast("中立") }
                .setOnCancelListener("") { dialog, which -> ToastUtils.showToast("取消1") }
                .create()
                .show(supportFragmentManager)

            R.id.button66 -> SelectDialog.Builder()
                .setTitle("")
                .setContent("我是内容")
                .setPositiveButton("") { dialog, which -> ToastUtils.showToast("确定2") }
                .setNeutralButton("") { dialog, which -> ToastUtils.showToast("中立") }
                .setOnCancelListener("") { dialog, which -> ToastUtils.showToast("取消1") }
                .create()
                .show(supportFragmentManager)

            R.id.button67 -> {
                val mFullBottomSheetFragment = FullBottomSheet()
                mFullBottomSheetFragment.show(supportFragmentManager)
            }

            R.id.button68 -> {
                val mFullBottomSheetFragment = FoldFullBottomSheet()
                mFullBottomSheetFragment.show(supportFragmentManager)
            }
            R.id.button95 -> {


                SelectDialog.Builder()
                    .setTitle("我是标题")
                    .setContent("我是内容")
                    .setDividingLine(false)
                    .setOnCancelListener("") { dialog, which -> ToastUtils.showToast("取消1") }
                    .create()
                    .show(supportFragmentManager)
            }
        }
    }

    open class ViewBuilderA : DialogViewBuilder() {
        override fun initView() {}
        override fun onClick(v: View) {}
        override fun getLayoutViewId(): Int {
            return R.layout.view_holder_a
        }
    }


}