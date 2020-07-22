package com.hqq.example.ui.jetpack.package1;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hqq.example.ui.jetpack.livedata.User;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example.ui.jetpack.package1
 * @FileName :   UserModel
 * @Date : 2020/7/21 0021  下午 3:56
 * @Email : qiqiang213@gmail.com
 * @Descrive : ViewModel
 */
public class UserViewModel extends ViewModel {
    MutableLiveData<User> mLiveData = new MutableLiveData<>();


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

    public void update(View view) {
        User user = mLiveData.getValue().setLevel((int) (Math.random() * 77));
        setLiveData(user);
    }

}
