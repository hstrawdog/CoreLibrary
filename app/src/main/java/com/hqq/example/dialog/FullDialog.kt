package com.hqq.example.dialog

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.hqq.core.adapter.BaseFragmentAdapter
import com.hqq.core.ui.dialog.BaseDialog
import com.hqq.example.R
import com.hqq.example.ui.tab.layout.TabFragment

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.coreapp.dialog
 * @FileName :   FullDialog
 * @Date : 2019/5/24 0024  上午 9:14
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class FullDialog : BaseDialog() {
    override val layoutId: Int = R.layout.dialog_full

    override fun initConfig() {}
    override val animation: Int = R.anim.fade_in

    override fun initView() {
        val mVpPage = rootView!!.findViewById<ViewPager2>(R.id.vp_page)
        val mTbTablayout1 = rootView!!.findViewById<TabLayout>(R.id.tb_layout)
        val adapter = ViewPageAdapter(this)
        mVpPage.adapter = adapter
        adapter.setupWithViewPager(mTbTablayout1, mVpPage)
    }

    internal class ViewPageAdapter(fragment: Fragment) : BaseFragmentAdapter(fragment) {
        init {
            stringSparseArray.append(0, "咖啡")
            stringSparseArray.append(1, "奶茶")
            stringSparseArray.append(2, "菊花茶")
            stringSparseArray.append(3, "霸王杯")
            stringSparseArray.append(4, "冬瓜茶")
        }

        override fun newFragment(position: Int): Fragment {
            return TabFragment()
        }
    }

    companion object {
        fun showDialog(fragmentManager: FragmentManager?) {
            val fragment = FullDialog()
            fragment.show(fragmentManager!!)
        }
    }
}