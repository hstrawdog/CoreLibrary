package com.easy.example.ui.flow

import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.core.utils.log.LogUtils
import com.easy.example.databinding.ActivityFlowBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.flow
 * @Date : 10:15
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class FlowActivity : BaseViewBindingActivity<ActivityFlowBinding>() {

    var _list = ArrayList<String>().apply {
        add("1")
        add("2")
        add("3")
        add("4")
    }
    var _list2 = ArrayList<String>().apply {
        add("a")
        add("b")
        add("c")
        add("d")
    }

    override fun initView() {
        binding.button79.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                _list.asFlow()
                    .zip(_list2.asFlow()) { i, s ->
                        " $i $s"
                    }
                    .collect() {
                        LogUtils.e("     FlowActivity   collect        ${it}        ")
                    }
            }
        }
    }


}