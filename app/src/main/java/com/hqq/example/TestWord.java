package com.hqq.example;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * @Author : huangqiqiang
 * @Package : com.hqq.example
 * @Date : 14:11
 * @Email : qiqiang213@gmail.com
 * @Describe :
 */
public class TestWord extends Worker {
    public TestWord(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {


        return Result.success();
    }
}
