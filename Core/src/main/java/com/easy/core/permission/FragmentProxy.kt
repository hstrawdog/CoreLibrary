package com.easy.core.permission

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.easy.core.CoreConfig
import com.easy.core.permission.dialog.HuaWeiTipDialog
import com.easy.core.utils.BaseSystemUtil
import com.easy.core.utils.data.isNotNull

/**
 * @Author : huangqiqiang
 * @Package :  com.easy.core.permission
 * @FileName :   FragmentProxy
 * @Date : 2019/6/6 0006  上午 11:02
 * @Email : qiqiang213@gmail.com
 * @Describe :
 * 代理类
 */
class FragmentProxy : IPermissionActions {
    /**
     *  核心代理 方法
     * @param permissions Array<String>
     * @param listener PermissionsResult?
     */
    override fun requestPermissions(permissions: Array<String>, listener: PermissionsResult?) {
        requestPermissions(permissions, listener, false, "")
    }

    /**
     *  通常情况下 应该使用这个
     * @param permissions Array<String>
     * @param listener PermissionsResult?
     * @param tipString String   提示框显示的 提示
     */
    override fun requestPermissions(permissions: Array<String>, listener: PermissionsResult?, tipString: String) {
        doRequestPermissions(permissions, listener, tipString, true)

    }

    /**
     *
     * @param permissions Array<String>
     * @param listener PermissionsResult?
     * @param isShowTip Boolean   华为系 是否显示 提示
     */
    override fun requestPermissions(permissions: Array<String>, listener: PermissionsResult?, isShowTip: Boolean,
                                    tipString: String) {
        doRequestPermissions(permissions, listener, tipString, isShowTip)
    }

    /**
     *   执行  权限判断  与 申请权限
     * @param permissions Array<String>
     * @param listener PermissionsResult?
     * @param isShowTip Boolean   是否显示 华为系 提示  默认 不显示
     */
    private fun doRequestPermissions(permissions: Array<String>, listener: PermissionsResult?, tipText: String = "",
                                     isShowTip: Boolean = CoreConfig.get().isShowPermissionTip) {
        // PermissionsHasImpl 判断
        if (!PermissionsHasImpl().hasPermission(CoreConfig.get().application, *permissions)) {
            val mPermissionsFragment =
                PermissionsFragmentFactory.getPermissionsFragment((CoreConfig.get().currActivity as AppCompatActivity).supportFragmentManager)

            if (BaseSystemUtil.isHuaWeiSeriesDevice() && isShowTip && tipText.isNotNull()) {
                showHuaWeiSeriesDevice(tipText) {
                    if (it) {
                        // 核心  申请权限
                        mPermissionsFragment!!.requestPermissions(permissions, listener)
                    } else {
                        // 未同意说明   当做是无权限
                        listener?.onPermissionsResult(false)
                    }
                }
            } else {
                // 核心  申请权限
                mPermissionsFragment!!.requestPermissions(permissions, listener)
            }


        } else {
            listener?.onPermissionsResult(true)
        }
    }

    private fun showHuaWeiSeriesDevice(tipText: String, call: (isHuawei: Boolean) -> Unit) {

        // 在申请权限之前 给用户进行 说明
        CoreConfig.get().currActivity?.let {
            var supportFragmentManager = (it as AppCompatActivity).supportFragmentManager
            HuaWeiTipDialog().apply {
                this.tipText = tipText
                this.call = {
                    call.invoke(it)

                }
            }
                .show(supportFragmentManager)
        }

    }

    private fun isHuaWeiSeriesDevice() {


    }

    /**
     *  忘记了 为什么要传 Fragment 进来
     * @param fragment Fragment
     * @param permissions Array<String>
     * @param listener PermissionsResult?
     */
    fun requestPermissions(fragment: Fragment, permissions: Array<String>, listener: PermissionsResult?) {
        if (!PermissionsHasImpl().hasPermission(CoreConfig.get().application, *permissions)) {
            val mPermissionsFragment = PermissionsFragmentFactory.getPermissionsFragment(fragment.childFragmentManager)
            mPermissionsFragment!!.requestPermissions(permissions, listener)
        } else {
            listener!!.onPermissionsResult(true)
        }
    }
}