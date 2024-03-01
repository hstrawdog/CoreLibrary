package com.easy.example.ui.customize

import com.easy.core.ui.base.BaseActivity
import com.easy.core.ui.base.BaseViewBindingActivity
import com.easy.example.R
import com.easy.example.databinding.ActivityRecProgressBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.customize
 * @Date : 18:31
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class RecProgressActivity : BaseViewBindingActivity<ActivityRecProgressBinding>() {
//    override fun getLayoutViewId(): Int {
//        return R.layout.activity_rec_progress
//    }

    override fun initView() {

        var count = 1
        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0..100) {
                delay(500)
                withContext(Dispatchers.Main) {
                    binding.recLayout2.setProgress(count)
                    count += 1
                }
            }

        }

    }
}
