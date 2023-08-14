package com.easy.example.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.easy.core.adapter.BaseFragmentAdapter
import com.easy.example.ui.fragment.MainFragment

/**
 * @Author : huangqiqiang
 * @Package : com.easy.iblibrary.ui.activity.fragment
 * @FileName :   IFragmentAdapter
 * @Date : 2018/11/23 0023  上午 9:40
 * @Descrive :
 * @Email :  qiqiang213@gmail.com
 */
class IFragmentAdapter : BaseFragmentAdapter {
    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)

    override fun getItemCount(): Int {
        return 10
    }

    override fun newFragment(position: Int): Fragment {
        return MainFragment.getIFragment(position)
    }
}