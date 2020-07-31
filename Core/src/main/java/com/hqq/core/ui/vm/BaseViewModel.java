package com.hqq.core.ui.vm;

import android.app.Activity;
import android.os.Bundle;

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
    /**
     * Activity跳转对象
     */
    MutableLiveData<OpenActivityComponent> mOpenActivityComponentMutableLiveData = new MutableLiveData<>();

    /**
     * 不建议使用此方法进行跳转
     * Activity 应当是要有统一的入口 方便维护与排查入参数
     *
     * @param cls
     */
    public void startActivity(Class<? extends Activity> cls) {
        startActivity(cls, null);
    }

    /**
     * @param cls
     * @param bundle
     */
    public void startActivity(Class<? extends Activity> cls, Bundle bundle) {
        startActivity(cls, bundle, -1);

    }

    /**
     * @param cls        Activity 类型
     * @param bundle     传递的值
     * @param resultCode 回调code
     */
    public void startActivity(Class<? extends Activity> cls, Bundle bundle, int resultCode) {
        mOpenActivityComponentMutableLiveData.setValue(new OpenActivityComponent(cls, bundle, resultCode));
    }

    public BaseViewModel setShowLoading(Boolean showLoading) {
        mShowLoading.setValue(showLoading);
        return this;
    }


    public BaseViewModel setShowToast(String showToast) {
        mShowToast.setValue(showToast);
        return this;
    }

    /**
     * 打开Activity的对象零件
     */
  public   static class OpenActivityComponent extends MutableLiveData {
        Class<? extends Activity> mActivityClass;
        Bundle mBundle;
        int mActivityResult;


        public OpenActivityComponent(Class<? extends Activity> cls) {
            this(cls, null);
        }

        public OpenActivityComponent(Class<? extends Activity> cls, Bundle bundle) {
            this(cls, bundle, -1);

        }

        public OpenActivityComponent(Class<? extends Activity> cls, Bundle bundle, int activityResult) {
            mActivityClass = cls;
            mBundle = bundle;
            mActivityResult = activityResult;
        }


    }

}
