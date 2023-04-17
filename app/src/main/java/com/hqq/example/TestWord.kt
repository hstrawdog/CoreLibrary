package com.hqq.example

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example
 * @Date : 14:11
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
class TestWord(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.success()
    }
}