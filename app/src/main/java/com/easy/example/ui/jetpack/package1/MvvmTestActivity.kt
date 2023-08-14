package com.easy.example.ui.jetpack.package1

import android.app.Activity
import android.content.Intent
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.easy.core.ui.base.BaseVmActivity
import com.easy.core.utils.log.LogUtils.e
import com.easy.example.R
import com.easy.example.databinding.ActivityMvvmBinding

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.jetpack.package1
 * @FileName :   Package1Activity
 * @Date : 2020/7/21 0021  下午 3:53
 * @Email :  qiqiang213@gmail.com
 * @Descrive : ViewBinding + ViewModel + liveData
 */
class MvvmTestActivity : BaseVmActivity<UserViewModel, ActivityMvvmBinding, >() {
    override fun addViewModel() {
        e(" MvvmTestActivity   addViewModel 1 ")
        //       mBinding.setVariable(BR.vm, new ViewModelProvider(this).get(UserViewModel.class));
        binding.vm = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.title = "这是一个变量String"
        val map = ObservableArrayMap<String, String>()
        map["key1"] = "ObservableArrayMap  value1"
        map["key2"] = "ObservableArrayMap value2"
        map["key3"] = "ObservableArrayMap value3"
        map["key4"] = "ObservableArrayMap value4"
        binding.map = map
        val observableArrayList: ObservableArrayList<String> = ObservableArrayList<String>()
        observableArrayList.add("key1")
        observableArrayList.add("key2")
        binding.setList(observableArrayList)
        e(" MvvmTestActivity   addViewModel 2 ")
    }

    override val bindingViewModelId: Int
        get() = 0

    override val layoutId: Int
        get() = R.layout.activity_mvvm

    override fun initViews() {
        binding?.vm?.liveData?.observeForever { }
    }

    object Fields {
        val AGE = MutableLiveData(1)
        const val AGE2 = 1
    }

    companion object {
        fun open(context: Activity) {
            val starter = Intent(context, MvvmTestActivity::class.java)
            context.startActivityForResult(starter, -1)
        }
    }
}