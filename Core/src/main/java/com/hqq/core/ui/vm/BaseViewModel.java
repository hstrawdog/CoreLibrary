package com.hqq.core.ui.vm;

import android.app.Activity;
import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hqq.core.lifecycle.BaseLifecycleObserver;
import com.hqq.core.utils.log.LogUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui.vm
 * @FileName :   BaseViewModel
 * @Date : 2020/7/27 0027  下午 2:39
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 * 1.  基本VM  包含 Toast LoadingView  以及不建议使用的OpenActivity
 * ViewModel 对象存在的时间范围是获取 ViewModel 时传递给 ViewModelProvider 的 Lifecycle。ViewModel 将一直留在内存中，
 * 直到限定其存在时间范围的 Lifecycle 永久消失：对于 Activity，是在 Activity 完成时；而对于 Fragment，是在 Fragment 分离时。
 * -> 与传递的this 相关
 * -------
 * 2.  实现lifeCycle接口  -> 问题 viewModel的lifeCycle中onCreate的方法的执行顺序是否与init中绑定有关系呢
 * ViewModel 中的 OnCreate 为什么是在BaseActivity的InitView 之后
 */
public abstract class BaseViewModel extends ViewModel implements BaseLifecycleObserver {
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
     * View 的intent 对象
     */
    Bundle mBundle;

    /**
     * ViewModel 的部分数据可以在这边进行初始化
     * 建议  单独方法给Activity 进行调用
     * 避免 出现 ViewModel onCreate 监听比 Activity Init 方法执行的早出现问题
     * 实际开发要是没有问题的话可以 移除此注释
     */
    @Override
    public void onCrete() {
        LogUtils.e(" BaseViewModel           onCrete ");
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onAny() {

    }

    public BaseViewModel setBundle(Bundle bundle) {
        mBundle = bundle;
        return this;
    }

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
    public static class OpenActivityComponent extends MutableLiveData {
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
