package com.hqq.example.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.hqq.core.adapter.BaseFragmentAdapter
import com.hqq.core.ui.base.BaseActivity
import com.hqq.core.ui.base.BaseViewBindingActivity
import com.hqq.example.databinding.ActivityViewPagerFragmetBinding

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.fragment
 * @Date : 10:14
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class ViewPager2FragmentLiveActivity : BaseViewBindingActivity<ActivityViewPagerFragmetBinding>() {
    override fun initView() {
        binding.viewPager.adapter = ViewPageAdapter(this)
        (binding.viewPager.adapter as ViewPageAdapter).setupWithViewPager(binding.tbLayout, binding.viewPager)
        // 设置缓存大小
        binding.viewPager.offscreenPageLimit = 5

    }
    class ViewPageAdapter(ac: BaseActivity) : BaseFragmentAdapter(ac) {
        init {
            for (i in 0..10) {
                stringSparseArray.append(i, "$i")
            }
        }

        override fun newFragment(position: Int): Fragment {
            var fragment = LiveFragment()
            fragment.arguments = Bundle().apply {
                putString("position", position.toString())
            }
            return fragment
        }

    }


}