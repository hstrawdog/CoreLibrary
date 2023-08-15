package com.easy.core.ui.dialog

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.easy.core.ui.ViewHolder
import com.easy.core.ui.base.IBaseViewBuilderHolder
import java.lang.ref.WeakReference

/**
 * @Author : huangqiqiang
 * @Package : com.easy.core.ui
 * @FileName :   BaseViewBuilderHolder
 * @Date : 2019/10/31 0031  下午 1:23
 * @Email : qiqiang213@gmail.com
 * @Descrive : 布局管理  与创建
 */
abstract class DialogViewBuilder : ViewHolder(), IBaseViewBuilderHolder, DefaultLifecycleObserver {
    /**
     *  window 跟布局对象
     */
    private var parentView: ViewGroup? = null

    /**
     *  弱引用 Activity
     */
    var activity: WeakReference<Activity>? = null


    /**
     *  创建 布局
     * @param parentView ViewGroup?
     * @param activity Activity?
     * @param context Context?
     * @param lifecycle Lifecycle
     */
    override fun createRootView(
        parentView: ViewGroup?, activity: Activity?, context: Context?, lifecycle: Lifecycle
    ) {
        this.parentView = parentView
        this.activity = WeakReference<Activity>(activity)
        convertView = if (getLayoutViewId() <= 0) {
            getLayoutView(this.parentView!!)
        } else {
            LayoutInflater.from(context).inflate(getLayoutViewId(), this.parentView, false)
        }
        lifecycle.addObserver(this)
        initView()
    }

    /**
     *   忽略
     * @param viewGroup ViewGroup
     * @return View?
     */
    override fun getLayoutView(viewGroup: ViewGroup): View? {
        return null
    }

    override fun initConfig() {}
    override fun onClick(view: View) {}
    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        removeFromParent()

    }

    /**
     *  fragment  FragmentDialog  构建入口
     *
     * @param fragment
     * @param parentView
     */
    fun builder(fragment: Fragment, parentView: ViewGroup?): DialogViewBuilder {
        createRootView(parentView, fragment.activity, fragment.context, fragment.lifecycle)
        return this
    }

    /**
     *  添加到布局中
     */
    fun addToParent() {
        if (parentView != null && convertView != null) {
            parentView!!.addView(convertView)
        }
    }

    /**
     * 移除布局
     */
    private fun removeFromParent() {
        convertView?.parent?.let {
            if (it is ViewGroup) {
                it.removeView(convertView)
            }

        }
    }

    /**
     *  获取 Activity
     * @return Activity?
     */
    fun getActivity(): Activity? {
        return activity?.get()
    }

}
