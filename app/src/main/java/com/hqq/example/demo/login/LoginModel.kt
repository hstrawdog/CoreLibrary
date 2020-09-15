package com.hqq.example.demo.login

import android.view.View
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.hqq.core.ui.vm.BaseViewModel
import com.hqq.example.demo.net.HttpManager.login

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo
 * @FileName :   LoginModel
 * @Date : 2020/7/28 0028  下午 3:53
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
class LoginModel : BaseViewModel() {
    var mPhone: MutableLiveData<String> = MediatorLiveData()
    var mPassWord: MutableLiveData<String> = MediatorLiveData()
    fun onLoginClick(view: View?) {
        setShowToast(mPhone.value.toString() + "" + mPassWord.value)
        login(loadingView)
    }

    val phone: String?
        get() = mPhone.value

    fun setPhone(phone: String): LoginModel {
        mPhone.value = phone
        return this
    }

    val passWord: String?
        get() = mPassWord.value

    fun setPassWord(passWord: String): LoginModel {
        mPassWord.value = passWord
        return this
    }
}