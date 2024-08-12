package com.easy.core.toolbar

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
import  com.easy.core.R

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
        val mToolbar = LayoutInflater.from(activity.baseContext)
            .inflate(R.layout.layout_toolbar_order_search, viewGroup, false) as Toolbar
        if (activity is AppCompatActivity) {
            activity.setSupportActionBar(mToolbar)
        }
        mToolbar.findViewById<View>(R.id.iv_bar_back)
            .setOnClickListener { activity.onBackPressed() }
        iniBase(mToolbar)
        return mToolbar
    }

    private fun iniBase(mToolbar: Toolbar) {
        mToolbar.findViewById<View>(R.id.iv_delete)
            .setOnClickListener {
                (mToolbar.findViewById<View>(R.id.edt_search) as EditText).setText("")
            }
        (mToolbar.findViewById<View>(R.id.edt_search) as EditText).addTextChangedListener(object : TextWatcher {
            /**
             *  文本改变之前调用，传入了四个参数：
             * @param s CharSequence 文本改变之前的内容
             * @param start Int 文本开始改变时的起点位置，从0开始计算
             * @param count Int 要被改变的文本字数，即将要被替代的选中文本字数
             * @param after Int 改变后添加的文本字数，即替代选中文本后的文本字数
             * 该方法调用是在文本没有被改变，但将要被改变的时候调用，把四个参数组成一句话就是：
             * 在当前文本s中，从start位置开始之后的count个字符（即将）要被after个字符替换掉
             */
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            /**
             * 是在当文本改变时被调用，同样传入了四个参数：
             * @param s CharSequence 文本改变之后的内容
             * @param start Int 文本开始改变时的起点位置，从0开始计算
             * @param before Int  要被改变的文本字数，即已经被替代的选中文本字数
             * @param count Int  改变后添加的文本字数，即替代选中文本后的文本字数
             * 该方法调用是在文本被改变时，改变的结果已经可以显示时调用，把四个参数组成一句话就是：
             * 在当前文本s中，从start位置开始之后的before个字符（已经）被count个字符替换掉了
             */
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length > 0) {
                    mToolbar.findViewById<View>(R.id.iv_delete).visibility = View.VISIBLE
                } else {
                    mToolbar.findViewById<View>(R.id.iv_delete).visibility = View.GONE
                }
            }

            /**
             * 该方法是在执行完beforeTextChanged、onTextChanged两个方法后才会被调用，此时的文本s为最终显示给用户看到的文本。我们可以再对该文本进行下一步处理，比如把文本s显示在UI界面上
             * @param s Editable 改变后的最终文本
             */
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