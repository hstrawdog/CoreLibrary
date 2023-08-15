package com.easy.example.ui.recycle

import com.easy.core.recycle.RecycleViewBanner
import com.easy.core.ui.base.BaseActivity
import com.easy.example.R

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.ui.activity
 * @FileName :   BannerActivity
 * @Date : 2018/12/20 0020
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
class BannerActivity : BaseActivity() {
    var mRcBanner: RecycleViewBanner? = null
    var mRcBanner2: RecycleViewBanner? = null



    override fun getLayoutViewId(): Int {
        return R.layout.activity_banner
    }

    override fun initView() {
        mRcBanner = findViewById(R.id.rc_banner)
        mRcBanner2 = findViewById(R.id.rc_banner2)
        val list = mutableListOf<Any>()
        list.add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
        list.add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
        list.add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
        list.add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
        list.add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")

        mRcBanner?.setRvBannerData(list)
        val list2: MutableList<Any> = ArrayList()
        list2.add(R.mipmap.ic_banner2)
        list2.add(R.mipmap.ic_banner2)
        mRcBanner2?.setRvBannerData(list2)
    }
}