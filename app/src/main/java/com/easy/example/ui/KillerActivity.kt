package com.easy.example.ui

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import  android.os.Process
import kotlin.system.exitProcess

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.ui
 * @Date : 11:15
 * @Email : qiqiang213@gmail.com
 * @Describe : 双进程交互实现App自动重启  https://juejin.cn/post/7350126677572304907
 */
class KillerActivity : FragmentActivity() {

    companion object {
        private const val EXTRA_MAIN_PID = "extra_main_pid"

        // 当主进程需要重启时，就直接调用此方法启动KillerActivity
        fun launch(activity: FragmentActivity) {
            activity.startActivity(Intent(activity, KillerActivity::class.java).apply {
                putExtra(EXTRA_MAIN_PID, Process.myPid())
            })
            activity.finish() // 主进程的Activity先关闭
            GlobalScope.launch {
                // 稍作延迟后，主进程kill掉自己
                delay(500L)
                killProcess()
            }
        }

        fun killProcess(pid: Int = Process.myPid()) {
            Process.killProcess(pid)
            exitProcess(0)
        }

        fun isMainProcessAlive(context: Context): Boolean = runCatching {
            (context.getSystemService(
                Context.ACTIVITY_SERVICE) as ActivityManager).runningAppProcesses.find { it.processName == context.packageName } != null
        }.getOrDefault(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            // 此处可以插入一些Loading的UI显示，比如ProgressDialog之类的
            // ...

            // 二次检查，防止主进程没杀掉
            doubleCheckMainProcess()
            // 稍作等待后，再次启动主进程的Activity
            startActivity(Intent(this@KillerActivity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            })

            // 进程B的KillerActivity先关闭，再kill掉自己
            finish()
            killProcess()
        }
    }

    private suspend fun doubleCheckMainProcess() {
        delay(1500L)
        if (isMainProcessAlive(this)) {
            val mainPid = intent.getIntExtra(EXTRA_MAIN_PID, 0)
            if (mainPid != 0) {
                killProcess(mainPid)
                delay(1500L)
            }
        }
    }
}
