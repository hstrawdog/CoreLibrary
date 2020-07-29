package com.hqq.core.ui.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseViewModel
 * @Date : 2020/7/27 0027  下午 2:39
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
public abstract class BaseViewModel extends ViewModel {
    /**
     * 是否显示Loading
     */
    protected MutableLiveData<Boolean> mShowLoading = new MutableLiveData<>();
    /**
     * 显示Toast
     */
    MutableLiveData<String> mShowToast = new MutableLiveData<>();


    public BaseViewModel setShowLoading(Boolean showLoading) {
        mShowLoading.setValue(showLoading);
        return this;
    }


    public BaseViewModel setShowToast(String showToast) {
        mShowToast.setValue(showToast);
        return this;
    }

}
