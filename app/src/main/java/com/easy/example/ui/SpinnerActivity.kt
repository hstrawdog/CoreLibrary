package com.easy.example.ui

import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.example.databinding.ActivitySpinnerBinding
import android.widget.AdapterView

import android.view.View
import android.widget.AdapterView.OnItemSelectedListener

import android.widget.ArrayAdapter
import android.widget.Spinner
import com.easy.core.utils.log.LogUtils


/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui
 * @Date : 10:57
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class SpinnerActivity : BaseViewBindingActivity<ActivitySpinnerBinding>() {
    private val starArray = arrayOf("水星", "金星", "地球", "火星", "木星", "土星")

    override fun initView() {
        initSpinner()

    }


    private fun initSpinner() {
        //声明一个下拉列表的数组适配器
        val starAdapter = ArrayAdapter(this, com.easy.example.R.layout.item_select, starArray)
        //设置数组适配器的布局样式
        starAdapter.setDropDownViewResource(com.easy.example.R.layout.item_dropdown)
        //从布局文件中获取名叫sp_dialog的下拉框
        val sp: Spinner = findViewById(com.easy.example.R.id.spinner)
        //设置下拉框的标题，不设置就没有难看的标题了
        sp.setPrompt("请选择行星")
        //设置下拉框的数组适配器
        sp.setAdapter(starAdapter)
        //设置下拉框默认的显示第一项
        sp.setSelection(0)
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp.setOnItemSelectedListener(MySelectedListener(starArray))
    }

    open class MySelectedListener( var starArray: Array<String>) : OnItemSelectedListener {
        override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, i: Int, l: Long) {
           LogUtils.e { "\"您选择的是：\" + ${starArray.get(i)}" }
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    }
}