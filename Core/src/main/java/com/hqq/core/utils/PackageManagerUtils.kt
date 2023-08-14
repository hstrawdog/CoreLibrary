package com.hqq.core.utils

import android.content.Context
import android.content.pm.PackageInfo
import java.util.*

/**
 * @author tamsiree
 * @date 2018/5/2 15:00:00
 *
 *      <uses-permission android:name="android.permission.QUERY_ALL_PACKAGES" tools:ignore="QueryAllPackagesPermission" />
 *
 *       貌似 12 以上需要 添加权限
 */
object PackageManagerUtils {
    @JvmStatic
    fun initPackageManager(mContext: Context): List<PackageInfo> {
        val mPackageManager = mContext.packageManager
        return mPackageManager.getInstalledPackages(0)
    }

    /**
     * 包名是否存在
     * @param mContext 实体
     * @param packageName 待检测 包名
     * @return 结果
     */
    @JvmStatic
    fun haveExistPackageName(mContext: Context, packageName: String): Boolean {
        val packageInfos = initPackageManager(mContext)
        val mPackageNames: MutableList<String> = ArrayList()
        for (i in packageInfos.indices) {
            mPackageNames.add(packageInfos[i].packageName)
        }
        return mPackageNames.contains(packageName)
    }
}