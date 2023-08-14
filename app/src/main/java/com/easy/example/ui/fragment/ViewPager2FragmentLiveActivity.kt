package com.easy.example.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.easy.core.adapter.BaseFragmentAdapter
import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.example.databinding.ActivityViewPagerFragmetBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.fragment
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