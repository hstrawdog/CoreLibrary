package com.hqq.example.ui.recycle

import com.hqq.core.recycle.RecycleViewBanner
import com.hqq.core.ui.base.BaseActivity
import com.hqq.example.R
import java.util.*

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.ui.activity
 * @FileName :   BannerActivity
 * @Date : 2018/12/20 0020
 * @Email :  qiqiang213@gmail.com
 * @Descrive : TODO
 */
class BannerActivity : BaseActivity() {
    var mRcBanner: RecycleViewBanner? = null
    var mRcBanner2: RecycleViewBanner? = null

    override val layoutViewId: Int
        get() = R.layout.activity_banner

    override fun initView() {
        mRcBanner = findViewById(R.id.rc_banner)
        mRcBanner2 = findViewById(R.id.rc_banner2)
        val list = ArrayList<String>()
        list.add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
        list.add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
        list.add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
        list.add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")
        list.add("http://pic2.sc.chinaz.com/files/pic/webjs1/201903/jiaoben6644.jpg")

        mRcBanner?.setRvBannerData(list as List<Nothing>)
        val list2: MutableList<Int> = ArrayList()
        list2.add(R.mipmap.ic_banner2)
        list2.add(R.mipmap.ic_banner2)
        mRcBanner2?.setRvBannerData(list2 as List<Nothing>)
    }
}