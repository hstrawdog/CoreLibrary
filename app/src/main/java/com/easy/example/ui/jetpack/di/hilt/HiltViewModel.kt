package com.easy.example.ui.jetpack.di.hilt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.jetpack.di.hilt
 * @FileName :   HiltViewModel
 * @Date : 2020/8/13 0013  下午 2:37
 * @Email : qiqiang213@gmail.com
 * @Describe :
 * UserHilt  自动注入
 * ViewModelInject 表示为ViewModel 对象  可以使用 委托 by viewModels() 创建
 */
class HiltViewModel  constructor(val userHilt: UserHilt) : ViewModel() {

    var data = MutableLiveData<UserHilt>()


    fun getData(): LiveData<UserHilt> {
        data.postValue(userHilt)
        return data
    }

}