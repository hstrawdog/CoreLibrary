package com.hqq.example.ui.jetpack.package1

import android.os.Handler
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.hqq.core.ui.base.BaseViewModel
import com.hqq.example.ui.jetpack.livedata.User

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.package1
 * @FileName :   UserModel
 * @Date : 2020/7/21 0021  下午 3:56
 * @Email : qiqiang213@gmail.com
 * @Descrive : ViewModel
 */
class UserViewModel : BaseViewModel() {
    var liveData = MutableLiveData<User>()
    var buttonText = MutableLiveData("更新User数据")
    var mString: MutableLiveData<String>? = null
    fun setLiveData(user: User): UserViewModel {
        liveData.value = user
        return this
    }

    val user: Unit
        get() {
            setLiveData(UserDataRepository().user)
        }

    fun update(view: View?) {
        val user = liveData.value!!.setLevel((Math.random() * 77).toInt())
        setLiveData(user)
    }

    fun openActivity(view: View?) {
        startActivity(MvvmTestActivity::class.java)
    }

    fun showLoading(view: View?) {
        setShowLoading(true)
        Handler().postDelayed({ setShowLoading(false) }, 3 * 1000.toLong())
    }

    init {
        user
    }
}