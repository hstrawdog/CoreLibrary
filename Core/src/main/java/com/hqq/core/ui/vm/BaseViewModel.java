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
public class BaseViewModel extends ViewModel {

    MutableLiveData<Boolean> mShowLoading = new MutableLiveData<>();


}
