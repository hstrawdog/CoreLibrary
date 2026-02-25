package com.easy.example.receiver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.easy.core.utils.log.LogUtils;

/**
 * @Author : huangqiqiang
 * @Package : com.easy.example.receiver
 * @Date : 11:58
 * @Email : qiqiang213@gmail.com
 * @Describe : 测试  监听 Service 监听 App 被杀
 */
public class KillAppServers extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //  并没执行到
        super.onTaskRemoved(rootIntent);
    }
}
