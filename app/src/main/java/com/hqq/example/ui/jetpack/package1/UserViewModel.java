package com.hqq.example.ui.jetpack.package1;

import android.view.View;

import androidx.lifecycle.MutableLiveData;

import com.hqq.core.ui.vm.BaseViewModel;
import com.hqq.example.ui.jetpack.livedata.User;


/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.package1
 * @FileName :   UserModel
 * @Date : 2020/7/21 0021  下午 3:56
 * @Email : qiqiang213@gmail.com
 * @Descrive : ViewModel
 */
public class UserViewModel extends BaseViewModel {
    MutableLiveData<User> mLiveData = new MutableLiveData<>();
    MutableLiveData<String> mButtonText = new MutableLiveData<>("更新User数据");

    MutableLiveData<String> mString;

    public UserViewModel() {
        getUser();
    }


    public UserViewModel setLiveData(User user) {
        mLiveData.setValue(user);
        return this;
    }

    public MutableLiveData<User> getLiveData() {
        return mLiveData;
    }


    public void getUser() {
        setLiveData(new UserDataRepository().getUser());
    }

    public MutableLiveData<String> getButtonText() {
        return mButtonText;
    }

    public void update(View view) {
        User user = mLiveData.getValue().setLevel((int) (Math.random() * 77));
        setLiveData(user);
    }

}
