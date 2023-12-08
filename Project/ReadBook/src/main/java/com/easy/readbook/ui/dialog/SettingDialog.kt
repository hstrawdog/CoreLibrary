package com.easy.readbook.ui.dialog

import android.view.Gravity
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easy.core.ui.dialog.BaseDialog
import com.easy.core.utils.ScreenUtils
import com.easy.readbook.R
import com.easy.readbook.weight.page.PageMode
import com.easy.readbook.weight.page.PageStyle
import com.easy.readbook.weight.page.ReadSettingManager
import com.easy.readbook.weight.page.loader.PageLoader

/**
 * @Author : huangqiqiang
 * @Package : com.easy.readbook.ui.book
 * @Date : 上午 10:16
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SettingDialog(private var mPageLoader: com.easy.readbook.weight.page.loader.PageLoader) : BaseDialog() {

    override fun getDialogLayoutId(): Int {
        return   R.layout.dialog_setting
    }

    override fun getGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun getDialogWeight(): Int {
        return WindowManager.LayoutParams.WRAP_CONTENT
    }

    override fun getDialogHeight(): Int {
        return WindowManager.LayoutParams.MATCH_PARENT
    }
    var settingManager = ReadSettingManager.getInstance()
    override fun initView() {
        var tvSize = rootView?.findViewById<TextView>(R.id.tv_size)

        //字体大小调节
        rootView?.findViewById<TextView>(R.id.tv_font_minus)?.setOnClickListener {
            val fontSize = ReadSettingManager.getInstance().textSize - 1
            if (fontSize < 0) {
                return@setOnClickListener
            }
            mPageLoader.setTextSize(fontSize)
            tvSize?.text = "$fontSize"
        }

        rootView?.findViewById<TextView>(R.id.tv_font_plus)?.setOnClickListener {
            val fontSize = ReadSettingManager.getInstance().textSize + 1
            mPageLoader.setTextSize(fontSize)
            tvSize?.text = "$fontSize"
        }
        rootView?.findViewById<TextView>(R.id.tv_font_default)?.setOnClickListener {
            val fontSize = ScreenUtils.spToPx(16f).toInt()
            ReadSettingManager.getInstance().textSize = fontSize
            mPageLoader.setTextSize(fontSize)
            tvSize?.text = "$fontSize"
        }

        rootView?.findViewById<RecyclerView>(R.id.rc_bg)?.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = BgAdapter().apply {
                settingManager.pageStyle.bgColor
                setOnItemClickListener { adapter, view, position ->
                    mPageLoader.setPageStyle(PageStyle.values()[position])
                    settingManager.pageStyle = PageStyle.values()[position]
                    adapter.notifyDataSetChanged()
                }
            }
        }

        when (settingManager.pageMode) {
            com.easy.readbook.weight.page.PageMode.SIMULATION -> {
                rootView?.findViewById<RadioButton>(R.id.rb_simulation)?.isChecked = true
            }
            com.easy.readbook.weight.page.PageMode.COVER -> {
                rootView?.findViewById<RadioButton>(R.id.rb_cover)?.isChecked = true
            }
            com.easy.readbook.weight.page.PageMode.NONE -> {
                rootView?.findViewById<RadioButton>(R.id.rb_scroll)?.isChecked = true
            }
            com.easy.readbook.weight.page.PageMode.SCROLL -> {
                rootView?.findViewById<RadioButton>(R.id.rb_none)?.isChecked = true
            }

            else -> {}
        }
        tvSize?.text = settingManager.textSize.toString()

        // 设置模式
        rootView?.findViewById<RadioGroup>(R.id.rg_page_mode)
            ?.setOnCheckedChangeListener { group, checkedId ->
                val pageMode: com.easy.readbook.weight.page.PageMode = when (checkedId) {
                    R.id.rb_simulation -> com.easy.readbook.weight.page.PageMode.SIMULATION
                    R.id.rb_cover -> com.easy.readbook.weight.page.PageMode.COVER
                    R.id.rb_scroll -> com.easy.readbook.weight.page.PageMode.SCROLL
                    R.id.rb_none -> com.easy.readbook.weight.page.PageMode.NONE
                    else -> com.easy.readbook.weight.page.PageMode.SIMULATION
                }
                mPageLoader.setPageMode(pageMode)

            }
    }
}