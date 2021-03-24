package com.qq.readbook.ui.book

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.hqq.core.CoreConfig
import com.hqq.core.ui.base.BaseViewModel


/**
 * @Author : huangqiqiang
 * @Package : com.qq.readbook.ui.book
 * @Date : 下午 2:27
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ReadBookViewModel : BaseViewModel() {
    /**
     *   是否点击亮度
     */
    var showLight = MutableLiveData<Boolean>(false)

    /**
     *显示缓存
     */
    var showCache = MutableLiveData<Boolean>(false)

    /**
     *  点击亮度
     */
    fun onClickLight(view: View) {
        showLight.value = !(showLight.value as Boolean)
    }

    /**
     *  是否是日间模式
     */
    val themeMode = MutableLiveData<Boolean>(true)

    /**
     *  显示缓存进度
     */
    val cacheSchedule = MutableLiveData(false)

    /**
     * 是否显示缓存
     * @param view View
     */
    fun onCache(view: View) {
        showCache.value = !(showCache.value as Boolean)
    }

    /**
     *  返回
     */
    fun onBack(view: View) {
        finish()
    }

    /**
     * 切换主题
     * @param view View
     */
    fun onThemeMode(view: View) {
        themeMode.value = !(themeMode.value as Boolean)
    }

    /**
     *  按钮空白位置
     */
    fun onLayoutMenu(view: View) {

    }

    /**
     *  打开当前地址
     */
    fun onOpenUrl(v: View) {
        var url = (v as TextView).text.toString()
        val intent = Intent()
        intent.action = "android.intent.action.VIEW"
        intent.data = Uri.parse(url)
        CoreConfig.get().currActivity?.startActivity(intent)

    }


}