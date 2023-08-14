package com.easy.example.ui.jetpack.package1

import android.os.Handler
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.easy.core.ui.base.BaseViewModel
import com.easy.example.ui.jetpack.livedata.User

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui.jetpack.package1
 * @FileName :   UserModel
 * @Date : 2020/7/21 0021  下午 3:56
 * @Email : qiqiang213@gmail.com
 * @Descrive : ViewModel
 */
class UserViewModel : BaseViewModel() {
    var liveData = MutableLiveData<com.easy.example.ui.jetpack.livedata.User>()
    var buttonText = MutableLiveData("更新User数据")
    var mString: MutableLiveData<String>? = null
    fun setLiveData(user: com.easy.example.ui.jetpack.livedata.User): UserViewModel {
        liveData.value = user
        return this
    }

    val user: Unit
        get() {
            setLiveData(com.easy.example.ui.jetpack.package1.UserDataRepository().user)
        }

    fun update(view: View?) {
        val user = liveData.value!!.setLevel((Math.random() * 77).toInt())
        setLiveData(user)
    }

    fun openActivity(view: View?) {
        startActivity(MvvmTestActivity::class.java)
    }

    fun showLoading(view: View?) {
        showLoading(true)
        Handler().postDelayed({ showLoading(false) }, 3 * 1000.toLong())
    }

    init {
        user
    }
}