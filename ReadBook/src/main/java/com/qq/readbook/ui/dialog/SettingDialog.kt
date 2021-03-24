package com.qq.readbook.ui.dialog

import android.view.Gravity
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hqq.core.ui.dialog.BaseDialog
import com.hqq.core.utils.ScreenUtils
import com.qq.readbook.R
import com.qq.readbook.weight.page.PageMode
import com.qq.readbook.weight.page.PageStyle
import com.qq.readbook.weight.page.ReadSettingManager
import com.qq.readbook.weight.page.loader.PageLoader

/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.ui.book
 * @Date : 上午 10:16
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SettingDialog(private var mPageLoader: PageLoader) : BaseDialog() {
    override val layoutId: Int = R.layout.dialog_setting
    override val gravity: Int = Gravity.BOTTOM
    override val weight: Int = WindowManager.LayoutParams.WRAP_CONTENT
    override val height: Int = WindowManager.LayoutParams.MATCH_PARENT
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
            PageMode.SIMULATION -> {
                rootView?.findViewById<RadioButton>(R.id.rb_simulation)?.isChecked = true
            }
            PageMode.COVER -> {
                rootView?.findViewById<RadioButton>(R.id.rb_cover)?.isChecked = true
            }
            PageMode.NONE -> {
                rootView?.findViewById<RadioButton>(R.id.rb_scroll)?.isChecked = true
            }
            PageMode.SCROLL -> {
                rootView?.findViewById<RadioButton>(R.id.rb_none)?.isChecked = true
            }
        }
        tvSize?.text = settingManager.textSize.toString()

        // 设置模式
        rootView?.findViewById<RadioGroup>(R.id.rg_page_mode)
            ?.setOnCheckedChangeListener { group, checkedId ->
                val pageMode: PageMode = when (checkedId) {
                    R.id.rb_simulation -> PageMode.SIMULATION
                    R.id.rb_cover -> PageMode.COVER
                    R.id.rb_scroll -> PageMode.SCROLL
                    R.id.rb_none -> PageMode.NONE
                    else -> PageMode.SIMULATION
                }
                mPageLoader.setPageMode(pageMode)

            }
    }
}