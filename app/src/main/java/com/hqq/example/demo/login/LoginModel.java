package com.hqq.example.demo.login;

import android.view.View;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.hqq.core.ui.vm.BaseViewModel;
import com.hqq.example.demo.Repository;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.demo
 * @FileName :   LoginModel
 * @Date : 2020/7/28 0028  下午 3:53
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public  class LoginModel extends BaseViewModel {

    MutableLiveData<String> mPhone = new MediatorLiveData<>();
    MutableLiveData<String> mPassWord = new MediatorLiveData<>();


    public void onLoginClick(View view) {


        setShowToast(mPhone.getValue()+""+mPassWord.getValue());
        Repository.login(mShowLoading);
    }


    public String getPhone() {
        return mPhone.getValue();
    }

    public LoginModel setPhone(String phone) {
        mPhone.setValue(phone);
        return this;
    }

    public String getPassWord() {
        return mPassWord.getValue();
    }

    public LoginModel setPassWord(String passWord) {
        mPassWord.setValue(passWord);
        return this;
    }
}
