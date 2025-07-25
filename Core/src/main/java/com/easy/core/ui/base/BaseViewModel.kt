package com.easy.core.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.lifecycle.*
import com.easy.core.common.TAG
import com.easy.core.utils.log.LogUtils

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui.vm
 * @FileName :   BaseViewModel
 * @Date : 2020/7/27 0027  下午 2:39
 * @Email : qiqiang213@gmail.com
 * @Describe :
 * 1.  基本VM  包含 Toast LoadingView  以及不建议使用的OpenActivity
 * ViewModel 对象存在的时间范围是获取 ViewModel 时传递给 ViewModelProvider 的 Lifecycle。ViewModel 将一直留在内存中，
 * 直到限定其存在时间范围的 Lifecycle 永久消失：对于 Activity，是在 Activity 完成时；而对于 Fragment，是在 Fragment 分离时。
 * -> 与传递的this 相关
 * -------
 * 2.  实现lifeCycle接口  -> 问题 viewModel的lifeCycle中onCreate的方法的执行顺序是否与init中绑定有关系呢
 * ViewModel 中的 OnCreate 为什么是在BaseActivity的InitView 之后
 */
abstract class BaseViewModel : ViewModel(), IBaseViewModel {
    /**
     * 是否显示Loading
     */
    val loadingView = MutableLiveData<Boolean>()

    /**
     * 显示Toast
     */
    val toast = MutableLiveData<String>()

    /**
     * Activity跳转对象
     */
    val openActivityComponentMutableLiveData = MutableLiveData<OpenActivityComponent>()

    /**
     * View 的intent 对象
     */
    var bundle: Bundle? = null

    /**
     * 回到上个页面
     */
    var goBack = MutableLiveData<GoBackComponent>()

    init {
        LogUtils.dMark( TAG.LIVE_TAG, "${this.toString()} BaseViewModel  init " )
    }

    /**
     * ViewModel 的部分数据可以在这边进行初始化
     * 建议  单独方法给Activity 进行调用
     * 避免 出现 ViewModel onCreate 监听比 Activity Init 方法执行的早出现问题
     * 实际开发要是没有问题的话可以 移除此注释
     */
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
       LogUtils.dMark( TAG.LIVE_TAG, "${this.toString()} BaseViewModel           onCrete ")
    }

    override fun initData(extras: Bundle?) {
        bundle = extras
       LogUtils.dMark( TAG.LIVE_TAG, "${this.toString()} BaseViewModel           initData ")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }


    /**
     * @param cls        Activity 类型
     * @param bundle     传递的值
     * @param resultCode 回调code
     */
    fun showLoading(showLoading: Boolean): BaseViewModel {
        this.loadingView.postValue(showLoading)
        return this
    }

    fun showToast(showToast: String): BaseViewModel {
        this.toast.postValue(showToast)
        return this
    }

    /**
     * 不建议使用此方法进行跳转
     * Activity 应当是要有统一的入口 方便维护与排查入参数
     *
     * @param cls
     */
    fun startActivity(
        cls: Class<out Activity?>?,
        bundle: Bundle? = null,
        result: ActivityResultCallback<ActivityResult> = ActivityResultCallback<ActivityResult> { }
    ) {
        openActivityComponentMutableLiveData.value = OpenActivityComponent(cls, bundle, result)
    }

    fun finish() {
        goBack.value = GoBackComponent(true)
    }

    /**
     * 打开Activity的对象零件
     */
    class OpenActivityComponent @JvmOverloads constructor(
        var activityClass: Class<out Activity?>?,
        var bundle: Bundle? = null,
        var result: ActivityResultCallback<ActivityResult> = ActivityResultCallback<ActivityResult> { },
    )

    class GoBackComponent @JvmOverloads constructor(
        var goBack: Boolean = false,
        var bundle: Bundle? = null
    ) : LiveData<Boolean>(goBack)
}