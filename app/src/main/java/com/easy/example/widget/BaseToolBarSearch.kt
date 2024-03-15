package com.easy.example.widget

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.easy.core.toolbar.BaseToolBar
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com..sellercenter.toolsbar
 * @FileName :   BaseToolBarSearch
 * @Date : 2018/11/27 0027  下午 1:14
 * @Describe :
 * @Email :  qiqiang213@gmail.com
 */
class BaseToolBarSearch() : BaseToolBar() {

    override var toolBarBg: ImageView? = null

    override fun iniToolBar(activity: Activity, viewGroup: ViewGroup?): View {
        val mToolbar = LayoutInflater.from(activity.baseContext).inflate(R.layout.layout_toolbar_order_search, viewGroup, false) as Toolbar
        if (activity is AppCompatActivity) {
            activity.setSupportActionBar(mToolbar)
        }
        mToolbar.findViewById<View>(R.id.iv_bar_back).setOnClickListener { activity.onBackPressed() }
        iniBase(mToolbar)
        return mToolbar
    }

    private fun iniBase(mToolbar: Toolbar) {
        mToolbar.findViewById<View>(R.id.iv_delete).setOnClickListener { (mToolbar.findViewById<View>(R.id.edt_search) as EditText).setText("") }
        (mToolbar.findViewById<View>(R.id.edt_search) as EditText).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length > 0) {
                    mToolbar.findViewById<View>(R.id.iv_delete).visibility = View.VISIBLE
                } else {
                    mToolbar.findViewById<View>(R.id.iv_delete).visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun setToolBarColor(colorId: Int) {}
    override fun setToolbarTitle(title: CharSequence?) {

    }
    val deleteView: ImageView
        get() = rootView!!.findViewById(R.id.iv_delete)

    val searchView: EditText
        get() = rootView!!.findViewById(R.id.edt_search)

    val rightTextView: TextView
        get() = rootView!!.findViewById(R.id.tv_bar_right)

    fun setRightTextView(text: String?, onClickListener: View.OnClickListener?) {
        val textView = rightTextView
        textView.text = text
        textView.visibility = View.VISIBLE
        textView.setOnClickListener(onClickListener)
    }

    fun setRightTextView(onClickListener: View.OnClickListener?) {
        val textView = rightTextView
        textView.visibility = View.VISIBLE
        textView.setOnClickListener(onClickListener)
    }
}