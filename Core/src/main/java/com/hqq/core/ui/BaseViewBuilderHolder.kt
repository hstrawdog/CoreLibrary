package com.hqq.core.ui

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.hqq.core.lifecycle.BaseLifecycleObserver
import com.hqq.core.ui.base.IRootView.IBaseViewBuilderHolder
import java.lang.ref.WeakReference

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.core.ui
 * @FileName :   BaseViewBuilderHolder
 * @Date : 2019/10/31 0031  下午 1:23
 * @Email : qiqiang213@gmail.com
 * @Descrive :
 */
abstract class BaseViewBuilderHolder : ViewHolder(), IBaseViewBuilderHolder, BaseLifecycleObserver {
    private var parentView: ViewGroup? = null

    var activity: WeakReference<Activity>? = null

    fun getActivity(): Activity? {
        return activity?.get()
    }

    /**
     * 构建activity
     *
     * @param appCompatActivity
     * @param parentView
     */
    fun builder(appCompatActivity: AppCompatActivity, parentView: ViewGroup?): BaseViewBuilderHolder {
        createRootView(parentView, appCompatActivity, appCompatActivity, appCompatActivity.lifecycle)
        return this
    }

    /**
     * 转移fragment
     *
     * @param fragment
     * @param parentView
     */
    fun builder(fragment: Fragment, parentView: ViewGroup?): BaseViewBuilderHolder {
        createRootView(parentView, fragment.activity, fragment.context, fragment.lifecycle)
        return this
    }

    override fun createRootView(parentView: ViewGroup?, activity: Activity?, context: Context?, lifecycle: Lifecycle) {
        this.parentView = parentView
        this.activity = WeakReference<Activity>(activity)
        convertView = if (layoutViewId <= 0) {
            getLayoutView(this.parentView!!)
        } else {
            LayoutInflater.from(context).inflate(layoutViewId, this.parentView, false)
        }
        lifecycle.addObserver(this)
        initView()
    }

    override fun initConfig() {}
    override fun onClick(view: View) {}
    override fun getLayoutView(viewGroup: ViewGroup): View? {
        return null
    }

    override fun onCrete() {}
    override fun onResume() {}
    override fun onStop() {}
    override fun onPause() {}
    override fun onStart() {}
    override fun onAny() {}
    override fun onDestroy() {
        removeFromParent()
    }

    fun addToParent() {
        if (parentView != null && convertView != null) {
            parentView!!.addView(convertView)
        }
    }

    fun removeFromParent() {
        val parent = convertView!!.parent
        if (parent != null) {
            (parent as ViewGroup).removeView(convertView)
        }
    }


}
