package com.easy.example.ui.recycle

import com.easy.core.recycle.RecycleViewBanner
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.example.R
import com.easy.example.databinding.ActivityBannerBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.coreapp.ui.activity
 * @FileName :   BannerActivity
 * @Date : 2018/12/20 0020
 * @Email :  qiqiang213@gmail.com
 * @Describe : TODO
 */
class BannerActivity : BaseViewBindingActivity<ActivityBannerBinding>() {
    var mRcBanner: RecycleViewBanner? = null
    var mRcBanner2: RecycleViewBanner? = null


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
        list2.add(R.mipmap.ic_banner2)
        list2.add(R.mipmap.ic_banner2)
        list2.add(R.mipmap.ic_banner2)
        list2.add(R.mipmap.ic_banner2)
        list2.add(R.mipmap.ic_banner2)
        list2.add(R.mipmap.ic_banner2)
        mRcBanner2?.setRvBannerData(list2)



        binding.button100.setOnClickListener {
            mRcBanner2?.smoothScrollToPosition(100)
            mRcBanner?.scrollToPosition(100)
        }

    }
}